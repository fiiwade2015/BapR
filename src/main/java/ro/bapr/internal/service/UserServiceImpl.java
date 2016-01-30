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

import ro.bapr.internal.model.Journey;
import ro.bapr.internal.model.KeyValue;
import ro.bapr.internal.model.LDObject;
import ro.bapr.internal.model.RegisterModel;
import ro.bapr.internal.model.Result;
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

    private boolean userExists(RegisterModel model) {
        String checkIfUSerExists = getUserByName.replace(":name:", model.getUsername());
        return !service.query(checkIfUSerExists).isEmpty();
    }

    private boolean userExists(String userId) {
        String checkIfUSerExists = getUserById.replace(":id:", transformDbId(userId, appNamespace));
        return !service.query(checkIfUSerExists).isEmpty();
    }

    @Override
    public ServiceResponse<Result> getJourneys(String userId) {
        String finalUserId = transformDbId(userId, appNamespace);
        String getUserJourneysQuery = getUsersJourney.replaceAll(":id:", finalUserId);

        ConcurrentMap<String, IRI> variableTypes = new ConcurrentHashMap<>();

        List<LDObject> journeyLocation = new ArrayList<>();
        List<LDObject> journeyDatas = new ArrayList<>();

        List<BindingSet> bindingSets = service.query(getUserJourneysQuery);

        bindingSets.stream().forEach(bindings -> splitJourneyLocation(variableTypes, journeyLocation,
                journeyDatas, bindings));


        //add this to the other journey stuff
        List<LDObject> journeyLocations = mergeLocations(journeyLocation);
        List<LDObject> journeys = mergeJourneys(journeyDatas);

        List<Test> finalR = new ArrayList<>();
        journeys.stream().parallel()
                .forEach(journey -> mapJourneyLocation(journeyLocations, finalR, journey));

        ContextCreator creator = new ContextCreator();
        creator.create(getUserJourneysQuery, variableTypes);

        //TODO all that is left is to transform test class into ceva ce poti returna


        ServiceResponse<Result> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(ServiceResponse.Status.SUCCESS);
        serviceResponse.setResult(null);

        return serviceResponse;
    }

    private void mapJourneyLocation(List<LDObject> journeyLocations, List<Test> finalR, LDObject journey) {
        Test s = new Test();
        s.setJurneyData(journey);
        List<LDObject> gggg = journeyLocations.stream()
                .parallel()
                .filter(loc -> {
                    try {
                        return journey.getMap().get("location").getValues()
                                .contains(loc.getMap().get("location").getSingleValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }).collect(Collectors.toList());

        s.setLocations(gggg);
        finalR.add(s);
    }

    private void splitJourneyLocation(ConcurrentMap<String, IRI> variableTypes, List<LDObject> journeyLocation, List<LDObject> journeyDatas, BindingSet bindings) {
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

            if ("location".equalsIgnoreCase(bindingName)) {
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
        r.forEach((k,v) -> {
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
                    return ldObject.getMap().get("location").getValues().get(0);
                }));

        List<LDObject> finalLocationForJourney = new ArrayList<>();

        final LDObject[] finalObj = {null};
        r.forEach((k,v) -> {
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


    private class Test {
        List<LDObject> locations;
        LDObject jurneyData;

        public List<LDObject> getLocations() {
            return locations;
        }

        public Test setLocations(List<LDObject> locations) {
            this.locations = locations;
            return this;
        }

        public LDObject getJurneyData() {
            return jurneyData;
        }

        public Test setJurneyData(LDObject jurneyData) {
            this.jurneyData = jurneyData;
            return this;
        }
    }
}
