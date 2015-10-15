
package main;

import java.util.List;

/**
 * Search 
 * 
 * @author (Reid Cain-Mondoux)
 * @version(0.1.0)
 */
public interface Search {
	
	/**
	 * 
	 * @param consumer - consumer that the search is based off of.
	 * @param documents - list of documents
	 * @param k - Number of documents to return.
	 * @return - A list of documents that ranked highest in the search. 
	 */
	public List<Document> search(Consumer c, List<Document> d, int k);
}
