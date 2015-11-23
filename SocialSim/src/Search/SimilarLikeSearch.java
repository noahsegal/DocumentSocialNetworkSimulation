package Search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Document;
import main.User;
/**
 * SimilarLikeSearch Checks the users to see if they have similar likes to you.
 * @author Reid Cain-Mondoux
 *
 */
public class SimilarLikeSearch extends HashMapSearch {

	@Override
	public List<Document> search(User u, List<Document> d, int k) {
		Map<Document, Integer> docs = new HashMap<Document, Integer>();
		d.forEach(doc -> {
			doc.getLikers().forEach(liker -> {
				if(docs.containsKey(doc)){
				     docs.put(doc, docs.get(doc)+compatibilityScore(u,liker));
				}
				else
				{
				    docs.put(doc, compatibilityScore(u,liker));
				}
			});
		});
		d.forEach(doc ->{
			if(docs.containsKey(doc)){
			     docs.put(doc, docs.get(doc)+1);
			}
			else
			{
			    docs.put(doc, 1);
			}
		});
		List<Document> sorteddocs = sortByValues(docs);	
		if(k>sorteddocs.size())
			return sorteddocs;
		else
			return sorteddocs.subList(0, k);
	}
	
	private int compatibilityScore(User user,User liker){
		int score = 0;
		for(Document doc: user.getLikedDocs()){
			if(liker.getLikedDocs().contains(doc)) score++;
		}
		return score;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj) return false;
		if ( !(obj instanceof SimilarLikeSearch ) ) return false;
		return true;
	}

}
