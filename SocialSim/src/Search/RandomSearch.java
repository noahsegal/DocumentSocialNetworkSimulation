package Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import main.Document;
import main.User;


/**
 * This will return random K documents for consumer c.
 * @author Reid Cain-Mondoux
 * @version 0.0.1
 */
public class RandomSearch implements Search {

	@Override
	public List<Document> search(User user, List<Document> documents, int k) {
		
		long seed = System.nanoTime();
		ArrayList<Document> documentCopy = new ArrayList<>(documents);
		//if there is less documents than requested return all documents
		if(k>documentCopy.size())
			return documentCopy;

		//copy documents and shuffle

		Collections.shuffle(documentCopy, new Random(seed));

		//return k elements
		return documentCopy.subList(0, k);
	}

	@Override
	public boolean equals(Object obj){
		if (null == obj) return false;
		if ( !(obj instanceof RandomSearch ) ) return false;
		return true;
	}
	
	@Override
	public String toString(){
		return this.getClass().getName();
	}
	
}
