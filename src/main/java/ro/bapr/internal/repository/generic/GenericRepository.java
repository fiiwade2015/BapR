package ro.bapr.internal.repository.generic;

import java.util.List;

import org.openrdf.query.BindingSet;

import ro.bapr.service.response.Result;

/**
 * Created by valentin.spac on 12/14/2015.
 */
public interface GenericRepository {
    void save(Result result);

    List<BindingSet> query(String query);
}
