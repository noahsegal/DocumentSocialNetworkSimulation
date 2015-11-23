package Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import main.Document;
import main.User;

public class UserPopularitySearch implements Search {
	
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Document> sortByValues(Map<Document, Integer> map) { 
		List sortedEntryList = new LinkedList<>(map.entrySet());
		Collections.sort(sortedEntryList, new Comparator() {
			public int compare(Object o1, Object o2) {
               return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
		});
		List sortedList = new ArrayList<Document>();
		for (Iterator it = sortedEntryList.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedList.add(entry.getKey());
		} 
		Collections.reverse(sortedList);
		return sortedList;
	}
	
	@Override
	public boolean equals(Object obj){
		if (null == obj) return false;
		if ( !(obj instanceof UserPopularitySearch ) ) return false;
		return true;
	}
	
	@Override
	public String toString(){
		return this.getClass().getName();
	}
}
