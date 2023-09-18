package com.cardManagement.cardmanagementapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cardManagement.cardmanagementapp.entities.CreditCard;
import com.cardManagement.cardmanagementapp.entities.PaymentTransactions;
import com.cardManagement.cardmanagementapp.exceptions.PaymentTransactionException;
import com.cardManagement.cardmanagementapp.service.TransactionService;
import org.springframework.http.HttpStatus;

/******************************************************************************
 * @author           Ronit Patil
 * Description       PaymentTransactionController is responsible for managing user-related transactions via RESTful endpoints.
                     It handles adding transactions,getting transactions by transactionID and credit card number.
                     It provides endpoints to interact with user's transactions in the system.
 * Endpoints:
 * - POST /transaction/{creditcardNUmber}:  Creates a new transaction.
 * - GET /transaction/{transactionId}: 		Retrieve user transaction by ID.
 * - GET /transactions : 					Retrieve all user transaction details
 * - GET /transactions/{creditcardNumber} : Retrieve all user transaction details
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class PaymentTransactionsController {
	
	@Autowired
	private TransactionService transactionService;

	/*********************************************************************************************************
     * Method                                -addPaymentTransactions
     * Description                           -add transaction based on credit card number
     * @param creditcardNumber               -to record transaction based on credit card number
     * @param newPaymentTransaction          -to store new payment transaction.
     * @return Payment transactions          -The newly created payment transaction by user.
     * @throws PaymentTransactionException   -Raised if data is invalid,card is not present,transaction amount exceeds limit.
     * Created by                             Ronit Patil
     * Created Date                           12-Sept-2023 
     *********************************************************************************************************/ 
	@PostMapping("/transaction/{creditcardNumber}")
	public PaymentTransactions addPaymentTransactions(@RequestBody PaymentTransactions newPaymentTransactions,@PathVariable Long creditcardNumber) throws PaymentTransactionException{
		try{
			return this.transactionService.addPaymentTransactions(newPaymentTransactions,creditcardNumber);
			
		} catch(PaymentTransactionException e) {
			throw e;
		}
	}
	
	/*********************************************************************************************************
     * Method                                -getPaymentTransactionsByID
     * Description                           -Retrieve transaction based on transaction's ID
     * @param transactionID                  -to store transaction based on a ID
     * @return Payment transactions          -Returns payment transactions by ID
     * @throws AppUserException              -Raised if the transaction does not exist.
     * Created by                             Ronit Patil
     * Created Date                           12-Sept-2023 
     *********************************************************************************************************/
	@GetMapping("/transaction/{transactionID}")
	public PaymentTransactions getPaymentTransactionById(@PathVariable("transactionID") Integer transactionID) throws PaymentTransactionException {
		try {
			return this.transactionService.getPaymentTransactionsByID(transactionID);
		} catch (PaymentTransactionException e) {
			throw e;
		}
	}
	
	/*********************************************************************************************************
     * Method                                      -getAllPaymentTransactions
     * Description                                 -Retrieve all transactions made till date
     * @return List<Payment transactions>          -Returns all the transactions made.
     * @throws PaymentTransactionException         -Raised if there is error retrieving transactions
     * Created by                                   Ronit Patil
     * Created Date                                 12-Sept-2023 
     *********************************************************************************************************/ 
	@GetMapping("/transactions/")
	@ResponseStatus(HttpStatus.OK)
	public List<PaymentTransactions> getAllPaymentTransactions() throws PaymentTransactionException{
		List<PaymentTransactions> paymentTransactionList;
		try {
			paymentTransactionList = this.transactionService.getAllPaymentTransactions();
			return paymentTransactionList;

		} catch (PaymentTransactionException e) {
			
			throw e;
		}
	}

	/*********************************************************************************************************
     * Method                                      -getAllPaymentTransactionByCard
     * Description                                 -Retrieve all transactions made by particular credit card
     * @param creditcardNumber               	   -to retrieve transactions based on credit card number
     * @return List<Payment transactions>          -Returns all the transactions made by particular card.
     * @throws PaymentTransactionException         -Raised if data is credit card number is invalid
     * Created by                                   Ronit Patil
     * Created Date                                 12-Sept-2023 
     *********************************************************************************************************/	
	@GetMapping("/transactions/{creditcardNumber}")
	public List<PaymentTransactions> getPaymentTransactionsByCard(@PathVariable Long creditcardNumber) throws PaymentTransactionException{
		List<PaymentTransactions> list = this.transactionService.getAllPaymentTransactionByCard(creditcardNumber);
		return list;
	}
}
