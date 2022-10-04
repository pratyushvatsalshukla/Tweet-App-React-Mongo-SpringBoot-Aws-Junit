package com.tweetApp.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tweetApp.blog.entities.User;
import com.tweetApp.blog.exceptions.ResourceNotFoundException;
import com.tweetApp.blog.repositories.UserRepository;

@Service
public class CustomUserDetailService  implements UserDetailsService {

	@Autowired
	private UserRepository userRepo ;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// Loading user from database
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "Email "+username,0)) ;
		return user;
	}   //////// 9.00

}
