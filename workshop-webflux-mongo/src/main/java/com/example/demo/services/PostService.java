package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.PostDTO;
import com.example.demo.repositories.PostRepository;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {
	
	@Autowired
	private PostRepository repository;
	
	public Mono<PostDTO> findById(String id) {
		return repository.findById(id)
				.map(existingPost -> new PostDTO(existingPost))
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Not Found")));
	}
	
	public Flux<PostDTO> findByTitle(String text) {
		return repository.searchTitle(text)
				.map(postFound -> new PostDTO(postFound));
	}
	
/*
	
	}
	
	
	
	public List<PostDTO> fullSearch(String text, String start, String end) {
		Instant startMoment = convertMoment(start, Instant.ofEpochMilli(0L));
		Instant endMoment = convertMoment(end, Instant.now());
		List<Post> result = repository.fullSearch(text, startMoment, endMoment);
		return result.stream().map(x -> new PostDTO(x)).toList();
	}
	
	private Instant convertMoment(String originalString, Instant alternative) {
		try {
			return Instant.parse(originalString);
		} 
		catch (DateTimeParseException e) {
			return alternative;
		}	
	}

	private Post getEntityById(String id) {
		Optional<Post> post = repository.findById(id);
		return post.orElseThrow(() -> new ResourceNotFoundException("Object not found"));
	}
	*/
}
