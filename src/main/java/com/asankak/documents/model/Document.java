package com.asankak.documents.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "document")
public class Document {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "body")
	private String body;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinTable(name = "document_author", joinColumns = @JoinColumn(name = "document_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
	private List<Author> authors = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinTable(name = "document_reference", joinColumns = @JoinColumn(name = "document_id"), inverseJoinColumns = @JoinColumn(name = "reference_id"))
	private List<Reference> references = new ArrayList<>();

}
