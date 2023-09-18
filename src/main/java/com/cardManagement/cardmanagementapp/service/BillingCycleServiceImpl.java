package com.cardManagement.cardmanagementapp.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardManagement.cardmanagementapp.dao.BillingCycleRepository;
import com.cardManagement.cardmanagementapp.dao.CreditCardRepository;
import com.cardManagement.cardmanagementapp.entities.BillingCycle;
import com.cardManagement.cardmanagementapp.entities.CreditCard;
import com.cardManagement.cardmanagementapp.exceptions.BillingCycleException;

/******************************************************************************
 * @author           Ronit Patil
 * Description       It is a Service implementation class that provides services for
 					 creating billing cycle and retrieving billing cycle.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@Service
public class BillingCycleServiceImpl implements BillingCycleService{

	@Autowired
	BillingCycleRepository billingCycleRepo;
	
	@Autowired
	CreditCardRepository creditCardRepo;
	
	@Autowired
	BillingCycleService billingCycleService;

	/******************************************************************************
     * Method                   -getBillingCycleById
     * Description              -Retrieves billing cycle by ID.
     * @param cycleId           -Id of cycle used to retrieve the billing cycle.
     * @return BillingCycle     -returns billing cycle of particular Id.
     * Created by                Ronit Patil
     * Created Date              12-Sept-2023 
     ******************************************************************************/
	@Override
	public BillingCycle getBillingCycleById(Integer cycleId) throws BillingCycleException {
	    if (cycleId == null || cycleId <= 0) {
	        throw new IllegalArgumentException("Invalid cycleId");
	    }
	    try {
	        return billingCycleRepo.findById(cycleId)
	                .orElseThrow(() -> new BillingCycleException("Billing cycle not found for ID: " + cycleId));
	    } catch (Exception e) {
	        throw new BillingCycleException("Error while retrieving billing cycle by ID: " + cycleId);
	    }
	}
	
	/******************************************************************************
     * Method                   -createBillingCycle
     * Description              -creates billing cycle for credit card.
     * @return BillingCycle     -Creates billing cycle for a particular credit card.
     * Created by                Ronit Patil
     * Created Date              12-Sept-2023 
     ******************************************************************************/
	@Override
	public BillingCycle createBillingCycle() throws BillingCycleException {
	    try {
	        BillingCycle billingCycle = new BillingCycle();
	        LocalDate currentDate = LocalDate.now();
	        LocalDate startDate = currentDate.withDayOfMonth(1);
	        LocalDate endDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
	        billingCycle.setStartDate(startDate);
	        billingCycle.setEndDate(endDate);
	        billingCycle.setGracePeriod(endDate.plusDays(10));
	        return billingCycleRepo.save(billingCycle);
	    }
	    catch (Exception e) {        
	        throw new BillingCycleException("Error while creating billing cycle");
	    }
	}

}
