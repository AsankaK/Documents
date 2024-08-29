package com.asankak.documents;

import org.springframework.boot.SpringApplication;

public class TestDocumentsApplication {

	public static void main(String[] args) {
		SpringApplication.from(DocumentsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
