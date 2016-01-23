package ro.bapr.external.dbpedia.service.api;

import java.util.List;

import org.openrdf.query.BindingSet;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
public interface DBPediaService {

    List<BindingSet> query(String queryString);
}
