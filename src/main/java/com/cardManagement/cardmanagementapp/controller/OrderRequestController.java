package com.cardManagement.cardmanagementapp.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cardManagement.cardmanagementapp.entities.OrderRequest;
import com.cardManagement.cardmanagementapp.entities.OrderResponse;
import com.cardManagement.cardmanagementapp.service.OrderRequestService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;


/******************************************************************************
 * @author         Yash Tatiya
 * Description     OrderRequestController is responsible for managing order-related operations via RESTful endpoints.
 *                 It handles the creation of new orders for payment.
 * Endpoints:
 * - POST /order: Create a new order for payment.
 * Version         1.0
 * Created Date    12-09-2023 
 ******************************************************************************/
@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderRequestController {
	
	@Autowired
	private OrderRequestService orderRequestService;
	
	/******************************************************************************
	 * Method                   - createOrder
	 * Description              - Creates a new order for payment based on the provided OrderRequest.
	 * @param orderRequest      - The OrderRequest containing order details.
	 * @return OrderResponse    - The response containing order information and Razorpay details.
	 * @throws RazorpayException - Thrown if there's an issue with creating the order.
	 * Created by                Yash Tatiya
	 * Created Date              12/09/2023
	 ******************************************************************************/
	@PostMapping("/order")
	public OrderResponse createOrder(@RequestBody OrderRequest orderRequest)throws RazorpayException {
		
		try {
			return this.orderRequestService.createOrder(orderRequest);
			
		} catch (RazorpayException e) {
			throw e;
		}
	}

}
