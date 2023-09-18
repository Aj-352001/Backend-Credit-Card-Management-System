package com.cardManagement.cardmanagementapp.test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cardManagement.cardmanagementapp.dao.AppUserRepository;
import com.cardManagement.cardmanagementapp.dto.AppUserDto;
import com.cardManagement.cardmanagementapp.entities.AppUser;
import com.cardManagement.cardmanagementapp.exceptions.AppUserException;
import com.cardManagement.cardmanagementapp.service.AppUserService;
import com.cardManagement.cardmanagementapp.service.AppUserServiceImpl;

/******************************************************************************
 * @author           Anushka Joshi
 * Description       It is a test class for service class that tests the fuctionalities of login and 
                     registration of user with valid data,invalid data,exceptions.
 * Version           1.0
 * Created Date      11-Sept-2023 
 ******************************************************************************/
@SpringBootTest
public class AppUserServiceTests {

    private AppUserService userService;
    private AppUserRepository userRepository;

    /******************************************************************************
     * Method            -testSaveUser_SuccessfulRegistration
     * Description       -To test whether user registration is successful with valid data 
     * Created by        Anushka Joshi
     * Created Date      11-Sept-2023 
     ******************************************************************************/
    @Test
    public void testSaveUser_SuccessfulRegistration() throws AppUserException {
        AppUserDto userDto = new AppUserDto("test@example.com", "password");
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(AppUser.class))).thenReturn(new AppUser("test@example.com", "password"));
        AppUser registeredUser = userService.saveUser(userDto);
        assertNotNull(registeredUser);
    }
    
    /******************************************************************************
     * Method            -testSaveUser_DuplicateEmail_ThrowsException
     * Description       -To test whether user registration throws correct exception with duplicate email
     * Created by        Anushka Joshi
     * Created Date      11-Sept-2023 
     ******************************************************************************/
    @Test
    public void testSaveUser_DuplicateEmail_ThrowsException() throws AppUserException {
        AppUserDto userDto = new AppUserDto("test@example.com", "password");
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(new AppUser("test@example.com", "password")));
        assertThrows(AppUserException.class, () -> userService.saveUser(userDto));
    }
    
    /******************************************************************************
     * Method            -testValidateUser_ValidCredentials_Success
     * Description       -To test whether user login is successful with valid credentials
     * Created by        Anushka Joshi
     * Created Date      11-Sept-2023 
     ******************************************************************************/
    @Test
    public void testValidateUser_ValidCredentials_Success() throws AppUserException {
        AppUserDto loginDetails = new AppUserDto("test@example.com", "password");
        AppUser existingUser = new AppUser("test@example.com", "password");
        when(userRepository.findByEmail(loginDetails.getEmail())).thenReturn(Optional.of(existingUser));
        AppUser authenticatedUser = userService.validateUser(loginDetails);
        assertNotNull(authenticatedUser);
    }
    /******************************************************************************
     * Method            -testValidateUser_InvalidEmail_ThrowsException
     * Description       -To test whether user login throws correct exception with invalid email
     * Created by        Anushka Joshi
     * Created Date      11-Sept-2023 
     ******************************************************************************/
    @Test
    public void testValidateUser_InvalidEmail_ThrowsException() {
        AppUserDto loginDetails = new AppUserDto("nonexistent@example.com", "password");
        assertThrows(AppUserException.class, () -> userService.validateUser(loginDetails));
    }
    /******************************************************************************
     * Method            -testValidateUser_InvalidPassword_ThrowsException
     * Description       -To test whether user login throws correct exception with invalid password
     * Created by        Anushka Joshi
     * Created Date      11-Sept-2023 
     ******************************************************************************/    
    @Test
    public void testValidateUser_InvalidPassword_ThrowsException() {
        AppUserDto loginDetails = new AppUserDto("test@example.com", "wrong_password");
        AppUser existingUser = new AppUser("test@example.com", "password");
        when(userRepository.findByEmail(loginDetails.getEmail())).thenReturn(Optional.of(existingUser));
        assertThrows(AppUserException.class, () -> userService.validateUser(loginDetails));
    }
    /******************************************************************************
     * Method            -testUpdateUser_ValidData_Success
     * Description       -To test whether user profile updation is successful with existing user email
     * Created by        Anushka Joshi
     * Created Date      11-Sept-2023 
     ******************************************************************************/  
    @Test
    public void testUpdateUser_ValidData_Success() throws AppUserException {
        AppUserDto userDto = new AppUserDto("test@example.com", "new_password");
        AppUser existingUser = new AppUser("test@example.com", "password");
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(AppUser.class))).thenReturn(existingUser);
        AppUser updatedUser = userService.updateUser(userDto);
        assertNotNull(updatedUser);
    }
    
    /******************************************************************************
     * Method            -testUpdateUser_UserNotFound_ThrowsException
     * Description       -To test whether correct exception is thrown after entering wrong email
     * Created by        Anushka Joshi
     * Created Date      11-Sept-2023 
     ******************************************************************************/    
    
    @Test
    public void testUpdateUser_UserNotFound_ThrowsException() {
        AppUserDto userDto = new AppUserDto("nonexistent@example.com", "new_password");
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        assertThrows(AppUserException.class, () -> userService.updateUser(userDto));
    }
}
