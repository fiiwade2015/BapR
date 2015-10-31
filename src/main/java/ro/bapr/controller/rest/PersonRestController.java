package ro.bapr.controller.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.bapr.controller.rest.model.Person;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 31.10.2015.
 */
@RestController
public class PersonRestController {
    private final Logger log = LogManager.getLogger(PersonRestController.class);

    @RequestMapping(value = RestURIConstants.TEST_URI, method = RequestMethod.GET)
    public ResponseEntity<Person> getPerson() {
        return new ResponseEntity<Person>(new Person("gigel", 21), HttpStatus.OK);
    }

    @RequestMapping(value = RestURIConstants.TEST_URI, method = RequestMethod.POST)
    public ResponseEntity<Person> setPerson(@RequestBody Person p) {
        log.info(p.getName());
        return new ResponseEntity<Person>(p, HttpStatus.OK);
    }

}
