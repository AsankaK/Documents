package com.asankak.documents.service;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.stereotype.Service;

import com.asankak.documents.exception.DocumentManagerException;
import com.asankak.documents.model.Document;
import com.asankak.documents.repository.DocumentRepository;

@Service
public class DocumentServiceImpl implements DocumentService {

	private final DocumentRepository repository;

	public DocumentServiceImpl(DocumentRepository repository) {
		this.repository = repository;
	}

	@Override
	public Document addDocument(Document document) {
		try {
			return repository.saveAndFlush(document);

		} catch (Exception ex) {
			throw new DocumentManagerException(
					MessageFormat.format("Failed to save Document. Reason : {0}", ex.getMessage()));
		}
	}

	@Override
	public void deleteDocument(Document document) {
		try {
			Document existing = repository.getReferenceById(document.getId());
			if (existing != null) {
				repository.delete(existing);
			}
		} catch (Exception ex) {
			throw new DocumentManagerException(MessageFormat
					.format("Failed to delete Document with id {0}. Reason : {1}", document.getId(), ex.getMessage()));
		}

	}

	@Override
	public Document updateDocument(Document document) {
		try {
			Document existing = repository.getReferenceById(document.getId());
			if (existing != null) {
				if (document.getTitle() != null) {
					existing.setTitle(document.getTitle());
				}
				if (document.getBody() != null) {
					existing.setBody(document.getBody());
				}
				if (document.getAuthors() != null) {
					existing.setAuthors(document.getAuthors());
				}
				if (document.getReferences() != null) {
					existing.setReferences(document.getReferences());
				}

			}
			return repository.saveAndFlush(existing);

		} catch (Exception ex) {
			throw new DocumentManagerException(MessageFormat
					.format("Failed to update Document with id {0}. Reason : {1}", document.getId(), ex.getMessage()));
		}

	}

	@Override
	public List<Document> getDocuments() {

		return repository.findAll();
	}

}
