package ro.bapr.external.openmobilenetwork.service.api;

import java.util.Collection;
import java.util.Map;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
public interface OpenMobileNetworkService {

    Collection<Map<String, Object>> query(String queryString);
}
