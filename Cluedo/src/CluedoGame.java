	import java.util.*;


public class CluedoGame
{
	// game object fields;
	private Board board;
	private List<Player> players;
	private RuleSet rules;
	private List<Card> cards;
	private List<Card> murderEnvelope;
	private int numPlayers;
	private List<Room> roomList;

	// dice
	int die1;
	int die2;



  public CluedoGame()
  {
	  // PREPARE GAME OBJECTS
	  board = new Board();
	  players = new ArrayList<Player>();
	  rules = new RuleSet();
	  cards = new Stack<Card>();
	  murderEnvelope = new ArrayList<Card>();
	  roomList = new ArrayList<Room>();
	  roomList.add(new Room("Kitchen",'k'));
	  roomList.add(new Room("Ballroom",'b'));
	  roomList.add(new Room("Conservatory",'c'));
	  roomList.add(new Room("Dining Room",'d'));
	  roomList.add(new Room("Billiard Room",'q'));
	  roomList.add(new Room("Library",'l'));
	  roomList.add(new Room("Lounge",'z'));
	  roomList.add(new Room("Hall",'h'));
	  roomList.add(new Room("Study",'s'));
	  numPlayers = 0;
	  // build card deck of all the unique cards, while generating murder circumstances
	  // add weapons
	  Stack<Card> weapons = new Stack<Card>();
	  weapons.add(new Weapon("Candlestick"));
	  weapons.add(new Weapon("Dagger"));
	  weapons.add(new Weapon("Lead Pipe"));
	  weapons.add(new Weapon("Revolver"));
	  weapons.add(new Weapon("Rope"));
	  weapons.add(new Weapon("Spanner"));
	  // shuffle weapons and add one to envelope
	  Collections.shuffle(weapons);
	  murderEnvelope.add(weapons.pop());
	  // add remaining cards to deck
	  while(!weapons.isEmpty()) {
		  cards.add(weapons.pop());
	  }

	  // add rooms
	  Stack<Card> rooms = new Stack<Card>();
	  rooms.add(new Room("Kitchen",'k'));
	  rooms.add(new Room("Ballroom",'b'));
	  rooms.add(new Room("Conservatory",'c'));
	  rooms.add(new Room("Dining Room",'d'));
	  rooms.add(new Room("Billiard Room",'q'));
	  rooms.add(new Room("Library",'l'));
	  rooms.add(new Room("Lounge",'z'));
	  rooms.add(new Room("Hall",'h'));
	  rooms.add(new Room("Study",'s'));
	  // shuffle rooms and add one to envelope
	  Collections.shuffle(rooms);
	  murderEnvelope.add(rooms.pop());
	  // add remaining cards to deck
	  while(!rooms.isEmpty()) {
		  cards.add(rooms.pop());
	  }

	  // add characters
	  Stack<Card> characters = new Stack<Card>();
	  characters.add(new CluedoCharacter("Miss Scarlet",'4'));
	  characters.add(new CluedoCharacter("Colonel Mustard",'5'));
	  characters.add(new CluedoCharacter("Mrs White",'0'));
	  characters.add(new CluedoCharacter("Mr Green",'1'));
	  characters.add(new CluedoCharacter("Mrs Peacock",'2'));
	  characters.add(new CluedoCharacter("Professor Plum",'3'));
	  // shuffle characters and add one to envelope
	  Collections.shuffle(characters);
	  murderEnvelope.add(characters.pop());
	  // add remaining cards to deck
	  while(!characters.isEmpty()) {
		  cards.add(characters.pop());
	  }

	  // START GAME
	  System.out.println("Welcome to Adrian and Cameron's implementation of Cluedo!");
	  // first set number of players
	  System.out.println("How many of you are playing today? (Input a number between 3-6)");
	  // number of players is set at 0 by default and invalid input returns 0.
	  while(numPlayers == 0) {
		  numPlayers = rules.setPlayers();
	  }
	  System.out.println("Great, you've selected " + numPlayers + " players.");
	  rules.setNumPlayers(numPlayers);
	  // create players character tokens
	  Stack<Card> tokens = new Stack<Card>();
	  tokens.add(new CluedoCharacter("Miss Scarlet",'4'));
	  tokens.add(new CluedoCharacter("Colonel Mustard",'5'));
	  tokens.add(new CluedoCharacter("Mrs White",'0'));
	  tokens.add(new CluedoCharacter("Mr Green",'1'));
	  tokens.add(new CluedoCharacter("Mrs Peacock",'2'));
	  tokens.add(new CluedoCharacter("Professor Plum",'3'));
	  // shuffle characters
	  Collections.shuffle(tokens);
	  // add players character names and numbers
	  for(int i = 0; i<numPlayers; i++) {
		  CluedoCharacter c = (CluedoCharacter) tokens.pop();
		  players.add(new Player(c.getName(),i+1,c));
		  System.out.println("Player " + (i+1) + " is " + players.get(i).getName());
	  }

	  // deal cards
	  Collections.shuffle(cards);
	  int count = 0;
	  for(Card c : cards) {
		  players.get(count).addCard(c);
		  count++;
		  if(count == players.size()) {
			  count = 0;
		  }
	  }

	  // now that we have players, cards in hands, a board and murder circumstances, begin the main game loop.
	  // MAIN GAME LOOP
	  while(!rules.isGamewon()) {
		  String murderAnswer = "";
		  for(Card mur : murderEnvelope) {
			  murderAnswer += mur.getName();
		  }
		  System.out.println(murderAnswer);
		  board.draw(players);
		  // start current turn
		  // TODO can player move
		  System.out.println("It's player "+ rules.getTurn() + ", " + players.get(rules.getTurn()-1).getName() + "'s turn.");
		  System.out.println("Cards in Player " + rules.getTurn() + "'s hand: " + players.get(rules.getTurn()-1).getHandString());
		  System.out.println("Rolling dice.....");
		  die1 = (int) (Math.random()*6)+1;
		  die2 = (int) (Math.random()*6)+1;
		  int turnMoves = die1 + die2;
		  System.out.println("Player " + rules.getTurn() + " rolled " + (turnMoves));

		  //TODO move character
		  while(turnMoves != 0) {
			  System.out.println("You have: " + turnMoves + " remaining");
			  System.out.println("Which direction would you like to go? (UP,DOWN,LEFT,RIGHT)");
			  board.moveCharacter(players.get(rules.getTurn()-1).getChar(), rules.getDirection());
			  board.draw(players);
			  //TODO check if character is in a room
			  for(Room r : roomList) {
				  if(players.get(rules.getTurn()-1).getChar().getCurrentTile() == r.getLetter()) {
					  System.out.println("Would you like to make an Accusation? If not you will Suggest.");
					  if(rules.playerAccChoice()) {
						  List<Card> accusation = new ArrayList<Card>();
						  accusation = rules.makeAccusation(players, r);
						  int winCount = 0;
						  int envCount = 0;
						  for(Card winC : accusation) {
							  System.out.println(envCount + " " + winC.getName());
							  System.out.println(envCount + " " + murderEnvelope.get(envCount).getName());
							  if(winC.getName().equals(murderEnvelope.get(envCount).getName())) {
								  winCount++;
							  }
							  envCount++;
						  }
						  if(winCount == 3) {
							  System.out.println("Player " + players.get(rules.getTurn()-1).getNumber() + " wins!");
							  rules.setGamewon();  
						  }	  
						  else {
							  System.out.println(winCount);
							  System.out.println("That was wrong!");
						  }
					  }
					  else {
						  rules.makeSuggestion(players,r);
					  }
					  
					  turnMoves = 1;
				  }
			  }
			  turnMoves--;
		  }
		  try {
			Thread.sleep(2000);
		  } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		  rules.passTurn();
	  }
  }


  public static void main(String[] args) {
		new CluedoGame();
	}
}