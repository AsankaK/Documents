package com.asankak.documents.controller;

import static org.assertj.core.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.asankak.documents.dto.DocumentList;
import com.asankak.documents.model.Author;
import com.asankak.documents.model.Document;
import com.asankak.documents.model.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Slf4j
public class DocumentControllerTest {

	private static final String API_DOCUMENT = "/api/document";

	@Autowired
	private WebTestClient testClient;

	Document initDoc;

	@BeforeEach
	void init() {
		Document document = new Document();

		document.setBody("docText1");
		document.setTitle("doc1");

		List<Author> authors = new ArrayList<>();

		List<Reference> references = new ArrayList<>();

		Reference ref = new Reference();
		ref.setRefName("ref1");

		Author author = new Author();
		author.setFirstName("fn");
		author.setLastName("ln");

		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String output = ow.writeValueAsString(author);
			Author result = testClient.post().uri("/api/author").contentType(MediaType.APPLICATION_JSON)
					.bodyValue(output).exchange().expectStatus().isOk().expectBody(Author.class).returnResult()
					.getResponseBody();

			authors.add(result);
			document.setAuthors(authors);

			String outputRef = ow.writeValueAsString(ref);
			Reference resultRef = testClient.post().uri("/api/reference").contentType(MediaType.APPLICATION_JSON)
					.bodyValue(outputRef).exchange().expectStatus().isOk().expectBody(Reference.class).returnResult()
					.getResponseBody();

			references.add(resultRef);
			document.setReferences(references);

			String outputDoc = ow.writeValueAsString(document);
			initDoc = testClient.post().uri(API_DOCUMENT).contentType(MediaType.APPLICATION_JSON).bodyValue(outputDoc)
					.exchange().expectStatus().isOk().expectBody(Document.class).returnResult().getResponseBody();

			Assertions.assertNotNull(initDoc);

		} catch (JsonProcessingException e) {
			fail("invalid json for POST input");
		}
	}

	@AfterEach
	void cleanUp() {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		try {
			String output = ow.writeValueAsString(initDoc);
			testClient.method(HttpMethod.DELETE).uri(API_DOCUMENT).contentType(MediaType.APPLICATION_JSON)
					.bodyValue(output).exchange().expectStatus().isOk();
		} catch (JsonProcessingException e) {
			fail("invalid json for DELETE input");
		}

	}

	@Test
	void get_document_should_return_success() {

		DocumentList list = testClient.get().uri(API_DOCUMENT).exchange().expectStatus().isOk()
				.expectBody(DocumentList.class).returnResult().getResponseBody();

		Assertions.assertTrue(list.getParameters().size() == 1);

	}

	@Test
	void delete_invalid_document_should_return_error() {
		Document document = new Document();
		document.setId(55L);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		try {
			String output = ow.writeValueAsString(document);
			testClient.method(HttpMethod.DELETE).uri(API_DOCUMENT).contentType(MediaType.APPLICATION_JSON)
					.bodyValue(output).exchange().expectStatus().isBadRequest();
		} catch (JsonProcessingException e) {
			fail("invalid json for DELETE input");
		}

	}

	@Test
	void patch_invalid_document_should_return_error() {
		Document document = new Document();
		document.setId(55L);
		document.setTitle("fn5");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		try {
			String output = ow.writeValueAsString(document);
			testClient.method(HttpMethod.PATCH).uri(API_DOCUMENT).contentType(MediaType.APPLICATION_JSON)
					.bodyValue(output).exchange().expectStatus().isBadRequest();
		} catch (JsonProcessingException e) {
			fail("invalid json for PATCH input");
		}

	}

	@Test
	void patch_valid_document_should_return_success() {

		String patchedName = "fnPatch";
		initDoc.setTitle(patchedName);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		try {
			String output = ow.writeValueAsString(initDoc);
			Document patched = testClient.method(HttpMethod.PATCH).uri(API_DOCUMENT)
					.contentType(MediaType.APPLICATION_JSON).bodyValue(output).exchange().expectStatus().isOk()
					.expectBody(Document.class).returnResult().getResponseBody();

			Assertions.assertEquals(patchedName, patched.getTitle());
			Assertions.assertEquals(initDoc.getId(), patched.getId());

		} catch (JsonProcessingException e) {
			fail("invalid json for PATCH input");
		}

	}

}
