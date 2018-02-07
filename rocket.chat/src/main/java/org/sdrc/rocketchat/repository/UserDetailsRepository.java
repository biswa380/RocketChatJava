package org.sdrc.rocketchat.repository;


import java.util.List;

import org.sdrc.rocketchat.domain.UserDetails;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(idClass=Integer.class,domainClass=UserDetails.class)
public interface UserDetailsRepository {

	UserDetails save(UserDetails user);
	
	List<UserDetails> findAll();
	
	UserDetails findByUserName(String username);
	
}
