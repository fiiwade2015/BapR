package ro.bapr.external.dbpedia.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ro.bapr.external.api.factory.ConnectionFactory;
import ro.bapr.external.dbpedia.repository.api.DBPediaRepository;
import ro.bapr.util.ContextCreator;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
@Repository
public class DBpediaRepositoryImpl implements DBPediaRepository {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public Collection<Map<String, Object>> query(String queryString) {
        RepositoryConnection conn = connectionFactory.getConnectionFor("dbpedia");
        TupleQueryResult result = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString).evaluate();

        ContextCreator r = new ContextCreator();
        r.create(queryString);

        Collection<Map<String, Object>> results = new ArrayList<>();
        BindingSet set;
        while(result.hasNext()) {
            set = result.next();

            Map<String, Object> item = new HashMap<>();
            for(String binding: set.getBindingNames()) {
                item.put(binding, set.getValue(binding).stringValue());
            }
            results.add(item);
        }

        return results;
    }
}
