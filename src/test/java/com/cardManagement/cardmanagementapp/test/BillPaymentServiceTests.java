package com.cardManagement.cardmanagementapp.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.cardManagement.cardmanagementapp.dao.BillPaymentRepository;
import com.cardManagement.cardmanagementapp.dao.CreditCardRepository;
import com.cardManagement.cardmanagementapp.dao.StatementRepository;
import com.cardManagement.cardmanagementapp.entities.BillPayment;
import com.cardManagement.cardmanagementapp.entities.BillingCycle;
import com.cardManagement.cardmanagementapp.entities.CreditCard;
import com.cardManagement.cardmanagementapp.entities.Statement;
import com.cardManagement.cardmanagementapp.exceptions.BillPaymentException;
import com.cardManagement.cardmanagementapp.service.BillPaymentsServiceImpl;

public class BillPaymentServiceTests {

	@Mock
	private CreditCardRepository cardRepo;

	@Mock
	private BillPaymentRepository billPaymentRepository;

	@Mock
	private StatementRepository statementRepo;
	
	@InjectMocks
	private BillPaymentsServiceImpl billPaymentService;

	@Test
	public void testAddBillPaymentWithinGracePeriod() throws BillPaymentException {
		CreditCard creditCard = new CreditCard();
		creditCard.setCreditCardNumber(1234567890L);
		creditCard.setAvailableBalance(1000.0);
		BillingCycle billCycle = new BillingCycle();
		billCycle.setGracePeriod(LocalDate.now().plusDays(30));
		creditCard.setBillCycle(billCycle);
		Statement statement = new Statement();
		statement.setBill(500.0);
		creditCard.getStatements().add(statement);

		when(cardRepo.findByCreditCardNumber(1234567890L)).thenReturn(Optional.of(creditCard));
		when(billPaymentRepository.save(any(BillPayment.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
		when(statementRepo.save(any(Statement.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
		
		BillPayment billPayment = billPaymentService.addBillPayment(1234567890L, 300.0);

        assertNotNull(billPayment);
        assertEquals("Online", billPayment.getPaymentType());
        assertEquals(1234567890L, billPayment.getCreditCardNumber().longValue());
        assertEquals(300.0, billPayment.getPaidAmount(), 0.001);
        assertEquals(200.0, billPayment.getBillAmount(), 0.001);

        assertEquals(1300.0, creditCard.getAvailableBalance(), 0.001);
        assertEquals(200.0, creditCard.getDueBalance(), 0.001);
        assertEquals(200.0, statement.getBill(), 0.001);

        verify(cardRepo, times(1)).save(creditCard);
        verify(billPaymentRepository, times(1)).save(billPayment);
        verify(statementRepo, times(1)).save(statement);
    
	}
	@Test
    public void testAddBillPaymentInvalidCard() throws BillPaymentException {
        when(cardRepo.findByCreditCardNumber(1234567890L)).thenReturn(Optional.empty());

        billPaymentService.addBillPayment(1234567890L, 300.0);
    }

	

}
