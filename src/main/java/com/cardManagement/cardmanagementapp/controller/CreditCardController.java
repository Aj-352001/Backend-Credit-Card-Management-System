package com.cardManagement.cardmanagementapp.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardManagement.cardmanagementapp.entities.CreditCard;
import com.cardManagement.cardmanagementapp.exceptions.AppUserException;
import com.cardManagement.cardmanagementapp.exceptions.ApplicationException;
import com.cardManagement.cardmanagementapp.exceptions.CreditCardException;
import com.cardManagement.cardmanagementapp.service.CreditCardService;

/******************************************************************************
 * @author           Anjali Kalange
 * Description       CreditCardController is responsible for managing creditcard-related operations via RESTful endpoints.
                     It handles credit card of users.
                     It provides endpoints to interact with user credit card data in the system.
 * Endpoints:
 * - POST /creditcard/{userId}/{appId}: Assign a new creditcard for application submitted by the user.
 * - GET /cards/{userId}	: Retrieve cards for a user by userId.
 * - GET /cards 			: Retrieve all cards present in thge repository.
 
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1")
public class CreditCardController {

	@Autowired
	private CreditCardService creditcardService;

	/******************************************************************************
     * Method                   -createCreditCard
     * Description              -post credit based on the applicationId and UserId.
     * @param applicationId	    -Id of application
     * @param userId		    -Id of user
     * @return Creditcard	    -The object of newly created credit card.
     * @throws ApplicationException -Raised if user does not exist or the submitted application is not found.
     * Created by                Anjali Kalange
     * Created Date              12-Sept-2023 
     ******************************************************************************/
	@PostMapping("/creditcard/{userId}/{appId}")
	public CreditCard createCreditCard(@PathVariable Integer userId,@PathVariable Integer appId) throws ApplicationException {
		return this.creditcardService.createCreditCard(userId,appId);
	}

	 /******************************************************************************
     * Method                  		    -displayAllCards
     * Description              	    -get all the credit cards.
     * @return Collection<CreditCard>   -Retrieves collection of credit cards present.
     * Created by                	     Anjali Kalange
     * Created Date             	     12-Sept-2023 
	******************************************************************************/

	@GetMapping("/cards/")
	public Collection<CreditCard> displayAllcards(){
		return this.creditcardService.displayAllCards();
	}
	/******************************************************************************
     * Method                   	-getCreditCardByUserId
     * Description              	-get credit cards for particular UserId.
     * @param userId		 		-The id of user
     * @return List<creditcard>     -List of credit cards belonging to that user.
     * @throws AppUserException     -Raised if user is not present.
     * Created by                	Anjali Kalange
     * Created Date              	12-Sept-2023 
     ******************************************************************************/
	@GetMapping("/cards/{userId}")
	public List<CreditCard> getCreditCardByUserId(@PathVariable Integer userId) throws AppUserException{
		return this.creditcardService.getCreditCardByUserId(userId);
	}

}
