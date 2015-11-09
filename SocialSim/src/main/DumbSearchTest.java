package main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DumbSearchTest {
	
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
		s = new DumbSearch();
		p = new Producer(1, new DumbSearch());
		p.setTag("test");
		c = new Consumer(2, new DumbSearch());
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
		list2.add(d4);
		list2.add(d3);
		assertEquals(list2, s.search(c, listD, 2));
		d1.likeDocument(c);
		list2.remove(d4);
		list2.add(d4);
		list2.set(0,d1);
		assertEquals(list2, s.search(p, listD, 2));
		d1.likeDocument(p);
		d3.likeDocument(c);
		d2.likeDocument(c);
		list2.set(0, d1);
		list2.set(1, d3);
		assertEquals(list2, s.search(p, listD, 2));
		list2.add(d2);
		assertEquals(list2, s.search(c, listD, 3));
	}
	
	@Test
	public void testEquals() {
		assertTrue(s.equals(new DumbSearch()));
		assertFalse(s.equals(new HipsterSearch()));
	}
	
}
