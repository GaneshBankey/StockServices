package org.stock.api.validation;

public enum WhiteList {
    DEFAULT("[<>]");
	
    WhiteList(String pattern){
    	this.pattern = pattern;
    }
    private String pattern;
    
    public String getPattern() {
    	return this.pattern;
    }
}
