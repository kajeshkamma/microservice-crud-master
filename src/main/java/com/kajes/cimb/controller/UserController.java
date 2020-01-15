package com.kajes.cimb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kajes.cimb.dto.UserRequest;
import com.kajes.cimb.dto.UserResponse;
import com.kajes.cimb.exception.UserNotFoundException;
import com.kajes.cimb.service.UsersService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/userinfo/api/v1")
@Api(value = "Users", description = "Users CRUD Operations")
public class UserController {

	@Autowired
	UsersService usersService;

	@GetMapping("/users")
	public UserResponse getAllUsers() {
		return usersService.getAllUsers();
	}

	@GetMapping("/users/{id}")
	public UserResponse getUserById(@PathVariable(value = "id") Long userId) throws UserNotFoundException {
		return usersService.getUserById(userId);
	}

	@PostMapping("/users")
	public UserResponse createUser(@RequestBody UserRequest userRequest) {
		return usersService.createUser(userRequest);
	}

	@DeleteMapping("/users/{id}")
	public UserResponse deleteUser(@PathVariable(value = "id") Long userId) throws UserNotFoundException {
		return usersService.deleteUser(userId);
	}

	@PutMapping("/users/{id}")
	public UserResponse updateUser(@PathVariable(value = "id") Long userId, @RequestBody UserRequest userRequest)
			throws UserNotFoundException {
		return usersService.updateUser(userId, userRequest);
	}
	
	@PatchMapping("/users/{id}")
	public UserResponse patchUser(@PathVariable(value = "id") Long userId, @RequestBody UserRequest userRequest)
			throws UserNotFoundException {
		return usersService.patchUser(userId, userRequest);
	}

}
