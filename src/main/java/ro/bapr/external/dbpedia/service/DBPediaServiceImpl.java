package ro.bapr.external.dbpedia.service;

import java.util.List;

import org.openrdf.query.BindingSet;
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
    public List<BindingSet> query(String queryString) {
        return repo.query(queryString);
    }
}
