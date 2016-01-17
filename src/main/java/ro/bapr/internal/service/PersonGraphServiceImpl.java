package ro.bapr.internal.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.bapr.internal.repository.PersonGraphRepositoryImpl;
import ro.bapr.internal.service.api.PersonGraphService;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 15.11.2015.
 */
@Service
@Deprecated
public class PersonGraphServiceImpl implements PersonGraphService {
    private final Logger log = LogManager.getLogger(PersonGraphServiceImpl.class);
    @Autowired
    private PersonGraphRepositoryImpl repo;

    @Override
    public String testThisShit() {
        return repo.get();
    }

    @Override
    public void add() {
        repo.test();
    }
}
