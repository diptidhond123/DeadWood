import java.util.*;
public class Player{

   private String name;
   private int rank;
   private int fame;
   private int money;
   private boolean hasRole;
   private int rehearsalCredits;
   Room position;
   Role currentRole;

//Constructors of Player class
   public Player(String name, Room position)  {
      this.name=name;
      this.position=position;
      rehearsalCredits=0;
      rank=0;
      fame=0;
      money=0;
      hasRole=false;
      currentRole=null;
  
   }

   public Player(String name, Room position, int money, int rank, int fame){
      this.name=name;
      this.position=position;
      rehearsalCredits=0;
      this.rank=rank;
      this.fame=fame;
      this.money=money;
      hasRole=false;
      currentRole=null;

   }

   public Player(){

   }
   

   //Functions of Player class
   public int calculateScore(){
      return money+fame+(5*rank);
   }
   
   public void leaveRole(){
      currentRole=null;
   }

   public boolean isActing(){
      return hasRole;
   }

   public void takeRole(){
      
   }

   public int act(Player player) {
      int retvalue = -1;
		if (hasRole) {
		   SetRoom scene = (SetRoom) position; 
			int roll = rollDice(1); 
			int budget = scene.getCurrentCard().getBudget(); 
			System.out.println("The budget for this scene is " + budget);
			System.out.println("You rolled a " + roll);
			System.out.println("You rehearsal count is " + rehearsalCredits);         
			if (roll + rehearsalCredits >= budget) {            
				System.out.println("You have succeeded!");
            int shots = scene.getNumShots();
				if (shots > 0) {
					System.out.println("There are " + (shots) + " shots left.");
					if(currentRole.isOnCard()) {
						System.out.println("You recieved 2 fame");
						fame = fame + 2; 
					} else {
						System.out.println("You recieved 1 fame and 1 dollar");
						fame++; 
						money++; 
					}
               scene.decreaseNumShots();
					retvalue = 1; 
				}if(shots == 0) {
					System.out.println("You have completed the scene!");
					hasRole = false;
               currentRole=null;
					int[] diceRolls=new int[scene.getCurrentCard().getBudget()];
               for(int i=0;i<diceRolls.length;i++){
                  diceRolls[i]=rollDice(1);
               }
					scene.distributeBonuses(diceRolls); 
					retvalue = 2; 
				}
			} else {
				System.out.println("You have failed."); 
				if(!currentRole.isOnCard()) {
					money++; 
				}
				retvalue = 1; 
			}
		} else {
			System.out.println("You cannot act because you haven't taken up a role yet.");
			retvalue = 0; 
		}
      return retvalue;
	}

   public boolean rehearse(){
      if(hasRole){
         if(rehearsalCredits<GameManager.findSetRoom(position).getCurrentCard().getBudget()){
            rehearsalCredits++;
            System.out.println("You have gained a rehearsal credit! You now have "+ rehearsalCredits);
            return true;
         }
         else{
            System.out.println("You have the maximum number of Rehearsal credits for this role");
            return false;
         }
      }
      else{
         System.out.println("You cannot rehearse because you don't have a role");
         return false;
      }
   }

   public int rollDice(int numDice){
      int total=0;
      Random diceRoll = new Random();
      
      for(int i=0;i<numDice;i++){
         total=total+diceRoll.nextInt(6)+1;
      }
      return total;
   }
   
   public void showName(){
      System.out.println("Name: "+name);
   }

   public void showLocation(){
      System.out.println("Current location: "+position.getName());
   }

   public void setRole(Role newRole){
      currentRole=newRole;
   }

   public Role getRole(){
      return currentRole;
   }
   
   public int getFame(){
      return fame;
   }
    
   public void changeFame(int fameAmount){
      fame = fame + fameAmount;
   }

   public Room getPosition(){
      return position;
   }

   public void setPosition(Room newRoom){
      position=newRoom;
   }

   public int getMoney(){
      return money;
   }
   public void changeMoney(int moneyAmount){
      money = money + moneyAmount;
   }
   
   public String getName(){
      return name;
   }

   public int getRank(){
      return rank;
   }

   public void setRank(int rank){
      this.rank=rank;
   }
   
   public void info(){
		System.out.print(name +" ($" + money + "," + fame + " fame)  Rank: " + rank+ " Total score: "+calculateScore());
		if(currentRole != null) {
			System.out.println(" working " + currentRole.getName() + ", " + currentRole.getPhrase());
		} else {
			System.out.println(); 
		}
	}
   
   public void where() {
		System.out.print("in ");
		if(position.getRoomType().equals("set")) {
			SetRoom scene = (SetRoom) position;
			scene.displayInfo(); 
		} 
      else{ 
			System.out.println(position.getName());
		}
	}
   
   public boolean move(String dest){
		if(!hasRole) {
			ArrayList <Room> moveOptions = this.position.getAdjacentRooms();
			//display adjacent rooms with no input
			if (dest.equals("none")) {
				System.out.println("The adjacent rooms you can move to are:");
				for(int i = 0; i < moveOptions.size(); i++){
					System.out.println("\t" + moveOptions.get(i).getName());
				}
				System.out.println("Type 'move' and the room you would like to move to (ie. 'move Saloon')"); 
				return false; 
			} else {
				for(int i = 0; i < moveOptions.size(); i++){
					if(moveOptions.get(i).getName().toLowerCase().equals(dest)){
						//position = moveOptions.get(i);
                  position = GameManager.newPosition(moveOptions.get(i)); 
						System.out.println("You have moved to " + moveOptions.get(i).getName());
						return true; 
					}     
				}
				System.out.println("That is not a valid room. Type 'move' to list the rooms you can move to");
				return false; 
			}
		} else {
			System.out.println("You can't move because you are currently acting.");
			return false; 
		}
	}
   
   public boolean work(String roleWanted) {
		if(!hasRole) {
			if (position.getRoomType().equals("set")) {
				SetRoom currentScene = (SetRoom)position;
				if(currentScene.getNumShots() == 0) {
					System.out.println("this scene is finished");
					return false; 
				} else {
					if(roleWanted.equals("none")) {
						currentScene.displayRoles(rank); 
						return false; 
					} else {
						 
						rehearsalCredits = 0; 
						currentRole = currentScene.takeRole(roleWanted, this);
                  if(currentRole!=null){
                     hasRole = true;
						   return true;

                  }
                  else{
                     return false;
                  }
					}
				}
			} else { 
				System.out.println("You are in " + position.getName() + ". You cannot take a role here.");
				return false; 
			}
		} else {
			System.out.println("You have already taken up a role.");
			return false; 
		}
	}
   
   public boolean upgrade(String type, int level) {
		if(position.getName().equals("office")) {
			CastingRoom cRoom = (CastingRoom) position;
			if(level > 1) {            				
            return cRoom.upgradeRank(type, this, level);
			} else {        
				cRoom.displayAvailableRanks(rank, fame, money); 
				return false; 
			}
		} else {
			System.out.println("You need to be in the casting office to upgrade your level");
			return false; 
		}
	}
   

} //end of Player class
