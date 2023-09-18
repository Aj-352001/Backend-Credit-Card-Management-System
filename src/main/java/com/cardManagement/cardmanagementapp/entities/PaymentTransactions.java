package com.cardManagement.cardmanagementapp.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

/******************************************************************************
 * @author           Ronit Patil
 * Description       BillingCycle is a JPA entity representing billing cycle of the credit card in the application.
                     This class defines the structure and properties of billing cycle entity, including the billing cycle's unique identifier (cycleId),
                     startDate, endDate, gracePeriod.
                     -The @Entity annotation marks this class as a JPA entity, allowing it to be mapped to a relational database table.
                     -The @Id annotation indicates that the userId property is the primary key for the entity.
                     -The @GeneratedValue annotation specifies that the userId is generated automatically using an identity strategy.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@Entity
public class PaymentTransactions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer transactionID;
	private LocalDate timeStamp;
	@NotEmpty(message="Purchase information cannot be empty. Please enetr purchase information.")
	private String purchaseInformation;
//	@NotEmpty(message="Transaction amount cannot be empty. Please enter amount.")
	private Double amount=0.0;
	private Boolean status;
//	private Double credit;
//	private Double debit;
	//@NotEmpty(message="Category cannot be empty. Please mention transaction category ")
	TransactionCategory category;
	
	public PaymentTransactions() {
		super();
	}

	
	public PaymentTransactions(Integer transactionID, LocalDate timeStamp, String purchaseInformation, Double amount,
			 Boolean status, Double credit, Double debit, TransactionCategory category) {
		super();
		//remove transaction id and time stamp from constructor as we are not getting it from the user,we are auto generating it. 
		this.transactionID = transactionID;
		this.timeStamp = timeStamp;
		this.purchaseInformation = purchaseInformation;
		this.amount = amount;
		this.status = status;
//		this.credit = credit;
//		this.debit = debit;
		this.category = category;
	}
	


	public PaymentTransactions(String purchaseInformation, Double amount, Boolean status,
			TransactionCategory category) {
		super();
		this.purchaseInformation = purchaseInformation;
		this.amount = amount;
		this.status = status;
		this.category = category;
	}


	public Integer getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}

	public LocalDate getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDate timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getPurchaseInformation() {
		return purchaseInformation;
	}

	public void setPurchaseInformation(String purchaseInformation) {
		this.purchaseInformation = purchaseInformation;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

//	public Double getCredit() {
//		return credit;
//	}
//
//	public void setCredit(Double credit) {
//		this.credit = credit;
//	}
//
//	public Double getDebit() {
//		return debit;
//	}
//
//	public void setDebit(Double debit) {
//		this.debit = debit;
//	}

	public TransactionCategory getCategory() {
		return category;
	}

	public void setCategory(TransactionCategory category) {
		this.category = category;
	}


	@Override
	public String toString() {
		return "PaymentTransactions [transactionID=" + transactionID + ", timeStamp=" + timeStamp
				+ ", purchaseInformation=" + purchaseInformation + ", amount=" + amount + ", branchCode=" 
				+ ", status=" + status + ", credit=" + ", debit=" + ", category=" + category + "]";
	}

	
}

