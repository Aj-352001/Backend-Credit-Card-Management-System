package com.cardManagement.cardmanagementapp.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cardManagement.cardmanagementapp.exceptions.AppUserException;

/******************************************************************************
 * @author           Anushka Joshi
 * Description       AppUserControllerAdvice is responsible for handling user exceptions. It return Http Status codes.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@RestControllerAdvice
public class AppUserControllerAdvice {
	
	/******************************************************************************
     * Method                   -handleUserException
     * Description              -This method is responsible for handling exceptions of type AppUserException that may be thrown during
	                             user-related operations, such as user registration. It takes an AppUserException as a parameter,
	                             extracts the error message from the exception, and returns an HTTP response with a Bad Request (400)
	                             status code along with the error message as the response body
     * @param userException     -The AppUserException to be handled.
     * @return ResponseEntity   -Contains the error message and a Bad Request status code.
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/ 
	@ExceptionHandler({AppUserException.class})
	public ResponseEntity<String> handleUserException(AppUserException userException) {
	    return new ResponseEntity<String>(userException.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
