package ro.bapr.external.dbpedia.repository.api;

import java.util.List;

import org.openrdf.model.Statement;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
public interface DBPediaRepository {
    List<Statement> query(String queryString);
}
