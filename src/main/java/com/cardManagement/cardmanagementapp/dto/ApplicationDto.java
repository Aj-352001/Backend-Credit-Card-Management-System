package com.cardManagement.cardmanagementapp.dto;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.cardManagement.cardmanagementapp.constraints.BirthDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ApplicationDto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer applicationId;
	
	private String name;
	
	@Size(min=10, max=10, message = "Please enter valid panNumber.")	
	private String panNumber;
	
	@Size(min=12, max=12, message = "Please enter valid aadhar number.")
	private String aadharNumber;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@BirthDate(message = "The birth date must be greater or equal than 18")
	@Past(message = "The date of birth must be in the past.")
	private LocalDate dateOfBirth;
	
	@Min(value=20_000)
	private Double income;
	
	private String status;
	
	public ApplicationDto() {
		super();
		this.status="PROCESSING";
		// TODO Auto-generated constructor stub
	}

	public ApplicationDto(Integer applicationId, String name,
			@Size(min = 10, max = 10, message = "Please enter valid panNumber.") String panNumber,
			@Size(min = 12, max = 12, message = "Please enter valid aadhar number.") String aadharNumber,
			@Past(message = "The date of birth must be in the past.") LocalDate dateOfBirth, @Min(20000) Double income,
			String status) {
		super();
		this.applicationId = applicationId;
		this.name = name;
		this.panNumber = panNumber;
		this.aadharNumber = aadharNumber;
		this.dateOfBirth = dateOfBirth;
		this.income = income;
		this.status = status;
	}


	public ApplicationDto(String name,
			@Size(min = 10, max = 10, message = "Please enter valid panNumber.") String panNumber,
			@Size(min = 12, max = 12, message = "Please enter valid aadhar number.") String aadharNumber,
			@Past(message = "The date of birth must be in the past.") LocalDate dateOfBirth, @Min(20000) Double income,
			String status) {
		super();
		this.name = name;
		this.panNumber = panNumber;
		this.aadharNumber = aadharNumber;
		this.dateOfBirth = dateOfBirth;
		this.income = income;
		this.status = status;
	}

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ApplicationDto [applicationId=" + applicationId + ", name=" + name + ", panNumber=" + panNumber
				+ ", aadharNumber=" + aadharNumber + ", dateOfBirth=" + dateOfBirth + ", income=" + income + ", status="
				+ status + "]";
	}

	

}
