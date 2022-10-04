package com.tweetApp.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tweetApp.blog.payloads.UserDto;
import com.tweetApp.blog.repositories.*;
import com.tweetApp.blog.services.UserService;
import com.tweetApp.blog.configurations.AppConstants;
import com.tweetApp.blog.entities.*;
import com.tweetApp.blog.exceptions.*;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo ;
	
	@Autowired
	private ModelMapper modelMapper ;
	
	@Autowired
	private PasswordEncoder passwordEncoder ;
	
	@Autowired
	private RoleRepository roleRepo ;

	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto) ;
		User savedUser = this.userRepo.save(user) ;
		System.out.println("UserDto" + userDto) ;
		System.out.println("Saved User : " + savedUser) ;
		
		return this.userToDto(savedUser) ;
		
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id",userId)) ;
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepo.save(user) ;
		UserDto userDtoUpdated = this.userToDto(updatedUser) ;
		
		return userDtoUpdated;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id",userId)) ;
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll() ;
		
		List <UserDto> userDtos = users.stream()
				.map(user -> this.userToDto(user))
				.collect(Collectors.toList()) ;
				
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id",userId)) ;
		this.userRepo.delete(user);

	}
	
	private User dtoToUser(UserDto userDto) {
//		User user = new User() ;
//		user.setId(userDto.getId()) ;
//		user.setName(userDto.getName()) ;
//		user.setEmail(userDto.getEmail()) ;
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		User user = this.modelMapper.map(userDto, User.class) ;
		return user ;
	}
	
	
	
	public UserDto userToDto(User user)
	{
//		UserDto userDto = new UserDto() ;
//		userDto.setId(user.getId()) ;
//		userDto.setName(user.getName()) ;
//		userDto.setEmail(user.getEmail()) ;
//		userDto.setPassword(user.getPassword()) ;
//		userDto.setAbout(user.getAbout()) ;
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class) ;
		
		return userDto ;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class) ;
		
		// Encoded The Password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));;
		
		// Setting the role to the new user
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get() ;   // Normal User
		user.getRoles().add(role) ;
		
		User newUser = this.userRepo.save(user) ;

		return this.modelMapper.map(newUser, UserDto.class);
	}

}
