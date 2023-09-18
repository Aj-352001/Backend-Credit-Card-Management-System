package com.cardManagement.cardmanagementapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cardManagement.cardmanagementapp.entities.Statement;
import com.cardManagement.cardmanagementapp.exceptions.PaymentTransactionException;
import com.cardManagement.cardmanagementapp.exceptions.StatementException;
import com.cardManagement.cardmanagementapp.service.EmailService;
import com.cardManagement.cardmanagementapp.service.PdfGenerationService;
import com.cardManagement.cardmanagementapp.service.StatementService;

/******************************************************************************
 * @author          Hetal Parmar
 * Description      This class is responsible for managing user-related operations through RESTful endpoints. 
                    It appears to handle operations related to user statements and provides various endpoints to interact with user data in the system.
 * Endpoints:
 * GET /statement/{statementId}: This retrieves a statement by its ID.
 * POST /statement/{creditcardNumber}: This adds a new statement to a credit card identified by its number.
 * PUT /statement/{statementId}: This updates an existing statement with new information.
 * PATCH /statement/{statementId}/{amount}: This calculates and updates the interest for a statement based on a specified amount.
 * GET /statements/: This  retrieves a list of all statements.
 * POST /pdf: This generates a PDF document from a list of Statement objects.
 * POST /send: This  sends a PDF document by email to a specified recipient.
 
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@RestController
@RequestMapping("api/v1")
public class StatementController {
	
	@Autowired
	private StatementService statementservice;

	@Autowired
	private PdfGenerationService pdfGenerationService;

	@Autowired
	private EmailService emailservice;

	/******************************************************************************
     * Method                   -getstatementById
     * Description              -Get the statement id 
     * @param userDto           -The statement to be generated on basis of id.
     * Created by                Hetal Parmar
     * Created Date              12-Sept-2023 
     ******************************************************************************/ 
	@GetMapping("/statement/{statementId}")
	public Statement getStatementbystatementId(@PathVariable("statementId") Integer statementId)
			throws StatementException {
		return this.statementservice.getStatementbyStatementId(statementId);
	}

	/******************************************************************************
	 * Method                              - addStatement
	 * Description                         - Add a statement to CreditCard.
	 * @param creditcardNumber             - The unique identifier of the credit card for which the statement is being added.
	 * @return Statement                   - The newly created Statement object representing the added statement.
	 * @throws StatementException          - Raised if there's an issue with adding the statement.
	 * @throws PaymentTransactionException - Raised if there's an issue with the payment transaction.
	 * Created by                            Hetal Parmar
	 * Created Date                          12-Sept-2023
	 ******************************************************************************/
	@PostMapping("/statement/{creditcardNumber}")
	public Statement addStatement(@PathVariable Long creditcardNumber)
			throws StatementException, PaymentTransactionException {
		try {
			return this.statementservice.addStatement(creditcardNumber);

		} catch (StatementException e) {
			throw e;
		}
	}

	/******************************************************************************
	 * Method                     - updateStatement
	 * Description                - Update an existing statement with new information.
	 * @param statementId         - The unique identifier of the statement to be updated.
	 * @param newStatement        - The updated statement data as a request body.
	 * @return Statement          - The updated Statement object representing the statement after the update.
	 * @throws StatementException - Raised if there's an issue with updating the statement.
	 * Created by                   Hetal Parmar
	 * Created Date                 12-Sept-2023
     *************************************************************************/
	@PutMapping("/statement/{statementId}")
	public Statement updateStatement(@RequestBody Statement newStatement) throws StatementException {
		return this.statementservice.updateStatement(newStatement);
	}

	 /******************************************************************************
	  * Method                     - interestCalculation
	  * Description                - Calculate and update the interest for a statement based on a specified amount.
	  * @param statementId         - The unique identifier of the statement for which interest is calculated.
	  * @param amount              - The amount on which interest is to be calculated.
	  * @return Statement          - The updated Statement object after interest calculation.
	  * @throws StatementException - Raised if there's an issue with calculating and updating the interest.
	  * Created by                   Hetal Parmar
	  * Created Date                 12-Sept-2023 
    ******************************************************************************/
	@PatchMapping("/statement/{statementId}/{amount}")
	public Statement interestCalculation(@PathVariable Integer statementId, @PathVariable Double amount)
			throws StatementException {
		return this.statementservice.interestCalculation(statementId, amount);
	}

	/******************************************************************************
	 * Method                   - getStatements
	 * Description              - Retrieve a list of all statements.
	 * @return List<Statement>  - A list of Statement objects representing all statements.
	 * Created by                 Hetal Parmar
	 * Created Date               12-Sept-2023 
	 ******************************************************************************/ 
	@GetMapping("/statements/") 
	public List<Statement> getStatements() {
		List<Statement> statementList = this.statementservice.getAllStatements();
		return statementList;
	}
	
	/******************************************************************************
	 * Method                   - convertToJson
	 * Description              - Generate a PDF document from a list of Statement objects.
	 * @param statement         - A list of Statement objects to be converted to a PDF.
	 * @return String           - A success message indicating that the PDF was generated successfully.
	 * Created by                 Hetal Parmar
	 * Created Date               12-Sept-2023  
	 ******************************************************************************/  
	@PostMapping("/pdf")
	public String convertToJson(@RequestBody List<Statement> statement) {
		String outputPath = "statement.pdf"; 
		pdfGenerationService.generatePdf(statement, outputPath);
		return "PDF generated successfully!";
	}

	/******************************************************************************
	 * Method                   - sendPdfByEmail
	 * Description              - Send a PDF document by email to the specified recipient.
	 * @param statements        - A list of Statement objects to be included in the email.
	 * @param recepientEmail    - The email address of the recipient.
	 * @return ResponseEntity<String> - A response indicating the status of the email sending operation.
	 * Created by                 Hetal Parmar
	 * Created Date               12-Sept-2023   
	 ******************************************************************************/  
	@PostMapping("/email")
	public ResponseEntity<String> sendPdfByEmail(@RequestBody List<Statement> statements,
			@RequestParam String recepientEmail) {
		try {
			String outputPath = "/Users/hetalparmar1/Desktop/pdf/Hetal_Parmar.pdf";
			emailservice.sendPdfByEmail(statements, outputPath, recepientEmail);

			return ResponseEntity.ok("Email sent successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
		}
	}
}