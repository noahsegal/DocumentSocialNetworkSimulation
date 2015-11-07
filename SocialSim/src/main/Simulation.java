package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Group: MyNiftyJavaRepo
 * Author: Monica Ruttle
 * Class responsible for iteration of turns and setting up the system
 */
public class Simulation {

	private List<String> tags;
	private List<User> users;
	private List<Document> documents;
	private HashMap<User, ArrayList<Integer>> payoffs;
	private int numberOfTags;
	private int numberOfConsumers;
	private int numberOfProducers;
	private int numberOfTurns;
	private int currentTurn;
	private int currentId;
	private MainWindow mw;

	public static void main(String[] args) 
	{
		Simulation sim = new Simulation();
		sim.mw = new MainWindow(sim);
	}
	
	/**
	 * Initialize the Simulation, creating the list of consumers, documents and tags
	 */
	public Simulation()
	{
		users = new ArrayList<User>();
		documents = new ArrayList<Document>();
		tags = new ArrayList<String>();
		payoffs = new HashMap<User, ArrayList<Integer>>();
		try {
			for (String line : Files.readAllLines(Paths.get("Tags.txt"))) {
				for (String tag : line.split(", ")) {
				    tags.add(tag);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Logic to start the game and get the necessary information from the use
	 */
	public void startGame(int numberOfTurns, int numberOfTags, int numberOfProducers, int numberOfConsumers)
	{
		int index;
		Random rand;
		
		this.numberOfTurns = numberOfTurns;
		if (numberOfTags > tags.size())
			this.numberOfTags = tags.size();
		else this.numberOfTags = numberOfTags;
		this.numberOfProducers = numberOfProducers;
		this.numberOfConsumers = numberOfConsumers;
		
		currentTurn = 1;
		currentId = 1;
		rand = new Random();
		
		while (currentId <= numberOfProducers)
		{
			Producer p = new Producer(currentId, new PopularitySearch());
			index = rand.nextInt(this.numberOfTags);
			String s = tags.get(index);
			p.setTag(s);
			users.add(p);
			payoffs.put(p, new ArrayList<Integer>());
			payoffs.get(p).add(0);
			currentId++;
		}
		
		while (currentId <= numberOfProducers+numberOfConsumers)
		{
			Consumer c = new Consumer(currentId, new PopularitySearch());
			index = rand.nextInt(this.numberOfTags);
			String s = tags.get(index);
			c.setTag(s);
			users.add(c);
			payoffs.put(c, new ArrayList<Integer>());
			payoffs.get(c).add(0);
			currentId++;
		}
		
	}

	/**
	 * The turn a consumer will take
	 * 
	 * @param k The number of documents to search for
	 * @return if the simulation is over
	 */
	public boolean takeTurn(int k)
	{
		//select random consumer or producer
		User c = users.get((int)(Math.random() * users.size()));
		
		//search the documents and calls the take turn method for either a consumer or a producer
		List<Document> searchResults = c.searchMethod.search(c, documents, k);
		
		Document d = c.takeTurn(searchResults);
		if (d != null){
			documents.add(d);
			searchResults.add(d);
		}
		
		calculateProducerPayoff(searchResults);
		if (!(c instanceof Producer))
		{
			payoffs.get(c).add(c.getPayoff());
		}
		
		mw.updateTables(documents, users);
		currentTurn++;
		if (numberOfTurns == currentTurn)
			return false;
		else return true;
		
	}
	
	/**
	 * Recalculate the payoff of each producer with a consumer's search results
	 * 
	 * @param docs the results of the search by a consumer
	 */
	private void calculateProducerPayoff(List<Document> docs)
	{
		for (User c : users) {
			if (c instanceof Producer)
			{
				Producer p = (Producer)c;
				p.calculatePayoff(docs);
				payoffs.get(p).add(p.getPayoff());
			}
		}
	}

	/**
	 * The string providing the information of the current state of the simulation
	 */
	public String toString(){
		String s = "Current Standing: \n\nCurrent Contributors:\n";
		for (int i = 0; i < currentId-1; i++)
		{
			s += users.get(i).toString() + "\n";
		}
		
		s += "Current Documents:\n";
		for (int y = 0; y < documents.size(); y++)
		{
			s += documents.get(y).toString() + "\n";
		}
		
		return s;
	}
	
	public void copy(Simulation sim){
		currentId = sim.currentId;
		currentTurn = sim.currentTurn;
		documents = new ArrayList<Document>(sim.documents);
		users = new ArrayList<User>(sim.users);
		numberOfConsumers = sim.numberOfConsumers;
		numberOfProducers = sim.numberOfProducers;
		numberOfTags = sim.numberOfTags;
		numberOfTurns = sim.numberOfTurns;
		tags = new ArrayList<String>(sim.tags);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Simulation))
			return false;
		
		Simulation s = (Simulation)obj;
		return s.currentId == currentId && s.currentTurn == currentTurn && s.documents.equals(documents) 
				&& s.numberOfConsumers == numberOfConsumers && s.numberOfProducers == numberOfProducers 
				&& s.numberOfTags == numberOfTags && s.numberOfTurns == numberOfTurns && s.tags.equals(tags)
				&& s.users.equals(users);
	}
	
	
	//////////////////////////
	//  Getters & Setters  ///
	//////////////////////////
	
	/**
	 * get the payoffs list
	 * 
	 * @return the hashmap of payoffs
	 */
	public HashMap<User, ArrayList<Integer>> getPayoffs() {
		return payoffs;
	}
	
	/**
	 * Set the currentTurn
	 * 
	 * @param currentTurn
	 */
	public void setCurrentTurn(int currentTurn)
	{
		this.currentTurn = currentTurn;
	}
	
	/**
	 * Get the current turn
	 * 
	 * @return int
	 */
	public int getCurrentTurn()
	{
		return currentTurn;
	}
	
	/**
	 * Set the current ID 
	 * 
	 * @param currentId
	 */
	public void setCurrentId(int currentId)
	{
		this.currentId = currentId;
	}
	
	/**
	 * Get the current ID
	 * 
	 * @return int
	 */
	public int getCurrentId()
	{
		return currentId;
	}
	
	/**
	 * Set the current ID 
	 * 
	 * @param currentId
	 */
	public void setConsumers(List<User> users)
	{
		this.users = users;
	}
	
	/**
	 * Get the current ID
	 * 
	 * @return consumer
	 */
	public List<User> getConsumers()
	{
		return users;
	}
	
	/**
	 * Set the current ID 
	 * 
	 * @param currentId
	 */
	public void setDocuments(List<Document> documents)
	{
		this.documents = documents;
	}
	
	/**
	 * Get the current ID
	 * 
	 * @return consumer
	 */
	public List<Document> getDocuments()
	{
		return documents;
	}

	/**
	 * Set the list of payoffs
	 * 
	 * @param payoffs
	 */
	public void setPayoffs(HashMap<User, ArrayList<Integer>> payoffs) {
		this.payoffs = payoffs;
	}

	/**
	 * get the list of tags
	 * 
	 * @return
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * set the list of tags
	 * 
	 * @param tags
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * get the number of consumers
	 * 
	 * @return
	 */
	public int getNumberOfConsumers() {
		return numberOfConsumers;
	}

	/**
	 * set the number of consumers
	 * 
	 * @param numberOfConsumers
	 */
	public void setNumberOfConsumers(int numberOfConsumers) {
		this.numberOfConsumers = numberOfConsumers;
	}

	/**
	 * get the number of producers
	 * 
	 * @return
	 */
	public int getNumberOfProducers() {
		return numberOfProducers;
	}

	/**
	 * set the number of producers
	 * 
	 * @param numberOfProducers
	 */
	public void setNumberOfProducers(int numberOfProducers) {
		this.numberOfProducers = numberOfProducers;
	}

	/**
	 * get the number of turns
	 * 
	 * @return
	 */
	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	/**
	 * set the number of turns
	 * 
	 * @param numberOfTurns
	 */
	public void setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = numberOfTurns;
	}
	
	/**
	 * sets a consumer index
	 * 
	 * @param i
	 * @param c
	 */
	public void setUser(int i, User c)
	{
		users.set(i, c);
	}
	
	/**
	 * get a specific consumer at an index
	 * 
	 * @param i
	 * @return
	 */
	public User getUsers(int i)
	{
		return users.get(i);
	}
	
	/**
	 * get all the users
	 * 
	 * @return the users in the simulation
	 */
	public List<User> getUsers()
	{
		return users;
	}
	
	/**
	 * set a document in the list
	 * 
	 * @param i
	 * @param d
	 */
	public void setTags(int i, String t)
	{
		tags.set(i, t);
	}
	
	/**
	 * get a specific document at an index
	 * 
	 * @param i
	 * @return
	 */
	public String getTags(int i)
	{
		return tags.get(i);
	}
	
	/**
	 * set a document in the list
	 * 
	 * @param i
	 * @param d
	 */
	public void setDocuments(int i, Document d)
	{
		documents.set(i, d);
	}
	
	/**
	 * get a specific document at an index
	 * 
	 * @param i
	 * @return
	 */
	public Document getDocuments(int i)
	{
		return documents.get(i);
	}
	
	public void setMainWindow(MainWindow mw) {
		this.mw = mw;
	}
}
