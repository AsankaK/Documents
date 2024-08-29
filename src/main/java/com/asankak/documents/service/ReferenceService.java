package com.asankak.documents.service;

import java.util.List;

import com.asankak.documents.model.Reference;

public interface ReferenceService {

	Reference addReference(Reference reference);

	void deleteReference(Reference reference);

	Reference updateReference(Reference reference);

	List<Reference> getReference();

}
