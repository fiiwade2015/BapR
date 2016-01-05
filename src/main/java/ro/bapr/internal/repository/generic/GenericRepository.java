package ro.bapr.internal.repository.generic;

import org.openrdf.query.TupleQueryResult;

import ro.bapr.response.Result;

/**
 * Created by valentin.spac on 12/14/2015.
 */
public interface GenericRepository {
    void save(Result result);
    void save(TupleQueryResult result);

    void query(String query);
}
