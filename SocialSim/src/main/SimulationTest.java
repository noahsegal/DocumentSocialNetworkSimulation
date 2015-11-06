package main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimulationTest {
	Simulation baseSimulation;
	@Before
	public void setUp() throws Exception {
		baseSimulation = new Simulation();
		baseSimulation.setMainWindow(new MainWindow(baseSimulation));
		baseSimulation.startGame(1, 1, 1, 1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimulation() {
		Simulation simTest = new Simulation();
		simTest.setMainWindow(new MainWindow(simTest));
		simTest.startGame(1, 1, 1, 1);
		assertEquals("simTest is the same as baseSimulation", simTest, baseSimulation);
	}

	@Test
	public void testStartGame() {
		assertEquals("The game has one turn", 1, baseSimulation.getNumberOfTurns());
		assertEquals("The game has one tag", 1, baseSimulation.getTags().size());
		assertEquals("The game has one consumer", 1, baseSimulation.getNumberOfConsumers());
		assertEquals("The game has one producer", 1, baseSimulation.getNumberOfProducers());
		assertEquals("The game has no documents", 0, baseSimulation.getDocuments().size());
	}

	@Test
	public void testTakeTurn() {
		int currentTurn = baseSimulation.getCurrentTurn();
		HashMap<User, ArrayList<Integer>> currentPayoffs = baseSimulation.getPayoffs();
		baseSimulation.takeTurn(5);
		assertEquals("The turn has incremented by 1", currentTurn+1, baseSimulation.getCurrentTurn());
		assertNotEquals("The payoffs have changed", currentPayoffs, baseSimulation.getPayoffs());
	}

	@Test
	public void testToString() {
		String s = "Current Standing: \n\nCurrent Contributors:\n";
		s += baseSimulation.getUsers().get(0) + "\n";
		
		s += "Current Documents:\n";
		assertEquals("The game will print its current status", s, baseSimulation.toString());
	}

	@Test
	public void testGetPayoffs() {
		for(User u:baseSimulation.getUsers()){
			assertEquals("Each payoff is 0", baseSimulation.getPayoffs().get(u).indexOf(0), 0);
		}
	}

	@Test
	public void testSetCurrentTurn() {
		baseSimulation.setCurrentTurn(5);
		assertEquals("The current turn is 5", 5, baseSimulation.getCurrentTurn());
	}

	@Test
	public void testGetCurrentTurn() {
		assertEquals("The current turn is 1", 1, baseSimulation.getCurrentTurn());
	}

	@Test
	public void testSetCurrentId() {
		baseSimulation.setCurrentId(5);
		assertEquals("The current id is 5", 5, baseSimulation.getCurrentId());
	}

	@Test
	public void testGetCurrentId() {
		assertEquals("The current turn is 1", 1, baseSimulation.getCurrentTurn());
	}

	@Test
	public void testSetConsumers() {
		baseSimulation.setConsumers(new ArrayList<User>());
		assertNotNull("There are consumers", baseSimulation.getConsumers());
	}

	@Test
	public void testGetConsumers() {
		assertEquals("There is one consumer", baseSimulation.getConsumers().size(), 1);
	}

	@Test
	public void testSetDocumentsListOfDocument() {
		List<Document> testDocs = new ArrayList<Document>();
		Document testDoc = new Document("Test", "Test", new Producer(0, new PopularitySearch()));
		testDocs.add(testDoc);
		baseSimulation.setDocuments(testDocs);
		assertEquals("The first document is testDoc", testDoc, baseSimulation.getDocuments().indexOf(0));
	}

	@Test
	public void testGetDocuments() {
		assertEquals("The documents list is empty", new ArrayList<Document>(), baseSimulation.getDocuments());
	}

	@Test
	public void testSetPayoffs() {
		HashMap<User, ArrayList<Integer>> testPayoffs = new HashMap<User, ArrayList<Integer>>();
		baseSimulation.setPayoffs(testPayoffs);
		assertEquals("The payofss list is empty", testPayoffs, baseSimulation.getPayoffs());
	}

	@Test
	public void testGetTags() {
		assertEquals("There should be 1 tag", 1, baseSimulation.getTags().size());
	}

	@Test
	public void testSetTagsListOfString() {
		List<String> testTags = new ArrayList<String>();
		baseSimulation.setTags(testTags);
		assertEquals("The tags list is set to testTags", testTags, baseSimulation.getTags());
	}

	@Test
	public void testGetNumberOfConsumers() {
		assertEquals("There is 1 consumer", 1, baseSimulation.getNumberOfConsumers());
	}

	@Test
	public void testSetNumberOfConsumers() {
		baseSimulation.setNumberOfConsumers(2);
		assertEquals("There are 2 consumers", 2, baseSimulation.getNumberOfConsumers());
	}

	@Test
	public void testGetNumberOfProducers() {
		assertEquals("There is 1 producer", 1, baseSimulation.getNumberOfProducers());
	}

	@Test
	public void testSetNumberOfProducers() {
		baseSimulation.setNumberOfConsumers(2);
		assertEquals("There are 2 producers", 2, baseSimulation.getNumberOfProducers());
	}

	@Test
	public void testGetNumberOfTurns() {
		assertEquals("There is 1 turn", 1, baseSimulation.getNumberOfTurns());
	}

	@Test
	public void testSetNumberOfTurns() {
		baseSimulation.setNumberOfTurns(5);
		assertEquals("There are 5 turns", 5, baseSimulation.getNumberOfTurns());
	}

	@Test
	public void testSetUser() {
		Consumer p = new Consumer(0, new PopularitySearch());
		baseSimulation.setUser(0, p);
		assertEquals("The first user is p", p, baseSimulation.getUsers().indexOf(0));
		
	}

	@Test
	public void testGetUsers() {
		Producer p = new Producer(0, new PopularitySearch());
		Consumer c = new Consumer(1, new PopularitySearch());
		List<User> users = new ArrayList<User>();
		users.add(p);
		users.add(c);
		assertEquals("There will one producer and one consumer", users, baseSimulation.getUsers());
	}

	@Test
	public void testSetTagsIntString() {
		baseSimulation.setTags(0, "Test");
		assertEquals("The first tag is Test", "Test", baseSimulation.getTags(0));
	}

	@Test
	public void testGetTagsInt() {
		baseSimulation.setTags(0, "Test");
		assertEquals("The first tag is Test", "Test", baseSimulation.getTags(0));
	}

	@Test
	public void testSetDocumentsIntDocument() {
		Document testDoc = new Document("Test", "Test", new Producer(0, new PopularitySearch()));
		baseSimulation.getDocuments().add(testDoc);
		assertEquals("The first doc is testDoc", testDoc, baseSimulation.getDocuments(0));
	}

	@Test
	public void testGetDocumentsInt() {
		Document testDoc = new Document("Test", "Test", new Producer(0, new PopularitySearch()));
		baseSimulation.setDocuments(new ArrayList<Document>());
		baseSimulation.getDocuments().add(testDoc);
		assertEquals("The first doc is testDoc", testDoc, baseSimulation.getDocuments(0));
	}
	
	@Test
	public void testEquals() {
		Simulation sim = new Simulation();
		sim.startGame(1, 1, 1, 1);
		assertEquals("sim and baseSimulation are the same", sim, baseSimulation);
	}
	
	@Test
	public void testCopy() {
		Simulation sim = new Simulation();
		sim.copy(baseSimulation);
		assertEquals("sim and baseSimulation are the same", sim, baseSimulation);
	}

}
