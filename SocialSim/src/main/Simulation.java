package main;

import java.util.*;

public class Simulation {
	
	private List<Consumer> consumers;
	private List<Document> documents;
	private List<String> tags;
	private int numberOfConsumers;
	private int numberOfProducers;
	private int numberOfTurns;
	private int numberOfDocsInSearch;
	private int currentTurn;
	private int currentId;

	public static void main(String[] args) 
	{
		System.out.println("Welcome to SocialSim.");
		Simulation sim = new Simulation();
		sim.startGame();
	}
	
	public Simulation()
	{
		
	}
	
	private void startGame()
	{
		consumers = new ArrayList<Consumer>();
		documents = new ArrayList<Document>();
		tags = new ArrayList<String>();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Input the number of producers");
		numberOfProducers = sc.nextInt();
		System.out.println("Input the number of consumers");
		numberOfConsumers = sc.nextInt();
		System.out.println("Input the number of turns");
		numberOfTurns = sc.nextInt();
		
		currentTurn = 0;
		currentId = 1;
		
		while (currentId <= numberOfProducers)
		{
			Producer p = new Producer(currentId);
			System.out.println("Producer #" + currentId + " is created. Input the tag of the producer.");
			String tag = sc.next();
			p.setTag(tag);
			documents.add(p.produceDocument(currentId + "-" + p.getUploadedDocumentSize()));
			consumers.add(p);
			tags.add(tag);
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
			currentId++;
		}
		
		System.out.println(this);
		
	}
	
	private void takeTurn()
	{
		
	}

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
		s += "Current Turn: " + currentTurn;
		
		return s;
	}

}
