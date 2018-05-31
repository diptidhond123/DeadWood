import java.util.*;
import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import java.util.HashMap;


public class GameManager{

  //static Room[] rooms;
  static Map<String, SetRoom> setRooms=new HashMap();
  static CastingRoom castRoom;
  static Room startingRoom;
  static ArrayList<SceneCard> cardArray=new ArrayList();
  static Player[] players;
  static int daysRemaining=0;
  static int numPlayers;
  
   //card file
   static File xmlCardFile = new File("cards.xml");
   //board file
   static File xmlBoardFile = new File("board.xml");
  
  //main runs the game
  public static void main(String[] args){
      System.out.println("Welcome to DeadWood");
    	System.out.println();
      
      //prompts user for number of players
      Scanner scan = new Scanner(System.in);
      
      numPlayers=0;
      while(numPlayers<2 || numPlayers>8){
         System.out.println("Please enter the number of players(2-8)");
         System.out.print(">");
         numPlayers = scan.nextInt();
      }
      
      readCards();
      configureRooms();
      distributeCards();
      
      
      //startingRoom=new Room("Starting Room","trailer");
      players=makePlayers(numPlayers, startingRoom);
            
      
      //main game loop:
      while(daysRemaining>0){
         System.out.println("There are " + daysRemaining + " days left.");
    		System.out.println();
         
         //reset player positions
         for(Player player : players) {
    			player.setPosition(startingRoom);
    		}
         //day progresses when 9 scenes are shot
         int scenesShot = 0;
    		while (scenesShot < 9) {            
    			int playerNum = 0; 
    			for(Player player : players) {
            System.out.println();
               player.info();
    				playerNum++;
    				System.out.println("Player "+ playerNum + " it is your turn");
    				System.out.println("Type 'help' to view actions");

    				String input = scan.nextLine();
    				boolean turnOver = false; 
    				boolean moved = false; 
    				while(!turnOver) {
                  //options available for the player:
                  //info, where, move, work, upgrade, rehearse, act, or end.
    					String[] inputArray = input.split("\\s+");
    					switch(inputArray[0]) {
    					case "info":
    						player.info(); 
    						break; 
    					case "where":
    						player.where(); 
    						break; 
    					case "move": 
    						if (inputArray.length == 1) {
    							player.move("none"); 
    						} else if (!moved) {
    							String roomName = mergeString(inputArray);
    							moved = player.move(roomName.toLowerCase());
    						} else {
    							System.out.println("You have already moved, you cannot move again.");
    						}
    						break; 
    					case "work": 
    						if (inputArray.length == 1) {
    							player.work("none"); 
    						} else {
    							String roleName = mergeString(inputArray); 
    							turnOver = player.work(roleName.toLowerCase());
    						}
    						break; 
    					case "upgrade":
                     if(inputArray.length == 1){
                        turnOver = player.upgrade("none", 0);
                     }else if(inputArray.length == 3){
                        if(inputArray[1].equals("m")){
                           turnOver = player.upgrade("money", Integer.parseInt(inputArray[2]));
                        }else if(inputArray[1].toLowerCase().equals("f")){
                           turnOver = player.upgrade("fame", Integer.parseInt(inputArray[2]));                        
                        }else{
                           System.out.println("That is an invalid  type to upgrade, type 'm' to pay with cash, and 'f' to pay with fame.");
                        }
                     }else{
                        System.out.println("We need three inputs: 'upgrade,' 'm(oney)/f(ame),' 'desired rank'");
                     }
    						break; 
    					case "rehearse": 
    						if(!moved) {
    							turnOver = player.rehearse(); 
    						} else {
    							System.out.println("You may not rehearse because you just moved.");
    						}
    						break;
    					case "act": 
    						if(!moved) {
    							int res = player.act(player);
    							if(res == 0) {
    								// no role, do nothing 
    							} else if (res == 1) {
                           //faliure
    								turnOver = true; 
    							} else if (res == 2) {
                           //success
    								scenesShot++; 
    								turnOver = true; 
    							}
    						} else {
    							System.out.println("You may not act because you just moved.");
    						}
    						break; 
    					case "end": 
    						turnOver = true; 
    						break;
    					case "help":
    						printHelp(); 
    						break;
    					}

    					if(!turnOver) {
    						System.out.print("> ");
    						input = scan.nextLine(); 
    					}
    				}
    			}
    		}
    		endDay();
      }
      displayWinner();
  }//end of main
   
   public static SetRoom findSetRoom(Room position){
       for (String key : setRooms.keySet()) {
            if(position.getName().equals(key)){
               return setRooms.get(key);
            }
       }
         System.out.println("No such SetRoom found");
         return null;
   }
   public static Room newPosition(Room newRoom){
      if(newRoom.getName().equals("Starting Room")){
         return startingRoom;
      }
      else if(newRoom.getName().equals("office")){
         return castRoom; 
      }
      else{
         for (String key : setRooms.keySet()) {
            if(newRoom.getName().equals(key)){
               return setRooms.get(key);
            }
         }
      }
      System.out.println("Something went wrong, moved to trailer");
      return startingRoom;
   }
   
   public static void configureRooms(){
      ArrayList<Room> rooms=new ArrayList<Room>();
      try{
         rooms=ParseXMLFile.readBoardData(ParseXMLFile.getDocFromFile(xmlBoardFile));
      }
      catch(ParserConfigurationException e){
         System.out.println("Parser error");
      }
      for(Room room:rooms){
         if (room.getRoomType().equals("set")){
            setRooms.put(room.getName(),(SetRoom)room);
         }
            
         if(room.getRoomType().equals("trailer")){
            startingRoom=room;
            System.out.println("Starting room set");
            
         }
         if(room.getRoomType().equals("office")){
            castRoom=(CastingRoom)room;
            System.out.println("casting room set");
         }
      }   
  }
  public static void readCards(){
   
      try{
         cardArray=ParseXMLFile.readCardData(ParseXMLFile.getDocFromFile(xmlCardFile));
         System.out.println("cards read");
      }
      
      catch(ParserConfigurationException e){
         System.out.println("Parser error");
      }
  }
  
  public static void distributeCards(){
      int i=0;
      for (String key : setRooms.keySet()) {
         boolean loop=true;
         while(loop &&i<cardArray.size()){
            if(cardArray.get(i).isAvailable()){
               //System.out.println(key+" has been given card: "+cardArray.get(i).getTitle());
               setRooms.get(key).setCard(cardArray.get(i));
               cardArray.get(i).changeAvailability();
               setRooms.get(key).resetShots();
               for(Role role:setRooms.get(key).getCurrentCard().getRolesonSceneCard()){
                  role.setCard(setRooms.get(key).getCurrentCard());
                  //System.out.println("role's card's title: "+role.getCard().getTitle());
                  //System.out.println("role's card's budget: "+role.getCard().getBudget());
               }
               //System.out.println(setRooms.get(key).getCurrentCard().getTitle());
               //for(Role role:setRooms.get(key).getCurrentCard().getRolesonSceneCard()){
               //   System.out.println("Role name: "+role.getName());
               //}
               
               cardArray.get(i).changeAvailability();
               loop=false;
            }
            else{
             i++;
            }
         }
         if(i==cardArray.size()){
            System.out.println("Out of cards");
         }
      }
  }
  
  public static void endDay(){
   daysRemaining--;
   distributeCards();
  }

  public Room getStartingRoom(){
      return startingRoom;
  }

  public int getDaysRemianing(){
      return daysRemaining;
  }

  public void setStartingRoom(Room startRoom){
      startingRoom= startRoom;
  }

  public void setDaysRemianing(int remainingDays){
      daysRemaining=remainingDays;
  }
  
  private static String mergeString(String[] strArray){
		String str = "";
		for(int i = 1; i < strArray.length; i++) {
			str = str + " " + strArray[i]; 
		}
		return str.substring(1); 	
	}
   
   private static void printHelp() {
		System.out.println("Here is a list of your possible actions:");
		System.out.println("info");
		System.out.println("   >Shows your status");
		System.out.println("where");
		System.out.println("   >Shows your position on the board");
		System.out.println("move [ROOM]");
		System.out.println("   >move to view adjacent rooms. 'move [ROOM]' to move there");
		System.out.println("work [ROLE]");
		System.out.println("   >work to view available roles to take. 'work [ROLE]' to take role");
		System.out.println("upgrade");
		System.out.println("   >view possible upgrades while in casting office.");
      System.out.println("upgrade [M(oney)/F(ame)] [RANK]' to upgrade to a certain rank using money ($) or credits (cr).");
		System.out.println("rehearse");
		System.out.println("   >rehearse a role");
		System.out.println("act");
		System.out.println("   >act a role");
		System.out.println("end");
		System.out.println("   >end your turn");
	}
   
   private static Player[] makePlayers(int numPlayers, Room trailer) {
		Player[] players = new Player[numPlayers];
		int bonusFame = 0; 
		int bonusRank = 1;
      daysRemaining = 4;
		
		switch(numPlayers) {
		case 2:  
		case 3: 
			daysRemaining = 3; 
			break; 
		case 5: 
			bonusFame = 2; 
			break; 
		case 6: 
			bonusFame = 4; 
			break; 
		case 7: 
		case 8:
			bonusRank = 2;
			break; 
		}
				
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new Player("Player " + (i + 1),trailer,0,bonusRank, bonusFame); 
		}
      		
		return players; 
	}
   public static void displayWinner(){
      Player winner=players[1];
      int winnerScore=0;
      
      for (Player player: players){
         int pScore=player.calculateScore();
         System.out.println(player.getName() +" score: "+pScore);
         if(pScore>=winnerScore){
            winner=player;
            winnerScore=pScore;
         }
      }
      System.out.println();
      System.out.println("The winner is: "+winner.getName() +" with "+winnerScore+" points! Congradulations!");
   }

}

