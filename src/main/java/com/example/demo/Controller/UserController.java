package com.example.demo.Controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.UserService;
import com.example.demo.model.Reply;
import com.example.demo.model.Tweet;
import com.example.demo.model.User;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1.0/tweets")
public class UserController {

	String username = "";
	
	@Autowired
	private UserService userService;



	@PostMapping("/Registration")
	public User registration(@RequestBody Map<String,Object> payload) {
		User newUser = new User();
		newUser.setFirstName((String)payload.get("firstName"));
		newUser.setLastName((String)payload.get("lastName"));
		newUser.setEmail((String)payload.get("email"));
		newUser.setPassword((String)payload.get("password"));
		newUser.setUserName((String)payload.get("userName"));
		return userService.registerUser(newUser);

	}

	@PostMapping("/Login")
	public User login(@RequestBody Map<String,Object> payLoad) {
		User userLogin = userService.loginUser((String)payLoad.get("username"),(String)payLoad.get("password"));
		System.out.println(userLogin);
		if(userLogin!=null) {
			username = userLogin.getUserName();
			System.out.println(username);
			return userLogin;
		}
		else {
			return null;
		}

	}

	@PostMapping(path = "/ForgotPassword")
	public User updatePassword(@RequestBody Map<String,Object> payload) {
		User userLogin = userService.updatePassword((String)payload.get("username"), (String)payload.get("pwd"));
		System.out.println(userLogin);
		return userLogin;

	}

	@GetMapping(path = "/Users/All")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping(path = "/User/username")
	public User getUserByName(@RequestBody Map<String,Object> payload) {
		return userService.getUserByName((String)payload.get("username"));
	}

	@PostMapping(path = "/AddTweet")
	public Tweet postTweet(@RequestBody Map<String,Object> payload) {
		System.out.println("Add Tweet"+username);
		return userService.postTweet((String)payload.get("content"), username);

	}

	@GetMapping(path = "/GetAllTweets")
	public List<Tweet> getAllTweets() {
		List<Tweet> tweet = userService.getAllTweets();
		return tweet;
	}

	@PutMapping(path = "/GetUserTweets/UpdateTweet")
	public Tweet updateTweet(@RequestBody Map<String,Object> payload) {
		System.out.println("Tweet Id: "+(String)payload.get("index"));
		System.out.println("TweetMessage:"+(String)payload.get("tweet"));
		Tweet updatedTweet = userService.updateTweet((String)payload.get("index"),(String)payload.get("tweet"));
		return updatedTweet;
	}

	@DeleteMapping(path = "/DeleteTweet/{tweetId}")
	public void deleteTweet(@PathVariable String tweetId) {
		userService.deleteTweetbyId(tweetId);

	}

	@PostMapping(path = "/GetAllTweets/PostReply")
	public Reply postReply(@RequestBody Map<String,String> payload) {
		Reply newReply = new Reply();
		newReply.setMessage((String)payload.get("replyContent"));
		newReply.setUserName((String)payload.get("username"));
		newReply.setTweetId((String)payload.get("tweetId"));
		Date date = Calendar.getInstance().getTime();  
		SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");  
	    String strDate = formatter.format(date);   
		newReply.setMsgPostTime(strDate);
		return userService.postReply(newReply);

	}

	@PostMapping("/GetTweets")
	public List<Tweet> getTweetByUser(@RequestBody Map<String,Object> payload) {
		System.out.println("Tweets of user"+(String)payload.get("userName"));
		return userService.getTweetsByUser(username);
	}

	@PostMapping("/GetAllTweets/GetReply")
	public List<Reply> getTweetReply(@RequestBody Map<String,Object> payload) {
		return userService.getTweetsReply((String)payload.get("tweetIndex"));
	}
	
	@PostMapping("/Main")
	public User Main(@RequestBody Map<String,Object> payload) {
		return userService.getUserByName((String)payload.get("userName"));
	}
	
	@GetMapping("/logout")
	public String logout() {
		username = "";
		return "Logout Successfully";
	}

}


