package com.socialapp.services;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.socialapp.Friend;
import com.socialapp.daos.UserDao;
import com.socialapp.daos.UserFriendDao;
import com.socialapp.pojo.User;
import com.socialapp.pojo.UserFriend;


@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	UserFriendDao friendDao;
	
	 List<User> allUsers= new ArrayList();
	int m=0;
	int[] visitArr = new int[30];
	 int[] distArr = new int[30];
 

	
	public void createUser(User user, int s_no) {
		
		user.setSerialNumber(s_no);
		allUsers.add(user);
			userDao.save(user);
			}
	
//getting the list of friends
	public List<UserFriend> getFriendList(Friend friend) {
		List<UserFriend> friends = friendDao.findAllByUserEmailId(friend.getUserEmailID());
		return friends;
	}

//adding to friend list
	public void addFriendInToList(Friend friend) {
		String useremailid= friend.getUserEmailID();
		String friendemaildid=friend.getFriendEmailId();
		UserFriend userFriend = new UserFriend();
		userFriend.setUserEmailId(useremailid);
		userFriend.setFriendEmailId(friendemaildid);		
		friendDao.save(userFriend);
		UserFriend mutualFriend = new UserFriend();
		mutualFriend.setUserEmailId(friendemaildid);
		mutualFriend.setFriendEmailId(useremailid);		
		friendDao.save(mutualFriend);
	}

//Removing from friend list
	public void removeFriendFromList(Friend friend) {
		friendDao.deleteByUserEmailId(friend.getUserEmailID(), friend.getFriendEmailId());
		friendDao.deleteByUserEmailId(friend.getFriendEmailId(), friend.getUserEmailID());
				
	}

//problem number 5 Getting The Connections 
	
public List<User> getFriendsAtDistanceK(Friend f){
	List<User> userlog = new ArrayList();
	       List<User> listOfUsers = makeTree(f);
      List<List<UserFriend>> listOfAllFriends= getAjacencyList(listOfUsers);
      
      travserseTree(listOfUsers,listOfAllFriends);
      
	for(int i=0;i<allUsers.toArray().length;i++) {
		if(distArr[i]==f.getK()) {
			int n=i;
			 for(User u:allUsers) {
				 if(u.getSerialNumber()==i) {
					 userlog.add(u);
				 }
			 }
		}
	}
	return userlog;
}


private void travserseTree(List<User> listOfUsers, List<List<UserFriend>> listOfAllFriends) {
    clearArray(); 
	int j=0;
  for(List<UserFriend> friends: listOfAllFriends ) {
	    
  int sNumber = fetchSerialNo(listOfUsers,friends);
     
  dfs(friends,j,sNumber,listOfUsers);//DOING Depth First Search on Tree with the node for which connecion is to be determined as the root node of tree.
 
  }
	
}



private void dfs(List<UserFriend> friends, int i,int k,List<User> users) {
    if(visitArr[k]==0) {     
	visitArr[k]=1;
         distArr[k]=i;
         i=i+1;
         for(UserFriend f: friends) {
        	 List<UserFriend> node = friendDao.findAllByUserEmailId(f.getFriendEmailId());
        	 int sNumber = fetchSerialNo(users,node);
        	 dfs(node,i,sNumber,users);//calling DFS recursively to traverse each node and calculate distance from the root node.
         }
    }
         
}


private int fetchSerialNo(List<User> listOfUsers, List<UserFriend> friends) {
	for(User u: listOfUsers) {
		if(u.getEmailId()==friends.get(0).getUserEmailId()) {
			return u.getSerialNumber();
		}
	}
	return 0;
	
}


private List<List<UserFriend>> getAjacencyList(List<User> listOfUsers) {
	List<List<UserFriend>> listOfAllFriends = new ArrayList();
	for(User u: listOfUsers) {
		List<UserFriend> friends = friendDao.findAllByUserEmailId(u.getEmailId());
		listOfAllFriends.add(friends);
	}
	return listOfAllFriends;
}


private List<User> makeTree(Friend f) {
	List<User> users = new ArrayList();
	users.addAll(allUsers);
	for(int i =0;i<users.toArray().length;i++) {
		if(users.get(i).getEmailId()==f.getUserEmailID()) {
			Collections.swap(users, 0, i);//making the user who requested the api  as root node
			int swap = users.get(i).getSerialNumber();
			users.get(i).setSerialNumber(users.get(0).getSerialNumber());
			users.get(0).setSerialNumber(swap);
		}
	}
	
	return users;
}



public  void clearArray() {
	for(int i=0;i<30;i++) {
		visitArr[i]=0;
		distArr[i]=0;
	}
}

public User getUser(User user) {
    User u = userDao.findByEmailId(user.getEmailId());
	return u;
}

public boolean getUserFriend(Friend friend) {
	List<UserFriend> uf = friendDao.findAllByUserEmailId(friend.getFriendEmailId());
	for(UserFriend u:uf) {
		if(u.getFriendEmailId()==friend.getFriendEmailId()) {
			return true;
		}
	}
	return false;
}
	

	
	
}
