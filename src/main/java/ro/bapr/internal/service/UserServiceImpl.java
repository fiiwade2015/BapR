package ro.bapr.internal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.openrdf.model.IRI;
import org.openrdf.query.BindingSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ro.bapr.internal.model.request.Journey;
import ro.bapr.internal.model.request.UserLocation;
import ro.bapr.internal.model.response.journey.JourneyResult;
import ro.bapr.internal.model.KeyValue;
import ro.bapr.internal.model.LDObject;
import ro.bapr.internal.model.request.RegisterModel;
import ro.bapr.internal.model.response.journey.JourneyData;
import ro.bapr.internal.repository.api.UserRepository;
import ro.bapr.internal.service.api.GenericService;
import ro.bapr.internal.service.api.UserService;
import ro.bapr.internal.service.model.ServiceResponse;
import ro.bapr.internal.utils.ContextCreator;
import ro.bapr.internal.utils.parser.QueryResultsParser;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
@Service
public class UserServiceImpl extends AbstractService implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Value("${user.get.by.name}")
    private String getUserByName;

    @Value("${user.get.by.id}")
    private String getUserById;

    @Value("${user.get.journeys}")
    private String getUsersJourney;

    @Autowired
    private GenericService service;

    public ServiceResponse<String> registerUser(RegisterModel model) {
        ServiceResponse<String> response = new ServiceResponse<>();
        response.setStatus(ServiceResponse.Status.SUCCESS);

        if(!userExists(model)) {
            response.setResult(userRepo.registerUser(model));
        } else {
            //user already exists
            response.setStatus(ServiceResponse.Status.FAIL);
            response.setMessage(ServiceResponse.Messages.USER_EXISTS);
        }

        return response;
    }

    @Override
    public ServiceResponse<String> addJourney(Journey journey, String userId) {
        ServiceResponse<String> response = new ServiceResponse<>();
        response.setStatus(ServiceResponse.Status.SUCCESS);

        if(userExists(userId)) {
            response.setResult(userRepo.addJourney(journey, userId));
        } else {
            //user doesn't exist
            response.setStatus(ServiceResponse.Status.FAIL);
            response.setMessage(ServiceResponse.Messages.USER_NOT_EXISTS);
        }

        return response;
    }

    @Override
    public ServiceResponse<UserLocation> updateUserLocation(String userId, UserLocation location) {
        location = userRepo.updateUserLocation(location, userId);

        ServiceResponse<UserLocation> serviceResponse = new ServiceResponse<>();
        if(location != null) {
            serviceResponse.setStatus(ServiceResponse.Status.SUCCESS);
            serviceResponse.setResult(location);
        } else {
            serviceResponse.setStatus(ServiceResponse.Status.FAIL);
            serviceResponse.setMessage(ServiceResponse.Messages.COULD_NOT_UPDATE_USER_LOCATION);
        }

        return serviceResponse;
    }

    /**
     * Don't modify this. Don't try to understand what this function does.
     * It works, leave it as is. If something breaks, verify query params (locationId, id, creationDate, etc)
     * Increment hour counter spent here : 5
     */
    @Override
    public ServiceResponse<JourneyResult> getJourneys(String userId) {
        String finalUserId = transformDbId(userId, appNamespace);
        String getUserJourneysQuery = getUsersJourney.replaceAll(":id:", finalUserId);

        ConcurrentMap<String, IRI> variableTypes = new ConcurrentHashMap<>();

        List<LDObject> journeyLocation = new ArrayList<>();
        List<LDObject> journeyData = new ArrayList<>();

        List<BindingSet> bindingSets = service.query(getUserJourneysQuery);

        bindingSets.stream().forEach(bindings -> splitJourneyLocation(variableTypes, journeyLocation,
                journeyData, bindings));


        //add this to the other journey stuff
        List<LDObject> journeyLocations = mergeLocations(journeyLocation);
        List<LDObject> journeys = mergeJourneys(journeyData);

        List<JourneyData> finalR = new ArrayList<>();
        journeys.stream().parallel()
                .forEach(journey -> mapJourneyLocation(journeyLocations, finalR, journey));

        ContextCreator creator = new ContextCreator();
        JourneyResult journeyResult = new JourneyResult();
        journeyResult.setContext(creator.create(getUserJourneysQuery, variableTypes));
        journeyResult.setItems(finalR);

        ServiceResponse<JourneyResult> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(ServiceResponse.Status.SUCCESS);
        serviceResponse.setResult(journeyResult);

        return serviceResponse;
    }

    private void mapJourneyLocation(List<LDObject> journeyLocations, List<JourneyData> finalR, LDObject journey) {
        JourneyData s = new JourneyData();
        s.setJourneyData(journey);
        List<LDObject> filteredLocations = journeyLocations.stream()
                .parallel()
                .filter(loc -> {
                    try {
                        return journey.getMap().get("locationId").getValues()
                                .contains(loc.getMap().get("locationId").getSingleValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }).collect(Collectors.toList());

        s.setLocations(filteredLocations);
        s.removeMappingProperty("locationId");
        finalR.add(s);
    }

    private void splitJourneyLocation(ConcurrentMap<String, IRI> variableTypes, List<LDObject> journeyLocation,
                                      List<LDObject> journeyDatas, BindingSet bindings) {
        LDObject locationsData = new LDObject();
        LDObject journeyData = new LDObject();

        bindings.getBindingNames().stream().forEach(bindingName -> {
            org.openrdf.model.Value value = bindings.getBinding(bindingName).getValue();
            QueryResultsParser.updateVariableTypes(variableTypes, bindingName, value);

            if (Arrays.asList("id", "creationDate", "name", "status").contains(bindingName)) {
                journeyData.addKeyValue(new KeyValue(bindingName, value.stringValue()));
            } else {
                locationsData.addKeyValue(new KeyValue(bindingName, value.stringValue()));
            }

            if ("locationId".equalsIgnoreCase(bindingName)) {
                journeyData.addKeyValue(new KeyValue(bindingName, value.stringValue()));
            }
        });

        journeyLocation.add(locationsData);
        journeyDatas.add(journeyData);
    }

    private  List<LDObject> mergeJourneys(List<LDObject> journeyDatas) {
        Map<String, List<LDObject>> r = journeyDatas.stream()
                .parallel()
                .collect(Collectors.groupingBy(LDObject::getId));

        List<LDObject> result = new ArrayList<>();

        final LDObject[] finalObj = {null};
        r.forEach((k, v) -> {
            v.stream().forEach(ldObject -> {
                if (finalObj[0] == null) {
                    finalObj[0] = ldObject;
                } else {
                    finalObj[0].merge(ldObject);
                }
            });
            result.add(finalObj[0]);
            finalObj[0] = null;
        });

        return result;
    }

    private List<LDObject> mergeLocations(List<LDObject> ldObjects) {
        Map<String, List<LDObject>> r = ldObjects.stream()
                .parallel()
                .collect(Collectors.groupingBy(ldObject -> {
                    return ldObject.getMap().get("locationId").getValues().get(0);
                }));

        List<LDObject> finalLocationForJourney = new ArrayList<>();

        final LDObject[] finalObj = {null};
        r.forEach((k, v) -> {
            v.stream().forEach(ldObject -> {
                if (finalObj[0] == null) {
                    finalObj[0] = ldObject;
                } else {
                    finalObj[0].merge(ldObject);
                }
            });
            finalLocationForJourney.add(finalObj[0]);
            finalObj[0] = null;
        });

        return finalLocationForJourney;
    }

    private boolean userExists(RegisterModel model) {
        String checkIfUSerExists = getUserByName.replace(":name:", model.getUsername());
        return !service.query(checkIfUSerExists).isEmpty();
    }

    private boolean userExists(String userId) {
        String checkIfUSerExists = getUserById.replace(":id:", transformDbId(userId, appNamespace));
        return !service.query(checkIfUSerExists).isEmpty();
    }

}
