package ro.bapr.external.dbpedia.repository.api;

import java.util.Collection;
import java.util.Map;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
public interface DBPediaRepository {
    Collection<Map<String, Object>> query(String queryString);
}
