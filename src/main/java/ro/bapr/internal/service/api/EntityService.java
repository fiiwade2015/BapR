package ro.bapr.internal.service.api;

import java.util.Optional;

import ro.bapr.internal.model.Result;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
public interface EntityService {
    Result getEntities(double lat, double lng, Optional<Double> optionalRadius);

    Result getWifi(double lat, double lng, Optional<Double> optionalRadius);
    
    Result getEntityDetails(String resourceId);
}
