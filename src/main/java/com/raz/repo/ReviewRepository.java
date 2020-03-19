package com.raz.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raz.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	//Review
	List<Review> findAll();
	
	List<Review>findAllByStars(int stars); 
	
	//Profile
	List<Review> findAllByProfileId(long userId);
	
	//Buy-Profile
	List<Review> findAllByBuyProfileId(long userId);
	
	//Category
//	List<Review>findAllReviewIAndCategory(long id, int category);
	
	
	
	
	
}
