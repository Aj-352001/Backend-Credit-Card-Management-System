package com.cardManagement.cardmanagementapp.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/******************************************************************************
 * @author           Anushka Joshi
 * Description       AppUser is a JPA entity representing a user in the application.This class defines the structure and properties of a user entity, including the user's unique identifier (userId),
                     password, email, name, associated applications, credit cards, and user role.
                     -The @Entity annotation marks this class as a JPA entity, allowing it to be mapped to a relational database table.
                     -The @Id annotation indicates that the userId property is the primary key for the entity.
                     -The @GeneratedValue annotation specifies that the userId is generated automatically using an identity strategy.
                     -The @OneToMany annotations define one-to-many relationships with Application and CreditCard entities,
                      indicating that a user can have multiple associated applications and credit cards.
                     -The userRole property represents the role of the user, typically defined as an enumeration (e.g., Role enum).
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@Entity
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String password;
	private String email;
	private String name;
	@OneToMany
	List<Application> application = new ArrayList<Application>();
	@OneToMany
	List<CreditCard> cards = new ArrayList<CreditCard>();
	private Role userRole;

	/**
     * Default constructor for AppUserDto.
    */
	public AppUser() {
		super();
	}

	/**
	 * Constructors for creating an AppUser with class properties.
	 */
	public AppUser(String email, String password) {
		super();
		this.password = password;
		this.email = email;
	}

	public AppUser(String password, String email, String name) {
		super();
		this.password = password;
		this.email = email;
		this.name = name;
	}

	public AppUser(String email, String password, Role userRole) {
		super();
		this.password = password;
		this.email = email;
		this.userRole = userRole;
	}

	public AppUser(Integer userId, String password, String email, List<Application> application, List<CreditCard> cards,
			Role userRole) {
		super();
		this.userId = userId;
		this.password = password;
		this.email = email;
		this.application = application;
		this.cards = cards;
		this.userRole = userRole;
	}

	// Getter and setter methods for the class properties
	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String userName) {
		this.name = userName;
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

	public List<Application> getApplication() {
		return application;
	}

	public void setApplication(List<Application> application) {
		this.application = application;
	}

	public List<CreditCard> getCards() {
		return cards;
	}

	public void setCards(List<CreditCard> cards) {
		this.cards = cards;
	}

	@Override
	public String toString() {
		return "AppUser [userId=" + userId + ", password=" + password + ", email=" + email + ", name=" + name
				+ ", application=" + application + ", cards=" + cards + ", userRole=" + userRole + "]";
	}

	

}