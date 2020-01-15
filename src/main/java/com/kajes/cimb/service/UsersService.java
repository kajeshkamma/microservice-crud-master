package com.kajes.cimb.service;

import org.springframework.stereotype.Service;

import com.kajes.cimb.dto.UserRequest;
import com.kajes.cimb.dto.UserResponse;
import com.kajes.cimb.exception.UserNotFoundException;

@Service
public interface UsersService {

	UserResponse createUser(UserRequest userRequest);

	UserResponse deleteUser(Long userId) throws UserNotFoundException;

	UserResponse getUserById(Long userId) throws UserNotFoundException;

	UserResponse getAllUsers();

	UserResponse updateUser(Long userId, UserRequest userRequest) throws UserNotFoundException;

	UserResponse patchUser(Long userId, UserRequest userRequest) throws UserNotFoundException;

}
