package com.cardManagement.cardmanagementapp.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.PathContainer.Options;
import org.springframework.stereotype.Service;

import com.cardManagement.cardmanagementapp.entities.OrderRequest;
import com.cardManagement.cardmanagementapp.entities.OrderResponse;
import com.cardManagement.cardmanagementapp.exceptions.BillPaymentException;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import com.razorpay.RazorpayClient;

/****************************************************************************
 * Class                    - OrderRequestServiceImpl
 * Description              - Implementation of the OrderRequestService interface.
 * @Autowired billPaymentsServiceImpl - Service for bill payments.
 * Updated by                [Yash Tatiya]
 * Updated Date              [12-sept-2023]
 ****************************************************************************/
@Service
public class OrderRequestServiceImpl implements OrderRequestService{
	
	@Autowired
	private BillPaymentsServiceImpl billPaymentsServiceImpl;
	
	
	
	private RazorpayClient client;
	//generate and change keys
	private static final String SECRET_ID1 = "rzp_test_wmDl5TjphiigG5";
	private static final String SECRET_KEY1 = "GLHzG7KW47OM0n8fcpWlmwQQ";
	private static final String SECRET_ID2 = "rzp_test_J4fInjDpTX475d";
	private static final String SECRET_KEY2 = "r8fNXAB78RmsVfdiQbWGwyjr";

	/****************************************************************************
	 * Method                    - createRazorPayOrder
	 * Description               - Creates a RazorPay order with the specified amount.
	 * @param amount             - The order amount.
	 * @return Order             - The created RazorPay Order object.
	 * @throws RazorpayException - Raised if there's an error during order creation.
	 * Author                    - [Yash Tatiya]
	 * Updated Date              - [12/09/2023]
	 ****************************************************************************/
	public Order createRazorPayOrder(Double amount) throws RazorpayException{
		 // You can enable this if you want to do Auto Capture.
		JSONObject optionOrder = new JSONObject();
			optionOrder.put("amount", amount * 100.0);
			optionOrder.put("currency", "INR");
			optionOrder.put("receipt", "txn_123456");
			optionOrder.put("payment_capture", 1);
			return this.client.orders.create(optionOrder);	
	}
	
	/****************************************************************************
	 * Method                   - createOrder
	 * Description              - Creates a RazorPay order based on the provided OrderRequest.
	 * @param orderRequest      - The order request containing details like amount, credit card number, etc.
	 * @return OrderResponse    - The response containing order information.
	 * @throws RazorpayException - Raised if there's an error during order creation.
	 * Author                   - [Yash Tatiya]
	 * Updated Date             - [12/09/2023]
	 ****************************************************************************/
	@Override
	public OrderResponse createOrder(OrderRequest orderRequest) throws RazorpayException {
		OrderResponse response = new OrderResponse();
		try {

			if (orderRequest.getAmount().intValue() > 1000) {
				client = new RazorpayClient(SECRET_ID1, SECRET_KEY1);
			} else {
				client = new RazorpayClient(SECRET_ID2, SECRET_KEY2);
			}

			Order order = createRazorPayOrder(orderRequest.getAmount());
			System.out.println("---------------------------");
			String orderId = (String) order.get("id");
			System.out.println("Order ID: " + orderId);
			System.out.println("---------------------------");
			System.out.println(orderRequest.getAmount());
			response.setRazorpayOrderId(orderId);
			response.setApplicationFee("" + orderRequest.getAmount());
			if (orderRequest.getAmount().intValue() > 1000) {
				response.setSecretKey(SECRET_KEY1);
				response.setSecretId(SECRET_ID1);
				response.setPgName("razor1");
			} else {
				response.setSecretKey(SECRET_KEY2);
				response.setSecretId(SECRET_ID2);
				response.setPgName("razor2");
			}
			try {
				this.billPaymentsServiceImpl.addBillPayment(orderRequest.getCreditCardNumber(),orderRequest.getAmount());
			} catch (BillPaymentException e) {
				e.printStackTrace();
			}
			return response;
		} catch (RazorpayException e) {
			e.printStackTrace();
		}
		return response;
		
		
	}
}
