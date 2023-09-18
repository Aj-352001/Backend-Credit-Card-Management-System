package com.cardManagement.cardmanagementapp.service;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/******************************************************************************
 * @author           Anushka Joshi
 * Description       OtpService is a service class responsible for managing OTP (One-Time Password) operations.
                     This class provides functionality for generating, storing, retrieving, validating, and clearing OTPs.
                     The OTPs are stored in a cache with a limited expiration time to ensure their security and validity.
                     The cache is implemented using Google Guava's LoadingCache.
 * Version           1.0
 * Created Date      13-Sept-2023 
  ******************************************************************************/
@Service
public class OtpService {

	private static final Integer EXPIRE_MIN = 5;
	private LoadingCache<String, Integer> otpCache;

	/**
     * Constructs an OtpService with an expiration time for OTPs.
     */
	public OtpService() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {
					@Override
					public Integer load(String s) throws Exception {
						return 0;
					}
				});
	}

	/******************************************************************************
     * Method                   -generateOtp
     * Description              -Generates and stores an OTP for the specified key.
     * @param key               - The key associated with the OTP.
     * @return otp              -The generated OTP.
     * Created by                Anushka Joshi
     * Created Date              13-Sept-2023 
     ******************************************************************************/
	public Integer generateOTP(String key) {
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		otpCache.put(key, otp);
		return otp;
	}

	/******************************************************************************
     * Method                   -getOtp
     * Description              -Retrieves the OTP for the specified key.
     * @param key               - The key associated with the OTP.
     * @return                  -The OTP if found, or 0 if not found or expired.
     * Created by                Anushka Joshi
     * Created Date              13-Sept-2023 
     ******************************************************************************/
	public Integer getOtp(String key) {
		try {
			return otpCache.get(key);
		} catch (Exception e) {
			return 0;
		}
	}

	/******************************************************************************
     * Method                   -validateOto
     * Description              -Validates an OTP entered by a user.
     * @param key               - The key associated with the OTP.
     * @param otpNumber         -The OTP entered by the user.
     * @return Boolean          -True if the OTP is valid, false otherwise.
     * Created by                Anushka Joshi
     * Created Date              13-Sept-2023 
     ******************************************************************************/
	public Boolean validateOtp(String key, Integer otpNumber) {
		// get OTP from cache
		Integer cacheOTP = this.getOtp(key);
		if (cacheOTP != null && cacheOTP.equals(otpNumber)) {
			this.clearOTP(key);
			return true;
		}
		return false;
	}

	/******************************************************************************
     * Method                   -clearOto
     * Description              -Clears (invalidates) an OTP for the specified key.
     * @param key               - The key associated with the OTP to be cleared.
     * Created by                Anushka Joshi
     * Created Date              13-Sept-2023 
     ******************************************************************************/
	public void clearOTP(String key) {
		otpCache.invalidate(key);
	}
	
}
