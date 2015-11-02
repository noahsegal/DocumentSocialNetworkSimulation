package main;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin Fleming
 * Class to hold information on a document 
 */
public class Document implements Comparable<Document> {
	private List<User> likers;	// List of Users who like me
	private String tag;				// Tag that specifies the interest
	private String name;			// Document Name
	private Producer producer;			// ID of the producer
	
	public Document(String name, String tag, Producer p) {
		this.tag = tag;
		this.name = name;
		producer = p;
		likers = new ArrayList<User>();
	}
	
	/**
	 * Add a liker to this Document
	 * @param u the User that likes this
	 */
	public void likeDocument(User u) {
		if(!likers.contains(u))
			likers.add(u);
	}
	
	/**
	 * Return a String representation of this object
	 * @return the string representation of this object
	 */
	@Override
	public String toString() {
		return "Name: "+ name +" ProducerID: " + producer.getID() + " Tag: " + tag
				+ " Number of likes: " + likers.size();
	}
	/**
	 * @param Document to compare against
	 * @return 1 if d is larger, -1 if this is larger, 0 if equal
	 */
	@Override
	public int compareTo(Document d) {
		return d.likers.size() - this.likers.size();
	}
	
	/**
	 * Compare this Object to another
	 * @param o the object to compare
	 * @return true if the objects are the same
	 */
	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		
		if(this == o) return true;
		
		if(o instanceof Document){
			Document d = (Document) o;
			if(this.likers.size() == d.likers.size()) {
				for(int i = 0; i < this.likers.size(); i++) {
					if(!this.likers.get(i).equals(d.likers.get(i))) {
						return false;
					}
				}
				return ( (this.tag.equals(d.tag)) && (this.name.equals(d.name)) &&
						(this.producer.equals(d.producer)) );
			}
		}
		return false;
		
	}
	//=========================================================================
	// Mutators and Accessors
	//=========================================================================
	/**
	 * Get the list of people who like this
	 * @return likers
	 */
	public List<User> getLikers() {
		return likers;
	}
	
	/**
	 * Get the User in the likers list at i
	 * @param i the index in list
	 * @return the value at i, null if out of range
	 */
	public User getLikers(int i) {
		if(i < likers.size() && i >= 0) {
			return likers.get(i);
		}
		return null;
	}
	
	/**
	 * Set the list of people who like this to likers
	 * @param likers, the new list
	 */
	public void setLikers(List<User> likers) {
		this.likers = likers;
	}
	
	/**
	 * Set the User at i
	 * @param i, index
	 * @param u, new User
	 */
	public void setLikers(int i, User u) {
		this.likers.set(i,u);
	}
	
	/**
	 * get the Tag for this Document
	 * @return tag
	 */
	public String getTag() {
		return tag;
	}
	
	/**
	 * Set the tag for this document
	 * @param tag, the new tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	/**
	 * get the name of this document
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * set the name of the document
	 * @param name, the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * get the producerID who made this Document
	 * @return producerID
	 */
	public Producer getProducer() {
		return producer;
	}

	/**
	 * set the producerID for this document
	 * @param producerID, new id
	 */
	public void setProducerID(Producer producer) {
		this.producer = producer;
	}
}