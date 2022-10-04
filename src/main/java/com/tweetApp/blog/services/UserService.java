package com.tweetApp.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tweetApp.blog.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto user) ;

	UserDto createUser(UserDto user) ;
	UserDto updateUser(UserDto user, Integer userID) ;
	UserDto getUserById(Integer userId) ;
	List<UserDto> getAllUsers() ;
	void deleteUser(Integer userId) ;

}
