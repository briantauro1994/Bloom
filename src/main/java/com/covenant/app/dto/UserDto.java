package com.covenant.app.dto;

import com.covenant.app.model.User;

public class UserDto {
	
	
	
	public UserDto(){
		
	}
	
	public UserDto(User user){
		
		this(user.getUserName(),user.getPasswordDigest(),user.getEmail());
		
	}
	
	public UserDto(String aUserName,String aPassword,String aEmail){
		
		userName = aUserName;
		
		passwordText = aPassword;
		
		email = aEmail;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordText() {
		return passwordText;
	}

	public void setPasswordText(String passwordText) {
		this.passwordText = passwordText;
	}
	
	
	public User toEntity(){
		
		return new User(this); 
	}
	

	private String userName;
	
	private String passwordText;
	
	private String email;
	
	

}
