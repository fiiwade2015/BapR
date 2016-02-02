package ro.bapr.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ro.bapr.internal.model.request.EntityReview;
import ro.bapr.internal.model.response.Result;
import ro.bapr.internal.service.api.EntityService;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 06.12.2015.
 */
@Controller
public class EntityController {

    @Autowired
    private EntityService service;

    //@SeeAlso(value = {"/entities/{id}/details"})
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
    
    /**
     * Method for getting details (such as: thumbnail, externalLinks, abstract and name) associated with a resourceId
     * @param resourceId - The id of the resource
     * @return
     */
    @RequestMapping(value = Endpoint.ENTITY_DETAILS, method = RequestMethod.GET)
    public ResponseEntity<Result> getEntityDetails(@PathVariable("id") String resourceId){
    	Result result = service.getEntityDetails(resourceId);
    			
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Method for getting details (such as: thumbnail, externalLinks, abstract and name) associated with a resourceId
     * @param resourceId - The id of the resource
     * @return
     */
    @RequestMapping(value = Endpoint.ENTITY_DETAILS, method = RequestMethod.PUT)
    public ResponseEntity<EntityReview> addEntityReview(@PathVariable("id") String resourceId,
                                                        @RequestHeader("X-User") String userId,
                                                        @RequestBody EntityReview review){

        //update entity details with what comes from request
        EntityReview result = service.addReview(resourceId, userId, review);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
