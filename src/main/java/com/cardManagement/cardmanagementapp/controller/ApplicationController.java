package com.cardManagement.cardmanagementapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardManagement.cardmanagementapp.dto.ApplicationDto;
import com.cardManagement.cardmanagementapp.entities.Application;
import com.cardManagement.cardmanagementapp.exceptions.ApplicationException;
import com.cardManagement.cardmanagementapp.service.ApplicationService;
/******************************************************************************
 * @author           Anjali Kalange
 * Description       ApplicationController is responsible for managing user-related operations via RESTful endpoints.
                     It handles user registration for credit card.
                     It provides endpoints to interact with user data in the system.
 * Endpoints:
 * - POST /application/{userId}: Register a new application for credit card.
 * - GET /application/{Id}: Retrieve application by applicationId.
 * - GET /apllications : Retrieve all applications.
 * - GET /application/{userId}: Retrieve application by userId.
 * - DELETE /application/{userId}/{applicationId}: Delete a user application.
 
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class ApplicationController {

	@Autowired
	private ApplicationService applicationService;

	/******************************************************************************
     * Method                       -getApplicationById
     * Description                  -get application based on the application id.
     * @param applicationId 	    -The application Id data to be get.
     * @return ApplicationDto       -The retrived Application Dto object representing the user application.
     * @throws ApplicationException -Raised if there's an issue with application not present.
     * Created by                   Anjali Kalange
     * Created Date                 12-Sept-2023 
     ******************************************************************************/
	@GetMapping("/application/{id}")
	public ApplicationDto getApplicationById(@PathVariable("id") Integer id) throws ApplicationException {		
			return this.applicationService.displayApplicationById(id);	
	}

	/******************************************************************************
     * Method                   -addApplication
     * Description              -post application based on the UserId.
     * @param newApplication    -The application data to be validated.
     * @param userId            - Id of user
     * @return String	        -The message of successful creation of Application object representing the user application.
     * @throws ApplicationException -Raised if there's an issue with application applying.
     * Created by                Anjali Kalange
     * Created Date              12-Sept-2023 
     ******************************************************************************/
	@PostMapping("/application/{userId}")
	public String addApplication(@RequestBody @Valid ApplicationDto newApplication, @PathVariable Integer userId) throws ApplicationException {
		this.applicationService.createApplication(newApplication,userId);
		return "application successful";

	}

	/******************************************************************************
     * Method                  		 -displayAllApplications
     * Description              	 -get all the applications.
     * @return Applications     	 -List of user applications
     * @throws ApplicationException  -Raised if an application is not present.
     * Created by                	  Anjali Kalange
     * Created Date             	  12-Sept-2023 
     ******************************************************************************/
	@GetMapping("/applications/")
	public List<ApplicationDto> displayAllApplications() throws ApplicationException {
		return this.applicationService.displayAllApplications();
	}

	/******************************************************************************
     * Method                  		 -deleteApplicationById
     * Description              	 -deletes an application by Id.
     * @param userId		     	 -Id of user
     * @param ApplicationId	     	 -Id of application
     * @return String		     	 -The string message of successful deletion of application.
     * @throws ApplicationException  -Raised if  application is not present.
     * Created by                	  Anjali Kalange
     * Created Date             	  12-Sept-2023 
     ******************************************************************************/
	@DeleteMapping("/application/{userId}/{applicationId}")
	public String deleteApplicationById(@PathVariable Integer userId, @PathVariable Integer applicationId) throws ApplicationException {
		return this.applicationService.deleteApplicationById(userId, applicationId);
	}

	/******************************************************************************
     * Method                   	-getApplicationByUserId
     * Description              	-get applications based on the UserId.
     * @param userId		 		-Id of user
     * @return List<Application>    -The List of Application objects representing the user applications.
     * @throws ApplicationException -Raised if an application is not present.
     * Created by                	Anjali Kalange
     * Created Date              	12-Sept-2023 
     ******************************************************************************/
	@GetMapping("/applications/{userId}")
	public List<Application> getApplicationByUserId(@PathVariable Integer userId) throws ApplicationException{
		return this.applicationService.getApplicationByUserId(userId);
	}
}
