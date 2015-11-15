package ro.bapr.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.bapr.repository.PersonGraphRepositoryImpl;
import ro.bapr.service.api.PersonGraphService;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 15.11.2015.
 */
@Service
public class PersonGraphServiceImpl implements PersonGraphService {
    private final Logger log = LogManager.getLogger(PersonGraphServiceImpl.class);
    @Autowired
    private PersonGraphRepositoryImpl repo;

    @Override
    public String testThisShit() {
        log.debug("Test this shit called");
        return repo.get();
    }

    @Override
    public void add() {
        log.debug("Add method called");
        repo.test();
    }
}
