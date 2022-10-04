package com.tweetApp.blog.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetApp.blog.entities.User;
import com.tweetApp.blog.payloads.JwtAuthRequest;
import com.tweetApp.blog.payloads.JwtAuthResponse;
import com.tweetApp.blog.payloads.UserDto;
import com.tweetApp.blog.repositories.UserRepository;
import com.tweetApp.blog.security.JwtTokenHelper;
import com.tweetApp.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
//@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenhelper ;
	
	@Autowired
	private UserDetailsService userDetailsService ;
	
	@Autowired
	private AuthenticationManager authenticationManager ;
	
	@Autowired
	private UserService userService ;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request
			) throws Exception{
		
		this.authenticate(request.getUsername(), request.getPassword()) ;
		
		UserDetails userDetail = this.userDetailsService.loadUserByUsername(request.getUsername()) ;
		
		String token = this.jwtTokenhelper.generateToken(userDetail) ;
		
		JwtAuthResponse response = new JwtAuthResponse() ;
		response.setToken(token) ;
		response.setUser(this.mapper.map((User)userDetail, UserDto.class)) ;
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK) ;
		
	}

	private void authenticate(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password) ;
		
		try {
			
		
		this.authenticationManager.authenticate(authenticationToken) ;
		
		}catch(DisabledException e) {
			throw new Exception("User is Disabled") ;
		}
		
	}
	
	
	// REGISTER NEW USER API
	@PostMapping("/register")
	public ResponseEntity<UserDto>registerUser(@Valid @RequestBody UserDto userDto){
		UserDto registerNewUser = this.userService.registerNewUser(userDto) ;
		return new ResponseEntity<UserDto>(registerNewUser, HttpStatus.CREATED) ;
	}
	
	// get loggedin user data
		@Autowired
		private UserRepository userRepo;
		@Autowired
		private ModelMapper mapper;

		@GetMapping("/current-user/")
		public ResponseEntity<UserDto> getUser(Principal principal) {
			User user = this.userRepo.findByEmail(principal.getName()).get();
			return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
		}

}
