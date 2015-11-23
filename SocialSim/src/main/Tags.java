package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Group: MyNiftyJavaRepo
 * @author Justin Fleming 
 * @author Noah Segal
 * Responsible for getting a list of Tags
 */

public class Tags {
	private static ArrayList<String> tags = new ArrayList<String>();
	static{
		tags.add("sushi");
		tags.add("pie");
		tags.add("starWars");
		tags.add("robots");
		tags.add("chineseFood");
		tags.add("indianFood");
		tags.add("pizza");
		tags.add("salmonTeriyaki");
		tags.add("sole");
		tags.add("jazz");
		tags.add("flute");
		tags.add("starTrek");
		tags.add("LOTR");
		tags.add("elves");
		tags.add("goblins");
		tags.add("cosplay");
		tags.add("batman");
		tags.add("deadpool");
		tags.add("goku");
		tags.add("LOL");
		tags.add("Dr.Octogonapus");
		tags.add("Firefly");
		tags.add("Ruby");
		tags.add("Java");
		tags.add("C");
		tags.add("planes");
		tags.add("trains");
		tags.add("Chevy");
		tags.add("Ford");
		tags.add("GMC");
		tags.add("Marvel");
		tags.add("DC");
		tags.add("Bruce");
		tags.add("Katelyn");
		tags.add("Babak");
		tags.add("Leeeeeroy");
		tags.add("theBeatles");
	}
	
	/**
	 * Get a specific number of tags
	 * @param n Number of desired tags
	 * @return List containing tags
	 */
	public static List getTags(int n) {
		if (n < 0) return new ArrayList<String>();
		else if (n > tags.size()) return tags;
		
		else {
			int size = tags.size();
			String tag;
			Random randy = new Random();
			ArrayList<String> returnTags = new ArrayList<String>();
			
			while (n > 0) {
				tag = tags.get(randy.nextInt(size));
				while (returnTags.contains(tag)) tag = tags.get(randy.nextInt(size));
				returnTags.add(tag);
				n--;
			}
			
			return returnTags;
		}
	}
	
}
