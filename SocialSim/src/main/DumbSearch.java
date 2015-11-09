package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DumbSearch implements Search {

	@Override
	public List<Document> search(User users, List<Document> documents, int k) {
		
		List<Document> documentsCopy = new ArrayList<Document>(documents);
		
		if(k>documentsCopy.size())
			return documentsCopy;

		//sorting based on popularity
		Collections.sort(documentsCopy);
		Collections.reverse(documentsCopy);
	
		return documentsCopy.subList(0, k);
	}

	public boolean equals(Object obj){
		if (null == obj) return false;
		if ( !(obj instanceof DumbSearch ) ) return false;
		return true;
	}

	
}
