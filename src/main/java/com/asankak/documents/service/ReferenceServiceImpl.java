package com.asankak.documents.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.asankak.documents.model.Reference;
import com.asankak.documents.repository.ReferenceRepository;

@Service
public class ReferenceServiceImpl implements ReferenceService {

	private final ReferenceRepository repository;

	public ReferenceServiceImpl(ReferenceRepository repository) {
		this.repository = repository;
	}

	@Override
	public Reference addReference(Reference reference) {
		return repository.save(reference);

	}

	@Override
	public void deleteReference(Reference reference) {
		repository.delete(reference);

	}

	@Override
	public Reference updateReference(Reference reference) {
		return repository.save(reference);

	}

	@Override
	public List<Reference> getReference() {

		return repository.findAll();
	}

}
