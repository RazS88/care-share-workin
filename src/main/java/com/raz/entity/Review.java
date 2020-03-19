package com.raz.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "review")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	@Column(name = "stars")
	private float stars;

	@Column(name = "comment")
	private String comment;

	@Any(metaColumn = @Column(name = "role"))
    @AnyMetaDef(idType = "long", metaType = "int", metaValues = { 
  		  @MetaValue(value = "1", targetEntity = Profile.class),
		      @MetaValue(value = "2", targetEntity = BuyProfile.class) })
@JoinColumn(name = "client_id")
	private Client client;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "profile_id")
	private Profile profile;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "buyProfile_id")
	private BuyProfile buyProfile;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "category_id")
	private Category category;

	public Review() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getStars() {
		return stars;
	}

	public void setStars(float stars) {
		this.stars = stars;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Profile getProfile() {
		return profile;
	}
	
	public Client getClientReview() {
		return client;
	}
	
	public void setClientReview(Client client) {
		this.client = client;
	}

	@JsonIgnore
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public BuyProfile getBuyProfile() {
		return buyProfile;
	}

	@JsonIgnore
	public void setBuyProfile(BuyProfile buyProfile) {
		this.buyProfile = buyProfile;
	}

	public Category getCategory() {
		return category;
	}

	@JsonIgnore
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void setReview(float stars,String comment) {
		this.setStars(stars);
		this.setComment(comment);
	}

}
