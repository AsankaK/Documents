package com.asankak.documents.service;

import java.util.List;

import com.asankak.documents.model.Document;

public interface DocumentService {

	Document addDocument(Document document);

	void deleteDocument(Document document);

	Document updateDocument(Document document);

	List<Document> getDocuments();

}
