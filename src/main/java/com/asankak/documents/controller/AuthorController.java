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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/author")
@ApiResponses(value = { @ApiResponse(responseCode = "500", description="server error", content=@Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "400", description="client error", content=@Content(mediaType = "application/json"))
})
public class AuthorController {

	private final AuthorService service;

	public AuthorController(AuthorService service) {
		super();
		this.service = service;
	}

	@Operation(summary = "Gets all authors", description = "If no authors exist, empty list is returned")
	@ApiResponse(responseCode = "200", description="returns a list of all authors", content=@Content(mediaType = "application/json"))
	@GetMapping
	public ResponseEntity<AuthorList> getAuthors() {
		return ResponseEntity.ok().body(new AuthorList(service.getAuthors()));
	}

	@Operation(summary = "Creates a new Author", description = "created Author is returned")
	@ApiResponse(responseCode = "200", description="returns newly created Author", content=@Content(mediaType = "application/json"))
	@PostMapping
	public ResponseEntity<Author> addAuthor(@Validated @RequestBody Author author) {
		return ResponseEntity.ok().body(service.addAuthor(author));
	}

	@Operation(summary = "Updates existing author", description = "If an author exists, with given id, all the non-null fields are updated."+
			"If no author exists with given id, error response is returned.")
	@ApiResponse(responseCode = "200", description="returns updated Author", content=@Content(mediaType = "application/json"))
	@PatchMapping
	public ResponseEntity<Author> updateAuthor(@Validated @RequestBody Author author) {

		return ResponseEntity.ok().body(service.updateAuthor(author));
	}

	@Operation(summary = "Deletes existing author", description = "If an author exists with specified id, it will be deleted."+
			"If no author exists with specified id, error response is returned.")
	@ApiResponse(responseCode = "200", description="indicates successful deletion of Author", content=@Content(mediaType = "application/json"))
	@DeleteMapping
	public ResponseEntity<Void> deleteAuthor(@RequestBody Author author) {
		service.deleteAuthor(author);
		return ResponseEntity.ok().build();
	}

}
