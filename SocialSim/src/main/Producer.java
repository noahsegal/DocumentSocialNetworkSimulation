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
	
	/**
	 * Create a new document
	 * Like documents, follow users, etc.
	 * 
	 * @return Document The newly created document
	 */
	public Document takeTurn(List<Document> documents) {
		String docName = this.getID() + "-" + this.getUploadedDocumentSize();
		Document newDoc = produceDocument(docName);
		documents.add(newDoc);
		
		super.takeTurn(documents);
		return newDoc;
	}
	
	/**
	 * Create a new document and add it to the list of uploaded documents
	 * 
	 * @param name Name for the new document
	 * @param tag Tag for the new document
	 * @param id ID for the new document
	 * @return a new document
	 */
	public Document produceDocument(String name) {
		Document newDoc = new Document(name, this.getTag(), this);
		uploadedDocuments.add(newDoc);
		return newDoc;
	}

	/**
	 * @return String Providing information on the Producer
	 */
	public String toString() {
		return super.toString()
				+ "\nUploaded " + uploadedDocuments.size() + " documents";
	}
	
	/**
	 * @return boolean Whether the Producer objects are equal or not
	 */
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		
		if ( !(obj instanceof Producer) ) {
			return false;
		}
		Producer producer = (Producer) obj;
		
		return uploadedDocuments.equals(producer.getUploadedDocuments());
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
	
	public int getUploadedDocumentSize() {
		return uploadedDocuments.size();
	}
	

}
