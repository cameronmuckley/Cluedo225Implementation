import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles rules checking and enforcement, as well as providing utility methods for the
 * main game class.
 */

public class RuleSet
{
  // fields for keeping track of gamestate
  private boolean gameWon = false;
  private int playersTurn = 1;
  private int numberPlayers;

  public enum Direction{
		  RIGHT,
		  LEFT,
		  UP,
		  DOWN
  }

  public RuleSet()
  {

  }

  public int setPlayers() {
	  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	  try {
		String s = br.readLine();
		// see of input is valid
		for(char c : s.toCharArray()) {
			if(!java.lang.Character.isDigit(c)) {
				System.out.println("Invalid input, try again.");
				br.close();
				return 0;
			}
		}
		int	i = Integer.parseInt(s);
		if(i >=3 && i <=6) {
			return i;
		}
		else {
			System.out.println("Invalid number of players entered, try again.");
		}

	  } catch (IOException e) {
		  e.printStackTrace();
	  }
	  return 0;
  }

  public Board.Direction getDirection() {
	  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	  try {
		String s = br.readLine();
		for(char c : s.toCharArray()) {
			if(java.lang.Character.isDigit(c)) {
				System.out.println("Invalid input, try again.");
				return null;
			}
		}
		if(s.equals("UP") || s.equals("Up") || s.equals("up")) {
			return Board.Direction.UP;
		}
		else if(s.equals("DOWN") || s.equals("Down") || s.equals("down")) {
			return Board.Direction.DOWN;
		}
		else if(s.equals("LEFT") || s.equals("Left") || s.equals("left")) {
			return Board.Direction.LEFT;
		}
		else if(s.equals("RIGHT") || s.equals("Right") || s.equals("right")) {
			return Board.Direction.RIGHT;
		}
		else {
			System.out.println("Invalid input, try again.");
			return null;
		}
	  } catch (IOException e) {
		e.printStackTrace();
	  }
	  return null;
  }

  public void passTurn() {
	  playersTurn++;
	  if(playersTurn > numberPlayers) {
		  playersTurn = 1;
	  }
  }

  public int getTurn() {
	  return playersTurn;
  }

  public boolean isGamewon() {
	  return gameWon;
  }
  
  public void setGamewon() {
	  gameWon = true;
  }

  public void setNumPlayers(int i) {
	  numberPlayers = i;
  }
  
  public Boolean playerAccChoice() {
	  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	  try {
		String s = br.readLine();
		for(char c : s.toCharArray()) {
			if(java.lang.Character.isDigit(c)) {
				System.out.println("Invalid input, try again.");
				return playerAccChoice();
			}
		}
		if(s.equals("YES") ||s.equals("Yes") || s.equals("yes")) {
			return true;
		}
		if(s.equals("NO") ||s.equals("No") || s.equals("no")) {
			return false;
		}
	  }catch (IOException e) {
			e.printStackTrace();
		  }
	  return false;
  }

  public List<Card> makeSuggestion(List<Player> players, Room room) {
	  List<Card> suggestion = new ArrayList<Card>();
	  // ask the player to nominate a weapon and killer from the room they are in.
	  System.out.println("Player "+ playersTurn + " is in the " + room.getName() + ".");
	  // room is added to suggestion pile
	  suggestion.add(room);
	  System.out.println("What was the murder weapon? (Dagger, Revolver, Lead Pipe, Rope, Spanner, Candlestick.)");
	  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	  try {
		Card weapon = null;
		while(weapon == null) {
			String s = br.readLine();
			if(s.equals("Dagger") || s.equals("Revolver") || s.equals("Lead Pipe") || s.equals("Rope") || s.equals("Spanner") || s.equals("Candlestick")) {
				for(Player p : players) {
					for(Card c : p.getHand()) {
						if(s.equals(c.getName())) {
							System.out.println("Player " + p.getNumber() + " refutes the Weapon Decision");
						}
					}
				}
				weapon = new Weapon(s);
				suggestion.add(weapon);
			}
			else {
				System.out.println("Invalid weapon, try again.");
			}
		}
		System.out.println("Who was the killer? (Mr Green, Professor Plum, Mrs White, Mrs Peacock, Miss Scarlet, Colonel Mustard");
		Card killer = null;
		while(killer == null) {
			String s = br.readLine();
			if(s.equals("Mr Green") || s.equals("Professor Plum") || s.equals("Mrs White") || s.equals("Mrs Peacock") || s.equals("Miss Scarlet") || s.equals("Colonel Mustard")) {
				for(Player p : players) {
					for(Card c : p.getHand()) {
						if(s.equals(c.getName())) {
							System.out.println("Player " + p.getNumber() + " refutes the Character Decision");
						}
					}
				}
				killer = new CluedoCharacter(s, 'x');
				suggestion.add(killer);
			}
			else {
				System.out.println("Invalid suspect, try again.");
			}
		}
		for(Player p : players) {
			for(Card c : p.getHand()) {
				if(room.getName().equals(c.getName())) {
					System.out.println("Player " + p.getNumber() + " refutes the Room Decision");
				}
			}
		}
	  }
	  
	  catch (IOException e) {
			e.printStackTrace();
	  }

	  // return suggestion packet.
	  return suggestion;
  }
  public List<Card> makeAccusation(List<Player> players, Room room) {
	  List<Card> accusation = new ArrayList<Card>();
	  // ask the player to nominate a weapon and killer from the room they are in.
	  System.out.println("Player "+ playersTurn + " is in the " + room.getName() + ".");
	  // room is added to suggestion pile
	  System.out.println("What was the murder weapon? (Dagger, Revolver, Lead Pipe, Rope, Spanner, Candlestick.)");
	  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	  try {
		Card weapon = null;
		while(weapon == null) {
			String s = br.readLine();
			if(s.equals("Dagger") || s.equals("Revolver") || s.equals("Lead Pipe") || s.equals("Rope") || s.equals("Spanner") || s.equals("Candlestick")) {
				weapon = new Weapon(s);
				accusation.add(weapon);
			}
			else {
				System.out.println("Invalid weapon, try again.");
			}
		}
		Card roomCard = null;
		roomCard = room;
		accusation.add(roomCard);
		System.out.println("Who was the killer? (Mr Green, Professor Plum, Mrs White, Mrs Peacock, Miss Scarlet, Colonel Mustard");
		Card killer = null;
		while(killer == null) {
			String s = br.readLine();
			if(s.equals("Mr Green") || s.equals("Professor Plum") || s.equals("Mrs White") || s.equals("Mrs Peacock") || s.equals("Miss Scarlet") || s.equals("Colonel Mustard")) {
				killer = new CluedoCharacter(s, 'x');
				accusation.add(killer);
			}
			else {
				System.out.println("Invalid suspect, try again.");
			}
		}
	  }
	  catch (IOException e) {
			e.printStackTrace();
	  }

	  // return suggestion packet.
	  return accusation;
  }
}