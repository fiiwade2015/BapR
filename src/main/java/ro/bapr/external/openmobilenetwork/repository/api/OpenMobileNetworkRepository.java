package ro.bapr.external.openmobilenetwork.repository.api;

import java.util.Collection;
import java.util.Map;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
public interface OpenMobileNetworkRepository {
    Collection<Map<String, Object>> query(String queryString);
}
