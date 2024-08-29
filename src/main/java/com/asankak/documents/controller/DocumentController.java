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

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/document")
@Slf4j
public class DocumentController {

	private final DocumentService service;

	public DocumentController(DocumentService service) {
		super();
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<DocumentList> getDocuments() {
		return ResponseEntity.ok().body(new DocumentList(service.getDocuments()));
	}

	@PostMapping
	public ResponseEntity<Document> addDocument(@Validated @RequestBody Document document) {
		return ResponseEntity.ok().body(service.addDocument(document));
	}

	@PatchMapping
	public ResponseEntity<Document> updateDocument(@Validated @RequestBody Document document) {
		return ResponseEntity.ok().body(service.updateDocument(document));
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteDocument(@Validated @RequestBody Document document) {
		service.deleteDocument(document);
		return ResponseEntity.ok().build();
	}
}
