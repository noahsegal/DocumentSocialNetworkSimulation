package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PopularitySearch implements Search {

	@Override
	public List<Document> search(Consumer c, List<Document> documents, int k) {
		
		//if there is less documents than requested return all documents
		if(k>documents.size())
			return documents;
		
		List<Document> documentCopy = new ArrayList<Document>(documents);
		Collections.sort(documentCopy);
		
		
		//going through list and moving all non-matching documents to back of the list in their popularity order.
		documentCopy.forEach(doc -> {
			if(!isSameTag(c,doc)) documentCopy.add(doc);
		});
		
		//return k popular documents
		return documentCopy.subList(0,k);
	}
	
	/**
	 * Check if the consumer and it tag match.
	 * @param consumer - consumer to compare
	 * @param document - document to compare
	 * @return - True if tag matches; False otherwise 
	 */
	private boolean isSameTag(Consumer c, Document d){
		return (c.getTag().equals(d.getTag()));
	}

}
