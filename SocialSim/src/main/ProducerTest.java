package main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ProducerTest {

	private Producer p1;
	private Producer p2;
	private Producer p3;
	private Document d1;
	private Document d2;
	private Document d3;
	private Document d4;
	private Document d5;
	private Document d6;
	private List<Document> docs;
	
	@Before
	public void setUp(){
		p1 = new Producer(1, new HipsterSearch());
		p2 = new Producer(2, new DumbSearch());
		p3 = new Producer(3, new PopularitySearch());
		p1.setTag("test");
		p2.setTag("table");
		d1 = new Document("test","test",p1);
		d2 = new Document("table","table",p2);
		d3 = new Document("table2","table",p2);
		docs = new ArrayList<>();
		docs.add(d1);
		docs.add(d2);
		docs.add(d3);
		d4 = p1.produceDocument("test2");
		d5 = p1.produceDocument("test3");
		d6 = p1.produceDocument("test4");
		
	}
	
	@Test
	public void testTakeTurn() {
		Document doc = p1.takeTurn(docs);
		assertEquals(doc, p1.getUploadedDocument(3));
		assertEquals(doc, p1.getLikedDocs(3));
		assertEquals(d1, p1.getLikedDocs(4));
		assertEquals(p1, d1.getLikers(0));
		assertEquals(p1, doc.getLikers(0));
		assertEquals(0,p1.getFollowing().size());
		Producer pTestTurn = new Producer(5, new HipsterSearch());
		Document doc2 = pTestTurn.takeTurn(docs);
		
	}

	@Test
	public void testCalculatePayoff() {
		p1.calculatePayoff(docs);
		assertEquals(1,p1.getPayoff());
		p1.calculatePayoff(docs);
		assertEquals(2,p1.getPayoff());
		p2.calculatePayoff(docs);
		assertEquals(2,p2.getPayoff());
		p3.calculatePayoff(docs);
		assertEquals(0,p3.getPayoff());
	}

	@Test
	public void testEqualsObject() {
		Producer ptest = new Producer(3, new PopularitySearch());
		assertTrue(p1.equals(p1));
		assertFalse(p1.equals(p2));
		assertFalse(p1.equals(null));
		assertTrue(p3.equals(ptest));
	}

	@Test
	public void testProducer() {
		assertEquals("NO_TAG",p3.getTag());
		assertEquals(3,p3.getID());
		assertEquals((new PopularitySearch()).getClass(),p3.getSearchMethod().getClass());
	}

	@Test
	public void testProduceDocument() {
		assertEquals(3,p1.getUploadedDocumentSize());
		assertEquals(d4,p1.getUploadedDocument(0));
		Document doc2 = p1.produceDocument("test2");
		assertEquals(doc2, p1.getUploadedDocument(3));
		assertEquals(4,p1.getUploadedDocumentSize());

	}

	@Test
	public void testToString() {
		assertEquals("Producer with ID: 2 and tag: table\nFollowing 0 people\nFollowed By 0 people\nLikes 0 documents\nUploaded 0 documents\n",p2.toString());
		assertEquals("Producer with ID: 1 and tag: test\nFollowing 0 people\nFollowed By 0 people\nLikes 3 documents\nUploaded 3 documents\n",p1.toString());
		p1.produceDocument("test5");
		p1.likeDoc(d1);
		assertEquals("Producer with ID: 1 and tag: test\nFollowing 0 people\nFollowed By 0 people\nLikes 5 documents\nUploaded 4 documents\n",p1.toString());
	}

	@Test
	public void testGetUploadedDocuments() {
		assertEquals(3, p1.getUploadedDocuments().size());
		assertEquals(0, p2.getUploadedDocuments().size());
		assertTrue(p1.getUploadedDocuments().contains(d4));
		assertTrue(p1.getUploadedDocuments().contains(d5));
		assertTrue(p1.getUploadedDocuments().contains(d6));
		
	}

	@Test
	public void testGetUploadedDocument() {
		assertEquals(null, p2.getUploadedDocument(0));
		assertEquals(d4, p1.getUploadedDocument(0));
		assertEquals(d5, p1.getUploadedDocument(1));
		assertEquals(d6, p1.getUploadedDocument(2));
	}

	@Test
	public void testSetUploadedDocuments() {
		p1.setUploadedDocuments(docs);
		assertEquals(docs, p1.getUploadedDocuments());
		p1.setUploadedDocuments(null);
		assertEquals(null, p1.getUploadedDocuments());
	}

	@Test
	public void testSetUploadedDocument() {
		assertNotEquals(d1, p1.getUploadedDocument(0));
		p1.setUploadedDocument(0, d1);
		assertEquals(d1, p1.getUploadedDocument(0));
		p1.setUploadedDocument(1, null);
		assertEquals(d4, p1.getUploadedDocument(1));
	}

	@Test
	public void testGetUploadedDocumentSize() {
		assertEquals(3,p1.getUploadedDocumentSize());
		assertEquals(0,p2.getUploadedDocumentSize());
		assertEquals(0,p3.getUploadedDocumentSize());
		p1.produceDocument("testing");
		p2.produceDocument("testing");
		assertEquals(4,p1.getUploadedDocumentSize());
		assertEquals(1,p2.getUploadedDocumentSize());
		assertEquals(0,p3.getUploadedDocumentSize());
	}

}
