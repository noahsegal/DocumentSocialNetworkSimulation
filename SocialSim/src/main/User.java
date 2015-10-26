package main;

import java.util.List;

public abstract class User {

	//////////////////////////
	//  Shared Variables   ///
	//////////////////////////
	
	protected String tag;
	protected List<User> following;
	protected List<User> followers;
	protected List<Document> likedDocs;
	protected int id;
	protected int payoff;
	
	//////////////////////////
	//   Abstract Methods  ///
	//////////////////////////
	
	public abstract Document takeTurn(List<Document> documents);
	
	protected abstract int calculatePayoff(List<Document> documents);
	
	
	//////////////////////////
	//   Shared Methods    ///
	//////////////////////////
	
	/**
	 * Add a new user to the list of people following this consumer
	 * 
	 * @param newFollower New User that follows you
	 */
	public void addFollower(User newFollower) {
		if (null != newFollower && !followers.contains(newFollower) && !this.equals(newFollower)) {
			followers.add(newFollower);
			newFollower.following.add(this);
		}
	}

	/**
	 * Follow a user (add them to the following list)
	 * 
	 * @param newUser New User you wish to follow
	 */
	public void followUser(User newUser) {
		if (null != newUser && !following.contains(newUser) && !this.equals(newUser)) {
			following.add(newUser);
			newUser.followers.add(this);
		}
	}

	/**
	 * Like a document (add it to the likedDoc list)
	 * 
	 * @param doc New document you wish to like
	 */
	public void likeDoc(Document doc) {
		if (null != doc && !likedDocs.contains(doc)) {
			likedDocs.add(doc);
			doc.likeDocument(this);
		}
	}
	
	/**
	 * Like documents with matching tags (interests)
	 * Follow the Producers of newly liked documents
	 * 
	 * @param documents List of documents
	 */
	protected void likeDocsFollowUsers(List<Document> documents) {
		for (Document doc: documents) {
			if (doc.getTag().equals(this.tag)) {
				likeDoc(doc);
				followUser(doc.getProducer());
				
				// Follow users who like articles you like
				doc.getLikers().forEach( (newUser) -> {
					followUser(newUser);
					});
				}
			}
	}

	//////////////////////////
	//  Getters & Setters  ///
	//////////////////////////

	/* Tag */
	/**
	 * Get the User's tag
	 * @return String
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Set the User's tag
	 * @param tag New tag for the User
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/* Followers */
	/**
	 * Get the list of people following this User
	 * @return List of Users
	 */
	public List<User> getFollowers() {
		return followers;
	}

	/**
	 * Return a specific User from the list of Following users
	 * @param i Index of a specific follower
	 * @return User specified by the index
	 */
	public User getFollowers(int i) {
		if (i >= 0 && i < followers.size()) {
			return followers.get(i);
		}
		return null;
	}

	/**
	 * Set the list of users following this User
	 * @param followers List of Users
	 */
	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	/**
	 * Set a new Follower at a specific index
	 * @param i Index of a specific user following this User
	 * @param u User to follow this User's
	 */
	public void setFollowers(int i, Consumer u) {
		if (i >= 0 && i < followers.size() && null != u) {
			followers.add(i, u);
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
	 * Get the list of people this User follows
	 * @return List of Users
	 */
	public List<User> getFollowing() {
		return following;
	}

	/**
	 * Return a specific User being followed
	 * @param i Index of a specific User
	 * @return User specified by the index
	 */
	public User getFollowing(int i) {
		if (i >= 0 && i < following.size()) {
			return followers.get(i);
		}
		return null;
	}

	/**
	 * Set the list of users following this User
	 * @param following List of Users
	 */
	public void setFollowing(List<User> following) {
		this.following = following;
	}

	/**
	 * Follow a new user at (at a specific index)
	 * @param i Index of a new user to follow
	 * @param u User you wish to follow
	 */
	public void setFollowing(int i, Consumer u) {
		if (i >= 0 && i < following.size() && null != u) {
			following.add(i, u);
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
	 * Get a list of documents this User likes
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
	 * Get the User's payoff value
	 * @return int Payoff value
	 */
	public int getPayoff() {
		return payoff;
	}

	/**
	 * Set the User's payoff value
	 * @param payoff New payoff value
	 */
	public void setPayoff(int payoff) {
		this.payoff = payoff;
	}

	/* ID */
	/**
	 * Get the User's ID
	 * @return int ID
	 */
	public int getID() {
		return id;
	}

	/**
	 * Set the User's ID
	 * @param id New ID 
	 */
	public void setID(int id) {
		this.id = id;
	}
}
