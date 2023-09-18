package com.cardManagement.cardmanagementapp.service;

import java.security.cert.PKIXRevocationChecker.Option;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.cardManagement.cardmanagementapp.dao.AppUserRepository;
import com.cardManagement.cardmanagementapp.dao.ApplicationRepository;
import com.cardManagement.cardmanagementapp.dao.CreditCardRepository;
import com.cardManagement.cardmanagementapp.dao.PaymentTransactionsRepository;
import com.cardManagement.cardmanagementapp.entities.AppUser;
import com.cardManagement.cardmanagementapp.entities.Application;
import com.cardManagement.cardmanagementapp.entities.BillingCycle;
import com.cardManagement.cardmanagementapp.entities.CardType;
import com.cardManagement.cardmanagementapp.entities.CreditCard;
import com.cardManagement.cardmanagementapp.entities.PaymentTransactions;
import com.cardManagement.cardmanagementapp.exceptions.AppUserException;
import com.cardManagement.cardmanagementapp.exceptions.ApplicationException;
import com.cardManagement.cardmanagementapp.exceptions.BillingCycleException;
import com.cardManagement.cardmanagementapp.exceptions.CreditCardException;

/******************************************************************************
 * @author           Anjali Kalange
 * Description       It is a Service class that provides services for credit cards.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@Service
public class CreditCardServiceImpl implements CreditCardService {

	@Autowired
	private CreditCardRepository creditcardRepo;

	@Autowired
	private ApplicationRepository appRepo;

	@Autowired
	private PaymentTransactionsRepository transactionRepo;
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AppUserRepository userRepo;

	@Autowired
	private BillingCycleService billcycleService;

	@Autowired
	private StatementService statementService;

	// generate card number
	Random random = new Random();

	public Long generatecardnumber() {

		String fix = "52427200";
		StringBuilder sb = new StringBuilder(8);
		for (int i = 0; i < 8; i++) {
			int randomDigit = random.nextInt(10);
			sb.append(randomDigit);

		}
		String ab = fix + sb;
		Long creditcardNumber = new Long(ab.toString());
		return creditcardNumber;
	}

	// generate issue date
	public LocalDate generateIssueDate(String status) {
		LocalDate issueDate = null;
		if (status == "APPROVED") {
			issueDate = LocalDate.now();
		}
		return issueDate;
	}

	// generate expire date
	public LocalDate generateExpiryDate() {
		LocalDate today = LocalDate.now();
		LocalDate expirydate = today.plusYears(5);
		return expirydate;
	}

	// generate limit
	public Double generateLimit(Double income) {
		Double limit = 0.15 * income;
		return limit;
	}
	
	//generate cardtype
	public CardType type(Double income) {
		CardType type = null;
		if (income < 25000)
			type = CardType.BASIC;
		if (income >= 250000 && income <= 500000)
			type = CardType.GOLD;
		if (income >= 500000)
			type = CardType.PLATINUM;
		return type;
	}

	// generate cvv
	public String generatecvv() {
		StringBuilder sb = new StringBuilder(3);
		for (int i = 0; i < 3; i++) {
			int randomDigit = random.nextInt(10);
			sb.append(randomDigit);
		}
		String cvv = new String(sb);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
		String cvvEncrypted = encoder.encode(cvv);
		return cvvEncrypted;
	}

	// generate pin
	public Integer generatepin() {
		StringBuilder sb = new StringBuilder(4);
		for (int i = 0; i < 4; i++) {
			int randomDigit = random.nextInt(10);
			sb.append(randomDigit);
		}
		Integer pin = new Integer(sb.toString());
		return pin;
	}

	// generate outstanding balance
	public Double generateOutstandingbalance() {
		Double outstandingbal = 0.0;
		List<PaymentTransactions> transactions = this.transactionRepo.findAll();
		for (PaymentTransactions paymentTransactions : transactions) {
			outstandingbal += paymentTransactions.getAmount();
		}
		return outstandingbal;
	}

	/******************************************************************************
     * Method                   -createcreditcard
     * Description              -generate credit card based on the applicationId and UserId.
     * @param ApplicationId	    -Id of application
     * @param userId		    -Id of user
     * @return Creditcard	    -The object of newly created credit card 
     * @throws ApplicationException -Raised if user does not exist or the application is not found.
     * Created by                Anjali Kalange
     * Created Date              12-Sept-2023 
     ******************************************************************************/
	@Override
	public CreditCard createCreditCard(Integer userId, Integer appId) throws ApplicationException {
		Optional<AppUser> user = this.userRepo.findById(userId);
		Optional<Application> appOpt = this.appRepo.findById(appId);
		if (!user.isPresent() || (appOpt.get().getStatus()== "APPROVED")) {
			throw new ApplicationException("INVALID ID");
		}
		String updatedStatus = "APPROVED";
		appOpt.get().setStatus(updatedStatus);
		Application updatedApplication = appOpt.get();
		this.appRepo.save(updatedApplication);
		
		CreditCard card = new CreditCard();
			Long cardNumber = generatecardnumber();
			card.setCreditCardNumber(cardNumber);

			LocalDate issueDate = generateIssueDate(updatedStatus);
			card.setIssueDate(issueDate);
			
			card.setName(appOpt.get().getName());

			card.setCardLender("HDFC");

			LocalDate expiryDate = generateExpiryDate();
			card.setExpiryDate(expiryDate);

			Double limit = generateLimit(appOpt.get().getIncome());
			card.setCardLimit(limit);

			String cvv = generatecvv();
			card.setCvv(cvv);

			Integer pin = generatepin();
			card.setPin(pin);

			card.setCreditScore(750);

			Double outbal = generateOutstandingbalance();
			card.setOutstandingBalance(outbal);

			card.setAvailableBalance(limit);

			card.setDueBalance(0.0);
			
			card.setType(type(appOpt.get().getIncome()));

			try {
				card.setBillCycle(billcycleService.createBillingCycle());
			} catch (BillingCycleException e) {
				e.printStackTrace();
			}
		this.creditcardRepo.save(card);
		AppUser updatedUser = user.get();
		updatedUser.getCards().add(card);
		this.userRepo.save(updatedUser);

		return card;
	}

	/******************************************************************************
     * Method                  		 -displayAllCards
     * Description              	 -gets all the credit cards.
     * @return Collection<creditcard>      -Retrieves Collection of credit cards present.
     * Created by                	  Anjali Kalange
     * Created Date             	  12-Sept-2023 
     ******************************************************************************/
	@Override
	public Collection<CreditCard> displayAllCards() {
		List<CreditCard> cards = this.creditcardRepo.findAll();
		for (CreditCard creditCard : cards) {
			System.out.println("credit card is " + cards);
		}
		return cards;
	}

	
	@Override
	public String billingCyclePresent(Long creditcardNumber) throws CreditCardException {
		Optional<CreditCard> card = this.creditcardRepo.findByCreditCardNumber(creditcardNumber);
		BillingCycle cardCycle = card.get().getBillCycle();
		if (cardCycle == null) {
			try {
				this.billcycleService.createBillingCycle();
			} catch (BillingCycleException e) {
				e.printStackTrace();
			}
		}
		return "billing cycle present for the card";
	}

	/******************************************************************************
     * Method                   	-getCreditCardByUserId
     * Description              	-get credit cards belonging to the particular user.
     * @param userId		 		-Id of user
     * @return List<creditcard>     -List of credit cards
     * @throws CreditCardException  -Raised if credit cards are not present.
     * Created by                	Anjali Kalange
     * Created Date              	12-Sept-2023 
     ******************************************************************************/
	@Override
	public List<CreditCard> getCreditCardByUserId(Integer userId) throws AppUserException {
		Optional<AppUser> userOpt = this.userRepo.findById(userId);
		if(!userOpt.isPresent()) {
			throw new AppUserException("User not present");
		}
		return userOpt.get().getCards();
	}

}
