package com.cardManagement.cardmanagementapp.exceptions;

/******************************************************************************
 * @author           Anushka Joshi
 * Description       EmailVerificationException is a custom exception class for handling email verification errors.
                     This exception is used to represent and handle errors related to email verification processes.
                     It extends the standard Java Exception class and allows for custom error messages to be provided when creating an instance of this exception.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
public class EmailVerificationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/******************************************************************************
     * Method                   -EmailVerificationException
     * Description              -Constructs an EmailVerificationException with the specified error message.
     * @param message           -A custom error message describing the reason for the exception.
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/ 
	public EmailVerificationException(String message) {
		super(message);
	}
}
