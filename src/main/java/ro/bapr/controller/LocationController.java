package ro.bapr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.bapr.internal.model.Journey;
import ro.bapr.internal.service.api.ServiceResponse;
import ro.bapr.internal.service.api.UserService;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 18.01.2016.
 */

public class LocationController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = Endpoint.REGISTER, method = RequestMethod.POST)
    public ResponseEntity<String> getEntities(@RequestBody Journey journey) {
        ServiceResponse<String> serviceResponse = userService.addJourney(journey);

        ResponseEntity<String> requestResponse;
        if(serviceResponse.getStatus().equals(ServiceResponse.Status.SUCCESS)) {
            requestResponse = new ResponseEntity<>(serviceResponse.getResult(), HttpStatus.OK);
        } else {
            requestResponse = new ResponseEntity<>(serviceResponse.getMessage().getDescription(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return requestResponse;
    }
}
