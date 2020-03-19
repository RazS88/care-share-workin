package com.raz.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "text")
	private String text;

	@Column(name = "categoryNumber")
	private int categoryNumber;

	@Column(name = "rateStars")
	private int rateStars;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "buyProfile_id")
	private BuyProfile buyProfile;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<Review> categoryReviews;

	public Category() {
		categoryReviews = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getCategoryNumber() {
		return categoryNumber;
	}

	public void setCategoryNumber(int categoryNumber) {
		this.categoryNumber = categoryNumber;
	}

	public int getRateStars() {
		return rateStars;
	}

	public void setRateStars(int rateStars) {
		this.rateStars = rateStars;
	}

	public BuyProfile getBuyProfile() {
		return buyProfile;
	}

	@JsonIgnore
	public void setBuyProfile(BuyProfile buyProfile) {
		this.buyProfile = buyProfile;
	}

	@JsonIgnore
	public List<Review> getCategoryReviews() {
		return categoryReviews;
	}

	public void setCategoryReviews(List<Review> categoryReviews) {
		this.categoryReviews = categoryReviews;
	}

	public void addCategoryReview(Review rev) {
		rev.setCategory(this);
		categoryReviews.add(rev);
	}

}
