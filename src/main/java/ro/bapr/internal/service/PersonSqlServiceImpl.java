package ro.bapr.internal.service;

import javax.annotation.Resource;
import java.util.List;

import org.springframework.stereotype.Service;

import ro.bapr.internal.model.Person;
import ro.bapr.internal.repository.api.PersonRepository;
import ro.bapr.internal.service.api.PersonNotFound;
import ro.bapr.internal.service.api.PersonSqlService;

/**
 * Created by valentin.spac on 11/10/2015.
 */
@Service
@Deprecated
public class PersonSqlServiceImpl implements PersonSqlService {

    @Resource
    private PersonRepository personRepository;

    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person delete(int id) throws PersonNotFound {
        Person deletedPerson = personRepository.findOne(id);

        if (deletedPerson == null)
            throw new PersonNotFound();

        personRepository.delete(deletedPerson);
        return deletedPerson;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person update(Person person) throws PersonNotFound {
        Person updatedPerson =  personRepository.findOne(person.getId().intValue());

        if (updatedPerson == null)
            throw new PersonNotFound();

        updatedPerson.setName(person.getName());
        updatedPerson.setEmail(person.getEmail());
        return updatedPerson;
    }

    @Override
    public Person findById(int id) {
        return personRepository.findOne(id);
    }

    @Override
    public Person findByName(String name) {
        return personRepository.findByName(name);
    }

	@Override
	public Person findByEmail(String email) {
		return personRepository.findByEmail(email);
	}

	@Override
	public Person findByEmailAndPassword(String email, String password) {
		return personRepository.findByEmailAndPassword(email, password);
	}
}
