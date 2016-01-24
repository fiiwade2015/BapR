package ro.bapr.internal.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ro.bapr.internal.model.Journey;
import ro.bapr.internal.model.RegisterModel;
import ro.bapr.internal.model.Result;
import ro.bapr.internal.repository.api.UserRepository;
import ro.bapr.internal.service.api.GenericService;
import ro.bapr.internal.service.api.UserService;
import ro.bapr.internal.service.model.ServiceResponse;

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

        Result result = query(getUserJourneysQuery, Collections::emptyList);

        ServiceResponse<Result> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(ServiceResponse.Status.SUCCESS);
        serviceResponse.setResult(result);

        return serviceResponse;
    }
}
