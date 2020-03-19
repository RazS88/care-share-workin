package com.raz.service;

import java.util.List;

import com.raz.entity.Category;
import com.raz.entity.Review;

public interface GuestService {
	
	Category getCategoryById(long id);

	List<Category>getAllCategories();
	
	Review getReviewById(long id);
	
	List<Review>getAllReview();
	
	
}
