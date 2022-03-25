package com.socialapp.controllers;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.socialapp.ProfileResponse;

import com.socialapp.pojo.User;
import com.socialapp.services.UserService;

@RestController
public class UserController {
   @Autowired
	UserService userService;
   private int serialNumber=0;
   
  
   @PostMapping("/user")
	public ResponseEntity<ProfileResponse> createUserProfiles(@RequestBody User user){
		
	   boolean ifValid = checkEmailValidation(user.getEmailId());
	   boolean ifNumberValid = checkNumber(user.getPhoneNumber());
	   User ifPresent = userService.getUser(user);
		
	   if((ifValid==true)&&(ifNumberValid==true)&& (ifPresent==null)) {
		   serialNumber = serialNumber+1;
		userService.createUser(user,serialNumber);
		
		ProfileResponse profile= new ProfileResponse("You have been registered Succesfully", user);
		return new ResponseEntity<>(profile, HttpStatus.OK);
	   }
	   ProfileResponse profile= new ProfileResponse("You have sent a bad request", user);
	   return new ResponseEntity<>(profile,HttpStatus.BAD_REQUEST);
	}



private boolean checkNumber(String phoneNumber) {
	String regex="^\\d{10}$";
 String number = phoneNumber.replaceAll("//s",""); 

 if(number.isEmpty() || (number.length()!=10)) {
		return false;
	}
 if(number.matches(regex)) {
	 return true;
 }
 return false;
}



boolean checkEmailValidation(String emailId) {
	String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
	String email = emailId;
	email.replaceAll("//s","");
	if(email.isEmpty()) {
		return false;
	}else if(email.matches(regex)) {
		return true;
	}
			
	return false;
	
	
}
	
   
}
