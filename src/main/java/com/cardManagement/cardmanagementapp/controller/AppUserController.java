package com.cardManagement.cardmanagementapp.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cardManagement.cardmanagementapp.dto.AppUserDto;
import com.cardManagement.cardmanagementapp.entities.AppUser;
import com.cardManagement.cardmanagementapp.exceptions.AppUserException;
import com.cardManagement.cardmanagementapp.exceptions.EmailVerificationException;
import com.cardManagement.cardmanagementapp.service.AppUserService;
import com.cardManagement.cardmanagementapp.dto.OtpDto;

/******************************************************************************
 * @author           Anushka Joshi
 * Description       AppUserController is responsible for managing user-related operations via RESTful endpoints.
                     It handles user registration, login, profile retrieval,otp for email verification and other user-related actions.
                     It provides endpoints to interact with user data in the system.
 * Endpoints:
 * - POST /user: Register a new user.
 * - POST /user/login: Authenticate and log in a user.
 * - POST /admin/login :  Authenticate and log in the admin.
 * - GET /user/{userId}: Retrieve user details by ID.
 * - GET /users : Retrieve all user details
 * - PUT /user/{userId}: Update user profile.
 * - DELETE /user/{userId}: Delete a user account.
 * - POST /user/otp: Generates otp 
 * - PATCH /user/otp: Validates otp 
 
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/

@RestController
@RequestMapping("api/v1")
public class AppUserController {

	@Autowired
	private AppUserService userService;

	/******************************************************************************
     * Method                   -saveUser
     * Description              -Registers a new user account based on the provided userDto.
     * @param userDto           -The user data to be registered.
     * @return AppUser          -The newly created AppUser object representing the registered user.
     * @throws AppUserException -Raised if there's an issue with user registration.
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/   
	@PostMapping("/user/")
	public AppUser saveUser(@RequestBody @Valid AppUserDto userDto) throws AppUserException {
		return this.userService.saveUser(userDto);
	}

	/******************************************************************************
     * Method                   -loginUser
     * Description              -Logs in a user based on the provided loginDetailsDto.
     * @param loginDetailsDto   -The user data to be validated.
     * @return AppUser          -The existing AppUser object representing the registered user.
     * @throws AppUserException -Raised if there are issues with user login.
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/ 
	@PostMapping("/user/login/")
	public AppUser loginUser(@RequestBody @Valid AppUserDto loginDetailsDto) throws AppUserException {
	 return this.userService.validateUser(loginDetailsDto);	
	}
	
	/******************************************************************************
     * Method                   -loginAdmin
     * Description              -Logs in the admin based on the provided loginDetailsDto.
     * @param loginDetailsDto   -The admin data to be validated.
     * @return AppUser          -The existing AppUser object representing the registered admin.
     * @throws AppUserException -Raised if there are issues with login.
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/ 
	@PostMapping("/admin/login/")
	public AppUser loginAdmin(@RequestBody @Valid AppUserDto loginDetailsDto) throws AppUserException {
	 return this.userService.validateUser(loginDetailsDto);	
	}

	/******************************************************************************
     * Method                   -updateUser
     * Description              -Updates a user profile based on the provided userDto.
     * @param userDto           -The user data to be updated.
     * @return AppUser          -The existing AppUser object representing the registered user.
     * @throws AppUserException -Raised if there are issues with user data.
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/ 
	@PutMapping("/user/{userId}")
	public AppUser updateUser(@RequestBody @Valid AppUserDto userDto) throws AppUserException {	
			return this.userService.updateUser(userDto);	
	}
	
	/******************************************************************************
     * Method                   -getAllUsers
     * Description              -Gets all users' details. This function is for the admin.
     * @return List<AppUser>    -List of all users
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/ 
	@GetMapping("/users/")
	@ResponseStatus(HttpStatus.OK)
	public List<AppUser> getAllUsers() {
		List<AppUser> listUsers = userService.findAllUsers();
		return listUsers;
	}
	
	/******************************************************************************
     * Method                   -getUserById
     * Description              -Gets a user's details by his id. This function is mainly for navigation to user dashboard in frontend.
     * @param id                - Id of user
     * @return AppUser          - AppUser object representing the registered user.
     * @throws AppUserException -Raised if user id is not found
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/ 
	@GetMapping("/user/{id}")
	@ResponseStatus(HttpStatus.OK)
	public AppUser getUserById(@PathVariable Integer id) throws AppUserException {
		return this.userService.getUserById(id);
	}
		
	/******************************************************************************
     * Method                             -generateOtp
     * Description                        -Generates otp for user email verification
     * @param userDto                     -Dto containing email of user
     * @throws EmailVerificationException -Raised if there is an issue in sending the otp through email
     * Created by                         Anushka Joshi
     * Created Date                       12-Sept-2023 
     ******************************************************************************/ 
	@PostMapping("/user/otp/")
	@ResponseStatus(HttpStatus.OK)
	public void generateOtp(@RequestBody @Valid AppUserDto userDto) throws EmailVerificationException{
		this.userService.generateOtpForEmailVerification(userDto);
	}

	/******************************************************************************
     * Method                             -verifyOtp
     * Description                        -Verifies otp sent to user email for email verification
     * @param otpDto                      -Dto containing otp sent to email 
     * @throws EmailVerificationException -Raised if otp entered is incorrect
     * Created by                         Anushka Joshi
     * Created Date                       12-Sept-2023 
     ******************************************************************************/ 
	@PatchMapping("/user/otp/")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpDto otpDto) throws EmailVerificationException
    {
		if( this.userService.verifyOtpForEmailVerification(otpDto)) {
			return ResponseEntity.status(HttpStatus.OK).body("Email validated.");
		}
		return null;
    }
	
}
