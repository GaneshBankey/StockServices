package org.stock.api.user.crud.in.memory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.stock.api.user.crud.api.UserCrudService;
import org.stock.api.user.entity.User;

import static java.util.Optional.ofNullable;

@Service
public class InMemoryUsers implements UserCrudService {

	Map<String, User> users = new ConcurrentHashMap<>();
	
	@Override
	public User save(User user) {
		return users.put(user.getId(), user);
	}

	@Override
	public Optional<User> find(String id) {
		return ofNullable(users.get(id));
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return users
				.values()
				.stream()
				.filter(user -> user.getUsername().equals(username))
				.findFirst();
	}

}
