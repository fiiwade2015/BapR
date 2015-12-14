package ro.bapr.external.dbpedia.service.api;

import java.util.Collection;
import java.util.Map;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
public interface DBPediaService {

    Collection<Map<String, Object>> query(String queryString);
}
