package com.example.demo.repositories;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.entities.User;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

	@Query("{ 'email' : { $regex: ?0, $options: 'i'}}")
	Mono<User> searchEmail(String email);
		
}
