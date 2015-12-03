package ro.bapr.internal.service;

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

    @Qualifier("personGraphServiceImpl")
    @Autowired
    private PersonGraphService graphService;

    @Qualifier("personSqlServiceImpl")
    @Autowired
    private PersonSqlService sqlService;

    @Override
    public String testThisShit() {
        return graphService.testThisShit();
    }

    @Override
    public void add() {
        graphService.add();
    }

    @Override
    public Person create(Person person) {
        return sqlService.create(person);
    }

    @Override
    public Person delete(int id) throws PersonNotFound {
        return sqlService.delete(id);
    }

    @Override
    public List<Person> findAll() {
        return sqlService.findAll();
    }

    @Override
    public Person update(Person person) throws PersonNotFound {
        return sqlService.update(person);
    }

    @Override
    public Person findById(int id) {
        return sqlService.findById(id);
    }

    @Override
    public Person findByName(String name) {
        return sqlService.findByName(name);
    }
    
    @Override
    public Person findByEmail(String email){
    	return sqlService.findByEmail(email);
    }

	@Override
	public Person findByEmailAndPassword(String email, String password) {
		return sqlService.findByEmailAndPassword(email, password);
	}
}
