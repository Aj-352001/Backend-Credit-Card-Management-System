package com.cardManagement.cardmanagementapp.dto;

/******************************************************************************
 * @author           Anushka Joshi
 * Description       OtpDto is a Data Transfer Object (DTO) class used for transferring user-related data for email verification. 
 *                   This class represents the data structure for user-related information, including email, and otp.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
public class OtpDto {
	
	private String email;
	private Integer otp;
	
	/**
     * Default constructor for AppUserDto.
     */
	public OtpDto() {
		super();
	}
	
	/**
     * Constructor for creating an AppUserDto with email and otp.
     *
     * @param email    - The user's email address.
     * @param otp      -The user's otp ssent through email.
     */
	public OtpDto(String email, Integer otp) {
		super();
		this.email = email;
		this.otp = otp;
	}
	
	// Getter and setter methods for the class properties
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getOtp() {
		return otp;
	}
	public void setOtp(Integer otp) {
		this.otp = otp;
	}

}
