package ro.bapr.internal.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ro.bapr.internal.model.Person;
import ro.bapr.internal.repository.api.PersonRepository;
import ro.bapr.internal.service.api.PersonNotFound;
import ro.bapr.internal.service.api.PersonSqlService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by valentin.spac on 11/10/2015.
 */
@Service
public class PersonSqlServiceImpl implements PersonSqlService {
    private final Logger log = LogManager.getLogger(PersonServiceImpl.class);

    @Resource
    private PersonRepository personRepository;

    @Override
    public Person create(Person person) {
        log.debug("Method call with params {}", person);
        return personRepository.save(person);
    }

    @Override
    public Person delete(int id) throws PersonNotFound {
        log.debug("Method call with params: {}", id);
        Person deletedPerson = personRepository.findOne(id);

        if (deletedPerson == null)
            throw new PersonNotFound();

        personRepository.delete(deletedPerson);
        return deletedPerson;
    }

    @Override
    public List<Person> findAll() {
        log.debug("Method call");
        return personRepository.findAll();
    }

    @Override
    public Person update(Person person) throws PersonNotFound {
        log.debug("Method call with params {}", person);
        Person updatedPerson = personRepository.findOne(person.getId());

        if (updatedPerson == null)
            throw new PersonNotFound();

        updatedPerson.setName(person.getName());
        updatedPerson.setAge(person.getAge());
        return updatedPerson;
    }

    @Override
    public Person findById(int id) {
        log.debug("Method call with params: {}", id);
        return personRepository.findOne(id);
    }

    @Override
    public Person findByName(String name) {
        log.debug("Method call with params: {}", name);
        return personRepository.findByName(name);
    }
}
