package com.raz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.raz.entity.BuyProfile;
import com.raz.entity.Category;
import com.raz.entity.Review;
import com.raz.entity.User;
import com.raz.repo.CategoryRepository;
import com.raz.repo.ReviewRepository;
import com.raz.repo.UserRepository;
import com.raz.service.ex.CategoryIsNotExistsException;
import com.raz.service.ex.InvalidUpdateCategoryException;
import com.raz.service.ex.InvalidUpdateReviewException;
import com.raz.service.ex.ReviewIsNotExistsException;
import com.raz.service.ex.SystemMulfunctionException;

@Service
@Scope("prototype")
public class BuyProfileServiceImpl implements BuyProfileService {

	private long userId;

	private UserRepository userRepo;

	private ReviewRepository reviewRepo;

	private CategoryRepository categoryRepo;

	@Autowired
	public BuyProfileServiceImpl(UserRepository userRepo, ReviewRepository reviewRepo,
			CategoryRepository categoryRepo) {
		this.userRepo = userRepo;
		this.reviewRepo = reviewRepo;
		this.categoryRepo = categoryRepo;
	}


	@Override
	public void setBuyProfileId(long userId) {
		this.userId = userId;

	}

	@Override
	public User getUserById() {
		return userRepo.findById(this.userId).orElse(null);
	}

	@Override
	public Review getReviewById(long id) {
		return reviewRepo.findById(id).orElse(null);
	}
	

	
	@Override
	public Review createReview(Review review) throws SystemMulfunctionException {
		Optional<User> userOpt = userRepo.findById(userId);
		if (userOpt.isPresent()) {
			BuyProfile buyProfile = (BuyProfile) userOpt.get().getClient();
			buyProfile.addBuyProfileReview(review);
			return reviewRepo.save(review);
		} else {
			throw new SystemMulfunctionException("cant  create review");
		}
	}
	
	@Override
	public Review updateReview(Review review) throws InvalidUpdateReviewException {
		Optional<User> userOpt = userRepo.findById(userId);
		Optional<Review> reviewOpt = reviewRepo.findById(review.getId());
		if (reviewOpt.isPresent()) {
			Review currentReview = reviewOpt.get();
			if (currentReview.getId()==review.getId()&&currentReview.getBuyProfile()==review.getBuyProfile()) {
				review.setBuyProfile((BuyProfile)userOpt.get().getClient());
				reviewRepo.save(review);
			}
		}
		throw new InvalidUpdateReviewException("can not update review");
	}


	@Override
	public void removeReview(long id) throws ReviewIsNotExistsException {
		Optional<Review> reviewOpt = reviewRepo.findById(id);
		if (reviewOpt.isPresent()) {
			reviewRepo.deleteById(id);
		} else {
			throw new ReviewIsNotExistsException("cant remove review");
		}

	}

	@Override
	public List<Review> getAllBuyProfileReview() {
		return reviewRepo.findAllByBuyProfileId(userId);
	}

	@Override
	public List<Review> getAllByBuyProfileReview(long userId) {
		return reviewRepo.findAllByBuyProfileId(userId);
	}

	@Override
	public Category getCategoryById(long id) {
		return categoryRepo.findById(id).orElse(null);
	}

	@Override
	public Category createCategory(Category category) throws SystemMulfunctionException {
		Optional<User> userOpt = userRepo.findById(userId);
		if (userOpt.isPresent()) {
			BuyProfile buyProfile = (BuyProfile) userOpt.get().getClient();
			buyProfile.addBuyProfileCategory(category);
			return categoryRepo.save(category);
		} else {
			throw new SystemMulfunctionException("cant create category");
		}

	}

	@Override
	public void removeCategory(long id) throws CategoryIsNotExistsException {
		Optional<Category> categoryOpt = categoryRepo.findById(id);
		if (categoryOpt.isPresent()) {
			categoryRepo.deleteById(id);
			
		}else {
			throw new CategoryIsNotExistsException("cant remove category");
		}
	
	}

	@Override
	public Category updateCategory(Category category) throws InvalidUpdateCategoryException {
		Optional<User> userOpt = userRepo.findById(userId);
		Optional<Category> categoryOpt = categoryRepo.findById(category.getId());
		if (categoryOpt.isPresent()) {
			Category currentCategory = categoryOpt.get();
			if (currentCategory.getBuyProfile() == category.getBuyProfile()&&currentCategory.getCategoryNumber()== category.getCategoryNumber()) {
				category.setBuyProfile((BuyProfile)userOpt.get().getClient());
				return categoryRepo.save(category);
			}
		}
		throw new InvalidUpdateCategoryException("cant update category");
	}

	@Override
	public List<Category> getAllBuyProfileCategory() {
	return categoryRepo.findAllByBuyProfileId(userId);
	}

	@Override
	public List<Category> getAllBuyBuyProfileCategory(long userId) {
	return categoryRepo.findAllByBuyProfileId(userId);
	}


	

	

}
