package com.covenant.app.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sub_categories")
public class SubCategory extends AbstractEntity {
	
	
	public SubCategory(){
		
	}
	
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @ManyToOne
	@JoinColumn(name = "categories_id", nullable = false, updatable = true)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	private Category category;

}
