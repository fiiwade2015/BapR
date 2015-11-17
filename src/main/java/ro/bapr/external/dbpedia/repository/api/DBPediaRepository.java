package ro.bapr.external.dbpedia.repository.api;

import org.openrdf.query.TupleQueryResult;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
public interface DBPediaRepository {

    TupleQueryResult query(String queryString);
}