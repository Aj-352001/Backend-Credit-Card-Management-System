package com.cardManagement.cardmanagementapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cardManagement.cardmanagementapp.entities.AppUser;
import com.cardManagement.cardmanagementapp.exceptions.AppUserException;


/******************************************************************************
 * @author           Anushka Joshi
 * Description       AppUserRepository is a Spring Data JPA repository for managing AppUser entities.
 *                   This interface extends the JpaRepository interface, which provides basic CRUD (Create, Read, Update, Delete)
                     operations for the AppUser entity. It also includes custom query methods for retrieving user entities by email.
 * Version           1.0
 * Created Date      12-Sept-2023 
 ******************************************************************************/
public interface AppUserRepository extends JpaRepository<AppUser, Integer>{
	
	/******************************************************************************
     * Method                   -findByEmail
     * Description              -Retrieves an AppUser entity by its email address.
     * @param email             -The email address to search for.
     * @return Optional<AppUser> -An Optional containing the AppUser entity if found, or an empty Optional if not found.
     * Created by                Anushka Joshi
     * Created Date              12-Sept-2023 
     ******************************************************************************/
    Optional<AppUser> findByEmail(String email);

}
