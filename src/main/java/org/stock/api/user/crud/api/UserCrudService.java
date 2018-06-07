package org.stock.api.user.crud.api;

import java.util.Optional;

import org.stock.api.user.entity.User;

/**
 * 
 * @author Ganesh.Bankey
 *
 */
public interface UserCrudService {
	
	User save(User user);

	Optional<User> find(String id);

	Optional<User> findByUsername(String username);
}
