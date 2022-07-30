package com.tweetapp.controller;

import com.tweetapp.kafka.Producer;
import com.tweetapp.model.Reply;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.repository.UserRepo;
import com.tweetapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1.0/tweets")
public class UserController {

    private final Producer producer;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    //User Registration
    @PostMapping("/register")
    public User registration(@RequestBody User user){
        userService.registerUser(user);
        return user;
    }

    //User Login
    @PostMapping("/login")
    public String login(@RequestBody Map<String,Object> user){
        //System.out.println("abcdu"+user);
        User user1 = userService.login((String)user.get("username"),(String)user.get("password"));
        if(user1!= null){
            String username = user1.getUserName();
            return username+"successfully logged in";
        }
        else{
            return "no such user";
        }
    }

    //Get all users
    @GetMapping("/users/all")
    public List<User> getUsers(){
        return userService.listUsers();
    }

    //Get user by Username
    @GetMapping("/user/searchuname/{username}")
    public List<User> findByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    //Get user by UserId
    @PostMapping("/user/searchid")
    public User findByUserId(@RequestBody String userid){ return userService.findByUserId(userid);}

    //Forgot Password
    @PostMapping("/forgot")
    public String forgotPassword(@RequestBody Map<String,String> credentials){
        //System.out.println(credentials);
        return userService.forgotPassword(credentials.get("userid"),credentials.get("password"));
    }

    //Post a tweet
    @PostMapping("/addTweet")
    public Tweet newTweet(@RequestBody Map<String,String> addTweet){
        return userService.postTweet(addTweet.get("tweet"),addTweet.get("username"));
    }

    //get all tweets
    @GetMapping("/allTweets")
    public List<Tweet> listTweets(){
        return userService.getAllTweets();
    }

    //get all tweets of a user
    @PostMapping("/allTweetsOfUser")
    public List<Tweet> listTweetsOfaUser(@RequestBody String username){
        return userService.getAllTweetsOfUser(username);
    }

    //update tweet
    @PutMapping("/updateTweet")
    public Tweet updateTweet(@RequestBody Map<String,String> newTweet){
        return userService.updateTweet(newTweet.get("id"),newTweet.get("content"));
    }

    //Delete tweet
    @DeleteMapping ("/deleteTweet")
    public String deleteTweet(@RequestBody Map<String,String> deleteInfo){
        return userService.deleteTweet(deleteInfo.get("id"));
    }

    //like tweet
    @PutMapping("/likeTweet")
    public List<String> likeTweet(@RequestBody Map<String,String> details){
        return userService.likeTweet(details.get("id"),details.get("username"));
    }

    //Reply to a tweet
    @PostMapping("/replyTweet")
    public Reply replyTweet(@RequestBody Map<String,String> reply){
        return userService.postTweetReply(reply.get("tweetid"),reply.get("username"),reply.get("tweetreply"));
    }

    //Get all replies
    @GetMapping("/getAllReplies")
    public List<Reply> listReplies(){
        return userService.getAllReplies();
    }

    @Autowired
    UserController(Producer producer){
        this.producer = producer;
    }

    @PostMapping("/publish")
    public String sendMessage(@RequestBody String message){
        this.producer.sendMessage(message);
        return "Connection established";
    }
}
