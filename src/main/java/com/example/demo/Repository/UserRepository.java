package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@EnableScan
@Repository
public interface UserRepository extends PagingAndSortingRepository<User,String>{

	User findTweetsByuserName(String username);

//	@Query()
	User findUserByUserNameAndPassword(String username, String password);

//	@Query("{'username':{'$regex':'?0','$options':'i'}}")  
//	List<User> findUserByUserName(String userName);

//	@Query()
	User findUserByUserName(String username);
	
//	@Query("{'userId':?0}")
	Optional<User> findById(String userId);
}
