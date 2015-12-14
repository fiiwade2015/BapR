package ro.bapr.internal.service.generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.bapr.internal.repository.generic.GenericRepository;
import ro.bapr.response.Result;

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
}
