package com.cardManagement.cardmanagementapp.service;

import java.util.List;

import com.cardManagement.cardmanagementapp.entities.BillPayment;
import com.cardManagement.cardmanagementapp.exceptions.BillPaymentException;

public interface BillPaymentsService {

	BillPayment addBillPayment(Long creditCardNumber,Double amount) throws BillPaymentException;

	BillPayment getBillPaymentById(Integer Id) throws BillPaymentException;
	
	//Void generateBillingCycle() throws BillPaymentException;

	List<BillPayment> getAllBillPayments();

}
