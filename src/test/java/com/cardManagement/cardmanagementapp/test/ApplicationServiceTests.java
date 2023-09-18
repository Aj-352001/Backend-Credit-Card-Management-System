package com.cardManagement.cardmanagementapp.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cardManagement.cardmanagementapp.dao.AppUserRepository;
import com.cardManagement.cardmanagementapp.dao.ApplicationRepository;
import com.cardManagement.cardmanagementapp.entities.AppUser;
import com.cardManagement.cardmanagementapp.entities.Application;
import com.cardManagement.cardmanagementapp.exceptions.ApplicationException;
import com.cardManagement.cardmanagementapp.service.ApplicationServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class ApplicationServiceTests {

	@InjectMocks
	private ApplicationServiceImpl applicationService;

	@Mock
	private ApplicationRepository applicationRepo;

	@Mock
	private AppUserRepository userRepo;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateApplication_Success() throws ApplicationException {
		// Arrange
		Integer userId = 1;
		AppUser user = new AppUser();
		user.setUserId(userId);

		Application applicationDto = new Application();
		// applicationDto.setUserId(userId);

		when(userRepo.findById(userId)).thenReturn(Optional.of(user));
		when(applicationRepo.save(applicationDto)).thenReturn(applicationDto);

		// Act
		String result = applicationService.createApplication(applicationDto, userId);

		// Assert
		assertEquals("Your application was applied successfully", result);
		assertTrue(user.getApplication().contains(applicationDto));
		verify(userRepo, times(1)).save(user);
	}

	@Test
	public void testCreateApplication_UserNotFound() {
		// Arrange
		Integer userId = 1;

		when(userRepo.findById(userId)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(ApplicationException.class, () -> {
			applicationService.createApplication(new Application(), userId);
		});
	}

	@Test
	public void testDeleteApplicationById_Success() throws ApplicationException {
		// Arrange
		Integer userId = 1;
		Integer applicationId = 2;
		AppUser user = new AppUser();
		user.setUserId(userId);
		Application applicationToDelete = new Application();
		applicationToDelete.setApplicationId(applicationId);
		user.getApplication().add(applicationToDelete);

		when(userRepo.findById(userId)).thenReturn(Optional.of(user));
		when(applicationRepo.findById(applicationId)).thenReturn(Optional.of(applicationToDelete));

		// Act
		String result = applicationService.deleteApplicationById(userId, applicationId);

		// Assert
		assertEquals("Application has been deleted for the user.", result);
		assertFalse(user.getApplication().contains(applicationToDelete));
		verify(userRepo, times(1)).save(user);
		verify(applicationRepo, times(1)).deleteById(applicationId);
	}

	@Test
	public void testGetApplicationByUserId_Success() throws ApplicationException {
		// Arrange
		Integer userId = 1;
		AppUser user = new AppUser();
		user.setUserId(userId);

		List<Application> appList = new ArrayList<>();
		Application application = new Application();
		appList.add(application);
		user.setApplication(appList);

		when(userRepo.findById(userId)).thenReturn(Optional.of(user));

		// Act
		List<Application> result = applicationService.getApplicationByUserId(userId);

		// Assert
		assertEquals(appList, result);
	}
}