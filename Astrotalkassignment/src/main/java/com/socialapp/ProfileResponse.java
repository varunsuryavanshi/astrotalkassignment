package com.socialapp;

import com.socialapp.pojo.*;

public class ProfileResponse {

	public ProfileResponse(String response, User user) {
	
		this.response = response;
		this.user = user;
	}
	private String response;
	private User user;
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
