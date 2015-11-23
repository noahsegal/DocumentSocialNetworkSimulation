package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import main.Consumer;
import main.Document;
import main.Producer;

import org.junit.Before;
import org.junit.Test;

import Search.DumbSearch;
import Search.FollowingSearch;
import Search.HipsterSearch;
import Search.RandomSearch;
import Search.Search;
import Search.UserPopularitySearch;

/**
 * Testing for UserPopularitySearch.
 * @author Reid Cain-Mondoux
 * @version 0.0.1
 */
public class UserPopularitySearchTest {

	private Search s;
	private Producer p;
	private Producer p2;
	private Consumer c;
	private Document d1;
	private Document d2;
	private Document d3;
	private Document d4;
	private List<Document> listD;
	private List<Document> list2;
	
	@Before
	public void setUp(){
		s = new UserPopularitySearch();
		p = new Producer(1, new UserPopularitySearch());
		p2 = new Producer(3, new UserPopularitySearch());
		p.setTag("test");
		p2.setTag("test");
		c = new Consumer(2, new UserPopularitySearch());
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
		assertEquals(4,s.search(c, listD, 5).size());
		p.likeDoc(d3);
		p.likeDoc(d4);
		p2.likeDoc(d4);
		c.followUser(p);
		c.followUser(p2);
		list2.add(d4);
		list2.add(d3);
		assertEquals(list2, s.search(c, listD, 2));
		p2.likeDoc(d1);
		p.likeDoc(d1);
		p.followUser(p2);
		list2.add(0,d1);
		assertEquals(list2, s.search(c, listD, 3));
	}
	
	@Test
	public void testEquals() {
		assertTrue(s.equals(new UserPopularitySearch()));
		assertFalse(s.equals(new HipsterSearch()));
	}
}
