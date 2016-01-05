package ro.bapr.external.dbpedia.repository;

import org.openrdf.model.Model;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.QueryResults;
import org.openrdf.repository.RepositoryConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ro.bapr.external.api.factory.ConnectionFactory;
import ro.bapr.external.dbpedia.repository.api.DBPediaRepository;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
@Repository
public class DBpediaRepositoryImpl implements DBPediaRepository {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public Model query(String queryString) {
        RepositoryConnection conn = connectionFactory.getConnectionFor("dbpedia");
        GraphQueryResult result = conn.prepareGraphQuery(QueryLanguage.SPARQL, queryString).evaluate();

        return QueryResults.asModel(result);
    }
}
