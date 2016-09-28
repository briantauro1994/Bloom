package com.covenant.app.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.covenant.app.dto.UserDto;
import com.covenant.app.model.User;
import com.covenant.app.services.UserService;

@Controller
@RequestMapping("/v1/users")
public class UserController {

	
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.POST)
	public void createUser(@RequestBody UserDto userDto) {
		
		userService.createUser(userDto);
		LOGGER.info("New User Created " + userDto.toEntity().toString()  );
		
	}
	
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public UserDto findUserByName(String userName){
		
		User user = userService.findByUserName(userName);
		LOGGER.info("Found UserName " + userName);
		return user.toDto();
		
	}
	
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> errorHandler(Exception exc) {
	        LOGGER.error(exc.getMessage(), exc);
	        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	
	
	@Autowired
	private UserService userService;
	
	private static final Logger LOGGER = Logger.getLogger(UserController.class);
}
