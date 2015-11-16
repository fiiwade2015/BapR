package ro.bapr.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.bapr.model.Person;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 15.11.2015.
 */
public interface PersonSqlRepository extends JpaRepository<Person, Integer> {
    Person findByName(String name);
}