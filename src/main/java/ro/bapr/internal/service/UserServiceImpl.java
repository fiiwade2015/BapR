package ro.bapr.internal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ro.bapr.internal.model.Journey;
import ro.bapr.internal.model.RegisterModel;
import ro.bapr.internal.repository.api.UserRepository;
import ro.bapr.internal.repository.generic.GenericRepository;
import ro.bapr.internal.service.model.ServiceResponse;
import ro.bapr.internal.service.api.UserService;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Value("${user.get.by.name}")
    private String getUserByName;

    @Autowired
    private GenericRepository repo;

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
    public ServiceResponse<String> addJourney(Journey journey) {
        userRepo.addJourney(journey);
        return null;
    }

    private boolean userExists(RegisterModel model) {
        String checkIfUSerExists = getUserByName.replace(":name:", model.getUsername());
        return !repo.query(checkIfUSerExists).isEmpty();
    }
}
