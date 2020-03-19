package com.raz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.raz.entity.BuyProfile;
import com.raz.entity.Client;
import com.raz.entity.Profile;
import com.raz.entity.Review;
import com.raz.entity.User;
import com.raz.repo.BuyProfileRepository;
import com.raz.repo.ProfileRepository;
import com.raz.repo.ReviewRepository;
import com.raz.repo.UserRepository;
import com.raz.service.ex.InvalidProfileUpdateException;
import com.raz.service.ex.InvalidUserUpdateException;
import com.raz.service.ex.ProfileNotExsistException;
import com.raz.service.ex.ReviewIsNotExistsException;
import com.raz.service.ex.SystemMulfunctionException;
import com.raz.service.ex.UserNotExisisteException;

@Service
@Scope("prototype")
public class ProfileServiceImpl implements ProfileService {

	private long userId;

	private long profileId;

	private UserRepository userRepo;

	private ReviewRepository reviewRepo;

	private BuyProfileRepository buyProfileRepo;

	private ProfileRepository profileRepo;

	@Autowired
	public ProfileServiceImpl(ReviewRepository reviewRepo, UserRepository userRepo, BuyProfileRepository buyProfileRepo,
			ProfileRepository profileRepo) {
		this.reviewRepo = reviewRepo;
		this.userRepo = userRepo;
		this.buyProfileRepo = buyProfileRepo;
		this.profileRepo = profileRepo;
	}

//	@Override
//	public BuyProfile purchase(long userId, boolean purchase,String lastName,String description,int totalStars, int phone,boolean purchase) {
//	
//	}

	// Review
	@Override
	public Review createReview(float stars, String comment) throws SystemMulfunctionException {
		Optional<User> userOpt = userRepo.findById(this.userId);
		System.out.println("1");
		if (userOpt.isPresent()) {
			System.out.println("2");
			Profile profile = (Profile) userOpt.get().getClient();
			System.out.println("3");
			Review review = new Review();
			
			review.setReview(stars, comment);
			
			review.setId(0);
			profile.addReview(review);
			review.setClientReview(profile);
			review.setProfile(profile);
			userRepo.save(userOpt.get());
			return reviewRepo.save(review);
		} else {
			throw new SystemMulfunctionException("can not create review");
		}
	}

	@Override
	public void removeReview(long id) throws ReviewIsNotExistsException {
		Optional<Review> reviewOpt = reviewRepo.findById(id);
		if (reviewOpt.isPresent()) {
			reviewRepo.deleteById(id);
			System.out.println("review remove");
		} else {
			throw new ReviewIsNotExistsException("review with this id not exisit");
		}

	}

	@Override
	public Review findReviewById(long id) {
		return reviewRepo.findById(id).orElse(null);
	}

	@Override
	public List<Review> findAllReviews() {
		return reviewRepo.findAll();
	}

	@Override
	public List<Review> findAllProfileReview() {
		return reviewRepo.findAllByProfileId(this.profileId);
	}

	@Override
	public List<Review> findAllByProfileReview(long profileId) {
		return reviewRepo.findAllByProfileId(profileId);
	}

	@Override
	public List<Review> findAllReviewByStars(int stars) {
		return reviewRepo.findAllByStars(stars);
	}

	// User

	@Override
	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public User getUserById(long userId) {
		return userRepo.findById(this.userId).orElse(null);
	}
	
	@Override
	public User updateUserById(User user) throws InvalidUserUpdateException, SystemMulfunctionException {
	User userById = getUserById(user.getId());
	if (userById!=null) {
	Client client = userById.getClient();
	if (client instanceof Profile) {
		Profile profile = (Profile)client;
		profile.setProfileImage(user.getProfileImage());
		profileRepo.save(profile);
		user.setClient(client);
		return userRepo.save(userById);
	}
	throw new InvalidUserUpdateException("can not update user");
	}
		throw new SystemMulfunctionException("there is problem to update user with profile");
	}


	// Profile
	@Override
	public Profile getProfileById(long profileId) throws UserNotExisisteException {
			return profileRepo.findById(this.profileId).orElse(null);

	}

	@Override
	public void setProfileId(long profileId) {
		this.profileId = profileId ;
	}

	@Override
	public void removeProfile(long profileId) throws ProfileNotExsistException {
		Optional<Profile> profileById = profileRepo.findById(profileId);
		if (profileById.isPresent()) {
			profileRepo.deleteById(profileId);
		}
		throw new ProfileNotExsistException("can not delete profile");
	}

	@Override
	public Profile updateProfile(Profile profile) throws InvalidProfileUpdateException, UserNotExisisteException {
		Profile profileById = getProfileById(profile.getId());
		if (profileById != null) {
			User byClient = userRepo.getByClient(profileById);
			byClient.setProfileImage(profile.getProfileImage());
			Profile profileSave = profileRepo.save(profile);
			byClient.setClient(profileSave);
			userRepo.save(byClient);
			return profileSave;
		}
		throw new InvalidProfileUpdateException("can not update Profile");
	}

	// BuyProfile
	@Override
	public BuyProfile purchase(long userId,  String lastName, String description, int totalStars,
			String phone) throws SystemMulfunctionException {
		Optional<User> userOpt = userRepo.findById(this.userId);
		if (userOpt.isPresent()) {
			Client client = userOpt.get().getClient();
			if (client instanceof Profile) {
				BuyProfile buyProfile = new BuyProfile();
				buyProfile.setId(0);
				buyProfileRepo.save(buyProfile);
				buyProfile.setId(userId);
				buyProfile.setName(((Profile) client).getName());
				buyProfile.setProfileImage(((Profile) client).getProfileImage());
				buyProfile.setDetails(lastName, description, totalStars, phone, true);
				buyProfileRepo.save(buyProfile);
				userOpt.get().setClient(buyProfile);
				userRepo.save(userOpt.get());
				profileRepo.delete((Profile)client);
				return buyProfile;

			} else {
				throw new SystemMulfunctionException("cant purchase BuyProfile");
			}

		} else {
			throw new SystemMulfunctionException("cant be BuyProfile");
		}

	}


}
