package Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.Document;
/**
 * HashMapSearch is a abstract class for FollowingSearch, SimilarLikeSEarch and UserPopularitySearch
 * It contains the method to sort HashMaps for searches and the toString method.
 * @author Reid Cain-Mondoux
 *
 */
public abstract class HashMapSearch implements Search {

	public HashMapSearch() {
		super();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<Document> sortByValues(Map<Document, Integer> map) { 
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
	public String toString() {
		return this.getClass().getName();
	}

}