package com.cardManagement.cardmanagementapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardManagement.cardmanagementapp.dao.BillPaymentRepository;
import com.cardManagement.cardmanagementapp.dao.CreditCardRepository;
import com.cardManagement.cardmanagementapp.dao.StatementRepository;
import com.cardManagement.cardmanagementapp.entities.BillPayment;
import com.cardManagement.cardmanagementapp.entities.CreditCard;
import com.cardManagement.cardmanagementapp.entities.Statement;
import com.cardManagement.cardmanagementapp.exceptions.BillPaymentException;
/******************************************************************************
 * Author:           [Yash Tatiya]
 * Description:      BillPaymentsServiceImpl is a service class responsible for handling bill payment-related operations.
 *                  It provides methods to add bill payments, retrieve bill payments by ID, and get a list of all bill payments.
 * Version:          1.0
 * Created Date:     [12-sept-2023]
 ******************************************************************************/
@Service
public class BillPaymentsServiceImpl implements BillPaymentsService {

	@Autowired
	private BillPaymentRepository billPaymentRepository;
	@Autowired
	private CreditCardRepository cardRepo;
	@Autowired
	private StatementRepository statementRepo;

	/****************************************************************************
	 * Method                   - addBillPayment
	 * Description              - Registers a new bill payment based on the provided creditCardNumber and amount.
	 * @param creditCardNumber  - The credit card number to make the payment from.
	 * @param amount            - The amount to be paid.
	 * @return BillPayment      - The newly created BillPayment object representing the payment.
	 * @throws BillPaymentException - Raised if the credit card is not present or if the payment is not within the grace period.
	 * Updated by                [Yash Tatiya]
	 * Updated Date              [12/09/2023]
	 ****************************************************************************/
	@Override
	public BillPayment addBillPayment(Long creditCardNumber, Double amount) throws BillPaymentException {
		Optional<CreditCard> cardOpt = this.cardRepo.findByCreditCardNumber(creditCardNumber);

		if (!cardOpt.isPresent()) {
			throw new BillPaymentException("card selected not present.");
		}
		BillPayment newBillPayment = new BillPayment();
		CreditCard card = cardOpt.get();
		List<Statement> statements = card.getStatements();
		Statement statement = statements.get(statements.size() - 1);
		Double bill = statement.getBill();
		newBillPayment.setPaymentType("Online");
		newBillPayment.setCreditCardNumber(creditCardNumber);
		newBillPayment.setPaidAmount(amount);
		LocalDate currentDate = LocalDate.now();
		LocalDate gracePeriod = card.getBillCycle().getGracePeriod();

		if (currentDate.isBefore(gracePeriod)) {
			bill -= amount;
			newBillPayment.setBillAmount(bill);
			this.billPaymentRepository.save(newBillPayment);
			Double availablebalance = card.getAvailableBalance();
			availablebalance += amount;
			card.setAvailableBalance(availablebalance);
			card.setDueBalance(bill);
			this.cardRepo.save(card);
			statement.getBillPayments().add(newBillPayment);
			statement.setBill(bill);
			this.statementRepo.save(statement);
			return newBillPayment;
		} else {
			throw new BillPaymentException("Payment is not within the grace period.");
		}
	}
	/****************************************************************************
	 * Method                   - getBillPaymentById
	 * Description              - Retrieves a bill payment by its unique identifier.
	 * @param Id                - The unique identifier of the bill payment to retrieve.
	 * @return BillPayment      - The BillPayment object representing the payment.
	 * @throws BillPaymentException - Raised if no payment with the given identifier is found.
	 * Updated by                [Yash Tatiya]
	 * Updated Date              [12/09/2023]
	 ****************************************************************************/
	@Override
	public BillPayment getBillPaymentById(Integer Id) throws BillPaymentException {
		Optional<BillPayment> billOpt = this.billPaymentRepository.findById(Id);
		if (!billOpt.isPresent()) {
			throw new BillPaymentException("No payment made");
		}
		return billOpt.get();
	}
	/****************************************************************************
	 * Method                   - getAllBillPayments
	 * Description              - Retrieves a list of all bill payments.
	 * @return List<BillPayment> - A list of BillPayment objects representing the payments.
	 * Updated by                [Yash Tatiya]
	 * Updated Date              [12/09/2023]
	 ****************************************************************************/
	@Override
	public List<BillPayment> getAllBillPayments() {
		return this.billPaymentRepository.findAll();
	}

}
