package ro.bapr.external.dbpedia.repository;

import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ro.bapr.external.dbpedia.manager.api.DbpediaManager;
import ro.bapr.external.dbpedia.repository.api.DBPediaRepository;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
@Repository
public class DBpediaRepositoryImpl implements DBPediaRepository {

    @Autowired
    private DbpediaManager manager;

    @Override
    public TupleQueryResult query(String queryString) {
        RepositoryConnection conn = manager.getConnection();
        TupleQueryResult result = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString).evaluate();
/*

        BindingSet set;
        while(result.hasNext()) {
            set = result.next();
            System.out.println(set);
        }
*/

        return result;
    }
}
