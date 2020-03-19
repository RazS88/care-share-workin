package com.raz.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raz.service.ex.SystemMulfunctionException;

@Entity
@Table(name = "buyProfile")
public class BuyProfile extends Client {

	@Column(name = "name")
	private String name;

	@Column(name = "profileImage")
	private String profileImage;

	@Column(name = "purchase")
	private boolean purchase;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "description")
	private String description;

	@Column(name = "totalStars")
	private int totalStars;

	@Column(name = "phone")
	private int[] phone;

	@OneToMany(mappedBy = "buyProfile", cascade = CascadeType.ALL)
	List<Category> buyProfileCategory;

	@OneToMany(mappedBy = "buyProfile", cascade = CascadeType.ALL)
	private List<Review> buyProfleReviews;

	public BuyProfile() {
		buyProfileCategory = new ArrayList<>();
		buyProfleReviews = new ArrayList<>();
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

	public boolean isPurchase() {
		return purchase;
	}

	public void setPurchase(boolean purchase) {
		this.purchase = purchase;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTotalStars() {
		return totalStars;
	}

	public void setTotalStars(int totalStars) {
		this.totalStars = totalStars;
	}

	public int[] getPhone() {
		return phone;
	}

	public void setPhone(int[] phone) {
		this.phone = phone;
	}

	public List<Review> getBuyProfleReviews() {
		return buyProfleReviews;
	}

	@JsonIgnore
	public List<Category> getBuyProfileCategory() {
		return buyProfileCategory;
	}

	public void addBuyProfileReview(Review rev) {
		rev.setBuyProfile(this);
		buyProfleReviews.add(rev);

	}

	public void addBuyProfileCategory(Category cat) {
		cat.setBuyProfile(this);
		buyProfileCategory.add(cat);
	}

	public void setDetails(String lastName, String description, int totalStars, String phone, boolean purchase)
			throws SystemMulfunctionException {
		this.setLastName(lastName);
		this.setDescription(description);
		this.setTotalStars(totalStars);
		this.setPurchase(purchase);
		int[] intArrPhone = convertPhone(phone);
		this.setPhone(intArrPhone);
	}

	private int[] convertPhone(String phone) throws SystemMulfunctionException {
		if (phone.length() != 10) {
			throw new SystemMulfunctionException("phone must be 10 chars");
		}
		int[] phoneArr = new int[10];
		int i = 0;
		for (char numChar : phone.toCharArray()) {
			int num = Character.getNumericValue(numChar);
			if (num >= 0 && num <= 9) {
				phoneArr[i] = num;
				i++;
			} else {
				throw new SystemMulfunctionException("must be number");
			}
		}
		return phoneArr;
	}
}
