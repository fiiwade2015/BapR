package ro.bapr.internal.service.api;

import ro.bapr.internal.model.Journey;
import ro.bapr.internal.model.RegisterModel;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
public interface UserService {
    ServiceResponse<String> registerUser(RegisterModel model);
    ServiceResponse<String> addJourney(Journey journey);
}
