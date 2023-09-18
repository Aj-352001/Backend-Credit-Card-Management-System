package com.cardManagement.cardmanagementapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cardManagement.cardmanagementapp.dao.AppUserRepository;
import com.cardManagement.cardmanagementapp.dao.BillPaymentRepository;
import com.cardManagement.cardmanagementapp.dao.BillingCycleRepository;
import com.cardManagement.cardmanagementapp.dao.CreditCardRepository;
import com.cardManagement.cardmanagementapp.dao.PaymentTransactionsRepository;
import com.cardManagement.cardmanagementapp.dao.StatementRepository;
import com.cardManagement.cardmanagementapp.entities.BillPayment;
import com.cardManagement.cardmanagementapp.entities.BillingCycle;
import com.cardManagement.cardmanagementapp.entities.CreditCard;
import com.cardManagement.cardmanagementapp.entities.PaymentTransactions;
import com.cardManagement.cardmanagementapp.entities.Statement;
import com.cardManagement.cardmanagementapp.entities.TransactionCategory;
import com.cardManagement.cardmanagementapp.exceptions.PaymentTransactionException;
import com.cardManagement.cardmanagementapp.exceptions.StatementException;

import net.bytebuddy.asm.Advice.Local;

/******************************************************************************
 * @author           Hetal Parmar
 * Description       It is a Service class that provides services for retrieving , adding and updating a statement.
 * Version           1.0
 * Created Date      13-Sept-2023 
 ******************************************************************************/
@Service
public class StatementServiceImpl implements StatementService {

	@Autowired
	private StatementRepository statementRepository;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private BillPaymentsService billService;

	@Autowired
	private BillingCycleRepository billCycle;

	@Autowired
	private CreditCardRepository cardRepo;
	
	@Autowired
	private BillPaymentRepository billRepo;
	
	 /******************************************************************************
     * Method                      - transactions
     * Description                 - Retrieve a list of payment transactions for a given credit card.
     * @param creditcardNumber     - The credit card number for which transactions are retrieved.
     * @return List<PaymentTransactions> - A list of payment transactions for the credit card.
     * @throws StatementException  - Thrown if the provided credit card number is invalid or not found.
     * Created by                    Hetal Parmar
	 * Created Date                  13-Sept-2023 
     ******************************************************************************/   
	public List<PaymentTransactions> transactions(Long creditcardNumber) throws StatementException{
		Optional<CreditCard> card = this.cardRepo.findByCreditCardNumber(creditcardNumber);
		if(!card.isPresent()) {
			throw new StatementException("card selected is not present");
		}
		List<PaymentTransactions> transactions = new ArrayList<>();
		transactions.addAll(card.get().getTransactions());
		return transactions;
	}
	
	 /******************************************************************************
     * Method                    - bill
     * Description               - Calculate the total bill amount for a given credit card.
     * @param creditcardNumber   - The credit card number for which the bill is calculated.
     * @return Double            - The total bill amount.
     * @throws StatementException - Thrown if the provided credit card number is invalid or not found.
     * Created by                   Hetal Parmar
	 * Created Date                 13-Sept-2023
     *******************************************************************************/  
	public Double bill(Long creditcardNumber) throws StatementException {
		Optional<CreditCard> card = this.cardRepo.findByCreditCardNumber(creditcardNumber);
		if(!card.isPresent()) {
			throw new StatementException("card selected is not present");
		}
		
		Double totalBill = 0.0;
		List<PaymentTransactions> transactions = card.get().getTransactions();
		for (PaymentTransactions paymentTransactions : transactions) {
			totalBill+=paymentTransactions.getAmount();
		}
		return totalBill;
	}

	/******************************************************************************
	 * Method                   - addStatement
	 * Description              - Add a new statement for a specified credit card.
	 * @param creditcardNumber  - The credit card number for which the statement is being added.
	 * @return Statement        - The newly created Statement object representing the added statement.
	 * @throws StatementException - Thrown if the provided credit card number is invalid or not found.
	 * @throws PaymentTransactionException - Thrown if there's an issue with payment transactions.
	 * Created by                   Hetal Parmar
	 * Created Date                 13-Sept-2023
	 ******************************************************************************/  
	@Override
	public Statement addStatement(Long creditcardNumber) throws StatementException, PaymentTransactionException {
		Optional<CreditCard> card = this.cardRepo.findByCreditCardNumber(creditcardNumber);
		if(!card.isPresent()) {
			throw new StatementException("cardID is invalid");
		}
		Statement statement = new Statement();
		LocalDate currentDate = LocalDate.now();
		LocalDate startDate = currentDate.withDayOfMonth(1);
		LocalDate endDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
		statement.setGenerationDate(endDate);
		
		Double bill = bill(creditcardNumber);
		statement.setBill(bill);
		
		statement.setCategory(TransactionCategory.Online);
		statement.setBillPayments(null);
		List<PaymentTransactions> transactions = transactions(creditcardNumber);
		statement.setTransaction(transactions);
		this.statementRepository.save(statement);

		CreditCard updatedCard= card.get();
		updatedCard.getStatements().add(statement);
		this.cardRepo.save(updatedCard);
		
		return statement;

	}
	/******************************************************************************
	 * Method                   - updateStatement
	 * Description              - Update an existing statement with new information.
	 * @param newStatement      - The updated Statement object containing the new information.
	 * @return Statement        - The updated Statement object after the update.
	 * @throws StatementException - Thrown if the statement with the provided ID is not found.
	 * Created by                   Hetal Parmar
	 * Created Date                 13-Sept-2023
	 ********************************************************************************/ 
	@Override
	public Statement updateStatement(Statement newStatement) throws StatementException {

		Optional<Statement> statementopt = this.statementRepository.findById(newStatement.getStatementId());
		if (!statementopt.isPresent()) {
			throw new StatementException("Statement not found for this idd ");
		}
		return statementRepository.save(newStatement);
	}
	 /******************************************************************************
	 * Method                   - getStatementbystatementId
	 * Description              - Retrieve a statement by its unique identifier.
	 * @param statementId       - The unique identifier of the statement to be retrieved.
	 * @return Statement        - The Statement object identified by the provided statementId.
	 * @throws StatementException - Thrown if the statement with the given ID is not found.
	 * Created by                   Hetal Parmar
	 * Created Date                 13-Sept-2023
	 ******************************************************************************/ 
	@Override
	public Statement getStatementbyStatementId(Integer statementId) throws StatementException {

		Optional<Statement> statementopt = this.statementRepository.findById(statementId);
		if (!statementopt.isPresent()) {
			throw new StatementException("Statement not found for Id " + statementId);

		}
		return statementopt.get();

	}
	 /******************************************************************************
	 * Method                   - getAllStatements
	 * Description              - Retrieve a list of all available statements.
	 * @return List<Statement>  - A list of Statement objects representing all available statements.
	 * Created by                   Hetal Parmar
	 * Created Date                 13-Sept-2023
	 ******************************************************************************/
	@Override
	public List<Statement> getAllStatements() {
		return this.statementRepository.findAll();
	}
	 /******************************************************************************
     * Method                   - interestCalculation
     * Description              - Calculate and update the bill amount for a statement based on the transaction category.
     * @param statementId       - The unique identifier of the statement to be updated.
     * @param amount            - The amount used for interest calculation.
     * @return Statement        - The updated Statement object after interest calculation.
     * @throws StatementException - Thrown if the provided statement ID is invalid or not found.
     * Created by                   Hetal Parmar
	 * Created Date                 13-Sept-2023
     ******************************************************************************/   
	@Override
	public Statement interestCalculation(Integer statementId, Double amount) throws StatementException {
		Optional<Statement> statementopt = this.statementRepository.findById(statementId);
		TransactionCategory category = statementopt.get().getCategory();
		Double bill = statementopt.get().getBill();
		if (category == TransactionCategory.Cash_Withdrawal_ATM) {
			amount += 0.03 * amount;
			bill += amount;
		}
		statementopt.get().setBill(bill);
		return this.statementRepository.save(statementopt.get());

	}
	
	 /******************************************************************************
     * Method                        - getStatementsByCard
     * Description                   - Retrieve a list of statements associated with a given credit card.
     * @param creditcardNumber       - The credit card number for which statements are retrieved.
     * @return List<Statement>       - A list of statements associated with the credit card.
     * @throws StatementException    - Thrown if the provided credit card number is invalid or not found.
     * @throws PaymentTransactionException - Thrown if there's an issue with payment transactions.
     * Created by                   Hetal Parmar
	 * Created Date                 13-Sept-2023
     ******************************************************************************/  

	@Override
	public List<Statement> getStatementsByCard(Long creditcardNumber) throws StatementException, PaymentTransactionException {
		Optional<CreditCard> card = this.cardRepo.findByCreditCardNumber(creditcardNumber);
		if(!card.isPresent()) {
			throw new StatementException("Credit card number is invalid. Please enter again");
		}
		List<Statement> statements = card.get().getStatements();
		return statements;
	}

}
