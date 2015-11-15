package ro.bapr.service.api;

import ro.bapr.model.Person;

import java.util.List;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 15.11.2015.
 */
public interface PersonSqlService {
    public Person create(Person shop);
    public Person delete(int id) throws PersonNotFound;
    public List<Person> findAll();
    public Person update(Person shop) throws PersonNotFound;
    public Person findById(int id);
}
