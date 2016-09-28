package com.covenant.app.utils;

import java.util.regex.Pattern;




public class Validator {

	
	
	private Validator(){
		
	}
	
	
	public static void assertNotBlank(String text,String message){
		
	}
	
	public static void assertValidLength(String text,int minLength,String errorMessage){
		if(text.length() < minLength)
			throw new IllegalArgumentException(errorMessage);
	}
	
	public static void assertPatternMatches(String text, Pattern pattern, String errorMessage){
		
		if(!pattern.matcher(text).matches())
			throw new IllegalArgumentException(errorMessage);
		
	}
	
	
}
