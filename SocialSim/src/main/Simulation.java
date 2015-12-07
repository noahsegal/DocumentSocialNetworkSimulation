package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Search.PopularitySearch;

/**
 * Group: MyNiftyJavaRepo
 * Author: Monica Ruttle
 * Class responsible for iteration of turns and setting up the system
 */
public class Simulation implements Serializable{
	
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
		payoffs = new HashMap<User, ArrayList<Integer>>();
	}
	
	/**
	 * Logic to start the game and get the necessary information from the use
	 * @param numberOfTurns the number of turns
	 * @param numberOfTags the number of tags
	 * @param numberOfProducers the number of producers
	 * @param numberOfConsumers the number of consumers
	 */
	public void startGame(int numberOfTurns, int numberOfTags, int numberOfProducers, int numberOfConsumers)
	{
		reset();
		int index;
		Random rand;
		
		this.numberOfTurns = numberOfTurns;
		this.numberOfTags = numberOfTags;
		this.numberOfProducers = numberOfProducers;
		this.numberOfConsumers = numberOfConsumers;
		
		tags = new ArrayList<String>(Tags.getTags(numberOfTags));
		if (tags.size() < numberOfTags)
			this.numberOfTags = tags.size();
		
		currentTurn = 0;
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
		currentTurn++;
		//select random consumer or producer
		User u = users.get((int)(Math.random() * users.size()));
		
		//search the documents and calls the take turn method for either a consumer or a producer
		List<Document> searchResults = new ArrayList<Document>(u.searchMethod.search(u, documents, k));
		
		Document d = u.takeTurn(searchResults);
		if (d != null){
			documents.add(d);
			searchResults.add(d);
		}
		
		calculateProducerPayoff(searchResults);
		if (!(u instanceof Producer))
		{
			payoffs.get(u).add(u.getPayoff());
		}
		
		mw.updateTables(documents, users);
		if (currentTurn == numberOfTurns)
		{
			return false;
		} else {
			return true;
		}
		
	}
	
	public void decrementTurn() {
		currentTurn--;
	}
	
	/**
	 * reset the game to its original state
	 */
	public void reset()
	{
		users = new ArrayList<User>();
		documents = new ArrayList<Document>();
		payoffs = new HashMap<User, ArrayList<Integer>>();
	}
	
	/**
	 * Recalculate the payoff of each producer with a consumer's search results
	 * 
	 * @param docs the results of the search by a consumer
	 */
	private void calculateProducerPayoff(List<Document> docs)
	{
		for (User u : users) {
			if (u instanceof Producer)
			{
				Producer p = (Producer)u;
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
	
	/**
	 * Do any restore things here
	 * @param window
	 * @param save 
	 */
	public void restoreState(MainWindow window, SimSave save) {
		tags = save.getTags();
		users = save.getUsers();
		documents = save.getDocuments();
		payoffs = save.getPayoffs();
		numberOfTags = save.getNumberOfTags();
		numberOfConsumers = save.getNumberOfConsumers();
		numberOfProducers = save.getNumberOfProducers();
		numberOfTurns = save.getNumberOfTurns();
		currentTurn = save.getCurrentTurn();
		currentId = save.getCurrentId();
		mw = window;
		copy(window.getSim());
		mw.updateTables(documents, users);
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
	 * Set the list of payoffs
	 * 
	 * @param payoffs
	 */
	public void setPayoffs(HashMap<User, ArrayList<Integer>> payoffs) {
		this.payoffs = payoffs;
	}

	/**
	 * Set the payoffs of a user
	 * 
	 * @param payoffs
	 */	
	public ArrayList<Integer> getPayoff(User u)
	{
		return payoffs.get(u);
	}

	/**
	 * Set the payoffs of a user
	 * 
	 * @param payoffs
	 */	
	public void setPayoff(User u, ArrayList<Integer> p)
	{
		payoffs.put(u, p);
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
	 * Set the currentTurn
	 * 
	 * @param currentTurn
	 */
	public void setCurrentTurn(int currentTurn)
	{
		this.currentTurn = currentTurn;
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
	public void setCurrentId(int currentId)
	{
		this.currentId = currentId;
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
	 * Set the current ID 
	 * 
	 * @param currentId
	 */
	public void setUsers(List<User> users)
	{
		this.users = users;
	}
	
	/**
	 * get a specific consumer at an index
	 * 
	 * @param i
	 * @return
	 */
	public User getUser(int k)
	{
		return users.get(k);
	}
	
	/**
	 * sets a consumer index
	 * 
	 * @param i
	 * @param c
	 */
	public void setUser(int k, User c)
	{
		users.set(k, c);
	}
	
	/**
	 * get a user from a specified id
	 * @param the specified id of the user
	 * @return the user with the corresponding id
	 */
	public User getUserById(int id) {
		for (User u: users)
			if (u.getID() == id)
				return u;
		return null;
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
	 * Set the current ID 
	 * 
	 * @param currentId
	 */
	public void setDocuments(List<Document> documents)
	{
		this.documents = documents;
	}
	
	/**
	 * get a document at a specific index
	 * @param k the index
	 * @return the document at that index
	 */
	public Document getDocument(int k) {
		return documents.get(k);
	}
	
	/**
	 * set a document at a specified index
	 * @param k the index
	 * @param d the document
	 */
	public void setDocument(int k, Document d) {
		documents.set(k, d);
	}
	
	/**
	 * Get the document with the corresponding name
	 * @param The name of the desired document
	 * @return The document with the requested name
	 */
	public Document getDocument(String name) {
		for (Document d: documents) {
			if (d.getName() == name)
				return d;
		}
		return null;
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
	 * get a specific document at an index
	 * 
	 * @param i
	 * @return
	 */
	public String getTag(int k)
	{
		return tags.get(k);
	}
	
	/**
	 * set a document in the list
	 * 
	 * @param i
	 * @param d
	 */
	public void setTag(int k, String t)
	{
		tags.set(k, t);
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
	 * get the number of tags
	 * 
	 * @return
	 */
	public int getNumberOfTags() {
		return numberOfTags;
	}

	/**
	 * set the number of consumers
	 * 
	 * @param numberOfConsumers
	 */
	public void setNumberOfTags(int numberOfTags) {
		this.numberOfTags = numberOfTags;
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
	 * get the main window
	 * @return the main window
	 */
	public MainWindow getMainWindow() {
		return mw;
	}
	
	/**
	 * set the main window
	 * @param mw the main window
	 */
	public void setMainWindow(MainWindow mw) {
		this.mw = mw;
	}
}
