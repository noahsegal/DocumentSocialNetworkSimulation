package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimSave {
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
	
	public SimSave() {
		
	}
	
	public SimSave(Simulation s) {
		tags = s.getTags();
		users = s.getUsers();
		documents = s.getDocuments();
		payoffs = s.getPayoffs();
		numberOfTags = s.getNumberOfTags();
		numberOfConsumers = s.getNumberOfConsumers();
		numberOfProducers = s.getNumberOfProducers();
		numberOfTurns = s.getNumberOfTurns();
		currentTurn = s.getCurrentTurn();
		currentId = s.getCurrentId();
	}

	public List<String> getTags() {
		return tags;
	}
	
	public String getTags(int i) {
		return tags.get(i);
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public void setTags(int i, String s){
		this.tags.set(i,s);
	}

	public List<User> getUsers() {
		return users;
	}
	
	public User getUsers(int i){
		return users.get(i);
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void setUsers(int i, User u) {
		this.users.set(i, u);
	}

	public List<Document> getDocuments() {
		return documents;
	}
	
	public Document getDocument(int i) {
		return documents.get(i);
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	
	public void setDocuments(int i, Document doc) {
		this.documents.set(i, doc);
	}

	public HashMap<User, ArrayList<Integer>> getPayoffs() {
		return payoffs;
	}

	public void setPayoffs(HashMap<User, ArrayList<Integer>> payoffs) {
		this.payoffs = payoffs;
	}

	public int getNumberOfTags() {
		return numberOfTags;
	}

	public void setNumberOfTags(int numberOfTags) {
		this.numberOfTags = numberOfTags;
	}

	public int getNumberOfConsumers() {
		return numberOfConsumers;
	}

	public void setNumberOfConsumers(int numberOfConsumers) {
		this.numberOfConsumers = numberOfConsumers;
	}

	public int getNumberOfProducers() {
		return numberOfProducers;
	}

	public void setNumberOfProducers(int numberOfProducers) {
		this.numberOfProducers = numberOfProducers;
	}

	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = numberOfTurns;
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	public int getCurrentId() {
		return currentId;
	}

	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}
}
