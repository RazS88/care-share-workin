package com.raz.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.raz.controller.AdminController;
import com.raz.controller.BuyProfileController;
import com.raz.controller.GuestController;
import com.raz.controller.LoginController;
import com.raz.controller.ProfileController;
import com.raz.controller.ex.InvalidFilterExeption;
import com.raz.controller.ex.InvalidListExeption;
import com.raz.rest.ex.InvalidLoginExeption;
import com.raz.service.ex.CategoryIsNotExistsException;
import com.raz.service.ex.InvalidUpdateCategoryException;
import com.raz.service.ex.InvalidUpdateReviewException;
import com.raz.service.ex.InvalidUserUpdateException;
import com.raz.service.ex.ReviewIsNotExistsException;
import com.raz.service.ex.SystemMulfunctionException;
import com.raz.service.ex.UserNotExisisteException;

@ControllerAdvice(assignableTypes = { LoginController.class, ProfileController.class, BuyProfileController.class,
		AdminController.class, GuestController.class })
public class UserRestException {

	//General & Login
	
	@ExceptionHandler(InvalidLoginExeption.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(InvalidLoginExeption ex) {
		return UserErrorResponse.now(HttpStatus.UNAUTHORIZED, String.format("unauthroized : %s", ex.getMessage()));
	}

	@ExceptionHandler(SystemMulfunctionException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(SystemMulfunctionException ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST, String.format("bad request: %s", ex.getMessage()));
	}

	// User

	@ExceptionHandler(UserNotExisisteException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(UserNotExisisteException ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST, String.format("bad request: %s", ex.getMessage()));
	}

	@ExceptionHandler(InvalidUserUpdateException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UserErrorResponse handleTaskMalform(InvalidUserUpdateException ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST, String.format("task malformed : %s", ex.getMessage()));
	}

	// Review

	@ExceptionHandler(InvalidUpdateReviewException.class)
	@ResponseStatus(value = HttpStatus.LOCKED)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(InvalidUpdateReviewException ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST,
				String.format("coupon is not exists: %s", ex.getMessage()));
	}

	@ExceptionHandler(ReviewIsNotExistsException.class)
	@ResponseStatus(value = HttpStatus.LOCKED)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(ReviewIsNotExistsException ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST,
				String.format("coupon is already purchased: %s", ex.getMessage()));
	}

	// Category

	@ExceptionHandler(CategoryIsNotExistsException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(CategoryIsNotExistsException ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST, String.format("bad request : %s", ex.getMessage()));
	}

	@ExceptionHandler(InvalidUpdateCategoryException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(InvalidUpdateCategoryException ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST, String.format("bad request: %s", ex.getMessage()));
	}
	
	@ExceptionHandler(InvalidListExeption.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(InvalidListExeption ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST, String.format("bad request: %s", ex.getMessage()));
	}
	
	@ExceptionHandler(InvalidFilterExeption.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(InvalidFilterExeption ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST, String.format("bad request: %s", ex.getMessage()));
	}

}
