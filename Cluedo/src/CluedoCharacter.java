/**
 * Represents a character card.
 */

public class CluedoCharacter extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
	private int id;
	private String name;
	private char symbol;
	private char currentTile = 'p';
	private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CluedoCharacter(String name, char symbol)
  {
    super();
    this.name = name;
    this.symbol = symbol;
  }

  //------------------------
  // INTERFACE
  //------------------------
  public int getID() {
	  return this.id;
  }

  public String getName() {
	  return this.name;
  }

  public char getSymbol() {
	  return this.symbol;
  }

  public char getCurrentTile() {
	  return this.currentTile;
  }

  public void setCurrentTile(char c) {
	  this.currentTile = c;
  }

  public void setActivePlayer(Player p) {
	  this.player = p;
  }

  public Player getPlayer() {
	  return this.player;
  }

}