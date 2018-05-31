//Casting room allows upgrades
import java.util.ArrayList;

public class CastingRoom extends Room{
   private int[][] upgradeTable;
   //private ArrayList<Room> adjacentRooms;


   public CastingRoom(String name, String type, ArrayList<Room> roomsAdjacent){
      roomName=name;
      roomType=type;
      upgradeTable = new int[][]{{4, 5}, {10, 10}, {18, 15}, {28, 20}, {40, 25}}; 
      adjacentRooms = roomsAdjacent; 
   }
   //shows the ranks available based on players resources
   //returns true if they can upgrade, false if they cant
   public boolean displayAvailableRanks(int rank, int fame, int money){
  
      boolean upgradeable = true;
      int i = rank-1;
      int[] currentRank = {0, 0}; //{Money, Fame} for rank in consideration
      System.out.println("These are the available ranks you can upgrade:");
      while(upgradeable){
         currentRank = upgradeTable[i];
         if(money < currentRank[0] && fame < currentRank[1]){
            upgradeable = false;
         }else{
            System.out.println("\t Rank " + (i + 2) + ": $" + currentRank[0] + " or " + currentRank[1] + " cr.");
            i++; 
         }
      }
      if(i == 0){
         System.out.println("You cannot upgrade your rank!");
      }
      return upgradeable;
   }
   
   //player is able to upgrade using a specific currency
   public boolean upgradeRank(String paymentType, Player player, int rankWanted){
  
      if(rankWanted < player.getRank() || rankWanted > 6){
         System.out.println("That is not an available rank.");
         return false;
      }else{
         int[] currRank = upgradeTable[rankWanted-2];
         if(paymentType.equals("money")){
            if(player.getMoney() >= currRank[0]){
               player.changeMoney(-currRank[0]);
               player.setRank(rankWanted);
               System.out.println("You are now rank " + rankWanted + "!");
               return true;
            }else{
               System.out.println("You cannot afford that rank!");
               return false;
            }
         }else if(paymentType.equals("fame")){
            if(player.getFame() >= currRank[1]){
               player.changeFame(-currRank[1]);
               player.setRank(rankWanted);
               System.out.println("You are now rank " + rankWanted + "!");
               return true;
            }else{
               System.out.println("You cannot afford that rank!");
               return false;
            }
         }
      }
      return false;
  }

 //get the UpgradeTable
  public int[][] getUpgradeTable(){
      return upgradeTable;
  }

}