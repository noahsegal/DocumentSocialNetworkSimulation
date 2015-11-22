
package Search;

import java.util.List;

import main.Document;
import main.User;

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
	public List<Document> search(User u, List<Document> d, int k);
	
	@Override
	public boolean equals(Object obj);
	
	@Override
	public String toString();
}
