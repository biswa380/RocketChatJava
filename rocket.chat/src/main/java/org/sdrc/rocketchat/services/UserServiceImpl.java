package org.sdrc.rocketchat.services;


import java.util.ArrayList;
import java.util.List;

import org.sdrc.rocketchat.domain.UserDetails;
import org.sdrc.rocketchat.model.UserModel;
import org.sdrc.rocketchat.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserDetailsRepository userDetailsRepository;
	
	
	@Transactional
	@Override
	public UserDetails register(String name, String userName, String email, String pwd) {
		UserDetails user = new UserDetails();
		user.setName(name);
		user.setUserName(userName);
		user.setEmail(email);
		user.setPassword(pwd);
//		user.setUserName(email.split("@")[0]);
		
		return userDetailsRepository.save(user);
	}

	@Override
	public List<UserModel> getUserList() {
		List<UserModel> userlist=new ArrayList<UserModel>();
		List<UserDetails>users=userDetailsRepository.findAll();
		System.out.println("User list length : "+users.size());
		for (UserDetails userDetails : users) {
			UserModel userModel = new UserModel();
			userModel.setUserId(userDetails.getUserId());
			userModel.setUserName(userDetails.getUserName());
			userModel.setEmail(userDetails.getEmail());
			userModel.setLive(userDetails.getLive());
			userModel.setPassword(userDetails.getPassword());
			
			userlist.add(userModel);
		}
		
		return userlist;
	}


}
