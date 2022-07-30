package com.tweetapp.service;

import com.tweetapp.model.Reply;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;

import java.util.List;

public interface UserService {
    public User registerUser(User user);

    List<User> listUsers();

    List<User> findByUsername(String username);

    User login(String username, String password);

    String forgotPassword(String userid, String password);

    Tweet postTweet(String tweet, String username);

    List<Tweet> getAllTweets();

    List<Tweet> getAllTweetsOfUser(String username);

    Tweet updateTweet(String id, String content);

    String deleteTweet(String id);

    List<String> likeTweet(String id,String username);

    Reply postTweetReply(String tweetid, String username, String tweetreply);

    List<Reply> getAllReplies();

    User findByUserId(String userId);
}
