package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.UserDTO;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public Flux<UserDTO> findAll() {
		return repository.findAll().map(user -> new UserDTO(user));
	}
	
	public Mono<UserDTO> findById(String id) {
		return repository.findById(id)
				.map(existingUser -> new UserDTO(existingUser))
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Not Found")));
	}
}
