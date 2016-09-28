package com.covenant.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@NamedQueries( { @NamedQuery(name = "Category.findByName", query = "Select c from Category c where c.name = :name" ) } )
public class Category extends AbstractEntity {
	
	
	
	
	public Category(){
		
	}
	
	private String name;
	
	private List<SubCategory> subCategories;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category", orphanRemoval = true)
	public List<SubCategory> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<SubCategory> subCategories) {
		this.subCategories = subCategories;
	}

}
