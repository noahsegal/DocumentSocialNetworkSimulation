package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * This is a searching algorithm that will always return the least popular k documents for consumer c.
 * @author Reid Cain-Mondoux
 * @version 0.0.1
 */
public class HipsterSearch implements Search {
	
	@Override
	public List<Document> search(Consumer c, List<Document> documents, int k) {
		
		if(k>documents.size())
			return documents;
		
		List<Document> documentCopy = new ArrayList<>(documents);
		Collections.reverse(documentCopy);
		return documentCopy.subList(0,k);
		
	}

}
