package com.cardManagement.cardmanagementapp.entities;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;


/****************************************************************************
 * Author           - Yash Tatiya
 * Description      - OrderRequest is a class representing a request to create an order in the application.
 *                    This class defines the structure and properties of an order request, including the unique identifier (id),
 *                    customer name, email, credit card number, and the order amount.
 *                  -The @Entity annotation marks this class as a JPA entity, allowing it to be mapped to a relational database table.
                    -The @Id annotation indicates that the userId property is the primary key for the entity.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ****************************************************************************/
public class OrderRequest {
	@Id
	private Integer id;
	private String customerName;
	private String email;
	@NotNull
	private Long creditCardNumber;
	@NotNull
	private Double amount;

	public OrderRequest() {
		super();
	}
	

	public OrderRequest(Integer id, String customerName, String email, @NotNull Long creditCardNumber,
			@NotNull Double amount) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.email = email;
		this.creditCardNumber = creditCardNumber;
		this.amount = amount;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(Long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		return "OrderRequest [id=" + id + ", customerName=" + customerName + ", email=" + email + ", creditCardNumber="
				+ creditCardNumber + ", amount=" + amount + "]";
	}
	
}

	
