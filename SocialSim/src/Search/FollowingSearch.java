package Search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Document;
import main.User;
/**
 * FollowingSearch finds the documents that has the most similar people following you.
 * @author Reid Cain-Mondoux
 *
 */
public class FollowingSearch extends HashMapSearch {

	@Override
	public List<Document> search(User u, List<Document> d, int k) {
		List<User> following = u.getFollowing();
		Map<Document, Integer> docs = new HashMap<Document, Integer>();
		following.forEach(follower -> {
			follower.getLikedDocs().forEach(doc -> {
				if(docs.containsKey(doc)){
				     docs.put(doc, docs.get(doc)+1);
				}
				else
				{
				    docs.put(doc, 1);
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
		if ( !(obj instanceof FollowingSearch ) ) return false;
		return true;
	}
}
