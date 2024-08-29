package com.asankak.documents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asankak.documents.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
