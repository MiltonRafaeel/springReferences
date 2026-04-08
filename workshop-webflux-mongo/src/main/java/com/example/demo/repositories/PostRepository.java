package com.example.demo.repositories;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.entities.Post;

import reactor.core.publisher.Flux;

@Repository
public interface PostRepository extends ReactiveMongoRepository<Post, String> {
	
	Flux<Post> findByTitleContainingIgnoreCase(String text);
	
	@Query("{ 'title' : { $regex: ?0, $options: 'i'}}")
	Flux<Post> searchTitle(String text);
	
	@Query("{ $and: [ { 'moment': { $gte : ?1 } } , { 'moment': { $lte : ?2 } }, { $or : [{ 'title' : { $regex: ?0, $options: 'i'}}, { 'body': { $regex: ?0, $options: 'i'}}, { 'comments.text': { $regex: ?0, $options: 'i'}}] } ] }")
	Flux<Post> fullSearch(String text, Instant startMoment, Instant endMoment);
	
	@Query("{ 'author.id' : ?0 }")
	Flux<Post> findByUser(String id);

}
