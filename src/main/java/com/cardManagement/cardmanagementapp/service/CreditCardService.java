package com.cardManagement.cardmanagementapp.service;

import java.util.Collection;
import java.util.List;

import com.cardManagement.cardmanagementapp.entities.CreditCard;
import com.cardManagement.cardmanagementapp.exceptions.AppUserException;
import com.cardManagement.cardmanagementapp.exceptions.ApplicationException;
import com.cardManagement.cardmanagementapp.exceptions.CreditCardException;

public interface CreditCardService {

	CreditCard createCreditCard(Integer userId, Integer appId) throws ApplicationException;

	Collection<CreditCard> displayAllCards();

	String billingCyclePresent(Long creditcardNumber) throws CreditCardException;

	List<CreditCard> getCreditCardByUserId(Integer userId) throws AppUserException;
	
}
