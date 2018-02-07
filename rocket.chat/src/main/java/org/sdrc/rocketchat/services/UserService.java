package org.sdrc.rocketchat.services;

import java.util.List;

import org.sdrc.rocketchat.domain.UserDetails;
import org.sdrc.rocketchat.model.UserModel;


public interface UserService {

	public UserDetails register(String name, String userName, String email, String pwd);
	
	public List<UserModel> getUserList();
	

}
