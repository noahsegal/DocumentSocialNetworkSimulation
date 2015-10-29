package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Group: MyNiftyJavaRepo
 * @author Noah Segal
 * Class responsible for the Producer --> Inherits from Consumer
 */

public class Producer extends User {

	private List<Document> uploadedDocuments;
	
	/**
	 * Initialize the Producer, creating uploadedDocuments
	 * 
	 * @param id User ID
	 */
	public Producer(int id) {
		this.id = id;
		following = new ArrayList<>();
		followers = new ArrayList<>();
		likedDocs = new ArrayList<>();
		uploadedDocuments = new ArrayList<>();
	}
	
	/**
	 * Take a turn in the simulation:
	 *  Create a new document
	 *  Likes documents & follow producers
	 *  
	 * @param documents List of documents from search
	 * @return Document Newly created document
	 */
	@Override
	public Document takeTurn(List<Document> documents) {
		String docName = this.getID() + "-" + this.getUploadedDocumentSize();
		Document newDoc = produceDocument(docName);
		
		likeDocsFollowUsers(documents);
		
		return newDoc;
	}
	
	/**
	 * Update payoff based on documents with same tag (interest)
	 * 
	 * @param documents List of documents returned from the search
	 * @return int The User's new payoff
	 */
	@Override
	public int calculatePayoff(List<Document> documents) {
		int pay = 0;
		for (Document doc: documents) {
			if (doc.getTag().equals(this.getTag())) {
				pay++;
			}
		}
		setPayoff(pay);
		return pay;
	}
	
	/**
	 * Create a new document, add it to the list of uploaded documents, and like it
	 * 
	 * @param name Name for the new document
	 * @return Document A new document
	 */
	public Document produceDocument(String name) {
		Document newDoc = new Document(name, this.getTag(), this);
		uploadedDocuments.add(newDoc);
		likeDoc(newDoc);
		return newDoc;
	}

	/**
	 * @return String description of the Producer
	 */
	@Override
	public String toString() {
		int docSize = uploadedDocuments.size();
		if (docSize == 1) {
			return super.toString() + "Uploaded " + docSize + " document\n";
		}
		return super.toString()  + "Uploaded " + uploadedDocuments.size() + " documents\n";
	}
	
	/**
	 * @param obj Object to check equality
	 * @return boolean Whether the Object is equal to this Producer
	 */
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		
		if ( !(obj instanceof Producer) ) return false;

		Producer producer = (Producer) obj;
		
		return uploadedDocuments.equals(producer.getUploadedDocuments());
	}
	
	
	//////////////////////////
	//  Getters & Setters  ///
	//////////////////////////
	
	/**
	 * Get the list of documents this Producer has uploaded
	 * @return List Uploaded documents
	 */
	public List<Document> getUploadedDocuments() {
		return uploadedDocuments;
	}
	
	/**
	 * Get an uploaded document specified by the index 
	 * @param i Index of document to get
	 * @return Document
	 */
	public Document getUploadedDocument(int i) {
		if (i >= 0 && !uploadedDocuments.isEmpty()) {
			return uploadedDocuments.get(i);
		}
		return null;
	}

	/**
	 * Set the list of uploaded documents
	 * @param uploadedDocuments List of documents
	 */
	public void setUploadedDocuments(List<Document> uploadedDocuments) {
		this.uploadedDocuments = uploadedDocuments;
	}
	
	/**
	 * Set an uploaded document specified by the index
	 * @param i Index of document to set
	 * @param d Document
	 */
	public void setUploadedDocument(int i, Document d) {
		if (i >= 0 && null != d) {
			uploadedDocuments.add(i, d);
		}
	}
	
	/**
	 * Get the size of the uploadedDocuments list
	 * @return int Size
	 */
	public int getUploadedDocumentSize() {
		return uploadedDocuments.size();
	}
	

}
