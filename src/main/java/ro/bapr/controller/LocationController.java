package ro.bapr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.bapr.internal.model.Journey;
import ro.bapr.internal.model.response.journey.JourneyResult;
import ro.bapr.internal.service.api.UserService;
import ro.bapr.internal.service.model.ServiceResponse;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 18.01.2016.
 */
@Controller
public class LocationController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = Endpoint.USER_LOCATION, method = RequestMethod.POST)
    public ResponseEntity<String> addJourney(@RequestBody Journey journey,
                                             @RequestHeader("X-User") String userId) {
        ServiceResponse<String> serviceResponse = userService.addJourney(journey, userId);

        ResponseEntity<String> requestResponse;
        if(serviceResponse.getStatus().equals(ServiceResponse.Status.SUCCESS)) {
            requestResponse = new ResponseEntity<>(serviceResponse.getResult(), HttpStatus.OK);
        } else {
            requestResponse = new ResponseEntity<>(serviceResponse.getMessage().getDescription(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return requestResponse;
    }

    @RequestMapping(value = Endpoint.USER_LOCATION, method = RequestMethod.GET)
    public ResponseEntity getUserJourneys(@RequestHeader("X-User") String userId) {
        ServiceResponse<JourneyResult> serviceResponse = userService.getJourneys(userId);

        ResponseEntity requestResponse;
        if(serviceResponse.getStatus().equals(ServiceResponse.Status.SUCCESS)) {
            requestResponse = new ResponseEntity<>(serviceResponse.getResult(), HttpStatus.OK);
        } else {
            requestResponse = new ResponseEntity<>(serviceResponse.getMessage().getDescription(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return requestResponse;
    }

}
