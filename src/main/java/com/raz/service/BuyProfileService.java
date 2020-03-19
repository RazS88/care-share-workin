package com.raz.service;

import java.util.List;

import com.raz.entity.Category;
import com.raz.entity.Review;
import com.raz.entity.User;
import com.raz.service.ex.CategoryIsNotExistsException;
import com.raz.service.ex.InvalidUpdateCategoryException;
import com.raz.service.ex.InvalidUpdateReviewException;
import com.raz.service.ex.ReviewIsNotExistsException;
import com.raz.service.ex.SystemMulfunctionException;

public interface BuyProfileService {

	void setBuyProfileId(long userId);

	User getUserById();

	Review getReviewById(long id);

	Review createReview(Review review) throws SystemMulfunctionException;

	Review updateReview(Review review) throws InvalidUpdateReviewException;
	


	void removeReview(long id) throws ReviewIsNotExistsException;

	List<Review> getAllBuyProfileReview();

	List<Review> getAllByBuyProfileReview(long userId);

	Category getCategoryById(long id);

	Category createCategory(Category category) throws SystemMulfunctionException;

	void removeCategory(long id) throws CategoryIsNotExistsException;

	Category updateCategory(Category category) throws InvalidUpdateCategoryException;

	List<Category> getAllBuyProfileCategory();

	List<Category> getAllBuyBuyProfileCategory(long userId);
}
