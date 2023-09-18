package com.cardManagement.cardmanagementapp.service;

import java.util.Collection;
import java.util.List;

import com.cardManagement.cardmanagementapp.dto.ApplicationDto;
import com.cardManagement.cardmanagementapp.entities.Application;
import com.cardManagement.cardmanagementapp.exceptions.ApplicationException;

public interface ApplicationService {

	String createApplication(ApplicationDto newApplication, Integer userId) throws ApplicationException;

	ApplicationDto displayApplicationById(Integer userId) throws ApplicationException;

	List<ApplicationDto> displayAllApplications();
	
	String deleteApplicationById(Integer userId, Integer applicationId) throws ApplicationException;
	
 	List<Application> getApplicationByUserId(Integer userId) throws ApplicationException;
	
	//get application by user id. remove mapping of user in application.
	
	
}
