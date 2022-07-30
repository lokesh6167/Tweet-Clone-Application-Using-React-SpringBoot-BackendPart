package com.tweetapp.repository;

import com.tweetapp.model.Reply;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReplyRepo extends MongoRepository<Reply,String> {
}
