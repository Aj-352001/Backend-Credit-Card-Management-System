package com.cardManagement.cardmanagementapp.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.cardManagement.cardmanagementapp.controller.AppUserController;
import com.cardManagement.cardmanagementapp.dto.AppUserDto;
import com.cardManagement.cardmanagementapp.entities.AppUser;
import com.cardManagement.cardmanagementapp.exceptions.AppUserException;
import com.cardManagement.cardmanagementapp.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/******************************************************************************
 * @author           Anushka Joshi
 * Description       It is a test class for controller class that tests the fuctionalities of login and 
                     registration of user with valid data,invalid data,exceptions.
 * Version           1.0
 * Created Date      11-Sept-2023 
 ******************************************************************************/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppUserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private AppUserService appUserService;
    
    @Autowired
    private AppUserController appUserController;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    ObjectMapper mapper = new ObjectMapper();
    
    /******************************************************************************
     * Method            -testLoginUser_ValidCredentials_Success
     * Description       -To test whether user login is successful with valid data 
     * Created by        Anushka Joshi
     * Created Date      11-Sept-2023 
     ******************************************************************************/
    @Test
    public void testLoginUser_ValidCredentials_Success() throws AppUserException {
        AppUserDto loginDetailsDto = new AppUserDto("test@example.com", "password");
        AppUser authenticatedUser = new AppUser();
        Mockito.when(appUserService.validateUser(loginDetailsDto)).thenReturn(authenticatedUser);
        ResponseEntity<AppUser> response = appUserController.loginUser(loginDetailsDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticatedUser, response.getBody());
     }

    /******************************************************************************
     * Method            -testLoginUser_InvalidCredentials_Unauthorized
     * Description       -To test whether user login returns right http status with invalid/missing data
     * Created by        Anushka Joshi
     * Created Date      11-Sept-2023 
     ******************************************************************************/
    @Test
    public void testLoginUser_InvalidCredentials_Unauthorized() throws AppUserException {
        AppUserDto loginDetailsDto = new AppUserDto("test@example.com", "invalid_password");
        Mockito.when(appUserService.validateUser(loginDetailsDto)).thenThrow(new AppUserException("Invalid credentials"));
        ResponseEntity<AppUser> response = appUserController.loginUser(loginDetailsDto);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    /******************************************************************************
     * Method            -testSaveUser_Success()
     * Description       -To test whether user registration is successful with valid data 
     * Created by        Anushka Joshi
     * Created Date      11-Sept-2023 
     ******************************************************************************/
    @Test
    public void testSaveUser_Success() throws Exception {
        AppUserDto userDto = new AppUserDto();
        userDto.setName("john.doe");
        userDto.setEmail("john.doe@gmail.com");
        userDto.setPassword("password123");
        ResultActions result = mockMvc.perform(post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userDto.getName()));
    }

    /******************************************************************************
     * Method            -testSaveUser_InvalidData()
     * Description       -To test whether user registration returns right http status with invalid/missing data
     * @throws Exception -ResultActions throws an exception for invalid data
     * Created by        Anushka Joshi
     * Created Date      11-Sept-2023 
     ******************************************************************************/
    @Test
    public void testSaveUser_InvalidData() throws Exception {
        AppUserDto invalidUserDto = new AppUserDto();
        mockMvc.perform(post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(invalidUserDto)))
                .andExpect(status().isBadRequest());
    }

    /******************************************************************************
     * Method            -testSaveUser_ExceptionHandling()
     * Description       -Tests whether valid exceptions are returned if user registration details are invalid 
     * @throws Exception -ResultActions throws an exception for invalid data
     * Created by        Anushka Joshi
     * Created Date      11-Sept-2023 
     ******************************************************************************/
    @Test
    public void testSaveUser_ExceptionHandling() throws Exception {
        AppUserDto userDto = new AppUserDto();
        userDto.setName("jane.doe");
        userDto.setEmail("jane.doegmail.com");
        userDto.setPassword("password123");
        Mockito.when(appUserService.saveUser(userDto)).thenThrow(new AppUserException("User registration failed"));
        mockMvc.perform(post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("User registration failed"));
    }
}

