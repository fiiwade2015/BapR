package ro.bapr.external.dbpedia.service.api;

import org.openrdf.query.TupleQueryResult;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
public interface DBPediaService {

    TupleQueryResult query(String queryString);
}
