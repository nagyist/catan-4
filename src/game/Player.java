package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import board.DevCard;
import board.Road;

/**
 * This class is a Player in the game Settlers of Catan
 */
public class Player {
	
	private final String name;
	private final Color color;
	private int victoryPoints;
	private HashMap<String, Integer> resources;
	private ArrayList<DevCard> hand;
	private ArrayList<Road> roads;
	private int numbKnights;
	private boolean hasLargestArmy;
	private boolean[] ports = {false, false, false, false, false, false};
					// 0 = general
					// 1 = brick
					// 2 = wool
					// 3 = ore
					// 4 = grain
					// 5 = lumber
	
	
	/**
	 * Constructor takes params for assignment to fields
	 * @param n is the Player's name
	 * @param c is the Player's color in game
	 */
	public Player(String n, Color c) {
		
		name = n;
		color = c;
		victoryPoints = 0;
		roads = new ArrayList<Road>();
		
		resources = new HashMap<String, Integer>(5);
		resources.put("BRICK", 0);
		resources.put("WOOL", 0);
		resources.put("ORE", 0);
		resources.put("GRAIN", 0);
		resources.put("LUMBER", 0);
		
		hand = new ArrayList<DevCard>();
	}
	
	public Player(String n, Color c, int brick, int wool, int ore, int grain, int lumber) {
		this(n,c);
		
		setNumberResourcesType("BRICK", brick);
		setNumberResourcesType("WOOL", wool);
		setNumberResourcesType("ORE", ore);
		setNumberResourcesType("GRAIN", grain);
		setNumberResourcesType("LUMBER", lumber);
		
	}
	
	/**
	 * Getter for the Player's name
	 * @return name of Player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for the Player's color
	 * @return color of Player
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Getter for the number of victory points of this Player
	 * @return number of victory points
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}
	
	/**
	 * Setter for the number of victory points of this Player
	 * @param vP new number of victory points
	 */
	public void setVictoryPoints(int vP) {
		victoryPoints = vP;
	}
	
	/**
	 * Getter for this Player's quantity of given resource type
	 * @param str resource to work with
	 * @return number of resources str owned by this Player
	 */
	public int getNumberResourcesType(String str) {
		return resources.get(str).intValue();
	}
	
	/**
	 * Setter for this Player's quantity of given resource type
	 * @param str resource to work with
	 * @param n new number of resources of type str
	 */
	public void setNumberResourcesType(String str, int n) {
		resources.put(str, Integer.valueOf(n));
	}
	
	/**
	 * Adds given DevCard to this Player's hand of DevCards
	 * @param dC the DevCard to give to this Player
	 */
	public void addDevCard(DevCard dC) {
		hand.add(dC);
	}
	
	/**
	 * Getter for this Player's hand of DevCards
	 * @return an ArrayList of DevCards owned by this Player
	 */
	public ArrayList<DevCard> getHand() {
		return hand;
	}
	
	/**
	 * Adds given road to list of owned roads
	 * @param r road added
	 */
	public void addRoad(Road r){
		roads.add(r);
	}
	
	/**
	 * Getter for this player's roads
	 * @return ArrayList<Road> list or owned roads
	 */
	public ArrayList<Road> getRoads(){
		return roads;
	}
	
	/**
	 * Method to get an ArrayList of all the resources this Player has one or more of
	 * @return an ArrayList of resources that this Player as one or more of
	 */
	public ArrayList<String> getOwnedResources() {
		
		ArrayList<String> res = new ArrayList<String>();
		if (resources.get("BRICK").intValue() > 0) {
			res.add("BRICK");
		}
		if (resources.get("GRAIN").intValue() > 0) {
			res.add("GRAIN");
		}
		if (resources.get("WOOL").intValue() > 0) {
			res.add("WOOL");
		}
		if (resources.get("LUMBER").intValue() > 0) {
			res.add("LUMBER");
		}
		if (resources.get("ORE").intValue() > 0) {
			res.add("ORE");
		}
		
		return res;
	}
	
	/**
	 * Increments the field indicating the number of knights played by this Player
	 */
	public void incrementNumbKnights() {
		numbKnights++;
	}
	
	/**
	 * Getter for the number of knights this Player has played
	 * @return the number of knights previously played by this player
	 */
	public int getNumbKnights() {
		return numbKnights;
	}
	
	/**
	 * Setter for whether this player has the largest army or not
	 * @param b whether this player has the largest army
	 */
	public void setHasLargestArmy(Boolean b) {
		hasLargestArmy = b;
	}
	
	/**
	 * Getter for whether this player has the largest army or not
	 * @return whether this player has the largest army or not
	 */
	public boolean hasLargestArmy() {
		return hasLargestArmy;
	}
	
	/**
	 * Checks if this Player has the specified resources
	 * @param res the resources to check
	 * @return whether the Player has those resources
	 */
	public boolean hasResources(ArrayList<String> res) {
		int wool = 0,
			ore = 0,
			lumber = 0,
			brick = 0,
			grain = 0;
		
		for (String s : res) {
			if (res.equals("WOOL"))
				wool++;
			else if (res.equals("ORE"))
				ore++;
			else if (res.equals("LUMBER"))
				lumber++;
			else if (res.equals("BRICK"))
				brick++;
			else if (res.equals("GRAIN"))
				grain++;
		}
		
		if (wool > resources.get("WOOL") || ore > resources.get("ORE") || lumber > resources.get("LUMBER") || brick > resources.get("BRICK") || grain > resources.get("GRAIN"))
			return false;
		else
			return true;
	}
	
	/**
	 * Sets the corresponding port to true
	 * @param portTag 0 = general
					  1 = brick
					  2 = wool
					  3 = ore
					  4 = grain
					  5 = lumber
	 */
	public void addPort(int portTag) {
		ports[portTag] = true;
	}
	
	/**
	 * Getter for list of ports
	 * @return ports list of ports
	 */
	public boolean[] getPorts() {
		return ports;
	}
	
	/**
	 * Overridden toString method
	 * @return name
	 */
	public String toString() {
		return name;
	}
}