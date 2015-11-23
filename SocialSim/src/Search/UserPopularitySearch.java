package Search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Document;
import main.User;
/**
 * UserPopularitySearch finds the documents where the user liking it are the most popular.
 * @author Reid Cain-Mondoux 
 */
public class UserPopularitySearch extends HashMapSearch {
	
	@Override
	public List<Document> search(User u, List<Document> d, int k) {
		Map<Document, Integer> docs = new HashMap<Document, Integer>();
		d.forEach(doc -> {
			doc.getLikers().forEach(liker -> {
				if(docs.containsKey(doc)){
				     docs.put(doc, docs.get(doc)+liker.getNumberOfFollowers());
				}
				else
				{
				    docs.put(doc, liker.getNumberOfFollowers());
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
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj) return false;
		if ( !(obj instanceof UserPopularitySearch ) ) return false;
		return true;
	}
}
