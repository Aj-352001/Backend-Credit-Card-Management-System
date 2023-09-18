package com.cardManagement.cardmanagementapp.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cardManagement.cardmanagementapp.dao.AppUserRepository;
import com.cardManagement.cardmanagementapp.dao.ApplicationRepository;
import com.cardManagement.cardmanagementapp.dao.CreditCardRepository;
import com.cardManagement.cardmanagementapp.entities.AppUser;
import com.cardManagement.cardmanagementapp.entities.Application;
import com.cardManagement.cardmanagementapp.entities.BillingCycle;
import com.cardManagement.cardmanagementapp.entities.CreditCard;
import com.cardManagement.cardmanagementapp.exceptions.ApplicationException;
import com.cardManagement.cardmanagementapp.service.BillingCycleService;
import com.cardManagement.cardmanagementapp.service.CreditCardServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
@Transactional
@SpringBootTest
public class CreditcardServiceTests {
	
	@InjectMocks
    private CreditCardServiceImpl creditCardService;

    @Mock
    private AppUserRepository userRepo;

    @Mock
    private ApplicationRepository appRepo;

    @Mock
    private CreditCardRepository creditcardRepo;

    @Mock
    private BillingCycleService billcycleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCreditCard_Success() {
        Integer userId = 1;  
        Integer appId = 2;  
        CreditCard card = null;
        try {
            card = creditCardService.createCreditCard(userId, appId);
        } catch (ApplicationException e) {
        	e.printStackTrace();
        }
        assertNotNull(card);
        }

    @Test
    public void testCreateCreditCard_UserNotFound() {
        Integer userId = 1;
        when(userRepo.findById(userId)).thenReturn(Optional.empty());
        assertThrows(ApplicationException.class, () -> {
            creditCardService.createCreditCard(userId, 2);
        });
    }
    

    @Test
    public void testDisplayAllCards() {
        when(creditcardRepo.findAll()).thenReturn(new ArrayList<>());
        Collection<CreditCard> result = creditCardService.displayAllCards();
        assertNotNull(result);
    }
}
