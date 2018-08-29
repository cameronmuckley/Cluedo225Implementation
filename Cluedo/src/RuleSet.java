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
  /**
   * sets number of players in the game to the supplied input.
   * @param str
   * @return
   */
  public int setPlayers(String str) {
		String s = str;
		// see of input is valid
		for(char c : s.toCharArray()) {
			if(!java.lang.Character.isDigit(c)) {
				return 0;
			}
		}
		int	i = Integer.parseInt(s);
		if(i >=3 && i <=6) {
			return i;
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
  
  /**
   * returns current player's turn.
   * @return
   */

  public int getTurn() {
	  return playersTurn;
  }
  
  /**
   * returns gamewon boolean.
   * @return
   */

  public boolean isGamewon() {
	  return gameWon;
  }
  /**
   * sets gamewon boolean.
   */
  public void setGamewon() {
	  gameWon = true;
  }
  
  /**
   * sets number of players.
   * @param i
   */

  public void setNumPlayers(int i) {
	  numberPlayers = i;
  }
}