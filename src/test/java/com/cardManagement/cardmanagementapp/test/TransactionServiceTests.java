package com.cardManagement.cardmanagementapp.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cardManagement.cardmanagementapp.dao.CreditCardRepository;
import com.cardManagement.cardmanagementapp.dao.PaymentTransactionsRepository;
import com.cardManagement.cardmanagementapp.entities.CreditCard;
import com.cardManagement.cardmanagementapp.entities.PaymentTransactions;
import com.cardManagement.cardmanagementapp.exceptions.PaymentTransactionException;
import com.cardManagement.cardmanagementapp.service.TransactionService;

@SpringBootTest
public class TransactionServiceTests {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	PaymentTransactionsRepository transactionRepo;
	
	@Autowired
	CreditCardRepository cardRepo;
	
	@Test
    public void testGetPaymentTransactionsByID() throws PaymentTransactionException {
        PaymentTransactions sampleTransaction = new PaymentTransactions();
        sampleTransaction.setTransactionID(1); 
        transactionRepo.save(sampleTransaction);

        try {
            PaymentTransactions retrievedTransaction = transactionService.getPaymentTransactionsByID(1);
            assertNotNull(retrievedTransaction);
            assertEquals(1, retrievedTransaction.getTransactionID());
        } catch (PaymentTransactionException e) {
            throw new PaymentTransactionException("Invalid transaction Id");
        }
    }
	
	@Test
    public void testAddPaymentTransactions() {
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardNumber(1234567890L);
        creditCard.setAvailableBalance(1000.0); 
        creditCard.setCardLimit(1500.0);
        creditCard.setOutstandingBalance(0.0); 
        cardRepo.save(creditCard);
        PaymentTransactions newPaymentTransactions = new PaymentTransactions();
        newPaymentTransactions.setAmount(500.0);

        try {
            PaymentTransactions addedTransaction = transactionService.addPaymentTransactions(newPaymentTransactions, 1234567890L); // Replace with the card number
            assertNotNull(addedTransaction);
        } catch (PaymentTransactionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
	
	@Test
    public void testAddPaymentTransactionsCardNotFound() {
        PaymentTransactions newPaymentTransactions = new PaymentTransactions();
        newPaymentTransactions.setAmount(500.0); 
        try {
            PaymentTransactions addedTransaction = transactionService.addPaymentTransactions(newPaymentTransactions, 1234567890L); // Replace with a non-existent card number
            fail("Expected PaymentTransactionException, but no exception was thrown.");
        } catch (PaymentTransactionException e) {
            assertEquals("card selected is not present.", e.getMessage());
        }
	}

}