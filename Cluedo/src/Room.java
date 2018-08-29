/**
 * Represents a character card.
 */

public class Room extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
	private String name;
	private char letter;



  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(String name, char letter)
  {
	  super();
	  this.name = name;
	  this.letter = letter;
  }

  //------------------------
  // INTERFACE
  //------------------------
  public String getName() {
	  return this.name;
  }

  public char getLetter() {
	  return this.letter;
  }

}