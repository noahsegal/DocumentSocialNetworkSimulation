package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DumbSearch implements Search {

	@Override
	public List<Document> search(User users, List<Document> documents, int k) {
		// TODO Auto-generated method stub
		if(k>documents.size())
			return documents;
		
		List<Document> documentsCopy = new ArrayList<Document>(documents);
		//sorting based on popularity
		Collections.sort(documentsCopy);
	
		return documentsCopy;
	}

}
