package ro.bapr.external.api.factory;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.sparql.SPARQLRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 21.11.2015.
 */
@Component("repositoryConnectionFactory")
public class ConnectionFactory {

    @Value("${dbpedia.sparql.endpoint}")
    private String dbpediaUrl;

    public RepositoryConnection getConnectionFor(String identifier) {
        switch (identifier) {
            case "dbpedia": return getConnection(dbpediaUrl);
            default: throw new IllegalArgumentException(String.format("Invalid identifier [%s]" , identifier));
        }
    }

    private RepositoryConnection getConnection(String url) {
        Repository repo = new SPARQLRepository(url);
        repo.initialize();

        return repo.getConnection();
    }

}
