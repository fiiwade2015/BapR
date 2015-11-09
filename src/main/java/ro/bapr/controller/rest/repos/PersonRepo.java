package ro.bapr.controller.rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ro.bapr.controller.rest.model.Person;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 09.11.2015.
 */
@EnableJpaRepositories
public interface PersonRepo extends JpaRepository<Person, Integer> {
//nice link: https://github.com/Fruzenshtein/spr-data/tree/master/src/main/java/com/spr/service
}
