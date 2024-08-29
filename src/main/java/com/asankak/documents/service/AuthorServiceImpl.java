package com.asankak.documents.service;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.stereotype.Service;

import com.asankak.documents.exception.DocumentManagerException;
import com.asankak.documents.model.Author;
import com.asankak.documents.repository.AuthorRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository repository;

	public AuthorServiceImpl(AuthorRepository repository) {
		this.repository = repository;
	}

	@Override
	public Author addAuthor(Author author) {
		return repository.saveAndFlush(author);

	}

	@Override
	public void deleteAuthor(Author author) {
		try {
			Author existing = repository.getReferenceById(author.getId());
			if (existing != null) {
				repository.delete(existing);
			}
		} catch (Exception ex) {
			throw new DocumentManagerException(MessageFormat.format("Failed to delete Author with id {0}. Reason : {1}",
					author.getId(), ex.getMessage()));
		}

	}

	@Override
	public Author updateAuthor(Author author) {
		try {
			Author existing = repository.getReferenceById(author.getId());
			if (existing != null) {
				if (author.getFirstName() != null) {
					existing.setFirstName(author.getFirstName());
				}
				if (author.getLastName() != null) {
					existing.setLastName(author.getLastName());
				}
			}
			return repository.saveAndFlush(existing);

		} catch (Exception ex) {
			throw new DocumentManagerException(MessageFormat.format("Failed to update Author with id {0}. Reason : {1}",
					author.getId(), ex.getMessage()));
		}

	}

	@Override
	public List<Author> getAuthors() {

		return repository.findAll();
	}

}
