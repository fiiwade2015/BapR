package ro.bapr.internal.service.generic;

import java.util.List;

import org.openrdf.query.BindingSet;

import ro.bapr.services.response.Result;

/**
 * Created by valentin.spac on 12/14/2015.
 */
public interface GenericService {
    void save(Result result);
    List<BindingSet> query(String query);
}
