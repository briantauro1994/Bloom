package com.covenant.app.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.covenant.app.model.Deal;

public class DealDto {
	
	
	
	public DealDto(){
		
	}
	
	public DealDto(String aTitle,String aStatus){
		title = aTitle;
		status = aStatus;
	}
	
	public DealDto getDtoFromEntity(Deal deal){
		
		return new DealDto(deal.getTitle(),deal.getStatus());
		
	}
	
	public List<DealDto> getAllDto(List<Deal> dealsList){
		
		return dealsList.stream().map( (deal) -> getDtoFromEntity(deal) ).collect(Collectors.toList()); 
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String title;
	
	private String status;
}
