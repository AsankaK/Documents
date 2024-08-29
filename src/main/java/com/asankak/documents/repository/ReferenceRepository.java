package com.asankak.documents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asankak.documents.model.Reference;

public interface ReferenceRepository extends JpaRepository<Reference, Long> {

}
