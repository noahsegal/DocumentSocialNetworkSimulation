package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Group: MyNiftyJavaRepo
 * @author Noah Segal
 * Class responsible for the Consumer
 */

public class Consumer {
	
	private String tag;
	private List<Consumer> following;
	private List<Consumer> followers;
	private List<Document> likedDocs;
	private int id;
	private int payoff;
	
	
	/**
	 * Initialize the Consumer
	 * Creates empty lists of followers, following, and liked documents
	 * Initializes searchMethod to Popularity Search (for now)
	 * 
	 * @param id User ID
	 */
	public Consumer(int id) {
		this.id = id;
		following = new ArrayList<>();
		followers = new ArrayList<>();
		likedDocs = new ArrayList<>();
	}

	/**
	 * Add a new user to the list of people following this consumer
	 * 
	 * @param newFollower New Consumer that follows you
	 */
	public void addFollower(Consumer newFollower) {
		if (null != newFollower && !followers.contains(newFollower) && !this.equals(newFollower)) {
			followers.add(newFollower);
			newFollower.following.add(this);
		}
	}
	
	/**
	 * Follow a user (add them to the following list)
	 * 
	 * @param newUser New user you wish to follow
	 */
	public void followUser(Consumer newUser) {
		if (null != newUser && !following.contains(newUser) && !this.equals(newUser)) {
			following.add(newUser);
			newUser.followers.add(this);
		}
	}
	
	/**
	 * Like a document (add it to the likedDoc list)
	 * 
	 * @param doc New document you wish to follow
	 */
	public void likeDoc(Document doc) {
		if (null != doc && !likedDocs.contains(doc)) {
			likedDocs.add(doc);
		}
	}

	/**
	 * Like documents with matching tags (interests)
	 * Follow the Producers of newly liked documents
	 * 
	 * @param documents List of documents
	 */
	protected void likeDocsAndProducers(List<Document> documents) {
		for (Document doc: documents) {
			if (doc.getTag().equals(this.tag) && !likedDocs.contains(doc)) {
				likedDocs.add(doc);
				followUser(doc.getProducer());
			}
		}
	}
	
	/**
	 * Take a turn in the simulation:
	 *  Likes documents & follow producers
	 *  Calculate payoff
	 *  
	 * @param dcuments List of all documents
	 * @return CONUSMER ALWAYS RETURNS NULL
	 */
	public Document takeTurn(List<Document> documents) {
		calcConsumerPayoff(documents);
		likeDocsAndProducers(documents);
		return null;		
	}

	/**
	 * Calculate and set a payoff based on number of documents
	 * the Consumer hasn't seen AND match their tag (interest)
	 * 
	 * @param documents List of documents returned from search
	 */
	private void calcConsumerPayoff(List<Document> documents) {
		int pay = 0;
		for (Document doc: documents) {
			if (!likedDocs.contains(doc) && doc.getTag().equals(this.tag)) {
				pay++;
			}
		}
		setPayoff(pay);
	}
	
	/**
	 * @return String Description of the Consumer
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName()
				+ " with ID: " + id + " and tag: " + tag 
				+ "\nFollowing " + following.size() + " people"
				+ "\nFollowed By " + followers.size() + " people"
				+ "\nLikes " + likedDocs.size() + " documents\n";
	}
	
	/**
	 * Check if two 
	 * 
	 * @param obj Object to check equality
	 * @return boolean Whether the Object is equal to this Consumer
	 */
	@Override
	public boolean equals(Object obj) {
		if (null == obj) return false;
		if ( !(obj instanceof Consumer) ) return false;

		Consumer consumer = (Consumer) obj;
		
		return tag.equals(consumer.tag) && followers.equals(consumer.followers) 
				&& following.equals(consumer.following) && likedDocs.equals(consumer.likedDocs);
	}
	
	
	//////////////////////////
	//  Getters & Setters  ///
	//////////////////////////
	
	/* Tag */
	/**
	 * Get the Consumer's tag
	 * 
	 * @return String
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Set the Consumer's tag
	 * 
	 * @param tag New tag for the Consumer
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/* Followers */
	/**
	 * Get the list of people following this Consumer
	 * @return List of Consumers
	 */
	public List<Consumer> getFollowers() {
		return followers;
	}

	/**
	 * Return a specific Consumer from the list of Following users
	 * @param i Index of a specific follower
	 * @return Consumer specified by the index
	 */
	public Consumer getFollowers(int i) {
		if (i >= 0 && i < followers.size()) {
			return followers.get(i);
		}
		return null;
	}
	
	/**
	 * Set the list of users following this Consumer
	 * @param followers List of users
	 */
	public void setFollowers(List<Consumer> followers) {
		this.followers = followers;
	}
	
	/**
	 * Set a new Follower at a specific index
	 * @param i Index of a specific user following this Consumer
	 * @param c Consumer to follow this Consumer
	 */
	public void setFollowers(int i, Consumer c) {
		if (i >= 0 && i < followers.size() && null != c) {
			followers.add(i, c);
		}
	}
	
	/**
	 * Get the size of the followers list
	 * @return int Size
	 */
	public int getNumberOfFollowers() {
		return followers.size();
	}

	/* Following */
	/**
	 * Get the list of people this Consumer follows
	 * @return List of Consumers
	 */
	public List<Consumer> getFollowing() {
		return following;
	}

	/**
	 * Return a specific User being followed
	 * @param i Index of a specific user
	 * @return Consumer specified by the index
	 */
	public Consumer getFollowing(int i) {
		if (i >= 0 && i < following.size()) {
			return followers.get(i);
		}
		return null;
	}
	
	/**
	 * Set the list of users following this Consumer
	 * @param following List of users
	 */
	public void setFollowing(List<Consumer> following) {
		this.following = following;
	}

	/**
	 * Follow a new user at (at a specific index)
	 * @param i Index of a new user to follow
	 * @param c Consumer you wish to follow
	 */
	public void setFollowing(int i, Consumer c) {
		if (i >= 0 && i < following.size() && null != c) {
			following.add(i, c);
		}
	}
	
	/**
	 * Get the size of the following list
	 * @return int Size
	 */
	public int getNumberOfFollowing() {
		return following.size();
	}
	
	/* Liked Documents */
	/**
	 * Get a list of documents this Consumer likes
	 * @return List of liked documents
	 */
	public List<Document> getLikedDocs() {
		return likedDocs;
	}
	
	/**
	 * Get a specific document
	 * @param i Index of document to get
	 * @return Document
	 */
	public Document getLikedDocs(int i) {
		if (i >= 0 && i < likedDocs.size()) {
			return likedDocs.get(i);
		}
		return null;
	}

	/**
	 * Set the list of liked documents
	 * @param likedDocs List of documents
	 */
	public void setLikedDocs(List<Document> likedDocs) {
		this.likedDocs = likedDocs;
	}
	
	/**
	 * Set a document to be liked at a specific index
	 * @param i Index of the new document
	 * @param d Document to add to the list of liked documents
	 */
	public void setLikedDocs(int i, Document d) {
		if (i >= 0 && i < likedDocs.size() && null != d) {
			likedDocs.add(i, d);
		}
	}
	
	/**
	 * Get the size of the likedDocs list
	 * @return int Size
	 */
	public int getNumberOfLikedDocs() {
		return likedDocs.size();
	}

	/* Payoff */
	/**
	 * Get the Consumer's payoff value
	 * @return int Payoff value
	 */
	public int getPayoff() {
		return payoff;
	}

	/**
	 * Set the Consumer's payoff value
	 * @param payoff New payoff value
	 */
	public void setPayoff(int payoff) {
		this.payoff = payoff;
	}
	
	/* ID */
	/**
	 * Get the Consumer's ID
	 * @return int ID
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Set the Consumer's ID
	 * @param id New ID 
	 */
	public void setID(int id) {
		this.id = id;
	}
	

}
