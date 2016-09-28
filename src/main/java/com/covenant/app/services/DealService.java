package com.covenant.app.services;


import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covenant.app.dao.DealRepository;
import com.covenant.app.model.Deal;

@Service
public class DealService {
	
	@Autowired
	DealRepository dealRepo;
	
	
	@Transactional
	public void saveDeal(Deal deal){
		
		dealRepo.save(deal);
		logger.info("Deal " + deal + " Saved ");
	}
	
	@Transactional
	public Deal findById(Long id){
		
		Deal deal = dealRepo.findDealById(id);
		logger.info("Deal " + deal + " found ");
		return deal;
	}
	
	
	public List<Deal> findAllDeals(){
		
		List<Deal> dealList = dealRepo.findAllDeals();
		return dealList;
	}
	
	
	/*@Transactional
	public List<Deal> findByTitle(String title){
		List<Deal> dealList = dealRepo.findByTitle(title);
		if(dealList.size() != 0)
			logger.info("Deal with title " + title + " found ");
		return dealList;
	}
	*/
	
	private static final Logger logger = LoggerFactory.getLogger(DealService.class);

}
