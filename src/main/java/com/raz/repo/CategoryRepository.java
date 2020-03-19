package com.raz.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raz.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	//Category
	List<Category>findAll();

	//BuyProfile
	List<Category>findAllByBuyProfileId(long userId);
	
	List<Category>findByBuyProfileIdAndRateStars(long userId,int rateStars);
	

//	List<Category> findByBuyProfleIdAndCategory( long userId ,  int category);
}
