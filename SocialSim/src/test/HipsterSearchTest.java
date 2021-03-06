package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.Consumer;
import main.Document;
import main.Producer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Search.HipsterSearch;
import Search.PopularitySearch;
import Search.Search;

/**
 * Testing for HipsterSearch.
 * @author Reid Cain-Mondoux
 * @version 0.0.1
 */
public class HipsterSearchTest {
	
	private Search s;
	private Producer p; 
	private Consumer c;
	private Document d1;
	private Document d2;
	private Document d3;
	private Document d4;
	private List<Document> listD;
	private List<Document> list2;
	
	@Before
	public void setUp(){
		s = new HipsterSearch();
		p = new Producer(1, new HipsterSearch());
		p.setTag("test");
		c = new Consumer(2, new HipsterSearch());
		c.setTag("test");
		listD = new ArrayList<>();
		list2 = new ArrayList<>();
		d1 = new Document("1","test",p);
		d2 = new Document("2","test2",p);
		d3 = new Document("3","test3",p);
		d4 = new Document("4","test4",p);
		listD.add(d1);
		listD.add(d2);	
		listD.add(d3);
		listD.add(d4);
	}

	
	@Test
	public void testSearch() {
		assertEquals(listD,	s.search(c, listD, 5));
		assertEquals(4,s.search(c, listD, 5).size());
		list2.add(d1);
		list2.add(d2);
		assertEquals(list2, s.search(c, listD, 2));
		d1.likeDocument(c);
		list2.remove(d1);
		list2.add(d3);
		assertEquals(list2, s.search(p, listD, 2));
		d1.likeDocument(p);
		d3.likeDocument(c);
		d4.likeDocument(c);
		list2.set(0, d2);
		list2.set(1, d3);
		assertEquals(list2, s.search(p, listD, 2));
	}
	
	@Test
	public void testEquals() {
		assertTrue(s.equals(new HipsterSearch()));
		assertFalse(s.equals(new PopularitySearch()));
	}

}
