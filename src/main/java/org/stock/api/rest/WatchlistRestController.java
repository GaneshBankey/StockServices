package org.stock.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stock.api.model.Watchlist;
import org.stock.api.service.WatchlistService;

@RestController
@RequestMapping("/api")
public class WatchlistRestController {

	@Autowired
	protected WatchlistService service;
	
	@RequestMapping(value = "/watchlists")
	public List<Watchlist> getAllWatchlists(){
		
		return service.getAllWatchlist();
		
	}
	@RequestMapping(value = "/createWatchlist", method=RequestMethod.POST)
	public Long createWatchlist(@RequestParam(name="name", required=true) String name) {
		return service.createWatchlist(name);
	}
}
