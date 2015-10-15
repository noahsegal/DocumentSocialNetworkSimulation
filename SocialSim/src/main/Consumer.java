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
	private SearchType searchMethod;
	private int id;
	private int payoff;
	
	
	/**
	 * Initialize the Consumer, creating follower/following/liked lists
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
	 * Add user to the list of people following this consumer
	 * 
	 * @param newFollower New Consumer that follows you
	 */
	public void addFollower(Consumer newFollower) {
		if (null != newFollower && !followers.contains(newFollower)) {
			followers.add(newFollower);
		}
	}
	
	/**
	 * Follow a user (add them to the following list)
	 * 
	 * @param newUser New user you wish to follow
	 */
	public void followUser(Consumer newUser) {
		if (null != newUser && !following.contains(newUser)) {
			following.add(newUser);
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
	 * Like documents, follow users, etc.
	 * 
	 * @param documents List of documents received from search()
	 * @return ALWAYS RETURN NULL
	 */
	public Document takeTurn(List<Document> documents) {
		// Like documents that are not currently liked && match tag
		// Follow producers that are not currently followed
		for (Document doc: documents) {
			if (doc.getTag().equals(this.tag) && !likedDocs.contains(doc)) {
				likedDocs.add(doc);
				if (!following.contains(doc.getProducer())) {
					following.add(doc.getProducer());
				}
			}
		}
		return null;		
	}
	
	/**
	 * @return String
	 */
	public String toString() {
		return this.getClass().getSimpleName()
				+ " with ID: " + id + " and tag: " + tag 
				+ "\nFollowing " + following.size() + " people"
				+ "\nFollowed By " + followers.size() + " people"
				+ "\nLikes " + likedDocs.size() + " documents\n";
	}
	
	/**
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		
		if ( !(obj instanceof Consumer) ) {
			return false;
		}
		
		Consumer consumer = (Consumer) obj;
		
		return tag.equals(consumer.tag) && followers.equals(consumer.followers) 
				&& following.equals(consumer.following) && likedDocs.equals(consumer.likedDocs);
	}
	
	
	/*
	 * Getter & Setter Methods
	 */
	
	/* Tag */
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	/* Followers */
	public List<Consumer> getFollowers() {
		return followers;
	}

	public Consumer getFollowers(int i) {
		if (i >= 0 && i < followers.size()) {
			return followers.get(i);
		}
		return null;
	}
	
	public void setFollowers(List<Consumer> followers) {
		this.followers = followers;
	}
	
	public void setFollowers(int i, Consumer c) {
		if (i >= 0 && i < followers.size() && null != c) {
			followers.add(i, c);
		}
	}
	
	public int getNumberOfFollowers() {
		return followers.size();
	}

	/* Following */
	public List<Consumer> getFollowing() {
		return following;
	}

	public Consumer getFollowing(int i) {
		if (i >= 0 && i < following.size()) {
			return followers.get(i);
		}
		return null;
	}
	
	public void setFollowing(List<Consumer> following) {
		this.following = following;
	}

	public void setFollowing(int i, Consumer c) {
		if (i >= 0 && i < following.size() && null != c) {
			following.add(i, c);
		}
	}
	
	public int getNumberOfFollowing() {
		return following.size();
	}
	
	/* Liked Documents */
	public List<Document> getLikedDocs() {
		return likedDocs;
	}
	
	public Document getLikedDocs(int i) {
		if (i >= 0 && i < likedDocs.size()) {
			return likedDocs.get(i);
		}
		return null;
	}

	public void setLikedDocs(List<Document> likedDocs) {
		this.likedDocs = likedDocs;
	}
	
	public void setLikedDocs(int i, Document d) {
		if (i >= 0 && i < likedDocs.size() && null != d) {
			likedDocs.add(i, d);
		}
	}
	
	public int getNumberOfLikedDocs() {
		return likedDocs.size();
	}

	/* Search Method */
	public SearchType getSearchMethod() {
		return searchMethod;
	}

	public void setSearch(SearchType searchMethod) {
		this.searchMethod = searchMethod;
	}

	/* Payoff */
	public int getPayoff() {
		return payoff;
	}

	public void setPayoff(int payoff) {
		this.payoff = payoff;
	}
	
	/* ID */
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	

}
