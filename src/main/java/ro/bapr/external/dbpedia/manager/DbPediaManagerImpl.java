package ro.bapr.external.dbpedia.manager;


import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.sparql.SPARQLRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ro.bapr.external.dbpedia.manager.api.DbpediaManager;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
@Component
public class DbPediaManagerImpl implements DbpediaManager {

    @Value("${dbpedia.sparql.endpoint}")
    private String connectionUrl;

    private SPARQLRepository repo;

    public RepositoryConnection getConnection() {
        if(repo == null) {
            repo = new SPARQLRepository(connectionUrl);
            repo.initialize();
        }

        return repo.getConnection();
    }
}
