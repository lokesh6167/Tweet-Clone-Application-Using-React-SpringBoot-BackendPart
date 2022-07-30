package com.example.demo.Service;

import java.util.List;

import com.example.demo.model.Tweet;
import com.example.demo.model.User;
import com.example.demo.model.Reply;


public interface UserService {

	public User registerUser(User user);

	public List<User> getAllUsers();

	//public User getTweetsByUser(String userName);

	public User loginUser(String username,String password);

	public User getUserByName(String userName);

	public User updatePassword(String username, String pwd);
	
	List<Tweet> getAllTweets();

	Tweet postTweet(String tweet, String userName);

	Tweet updateTweet(String tweet, String tweetId);

	void deleteTweetbyId(String tweetId);

	Reply postReply(Reply reply);

	List<Tweet> getTweetsByUser(String userName);

	List<Reply> getTweetsReply(String tweetId);
	
//	User getUserById(int id);

}
