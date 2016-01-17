package ro.bapr.internal.service.api;

import java.util.List;

import ro.bapr.internal.model.Person;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 15.11.2015.
 */
@Deprecated
public interface PersonSqlService {
    public Person create(Person shop);
    public Person delete(int id) throws PersonNotFound;
    public List<Person> findAll();
    public Person update(Person shop) throws PersonNotFound;
    public Person findById(int id);
    public Person findByName(String name);
    public Person findByEmail(String email); 
    public Person findByEmailAndPassword(String email, String password);
}
