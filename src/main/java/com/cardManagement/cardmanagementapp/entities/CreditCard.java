package com.cardManagement.cardmanagementapp.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/******************************************************************************
 * @author           Anjali Kalange
 * Description       CreditCard is a JPA entity representing a credit card assigned to the user by the application applied.This class defines the structure and properties of a credit card entity,which
   					 includes the creditcard's unique identifier, creditcardNumber, user's name, issue date of card, cardlender, expiryDate, cardlimit based on the income of the user, cvv encrypted, pin, 
   					 creditscore, outstanding balance of card, available balance, due balance, and card type.
                     -The @Entity annotation marks this class as a JPA entity, allowing it to be mapped to a relational database table.
                     -The @Id annotation indicates that the creditcardNumber property is the primary key for the entity.
                     -The @GeneratedValue annotation specifies that the creditcardNumber is generated automatically using an identity strategy.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@Entity
public class CreditCard {

	@Id
	private Long creditCardNumber;
	private String name;
	private LocalDate issueDate;
	private String cardLender;
	private LocalDate expiryDate;
	private Double cardLimit;
	private String cvv;
	private Integer pin;
	private Integer creditScore;
	private Double outstandingBalance;
	private Double availableBalance;
	private Double dueBalance;
	private CardType type;
	@OneToMany
	List<PaymentTransactions> transactions = new ArrayList<PaymentTransactions>();
	@OneToOne
	BillingCycle billCycle;
	@OneToMany
	List<Statement> statements = new ArrayList<Statement>();

	/**
	 * Default constructor
	 */
	public CreditCard() {
		super();
	}
	
	/**
	 * Constructors for creating an Application with class properties.
	 */
	public CreditCard(Long creditCardNumber, String name, LocalDate issueDate, String cardLender, LocalDate expiryDate,
			Double cardLimit, String cvv, Integer pin, Integer creditScore, Double outstandingBalance,
			Double availableBalance, Double dueBalance, CardType type, List<PaymentTransactions> transactions,
			BillingCycle billCycle, List<Statement> statements) {
		super();
		this.creditCardNumber = creditCardNumber;
		this.name = name;
		this.issueDate = issueDate;
		this.cardLender = cardLender;
		this.expiryDate = expiryDate;
		this.cardLimit = cardLimit;
		this.cvv = cvv;
		this.pin = pin;
		this.creditScore = creditScore;
		this.outstandingBalance = outstandingBalance;
		this.availableBalance = availableBalance;
		this.dueBalance = dueBalance;
		this.type = type;
		this.transactions = transactions;
		this.billCycle = billCycle;
		this.statements = statements;
	}

	//Getter and setter methods for class properties
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(Long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public String getCardLender() {
		return cardLender;
	}

	public void setCardLender(String cardLender) {
		this.cardLender = cardLender;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Double getCardLimit() {
		return cardLimit;
	}

	public void setCardLimit(Double cardLimit) {
		this.cardLimit = cardLimit;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public Integer getPin() {
		return pin;
	}

	public void setPin(Integer pin) {
		this.pin = pin;
	}

	public Integer getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
	}

	public Double getOutstandingBalance() {
		return outstandingBalance;
	}

	public void setOutstandingBalance(Double outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Double getDueBalance() {
		return dueBalance;
	}

	public void setDueBalance(Double dueBalance) {
		this.dueBalance = dueBalance;
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public List<PaymentTransactions> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<PaymentTransactions> transactions) {
		this.transactions = transactions;
	}

	public BillingCycle getBillCycle() {
		return billCycle;
	}

	public void setBillCycle(BillingCycle billCycle) {
		this.billCycle = billCycle;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	public void setStatements(List<Statement> statements) {
		this.statements = statements;
	}

}
