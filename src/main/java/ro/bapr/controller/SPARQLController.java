package ro.bapr.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ro.bapr.internal.service.api.GenericService;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 23.01.2016.
 */
@Controller
public class SPARQLController {

    @Autowired
    private GenericService genericService;

    @RequestMapping(value = Endpoint.SPARQL, method = RequestMethod.GET)
    public ResponseEntity<JsonNode> queryTripleStore(@RequestParam("q") String query,
                                                   @RequestHeader(value = "Content-Type",
                                                           required = false) String mimeType) throws IOException {
        String result = genericService.query(query, mimeType);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(result);

        return new ResponseEntity<>(actualObj, HttpStatus.OK);
    }

}
