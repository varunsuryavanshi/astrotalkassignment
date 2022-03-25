package com.socialapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.socialapp.Friend;
import com.socialapp.pojo.User;
import com.socialapp.pojo.UserFriend;
import com.socialapp.services.UserService;


@RestController
public class FriendListController {

	@Autowired
	UserService userservice;
	
@GetMapping("/getfriendslist")
ResponseEntity<List<UserFriend>> getFriendsList(@RequestBody Friend friend){
	List<UserFriend> friendList = userservice.getFriendList(friend);
	return new ResponseEntity(friendList,HttpStatus.OK);
}

@PostMapping("/addfriend")
 ResponseEntity<String> addFriend(@RequestBody  Friend friend){
	boolean ifUserEmailIdValid = checkEmailValidation(friend.getUserEmailID());
	boolean ifFriendEmailIdValid= checkEmailValidation(friend.getFriendEmailId());
	boolean isAlreadyFriend= userservice.getUserFriend(friend);
	if(ifUserEmailIdValid==true && ifFriendEmailIdValid==true && isAlreadyFriend== false)
	{
	userservice.addFriendInToList(friend)	;
	return new ResponseEntity<>(friend.getFriendEmailId() + " has been added to your friends list",HttpStatus.OK);
	}
	return new ResponseEntity<>(" EMAIL ID is not valid",HttpStatus.BAD_REQUEST);
}


@DeleteMapping("/removefriend")
ResponseEntity<String> removeFriend(@RequestBody Friend friend){
	boolean ifUserEmailIdValid = checkEmailValidation(friend.getUserEmailID());
	boolean ifFriendEmailIdValid= checkEmailValidation(friend.getFriendEmailId());
	
	if(ifUserEmailIdValid==true && ifFriendEmailIdValid==true ) {
	userservice.removeFriendFromList(friend)	;
	return new ResponseEntity<>(friend.getFriendEmailId() + " has been removed from your friends list",HttpStatus.OK);
	}
	return new ResponseEntity<>(" EMAIL ID is not valid",HttpStatus.BAD_REQUEST);
}

@GetMapping("/getconnectionatK")
ResponseEntity<List<User>> getFriendsListatDistanceK(@RequestBody Friend friend){
	List<User> userAtDistanceK = userservice.getFriendsAtDistanceK(friend);
	 return new ResponseEntity(userAtDistanceK,HttpStatus.OK);
	
}



	boolean checkEmailValidation(String emailId) {
		String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
		String email = emailId.replaceAll("//s","");
		
		if(email.isEmpty()) {
			return false;
		}else if(email.matches(regex)) {
			return true;
		}
				
		return false;
		
		
	}
	


	
}
