package com.gp.electro.store.service;

import java.util.List;

import com.gp.electro.store.dtos.PageableResponse;
import com.gp.electro.store.dtos.UserDto;



public interface UserService {
	// create
	UserDto createUser(UserDto user);

	// update
	UserDto updateUser(UserDto userDto, String userId);

	// delete
	void deleteUser(String userId);

	// get all users
	PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortType);

	// gets single user by id
	UserDto getUserById(String UserId);

	// get user by email
	UserDto getuserByEmail(String userEmail);

	// search user
	List<UserDto> searchUsers(String keyword);
	
	List<String> allUserEmail();

}
