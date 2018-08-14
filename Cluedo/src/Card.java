import java.util.*;

public class Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
	
  

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Card()
  {
    
  }

  //------------------------
  // INTERFACE
  //------------------------
  public Card get() {
	  return this;
  }

  public String getType() {
	return "Card";
  }
  
  public String getName() {
	  System.out.println("This should never be called.");
	  return null;
  }
}