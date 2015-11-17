package ro.bapr.internal.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ro.bapr.internal.model.Person;
import ro.bapr.internal.service.api.PersonGraphService;
import ro.bapr.internal.service.api.PersonNotFound;
import ro.bapr.internal.service.api.PersonService;
import ro.bapr.internal.service.api.PersonSqlService;

import java.util.List;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 15.11.2015.
 */
@Service
public class PersonServiceImpl implements PersonService {
    private final Logger log = LogManager.getLogger(PersonServiceImpl.class);

    @Qualifier("personGraphServiceImpl")
    @Autowired
    private PersonGraphService graphService;

    @Qualifier("personSqlServiceImpl")
    @Autowired
    private PersonSqlService sqlService;

    @Override
    public String testThisShit() {
        log.debug("Method call");
        return graphService.testThisShit();
    }

    @Override
    public void add() {
        log.debug("Method call");
        graphService.add();
    }

    @Override
    public Person create(Person person) {
        log.debug("Method call");
        return sqlService.create(person);
    }

    @Override
    public Person delete(int id) throws PersonNotFound {
        log.debug("Method call");
        return sqlService.delete(id);
    }

    @Override
    public List<Person> findAll() {
        log.debug("Method call");
        return sqlService.findAll();
    }

    @Override
    public Person update(Person person) throws PersonNotFound {
        log.debug("Method call");
        return sqlService.update(person);
    }

    @Override
    public Person findById(int id) {
        log.debug("Method call");
        return sqlService.findById(id);
    }

    @Override
    public Person findByName(String name) {
        log.debug("Method call with param {}", name);
        return sqlService.findByName(name);
    }
}
