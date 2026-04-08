package com.example.demo.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.entities.User;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
