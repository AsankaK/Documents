package com.asankak.documents.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asankak.documents.model.Reference;
import com.asankak.documents.service.ReferenceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reference")
public class ReferenceController {

	private final ReferenceService service;

	@GetMapping
	public ResponseEntity<List<Reference>> getAReferences() {
		return ResponseEntity.ok().body(service.getReference());
	}

	@PostMapping
	public ResponseEntity<Reference> addReference(@Validated @RequestBody Reference reference) {
		return ResponseEntity.ok().body(service.addReference(reference));
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteReference(@Validated @RequestBody Reference reference) {
		service.deleteReference(reference);
		return ResponseEntity.ok().build();
	}

}
