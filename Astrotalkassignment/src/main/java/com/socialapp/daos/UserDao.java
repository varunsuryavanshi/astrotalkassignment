package com.socialapp.daos;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.socialapp.pojo.User;


public interface UserDao extends JpaRepository<User,String> {

	User findByEmailId(String emailId);
	
	

		
}
