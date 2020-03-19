package com.raz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;
	
	@Column(name = "profileImage")
	private String profileImage;

	@Any(metaColumn = @Column(name = "role"))
	      @AnyMetaDef(idType = "long", metaType = "int", metaValues = { 
	    		  @MetaValue(value = "1", targetEntity = Profile.class),
			      @MetaValue(value = "2", targetEntity = BuyProfile.class) })
	@JoinColumn(name = "client_id")
	private Client client;

	public User() {

	}

	public User(@Qualifier Client client) {
		if (client instanceof Profile) {
			this.client = (Profile) client;
		} else {
			this.client = (BuyProfile) client;
		}
	}

	@Autowired
	public User(Profile profile) {
		this.client = profile;
		this.profileImage = this.getProfileImage();
	}

	@Autowired
	public User(BuyProfile buyProfile) {
		this.client = buyProfile;
		this.profileImage = this.getProfileImage();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	
	
}
