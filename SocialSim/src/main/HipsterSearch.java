package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
