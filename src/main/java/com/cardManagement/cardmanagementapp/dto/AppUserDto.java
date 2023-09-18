package com.cardManagement.cardmanagementapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/******************************************************************************
 * @author           Anushka Joshi
 * Description       AppUserDto is a Data Transfer Object (DTO) class used for transferring user-related data. 
 *                   This class represents the data structure for user-related information, including name, email, and password.
 *                   It is commonly used for transferring user data between different parts of the application, such as controllers and services.
                     Validation annotations such as @NotEmpty, @Email, and @Size are used to enforce data integrity and
                     ensure that the provided data meets certain criteria.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
public class AppUserDto {
	    
		private String name;
	    @NotEmpty(message = "Email should not be empty")
	    @Email
	    private String email;
	    @NotEmpty(message = "Password should not be empty")
	    @Size(min=8, max=15)
	    private String password;
	    
	    /**
	     * Default constructor for AppUserDto.
	     */
		public AppUserDto() {
			super();
		}
		
		/**
	     * Constructor for creating an AppUserDto with email and password.
	     *
	     * @param email    - The user's email address.
	     * @param password -The user's password.
	     */
		public AppUserDto(
				@NotEmpty(message = "Email should not be empty") @Email String email,
				@NotEmpty(message = "Password should not be empty") String password) {
			super();
			this.email = email;
			this.password = password;
		}
		
		/**
	     * Constructor for creating an AppUserDto with name, email, and password.
	     *
	     * @param name     -The user's name.
	     * @param email    -The user's email address.
	     * @param password -The user's password.
	     */
		public AppUserDto(String name, @NotEmpty(message = "Email should not be empty") @Email String email,
				@NotEmpty(message = "Password should not be empty") @Size(min = 8, max = 15) String password) {
			super();
			this.name = name;
			this.email = email;
			this.password = password;
		}
		
		// Getter and setter methods for the class properties
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
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	    
	}

