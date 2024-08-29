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

import com.asankak.documents.dto.DocumentList;
import com.asankak.documents.model.Document;
import com.asankak.documents.service.DocumentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/document")
@Slf4j
@ApiResponses(value = { @ApiResponse(responseCode = "500", description="server error", content=@Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "400", description="client error", content=@Content(mediaType = "application/json"))
})
public class DocumentController {

	private final DocumentService service;

	public DocumentController(DocumentService service) {
		super();
		this.service = service;
	}

	@Operation(summary = "Gets all documents", description = "If no documents exist, empty list is returned")
	@ApiResponse(responseCode = "200", description="returns a list of all documents", content=@Content(mediaType = "application/json"))
	@GetMapping
	public ResponseEntity<DocumentList> getDocuments() {
		return ResponseEntity.ok().body(new DocumentList(service.getDocuments()));
	}

	@Operation(summary = "creates a new Document", description = "created Document is returned.")
	@ApiResponse(responseCode = "200", description="returns the created document", content=@Content(mediaType = "application/json"))
	@PostMapping
	public ResponseEntity<Document> addDocument(@Validated @RequestBody Document document) {
		return ResponseEntity.ok().body(service.addDocument(document));
	}

	@Operation(summary = "Updates existing Document", description = "If a document exists with specified id, all the non-null fields are updated."+
			"If no document exists with specified id, error response is returned.")
	@ApiResponse(responseCode = "200", description="returns updated document", content=@Content(mediaType = "application/json"))
	@PatchMapping
	public ResponseEntity<Document> updateDocument(@Validated @RequestBody Document document) {
		return ResponseEntity.ok().body(service.updateDocument(document));
	}

	@Operation(summary = "Deletes existing Document", description = "If a Document exists with specified id, it will be deleted."+
			"If no Document exists with specified id, error response is returned.")
	@ApiResponse(responseCode = "200", description="indicates successful deletion of document", content=@Content(mediaType = "application/json"))
	@DeleteMapping
	public ResponseEntity<Void> deleteDocument(@Validated @RequestBody Document document) {
		service.deleteDocument(document);
		return ResponseEntity.ok().build();
	}
}
