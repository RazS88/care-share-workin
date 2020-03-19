package com.raz.rest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.raz.service.AdminService;
import com.raz.service.BuyProfileService;
import com.raz.service.ProfileService;

@Component
@Scope("prototype")
public class ClientSession {

	private long lastAccessedMills;
	
	private ProfileService profileService;
	
	private BuyProfileService buyProfileService;
	
	private AdminService adminService;
	
	private String role = "0";
	
	
	
	public ProfileService getProfileService() {
		return profileService;
	}

	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	public BuyProfileService getBuyProfileService() {
		return buyProfileService;
	}

	public void setBuyProfileService(BuyProfileService buyProfileService) {
		this.buyProfileService = buyProfileService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public long getLastAccessedMills() {
		return lastAccessedMills;
	}
	
	public void accessed() {
		this.lastAccessedMills = System.currentTimeMillis();
	}
}
