package ro.bapr.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.bapr.internal.model.Person;
import ro.bapr.internal.service.api.PersonService;
import ro.bapr.internal.utils.security.EncryptionUtil;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 31.10.2015.
 */
@RestController
public class PersonRestController {
    private final Logger log = LogManager.getLogger(PersonRestController.class);

    @Autowired
    private PersonService s;

    @RequestMapping(value = Endpoint.TEST_URI, method = RequestMethod.GET)
    public ResponseEntity<Person> getPerson() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Person test = new Person("gigel1@yahoo.com", "gigel" ,EncryptionUtil.getSHA256Hash("123"));
        s.add();
        test = s.create(test);
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @RequestMapping(value = Endpoint.TEST_URI, method = RequestMethod.POST)
    public ResponseEntity<Person> setPerson(@RequestBody Person p) {
        log.info(p.getName());
        return new ResponseEntity<Person>(p, HttpStatus.OK);
    }

    @RequestMapping(value = Endpoint.GRAPH_TEST_URI, method = RequestMethod.GET)
    public ResponseEntity<String> getGraphPerson() {

        return new ResponseEntity<>(s.testThisShit(), HttpStatus.OK);
    }

}
