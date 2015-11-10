package ro.bapr.setupdemo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.bapr.setupdemo.model.Person;
import ro.bapr.setupdemo.repos.PersonRepo;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by valentin.spac on 11/10/2015.
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Resource
    private PersonRepo personRepository;

    @Override
    @Transactional
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional(rollbackFor=PersonNotFound.class)
    public Person delete(int id) throws PersonNotFound {
        Person deletedPerson = personRepository.findOne(id);

        if (deletedPerson == null)
            throw new PersonNotFound();

        personRepository.delete(deletedPerson);
        return deletedPerson;
    }

    @Override
    @Transactional
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor=PersonNotFound.class)
    public Person update(Person person) throws PersonNotFound {
        Person updatedPerson = personRepository.findOne(person.getId());

        if (updatedPerson == null)
            throw new PersonNotFound();

        updatedPerson.setName(person.getName());
        updatedPerson.setAge(person.getAge());
        return updatedPerson;
    }

    @Override
    public Person findById(int id) {
        return personRepository.findOne(id);
    }

    public class PersonNotFound extends Throwable {
    }
}
