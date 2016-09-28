package com.covenant.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.covenant.app.dto.UserDto;

@Entity
@Table(name = "Users")
@NamedQueries( { @NamedQuery(name = "User.findByUserName", query = "Select u from User u where u.userName = :userName" ) } )
public class User extends AbstractEntity {

	
	public User(){
		
	}
	
	
	public User(String aUserName,String aPasswordDigest,String aEmail){
		userName = aUserName;
		passwordDigest = aPasswordDigest;
		email = aEmail;
	}
	
	public User(UserDto userDto){
		
		this(userDto.getUserName(),userDto.getPasswordText(),userDto.getEmail());
	}
	
	@Column(name = "username", nullable = false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "salt", nullable = false)
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = "passwordDigest", nullable = false)
	public String getPasswordDigest() {
		return passwordDigest;
	}

	public void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString(){
		
		return "User [ Name : " + userName + ", Email : " + email  + " ]" ;
	}
	

	public UserDto toDto(){
	
		return new UserDto(this);
	}
	
    private String userName;
	
	private String salt;
	
	private String passwordDigest;
	
	private String email;

}
