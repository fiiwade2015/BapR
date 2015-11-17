package ro.bapr.external.dbpedia.service;

import org.openrdf.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.bapr.external.dbpedia.repository.api.DBPediaRepository;
import ro.bapr.external.dbpedia.service.api.DBPediaService;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
@Service
public class DBPediaServiceImpl implements DBPediaService {

    @Autowired
    private DBPediaRepository repo;

    @Override
    public TupleQueryResult query(String queryString) {
        return repo.query(queryString);
    }
}
