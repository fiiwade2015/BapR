package ro.bapr.external.openmobilenetwork.service.api;

import java.util.List;

import org.openrdf.query.BindingSet;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
public interface OpenMobileNetworkService {

    List<BindingSet> query(String queryString);
}
