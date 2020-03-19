package com.raz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.raz.entity.Category;
import com.raz.entity.Review;
import com.raz.repo.CategoryRepository;
import com.raz.repo.ReviewRepository;

@Service
@Scope("prototype")
public class GuestServiceImpl implements GuestService {

	private ReviewRepository reviewRepo;

	private CategoryRepository categoryRepo;

	@Autowired
	public GuestServiceImpl(ReviewRepository reviewRepo, CategoryRepository categoryRepo) {
		this.reviewRepo = reviewRepo;
		this.categoryRepo = categoryRepo;
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepo.findAll();
	}

	@Override
	public Category getCategoryById(long id) {
		return categoryRepo.findById(id).orElse(null);
	}

	@Override
	public List<Review> getAllReview() {
		return reviewRepo.findAll();
	}

	@Override
	public Review getReviewById(long id) {
		return reviewRepo.findById(id).orElse(null);
	}

}
