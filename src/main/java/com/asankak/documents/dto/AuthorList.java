package com.asankak.documents.dto;

import java.util.List;

import com.asankak.documents.model.Author;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor

@Data
public class AuthorList {

	private List<Author> parameters;

	public AuthorList(List<Author> param) {
		this.parameters = param;
	}

}
