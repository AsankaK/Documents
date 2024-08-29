package com.asankak.documents.service;

import java.util.List;

import com.asankak.documents.model.Author;

public interface AuthorService {

	Author addAuthor(Author author);

	void deleteAuthor(Author author);

	Author updateAuthor(Author author);

	List<Author> getAuthors();

}
