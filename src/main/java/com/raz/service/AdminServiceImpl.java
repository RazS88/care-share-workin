package com.raz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.raz.entity.BuyProfile;
import com.raz.entity.Client;
import com.raz.entity.Profile;
import com.raz.entity.User;
import com.raz.repo.BuyProfileRepository;
import com.raz.repo.ProfileRepository;
import com.raz.repo.UserRepository;
import com.raz.service.ex.InvalidUserUpdateException;
import com.raz.service.ex.SystemMulfunctionException;
import com.raz.service.ex.UserNotExisisteException;

@Service
@Scope("prototype")
public class AdminServiceImpl implements AdminService {

	private UserRepository userRepo;

	private ProfileRepository profileRepo;

	private BuyProfileRepository buyProfileRepo;

	@Autowired
	public AdminServiceImpl(UserRepository userRepo, ProfileRepository profileRepo,
			BuyProfileRepository buyProfileRepo) {
		this.userRepo = userRepo;
		this.profileRepo = profileRepo;
		this.buyProfileRepo = buyProfileRepo;
	}

	@Override
	public User createUserProfile(String email, String password, String name, String profileImage) {
		Profile profile = new Profile();
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setProfileImage(profileImage);
		user.setId(0);
		profile.setName(name);
		profile.setProfileImage(profileImage);
		profile.setId(0);
		profileRepo.save(profile);
		user.setClient(profile);
		return userRepo.save(user);
	}

	@Override
	public User createUserBuyProfile(String email, String password, String name, String profileImage, String lastName,
			String description, int totalStars, String phone, boolean purchase) throws SystemMulfunctionException {
		BuyProfile buyProfile = new BuyProfile();
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setProfileImage(profileImage);
		user.setId(0);
		buyProfile.setName(name);
		buyProfile.setProfileImage(profileImage);
		buyProfile.setDetails(lastName, description, totalStars, phone, purchase);
		;
		buyProfile.setId(0);
		buyProfileRepo.save(buyProfile);
		user.setClient(buyProfile);
		return userRepo.save(user);
	}

	@Override
	public User updateUser(User user) throws InvalidUserUpdateException {
		User userById = getUserById(user.getId());
		if (userById != null) {
			Client client = userById.getClient();
			if (client instanceof Profile) {
				Profile profile = (Profile) client;
				profile.setProfileImage(user.getProfileImage());
				profileRepo.save(profile);
			}
			if (client instanceof BuyProfile) {
				BuyProfile buyProfile = (BuyProfile) client;
				buyProfile.setProfileImage(user.getProfileImage());
				buyProfileRepo.save(buyProfile);
			}

			user.setClient(client);
			return userRepo.save(user);
		}
		throw new InvalidUserUpdateException("can not update user");
	}

	@Override
	public void removeUser(long id) throws UserNotExisisteException {
		Optional<User> userOpt = userRepo.findById(id);
		if (userOpt.isPresent()) {
			if (userOpt.get().getClient() instanceof Profile) {
				profileRepo.deleteById(userOpt.get().getClient().getId());
			}
			if (userOpt.get().getClient() instanceof BuyProfile) {
				buyProfileRepo.deleteById(userOpt.get().getClient().getId());
			}

			userRepo.deleteById(id);

		}

	}

//	@Override
//	public User updateProfileUser(User user) throws InvalidUserUpdateException {
//		User userById = getUserById(user.getId());
//		if (userById != null) {
//			Client client = userById.getClient();
//			if (client instanceof Profile) {
//				Profile profile = (Profile) client;
//				profile.setProfileImage(user.getProfileImage());
//				profileRepo.save(profile);
//			}
//			if (client instanceof BuyProfile) {
//				BuyProfile buyProfile = (BuyProfile) client;
//				buyProfile.setProfileImage(user.getProfileImage());
//				buyProfileRepo.save(buyProfile);
//			}
//
//			user.setClient(client);
//			return userRepo.save(user);
//		}
//		throw new InvalidUserUpdateException("can not update user");
//	}

	@Override
	public Profile updateProfileUser(Profile profile) throws UserNotExisisteException {
		Profile userProfileById = getUserProfileById(profile.getId());
		if (userProfileById != null) {
			User byClient = userRepo.getByClient(userProfileById);
			byClient.setProfileImage(profile.getProfileImage());
			Profile profileSave = profileRepo.save(profile);
			byClient.setClient(profile);
			userRepo.save(byClient);
			return profileSave;
		}
		throw new UserNotExisisteException("can not Update Profile");
	}

	@Override
	public BuyProfile updateBuyProfileUser(BuyProfile buyProfile) throws InvalidUserUpdateException {
		BuyProfile buyProfileById = getUserBuyProfileById(buyProfile.getId());
		if (buyProfileById != null) {
			User byClient = userRepo.getByClient(buyProfileById);
			byClient.setProfileImage(buyProfile.getProfileImage());
			BuyProfile buyProfileSave = buyProfileRepo.save(buyProfile);
			byClient.setClient(buyProfile);
			userRepo.save(byClient);
			return buyProfileSave;
		}
		throw new InvalidUserUpdateException("can not update buy profile user");
	}

	@Override
	public List<User> findAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User getUserById(long id) {
		return userRepo.findById(id).orElse(null);
	}

	@Override
	public Profile getUserProfileById(long id) {
		return profileRepo.findById(id).orElse(null);
	}

	@Override
	public BuyProfile getUserBuyProfileById(long id) {
		return buyProfileRepo.findById(id).orElse(null);
	}

	@Override
	public List<User> getAllByRole(int role) {
		return userRepo.findAllByRole(role);
	}

	@Override
	public List<Profile> getAllProfiles() {
		return profileRepo.findAll();
	}

	@Override
	public List<BuyProfile> getAllByProfiles() {
		return buyProfileRepo.findAll();
	}

}
