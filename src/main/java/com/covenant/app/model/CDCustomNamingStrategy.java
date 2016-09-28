package com.covenant.app.model;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * Custom Naming Strategy for Covenant Deals with the Prefix of CD_
 * @author lenindr
 *
 */
public class CDCustomNamingStrategy extends ImprovedNamingStrategy{
	
	
	
	
	@Override
	public String columnName(String columnName) {
		return super.columnName(columnName).toUpperCase();
	}
	
	@Override
	public String propertyToColumnName(String propertyName) {
		return super.propertyToColumnName(propertyName).toUpperCase();
	}
	
	@Override
	public String tableName(String tableName) {
		if (tableName.contains("AUD") || tableName.contains("aud"))
			if (tableName.contains(APPLICATION_SCHEMA_PREFIX)
					|| tableName.contains(APPLICATION_SCHEMA_PREFIX.toLowerCase()))
				return super.tableName(tableName).toUpperCase();
			else
				return (APPLICATION_SCHEMA_PREFIX + super.tableName(tableName)).toUpperCase();
		else
			return (APPLICATION_SCHEMA_PREFIX + super.tableName(tableName)).toUpperCase();
	}
	
	private static final long serialVersionUID = 1L;
	
	private static final String APPLICATION_SCHEMA_PREFIX = "CD_";

}
