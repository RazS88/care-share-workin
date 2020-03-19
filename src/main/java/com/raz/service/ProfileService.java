package com.raz.service;

import java.util.List;

import com.raz.entity.BuyProfile;
import com.raz.entity.Profile;
import com.raz.entity.Review;
import com.raz.entity.User;
import com.raz.service.ex.InvalidProfileUpdateException;
import com.raz.service.ex.InvalidUserUpdateException;
import com.raz.service.ex.ProfileNotExsistException;
import com.raz.service.ex.ReviewIsNotExistsException;
import com.raz.service.ex.SystemMulfunctionException;
import com.raz.service.ex.UserNotExisisteException;

public interface ProfileService {

	//User
	void setUserId(long userId);

	User getUserById(long userId);
	
	User updateUserById(User user) throws InvalidUserUpdateException, SystemMulfunctionException;
	
	
	//Profile
	Profile getProfileById(long profileId) throws UserNotExisisteException;
	
	Profile updateProfile(Profile profile) throws InvalidProfileUpdateException, UserNotExisisteException;
	
	void setProfileId(long profileId);
	
	void removeProfile(long profileId) throws ProfileNotExsistException;
	
	//BuyProfile
	BuyProfile purchase(long userId, String lastName, String description, int totalStars, String phone) throws SystemMulfunctionException;

    //Review
	Review findReviewById(long id);

	Review createReview(float stars,String comment) throws SystemMulfunctionException;
	
	void removeReview(long id) throws ReviewIsNotExistsException;

	List<Review> findAllReviews();
	
	List<Review> findAllProfileReview();
	
	List<Review> findAllByProfileReview(long profileId);
	
	List<Review>findAllReviewByStars(int stars);


	
	





}
