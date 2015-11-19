package Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.Document;
import main.User;
/**
 * This is a searching algorithm that will always return the least popular k documents for consumer c.
 * @author Reid Cain-Mondoux
 * @version 0.0.1
 */
public class HipsterSearch implements Search {
	
	@Override
	public List<Document> search(User user, List<Document> documents, int k) {
	
		List<Document> documentCopy = new ArrayList<>(documents);
		if(k>documentCopy.size())
			return documentCopy;
		

		Collections.sort(documentCopy);
		return documentCopy.subList(0,k);
		
	}
	
	@Override
	public boolean equals(Object obj){
		if (null == obj) return false;
		if ( !(obj instanceof HipsterSearch ) ) return false;
		return true;
	}

}
