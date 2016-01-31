package ro.bapr.internal.repository.api;

import java.util.List;

import org.openrdf.query.BindingSet;
import org.openrdf.query.resultio.TupleQueryResultFormat;

import ro.bapr.internal.model.response.Result;

/**
 * Created by valentin.spac on 12/14/2015.
 */
public interface GenericRepository {
    void save(Result result);

    List<BindingSet> query(String query);

    String query(String queryString, TupleQueryResultFormat format);
}
