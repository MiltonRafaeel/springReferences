package com.example.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.PostDTO;
import com.example.demo.services.PostService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping(value = "/{id}")
	public Mono<ResponseEntity<PostDTO>> findById(@PathVariable String id) {
		return postService.findById(id).map(postDto -> ResponseEntity.ok().body(postDto));
	}
	
	@GetMapping(value = "/titlesearch")
	public Flux<PostDTO> findByTitle(@RequestParam(value = "text", defaultValue = "") String text)  {
		return postService.findByTitle(text);
	}
	
	
	/*
	
	
	
	
	@GetMapping(value = "/fullsearch")
	public ResponseEntity<List<PostDTO>> fullSearch(
			@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "start", defaultValue = "") String start,
			@RequestParam(value = "end", defaultValue = "") String end
			) {
		List<PostDTO> result = postService.fullSearch(text, start, end);
		return ResponseEntity.ok().body(result);
	}
	*/
}
