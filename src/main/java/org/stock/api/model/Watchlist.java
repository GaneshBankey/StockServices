package org.stock.api.model;

/**
 * 
 * @author gbankey
 *
 */
public class Watchlist {
	
	public long id;
	public String name;
	
	public Watchlist(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
