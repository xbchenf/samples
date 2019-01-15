package com.xbchen.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id,User user1) {
		logger.info("Fetching User with id {}", id);
		User user=new User();
		user.setName(user1.getName());
		user.setId(1001);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user1) {
		logger.info("Creating User : {}", user1);
		User user=new User();
		user.setName(user1.getName());
		user.setId(1001);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

}