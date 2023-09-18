package com.cardManagement.cardmanagementapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cardManagement.cardmanagementapp.entities.BillPayment;

public interface BillPaymentRepository extends JpaRepository<BillPayment, Integer> {
	
	//public BillPayment findByCreditCardNumber(Long creditCardNumber);
	List<BillPayment> findByCreditCardNumber(Long creditCardNumber);
}

