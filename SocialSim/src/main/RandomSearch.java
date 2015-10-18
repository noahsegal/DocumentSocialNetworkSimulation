package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * This will return random K documents for consumer c.
 * @author Reid Cain-Mondoux
 * @version 0.0.1
 */
public class RandomSearch implements Search {

	@Override
	public List<Document> search(Consumer consumer, List<Document> documents, int k) {
		
		long seed = System.nanoTime();
		//if there is less documents than requested return all documents
		if(k>documents.size())
			return documents;

		//copy documents and shuffle
		ArrayList<Document> documentCopy = new ArrayList<>(documents);
		Collections.shuffle(documentCopy, new Random(seed));

		//return k elements
		return documentCopy.subList(0, k);
	}
}
