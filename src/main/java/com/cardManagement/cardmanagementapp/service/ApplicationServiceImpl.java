package com.cardManagement.cardmanagementapp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardManagement.cardmanagementapp.dao.AppUserRepository;
import com.cardManagement.cardmanagementapp.dao.ApplicationRepository;
import com.cardManagement.cardmanagementapp.dto.ApplicationDto;
import com.cardManagement.cardmanagementapp.dto.ApplicationMapper;
import com.cardManagement.cardmanagementapp.entities.AppUser;
import com.cardManagement.cardmanagementapp.entities.Application;
import com.cardManagement.cardmanagementapp.exceptions.ApplicationException;
/******************************************************************************
 * @author           Anjali Kalange
 * Description       It is a Service class that provides services for credit card application.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepo;
	
	@Autowired
	private AppUserRepository userRepo;

	/******************************************************************************
     * Method                   -createApplication
     * Description              -Creates an application for the given user
     * @param applicationDto    -The application dto object containing data to be registered.
     * @return String		    -A string message representing successful submission of application by the user.
     * Created by                Anjali Kalange
     * Created Date              12-Sept-2023 
     ******************************************************************************/
	@Override
	public String createApplication(ApplicationDto applicationDto,Integer userId) throws ApplicationException {
		Optional<AppUser> userOpt=this.userRepo.findById(userId);
		if(!userOpt.isPresent()) {
			throw new ApplicationException("User not found.");
		}
		Application newapplication = ApplicationMapper.mapToApplication(applicationDto);
		Application savedapp = this.applicationRepo.save(newapplication);	
		AppUser user=userOpt.get();
		user.getApplication().add(savedapp);
		this.userRepo.save(user);		
		return "Your appplication was applied successfully";
	}

	/******************************************************************************
     * Method                   -displayApplicationById
     * Description              -Retrieves the application object of the given applicationId.
     * @param applicationId     -Id of application
     * @return ApplicationDto	-The retrived Application object representing the user application.
     * Created by                Anjali Kalange
     * Created Date              12-Sept-2023 
     ******************************************************************************/
	@Override
	public ApplicationDto displayApplicationById(Integer id) throws ApplicationException {	
		Optional<Application> applicationOpt = this.applicationRepo.findById(id);
		if (!applicationOpt.isPresent()) {
			throw new ApplicationException("Application requested does not exist.");
		}
		Application app = applicationOpt.get();
		return ApplicationMapper.mapToApplicationDto(app);
	}

	/******************************************************************************
     * Method                   	-displayAllApplications
     * Description              	-Retrieves all applications 
     * @return List<ApplicationDto>	-list of application dto objects
     * Created by                	Anjali Kalange
     * Created Date              	12-Sept-2023 
     ******************************************************************************/
	@Override
	public List<ApplicationDto> displayAllApplications() {
		List<Application> applicationList = this.applicationRepo.findAll();
		return applicationList.stream().map(ApplicationMapper::mapToApplicationDto).collect(Collectors.toList());
	}

	/******************************************************************************
     * Method                  		 -deleteApplicationById
     * Description              	 -delete an application by Id.
     * @param userId		     	 -Id of user
     * @param ApplicationId	     	 -Id of application
     * @return String		     	 -The string message of successful deletion of application.
     * @throws ApplicationException  -Raised if application is not present.
     * Created by                	  Anjali Kalange
     * Created Date             	  12-Sept-2023 
     ******************************************************************************/
	@Override
	public String deleteApplicationById(Integer userId, Integer applicationId) throws ApplicationException {
	    Optional<AppUser> userOpt = this.userRepo.findById(userId);
	    if (!userOpt.isPresent()) {
	        throw new ApplicationException("User does not exist.");
	    }
	    AppUser user = userOpt.get();
	    Optional<Application> appOpt = user.getApplication().stream()
	            .filter(app -> app.getApplicationId().equals(applicationId))
	            .findFirst();
	    if (!appOpt.isPresent()) {
	        throw new ApplicationException("Application does not exist.");
	    }
	    Application applicationToDelete = appOpt.get();
	    user.getApplication().remove(applicationToDelete);
	    this.userRepo.save(user);
	    this.applicationRepo.deleteById(applicationId);
	    return "Application has been deleted for the user.";
	}

	/******************************************************************************
     * Method                   	-getApplicationByUserId
     * Description              	-get application based on the UserId.
     * @param userId		 		-Id of user
     * @return List<Application>    -List of applications submitted by that user
     * @throws ApplicationException -Raised if applications are not present.
     * Created by                	Anjali Kalange
     * Created Date              	12-Sept-2023 
     ******************************************************************************/
	@Override
	public List<Application> getApplicationByUserId(Integer userId) throws ApplicationException {
		Optional<AppUser> userOpt = this.userRepo.findById(userId);
		if(!userOpt.isPresent()) {
			throw new ApplicationException("User not found");
		}
		List<Application> appList = userOpt.get().getApplication();
		if(appList == null) {
			throw new ApplicationException("No applications present");
		}
		return appList;
	}


}
