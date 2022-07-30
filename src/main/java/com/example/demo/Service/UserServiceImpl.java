package com.example.demo.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.ReplyRepository;
import com.example.demo.Repository.TweetRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Reply;
import com.example.demo.model.Tweet;
import com.example.demo.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TweetRepository tweetRepository;
	
	@Autowired
	private ReplyRepository replyRepository;

	@Override
	public User registerUser(User user) {
		User newUser = new User();
		try {
			if (user != null) {
				newUser = userRepository.save(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newUser;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return (List<User>) userRepository.findAll();
	}

//	@Override
//	public User getTweetsByUser(String userName) {
//		// TODO Auto-generated method stub
//		return userRepository.findTweetsByuserName(userName);
//	}

	@Override
	public User loginUser(String username,String password) {
		// TODO Auto-generated method stub
		User checkUser = new User();
		try {
			checkUser = userRepository.findUserByUserNameAndPassword(username, password);
			if (checkUser != null) {
			if(checkUser.getUserName().equals(username) && checkUser.getPassword().equals(password))
				return checkUser;
			}
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public User getUserByName(String username) {
		// TODO Auto-generated method stub
		System.out.println("Returned users"+userRepository.findUserByUserName(username));
		return userRepository.findUserByUserName(username);
	}

	@Override
	public User updatePassword(String username, String pwd) {
		User updateUser=userRepository.findUserByUserName(username);
		User updatedPassword=new User();
		try {
		if(updateUser!=null) {
		updateUser.setPassword(pwd);
		//updateUser.setConfirmPassword(user.getConfirmPassword());
		 updatedPassword=userRepository.save(updateUser);}}
		catch (Exception e) {
			e.printStackTrace();
		}
		return updatedPassword;
	}
	
	@Override
	public List<Tweet> getAllTweets() {
		List<Tweet> allTweets = new ArrayList<Tweet>();
		try {
			allTweets = (List<Tweet>) tweetRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allTweets;
	}

	@Override
	public Tweet postTweet(String tweet, String userName) {
		Tweet savedTweet=new Tweet();
		Tweet newTweet = new Tweet();
		try {
		savedTweet.setUserName(userName);
		savedTweet.setMessage(tweet);
		Date date = Calendar.getInstance().getTime();  
		SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");  
	    String strDate = formatter.format(date);  
		savedTweet.setMsgPostTime(strDate);
		newTweet= tweetRepository.save(savedTweet);
		System.out.println(savedTweet);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return newTweet;
	}

	@Override
	public Tweet updateTweet(String tweetId, String tweet) {
		
		Tweet updateTweet = tweetRepository.findById(tweetId).get();
		updateTweet.setMessage(tweet);
		System.out.println("New Tweet"+updateTweet);
		return tweetRepository.save(updateTweet);
	}

	@Override
	public void deleteTweetbyId(String tweetId) {
		try {
			tweetRepository.deleteById(tweetId);
			replyRepository.deleteById(tweetId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public Reply postReply(Reply reply) {
		Reply tweetReply = new Reply();
		try {
			tweetReply = replyRepository.save(reply);

		} catch (DataAccessException exception) {
			exception.printStackTrace();
		}
		return tweetReply;
	}

	@Override
	public List<Tweet> getTweetsByUser(String userName) {
		
		return tweetRepository.findTweetsByuserName(userName);
	}

	@Override
	public List<Reply> getTweetsReply(String tweetId) {
		// TODO Auto-generated method stub
		return replyRepository.findReplyByTweet(tweetId);
	}
	
//	@Override
//	public User getUserById(int id) {
//		return userRepository.findById(id);
//	}
}
