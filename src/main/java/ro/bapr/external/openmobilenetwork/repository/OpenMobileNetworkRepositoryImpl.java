package ro.bapr.external.openmobilenetwork.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ro.bapr.external.api.factory.ConnectionFactory;
import ro.bapr.external.openmobilenetwork.repository.api.OpenMobileNetworkRepository;
import ro.bapr.util.ContextCreator;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
@Repository
public class OpenMobileNetworkRepositoryImpl implements OpenMobileNetworkRepository {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public Collection<Map<String, Object>> query(String queryString) {
        /*RepositoryConnection conn = connectionFactory.getConnectionFor("omn");
        TupleQueryResult result = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString).evaluate();
*/
        ContextCreator r = new ContextCreator();
        r.create(queryString);

        Collection<Map<String, Object>> results = new ArrayList<>();
        /*BindingSet set;
        while(result.hasNext()) {
            set = result.next();

            Map<String, Object> item = new HashMap<>();
            for(String binding: set.getBindingNames()) {
                item.put(binding, set.getValue(binding).stringValue());
            }
            results.add(item);
        }*/

        return results;
    }
}
