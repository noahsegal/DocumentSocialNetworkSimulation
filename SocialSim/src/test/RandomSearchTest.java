package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.Consumer;
import main.Document;
import main.HipsterSearch;
import main.Producer;
import main.RandomSearch;
import main.Search;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing for RandomSearch.
 * @author Reid Cain-Mondoux
 * @version 0.0.1
 */
public class RandomSearchTest {

	private Search s;
	private Producer p; 
	private Consumer c;
	private Document d1;
	private Document d2;
	private Document d3;
	private Document d4;
	private List<Document> listD;
	
	@Before
	public void setUp(){
		s = new RandomSearch();
		p = new Producer(1, new RandomSearch());
		p.setTag("test");
		c = new Consumer(2, new RandomSearch());
		c.setTag("test");
		listD = new ArrayList<>();
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
		assertEquals(2, s.search(c, listD, 2).size());
		assertEquals(3, s.search(p, listD, 3).size());
		assertEquals(1, s.search(c, listD, 1).size());
	}
	
	@Test
	public void testEquals() {
		assertTrue(s.equals(new RandomSearch()));
		assertFalse(s.equals(new HipsterSearch()));
	}
}
