package org.stock.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.stock.api.model.Watchlist;

@Service
public class WatchlistServiceImpl implements WatchlistService {
	
	public static List<Watchlist> allWatchlists = new ArrayList<>();
	static {
		allWatchlists.add(new Watchlist(1, "Test1"));
		allWatchlists.add(new Watchlist(2, "Test2"));
	}
	@Override
	public List<Watchlist> getAllWatchlist() {
		
		return allWatchlists;
	}
	@Override
	public Long createWatchlist(String name) {
		long newId = allWatchlists.size()+1;
		Watchlist newWatchlist = new Watchlist(newId, name);
		allWatchlists.add(newWatchlist);
		return newId;
	}

}
