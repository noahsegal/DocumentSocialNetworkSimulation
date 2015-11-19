package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.Consumer;
import main.Document;
import main.Producer;

import org.junit.Before;
import org.junit.Test;

import Search.DumbSearch;
import Search.HipsterSearch;
import Search.PopularitySearch;
import Search.RandomSearch;

/**
 * Testing for consumer
 * @author Reid Cain-Mondoux
 * @version 0.0.1
 */
public class ConsumerTest {
	
	private Consumer consumer1;
	private Consumer consumer2;
	private Consumer consumer3;
	private Document document1;
	private Document document2;
	private Document document3;
	private Producer p;
	private List<Document> documents;
	
	@Before
	public void setUp(){
		p = new Producer(3, new PopularitySearch());
		consumer1 = new Consumer(1, new HipsterSearch());
		consumer2 = new Consumer(2, new DumbSearch());
		consumer3 = new Consumer(3, new RandomSearch());
		consumer1.setTag("test");
		consumer2.setTag("table");
		consumer3.setTag("apple");
		document1 = new Document("test","test",p);
		document2 = new Document("table","table",p);
		document3 = new Document("table2","table",p);
		documents = new ArrayList<>();
		documents.add(document1);
		documents.add(document2);
		documents.add(document3);
	}

	@Test
	public void testTakeTurn() {
		int[] ints = {1,2,0};
		Document[] docs = {document1,document2,null};
		Consumer[] consumers = { consumer1, consumer2, consumer3};	
		
		for(int i=0; i<consumers.length; i++){
			assertNull((consumers[i]).takeTurn(documents));
			assertEquals( ints[i], consumers[i].getPayoff());
			assertEquals( docs[i], consumers[i].getLikedDocs(0));
		}
	}

	@Test
	public void testCalculatePayoff() {
		consumer1.calculatePayoff(new ArrayList<Document>());
		assertEquals(0,consumer1.getPayoff());
		consumer1.calculatePayoff(documents);
		assertEquals(1,consumer1.getPayoff());
		consumer2.calculatePayoff(documents);
		assertEquals(2,consumer2.getPayoff());
	}

	@Test
	public void testEqualsObject() {
		Consumer testcon = new Consumer(1,new HipsterSearch());
		testcon.setTag("test");
		assertFalse(consumer1.equals(consumer2));
		assertTrue(consumer1.equals(consumer1));;
		assertFalse(consumer1.equals(null));
		assertFalse(consumer1.equals(p));
		assertTrue(consumer1.equals(testcon));
		consumer1.takeTurn(documents);
		assertFalse(consumer1.equals(testcon));
	}

	@Test
	public void testConsumer() {
		assertEquals(1, consumer1.getID());
		assertEquals(new HipsterSearch().getClass(), consumer1.getSearchMethod().getClass());
		assertEquals(2, consumer2.getID());
		assertEquals(new DumbSearch().getClass(), consumer2.getSearchMethod().getClass());
	}

	@Test
	public void testToString() {
		assertEquals("Consumer with ID: 1 and tag: test\nFollowing 0 people\nFollowed By 0 people\nLikes 0 documents\n",consumer1.toString());
		assertEquals("Consumer with ID: 2 and tag: table\nFollowing 0 people\nFollowed By 0 people\nLikes 0 documents\n",consumer2.toString());
	}

}
