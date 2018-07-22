package org.stock.api.rest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stock.api.user.entity.User;
import org.stock.api.validation.ValidateParam;
import org.stock.api.validation.ValidateParams;

@RestController
@RequestMapping("/api")
public class StockRestController {
	@GetMapping("/current")
	User getCurrent(@AuthenticationPrincipal final User user) {
		return user;
	}
	
	@ValidateParams(
			@ValidateParam(name = "sourceId", required=true)
	)
	@GetMapping("/validate")
	public String validate(@RequestParam(name="sourceId") String sourceID) {
		return "Valid Soruce ID";
	}
}
