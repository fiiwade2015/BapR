package ro.bapr.external.dbpedia.service;

import java.util.Collection;
import java.util.Map;

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
    public Collection<Map<String, Object>> query(String queryString) {
        return repo.query(queryString);
    }
}
