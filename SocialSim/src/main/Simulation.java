package main;

import java.util.*;

/**
 * Group: MyNiftyJavaRepo
 * Author: Monica Ruttle
 * Class responsible for iteration of turns and setting up the system
 */
public class Simulation {
	
	private List<Consumer> consumers;
	private List<Document> documents;
	private HashMap<Consumer, ArrayList<Integer>> payoffs;
	private PopularitySearch searchMethod;
	private List<String> tags;
	private int numberOfConsumers;
	private int numberOfProducers;
	private int numberOfTurns;
	private int currentTurn;
	private int currentId;

	public static void main(String[] args) 
	{
		System.out.println("Welcome to SocialSim.");
		Simulation sim = new Simulation();
		sim.startGame();
		sim.playGame();
	}
	
	/**
	 * Initialize the Simulation, creating the list of consumers, documents and tags
	 */
	public Simulation()
	{
		consumers = new ArrayList<Consumer>();
		documents = new ArrayList<Document>();
		tags = new ArrayList<String>();
		payoffs = new HashMap<Consumer, ArrayList<Integer>>();
		searchMethod = new PopularitySearch();
	}
	
	/**
	 * Logic to start the game and get the necessary information from the use
	 */
	private void startGame()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("How many producers are there?");
		numberOfProducers = sc.nextInt();
		System.out.println("How many consumers are there?");
		numberOfConsumers = sc.nextInt();
		System.out.println("How many turns are there?");
		numberOfTurns = sc.nextInt();
		
		currentTurn = 1;
		currentId = 1;
		
		while (currentId <= numberOfProducers)
		{
			Producer p = new Producer(currentId);
			System.out.println("Producer #" + currentId + " is created. Input the taste of the producer.");
			String tag = sc.next();
			p.setTag(tag);
			documents.add(p.produceDocument(currentId + "-" + p.getUploadedDocumentSize()));
			consumers.add(p);
			tags.add(tag);
			payoffs.put(p, new ArrayList<Integer>());
			payoffs.get(p).add(0);
			currentId++;
		}
		
		Random rand = new Random();
		
		while (currentId <= numberOfProducers+numberOfConsumers)
		{
			Consumer c = new Consumer(currentId);
			int index = rand.nextInt(tags.size());
			String s = tags.get(index);
			c.setTag(s);
			consumers.add(c);
			payoffs.put(c, new ArrayList<Integer>());
			payoffs.get(c).add(0);
			currentId++;
		}
		
		System.out.println(this);
		
	}
	
	/**
	 * The game play loop
	 */
	private void playGame()
	{
		Scanner sc = new Scanner(System.in);
		currentTurn = 1;
		
		System.out.println("Would you like to begin the simulation? Y or N");
		String s = sc.next();
		if (s.equals("N"))
			System.exit(0);
		else if (!s.equals("Y"))
		{
			System.out.println("Invalid input.");
			playGame();
		}
		
		
		//the loop for the duration of the game
		while(currentTurn < numberOfTurns+1)
		{
			while (true)
			{
				System.out.println("Next Turn? Y or N");
				s = sc.next();
				if (s.equals("N"))
				{
					System.out.println("Exiting Game");
					System.exit(0);
				}
				else if (!s.equals("Y"))
				{
					System.out.println("Invalid input.");
					continue;
				} else break;
			}
			
			int k = 0;
			boolean loop = true;
			while (loop)
			{
				System.out.println("How many documents would you like to search for?");
				k = sc.nextInt();
				if (k > documents.size())
					k = documents.size();
				else loop = false;
			}
			takeTurn(k);
			currentTurn++;
			
		}
		s = "\n";
		s += "Game is over. " + toString() + "\nHistory of consumer payoffs:";
		for (Consumer con: consumers)
		{
			s += "\n" + con.getClass().getSimpleName() + " #" + con.getID() +": ";
			for(Integer i:payoffs.get(con))
			{
				s+= i + " ";
			}
		}
		System.out.println(s);
	}
	
	/**
	 * The turn a consumer will take
	 * 
	 * @param k The number of documents to search for
	 */
	private void takeTurn(int k)
	{
		//select random consumer or producer
		Random rand = new Random();
		Consumer c = consumers.get(rand.nextInt(consumers.size()));
		
		System.out.println("Turn #" + currentTurn + "\nTurn of " + c.getClass().getSimpleName() + " #" + c.getID());
		
		//search the documents and calls the take turn method for either a consumer or a producer
		List<Document> searchResults = searchMethod.search(c, documents, k);
		Document d = c.takeTurn(searchResults);
		if (d != null)
			documents.add(d);
		
		String results = "\nSearch Results: \n";
		for(Document doc: searchResults)
		{
			results += doc.toString()+"\n";
		}
		System.out.println(results + "\n");

		calculateProducerPayoff(searchResults);
		if (!(c instanceof Producer))
		{
			payoffs.get(c).add(c.getPayoff());
		}
		
		System.out.println(toString());
		
	}
	
	/**
	 * Recalculate the payoff of each producer with a consumer's search results
	 * 
	 * @param docs the results of the search by a consumer
	 */
	private void calculateProducerPayoff(List<Document> docs)
	{
		for (Consumer c : consumers) {
			if (c instanceof Producer)
			{
				Producer p = (Producer)c;
				p.calcProducerPayoff(docs);
				payoffs.get(p).add(p.getPayoff());
			}
		}
	}

	/**
	 * The string providing the information of the current state of the simulation
	 */
	public String toString()
	{
		String s = "Current Standing: \n\nCurrent Contributors:\n";
		for (int i = 0; i < currentId-1; i++)
		{
			s += consumers.get(i).toString() + "\n";
		}
		
		s += "Current Documents:\n";
		for (int y = 0; y < documents.size(); y++)
		{
			s += documents.get(y).toString() + "\n";
		}
		
		return s;
	}
	
	
	//////////////////////////
	//  Getters & Setters  ///
	//////////////////////////
	
	/* Current Turn */
	
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

}
