package org.stock.api.security.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Optional.ofNullable;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final String AUTh_TOKEN = "AuthToken=";
	private static final String AUTHORIZATION = "Authorization";
	 
	protected TokenAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		  final String param = ofNullable(request.getHeader(AUTHORIZATION))
			      .orElse(request.getParameter("t"));
		  
		  final String token = ofNullable(param)
			      .map(value -> removeStart(value, AUTh_TOKEN))
			      .map(String::trim)
			      .orElseThrow(() -> new BadCredentialsException("Missing Authentication Token"));

		  final Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
		  return getAuthenticationManager().authenticate(auth);
	}
	@Override
	protected void successfulAuthentication(
	    final HttpServletRequest request,
	    final HttpServletResponse response,
	    final FilterChain chain,
	    final Authentication authResult) throws IOException, ServletException {
	    super.successfulAuthentication(request, response, chain, authResult);
	    
	    chain.doFilter(request, response);
	}
	private String removeStart(String value, String bearer) {
		return value.replaceFirst(bearer, "");
	}
	
}
