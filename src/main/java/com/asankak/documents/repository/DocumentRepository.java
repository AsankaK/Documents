package com.asankak.documents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asankak.documents.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
