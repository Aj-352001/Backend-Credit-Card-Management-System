package com.cardManagement.cardmanagementapp.entities;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/****************************************************************************
 * Author           - Yash Tatiya
 * Description      - BillPayment is a JPA entity representing a payment made by a user in the application.
 *                    This class defines the structure and properties of a payment entity, including the payment's unique identifier (paymentId),
 *                    bill amount, payment type, associated credit card number, and the amount paid.
 *                  -The @Entity annotation marks this class as a JPA entity, allowing it to be mapped to a relational database table.
                    -The @Id annotation indicates that the userId property is the primary key for the entity.
                    -The @GeneratedValue annotation specifies that the userId is generated automatically using an identity strategy.

 * Version           1.0
 * Created Date      12-Sept-2023 
 ****************************************************************************/
@Entity
public class BillPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer paymentId;
	private Double billAmount=0.0;
	private String paymentType;	
	private Long  creditCardNumber;
	
	private Double paidAmount = 0.0;
	
	public BillPayment() {
		super();
	}

	public BillPayment(Integer paymentId, Double billAmount, String paymentType, Long creditCardNumber,
			Double paidAmount) {
		super();
		this.paymentId = paymentId;
		this.billAmount = billAmount;
		this.paymentType = paymentType;
		this.creditCardNumber = creditCardNumber;
		this.paidAmount = paidAmount;
	}

	public BillPayment(String paymentType, Double paidAmount) {
		super();
		this.paymentType = paymentType;
		this.paidAmount = paidAmount;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Long getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(Long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	@Override
	public String toString() {
		return "BillPayment [paymentId=" + paymentId + ", billAmount=" + billAmount + ", paymentType=" + paymentType
				+ ", creditCardNumber=" + creditCardNumber + ", paidAmount=" + paidAmount + "]";
	}
	
	
	
	
	
	
		

}