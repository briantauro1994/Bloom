package com.covenant.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name = "Deals")
@NamedQueries( { @NamedQuery(name = "Deal.findByTitle", query = "Select d from Deal d where d.title = :title" ) } )
public class Deal extends AbstractEntity {
	
	
	
	public Deal(){
		
	}
	
	public Deal(String aTitle, String aStatus){
		title = aTitle;
		status = aStatus;
	}
	
	
	@Column(name = "title",nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "dealStatus", nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString(){
		
		return "Deal [ " + title + " " + status + " ]";
	}
	
	private String status;
	
	private String title;

}
