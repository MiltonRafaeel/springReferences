package com.example.demo.services;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.PostDTO;
import com.example.demo.models.entities.Post;
import com.example.demo.repositories.PostRepository;
import com.example.demo.services.exceptions.ResourceNotFoundException;

@Service
public class PostService {
	
	@Autowired
	private PostRepository repository;

	@Transactional(readOnly = true)
	public PostDTO findById(String id) {
		Post result = getEntityById(id);
		return new PostDTO(result);
	}
	
	public List<PostDTO> findByTitle(String text) {
		List<Post> result = repository.searchTitle(text);
		return result.stream().map(x -> new PostDTO(x)).toList();
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
}
