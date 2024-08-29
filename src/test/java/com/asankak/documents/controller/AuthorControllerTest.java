package com.asankak.documents.controller;

import static org.assertj.core.api.Assertions.fail;

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

import com.asankak.documents.dto.AuthorList;
import com.asankak.documents.model.Author;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Slf4j
public class AuthorControllerTest {

	private static final String API_AUTHOR = "/api/author";

	@Autowired
	private WebTestClient testClient;

	Author initAuth;

	@BeforeEach
	void init() {
		Author author = new Author();
		author.setFirstName("fn");
		author.setLastName("ln");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		try {
			String output = ow.writeValueAsString(author);
			initAuth = testClient.post().uri(API_AUTHOR).contentType(MediaType.APPLICATION_JSON).bodyValue(output)
					.exchange().expectStatus().isOk().expectBody(Author.class).returnResult().getResponseBody();

		} catch (JsonProcessingException e) {
			fail("invalid json for POST input");
		}
	}

	@AfterEach
	void cleanUp() {

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		try {
			String output = ow.writeValueAsString(initAuth);
			testClient.method(HttpMethod.DELETE).uri(API_AUTHOR).contentType(MediaType.APPLICATION_JSON)
					.bodyValue(output).exchange().expectStatus().isOk();
		} catch (JsonProcessingException e) {
			fail("invalid json for DELETE input");
		}

	}

	@Test
	void get_author_should_return_success() {

		AuthorList list = testClient.get().uri(API_AUTHOR).exchange().expectStatus().isOk().expectBody(AuthorList.class)
				.returnResult().getResponseBody();

		Assertions.assertTrue(list.getParameters().size() == 1);

	}

	@Test
	void delete_invalid_author_should_return_error() {
		Author author = new Author();
		author.setId(55L);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		try {
			String output = ow.writeValueAsString(author);
			testClient.method(HttpMethod.DELETE).uri(API_AUTHOR).contentType(MediaType.APPLICATION_JSON)
					.bodyValue(output).exchange().expectStatus().isBadRequest();
		} catch (JsonProcessingException e) {
			fail("invalid json for DELETE input");
		}

	}

	@Test
	void patch_invalid_author_should_return_error() {
		Author author = new Author();
		author.setId(55L);
		author.setFirstName("fn5");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		try {
			String output = ow.writeValueAsString(author);
			testClient.method(HttpMethod.PATCH).uri(API_AUTHOR).contentType(MediaType.APPLICATION_JSON)
					.bodyValue(output).exchange().expectStatus().isBadRequest();
		} catch (JsonProcessingException e) {
			fail("invalid json for PATCH input");
		}

	}

	@Test
	void patch_valid_author_should_return_success() {

		String patchedName = "fnPatch";
		initAuth.setFirstName(patchedName);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		try {
			String output = ow.writeValueAsString(initAuth);
			Author patched = testClient.method(HttpMethod.PATCH).uri(API_AUTHOR).contentType(MediaType.APPLICATION_JSON)
					.bodyValue(output).exchange().expectStatus().isOk().expectBody(Author.class).returnResult()
					.getResponseBody();

			Assertions.assertEquals(patchedName, patched.getFirstName());
			Assertions.assertEquals(initAuth.getId(), patched.getId());

		} catch (JsonProcessingException e) {
			fail("invalid json for PATCH input");
		}

	}

}
