package com.cardManagement.cardmanagementapp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cardManagement.cardmanagementapp.dao.CreditCardRepository;
import com.cardManagement.cardmanagementapp.dao.PaymentTransactionsRepository;
import com.cardManagement.cardmanagementapp.entities.CreditCard;
import com.cardManagement.cardmanagementapp.entities.PaymentTransactions;
import com.cardManagement.cardmanagementapp.exceptions.PaymentTransactionException;

/******************************************************************************
 * @author           Ronit Patil
 * Description       It is a Service implementation class that provides services for 
 					 getting payment by ID,getting all transactions,adding transaction,
  					 getting transactions and amount based on credit card.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private PaymentTransactionsRepository transactionRepo;
	@Autowired
	private CreditCardRepository cardRepo;	
	
	/*********************************************************************************************************
     * Method                                -getPaymentTransactionByID
     * Description                           -Retrieve transaction based on transaction's ID
     * @param transactionID                  -Id of transaction 
     * @return Payment transactions          -Returns payment transaction object by ID
     * @throws AppUserException              -Raised if the transaction does not exist.
     * Created by                             Ronit Patil
     * Created Date                           12-Sept-2023 
     *********************************************************************************************************/ 
	@Override
	public PaymentTransactions getPaymentTransactionsByID(Integer transactionID) throws PaymentTransactionException {
	    //  validation
	    if (transactionID == null || transactionID <= 0) {
	        throw new PaymentTransactionException("Invalid transactionID");
	    }
	    try {
	        Optional<PaymentTransactions> transactionOpt = this.transactionRepo.findById(transactionID);        
	        if (!transactionOpt.isPresent()) {
	            throw new PaymentTransactionException("Transaction not found by ID: " + transactionID);
	        }
	        return transactionOpt.get();
	    } catch (PaymentTransactionException e) {       
	        throw new PaymentTransactionException("Error while retrieving payment transaction by ID: " + transactionID);
	    }
	}

	/*********************************************************************************************************
     * Method                                      -getAllPaymentTransactions
     * Description                                 -Retrieve all transactions made by customer
     * @return List<Payment transactions>          -Returns list of all the transactions made.
     * @throws PaymentTransactionException         -Raised if there is error retrieving transactions
     * Created by                                   Ronit Patil
     * Created Date                                 12-Sept-2023 
     *********************************************************************************************************/ 
	public List<PaymentTransactions> getAllPaymentTransactions() throws PaymentTransactionException {
	    try {
	        List<PaymentTransactions> transactions = this.transactionRepo.findAll();
	        return transactions;
	    } catch (Exception e) {
	        throw new PaymentTransactionException("Error while retrieving all payment transactions");
	    }
	}

	/*********************************************************************************************************
     * Method                                -addPaymentTransactions
     * Description                           -add transaction based on credit card number
     * @param creditcardNumber               -to record transaction based on credit card number
     * @param newPaymentTransaction          -Stores new payment transaction details.
     * @return Payment transactions          -The newly created payment transaction by user.
     * @throws PaymentTransactionException   -Raised if data is invalid,card is not present,transaction amount exceeds limit.
     * Created by                             Ronit Patil
     * Created Date                           12-Sept-2023 
     *********************************************************************************************************/
	@Override
	public PaymentTransactions addPaymentTransactions(PaymentTransactions newPaymentTransactions,Long creditcardNumber)throws PaymentTransactionException {
		if (newPaymentTransactions == null || creditcardNumber == null) {
	        throw new PaymentTransactionException("Invalid input data.");
	    }
		try {
		LocalDate timestamp = LocalDate.now();
		Double amount = newPaymentTransactions.getAmount();
		Optional<CreditCard> card = cardRepo.findByCreditCardNumber(creditcardNumber);
		if(!card.isPresent()) {
			throw new TransactionException("card selected is not present.");
		}
		Double availableLimit = card.get().getAvailableBalance();
		Double cardLimit = card.get().getCardLimit();
		Double outstandingBalance = card.get().getOutstandingBalance();
		if(amount <= cardLimit && amount <= availableLimit) {
			newPaymentTransactions.setTimeStamp(timestamp);
			this.transactionRepo.save(newPaymentTransactions);
		}
		else {
            throw new PaymentTransactionException("Transaction amount exceeds card limit or available balance.");
		}
		CreditCard updatedCard = card.get();
		updatedCard.getTransactions().add(newPaymentTransactions);
		availableLimit-=amount;
		outstandingBalance+=amount;
		updatedCard.setAvailableBalance(availableLimit);
		this.cardRepo.save(updatedCard);		
		return newPaymentTransactions;
	}
		catch(PaymentTransactionException e) {
	        throw new PaymentTransactionException("Error while processing payment transaction");
		}
	}

	/*********************************************************************************************************
     * Method                                      -getAllPaymentTransactionByCard
     * Description                                 -Retrieve all transactions made by particular credit card
     * @param creditcardNumber               	   -to retrieve transactions based on credit card number
     * @return List<Payment transactions>          -Returnslist of all the transactions made by particular card.
     * @throws PaymentTransactionException         -Raised if data is credit card number is invalid
     * Created by                                   Ronit Patil
     * Created Date                                 12-Sept-2023 
     *********************************************************************************************************/ 
	@Override
	public List<PaymentTransactions> getAllPaymentTransactionByCard(Long creditcardNumber)throws PaymentTransactionException {
		if (creditcardNumber == null || creditcardNumber <= 0) {
	        throw new PaymentTransactionException("Invalid credit card number");
	    }
		try {
		Optional<CreditCard> card =this.cardRepo.findByCreditCardNumber(creditcardNumber);
		if(!card.isPresent()) {
			throw new PaymentTransactionException("Credit card Id is invalid");
		}
		List<PaymentTransactions> payments = card.get().getTransactions();
		return payments;
		}
		catch(PaymentTransactionException e) {
	        throw new PaymentTransactionException("Error while retrieving payment transactions");
		}
	}

	/*********************************************************************************************************
     * Method                                      -resetOutstandingBalances 
     * Description                                 -to reset the outstanding balance after every month
     * @throws PaymentTransactionException         -Raised if outstanding balance cannot be set to 0.
     * Created by                                   Ronit Patil
     * Created Date                                 12-Sept-2023 
     *********************************************************************************************************/ 	
	@Scheduled(cron = "0 0 1 1 * ?") // Runs at midnight on the 1st day of every month
    public void resetOutstandingBalances() throws PaymentTransactionException{
        try {
            List<CreditCard> allCreditCards = cardRepo.findAll();
            for (CreditCard card : allCreditCards) {
                card.setOutstandingBalance(0.0);
            }
            cardRepo.saveAll(allCreditCards);
        } catch (Exception e) {
           throw new PaymentTransactionException("Cannot reset outstanding balance to zero.");
        }
    }
	
}
