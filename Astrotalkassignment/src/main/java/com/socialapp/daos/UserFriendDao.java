package com.socialapp.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.socialapp.pojo.UserFriend;


public interface UserFriendDao extends JpaRepository<UserFriend,Integer>{

	@Transactional
	@Modifying
	@Query(value="DELETE from UserFriend u WHERE u.userEmailId=:useremailid AND u.friendEmailId=:friendemailid")
      public void deleteByUserEmailId(@Param("useremailid")String userEmailId,@Param("friendemailid") String friendEmailId);

	


	public List<UserFriend> findAllByUserEmailId(String userEmailID);



  
	
}
