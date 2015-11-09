package main;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Group: MyNiftyJavaRepo
 * Author: Monica Ruttle
 * Class responsible for testing the Simulation class
 */

public class SimulationTest {
	Simulation baseSimulation;
	MainWindow mw;
	@Before
	public void setUp() throws Exception {
		baseSimulation = new Simulation();
		mw = new MainWindow(baseSimulation);
		baseSimulation.setMainWindow(mw);
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
		assertEquals("simTest is the same as baseSimulation", baseSimulation, simTest);
	}

	@Test
	public void testStartGame() {
		assertEquals("The game has one turn", 1, baseSimulation.getNumberOfTurns());
		assertEquals("The game has one tag", 1, baseSimulation.getNumberOfTags());
		assertEquals("The game has one consumer", 1, baseSimulation.getNumberOfConsumers());
		assertEquals("The game has one producer", 1, baseSimulation.getNumberOfProducers());
		assertEquals("The game has no documents", 0, baseSimulation.getDocuments().size());
	}

	@Test
	public void testTakeTurn() {
		int currentTurn = baseSimulation.getCurrentTurn();
		boolean gameOver = baseSimulation.takeTurn(5);
		assertEquals("The turn has incremented by 1", currentTurn+1, baseSimulation.getCurrentTurn());
		assertFalse("The game is over", gameOver);
	}
	
	public void testReset() {
		baseSimulation.reset();
		Simulation test = new Simulation();
		assertEquals("A new instance of a simulation is the same as a reset one", baseSimulation, test);
	}

	@Test
	public void testToString() {
		String s = "Current Standing: \n\nCurrent Contributors:\n";
		s += baseSimulation.getUsers().get(0) + "\n";
		s += baseSimulation.getUsers().get(1) + "\n";
		
		s += "Current Documents:\n";
		assertEquals("The game will print its current status", s, baseSimulation.toString());
	}
	
	@Test
	public void testCopy() {
		Simulation sim = new Simulation();
		sim.copy(baseSimulation);
		assertEquals("sim and baseSimulation are the same", baseSimulation, sim);
	}
	
	@Test
	public void testEquals() {
		Simulation sim = new Simulation();
		sim.startGame(1, 1, 1, 1);
		assertEquals("sim and baseSimulation are the same", baseSimulation, sim);
	}

	@Test
	public void testGetPayoffs() {
		for(User u:baseSimulation.getUsers()){
			assertEquals("Each payoff is 0", 0, baseSimulation.getPayoffs().get(u).indexOf(0));
		}
	}

	@Test
	public void testSetPayoffs() {
		HashMap<User, ArrayList<Integer>> testPayoffs = new HashMap<User, ArrayList<Integer>>();
		baseSimulation.setPayoffs(testPayoffs);
		assertEquals("The payofss list is empty", testPayoffs, baseSimulation.getPayoffs());
	}
	
	@Test
	public void testGetPayoff() {
		ArrayList<Integer> payoffList = new ArrayList<Integer>();
		payoffList.add(0);
		assertEquals("The first user has a payoff list with one entry of 0", payoffList, baseSimulation.getPayoff(baseSimulation.getUser(0)));
	}
	
	@Test
	public void testSetPayoff() {
		User u = baseSimulation.getUser(0);
		ArrayList<Integer> payoff = new ArrayList<Integer>();
		baseSimulation.setPayoff(u, payoff);
		assertEquals("The user u has an empty payoff list", payoff, baseSimulation.getPayoff(u));
	}

	@Test
	public void testGetCurrentTurn() {
		assertEquals("The current turn is 1", 1, baseSimulation.getCurrentTurn());
	}

	@Test
	public void testSetCurrentTurn() {
		baseSimulation.setCurrentTurn(5);
		assertEquals("The current turn is 5", 5, baseSimulation.getCurrentTurn());
	}

	@Test
	public void testGetCurrentId() {
		assertEquals("The current Id is 3, since the next user would be the third user", 3, baseSimulation.getCurrentId());
	}

	@Test
	public void testSetCurrentId() {
		baseSimulation.setCurrentId(5);
		assertEquals("The current Id is 5", 5, baseSimulation.getCurrentId());
	}

	@Test
	public void testGetUsers() {
		Producer p = new Producer(1, new PopularitySearch());
		Consumer c = new Consumer(2, new PopularitySearch());
		List<User> users = new ArrayList<User>();
		users.add(p);
		p.setTag(baseSimulation.getUsers().get(0).getTag());
		users.add(c);
		c.setTag(baseSimulation.getUsers().get(1).getTag());
		assertEquals("There will one producer and one consumer", users, baseSimulation.getUsers());
	}

	@Test
	public void testSetUsers() {
		ArrayList<User> test = new ArrayList<User>();
		baseSimulation.setUsers(test);
		assertEquals("There is an empty user list", test, baseSimulation.getUsers());
	}
	
	@Test
	public void testGetUser() {
		User p = baseSimulation.getUsers().get(0);
		assertEquals("The first user is p", p, baseSimulation.getUser(0));
	}

	@Test
	public void testSetUser() {
		Consumer p = new Consumer(0, new PopularitySearch());
		p.setTag("Test");
		baseSimulation.setUser(0, p);
		assertEquals("The first user is p", p, baseSimulation.getUsers().get(0));
		
	}

	@Test
	public void testGetDocuments() {
		assertEquals("The documents list is empty", new ArrayList<Document>(), baseSimulation.getDocuments());
	}

	@Test
	public void testSetDocumentsListOfDocument() {
		List<Document> testDocs = new ArrayList<Document>();
		Document testDoc = new Document("Test", "Test", new Producer(0, new PopularitySearch()));
		testDocs.add(testDoc);
		baseSimulation.setDocuments(testDocs);
		assertEquals("The documents list is testDocs", testDocs, baseSimulation.getDocuments());
	}

	@Test
	public void testSetDocumentIntDocument() {
		Document testDoc = new Document("Test", "Test", new Producer(0, new PopularitySearch()));
		baseSimulation.getDocuments().add(new Document(null, null, null));
		baseSimulation.setDocument(0, testDoc);
		assertEquals("The first doc is testDoc", testDoc, baseSimulation.getDocument(0));
	}

	@Test
	public void testGetDocumentInt() {
		Document testDoc = new Document("Test", "Test", new Producer(0, new PopularitySearch()));
		baseSimulation.getDocuments().add(testDoc);
		assertEquals("The first doc is testDoc", testDoc, baseSimulation.getDocument(0));
	}

	@Test
	public void testGetTags() {
		List<String> tags = new ArrayList<String>();
		try {
			for (String line : Files.readAllLines(Paths.get("Tags.txt"))) {
				for (String tag : line.split(", ")) {
				    tags.add(tag);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals("The tag lists from the file are the same", tags, baseSimulation.getTags());
	}

	@Test
	public void testSetTagsListOfString() {
		List<String> testTags = new ArrayList<String>();
		baseSimulation.setTags(testTags);
		assertEquals("The tags list is set to testTags", testTags, baseSimulation.getTags());
	}

	@Test
	public void testGetTagsInt() {
		baseSimulation.setTag(0, "Test");
		assertEquals("The first tag is Test", "Test", baseSimulation.getTag(0));
	}

	@Test
	public void testSetTagsIntString() {
		baseSimulation.setTag(0, "Test");
		assertEquals("The first tag is Test", "Test", baseSimulation.getTag(0));
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
	public void testGetNumberOfTags() {
		assertEquals("There is 1 tags", 1, baseSimulation.getNumberOfTags());
	}
	
	@Test
	public void testSetNumberOfTags() {
		baseSimulation.setNumberOfTags(0);
		assertEquals("There are 0 tags", 0, baseSimulation.getNumberOfTags());
	}

	@Test
	public void testGetNumberOfProducers() {
		assertEquals("There is 1 producer", 1, baseSimulation.getNumberOfProducers());
	}

	@Test
	public void testSetNumberOfProducers() {
		baseSimulation.setNumberOfProducers(2);
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
	public void testGetMainWindow() {
		assertEquals("The main window is the same as a new maindow", mw, baseSimulation.getMainWindow());
	}
	
	@Test
	public void testSetMainWindow() {
		MainWindow mw2 = new MainWindow(baseSimulation);
		baseSimulation.setMainWindow(mw2);
		assertEquals("The main windows has been set to mw2", mw2, baseSimulation.getMainWindow());
	}

}
