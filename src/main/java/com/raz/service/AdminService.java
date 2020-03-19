package com.raz.service;

import java.util.List;

import com.raz.entity.BuyProfile;
import com.raz.entity.Profile;
import com.raz.entity.User;
import com.raz.service.ex.InvalidUserUpdateException;
import com.raz.service.ex.SystemMulfunctionException;
import com.raz.service.ex.UserNotExisisteException;

public interface AdminService {

	// User

	User createUserProfile(String email, String password, String name, String profileImg);

	User createUserBuyProfile(String email, String password, String name, String profileImage, String lastName,
			String description, int totalStars, String phone, boolean purchase) throws SystemMulfunctionException;

	User getUserById(long id);

	public User updateUser(User user) throws InvalidUserUpdateException;

	void removeUser(long id) throws UserNotExisisteException;

	List<User> findAllUsers();

	List<User> getAllByRole(int role);

	// Profile

	Profile getUserProfileById(long id);

	Profile updateProfileUser(Profile profile) throws UserNotExisisteException;

	List<Profile> getAllProfiles();

	// BuyProfile

	BuyProfile getUserBuyProfileById(long id);

	BuyProfile updateBuyProfileUser(BuyProfile buyProfile) throws InvalidUserUpdateException;

	List<BuyProfile> getAllByProfiles();

}
