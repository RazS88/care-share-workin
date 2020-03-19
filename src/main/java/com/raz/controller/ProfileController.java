package com.raz.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raz.controller.ex.InvalidFilterExeption;
import com.raz.controller.ex.InvalidListExeption;
import com.raz.entity.BuyProfile;
import com.raz.entity.Profile;
import com.raz.entity.Review;
import com.raz.entity.User;
import com.raz.rest.ClientSession;
import com.raz.service.ProfileService;
import com.raz.service.ex.InvalidProfileUpdateException;
import com.raz.service.ex.InvalidUserUpdateException;
import com.raz.service.ex.ProfileNotExsistException;
import com.raz.service.ex.ReviewIsNotExistsException;
import com.raz.service.ex.SystemMulfunctionException;
import com.raz.service.ex.UserNotExisisteException;

@RestController
@RequestMapping("/api")
public class ProfileController {

	private Map<String, ClientSession> tokenMap;

	private String token;

	public ProfileController(@Qualifier("tokens") Map<String, ClientSession> tokenMap) {
		this.tokenMap = tokenMap;
	}

	private ClientSession getSession(String token) {
		return tokenMap.get(token);
	}

	public Map<String, ClientSession> getTokenMap() {
		return tokenMap;
	}

	// Review

	@GetMapping("/review/{id}")
	public ResponseEntity<Review> getReviewById(@PathVariable long id) throws InvalidListExeption {
		ClientSession session = getSession(this.token);
		if (session != null) {
			ProfileService service = session.getProfileService();
			Review findReviewById = service.findReviewById(id);
			if (findReviewById != null) {
				return ResponseEntity.ok(findReviewById);
			}
		}
		throw new InvalidListExeption("System Malfanction no Reviews with this Id");
	}

	@GetMapping("/reviews/{token}")
	public ResponseEntity<Collection<Review>> getAllReviews(@PathVariable String token) throws InvalidListExeption {
		ClientSession session = getSession(token);
		this.token = token;
		if (session != null) {
			ProfileService service = session.getProfileService();
			List<Review> findAllReviews = service.findAllReviews();
			if (findAllReviews != null) {
				return ResponseEntity.ok(findAllReviews);
			}
		}
		throw new InvalidListExeption("System Malfanction no Users");
	}

	@GetMapping("/reviewsp")
	public ResponseEntity<List<Review>> getAllProfileReview() throws InvalidListExeption {
		ClientSession session = getSession(this.token);
		if (session != null) {
			ProfileService service = session.getProfileService();
			List<Review> allProfileReview = service.findAllProfileReview();
			if (allProfileReview != null) {
				return ResponseEntity.ok(allProfileReview);
			}
		}
		throw new InvalidListExeption("System Malfunction no reviews for Profile");

	}

	@PostMapping("/review")
	public ResponseEntity<Review> createReview(@RequestParam int stars, @RequestParam String comment)
			throws SystemMulfunctionException {
		ClientSession session = getSession(token);
		if (session != null) {
			ProfileService service = session.getProfileService();
			Review createReview = service.createReview(stars, comment);
			if (createReview != null) {
				return ResponseEntity.ok(createReview);
			}
		}
		throw new SystemMulfunctionException("can not create review");
	}

	@GetMapping("/reviewp/{stars}")
	public ResponseEntity<List<Review>> getAllReviewsByStars(@PathVariable int stars) throws InvalidFilterExeption  {
		ClientSession session = getSession(this.token);
		if (session != null) {
			ProfileService service = session.getProfileService();
			List<Review> findAllReviewByStars = service.findAllReviewByStars(stars);
			if (findAllReviewByStars != null) {
				return ResponseEntity.ok(findAllReviewByStars);
			}
		}
		throw new InvalidFilterExeption("there is no stars for review");
	}

	@DeleteMapping("/review")
	public ResponseEntity<Review> deleteReviewById(@PathVariable long id)
			throws ReviewIsNotExistsException, SystemMulfunctionException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			ProfileService service = session.getProfileService();
			Review findReviewById = service.findReviewById(id);
			if (findReviewById != null) {
				service.removeReview(id);
				return ResponseEntity.ok(findReviewById);
			}
		}
		throw new SystemMulfunctionException("can not remove review");
	}

	// User
	@GetMapping("/userp/{id}")
	public ResponseEntity<User> getUserById(@PathVariable long userId) throws SystemMulfunctionException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			ProfileService service = session.getProfileService();
			User userById = service.getUserById(userId);
			if (userById != null) {
				return ResponseEntity.ok(userById);
			}
		}
		throw new SystemMulfunctionException("can not find user in db");
	}

	@PutMapping("/userpu")
	public ResponseEntity<User> updateUser(@RequestBody User user)
			throws InvalidUserUpdateException, SystemMulfunctionException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			ProfileService service = session.getProfileService();
			User userById = service.getUserById(user.getId());
			if (userById != null) {
				user.setClient(userById.getClient());
				User updateUserById = service.updateUserById(user);
				return ResponseEntity.ok(updateUserById);
			}
		}
		throw new InvalidUserUpdateException("can not update user in profile");
	}

	// Profile
	@GetMapping("/profiles")
	public ResponseEntity<Profile> getProfileById(@PathVariable long profileId) throws UserNotExisisteException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			ProfileService service = session.getProfileService();
			Profile profileById = service.getProfileById(profileId);
			if (profileById != null) {
				return ResponseEntity.ok(profileById);
			}
		}
		throw new UserNotExisisteException("user profile not found in system");
	}

	@PutMapping("/profilesu")
	public ResponseEntity<Profile> updateProfileUser(@RequestBody Profile profile)
			throws InvalidUserUpdateException, InvalidProfileUpdateException, UserNotExisisteException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			ProfileService service = session.getProfileService();
			Profile profileById = service.getProfileById(profile.getId());
			if (profileById != null) {
				Profile updateProfile = service.updateProfile(profile);
				return ResponseEntity.ok(updateProfile);
			}
		}
		throw new InvalidUserUpdateException("can not update profile");
	}

	@DeleteMapping("/profiles/{id}")
	public ResponseEntity<Profile> deleteProfile(@PathVariable long id)
			throws SystemMulfunctionException, ProfileNotExsistException, UserNotExisisteException{
		ClientSession session = getSession(this.token);
		if (session != null) {
			ProfileService service = session.getProfileService();
			Profile profileById = service.getProfileById(id);
			if (profileById != null) {
				service.removeProfile(id);
				return ResponseEntity.ok(profileById);
			}
		}
		throw new SystemMulfunctionException("can not remove profile");
	}

	// BuyProfile
	@PostMapping("/nbuyprofile")
	public ResponseEntity<BuyProfile> purchaseBuyProfile(@RequestParam long userId, @RequestParam String lastName,
			@RequestParam String description, @RequestParam int totalStars, @RequestParam String phone)
			throws SystemMulfunctionException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			ProfileService service = session.getProfileService();
			User userById = service.getUserById(userId);
			if (userById != null) {
				BuyProfile purchase = service.purchase(userId, lastName, description, totalStars, phone);
				return ResponseEntity.ok(purchase);
			}
		}
		throw new SystemMulfunctionException("can not purchase");
	}

}
