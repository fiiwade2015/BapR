package ro.bapr.internal.service.api;

import ro.bapr.internal.model.request.Journey;
import ro.bapr.internal.model.request.RegisterModel;
import ro.bapr.internal.model.request.UserLocation;
import ro.bapr.internal.model.response.journey.JourneyResult;
import ro.bapr.internal.service.model.ServiceResponse;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
public interface UserService {
    ServiceResponse<String> registerUser(RegisterModel model);
    ServiceResponse<String> addJourney(Journey journey, String userId);
    ServiceResponse<JourneyResult> getJourneys(String userId);

    ServiceResponse<UserLocation> updateUserLocation(String userId, UserLocation location);
}
