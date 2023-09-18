package com.cardManagement.cardmanagementapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.cardManagement.cardmanagementapp.entities.BillPayment;
import com.cardManagement.cardmanagementapp.exceptions.BillPaymentException;
import com.cardManagement.cardmanagementapp.service.BillPaymentsService;

/******************************************************************************
 * @author         Yash Tatiya
 * Description     BillPaymentsController is responsible for managing bill payment-related operations via RESTful endpoints.
 *                 It handles retrieving, adding, and listing bill payments.
 * Endpoints:
 * - GET /billPayment/{paymentId}: Retrieve a specific Bill Payment by its ID.
 * - POST /billPayment/{creditCardNumber}/{amount}: Add a new Bill Payment.
 * - GET /billPayments: Retrieve a list of all Bill Payments.
 * Version         1.0
 * Created Date    12-09-2023 
 ******************************************************************************/
@RestController
@RequestMapping("api/v1")
public class BillPaymentsController {

	@Autowired
	BillPaymentsService billPaymentService;

	/******************************************************************************
	 * Method                   - getBillPaymentById
	 * Description              - Retrieves a specific Bill Payment by its ID.
	 * @param paymentId         - The ID of the Bill Payment to retrieve.
	 * @return BillPayment     - The Bill Payment object corresponding to the given ID.
	 * @throws BillPaymentException - Thrown if there's an issue with retrieving the Bill Payment.
	 * Created by                [Yash Tatiya]
	 * Created Date              [12/09/2023]
	 ******************************************************************************/
	@GetMapping("/billPayment/{paymentId}")
	public BillPayment getBillPaymentById(@PathVariable("paymentId") Integer paymentId) throws BillPaymentException {
		try {
			return this.billPaymentService.getBillPaymentById(paymentId);
		} catch (BillPaymentException e) {
			throw e;
		}
	}
	/******************************************************************************
	 * Method                   - addBillPayment
	 * Description              - Adds a new Bill Payment for the provided credit card number and amount.
	 * @param creditCardNumber  - The credit card number for which the payment is made.
	 * @param amount            - The payment amount.
	 * @return BillPayment     - The newly created Bill Payment object representing the payment.
	 * @throws BillPaymentException - Thrown if there's an issue with adding the Bill Payment.
	 * Created by                [Yash Tatiya]
	 * Created Date              [12/09/2023]
	 ******************************************************************************/
	@PostMapping("/billPayment/{creditCardNumber}/{amount}")
	public BillPayment addBillPayment( @PathVariable Long creditCardNumber,@PathVariable Double amount) 
			throws BillPaymentException {
		try {
			return this.billPaymentService.addBillPayment(creditCardNumber,amount);

		} catch (BillPaymentException e) {
			throw e;
		}
	}
	
	/******************************************************************************
	 * Method                   - getBillPayments
	 * Description              - Retrieves a list of all Bill Payments.
	 * @return List<BillPayment> - A list containing all Bill Payment objects.
	 * Created by                [Yash Tatiya]
	 * Created Date              [12/09/2023]
	 ******************************************************************************/
	@GetMapping("/billPayments")
	@ResponseStatus(HttpStatus.OK)
	public List<BillPayment> getBillPayments() {
		List<BillPayment> billPaymentList = this.billPaymentService.getAllBillPayments();
		return billPaymentList;

	}
}
