package com.tweetapp.repository;

import com.tweetapp.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TweetRepo extends MongoRepository<Tweet,String> {
    @Query("{'username': ?0}")
    List<Tweet> getTweetsByUsername(String username);

    @Query("{'_id':?0}")
    Tweet findTweetById(String _id);
}
