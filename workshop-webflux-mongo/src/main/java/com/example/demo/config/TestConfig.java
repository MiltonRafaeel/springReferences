package com.example.demo.config;

import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.demo.models.embedded.Author;
import com.example.demo.models.embedded.Comment;
import com.example.demo.models.entities.Post;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@PostConstruct
	public void init() throws InterruptedException, ExecutionException {
		
		postRepository.deleteAll().block();
	    userRepository.deleteAll().block();

	    User maria = new User(null, "Maria Brown", "maria@gmail.com");
	    User alex = new User(null, "Alex Green", "alex@gmail.com");
	    User bob = new User(null, "Bob Grey", "bob@gmail.com");

	    userRepository.saveAll(Arrays.asList(maria, alex, bob)).blockLast();

	    maria = userRepository.searchEmail("maria@gmail.com").block();
	    alex  = userRepository.searchEmail("alex@gmail.com").block();
	    bob   = userRepository.searchEmail("bob@gmail.com").block();

	    Post post1 = new Post(null, Instant.parse("2021-02-13T11:15:01Z"), "Partiu viagem",
	            "Vou viajar para São Paulo. Abraços!", new Author(maria));
	    Post post2 = new Post(null, Instant.parse("2021-02-14T10:05:49Z"), "Bom dia",
	            "Acordei feliz hoje!", new Author(maria));

	    Comment c1 = new Comment("Boa viagem mano!", Instant.parse("2021-02-13T14:30:01Z"), new Author(alex));
	    Comment c2 = new Comment("Aproveite", Instant.parse("2021-02-13T15:38:05Z"), new Author(bob));
	    Comment c3 = new Comment("Tenha um ótimo dia!", Instant.parse("2021-02-14T12:34:26Z"), new Author(alex));

	    post1.getComments().addAll(Arrays.asList(c1, c2));
	    post2.getComments().addAll(Arrays.asList(c3));

	    postRepository.saveAll(Arrays.asList(post1, post2)).blockLast();

	}	
}
