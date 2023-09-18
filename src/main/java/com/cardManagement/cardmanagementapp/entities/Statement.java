package com.cardManagement.cardmanagementapp.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/******************************************************************************
 * @author           Hetal Parmar
 * Description       Statement is a JPA entity representing a CreditCard statement in the application.This class defines the structure and properties of a statement entity, including the statements  unique identifier (statementId),
                     generationDate , bill ,category  associated billPayments and PaymentTransactions.
                     -The @Entity annotation marks this class as a JPA entity, allowing it to be mapped to a relational database table.
                     -The @Id annotation indicates that the StatementId property is the primary key for the entity.
                     -The @GeneratedValue annotation specifies that the StatementId is generated automatically using an identity strategy.
                     -The @OneToMany annotations define one-to-many relationships with BillPayment and PaymentTransactions entities.
              
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@Entity
public class Statement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer statementId;
	private LocalDate generationDate;
	private Double bill;
	private TransactionCategory category;
	
	@OneToMany
	List<BillPayment> billPayments = new ArrayList<BillPayment>();
	@OneToMany
	List<PaymentTransactions> transaction = new ArrayList<PaymentTransactions>();
	
	/**
     * Default constructor for Statement
    */
	public Statement() {
		super();
	}

	/**
     * constructor for generating a statement
    */	
	public Statement(Integer statementId, LocalDate generationDate, Double bill, String transactionDetails,
			TransactionCategory category, List<BillPayment> billPayments, List<PaymentTransactions> transaction) {
		super();
		this.statementId = statementId;
		this.generationDate = generationDate;
		this.bill = bill;
		this.category = category;
		this.billPayments = billPayments;
		this.transaction = transaction;
	}



	public Integer getStatementId() {
		return statementId;
	}

	public void setStatementId(Integer statementId) {
		this.statementId = statementId;
	}

	public LocalDate getGenerationDate() {
		return generationDate;
	}

	public void setGenerationDate(LocalDate generationDate) {
		this.generationDate = generationDate;
	}

	public Double getBill() {
		return bill;
	}

	public void setBill(Double bill) {
		this.bill = bill;
	}

	
	public List<PaymentTransactions> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<PaymentTransactions> transaction) {
		this.transaction = transaction;
	}

	public TransactionCategory getCategory() {
		return category;
	}

	public void setCategory(TransactionCategory category) {
		this.category = category;
	}

	public List<BillPayment> getBillPayments() {
		return billPayments;
	}

	public void setBillPayments(List<BillPayment> billPayments) {
		this.billPayments = billPayments;
	}

	@Override
	public String toString() {
		return "Statement [statementId=" + statementId + ", generationDate=" + generationDate + ", bill=" + bill
				+ ", category=" + category + ", billPayments="
				+ billPayments + ", transaction=" + transaction + "]";
	}

}
