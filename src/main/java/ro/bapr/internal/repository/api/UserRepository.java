package ro.bapr.internal.repository.api;

import ro.bapr.internal.model.request.Journey;
import ro.bapr.internal.model.request.RegisterModel;
import ro.bapr.internal.model.request.UserLocation;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
public interface UserRepository {

    String registerUser(RegisterModel model);
    String addJourney(Journey journey, String userId);
    UserLocation updateUserLocation(UserLocation userLocation, String userId);
}
