package ro.bapr.setupdemo.controller.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.bapr.setupdemo.model.Person;
import ro.bapr.setupdemo.service.PersonService;
import ro.bapr.setupdemo.service.PersonServiceImpl;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 31.10.2015.
 */
@RestController
public class PersonRestController {
    private final Logger log = LogManager.getLogger(PersonRestController.class);

    @Autowired
    private PersonService s;


    @RequestMapping(value = RestURIConstants.TEST_URI, method = RequestMethod.GET)
    public ResponseEntity<Person> getPerson() {
        Person test = new Person("gigel", 21);

        test = s.create(test);
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @RequestMapping(value = RestURIConstants.TEST_URI, method = RequestMethod.POST)
    public ResponseEntity<Person> setPerson(@RequestBody Person p) {
        log.info(p.getName());
        return new ResponseEntity<Person>(p, HttpStatus.OK);
    }

}
