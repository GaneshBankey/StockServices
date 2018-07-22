package org.stock.api.service;

import java.util.List;

import org.stock.api.model.Watchlist;

public interface WatchlistService {
	List<Watchlist> getAllWatchlist();
	Long createWatchlist(String name);
}
