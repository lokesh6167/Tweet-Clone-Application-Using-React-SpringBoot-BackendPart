package com.example.demo.Repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.mongodb.repository.Query;
//import org.socialsignin.spring.data.dynamodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Reply;

@EnableScan
@Repository
public interface ReplyRepository extends PagingAndSortingRepository<Reply,String>{
	
	@Query("{'tweetId': ?0}")
	List<Reply> findReplyByTweet(String tweetId);

}
