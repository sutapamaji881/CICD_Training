package com.example;

/**
 * This is a class.
 */
public class Greeter {

	/**
	 * This is a constructor.
	 */
	public Greeter() {

	}

	// TODO: Add javadoc comment
	public String greet(String someone) {
		return String.format("Hello How are you, %s!", someone);
	}

	public String greet1(String someone) {
		return String.format("Hello How are you, %s!", someone);
	}
	
	
	public void codeSmell() {
		
		String unusedVariable;
		
		int val;
		if(true) {
			val = 1;
		}
		
		boolean flag;
		
		if(flag=true) {
			val = 2;
		}
		
	}
	
public void duplicate() {
		
		String unusedVariable;
		
		int val;
		if(true) {
			val = 1;
		}
		
		boolean flag;
		
		if(flag=true) {
			val = 2;
		}
		
	}
	
	public String greet2(String someone) {
		return String.format("Hello How are you, %s!", someone);
	}

	public String greet3(String someone) {
		return String.format("Hello How are you, %s!", someone);
	}
	
	
}
