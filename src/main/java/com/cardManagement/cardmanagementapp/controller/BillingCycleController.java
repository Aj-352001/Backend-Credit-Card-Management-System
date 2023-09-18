package com.cardManagement.cardmanagementapp.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardManagement.cardmanagementapp.entities.BillingCycle;
import com.cardManagement.cardmanagementapp.exceptions.BillingCycleException;
import com.cardManagement.cardmanagementapp.service.BillingCycleService;

/******************************************************************************
 * @author           Ronit Patil
 * Description       BillingCycleController is responsible for creating and displaying
  					 the billing cycle of a credit card
 * Endpoints:
 * - POST /billingcycle               : Create a new billing cycle
 * - GET /billingcycle/{cycleId}      : Get billing cycle details by id
 * Version              			  : 1.0
 * Created Date      			      :12-Sept-2023 
 ******************************************************************************/
@RestController
@RequestMapping("api/v1")
public class BillingCycleController {
	
	
	@Autowired
	private BillingCycleService billingCycleService;
	
	/******************************************************************************
     * Method                   -createBillingCycle
     * Description              -creates billing cycle for credit card.
     * @param startDate         -start date of the billing cycle.
     * @param endDate           -end date of the billing cycle.
     * @return BillingCycle     -Creates billing cycle for a particular credit card.
     * Created by                Ronit Patil
     * Created Date              12-Sept-2023 
     ******************************************************************************/
	@PostMapping("/billingcycle/")
    public ResponseEntity<String> createBillingCycle(LocalDate startDate, LocalDate endDate){
        try {
			billingCycleService.createBillingCycle();
		} catch (BillingCycleException e) {	
			e.printStackTrace();
		}
        return ResponseEntity.ok("Billing cycle generated successfully.");
    }
	
	/******************************************************************************
     * Method                   -getBillingCycleById
     * Description              -Retrieves billing cycle by ID.
     * @param cycleId           -cycle Id used to retrieve the billing cycle.
     * @return BillingCycle     -returns billing cycle of particular Id.
     * Created by                Ronit Patil
     * Created Date              12-Sept-2023 
     ******************************************************************************/
	@GetMapping("/billingcycle/{cycleId}")
    public BillingCycle getBillingCycleById(@PathVariable Integer cycleId) throws BillingCycleException{
        try {
			return billingCycleService.getBillingCycleById(cycleId);
		} 
        catch (BillingCycleException e) {	
			throw e;
		}
    }

}
