package main;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DocumentTest {

	private Producer p;
	private Consumer c;
	private Document d;
	private Document d2;
	
	@Before
	public void setUp(){
		p = new Producer(1, new PopularitySearch());
		c = new Consumer(2, new HipsterSearch());
		c.setTag("test");
		p.setTag("Orange");
		d = new Document("test","test",p);
		d2 = new Document("apple","apple",p);
	}
	
	@After
	public void tearDown(){
		p = null;
		c = null;
		d = null;
		d2 = null;
	}
	
	@Test
	public void testDocument() {
		assertEquals(d,new Document("test","test",p));
		assertNotNull(new Document("apple","orange",p));
	}

	@Test
	public void testLikeDocument() {
		d.likeDocument(p);
		assertEquals(d.getLikers(0),p);
		d.likeDocument(p);
		assertEquals(d.getLikers().size(),1);
		d.likeDocument(c);
		assertEquals(d.getLikers(1),c);
		d2.likeDocument(c);
		assertEquals(d2.getLikers(0),c);
	}

	@Test
	public void testToString() {
		assertEquals(d.toString(),"Name: test ProducerID: 1 Tag: test Number of likes: 0");
		d.likeDocument(p);
		assertEquals(d.toString(),"Name: test ProducerID: 1 Tag: test Number of likes: 1");
	}

	@Test
	public void testCompareTo() {
		assertEquals(d.compareTo(d), 0);
		assertEquals(d.compareTo(d2), 0);
		d.likeDocument(p);
		assertEquals(d.compareTo(d), 0);
		assertEquals(d.compareTo(d2), 1);
		assertEquals(d2.compareTo(d), -1);
	}

	@Test
	public void testEqualsObject() {
		assertTrue(d.equals(d));
		assertFalse(d.equals(d2));
		assertTrue(d2.equals(new Document("apple","apple",p)));
	}

	@Test
	public void testGetLikers() {
		Document dtest = new Document("test2","test2",p);
		assertEquals(dtest.getLikers(),d.getLikers());
		assertEquals(d.getLikers(),d2.getLikers());
		d.likeDocument(c);
		assertNotEquals(d.getLikers(), dtest.getLikers());
		d2.likeDocument(p);
		assertNotEquals(d.getLikers(), d2.getLikers());
		dtest.likeDocument(c);
		System.out.println(c);
		assertEquals(d.getLikers(), dtest.getLikers());
		assertEquals(d.getLikers(),d.getLikers());
	}

	@Test
	public void testGetLikersInt() {
		assertNull(d.getLikers(0));
		d.likeDocument(c);
		assertEquals(d.getLikers(0),c);
		d.likeDocument(p);
		assertEquals(d.getLikers(1),p);
		assertNull(d.getLikers(2));
	}

	@Test
	public void testSetLikersListOfUser() {
		d.likeDocument(c);
		d2.setLikers(d.getLikers());
		assertEquals(d.getLikers(), d2.getLikers());
		d2.setLikers(null);
		assertNull(d2.getLikers());
	}

	@Test
	public void testSetLikersIntUser() {
		d.likeDocument(c);
		d.setLikers(2,c);
		assertEquals(2, d.getLikers().size());
		d.setLikers(1,p);
		assertEquals(3, d.getLikers().size());
	}

	@Test
	public void testGetTag() {
		assertEquals("test",d.getTag());
		assertEquals("apple",d2.getTag());
	}

	@Test
	public void testSetTag() {
		assertEquals("test",d.getTag());
		d.setTag(null);
		assertNull(d.getTag());
		d.setTag("testing");
		assertEquals("testing",d.getTag());
		
	}

	@Test
	public void testGetName() {
		assertEquals("test",d.getName());
		assertEquals("apple",d2.getName());
	}

	@Test
	public void testSetName() {
		d.setName(null);
		assertNull(d.getName());
		d.setName("Timmy");
		assertEquals("Timmy",d.getName());
	}

	@Test
	public void testGetProducer() {
		assertEquals(p,d.getProducer());
		assertEquals(p,d2.getProducer());
	}

	@Test
	public void testSetProducerID() {
		Producer p2 = new Producer(3, new DumbSearch());
		d.setProducerID(null);
		assertNull(d.getProducer());
		d.setProducerID(p2);
		assertEquals(p2,d.getProducer());
	}

}
