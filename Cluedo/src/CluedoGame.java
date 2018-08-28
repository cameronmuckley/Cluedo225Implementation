	import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class CluedoGame extends GUI
{
	// game object fields;
	private Board board;
	private List<Player> players;
	private RuleSet rules;
	private List<Card> cards;
	private List<Card> murderEnvelope;
	private int numPlayers = 0;
	private List<Room> roomList;

	// dice
	int die1;
	int die2;



  public CluedoGame()
  {
	  // PREPARE GAME OBJECTS
	  initalizeGame();
	  // START GAME
	  getTextOutputArea().setText("Welcome to Adrian and Cameron's implementation of Cluedo!\n");
	  getTextOutputArea().append("How many of you are playing today? (Input a number between 3-6)\n");  
  }
  
  public void initalizeGame() {
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
  }
  
  public void playGame() {	
	getTextOutputArea().setText("Great, you've selected " + numPlayers + " players./n");
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
		  getTextOutputArea().append( "Player " + (i+1) + " is " + players.get(i).getName() + "/n");
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
	  // MAIN GAME LOOP
	  		String murderAnswer = "";
		  for(Card mur : murderEnvelope) {
			  murderAnswer += mur.getName();
		  }
		  redraw();
		  // start current turn
		  // TODO can player move
		  getTextOutputArea().setText("It's player "+ rules.getTurn() + ", " + players.get(rules.getTurn()-1).getName() + "'s turn. /n");
		  getTextOutputArea().append("Cards in Player " + rules.getTurn() + "'s hand: " + players.get(rules.getTurn()-1).getHandString() + "/n");
		  getTextOutputArea().append("Roll the dice/n");

		  //TODO move character
		 
}
	  
@Override
protected void  rollDice() {
	if(!players.get(rules.getTurn()-1).diceRolled) {
		die1 = (int) (Math.random()*6)+1;
		die2 = (int) (Math.random()*6)+1;
		players.get(rules.getTurn()-1).diceRolled = true;
		players.get(rules.getTurn()-1).turnMoves = die1 + die2;
		getTextOutputArea().append("Player " + rules.getTurn() + " rolled " + (players.get(rules.getTurn()-1).turnMoves) + "/n");
	}
	else {
		getTextOutputArea().append("Player already rolled");
	}
}
  
protected void makeMove(Board.Direction dir) {
	if(players.get(rules.getTurn()-1).turnMoves == 0) {
		getTextOutputArea().append("Please roll the dice");
	}
	 while(players.get(rules.getTurn()-1).turnMoves != 0) {
		  getTextOutputArea().setText("You have: " + players.get(rules.getTurn()-1).turnMoves + " remaining /n");
		  getTextOutputArea().append("Which direction would you like to go? /n");
		  board.moveCharacter(players.get(rules.getTurn()-1).getChar(), dir);
		  //TODO check if character is in a room
		  /*for(Room r : roomList) {
			  if(players.get(rules.getTurn()-1).getChar().getCurrentTile() == r.getLetter()) {
				  getTextOutputArea().setText("Would you like to make an Accusation? If not you will Suggest. /n");
				  if(rules.playerAccChoice()) {
					  List<Card> accusation = new ArrayList<Card>();
					  accusation = rules.makeAccusation(players, r);
					  int winCount = 0;
					  int envCount = 0;
					  for(Card winC : accusation) {
						  if(winC.getName().equals(murderEnvelope.get(envCount).getName())) {
							  winCount++;
						  }
						  envCount++;
					  }
					  if(winCount == 3) {
						  getTextOutputArea().setText("Player " + players.get(rules.getTurn()-1).getNumber() + " wins! /n");
						  rules.setGamewon();  
					  }	  
					  else {
						  getTextOutputArea().setText("That was wrong!");
					  }
				  }
				  else {
					  rules.makeSuggestion(players,r);
				  }
				  
				  turnMoves = 1;
			  }
		  }*/
		  players.get(rules.getTurn()-1).turnMoves--;
	  }
	  try {
		Thread.sleep(2000);
	  } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	  players.get(rules.getTurn()-1).diceRolled = false;
	  rules.passTurn();
 }

@Override
protected void redraw(Graphics g) {
	board.draw(g,players);	
}



@Override
protected void onClick(MouseEvent e) {
	// TODO Auto-generated method stub
	
}



@Override
protected void onMove(Direction d) {
	// TODO Auto-generated method stub
	
}

@Override
protected void onInput() {
	if(numPlayers == 0) {
		int tempNum = rules.setPlayers(getSearchBox().getText());
		if(tempNum != 0) {
			numPlayers = tempNum;
			playGame();
		}
	}
}

public static void main(String[] args) {
	new CluedoGame();
}
}