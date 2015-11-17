package ro.bapr.external.dbpedia.manager.api;

import org.openrdf.repository.RepositoryConnection;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
public interface DbpediaManager {
    RepositoryConnection getConnection();
}
