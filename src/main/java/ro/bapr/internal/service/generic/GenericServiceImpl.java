package ro.bapr.internal.service.generic;

import java.util.List;

import org.openrdf.query.BindingSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.bapr.internal.repository.generic.GenericRepository;
import ro.bapr.services.response.Result;

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
}
