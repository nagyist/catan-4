package game;

import java.util.ArrayList;
import java.util.Collections;

import board.*;


/**
 * The main game class of Settlers of Catan
 */
public class Game {
	
	private Board board;
	private ArrayList<Player> players;
	private Deck deck;
	
	
	/**
	 * Constructor for game, creates the Board. 
	 * @param givenPlayers the players of the game
	 */
	public Game(ArrayList<Player> givenPlayers) {
		
		if (givenPlayers.size() < 3 || givenPlayers.size() > 4)
			throw new IllegalArgumentException("Game must be played with three or four players");
		
		Collections.shuffle(givenPlayers);
		
		players = givenPlayers;
		board = new Board();
		deck = new Deck();
	}
	
	/**
	 * Method to start the game
	 */
	public void start() {
		setup();
		play();
	}
	
	/**
	 * Runs the setup phase of the game
	 */
	private void setup() {
		
		for (int i = 0; i <= 1; i++) {
			for (Player p : players) {
				//TODO: ask to place settlement and road
			}
		}
		
		Collections.reverse(players);
	}
	
	/**
	 * Loops through the players, executing each phase of their turn
	 * Ends when one player has ten or more victory points and more points than any other player
	 */
	private void play() {
		
		boolean gameOver;
		
		do {
			
			// Check if the game is over
			
			int maxVictoryPoints = 0;
			int secondMaxVictoryPoints = 0;
			
			for (Player p : players) {
				int victoryPoints = p.getVictoryPoints();
				
				if (victoryPoints > maxVictoryPoints) {
					maxVictoryPoints = victoryPoints;
				}
				else if (victoryPoints > secondMaxVictoryPoints) {
					secondMaxVictoryPoints = victoryPoints;
				}
			}
			gameOver = maxVictoryPoints >= 10 && maxVictoryPoints > secondMaxVictoryPoints;
			
			// For each player execute the phases of their turn (roll, trade, build)
			
			for (Player p : players) {
				roll(p);
				tradeAndBuild(p);
			}
		} while (!gameOver);
		
	}
	
	/**
	 * Rolls the die and allocates resources to players
	 * @param p the Player rolling (in case Player wants to play dev card first)
	 */
	private void roll(Player p) {
		
		int input;
			/* Possible values:
			 * 0 - play dev card
			 * 1 - continue
			 */
		do {
			input = 1; //TODO: input here
			if (input == 0) {
				playDevCard(p);
			}
		} while (input != 1);
		
		// RTD
		int roll1 = (int)(Math.random() * 6 + 1);
		int roll2 = (int)(Math.random() * 6 + 1);
		
		if (roll1 == 7 || roll2 == 7) {
			// Deal with Robber case
			halfCards();
			moveRobber(p);
		}
		else {
			// Distribute resources
			board.distributeResources(roll1 + roll2);
		}
	}
	
	/**
	 * Allows the given Player to move the Robber
	 * @param p the Player who moved the Robber
	 */
	private void moveRobber(Player p) {
		int locInput = 0; //TODO: input
			/* Two digits
			 * xCoord is hundreds place within [1, 7]
			 * yCoord is tens place within [1, 7]
			 */
		int xCoord = locInput / 10;
		int yCoord = locInput % 10;
		
		Location loc = new Location(xCoord, yCoord);
		Location prev = board.getRobberLocation();
		
		if (loc.equals(prev)) {
			//TODO: throw error about need to move Robber
		}
		
		board.setRobberLocation(loc);
		board.getTile(loc).setRobber(true);
		board.getTile(prev).setRobber(false);
		
		takeCard(p, loc);
	}
	
	/**
	 * Allows the given Player to take a card from any Player with a Settlement on the Tile of the given Location
	 * @param p the Player taking a card
	 * @param loc the Location of the Tile
	 */
	private void takeCard(Player p, Location loc) {
		
	}
	
	/**
	 * If any Player has more than seven cards, they choose half their cards (rounded down) to return to the bank
	 */
	private void halfCards() {
		for (Player p : players) {
			
			int cap = 7;
			int numbCards = p.getNumberResourcesType("BRICK") + 
							p.getNumberResourcesType("WOOL") + 
							p.getNumberResourcesType("ORE") + 
							p.getNumberResourcesType("GRAIN") + 
							p.getNumberResourcesType("LUMBER");
			int currentCards = numbCards;
			
			boolean done = false;
			
			do {
				currentCards = p.getNumberResourcesType("BRICK") + 
							   p.getNumberResourcesType("WOOL") + 
							   p.getNumberResourcesType("ORE") + 
							   p.getNumberResourcesType("GRAIN") + 
							   p.getNumberResourcesType("LUMBER");
				
				if (currentCards > cap) {
					int input = 0; //TODO: input
						/* Possible Values:
						 * 0 - LUMBER
						 * 1 - BRICK
						 * 2 - WOOL
						 * 3 - GRAIN
						 * 4 - ORE
						 */
					cap = numbCards / 2;
					
					switch (input) {
					case 0: 
						p.setNumberResourcesType("LUMBER", p.getNumberResourcesType("LUMBER") - 1);
						break;
					case 1: 
						p.setNumberResourcesType("BRICK", p.getNumberResourcesType("BRICK") - 1);
						break;
					case 2: 
						p.setNumberResourcesType("WOOL", p.getNumberResourcesType("WOOL") - 1);
						break;
					case 3: 
						p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 1);
						break;
					case 4: 
						p.setNumberResourcesType("ORE", p.getNumberResourcesType("ORE") - 1);
						break;
					}
				}
				else {
					done = true;
				}
			} while (!done);
		}
	}
	
	/**
	 * Allows the provided Player to play a dev card
	 * @param p
	 */
	private void playDevCard(Player p) {
		
		ArrayList<DevCard> cards = p.getHand();
		
		int input = 0; //TODO: input
			/* Possible values:
			 * any index within ArrayList cards
			 */
	
		if (input >= 0 && input < cards.size()) {
			DevCard dC = cards.remove(input);
			
			if (dC.getType().equals("Knight")) {
				moveRobber(p);
			}
			else if (dC.getType().equals("Progress")) {
				if (dC.getSubType().equals("Road Building")) {
					
				}
				else if (dC.getSubType().equals("Monoply")) {
					
				}
				else if (dC.getSubType().equals("Year of Plenty")) {
					
				}
			}
			else if (dC.getType().equals("Victory Point")) {
				
			}
		}
		else {
			//TODO: throw error about invalid dev card selection
		}
	}
	
	/**
	 * Goes through the build phase for Player p
	 * @param p the Player doing the building
	 */
	private void tradeAndBuild(Player p) {
		
		int input;
			/* Possible values:
			 * 0 - trade
			 * 1 - buy
			 * 2 - play dev card
			 * 3 - end turn
			 */
		
		do {
			input = 3; //TODO: input

			if (input == 0) {
				trade(p);
			}
			else if (input == 1) {
				buy(p);
			}
			else if (input == 2) {
				playDevCard(p);
			}
			else {
				//TODO: throw error about invalid action selection
			}
		} while (input != 3);
	}
	
	/**
	 * Have given Player trade other Players
	 * @param p the Player initiating trading
	 */
	private void trade(Player p) {
		int input = 0; //TODO: input
			/* Possible values:
			 * 0 - trade player 0
			 * 1 - trade player 1
			 * 2 - trade player 2 (if 4 player game)
			 * 3 - done trading
			 */
		
		//TODO: functionality
	}
	
	/**
	 * The actual buying followed by placing (if applicable) of something
	 * This method handles the most checks (excluding object placement) and changes values of resources and victory points
	 * @param p the Player doing the buying
	 * @return whether the buying succeeded or not
	 */
	private void buy(Player p) {
		
		int input; //TODO: input
			/* Possibe values:
			 * 1 - road
			 * 2 - settlement
			 * 3 - city
			 * 4 - dev card
			 * 5 - done
			 */
		
		do {
			input = 5; //TODO: input
			
			switch (input) {
			case 1:
				
				if (p.getNumberResourcesType("BRICK") < 1 || p.getNumberResourcesType("LUMBER") < 1) {
					//TODO: throw error about not enough resources
				}
				
				// Check Player has not exceeded capacity for object
				int numbRoads = 0;
				for (int i = 0; i < 7; i++) {
					for (int j = 0; j < 7; j++) {
						for (int k = 0; k < 3; k++) {
							if (board.getRoad(new EdgeLocation(i, j, k)).getOwner().equals(p))
								numbRoads++;
						}
					}
				}
				if (numbRoads >= 15) {
					//TODO: throw error about too many of object owned already
				}
				
				// Place the Settlement
				buyObject(p, input);
				
				p.setNumberResourcesType("BRICK", p.getNumberResourcesType("BRICK") - 1);
				p.setNumberResourcesType("LUMBER", p.getNumberResourcesType("LUMBER") - 1);
				
				p.setVictoryPoints(p.getVictoryPoints() + 1);
	
				break;
			case 2:
				
				// Check Player has sufficient resources
				if (p.getNumberResourcesType("BRICK") < 1 || p.getNumberResourcesType("GRAIN") < 1 || p.getNumberResourcesType("WOOL") < 1 || p.getNumberResourcesType("LUMBER") < 1) {
					//TODO: throw error about not enough resources
				}
				
				// Check Player has not exceeded capacity for object
				int numbSettlements = 0;
				for (int i = 0; i < 7; i++) {
					for (int j = 0; j < 7; j++) {
						for (int k = 0; k < 2; k++) {
							if (board.getStructure(new VertexLocation(i, j, k)).getType() == 0 && board.getStructure(new VertexLocation(i, j, k)).getOwner().equals(p))
								numbSettlements++;
						}
					}
				}
				if (numbSettlements >= 5) {
					//TODO: throw error about too many of object owned already
				}
				
				
				// Place the Settlement
				buyObject(p, input);
				
				p.setNumberResourcesType("BRICK", p.getNumberResourcesType("BRICK") - 1);
				p.setNumberResourcesType("LUMBER", p.getNumberResourcesType("LUMBER") - 1);
				p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 1);
				p.setNumberResourcesType("WOOL", p.getNumberResourcesType("WOOL") - 1);
	
				p.setVictoryPoints(p.getVictoryPoints() + 1);
	
				break;
			case 3:
				
				// Check Player has sufficient resources
				if (p.getNumberResourcesType("GRAIN") < 2 || p.getNumberResourcesType("ORE") < 3) {
					//TODO: throw error about not enough resources
				}
				
				// Check Player has not exceeded capacity for object
				int numbCities = 0;
				for (int i = 0; i < 7; i++) {
					for (int j = 0; j < 7; j++) {
						for (int k = 0; k < 2; k++) {
							if (board.getStructure(new VertexLocation(i, j, k)).getType() == 1 && board.getStructure(new VertexLocation(i, j, k)).getOwner().equals(p))
								numbCities++;
						}
					}
				}
				if (numbCities >= 4) {
					//TODO: throw error about too many of object owned already
				}
				
				// Upgrade the settlement
				buyObject(p, input);
				
				p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 2);
				p.setNumberResourcesType("ORE", p.getNumberResourcesType("ORE") - 3);
	
				p.setVictoryPoints(p.getVictoryPoints() + 1);
	
				break;
			case 4:
				
				// Check Player has sufficient resources
				if (p.getNumberResourcesType("ORE") < 1 || p.getNumberResourcesType("WOOL") < 1 || p.getNumberResourcesType("GRAIN") < 1) {
					//TODO: throw error about not enough resources
				}
				
				// Assign the DevCard to the Player
				buyObject(p, input);
				
				p.setNumberResourcesType("ORE", p.getNumberResourcesType("ORE") - 1);
				p.setNumberResourcesType("WOOL", p.getNumberResourcesType("WOOL") - 1);
				p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 1);
	
				break;
			}
		} while (input != 5);
	}
	
	/**
	 * Have Player p buy a thing of type choice
	 * This method mostly handles calling the actual placement of an object
	 * @param p the Player doing the buying
	 * @param choice the structure/road/card to buy
	 */
	private void buyObject(Player p, int choice) {
		
		if (choice == 1 || choice == 2 || choice == 3) {

			boolean goodSpot = true;
			
			if (choice == 1) {
				do {
					if (!goodSpot) {
						//TODO: throw error invalid placement
					}
					
					int locInput = 0; //TODO: input
						/* Three digits
						 * xCoord is hundreds place within [1, 7]
						 * yCoord is tens place within [1, 7]
						 * orient is ones place within [0, 2] 
						 */
					int xCoord = locInput / 100;
					int yCoord = (locInput - (int)(locInput / 100)*(100))/10;
					int orient = locInput % 10;
					
					EdgeLocation loc = new EdgeLocation(xCoord, yCoord, orient);
					goodSpot = board.placeRoad(loc, p);
				} while (!goodSpot);
			}
			else if (choice == 2) {
				do {
					if (!goodSpot) {
						//TODO: throw error invalid placement
					}
					
					int locInput = 0; //TODO: input
						/* Three digits
						 * xCoord is hundreds place within [1, 7]
						 * yCoord is tens place within [1, 7]
						 * orient is ones place within [0, 1] 
						 */
					int xCoord = locInput / 100;
					int yCoord = (locInput - (int)(locInput / 100)*(100))/10;
					int orient = locInput % 10;
					
					VertexLocation loc = new VertexLocation(xCoord, yCoord, orient);
					goodSpot = board.placeStructure(loc, p);
				} while (!goodSpot);
			}
			else if (choice == 3) {
				do {
					int locInput = 0; //TODO: input
						/* Three digits
						 * xCoord is hundreds place within [1, 7]
						 * yCoord is tens place within [1, 7]
						 * orient is ones place within [0, 1] 
						 */
					int xCoord = locInput / 100;
					int yCoord = (locInput - (int)(locInput / 100)*(100))/10;
					int orient = locInput % 10;
										
					VertexLocation loc = new VertexLocation(xCoord, yCoord, orient);
					
					Structure s = board.getStructure(loc);
					
					if (!s.getOwner().equals(p)) {
						//TODO: throw error about unowned settlement
					}
					else {
						goodSpot = true;
					}
	
					Structure c = new City(s.getLocation());
					c.setOwner(s.getOwner());
					board.setStructure(loc, c);
				} while (!goodSpot);
			}
		}
		else if (choice == 4) {
			DevCard dC = deck.draw();
			if (null != dC) {
				p.addDevCard(dC);
			}
			else {
				//TODO: throw error about no dev cards left
			}
		}
	}
}