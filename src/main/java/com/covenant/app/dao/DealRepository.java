package com.covenant.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.covenant.app.model.Deal;

@Repository
public class DealRepository {

	
	
	
	public void save(Deal deal){
		
		em.persist(deal);
	}
	
	
	public Deal findDealById(Long id){
		
		return em.find(Deal.class,id);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Deal> findAllDeals(){
		
		Query query = em.createQuery("Select d from Deal d");
		return (List<Deal>) query.getResultList();
	}
	
	public List<Deal> findDealByTitle(String dealTitle){
		
		List<Deal> dealList = em.createNamedQuery("Deal.findByTitle",Deal.class)
		.setParameter("title", dealTitle)
		.getResultList();
		
		return dealList;
	}
	
	public void deleteDeal(Long dealId){
		
		Deal deal = em.find(Deal.class, dealId);
		em.remove(deal);
	}
	
	
	
	
	
	
	
	
	@PersistenceContext 
	private EntityManager em;
}
