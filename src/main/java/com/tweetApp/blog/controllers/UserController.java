package com.tweetApp.blog.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetApp.blog.payloads.UserDto;
import com.tweetApp.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	// Post - Create User
	@Autowired
	private UserService userService ;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createUserDto = this.userService.createUser(userDto) ;
		return new ResponseEntity<>(createUserDto, HttpStatus.OK) ;
		
	}
	
	// PUT - Update User
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId )
	{
		UserDto updatedUser = this.userService.updateUser(userDto, userId) ;
		return ResponseEntity.ok(updatedUser) ;
	}
	
	
	//Can Be Called Only By Admin
	
	// DELETE - Delete User
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") Integer userId)
	{
		this.userService.deleteUser(userId) ;
	}
	
	// GET - USER GET
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers()) ;
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId)) ;
	}
	
	
	
	
	
	
	
	
	

}
