package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.Consumer;
import main.Document;
import main.Producer;
import main.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Search.HipsterSearch;
import Search.PopularitySearch;
import Search.Search;

/**
 * Group: MyNiftyJavaRepo
 * @author Noah Segal
 * Unit Test for the User Class
 */

public class UserTest {
	
	private Search s1;
	
	private Producer p1;
	private Producer p2;
	
	private Consumer c1;
	private Consumer c2;
	private Consumer c3;
	
	private Document d1;
	private Document d2;
	private Document d3;
	private Document d4;
	private Document d5;
	
	private List<Document> dList;
	private List<User> uList1;
	private List<User> uList2;


	@Before
	public void setUp() throws Exception {
		s1 = new PopularitySearch();
		
		p1 = new Producer(0, s1);
		p2 = new Producer(1, s1);
		
		c1 = new Consumer(2, s1);
		c2 = new Consumer(3, s1);
		c3 = new Consumer(4, s1);
		
		d1 = new Document("d1", "StarWars", p1);
		d2 = new Document("d2", "LOTR", p2);
		d3 = new Document("d3", "StarWars", p1);
		d4 = new Document("d4", "LOTR", p2);
		d5 = new Document("d5", "StarWars", p1);
		
		dList = new ArrayList<Document>();
		dList.add(d1);
		dList.add(d2);
		dList.add(d3);
		
		uList1 = new ArrayList<User>();
		uList2 = new ArrayList<User>();
	}

	@After
	public void tearDown() throws Exception {
		s1 = null;
		
		p1 = null;
		p2 = null;
		
		c1 = null;
		c2 = null;
		c3 = null;
		
		d1 = null;
		d2 = null;
		d3 = null;
		d4 = null;
		d5 = null;
		
		dList = null;
		
		uList1 = null;
		uList2 = null;
	}


	@Test
	public void testAddFollower() {
		// Producer
		p1.addFollower(c1);
		assertEquals("Producer p1 followed by one User", 1, p1.getNumberOfFollowers());
		assertTrue("Producer p1 followed by Consumer c1", p1.getFollowers().contains(c1));
		assertTrue("Consumer c1 is following Producer p1", c1.getFollowing().contains(p1));
		
		p1.addFollower(p2);
		assertEquals("Producer p1 followed by two Users", 2, p1.getNumberOfFollowers());
		assertTrue("Producer p1 followed by Producer p2", p1.getFollowers().contains(p2));
		assertTrue("Producer p2 is following Producer p1", p2.getFollowing().contains(p1));
		
		p1.addFollower(p1);
		assertEquals("Producer p1 cannot follow itself", 2, p1.getNumberOfFollowers());
		assertFalse("Producer p1 isn't followed by itself", p1.getFollowers().contains(p1));
		assertFalse("Producer p1 isn't following itself", p1.getFollowing().contains(p1));
		
		p1.addFollower(c1);
		assertEquals("Consumer c1 cannot follow Producer p1 again", 2, p1.getNumberOfFollowers());
		
		p1.addFollower(null);
		assertEquals("Producer p1 cannot be followed by null", 2, p1.getNumberOfFollowers());
		
		// Consumer
		c2.addFollower(c1);
		assertEquals("Consumer c2 followed by one User", 1, c2.getNumberOfFollowers());
		assertTrue("Consumer c2 followed by Consumer c1", c2.getFollowers().contains(c1));
		assertTrue("Consumer c1 is following Consumer c2", c1.getFollowing().contains(c2));
		
		c2.addFollower(p1);
		assertEquals("Consumer c2 followed by two Users", 2, c2.getNumberOfFollowers());
		assertTrue("Consumer c2 followed by Producer p1", c2.getFollowers().contains(p1));
		assertTrue("Producer p2 is following Consumer c2", p1.getFollowing().contains(c2));
		
		c2.addFollower(c2);
		assertEquals("Consumer c1 cannot follow itself", 2, c2.getNumberOfFollowers());
		assertFalse("Consumer c2 isn't followed by itself", c2.getFollowers().contains(c2));
		assertFalse("Consumer c2 isn't following itself", c2.getFollowing().contains(c2));
		
		c2.addFollower(c1);
		assertEquals("Consumer c1 cannot follow Consumer c2 again", 2, c2.getNumberOfFollowers());
		
		c2.addFollower(null);
		assertEquals("Consumer c2 cannot be followed by null", 2, c2.getNumberOfFollowers());
	}

	@Test
	public void testFollowUser() {
		// Producer
		p1.followUser(c1);
		assertEquals("Producer p1 following one User", 1, p1.getNumberOfFollowing());
		assertTrue("Producer p1 is following Consumer c1", p1.getFollowing().contains(c1));
		assertTrue("Consumer c1 followed byProducer p1", c1.getFollowers().contains(p1));
		
		p1.followUser(p2);
		assertEquals("Producer p1 following two Users", 2, p1.getNumberOfFollowing());
		assertTrue("Producer p1 is following Producer p2", p1.getFollowing().contains(p2));
		assertTrue("Producer p2 followed by Producer p1", p2.getFollowers().contains(p1));
		
		p1.followUser(p1);
		assertEquals("Producer p1 cannot follow itself", 2, p1.getNumberOfFollowing());
		assertFalse("Producer p1 isn't following itself", p1.getFollowers().contains(p1));
		assertFalse("Producer p1 isn't followed by itself", p1.getFollowing().contains(p1));
		
		p1.followUser(c1);
		assertEquals("Producer p1 cannot follow Consumer c1 again", 2, p1.getNumberOfFollowing());
		
		p1.followUser(null);
		assertEquals("Producer p1 cannot follow null", 2, p1.getNumberOfFollowing());
		
		// Consumer
		c2.followUser(c1);
		assertEquals("Consumer c2 following one User", 1, c2.getNumberOfFollowing());
		assertTrue("Consumer c2 is following Consumer c1", c2.getFollowing().contains(c1));
		assertTrue("Consumer c1 followed by Consumer c2", c1.getFollowers().contains(c2));
		
		c2.followUser(p1);
		assertEquals("Consumer c2 following two Users", 2, c2.getNumberOfFollowing());
		assertTrue("Consumer c2 is following Producer p1", c2.getFollowing().contains(p1));
		assertTrue("Producer p2 followed by Consumer c2", p1.getFollowers().contains(c2));
		
		c2.followUser(c2);
		assertEquals("Consumer c1 cannot follow itself", 2, c2.getNumberOfFollowing());
		assertFalse("Consumer c2 isn't following itself", c2.getFollowing().contains(c2));
		assertFalse("Consumer c2 isn't followed by itself", c2.getFollowers().contains(c2));
		
		c2.followUser(c1);
		assertEquals("Consumer c2 cannot follow Consumer c1 again", 2, c2.getNumberOfFollowing());
		
		c2.addFollower(null);
		assertEquals("Consumer c2 cannot follow null", 2, c2.getNumberOfFollowing());
	}

	@Test
	public void testLikeDoc() {
		// Producer
		p1.likeDoc(d1);
		assertEquals("Producer p1 likes one Document", 1, p1.getNumberOfLikedDocs());
		assertTrue("Producer p1 likes Document d1, which it created", p1.getLikedDocs().contains(d1));
		
		p1.likeDoc(d2);
		assertEquals("Producer p1 likes two Documents", 2, p1.getNumberOfLikedDocs());
		assertTrue("Producer p1 likes Document d2, which it didn't create", p1.getLikedDocs().contains(d2));
		
		p1.likeDoc(d1);
		assertEquals("Producer p1 cannot like previously liked Document d1", 2, p1.getNumberOfLikedDocs());
		
		p1.likeDoc(null);
		assertEquals("Producer p1 cannot like a null Document", 2, p1.getNumberOfLikedDocs());
		
		// Consumer
		c1.likeDoc(d1);
		assertEquals("Consumer c1 likes one Document", 1, c1.getNumberOfLikedDocs());
		assertTrue("Consumer c1 likes Document d1", c1.getLikedDocs().contains(d1));
		
		c1.likeDoc(d2);
		assertEquals("Consumer c1 likes two Documents", 2, c1.getNumberOfLikedDocs());
		assertTrue("Consumer c1 likes Document d2", c1.getLikedDocs().contains(d2));
		
		c1.likeDoc(d1);
		assertEquals("Consumer c1 cannot like previously liked Document d1", 2, c1.getNumberOfLikedDocs());
		
		c1.likeDoc(null);
		assertEquals("Consumer c1 cannot like a null Document", 2, c1.getNumberOfLikedDocs());
		
	}

	@Test
	public void testLikeDocsFollowUsers() {
		p1.setTag("StarWars");
		c1.setTag("LOTR");
		
		c2.likeDoc(d1);
		c3.likeDoc(d2);
		p2.likeDoc(d3);
		
		assertEquals("Document List dList contains three documents", 3, dList.size() );
		
		// Producer
		p1.likeDocsFollowUsers(dList, p1.getTag());
		assertEquals("Producer p1 likes two of the three documents", 2, p1.getNumberOfLikedDocs());
		assertEquals("Producer p1 follows two Users", 2, p1.getNumberOfFollowing());
		
		assertTrue("Producer p1 follows Consumer c1", p1.getFollowing().contains(c2));
		assertTrue("Producer p1 follows Producer p2", p1.getFollowing().contains(p2));
		
		assertTrue("Consumer c2 followed by Producer p1", c2.getFollowers().contains(p1));
		assertTrue("Producer p2 followed by Producer p1", p2.getFollowers().contains(p1));
		
		
		// Consumer
		c1.likeDocsFollowUsers(dList, c1.getTag());
		assertEquals("Consumer c1 likes one of the three documents", 1, c1.getNumberOfLikedDocs());
		assertEquals("Consumer c1 follows two Users", 2, c1.getNumberOfFollowing());
		
		assertTrue("Consumer c1 follows Producer p2", c1.getFollowing().contains(p2));
		assertTrue("Consumer c1 follows Consumer c3", c1.getFollowing().contains(c3));
		
		assertTrue("Producer p2 followed by Consumer c1", p2.getFollowers().contains(c1));
		assertTrue("Consumer c3 followed by Consumer c1", c3.getFollowers().contains(c1));
	}

	@Test
	public void testListOfFollowers() {
		// Producer
		c1.followUser(p1);
		p2.followUser(p1);
		c2.followUser(p1);
		
		ArrayList<String> followers = p1.listOfFollowers();
		
		assertTrue("Producer p1's list of Followers contains Consumer c1", followers.contains("Consumer: 2"));
		assertTrue("Producer p1's list of Followers contains Producer p2", followers.contains("Producer: 1"));
		assertTrue("Producer p1's list of Followers contains Consumer c2", followers.contains("Consumer: 3"));
		
		// Consumer
		p1.followUser(c1);
		c2.followUser(c1);
		p2.followUser(c1);
		
		followers = c1.listOfFollowers();
		
		assertTrue("Consumer c1's list of Followers contains Producer p1", followers.contains("Producer: 0"));
		assertTrue("Consumer c1's list of Followers contains Consumer c2", followers.contains("Consumer: 3"));
		assertTrue("Consumer c1's list of Followers contains Producer p2", followers.contains("Producer: 1"));
	}

	@Test
	public void testListOfFollowing() {
		// Producer
		p1.followUser(c1);
		p1.followUser(c2);
		p1.followUser(p1);
		p1.followUser(p2);
		
		ArrayList<String> following = p1.listOfFollowing();
		
		assertTrue("Producer p1's list of Following contains Consumer c1", following.contains("Consumer: 2"));
		assertTrue("Producer p1's list of Following contains Consumer c2", following.contains("Consumer: 3"));
		assertFalse("Producer p1's list of Following doesn't contain Producer p1", following.contains("Producer: 0"));
		assertTrue("Producer p1's list of Following contains Producer p2", following.contains("Producer: 1"));
		
		// Consumer	
		c1.followUser(p1);
		c1.followUser(p2);
		c1.followUser(c1);
		c1.followUser(c2);
		
		following = c1.listOfFollowing();
		
		assertTrue("Consumer c1's list of Following contains Producer p1", following.contains("Producer: 0"));
		assertTrue("Consumer c1's list of Following contains Producer p2", following.contains("Producer: 1"));
		assertFalse("Consumer c1's list of Following doesn't contains Consumer c1", following.contains("Consumer: 2"));
		assertTrue("Consumer c1's list of Following contains Consumer c2", following.contains("Consumer: 3"));
		
	}

	@Test
	public void testEqualsObject() {
		// Producer
		Producer p1Copy = new Producer(0, s1);
		assertTrue("Producer p1 equals itself", p1.equals(p1));
		assertTrue("Producer p1 equals Producer p1Copy", p1.equals(p1Copy));
		assertFalse("Producer p1 doesn't equal Producer p2", p1.equals(p2));
		assertFalse("Producer p1 doesn't equal Consumer c1", p1.equals(c1));
		assertFalse("Producer p1 doesn't equals null", p1.equals(null));
		
		// Consumer
		Consumer c1Copy = new Consumer(2, s1);
		assertTrue("Consumer c1 equals itself", c1.equals(c1));
		assertTrue("Consumer c1 equals Consumer c1Copy", c1.equals(c1Copy));
		assertFalse("Consumer c1 doesn't equal Consumer c2", c1.equals(c2));
		assertFalse("Consumer c1 doesn't equal Producer c1", c1.equals(p1));
		assertFalse("Consumer c1 doesn't equal null", c1.equals(null));
	}

	@Test
	public void testGetTag() {
		assertEquals("Producer p1's tag is \"NO_TAG\"", "NO_TAG", p1.getTag());
		assertEquals("Consumer c1's tag is \"NO_TAG\"", "NO_TAG", c1.getTag());
	}

	@Test
	public void testSetTag() {
		p1.setTag("Leeeeeerooooyyy");
		c1.setTag("Jennnnnnkins!!!");
		
		assertEquals("Producer p1's new tag", "Leeeeeerooooyyy", p1.getTag());
		assertEquals("Consumer c1's new tag", "Jennnnnnkins!!!", c1.getTag());
	}

	@Test
	public void testGetFollowers() {
		// Producer
		assertTrue("Producer p1 followed by no Users", p1.getFollowers().isEmpty());
		
		c1.followUser(p1);
		c2.followUser(p1);
		c3.followUser(p1);
		
		uList1 = p1.getFollowers();
		assertTrue("Producer p1 followed by Consumer c1", uList1.contains(c1));
		assertTrue("Producer p1 followed by Consumer c2", uList1.contains(c2));
		assertTrue("Producer p1 followed by Consumer c3", uList1.contains(c3));
		
		// Consumer
		assertTrue("Consumer c1 followed by no Users", c1.getFollowers().isEmpty());
		
		p1.followUser(c1);
		p2.followUser(c1);
		c2.followUser(c1);
		
		uList2 = c1.getFollowers();
		assertTrue("Consumer c1 followed by Producer p1", uList2.contains(p1));
		assertTrue("Consumer c1 followed by Producer p2", uList2.contains(p2));
		assertTrue("Consumer c1 followed by Consumer c2", uList2.contains(c2));
	}

	@Test
	public void testGetFollowersInt() {
		// Producer
		c1.followUser(p1);
		c2.followUser(p1);
		c3.followUser(p1);
		
		assertNull("Cannot get following User at location -1 (out of range)", p1.getFollowers(-1));
		assertEquals("Producer p1 followed by Consumer c1", c1, p1.getFollowers(0));
		assertEquals("Producer p1 followed by Consumer c2", c2, p1.getFollowers(1));
		assertEquals("Producer p1 followed by Consumer c3", c3, p1.getFollowers(2));
		assertNull("Cannot get following User at location 3 (out of range)", p1.getFollowers(3));
		
		// Consumer
		p1.followUser(c1);
		p2.followUser(c1);
		c2.followUser(c1);
		
		assertNull("Cannot get following User at location -1 (out of range)", p1.getFollowers(-1));
		assertEquals("Consumer c1 followed by Producer p1", p1, c1.getFollowers(0));
		assertEquals("Consumer c1 followed by Producer p2", p2, c1.getFollowers(1));
		assertEquals("Consumer c1 followed by Consumer c2", c2, c1.getFollowers(2));
		assertNull("Cannot get following User at location 3 (out of range)", p1.getFollowers(3));
	}

	@Test
	public void testSetFollowersListOfUser() {
		// Producer
		uList1.add(p2);
		uList1.add(c2);
		uList1.add(c3);
		
		p1.setFollowers(uList1);
		assertEquals("Producer p1 followed by three Users", 3, p1.getNumberOfFollowers());
		assertTrue("Producer p1 followed by Producer p2", p1.getFollowers().contains(p2));
		assertTrue("Producer p1 followed by Consumer c2", p1.getFollowers().contains(c2));
		assertTrue("Producer p1 followed by Consumer c3", p1.getFollowers().contains(c3));
		
		// Consumer
		uList2.add(p2);
		uList2.add(c2);
		uList2.add(c3);
		
		c1.setFollowers(uList2);
		assertEquals("Consumer c1 followed by three Users", 3, c1.getNumberOfFollowers());
		assertTrue("Consumer c1 followed by Producer p2", c1.getFollowers().contains(p2));
		assertTrue("Consumer c1 followed by Consumer c2", c1.getFollowers().contains(c2));
		assertTrue("Consumer c1 followed by Consumer c3", c1.getFollowers().contains(c3));
	}

	@Test
	public void testSetFollowersIntUser() {
		// Producer
		uList1.add(p2);
		uList1.add(c2);
		
		p1.setFollowers(uList1);
		p1.setFollowers(1, c3);
		assertEquals("Producer p1 followed by three Users", 3, p1.getNumberOfFollowers());
		assertEquals("Producer p1 followed by Consumer c3 at index 1", c3, p1.getFollowers().get(1));
		assertEquals("Producer p1 followed by Consumer c2, moved to index 2", c2, p1.getFollowers().get(2));
		
		p1.setFollowers(2, c3);
		assertEquals("Producer p1 still followed by Consumer c3 at index 1", c3, p1.getFollowers().get(1));
		assertEquals("Producer p1 still followed by Consumer c2 at index 2", c2, p1.getFollowers().get(2));
		
		
		p1.setFollowers(1, null);
		assertEquals("Producer p1 still followed by three Users", 3, p1.getNumberOfFollowers());
		
		p1.setFollowers(-1, c1);
		assertEquals("Producer p1 still followed by three Users", 3, p1.getNumberOfFollowers());
		assertFalse("Producer p1 not followed by Consumer c1", p1.getFollowers().contains(c1));
		
		p1.setFollowers(3, c1);
		assertEquals("Producer p1 still followed by three Users", 3, p1.getNumberOfFollowers());
		assertFalse("Producer p1 not followed by Consumer c1", p1.getFollowers().contains(c1));
		
		// Consumer	
		uList2.add(p2);
		uList2.add(c2);
		
		c1.setFollowers(uList2);
		c1.setFollowers(1, c3);
		assertEquals("Consumer c1 followed by three Users", 3, c1.getNumberOfFollowers());
		assertEquals("Consumer c1 followed by Consumer c3 at index 1", c3, c1.getFollowers().get(1));
		assertEquals("Consumer c1 followed by Consumer c2, moved to index 2", c2, c1.getFollowers().get(2));
		
		c1.setFollowers(2, c3);
		assertEquals("Consumer c1 still followed by Consumer c3 at index 1", c3, c1.getFollowers().get(1));
		assertEquals("Consumer c1 still followed by Consumer c2 at index 2", c2, c1.getFollowers().get(2));

		
		c1.setFollowers(1, null);
		assertEquals("Consumer c1 still followed by three Users", 3, c1.getNumberOfFollowers());
		
		c1.setFollowers(-1, p1);
		assertEquals("Consumer c1 still followed by three Users", 3, c1.getNumberOfFollowers());
		assertFalse("Consumer c1 not followed by Producer p1", c1.getFollowers().contains(p1));
		
		c1.setFollowers(3, p1);
		assertEquals("Consumer c1 still followed by three Users", 3, c1.getNumberOfFollowers());
		assertFalse("Consumer c1 not followed by Producer p1", c1.getFollowers().contains(p1));
	}

	@Test
	public void testGetNumberOfFollowers() {
		// Producer
		assertEquals("Producer p1 followed by no Users", 0, p1.getNumberOfFollowers());
		p2.followUser(p1);
		assertEquals("Producer p1 followed by one User", 1, p1.getNumberOfFollowers());
		c2.followUser(p1);
		assertEquals("Producer p1 followed by two Users", 2, p1.getNumberOfFollowers());
		
		// Consumer
		assertEquals("Consumer c1 followed by no Users", 0, c1.getNumberOfFollowers());
		p2.followUser(c1);
		assertEquals("Consumer c1 followed by one User", 1, c1.getNumberOfFollowers());
		c2.followUser(c1);
		assertEquals("Consumer c1 followed by two Users", 2, c1.getNumberOfFollowers());
	}

	@Test
	public void testGetFollowing() {
		// Producer
		assertTrue("Producer p1 following no Users", p1.getFollowing().isEmpty());
		
		p1.followUser(p2);
		p1.followUser(c2);
		
		uList1 = p1.getFollowing();
		assertTrue("Producer p1 following Producer p2", uList1.contains(p2));
		assertTrue("Producer p1 following Consumer c2", uList1.contains(c2));
		
		
		// Consumer
		assertTrue("Consumer c1 following no Users", c1.getFollowing().isEmpty());
		
		c1.followUser(p2);
		c1.followUser(c2);
		
		uList2 = p1.getFollowing();
		assertTrue("Consumer c1 following Producer p2", uList2.contains(p2));
		assertTrue("Consumer c1 following Consumer c2", uList2.contains(c2));
	}

	@Test
	public void testGetFollowingInt() {
		// Producer
		p1.followUser(c1);
		p1.followUser(c2);
		p1.followUser(c3);
		
		assertNull("Cannot get User being followed at location -1 (out of range)", p1.getFollowing(-1));
		assertEquals("Producer p1 following Consumer c1", c1, p1.getFollowing(0));
		assertEquals("Producer p1 following Consumer c2", c2, p1.getFollowing(1));
		assertEquals("Producer p1 following Consumer c3", c3, p1.getFollowing(2));
		assertNull("Cannot get User being followed at location 3 (out of range)", p1.getFollowing(3));
		
		// Consumer
		c1.followUser(p1);
		c1.followUser(p2);
		c1.followUser(c2);
		
		assertNull("Cannot get USer being followed at location -1 (out of range)", c1.getFollowing(-1));
		assertEquals("Consumer c1 followig Producer p1", p1, c1.getFollowing(0));
		assertEquals("Consumer c1 followig Producer p2", p2, c1.getFollowing(1));
		assertEquals("Consumer c1 followig Consumer c2", c2, c1.getFollowing(2));
		assertNull("Cannot get USer being followed at location 3 (out of range)", c1.getFollowing(3));
	}

	@Test
	public void testSetFollowingListOfUser() {		
		// Producer
		uList1.add(p2);
		uList1.add(c2);
		uList1.add(c3);
		
		p1.setFollowing(uList1);
		assertEquals("Producer p1 following three Users", 3, p1.getNumberOfFollowing());
		assertTrue("Producer p1 following Producer p2", p1.getFollowing().contains(p2));
		assertTrue("Producer p1 following Consumer c2", p1.getFollowing().contains(c2));
		assertTrue("Producer p1 following Consumer c3", p1.getFollowing().contains(c3));
		
		// Consumer
		uList2.add(p2);
		uList2.add(c2);
		uList2.add(c3);
		
		c1.setFollowing(uList2);
		assertEquals("Consumer c1 following three Users", 3, c1.getNumberOfFollowing());
		assertTrue("Consumer c2 following Producer p2", c1.getFollowing().contains(p2));
		assertTrue("Consumer c2 following Consumer c2", c1.getFollowing().contains(c2));
		assertTrue("Consumer c2 following Consumer c3", c1.getFollowing().contains(c3));
	}

	@Test
	public void testSetFollowingIntUser() {
		// Producer
		uList1.add(p2);
		uList1.add(c2);
		
		p1.setFollowing(uList1);
		p1.setFollowing(1, c3);
		assertEquals("Producer p1 following three Users", 3, p1.getNumberOfFollowing());
		assertEquals("Producer p1 following Consumer c3 at index 1", c3, p1.getFollowing().get(1));
		
		p1.setFollowing(1, null);
		assertEquals("Producer p1 still followed by three Users", 3, p1.getNumberOfFollowing());
		
		p1.setFollowing(-1, c1);
		assertEquals("Producer p1 still followed by three Users", 3, p1.getNumberOfFollowing());
		assertFalse("Producer p1 not following Consumer c1", p1.getFollowing().contains(c1));
		
		p1.setFollowing(3, c1);
		assertEquals("Producer p1 still following three Users", 3, p1.getNumberOfFollowing());
		assertFalse("Producer p1 not following Consumer c1", p1.getFollowing().contains(c1));
		
		// Consumer
		uList2.add(p2);
		uList2.add(c2);
		
		c1.setFollowing(uList2);
		c1.setFollowing(1, c3);
		assertEquals("Consumer c1 following three Users", 3, c1.getNumberOfFollowing());
		assertEquals("Consumer c1 following Consumer c3 at index 1", c3, c1.getFollowing().get(1));
		
		c1.setFollowing(1, null);
		assertEquals("Consumer c1 still following three Users", 3, c1.getNumberOfFollowing());
		
		c1.setFollowing(-1, p1);
		assertEquals("Consumer c1 still following three Users", 3, c1.getNumberOfFollowing());
		assertFalse("Consumer c1 not following Producer p1", c1.getFollowing().contains(p1));
		
		c1.setFollowing(3, p1);
		assertEquals("Consumer c1 still following three Users", 3, c1.getNumberOfFollowing());
		assertFalse("Consumer c1 not following Producer p1", c1.getFollowing().contains(p1));
	}

	@Test
	public void testGetNumberOfFollowing() {
		// Producer
		assertEquals("Producer p1 following no Users", 0, p1.getNumberOfFollowing());
		p1.followUser(p2);
		assertEquals("Producer p1 following one User", 1, p1.getNumberOfFollowing());
		p1.followUser(c2);
		assertEquals("Producer p1 following two Users", 2, p1.getNumberOfFollowing());
		
		// Consumer
		assertEquals("Consumer c1 following no Users", 0, c1.getNumberOfFollowing());
		c1.followUser(p2);
		assertEquals("Consumer c1 following one User", 1, c1.getNumberOfFollowing());
		c1.followUser(c2);
		assertEquals("Consumer c1 following two Users", 2, c1.getNumberOfFollowing());
	}

	@Test
	public void testGetLikedDocs() {
		List<Document> dListTemp;
		
		// Producer
		assertTrue("Producer p1 has no liked Documents", p1.getLikedDocs().isEmpty());
		p1.setLikedDocs(dList);
		dListTemp = p1.getLikedDocs();
		
		assertTrue("List dListTemp is the same as dList", dListTemp.equals(dList));
		assertTrue("Producer p1 likes Document d1", p1.getLikedDocs().contains(d1));
		assertTrue("Producer p1 likes Document d2", p1.getLikedDocs().contains(d2));
		assertTrue("Producer p1 likes Document d3", p1.getLikedDocs().contains(d3));
		
		// Consumer
		assertTrue("Consumer c1 has no liked Documents", c1.getLikedDocs().isEmpty());
		c1.setLikedDocs(dList);
		dListTemp = c1.getLikedDocs();
		
		assertTrue("List dListTemp is the same as dList", dListTemp.equals(dList));
		assertTrue("Consumer c1 likes Document d1", c1.getLikedDocs().contains(d1));
		assertTrue("Consumer c1 likes Document d2", c1.getLikedDocs().contains(d2));
		assertTrue("Consumer c1 likes Document d3", c1.getLikedDocs().contains(d3));
	}

	@Test
	public void testGetLikedDocsInt() {
		// Producer
		p1.setLikedDocs(dList);
		assertNull("Cannot get Document at index -1 (out of range)", p1.getLikedDocs(-1));
		assertEquals("Producer p1 likes Document d1", d1, p1.getLikedDocs(0));
		assertEquals("Producer p1 likes Document d2", d2, p1.getLikedDocs(1));
		assertEquals("Producer p1 likes Document d3", d3, p1.getLikedDocs(2));
		assertNull("Cannot get Document at index 3 (out of range)", p1.getLikedDocs(3));
		
		// Consumer
		c1.setLikedDocs(dList);
		assertNull("Cannot get Document at index -1 (out of range)", c1.getLikedDocs(-1));
		assertEquals("Consumer c1 likes Document d1", d1, c1.getLikedDocs(0));
		assertEquals("Consumer c1 likes Document d2", d2, c1.getLikedDocs(1));
		assertEquals("Consumer c1 likes Document d3", d3, c1.getLikedDocs(2));
		assertNull("Cannot get Document at index 3 (out of range)", c1.getLikedDocs(3));
	}

	@Test
	public void testSetLikedDocsListOfDocument() {
		// Producer
		p1.setLikedDocs(dList);
		assertEquals("Producer p1 likes three Documents", 3, p1.getNumberOfLikedDocs());
		assertTrue("Producer p1 likes Document d1", p1.getLikedDocs().contains(d1));
		assertTrue("Producer p1 likes Document d2", p1.getLikedDocs().contains(d2));
		assertTrue("Producer p1 likes Document d3", p1.getLikedDocs().contains(d3));	
		
		// Consumer
		c1.setLikedDocs(dList);
		assertEquals("Consumer c1 likes three Documents", 3, c1.getNumberOfLikedDocs());
		assertTrue("Consumer c1 likes Document d1", c1.getLikedDocs().contains(d1));
		assertTrue("Consumer c1 likes Document d2", c1.getLikedDocs().contains(d2));
		assertTrue("Consumer c1 likes Document d3", c1.getLikedDocs().contains(d3));
	}

	@Test
	public void testSetLikedDocsIntDocument() {
		// Producer
		p1.setLikedDocs(dList);
		p1.setLikedDocs(1, d4);
		assertEquals("Producer p1 likes four Documents", 4, p1.getNumberOfLikedDocs());
		assertEquals("Producer p1 likes Document d4 at index 1", d4, p1.getLikedDocs().get(1));
		
		p1.setLikedDocs(1, null);
		assertEquals("Producer p1 still likes four Documents", 4, p1.getNumberOfLikedDocs());
		
		p1.setLikedDocs(-1, d5);
		assertEquals("Producer p1 still likes four Documents", 4, p1.getNumberOfLikedDocs());
		assertFalse("Producer p1 doesn't like Document d5", p1.getLikedDocs().contains(d5));
		
		p1.setLikedDocs(4, d5);
		assertEquals("Producer p1 still likes four Documents", 4, p1.getNumberOfLikedDocs());
		assertFalse("Producer p1 doesn't like Document d5", p1.getLikedDocs().contains(d5));
		
		// Consumer
		c1.setLikedDocs(dList);
		c1.setLikedDocs(1, d4);
		assertEquals("Consumer c1 likes four Documents", 4, c1.getNumberOfLikedDocs());
		assertEquals("Consumer c1 likes Document d4 at index 1", d4, c1.getLikedDocs().get(1));
		
		p1.setLikedDocs(1, null);
		assertEquals("Consumer c1 still likes four Documents", 4, c1.getNumberOfLikedDocs());
		
		p1.setLikedDocs(-1, d5);
		assertEquals("Consumer c1 still likes four Documents", 4, c1.getNumberOfLikedDocs());
		assertFalse("Consumer c1 doesn't like Document d5", c1.getLikedDocs().contains(d5));
		
		p1.setLikedDocs(4, d5);
		assertEquals("Consumer c1 still likes four Documents", 4, c1.getNumberOfLikedDocs());
		assertFalse("Consumer c1 doesn't like Document d5", c1.getLikedDocs().contains(d5));
		
	}

	@Test
	public void testGetNumberOfLikedDocs() {
		// Producer
		assertEquals("Producer p1 likes zero Documents", 0, p1.getNumberOfLikedDocs());
		p1.likeDoc(d1);
		assertEquals("Producer p1 likes one Document", 1, p1.getNumberOfLikedDocs());
		p1.likeDoc(d2);
		assertEquals("Producer p1 likes two Documents", 2, p1.getNumberOfLikedDocs());
		p1.likeDoc(d3);
		assertEquals("Producer p1 likes three Documents", 3, p1.getNumberOfLikedDocs());
		
		// Consumer
		assertEquals("Consumer c1 likes zero Documents", 0, c1.getNumberOfLikedDocs());
		c1.likeDoc(d1);
		assertEquals("Consumer c1 likes one Document", 1, c1.getNumberOfLikedDocs());
		c1.likeDoc(d2);
		assertEquals("Consumer c1 likes two Documents", 2, c1.getNumberOfLikedDocs());
		c1.likeDoc(d3);
		assertEquals("Consumer c1 likes three Documents", 3, c1.getNumberOfLikedDocs());
	}

	@Test
	public void testGetSearchMethod() {
		// Producer
		assertEquals("Producer p1 has Search method s1", s1, p1.getSearchMethod());
		
		// Consumer
		assertEquals("Consumer c1 has Search method s1", s1, c1.getSearchMethod());
		
	}

	@Test
	public void testSetSearchMethod() {
		Search sTemp = new HipsterSearch();
		
		// Producer
		p1.setSearchMethod(sTemp);
		assertEquals("Producer p1's new Search method is sTemp", sTemp, p1.getSearchMethod());
		
		// Consumer
		c1.setSearchMethod(sTemp);
		assertEquals("Consumer c1's new Search method is sTemp", sTemp, c1.getSearchMethod());
		
	}

	@Test
	public void testGetPayoff() {
		// Producer
		assertEquals("Producer p1 has an initial Payoff of zero", 0, p1.getPayoff());
		
		// Consumer
		assertEquals("Consumer c1 has an initial Payoff of zero", 0, c1.getPayoff());
	}

	@Test
	public void testSetPayoff() {
		// Producer
		p1.setPayoff(5);
		assertEquals("Producer p1 has a new Payoff of five", 5, p1.getPayoff());

		// Consumer
		c1.setPayoff(10);
		assertEquals("Consumer c1 has a new Payoff of ten", 10, c1.getPayoff());
	}

	@Test
	public void testGetID() {
		// Producer
		assertEquals("Producer p1 has an ID of zero", 0, p1.getID());
		
		// Consumer
		assertEquals("Consumer c1 has an ID of 2", 2, c1.getID());
	}

	@Test
	public void testSetID() {
		// Producer
		p1.setID(20);
		assertEquals("Producer p1 has a new ID of twenty", 20, p1.getID());
		
		// Consumer
		c1.setID(30);
		assertEquals("Producer p1 has a new ID of thirty", 30, c1.getID());
	}

}
