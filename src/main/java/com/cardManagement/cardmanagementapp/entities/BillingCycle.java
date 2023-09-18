package com.cardManagement.cardmanagementapp.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class BillingCycle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cycleID;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDate gracePeriod;

	public BillingCycle() {
		super();
	}

	public BillingCycle(Integer cycleID, LocalDate startDate, LocalDate endDate, LocalDate gracePeriod) {
		super();
		this.cycleID = cycleID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.gracePeriod = gracePeriod;
	}

	public Integer getCycleID() {
		return cycleID;
	}

	public void setCycleID(Integer cycleID) {
		this.cycleID = cycleID;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDate getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(LocalDate gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	@Override
	public String toString() {
		return "BillingCycle [cycleID=" + cycleID + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", gracePeriod=" + gracePeriod + "]";
	}


}
