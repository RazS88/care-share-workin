package com.raz.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raz.entity.Token;
import com.raz.rest.ClientSession;
import com.raz.rest.UserSystem;
import com.raz.rest.ex.InvalidLoginExeption;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class LoginController {
	
	private UserSystem userSystem;
	
	private Map<String,ClientSession>tokenMap;

	private static final int LENGTH_TOKEN=20;

	public LoginController(UserSystem userSystem,@Qualifier("tokens") Map<String, ClientSession> tokenMap) {
		this.userSystem = userSystem;
		this.tokenMap = tokenMap;
	}
	
	@PostMapping("/login")
	public ResponseEntity<Token>login(@RequestParam String email,@RequestParam String password) throws InvalidLoginExeption{
	
		ClientSession session = userSystem.login(email, password);
		String token = generateToken();
		tokenMap.put(token, session);
		System.out.println(token);
		return ResponseEntity.ok(new Token(token));
	}

	private String generateToken() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0,LENGTH_TOKEN);
	}
	
	

}
