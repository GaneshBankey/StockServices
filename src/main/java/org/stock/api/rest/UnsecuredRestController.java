package org.stock.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stock.api.auth.UserAuthenticationService;
import org.stock.api.model.MessageBody;
import org.stock.api.user.crud.api.UserCrudService;
import org.stock.api.user.entity.User;
import org.stock.api.validation.ValidateParam;
import org.stock.api.validation.ValidateParams;

@RestController
@RequestMapping("/api/public/users")
public class UnsecuredRestController {

	@Autowired
	UserAuthenticationService authentication;
	
	@Autowired
	UserCrudService users;
	
	@GetMapping("/getMessage")
	public String getMessage() {
		return "My Message";
	}
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public  ResponseEntity < String > register(
    	    @RequestParam("username") final String username,
    	    @RequestParam("password") final String password) {
    	users.save(new User(username,username,password));

      return login(username, password);
    }
	
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity < String > login(
    	    @RequestParam("username") final String username,
    	    @RequestParam("password") final String password) {
    	
    	return new ResponseEntity (authentication
    		      .login(username, password)
    		      .orElseThrow(() -> new RuntimeException("invalid login and/or password")),HttpStatus.CREATED);
    }
    
    @ValidateParams(
    		isBodyValidation = true,
    		value = {@ValidateParam(name = "sourceId", required=true)}
	)
    @PostMapping(path="/validate", consumes="application/json")
	public MessageBody validate(@RequestParam(name="sourceId") String sourceID, @RequestBody MessageBody payload) {
		return payload;
	}
}
