package ro.bapr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.bapr.internal.model.request.RegisterModel;
import ro.bapr.internal.service.model.ServiceResponse;
import ro.bapr.internal.service.api.UserService;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = Endpoint.REGISTER, method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@RequestBody RegisterModel register) {
        ServiceResponse<String> serviceResponse = userService.registerUser(register);

        ResponseEntity<String> requestResponse;
        if(serviceResponse.getStatus().equals(ServiceResponse.Status.SUCCESS)) {
            requestResponse = new ResponseEntity<>(serviceResponse.getResult(), HttpStatus.OK);
        } else {
            requestResponse = new ResponseEntity<>(serviceResponse.getMessage().getDescription(), HttpStatus.CONFLICT);
        }

        return requestResponse;
    }


}
