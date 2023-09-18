package com.cardManagement.cardmanagementapp.exceptions;

/******************************************************************************
 * @author           Anushka Joshi
 * Description       AppUserException is a custom exception class for handling user-related errors.
                     This exception is used to represent and handle errors related to user operations within the application.
                     It extends the standard Java Exception class and allows for custom error messages to be provided
                     when creating an instance of this exception.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
public class AppUserException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/******************************************************************************
     * Method                   -AppUserException
     * Description              -Constructs an AppUserException with the specified error message.
     * @param message           -A custom error message describing the reason for the exception.
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/ 
	public AppUserException(String msg) {
		super(msg);
	}
}
