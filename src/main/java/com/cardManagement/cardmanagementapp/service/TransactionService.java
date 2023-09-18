package com.cardManagement.cardmanagementapp.service;

import java.util.List;

import com.cardManagement.cardmanagementapp.entities.PaymentTransactions;
//import com.cardManagement.cardmanagementapp.entities.TransactionsDto;
import com.cardManagement.cardmanagementapp.exceptions.PaymentTransactionException;

public interface TransactionService {

	PaymentTransactions addPaymentTransactions(PaymentTransactions newPaymentTransactions,Long creditcardNumber) throws PaymentTransactionException;
	
	PaymentTransactions getPaymentTransactionsByID(Integer transactionID) throws PaymentTransactionException;
	
	List<PaymentTransactions> getAllPaymentTransactions() throws PaymentTransactionException;
	
	//List<PaymentTransactions> getPaymentTransactionByCardId(Long cardId);
	
	//Double calculateTotalAmount(Double credit,Double debit) throws PaymentTransactionException;
	
	List<PaymentTransactions> getAllPaymentTransactionByCard(Long creditcardNumber) throws PaymentTransactionException;

	//PaymentTransactions addPaymentTransactions(TransactionsDto transactionDto, Integer creditcardNumber) throws PaymentTransactionException;
	Double getAllPaymentTransactionAmountByCard(Long creditcardNumber) throws PaymentTransactionException;

}
