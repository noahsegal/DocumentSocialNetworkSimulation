package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Group: MyNiftyJavaRepo
 * @author Noah Segal
 * Class responsible for the Producer --> Inherits from Consumer
 */

public class Producer extends Consumer {

	private List<Document> uploadedDocuments;
	
	/**
	 * Initialize the Producer, creating uploadedDocuments
	 * 
	 * @param id User ID
	 */
	public Producer(int id) {
		super(id);
		uploadedDocuments = new ArrayList<>();
	}
	
	public void takeTurn(List<Document> documents) {
		
	}

	/**
	 * 
	 */
	public String toString() {
		return super.toString()
				+ "\nUploaded " + uploadedDocuments.size() + " documents";
	}
	
	/**
	 * 
	 */
	public boolean equals(Object obj) {
		return false;
	}
	
	/**
	 * Create a new document and add it to the list of uploaded documents
	 * 
	 * @param name Name for the new document
	 * @param tag Tag for the new document
	 * @param id ID for the new document
	 * @return a new document
	 */
	public Document produceDocument(String name, String tag, int id) {
		Document newDoc = new Document(name, tag, id);
		uploadedDocuments.add(newDoc);
		return newDoc;
	}
	
	
	/*
	 * Getters & Setters
	 */
	
	/**
	 * 
	 * @return
	 */
	public List<Document> getUploadedDocuments() {
		return uploadedDocuments;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public Document getUploadedDocument(int i) {
		if (i >= 0 && !uploadedDocuments.isEmpty()) {
			return uploadedDocuments.get(i);
		}
		return null;
	}

	/**
	 * 
	 * @param uploadedDocuments
	 */
	public void setUploadedDocuments(List<Document> uploadedDocuments) {
		this.uploadedDocuments = uploadedDocuments;
	}
	
	/**
	 * 
	 * @param i
	 * @param d
	 */
	public void setUploadedDocument(int i, Document d) {
		if (i >= 0 && null != d) {
			uploadedDocuments.add(i, d);
		}
	}
	

}
