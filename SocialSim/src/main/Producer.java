package main;

import java.util.ArrayList;
import java.util.List;

import Search.Search;

/**
 * Group: MyNiftyJavaRepo
 * @author Noah Segal
 * Class responsible for the Producer. Extends the Abstract User Class
 */

public class Producer extends User {

	private String altTag;
	private boolean actAsConsumer;
	private List<Document> uploadedDocuments;
	
	/**
	 * Initialize the Producer, creating uploadedDocuments
	 * 
	 * @param id User ID
	 */
	public Producer(int id, Search searchMethod) {
		this.id = id;
		this.tag = "NO_TAG";
		this.altTag = "Set Alt Tag";
		this.payoff = 0;
		this.searchMethod = searchMethod;
		following = new ArrayList<>();
		followers = new ArrayList<>();
		likedDocs = new ArrayList<>();
		uploadedDocuments = new ArrayList<>();
		
		actAsConsumer = true;
	}
	
	public Producer(){
		
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
		
		if (actAsConsumer) likeDocsFollowUsers(documents, tag);
		else likeDocsFollowUsers(documents, altTag);
		
		return newDoc;
	}
	
	/**
	 * Toggle whether the Producer acts a Consumer
	 */
	public void toggleActAsConsumer() {
		if (actAsConsumer) actAsConsumer = false;
		else actAsConsumer = true;
	}

	
	/**
	 * Update payoff based on documents with same tag (interest)
	 * 
	 * @param documents List of documents returned from the search
	 */
	@Override
	public void calculatePayoff(List<Document> documents) {
		for (Document doc: documents) {
			if (doc.getProducer().equals(this)) {
				payoff++;
			}
		}
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
	public String toString() {
		int docSize = uploadedDocuments.size();
		if (docSize == 1) {
			return super.toString() + "Uploaded " + docSize + " document\n" + "Alternate Tag: " + altTag;
		}
		return super.toString()  + "Uploaded " + uploadedDocuments.size() + " documents\n" + "Alternate Tag: " + altTag;
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
		
		return uploadedDocuments.equals(producer.getUploadedDocuments()) && super.equals(producer);
	}
	
	
	//////////////////////////
	//  Getters & Setters  ///
	//////////////////////////
	
	/**
	 * Get the Producer's alternative tag
	 * @return String
	 */
	public String getAltTag() {
		return altTag;
	}
	
	/**
	 * Set the Producer's alternative tag
	 * @param altTag
	 */
	public void setAltTag(String altTag) {
		this.altTag = altTag;
	}
	
	/**
	 * Get the Producer's actAsConsumer value
	 * @return boolean
	 */
	public boolean getActAsConsumer() {
		return actAsConsumer;
	}
	
	/**
	 * Set whether the Producer acts as a Consumer
	 * @param actAsConsumer
	 */
	public void setActAsConsumer(boolean actAsConsumer) {
		this.actAsConsumer = actAsConsumer;
	}
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
