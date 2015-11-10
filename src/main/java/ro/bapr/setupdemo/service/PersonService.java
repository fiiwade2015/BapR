package ro.bapr.setupdemo.service;

import ro.bapr.setupdemo.model.Person;

import java.util.List;

/**
 * Created by valentin.spac on 11/10/2015.
 */
public interface PersonService {
    public Person create(Person shop);
    public Person delete(int id) throws PersonServiceImpl.PersonNotFound;
    public List<Person> findAll();
    public Person update(Person shop) throws PersonServiceImpl.PersonNotFound;
    public Person findById(int id);
}
