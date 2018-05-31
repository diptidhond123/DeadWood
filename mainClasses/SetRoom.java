import java.util.ArrayList;
import java.util.*;

//Complete
//possibly add displayInfo class?

public class SetRoom extends Room{

   //setRoom attributes
   private int numShots;
   private int maxNumShots;
   private SceneCard currentCard;
   private ArrayList<Role> roomRoles;
   private ArrayList<Coordinates> takes;

   //SetRoom constructors
   public SetRoom(String name, String type){
      super(name, type); 
   }

   public SetRoom(String name, String type, int shots, ArrayList<Role> roles, ArrayList<Room> roomsAdjacent, ArrayList<Coordinates> takes){
      roomName = name; 
		roomType = type; 
		numShots = shots;
      maxNumShots=shots;
      roomRoles=roles;
      adjacentRooms=roomsAdjacent;
      takes = takes;
   }
   
   
   //setRoom methods
   public ArrayList<Role> getRoomRoles(){
      return roomRoles;
   }
  
   public ArrayList<Role> getCardRoles(){
      return currentCard.getRolesonSceneCard();
   }
   
   public int getNumShots(){
      return numShots;
   }
   
   public void resetShots(){
      numShots=maxNumShots;
   }

   public void giveRole(Player player,Role role){
      player.setRole(role);
      role.addPlayer(player);
   }


   public SceneCard getCurrentCard(){
      return currentCard;
   }

   public void decreaseNumShots(){
      numShots--;
   }

   public void setCard(SceneCard card){
      currentCard = card;
   }
  
   public void displayInfo() {
		System.out.println(roomName + " shooting " + currentCard.getTitle());
	}
   
   //display all the roles player can take with their rank
   public void displayRoles(int rank) {
		ArrayList <Role> cardRoles = currentCard.getRolesonSceneCard();
      System.out.println("The scene's budget is: $" + currentCard.getBudget() + " million dollars, and has " + numShots + " shots left.");
		System.out.println("These are the starring roles you can take:");
      boolean roleAvailable = false;
		for(Role role : cardRoles) {         
			if(!role.isTaken() && (role.getrankLevel() <= rank) && !(role.getName().equals("0"))) {
				System.out.println("\t" + role.getName() + ": \"" + role.getPhrase()+ "\" which is level " + role.getrankLevel());
            roleAvailable = true;
			}
		}
      if(!roleAvailable){
         System.out.println("\t" + "There are no starring roles you can take!");
      }

		System.out.println("These are the extra roles you can take:");
      roleAvailable = false;
		for(Role role: roomRoles) {
			if(!role.isTaken() && (role.getrankLevel() <= rank) && !(role.getName().equals("0"))) {
				System.out.println("\t" + role.getName() + ": \"" + role.getPhrase()+ "\" which is level " + role.getrankLevel());
            roleAvailable = true;
			} 
		}
      if(!roleAvailable){
         System.out.println("\t" + "There are no extra roles you can take!");
      }	
	}
   
   //check if role can be taken- take role
   public Role takeRole(String roleWanted, Player player) {
		ArrayList<Role> cardRoles = currentCard.getRolesonSceneCard();
		for(Role role : cardRoles) {
			if(role.getName().toLowerCase().equals(roleWanted)) {
				if(role.isTaken()) {
					System.out.println("Sorry this role has already been taken");
					return null; 
				} else {
					role.addPlayer(player);
					System.out.println("You have taken the starring role of " + role.getName());
					return role; 
				}
			}
		}

		for(Role role : roomRoles) {
			if(role.getName().toLowerCase().equals(roleWanted)) {
				if(role.isTaken()) {
					System.out.println("Sorry this role has already been taken");
					return null; 
				} else {
					role.addPlayer(player);
					System.out.println("You have taken the extra role of " + role.getName());
					return role; 
				}
			}
		}

		System.out.println("You did not enter a valid role name. Type 'work' to list the available roles");
      System.out.println("Please re-enter the role name as it's listed: ");
		return null; 
	}
   
   //distribute bonuses once scene is completed
   public void distributeBonuses(int[] diceRolls) {
		if(currentCard.isARoleTaken()) {
         Arrays.sort(diceRolls);
         
         //invert array to reverse order
         for(int i=0; i<diceRolls.length/2; i++){
            int temp = diceRolls[i];
            diceRolls[i] = diceRolls[diceRolls.length -i -1];
            diceRolls[diceRolls.length -i -1] = temp;
         }

			System.out.println("Bonuses will be distributed");
			ArrayList<Role> cardRoles = getCardRoles();
         //sort roles by rank
         Role[] sortedCardRoles=new Role[cardRoles.size()];
         for(int i=0; i<sortedCardRoles.length;i++){
            
            int rIndex=0;
            Role maxRankRole=cardRoles.get(rIndex);
            int roleRank=maxRankRole.getrankLevel();
            
            for(int j=0;j<cardRoles.size();j++){
               if(cardRoles.get(j).getrankLevel()>roleRank){
                  rIndex=j;
                  maxRankRole=cardRoles.get(rIndex);
                  roleRank=maxRankRole.getrankLevel();
               }
            }
            sortedCardRoles[i]=maxRankRole;
            cardRoles.remove(rIndex);  
         }
         //distributeBonuses
			for (int i = 0; i < diceRolls.length; i++) {
            sortedCardRoles[i%sortedCardRoles.length].giveBonus(diceRolls[i]);
         }

			for (Role role : roomRoles) {
            if(role.isTaken()){
				   role.giveBonus(0);
            }
			}

		} else {
			System.out.println("Nobody held a starring role. Bonuses will not be distributed");
		}
		clearRoles(); 
	}
   
   //reset all the roles in the setRoom
   private void clearRoles() {
		ArrayList<Role> cardRoles = getCardRoles(); 

		for(Role role : cardRoles) {
         if(role.isTaken()){
   			role.removePlayer(); 
         }
		}

		for(Role role : roomRoles) {
         if(role.isTaken()){
   			role.removePlayer(); 
         }      
		}
	}
}