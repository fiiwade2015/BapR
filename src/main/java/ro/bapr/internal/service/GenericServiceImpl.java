package ro.bapr.internal.service;

import java.util.List;

import org.openrdf.query.BindingSet;
import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.bapr.internal.model.Result;
import ro.bapr.internal.repository.api.GenericRepository;
import ro.bapr.internal.service.api.GenericService;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 14.12.2015.
 */
@Service
public class GenericServiceImpl implements GenericService {

    @Autowired
    private GenericRepository repository;


    @Override
    public void save(Result result) {
        repository.save(result);
    }

    @Override
    public List<BindingSet> query(String query) {
        return repository.query(query);
    }

    @Override
    public String query(String query, String mimeType) {
        return repository.query(query, TupleQueryResultFormat.JSON);
    }
}
