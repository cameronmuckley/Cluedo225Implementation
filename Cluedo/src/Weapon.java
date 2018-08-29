/**
 * Represents a character card.
 */

public class Weapon extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
	private String name;
	private String type;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Weapon(String name)
  {
	super();
    this.name = name;
    this.type = "Weapon";
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getName() {
	  return this.name;
  }

  public char getLetter() {
	  return this.name.charAt(0);
  }

  @Override
  public String getType() {
	  return this.name;
  }

}