package com.asankak.documents.dto;

import java.util.List;

import com.asankak.documents.model.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DocumentList {

	private List<Document> parameters;
}
