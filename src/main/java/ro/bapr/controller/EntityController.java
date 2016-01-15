package ro.bapr.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ro.bapr.aop.SeeAlso;
import ro.bapr.service.EntityService;
import ro.bapr.service.response.Result;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 06.12.2015.
 */
@Controller
public class EntityController {

    @Autowired
    private EntityService service;

    @SeeAlso(value = {"/entities/{id}/details"})
    @RequestMapping(value = Endpoint.ENTITIES, method = RequestMethod.GET)
    public ResponseEntity<Result> getEntities(@RequestParam("lat") double lat,
                                              @RequestParam("long") double lng,
                                              @RequestParam(value = "radius",
                                                      required = false) Optional<Double> optionalRadius) {
        Result result = service.getEntities(lat, lng, optionalRadius);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = Endpoint.WIFI, method = RequestMethod.GET)
    public ResponseEntity<Result> getWifi(@RequestParam("lat") double lat,
                                          @RequestParam("long") double lng,
                                          @RequestParam(value = "radius",
                                                  required = false) Optional<Double> optionalRadius) {
        Result result = service.getWifi(lat, lng, optionalRadius);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
