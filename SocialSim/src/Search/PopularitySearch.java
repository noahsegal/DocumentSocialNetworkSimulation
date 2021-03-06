package Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.Document;
import main.User;

/**
 * PopularitySearch implements search and returns the most popular search results matching a users tag.
 * If there is not enough Documents that match a users tag. It will return the most popular unmatched documents.
 * If the user request to many documents it will return all documents.
 * @author Reid Cain-Mondoux
 * @version 0.0.1
 *
 */
public class PopularitySearch implements Search {

	@Override
	public List<Document> search(User user, List<Document> documents, int k) {
		
		List<Document> documentsCopy = new ArrayList<Document>(documents);
		
		//if there is less documents than requested return all documents
		if(k>documentsCopy.size())
			return documentsCopy;
		

		List<Document> unmatchedDocuments = new ArrayList<Document>();
		List<Document> popularityDocuments = new ArrayList<Document>();
		Collections.sort(documentsCopy);
		Collections.reverse(documentsCopy);
		
		//going through list and moving all matching documents to the matched list in their popularity order. Moving all unmatched to a unmatched in popularity order.
		for(Document d : documentsCopy){
			if(isSameTag(user,d)){
				popularityDocuments.add(d);
			}else{
				unmatchedDocuments.add(d);
			}
		}
	
		//adding all unmatched documents to the back of the main list again.
		popularityDocuments.addAll(unmatchedDocuments);
		
		//return k popular documents
		return popularityDocuments.subList(0,k);
	}
	
	/**
	 * Check if the consumer and it tag match.
	 * @param consumer - consumer to compare
	 * @param document - document to compare
	 * @return - True if tag matches; False otherwise 
	 */
	private boolean isSameTag(User user, Document d){
		return (user.getTag().equals(d.getTag()));
	}

	public boolean equals(Object obj){
		if (null == obj) return false;
		if ( !(obj instanceof PopularitySearch ) ) return false;
		return true;
	}
	
	@Override
	public String toString(){
		return this.getClass().getName();
	}

}
