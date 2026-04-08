package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.UserDTO;
import com.example.demo.models.entities.User;
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
	
	public Mono<UserDTO> insert(UserDTO dto) {
		User entity = new User();
		copyDtotoEntity(dto, entity);
		Mono<UserDTO> result = repository.save(entity).map(user -> new UserDTO(user));
		return result;
	}

	private void copyDtotoEntity(UserDTO dto, User entity) {
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		
	}
}
