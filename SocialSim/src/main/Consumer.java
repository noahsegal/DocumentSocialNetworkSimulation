package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Group: MyNiftyJavaRepo
 * @author Noah Segal
 * Class responsible for the Consumer. Extends the Abstract User Class
 */

public class Consumer extends User {
	
	/**
	 * Initialize the Consumer
	 * Creates empty lists of followers, following, and liked documents
	 * Initializes searchMethod to Popularity Search (for now)
	 * 
	 * @param id User ID
	 */
	public Consumer(int id, Search searchMethod) {
		this.id = id;
		this.tag = "NO_TAG";
		this.searchMethod = searchMethod;
		following = new ArrayList<>();
		followers = new ArrayList<>();
		likedDocs = new ArrayList<>();
	}
	
	/**
	 * Take a turn in the simulation:
	 *  Likes documents & follow producers
	 *  Calculate payoff
	 *  
	 * @param dcuments List of all documents
	 * @return CONUSMER ALWAYS RETURNS NULL
	 */
	@Override
	public Document takeTurn(List<Document> documents) {
		calculatePayoff(documents);
		likeDocsFollowUsers(documents);
		return null;		
	}

	/**
	 * Calculate and set a payoff based on number of documents
	 * the Consumer hasn't seen AND match their tag (interest)
	 * 
	 * @param documents List of documents returned from search
	 */
	@Override
	protected void calculatePayoff(List<Document> documents) {
		for (Document doc: documents) {
			if (!likedDocs.contains(doc) && doc.getTag().equals(this.tag)) {
				payoff++;
			}
		}
	}
	
	/**
	 * @return String Description of the Consumer
	 */
	@Override
	public String toString() {
		String s = this.getClass().getSimpleName() + " with ID: " + id + " and tag: " + tag;
		
		if (following.size() == 1) s += "\nFollowing " + following.size() + " person";
		else s += "\nFollowing " + following.size() + " people";
		
		if (followers.size() == 1) s += "\nFollowed by " + followers.size() + " person";
		else s += "\nFollowed By " + followers.size() + " people";
		
		if (likedDocs.size() == 1) s += "\nLikes " + likedDocs.size() + " document\n";
		else s += "\nLikes " + likedDocs.size() + " documents\n";
		
		return s;
	}
	
	/**
	 * Check if two Consumers are the same
	 * 
	 * @param obj Object to check equality
	 * @return boolean Whether the Object is equal to this Consumer
	 */
	@Override
	public boolean equals(Object obj) {
		if (null == obj) return false;
		if ( !(obj instanceof Consumer) ) return false;

		Consumer consumer = (Consumer) obj;
		
		return tag.equals(consumer.tag) && (id == consumer.id) && followers.equals(consumer.followers) 
				&& following.equals(consumer.following) && likedDocs.equals(consumer.likedDocs);
	}
	

}
