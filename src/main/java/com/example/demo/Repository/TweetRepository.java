package com.example.demo.Repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Tweet;

@EnableScan
@Repository
public interface TweetRepository extends PagingAndSortingRepository<Tweet,String> {

//	@Query()
	List<Tweet> findTweetsByuserName(String userName);
}
		