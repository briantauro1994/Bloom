package com.covenant.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.covenant.app.model.User;

@Repository
public class UserRepository {
	
	
	
	public void createUser(User user){
		em.persist(user);
		logger.info("New User Created " + user );
	}
	
	
	public User findById(Long pkey){
		User user = em.find(User.class, pkey);
		return user;
	}
	
	public User findUserByName(String userName){
		
		User user = (User) em.createNamedQuery("User.findByUserName",User.class)
				.setParameter("username", userName)
				.getSingleResult();
		
		return user;
	}
	
	public boolean isUserNameAvailable(String newUserName){
		
		List<User> userList = em.createQuery("User.findByUserName",User.class)
				.setParameter("username",newUserName)
				.getResultList();
		
		return userList.isEmpty();
	}
	
	
	
	
	
	@PersistenceContext
	private EntityManager em;
	
	private static final Logger logger = Logger.getLogger(UserRepository.class);

}
