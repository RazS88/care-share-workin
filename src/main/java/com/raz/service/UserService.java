package com.raz.service;

import java.util.List;

import com.raz.entity.User;

public interface UserService {

	List<User> findAllUsers();
	
	User findUserByEmailAndPassword(String email, String password);
	
	
}
