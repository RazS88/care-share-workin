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

import com.raz.controller.ex.InvalidListExeption;
import com.raz.entity.BuyProfile;
import com.raz.entity.Client;
import com.raz.entity.Profile;
import com.raz.entity.User;
import com.raz.rest.ClientSession;
import com.raz.service.AdminService;
import com.raz.service.ex.InvalidUserUpdateException;
import com.raz.service.ex.SystemMulfunctionException;
import com.raz.service.ex.UserNotExisisteException;

@RestController
@RequestMapping("/api")
public class AdminController {

	private Map<String, ClientSession> tokenMap;

	private String token;

	public AdminController(@Qualifier("tokens") Map<String, ClientSession> tokenMap) {
		this.tokenMap = tokenMap;
	}

	private ClientSession getSession(String token) {
		return tokenMap.get(token);
	}

	public Map<String, ClientSession> getTokenMap() {
		return tokenMap;
	}

	// User

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable long id) throws UserNotExisisteException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			AdminService service = session.getAdminService();
			User userById = service.getUserById(id);
			if (userById != null) {
				return ResponseEntity.ok(userById);
			}
		}
		throw new UserNotExisisteException("user not exist in system");
	}

	@GetMapping("/users/{token}")
	public ResponseEntity<Collection<User>> getAllUsers(@PathVariable String token) throws InvalidListExeption {
		ClientSession session = getSession(token);
		this.token = token;
		if (session != null) {
			AdminService service = session.getAdminService();
			List<User> findAllUsers = service.findAllUsers();
			if (findAllUsers != null) {
				return ResponseEntity.ok(findAllUsers);
			}
		}
		throw new InvalidListExeption("System Malfanction Suppose Token Not Right For Admin");
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<Client> deleteUser(@PathVariable long id)
			throws SystemMulfunctionException, UserNotExisisteException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			AdminService service = session.getAdminService();
			User removeById = service.getUserById(id);
			if (removeById != null) {
				service.removeUser(id);
				return ResponseEntity.ok(removeById.getClient());
			}
		}
		throw new SystemMulfunctionException("can not delete user from db");
	}

	@PutMapping("/useru")
	public ResponseEntity<User> updateUser(@RequestBody User user) throws InvalidUserUpdateException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			AdminService service = session.getAdminService();
			User userById = service.getUserById(user.getId());
			if (userById != null) {
				user.setClient(userById.getClient());
				User updateUser = service.updateUser(user);
				System.out.println("1");
				return ResponseEntity.ok(updateUser);
			}
		}
		throw new InvalidUserUpdateException("can not update user");
	}

	// Profile

	@GetMapping("/profile/{token}")
	public ResponseEntity<List<Profile>> getAllProfileUsers(@PathVariable String token) throws InvalidListExeption {
		ClientSession session = getSession(token);
		this.token = token;
		if (session != null) {
			AdminService service = session.getAdminService();
			List<Profile> allByRoleProfile = service.getAllProfiles();
			if (allByRoleProfile != null) {
				return ResponseEntity.ok(allByRoleProfile);
			}
		}
		throw new InvalidListExeption("System Malfanction Suppose Token Not Right For Admin");
	}

	@PostMapping("/profile")
	public ResponseEntity<User> createUserProfile(@RequestParam String email, @RequestParam String password,
			@RequestParam String name, @RequestParam String profileImg) throws SystemMulfunctionException {

		ClientSession session = getSession(this.token);
		if (session != null) {
			AdminService service = session.getAdminService();
			User profile = service.createUserProfile(email, password, name, profileImg);
			if (profile != null) {
				return ResponseEntity.ok(profile);
			}
		}
		throw new SystemMulfunctionException("can not create profile");
	}

	@GetMapping("/oneprofile/{id}")
	public ResponseEntity<Profile> getUserProfileById(@PathVariable long id) throws UserNotExisisteException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			AdminService service = session.getAdminService();
			Profile userById = service.getUserProfileById(id);
			if (userById != null) {
				return ResponseEntity.ok(userById);
			}
		}
		throw new UserNotExisisteException("profile not exist in system");
	}

	@PutMapping("/profileu")
	public ResponseEntity<Profile> updateUserProfile(@RequestBody Profile profile)
			throws InvalidUserUpdateException, UserNotExisisteException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			AdminService service = session.getAdminService();
			Profile userById = service.getUserProfileById(profile.getId());
			if (userById != null) {
				Profile updateProfileUser = service.updateProfileUser(profile);
				return ResponseEntity.ok(updateProfileUser);
			}

		}
		throw new InvalidUserUpdateException("can not update user profile");
	}

	// Buy Profile

	@GetMapping("/buyProfile/{token}")
	public ResponseEntity<List<BuyProfile>> getAllBuyProfileUsers(@PathVariable String token)
			throws InvalidListExeption {
		ClientSession session = getSession(token);
		this.token = token;
		if (session != null) {
			AdminService service = session.getAdminService();
			List<BuyProfile> allByRoleBuyProfile = service.getAllByProfiles();
			if (allByRoleBuyProfile != null) {
				return ResponseEntity.ok(allByRoleBuyProfile);
			}
		}
		throw new InvalidListExeption("System Malfanction Suppose Token Not Right For  Admin ");
	}

	@GetMapping("/buyOneProfile/{id}")
	public ResponseEntity<BuyProfile> getUserBuyProfileById(@PathVariable long id) throws UserNotExisisteException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			AdminService service = session.getAdminService();
			BuyProfile userById = service.getUserBuyProfileById(id);
			if (userById != null) {
				return ResponseEntity.ok(userById);
			}
		}
		throw new UserNotExisisteException("profile not exist in system");
	}

	@PostMapping("/buyProfile")
	public ResponseEntity<User> createUserBuyProfile(@RequestParam String email, @RequestParam String password,
			@RequestParam String name, @RequestParam String profileImage, @RequestParam String lastName,
			@RequestParam String description, @RequestParam int totalStars, @RequestParam String phone,
			@RequestParam boolean purchase) throws SystemMulfunctionException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			AdminService service = session.getAdminService();
			User buyProfile = service.createUserBuyProfile(email, password, name, profileImage, lastName, description,
					totalStars, phone, true);
			if (buyProfile != null) {
				return ResponseEntity.ok(buyProfile);
			}
		}
		throw new SystemMulfunctionException("can not create buy profile");
	}

	@PutMapping("/buyProfileu")
	public ResponseEntity<BuyProfile> updateUserBuyProfile(@RequestBody BuyProfile buyProfile)
			throws InvalidUserUpdateException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			AdminService service = session.getAdminService();
			BuyProfile buyProfileById = service.getUserBuyProfileById(buyProfile.getId());
			if (buyProfileById != null) {
				BuyProfile updateBuyProfileUser = service.updateBuyProfileUser(buyProfile);
				return ResponseEntity.ok(updateBuyProfileUser);
			}
		}
		throw new InvalidUserUpdateException("can not update buy profile");
	}

}
