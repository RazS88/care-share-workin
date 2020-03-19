package com.raz.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raz.entity.BuyProfile;
import com.raz.entity.Client;
import com.raz.entity.Profile;
import com.raz.entity.User;
import com.raz.rest.ex.InvalidLoginExeption;
import com.raz.service.AdminService;
import com.raz.service.BuyProfileService;
import com.raz.service.ProfileService;
import com.raz.service.UserService;

@Service
public class UserSystem {
	
	
	private ClientSession session;
	
	private UserService userService;
	
	private ProfileService profileService;
	
	private BuyProfileService buyProfileService;
	
	private AdminService adminService;

	
	@Autowired
	public UserSystem(ClientSession session, UserService userService, ProfileService profileService,
			BuyProfileService buyProfileService, AdminService adminService) {
		this.session = session;
		this.userService = userService;
		this.profileService = profileService;
		this.buyProfileService = buyProfileService;
		this.adminService = adminService;
	}
	
	public ClientSession login(String email,String password) throws InvalidLoginExeption {
		String adminEmail ="admin";
		
		String adminPassword = "1234";
		
		if (email.equals(adminEmail)&&password.equals(adminPassword)) {
			session.setAdminService(adminService);
			session.accessed();
			return session;
		}
		
		User user = userService.findUserByEmailAndPassword(email, password);
	if (user==null) {
		throw new InvalidLoginExeption("user name or password is not currect");
	}
	    Client clientType = user.getClient();
	    if (clientType instanceof Profile) {
			session.setProfileService(profileService);
			profileService.setProfileId(user.getId());
			session.accessed();
			session.setRole("1");
			return session;
		}else if (clientType instanceof BuyProfile) {
			session.setBuyProfileService(buyProfileService);
			buyProfileService.setBuyProfileId(user.getId());
			session.accessed();
			session.setRole("2");
			return session;
		}
	    return null;
	}
	
}
	
	


