package com.raz.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "profile")
public class Profile extends Client {

	@Column(name = "name")
	private String name;

	@Column(name = "profileImage")
	private String profileImage;

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	private List<Review> userReviews;

	public Profile() {
		userReviews = new ArrayList<>();
	}
	
	public void addReview(Review rev) {
		  rev.setProfile(this);
		  userReviews.add(rev);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	@JsonIgnore
	public List<Review> getUserReviews() {
		return userReviews;
	}

	public void setUserReviews(List<Review> userReviews) {
		this.userReviews = userReviews;
	}
	
	
}