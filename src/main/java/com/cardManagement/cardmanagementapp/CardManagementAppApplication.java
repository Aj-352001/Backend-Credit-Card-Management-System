package com.cardManagement.cardmanagementapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cardManagement.cardmanagementapp.dao.AppUserRepository;
import com.cardManagement.cardmanagementapp.entities.AppUser;
import com.cardManagement.cardmanagementapp.entities.Role;
@SpringBootApplication
@EnableScheduling
public class CardManagementAppApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CardManagementAppApplication.class, args);
	}
	@Autowired
	AppUserRepository userRepo;
	@Bean
    public WebMvcConfigurer corsConfigurer() 
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
		String passwordAdmin = encoder.encode("admin123");
		AppUser admin = new AppUser(1,"admin@gmail.com",passwordAdmin,null,null,Role.ADMIN);
		this.userRepo.save(admin);
	}
}