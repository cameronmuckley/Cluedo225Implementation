import java.awt.Color;
import java.awt.Graphics;
import java.util.*;
/**
 * This class represents the board in the game. 
 * It manages position data, as well as draws the board.
 *
 */
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
	private final int BOARD_WIDTH = 26;
	private final int BOARD_HEIGHT = 27;
	private final int TILE_SIZE = 10;
	private char[][] board = new char[BOARD_HEIGHT][BOARD_WIDTH];

	public enum Direction{
		  RIGHT,
		  LEFT,
		  UP,
		  DOWN
	}


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board()
  {
	  //Save the String to store the board values
	  String boardVals;
	  //Create the board line by line
	  boardVals =  "xxxxxxxxxxxxxxxxxxxxxxxxxx";
	  boardVals += "xxxxxxxxxx0xbbx1xxxxxxxxxx";
	  boardVals += "xkkkkkxxpppxbbxpppxxcccccx";
	  boardVals += "xkkkkkxppxxxbbxxxppxcccccx";
	  boardVals += "xkkkkkxppxbbbbbbxppxcccccx";
	  boardVals += "xkkkkkxppxbbbbbbxppccccccx";
	  boardVals += "xkkkkkxppbbbbbbbbpppxxxxxx";
	  boardVals += "xxxxkxxppxbbbbbbxppppppp2x";
	  boardVals += "xppppppppxbxxxxbxpppppppxx";
	  boardVals += "xxpppppppppppppppppxxxxxxx";
	  boardVals += "xxxxxxpppppxxxxxpppqqqqqqx";
	  boardVals += "xddddxxxxppxxxxxpppxqqqqqx";
	  boardVals += "xdddddddxppxxxxxpppxqqqqqx";
	  boardVals += "xddddddddppxxxxxpppxxxxxxx";
	  boardVals += "xdddddddxppxxxxxppppppppxx";
	  boardVals += "xdddddddxppxxxxxpppxxxxxxx";
	  boardVals += "xxxxxxdxxppxxxxxppxllllllx";
	  boardVals += "xxpppppppppxxxxxpplllllllx";
	  boardVals += "x5ppppppppppppppppxllllllx";
	  boardVals += "xxppppppppxxhhxxpppxxxxxxx";
	  boardVals += "xxxxxxzxppxhhhhxpppppppp3x";
	  boardVals += "xzzzzzzxppxhhhhxppppppppxx";
	  boardVals += "xzzzzzzxppxhhhhxppxsxxxxxx";
	  boardVals += "xzzzzzzxppxhhhhxppxssssssx";
	  boardVals += "xzzzzzzxppxhhhhxppxssssssx";
	  boardVals += "xzzzzzzx4xxhhhhxppxssssssx";
	  boardVals += "xxxxxxxxxxxxxxxxxxxxxxxxxx";
	  int count = 0;
	  for(int i=0; i < BOARD_HEIGHT; i++) {
		  for(int j=0; j < BOARD_WIDTH; j++) {
				  board[i][j] = boardVals.charAt(count);
				  count++;
		  }
	  }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /**
   * takes inputted movement data and adjusts character position accordingly.
   * @param c
   * @param d
   */
  public void moveCharacter(CluedoCharacter c, Direction d) {
	  for(int i=0; i < BOARD_HEIGHT; i++) {
		  for(int j=0; j < BOARD_WIDTH; j++) {
			  if(board[i][j] == c.getSymbol()) {
				  if(d.equals(Direction.UP)) {
					  if(!((i-1) < 0)) {
						  if(board[i-1][j] == 'x') {
							  return;
						  }
						  board[i][j] = c.getCurrentTile();
						  c.setCurrentTile(board[i-1][j]);
						  board[i-1][j] = c.getSymbol();
						  return;
					  }
				  }
				  else if(d.equals(Direction.DOWN)) {
					  if(!((i+1) > BOARD_HEIGHT-1)) {
						  if(board[i+1][j] == 'x') {
							  return;
						  }
						  board[i][j] = c.getCurrentTile();
						  c.setCurrentTile(board[i+1][j]);
						  board[i+1][j] = c.getSymbol();
						  return;
					  }
				  }
				  else if(d.equals(Direction.LEFT)) {
					  if(!((j-1) < 0)) {
						  if(board[i][j-1] == 'x') {
							  return;
						  }
						  board[i][j] = c.getCurrentTile();
						  c.setCurrentTile(board[i][j-1]);
						  board[i][j-1] = c.getSymbol();
						  return;
					  }
				  }
				  else if(d.equals(Direction.RIGHT)) {
					  if(!((j+1) > BOARD_WIDTH-1)) {
						  if(board[i][j+1] == 'x') {
							  return;
						  }
						  board[i][j] = c.getCurrentTile();
						  c.setCurrentTile(board[i][j+1]);
						  board[i][j+1] = c.getSymbol();
						  return;

					  }
				  }
				  else {
					  return;
				  }
			  }
		  }
	  }
  }
  
  /**
   * draws the board to the JFrame.
   * @param g
   * @param players
   */

  public void draw(Graphics g,List<Player> players) {
	  List<Character> activeChar = new ArrayList<Character>();
	  for(Player p : players) {
		  activeChar.add(p.getChar().getSymbol());
	  }
	  for(int i=0; i < BOARD_HEIGHT; i++) {
		  for(int j=0; j < BOARD_WIDTH; j++) {
			  if(board[i][j] == 'p') {
				  g.setColor(Color.GRAY);
				  g.fillRect((i*TILE_SIZE), (j*TILE_SIZE), TILE_SIZE, TILE_SIZE);
				  g.setColor(Color.BLACK);
				  g.drawRect((i*TILE_SIZE), (j*TILE_SIZE), TILE_SIZE, TILE_SIZE);
			  }
			  else if(board[i][j] == 'x') {
				  g.setColor(Color.BLACK);
				  g.fillRect((i*TILE_SIZE), (j*TILE_SIZE), TILE_SIZE, TILE_SIZE);
			  }
			  else if(Character.isDigit(board[i][j])) {
				  if(activeChar.contains(board[i][j])) {
					  for(Player p : players) {
						  if(p.getFromchar(activeChar.get(activeChar.indexOf(board[i][j])))!=null) {
							  g.setColor(Color.YELLOW);
							  g.fillRect((i*TILE_SIZE), (j*TILE_SIZE), TILE_SIZE, TILE_SIZE);
							  g.setColor(Color.BLACK);
							  g.drawRect((i*TILE_SIZE), (j*TILE_SIZE), TILE_SIZE, TILE_SIZE);
							  g.drawString(String.valueOf(board[i][j]),(i*TILE_SIZE), (j*TILE_SIZE)+TILE_SIZE);
						  }
					  }
				  }
				  else {
					  g.setColor(Color.WHITE);
					  g.fillRect((i*TILE_SIZE), (j*TILE_SIZE), TILE_SIZE, TILE_SIZE);
					  g.setColor(Color.BLACK);
					  g.drawRect((i*TILE_SIZE), (j*TILE_SIZE), TILE_SIZE, TILE_SIZE);
				  }
			  }
		  }
	  }
  }
}