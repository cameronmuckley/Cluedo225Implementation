import java.util.ArrayList;
import java.util.List;

public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
	private String name;
	private List<Card> hand = new ArrayList<Card>();
	private CluedoCharacter character;
	private int number;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String name,int number, CluedoCharacter character)
  {
    this.name = name;
    this.number = number;
    this.character = character;
    character.setActivePlayer(this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void addCard(Card c) {
	  this.hand.add(c);
  }

  public List<Card> getHand(){
	  return this.hand;
  }

  public String getName() {
	  return this.name;
  }

  public CluedoCharacter getChar() {
	  return this.character;
  }

  public int getNumber() {
	  return this.number;
  }

  public String getHandString() {
	  String s = "";
	  for(Card c : hand) {
		  s += c.getName() + ", ";
	  }
	  return s;
  }

  public Player getFromchar(char c) {
	  if(this.getChar().getSymbol() == c) {
		  return this;
	  }
	  else {
		  return null;
	  }
  }


}