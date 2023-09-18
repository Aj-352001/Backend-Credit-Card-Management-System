package com.cardManagement.cardmanagementapp.service;

import java.time.LocalDate;

import com.cardManagement.cardmanagementapp.entities.BillingCycle;
import com.cardManagement.cardmanagementapp.exceptions.BillingCycleException;

public interface BillingCycleService {

    BillingCycle createBillingCycle() throws BillingCycleException;

    BillingCycle getBillingCycleById(Integer cycleId) throws BillingCycleException;

}
