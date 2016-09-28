package com.covenant.app.services;

import java.util.regex.Pattern;


import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covenant.app.dao.UserRepository;
import com.covenant.app.dto.UserDto;
import com.covenant.app.model.User;
import static com.covenant.app.utils.Validator.*;

@Service
public class UserService {
	
	
	
	
	@Transactional 
	public User findByUserName(String userName){
		
		User user = userRepository.findUserByName(userName);
		logger.info(" Found user with Username :" + " userName" + "\n " + user);
		return user;
	}
	

	@Transactional 
	public void createUser(UserDto userDto){
		
		assertNotBlank(userDto.getUserName(), "Username cannot be empty.");
        assertValidLength(userDto.getUserName(), 6, "Username must have at least 6 characters.");
        assertNotBlank(userDto.getEmail(), "Email cannot be empty.");
        assertPatternMatches(userDto.getEmail(), EMAIL_REGEX, "Invalid email.");
        assertNotBlank(userDto.getPasswordText(), "Password cannot be empty.");
        assertPatternMatches(userDto.getPasswordText(), PASSWORD_REGEX, "Password must have at least 8 characters, with 1 numeric and 1 uppercase character.");

        if(! userRepository.isUserNameAvailable(userDto.getUserName()))
        	throw new IllegalArgumentException("User Name is not Available ");
        
        User user = new User(userDto.getUserName(),userDto.getPasswordText(),userDto.getEmail());
        
        logger.info("Creating a new User " + user);
        
        userRepository.createUser(user);
	}
	
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Pattern PASSWORD_REGEX = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}");

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    
    private static final Logger logger = Logger.getLogger(UserService.class);
}
