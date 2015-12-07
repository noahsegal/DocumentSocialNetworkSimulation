package main;

import java.io.Serializable;

public class MainWindowSave implements Serializable{
	private int numTurns;
	private int numCons;
	private int numTags;
	private int numProds;
	private int numSearch;
	private boolean init;
	private boolean onGoing;
	private SimSave sim;
	
	public MainWindowSave() {
		
	}
	
	public MainWindowSave(MainWindow mw){
		numTurns 	= mw.getNumberOfTurns();
		numCons 	= mw.getNumberOfCons();
		numProds 	= mw.getNumberOfProds();
		numTags 	= mw.getNumberOfTags();
		numSearch 	= mw.getNumberOfSearchResults();
		init 		= mw.isInitialized();
		onGoing 	= mw.isOnGoing();
		sim 		= new SimSave(mw.getSim());
	}

	public int getNumTurns() {
		return numTurns;
	}

	public void setNumTurns(int numTurns) {
		this.numTurns = numTurns;
	}

	public int getNumCons() {
		return numCons;
	}

	public void setNumCons(int numCons) {
		this.numCons = numCons;
	}

	public int getNumTags() {
		return numTags;
	}

	public void setNumTags(int numTags) {
		this.numTags = numTags;
	}

	public int getNumProds() {
		return numProds;
	}

	public void setNumProds(int numProds) {
		this.numProds = numProds;
	}

	public int getNumSearch() {
		return numSearch;
	}

	public void setNumSearch(int numSearch) {
		this.numSearch = numSearch;
	}

	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	public boolean isOnGoing() {
		return onGoing;
	}

	public void setOnGoing(boolean onGoing) {
		this.onGoing = onGoing;
	}

	public SimSave getSim() {
		return sim;
	}

	public void setSim(SimSave sim) {
		this.sim = sim;
	}
}
