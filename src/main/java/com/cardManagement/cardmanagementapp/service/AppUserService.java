package com.cardManagement.cardmanagementapp.service;

import java.util.List;

import com.cardManagement.cardmanagementapp.dto.AppUserDto;
import com.cardManagement.cardmanagementapp.dto.OtpDto;
import com.cardManagement.cardmanagementapp.entities.AppUser;
import com.cardManagement.cardmanagementapp.exceptions.AppUserException;
import com.cardManagement.cardmanagementapp.exceptions.EmailVerificationException;

/******************************************************************************
 * @author           Anushka Joshi
 * Description       AppUserService interface defines the contract for managing user-related operations.
                     This interface declares methods for performing various user-related actions, including user registration,
                     retrieval, validation, updating, email verification, and OTP verification.
                     Implementations of this interface handle the underlying logic for these operations.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
public interface AppUserService {

	AppUser saveUser(AppUserDto userDto) throws AppUserException;

	List<AppUser> findAllUsers();

	AppUser getUserById(Integer id) throws AppUserException;

	AppUser validateUser(AppUserDto loginDetails) throws AppUserException;

	AppUser updateUser(AppUserDto userDto) throws AppUserException;

	void generateOtpForEmailVerification(AppUserDto userDto) throws EmailVerificationException;

	Boolean verifyOtpForEmailVerification(OtpDto otpDto) throws EmailVerificationException;

}
