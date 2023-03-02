package com.stg.tsm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * author: raghuram
 *
 */
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider  {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
		return checkPassword(userDetails, password);
	}

	public Authentication checkPassword(UserDetails userDetails, String rawPassword) {
		if(passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
			return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
		}else {
			throw new BadCredentialsException("Invalid Password");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}


}
