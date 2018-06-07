package org.stock.api.rest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stock.api.user.entity.User;

@RestController
@RequestMapping("/api")
public class StockRestController {
	@GetMapping("/current")
	 User getCurrent(@AuthenticationPrincipal final User user) {
	 return user;
	}
}
