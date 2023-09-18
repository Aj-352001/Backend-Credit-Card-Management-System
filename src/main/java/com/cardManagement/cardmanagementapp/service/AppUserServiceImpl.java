package com.cardManagement.cardmanagementapp.service;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cardManagement.cardmanagementapp.dao.AppUserRepository;
import com.cardManagement.cardmanagementapp.dao.ApplicationRepository;
import com.cardManagement.cardmanagementapp.dto.AppUserDto;
import com.cardManagement.cardmanagementapp.dto.OtpDto;
import com.cardManagement.cardmanagementapp.entities.AppUser;
import com.cardManagement.cardmanagementapp.entities.Application;
import com.cardManagement.cardmanagementapp.entities.Role;
import com.cardManagement.cardmanagementapp.exceptions.AppUserException;
import com.cardManagement.cardmanagementapp.exceptions.EmailVerificationException;

/******************************************************************************
 * @author           Anushka Joshi
 * Description       It is a Service class that provides services for user registration,
                     retrieval, validation, updating profile, email verification through OTP.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
@Service
public class AppUserServiceImpl implements AppUserService{
	
	@Autowired
	private AppUserRepository userRepo;
	@Autowired
	private OtpService otpService;
	@Autowired
	private EmailService emailService;

	/******************************************************************************
     * Method                   -findAllUsers
     * Description              -Retrieves a list of all users in the system.
     * @param userDto           -The user data to be registered.
     * @return List<AppUser>    -A list of AppUser objects representing all registered users.
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/
    @Override
    public List<AppUser> findAllUsers() {
        List<AppUser> users = userRepo.findAll();
		return users;
    }

    /******************************************************************************
     * Method                   -saveUser
     * Description              -Registers a new user account based on the provided userDto.
     * @param userDto           -The user data to be registered.
     * @return AppUser          -The newly created AppUser object representing the registered user.
     * @throws AppUserException -Raised if the registered email already exists.
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/  
	@Override
	public AppUser saveUser(AppUserDto userDto) throws AppUserException{
		Optional<AppUser> userOpt = this.userRepo.findByEmail(userDto.getEmail());
		if(userOpt.isPresent())
			throw new AppUserException("There is already an account registered with the same email");
	    AppUser user = new AppUser();
        user.setEmail(userDto.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        user.setUserRole(Role.USER);
        return this.userRepo.save(user);  
	}

	/******************************************************************************
     * Method                   -validateUser
     * Description              -Validates user login credentials based on the provided loginDetails.
     * @param loginDetails      -The user login data dto
     * @return AppUser          -AppUser object representing the validated user.
     * @throws AppUserException -Raised if the email is not found or password entered is wrong
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/
	@Override
	public AppUser validateUser(AppUserDto loginDetails) throws AppUserException {
		Optional<AppUser> userOpt =this.userRepo.findByEmail(loginDetails.getEmail());
		if(!userOpt.isPresent())
			throw new AppUserException("User email not found.");
		AppUser user = userOpt.get();
		String email = user.getEmail();
		String password = user.getPassword();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if(!passwordEncoder.matches(loginDetails.getPassword(),password)) {
			throw new AppUserException("You have entered wrong password.");
		}
		return user;
	}

	/******************************************************************************
     * Method                   -updateUser
     * Description              -Updates user profile information based on the provided userDto.
     * @param userDto           -The updated user data.
     * @return AppUser          -The updated AppUser object representing the user.
     * @throws AppUserException -Raised if user is not found.
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/
	@Override
	public AppUser updateUser(AppUserDto userDto) throws AppUserException {
		Optional<AppUser> userOpt = this.userRepo.findByEmail(userDto.getEmail());
		if(!userOpt.isPresent())
			throw new AppUserException("User not found");
		AppUser user = userOpt.get();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        return userRepo.save(user);
	}

	/******************************************************************************
     * Method                   -getUserById
     * Description              -Retrieves a user by their unique identifier.
     * @param id                -The unique identifier of the user to retrieve.
     * @return AppUser          -The AppUser object representing the user with the specified ID.
     * @throws AppUserException -Raised if user is not found.
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/
	@Override
	public AppUser getUserById(Integer id) throws AppUserException {
		Optional<AppUser> user = this.userRepo.findById(id);
		if(!user.isPresent())
			throw new AppUserException("User not found");
		return user.get();
	}
	
	/******************************************************************************
     * Method                             -generateOtpForEmailVerification
     * Description                        -Generates an OTP (One-Time Password) for email verification based on the provided userDto.
     * @param userDto                     -The user data for email verification.
     * @throws EmailVerificationException -Raised if there are issues with OTP generation.
     * Created by                         Anushka Joshi
     * Created Date                       13-Sept-2023 
     *******************************************************************************/
	@Override
	public void generateOtpForEmailVerification(AppUserDto userDto) throws EmailVerificationException {
		String userEmail = userDto.getEmail();
		Integer generatedOtp = otpService.generateOTP(userEmail);
		String message = generatedOtp + "is your Otp for email verification. This OTP is valid for 5 minutes.";
		try {
			emailService.sendOtpMessage(userEmail, "OTP for email verification",message);
		}
		catch(MessagingException e) {
			throw new EmailVerificationException("Email cannot be sent");
		}
	}

	/******************************************************************************
     * Method                             -verifyOtpForEmailVerification
     * Description                        -Verifies an OTP (One-Time Password) for email verification.
     * @param Otp Dto                     -The OTP data to be verified.
     * @throws EmailVerificationException -Raised if there are issues with OTP verification.
     * Created by                         Anushka Joshi
     * Created Date                       13-Sept-2023 
     *******************************************************************************/
	public Boolean verifyOtpForEmailVerification(OtpDto otpDto) throws EmailVerificationException{
		String clientEmail = otpDto.getEmail();
		Integer clientOTP = otpDto.getOtp();
		Boolean isVerified = this.otpService.validateOtp(clientEmail,clientOTP);
		if(! isVerified)
		{
			throw new EmailVerificationException("You have entered wrong OTP.");
		}
		return true;
	}

}
