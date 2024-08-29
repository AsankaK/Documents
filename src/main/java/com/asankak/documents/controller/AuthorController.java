package com.asankak.documents.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asankak.documents.dto.AuthorList;
import com.asankak.documents.model.Author;
import com.asankak.documents.service.AuthorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/author")
public class AuthorController {

	private final AuthorService service;

	public AuthorController(AuthorService service) {
		super();
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<AuthorList> getAuthors() {
		return ResponseEntity.ok().body(new AuthorList(service.getAuthors()));
	}

	@PostMapping
	public ResponseEntity<Author> addAuthor(@Validated @RequestBody Author author) {
		return ResponseEntity.ok().body(service.addAuthor(author));
	}

	@PatchMapping
	public ResponseEntity<Author> updateAuthor(@Validated @RequestBody Author author) {

		return ResponseEntity.ok().body(service.updateAuthor(author));
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteAuthor(@RequestBody Author author) {
		service.deleteAuthor(author);
		return ResponseEntity.ok().build();
	}

}
