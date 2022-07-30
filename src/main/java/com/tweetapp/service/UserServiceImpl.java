package com.tweetapp.service;

import com.tweetapp.model.Reply;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.repository.ReplyRepo;
import com.tweetapp.repository.TweetRepo;
import com.tweetapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TweetRepo tweetRepo;

    @Autowired
    private ReplyRepo replyRepo;

    @Override
    public User registerUser(User user) {
        User newUser = new User();
        try{
            if(user!=null){
                newUser = userRepo.save(user);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return newUser;
    }

    //all the registered user
    public List<User> listUsers(){
        return userRepo.findAll();
    }

    //username as input -> list of users with that username as output
    @Override
    public List<User> findByUsername(String username) {
        List<User> users = userRepo.findAll();
        List<User> usersByUname = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if ((users.get(i).getUserName()).equals(username)) {
                User reqUser=users.get(i);
                usersByUname.add(reqUser);
            }
        }
        return usersByUname;
    }

    //username & password as input -> success if credentials are right
    @Override
    public User login(String username, String password) {
        User user;
        try{
            user = userRepo.findUserByUserNameAndPassword(username,password);
            System.out.println(user.getEmail());
            return user;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //user id as input -> changes the password of that particular user
    @Override
    public String forgotPassword(String userid,String password) {
        if(userid!=null && password!=null) {
            try {
                User user = userRepo.findUserByUserId(userid);
                user.setPassword(password);
                System.out.println(user.getUserName()+" "+user.getPassword());
                userRepo.save(user);
                return "Password updated successfully";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "insufficient data";
    }

    //any authorised user after login can post a tweet
    // tweet content and username as input -> the content is added to list of tweets
    @Override
    public Tweet postTweet(String tweet, String username) {
        Tweet newTweet = new Tweet();
        newTweet.setTweetContent(tweet);
        newTweet.setUsername(username);
        int max=10000;
        int min = 100;
        String tweetid = "T" + Integer.toString((int)(Math.random()*(max-min+1)+min));
        newTweet.setTweetId(tweetid);
        LocalDateTime current =  LocalDateTime.now();
        newTweet.setPostTime(current);
        List<String> likedUsers = new ArrayList<>();
        newTweet.setLikedUsers(likedUsers);
        tweetRepo.save(newTweet);
        return newTweet;
    }

    //all tweets
    @Override
    public List<Tweet> getAllTweets() {
        return tweetRepo.findAll();
    }

    // all tweets of particular user
    @Override
    public List<Tweet> getAllTweetsOfUser(String username) {
        List<Tweet> newTweet = tweetRepo.getTweetsByUsername(username);
        return newTweet;
    }

    //logged in user can update tweet by providing the content
    @Override
    public Tweet updateTweet(String id, String content) {
        Tweet updateTweet = tweetRepo.findTweetById(id);
        updateTweet.setTweetContent(content);
        tweetRepo.save(updateTweet);
        return updateTweet;
    }

    //delete a particular tweet
    @Override
    public String deleteTweet(String id) {
        Tweet deleteTweet = tweetRepo.findTweetById(id);
        String ret_str;
        if(deleteTweet!=null){
            tweetRepo.deleteById(id);
            ret_str = "deleted tweet with id "+id;
        }
        else
            ret_str = "no tweet found with id "+id;
        return ret_str;
    }

    // tweet id & username as input -> like status is updated
    @Override
    public List<String> likeTweet(String id, String username) {
        Tweet tweet = tweetRepo.findTweetById(id);
        List<String> likedUsers  = tweet.getLikedUsers();
        if(id!=null && username!=null){
            likedUsers.add(username);
        }
        System.out.println(likedUsers);
        tweetRepo.save(tweet);
        return tweet.getLikedUsers();
    }

    //reply to tweet
    @Override
    public Reply postTweetReply(String tweetid, String username, String tweetreply) {
        Reply newReply = new Reply();
        newReply.setTweetId(tweetid);
        newReply.setUsername(username);
        newReply.setReplyContent(tweetreply);
        LocalDateTime current =  LocalDateTime.now();
        newReply.setReplyPostTime(current);
        int max=10000;
        int min = 100;
        String replyid = "Rep" + Integer.toString((int)(Math.random()*(max-min+1)+min));
        newReply.setReplyId(replyid);
        replyRepo.save(newReply);
        return newReply;
    }

    // all replies
    @Override
    public List<Reply> getAllReplies() {
        return replyRepo.findAll();
    }

    // returns user specific to the id
    @Override
    public User findByUserId(String userId) {
        User userById = new User();
        userById = userRepo.findUserByUserId(userId);
        if(userById!=null)
            return userById;
        return null;
    }
}
