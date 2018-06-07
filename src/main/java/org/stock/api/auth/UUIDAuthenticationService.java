package org.stock.api.auth;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stock.api.user.crud.api.UserCrudService;
import org.stock.api.user.entity.User;

@Service
public class UUIDAuthenticationService implements UserAuthenticationService {

	@Autowired
	UserCrudService users;
	
	@Override
	public Optional<String> login(String username, String password) {
		final String uuid = UUID.randomUUID().toString();
	    final User user = new User(uuid, username,password);
	    users.save(user);
	    return Optional.of(uuid);  
	}

	@Override
	public Optional<User> findByToken(String token) {
		return users.find(token);
	}

	@Override
	public void logout(User user) {
		// TODO Auto-generated method stub

	}

}
