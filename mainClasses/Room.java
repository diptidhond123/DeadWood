//Class contains basic room information, is extended by others

import java.util.ArrayList;

public class Room{

   protected String roomName;
   protected String roomType;
   protected ArrayList<Room> adjacentRooms;

      
   public Room(){
      roomName=null;
      roomType=null;
   }
   
   public Room(String name){
      roomName = name;
   
   }

   public Room(String name, String type){
      roomName=name;
      roomType=type;
   }

   public Room(String name, String type, ArrayList<Room> adjRooms){
      roomName=name;
      roomType=type;
      adjacentRooms=adjRooms;
   }

   public String getName(){
      return roomName;
   }

   public String getRoomType(){
      return roomType;
   }

   public ArrayList<Room> getAdjacentRooms(){
      return adjacentRooms;
   }

   public void setName(String name){
      roomName=name;
   }

   public void setRoomType(String type){
      roomType=type;
   }

   public void setAdjacentRooms(ArrayList<Room> adjRooms){
      adjacentRooms=adjRooms;
   }
}
