import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;



public class ParseXMLFile {
   //card file
   static File xmlCardFile = new File("cards.xml");
   //board file
   static File xmlBoardFile = new File("board.xml");
   
   //main method for testing
   public static void main(String[] args)throws ParserConfigurationException{
      readCardData(getDocFromFile(xmlCardFile));
    /* ArrayList<Room> rooms=readBoardData(getDocFromFile(xmlBoardFile));
         for(Room room:rooms){
         System.out.println(room.getName());
         //System.out.println("Adjacent rooms:");
         for(Room adjacent: room.getAdjacentRooms()){
            System.out.println("\t"+adjacent.getName());
         
         }
      } */
   }
   
   
   
   public static Document getDocFromFile(File file) throws ParserConfigurationException{
      
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = null;
            try{
               doc = db.parse(file);
            } catch (Exception ex){
            System.out.println("XML parse failure");
            ex.printStackTrace();
               }
            return doc;
       // exception handling
  }    
   
   
   
   public static ArrayList<SceneCard> readCardData(Document d){
   
      ArrayList<SceneCard> cardDeck=new ArrayList();
      String description = null;
      
      int budget = 0;
      String cardName = null;
      String img = null;
      int sceneNumber=0;
   
   
      Element root = d.getDocumentElement();
      NodeList cards = root.getElementsByTagName("card");
      for (int i=0; i<cards.getLength();i++){
      
      //System.out.println("Onto the next card");
        
      
         //reads data from the nodes
         Node card = cards.item(i);
         //get cardname, img and budget
         //Attributes
         String budgetString = card.getAttributes().getNamedItem("budget").getNodeValue();
         budget=Integer.parseInt(budgetString);
         cardName = card.getAttributes().getNamedItem("name").getNodeValue();
         img = card.getAttributes().getNamedItem("img").getNodeValue();
         
         //System.out.println("Budget = "+budget);
         //System.out.println("Card Name = "+cardName);
         //System.out.println("Img = "+img);

         
         //reads data from children
         ArrayList<Role> roles = new ArrayList();
         NodeList children = card.getChildNodes();
         for (int j=0; j< children.getLength(); j++){
            Node sub = children.item(j);
            
            
            
            if ("scene".equals(sub.getNodeName())){
               String sceneNumberString = sub.getAttributes().getNamedItem("number").getNodeValue();
               sceneNumber=Integer.parseInt(sceneNumberString);
              // System.out.println("Scene Number = "+sceneNumber);
               description = sub.getTextContent();
               //System.out.println("Scene Description = "+description);
            }
            
            
            else if ("part".equals(sub.getNodeName())){
            //System.out.println();
            //System.out.println("Onto the next part");
               
               int rankNumber = 0;
               String title = null;
               String line = null;
            
            
               title = sub.getAttributes().getNamedItem("name").getNodeValue(); //title
               //System.out.println("The title of this role is:" + title);
               String rankNumberString = sub.getAttributes().getNamedItem("level").getNodeValue();
               rankNumber = Integer.parseInt(rankNumberString); //rankNumber
               //System.out.println("Rank Number = " +rankNumber); 
               NodeList grandchildren = sub.getChildNodes();
               Coordinates coords = new Coordinates();
               for (int k = 0; k < grandchildren.getLength(); k++){
                  Node sub2 = grandchildren.item(k);
                  
                  if ("area".equals(sub2.getNodeName())){
                     String xPos = sub2.getAttributes().getNamedItem("x").getNodeValue();
                     int xPosi = Integer.parseInt(xPos);
                     String yPos = sub2.getAttributes().getNamedItem("y").getNodeValue();
                     int yPosi = Integer.parseInt(yPos);
                     String h = sub2.getAttributes().getNamedItem("h").getNodeValue();
                     int hPosi = Integer.parseInt(h);
                     String w = sub2.getAttributes().getNamedItem("w").getNodeValue();
                     int wPosi = Integer.parseInt(w);
                     //System.out.println(" xPos = "+xPos);
                     //System.out.println(" yPos = "+yPos);
                     //System.out.println(" height = "+h);
                     //System.out.println(" width = "+w);
                     coords = new Coordinates(xPosi, yPosi, hPosi, wPosi);
                  }
            
                  else if ("line".equals(sub2.getNodeName())){
                     line = sub2.getTextContent();
                     //System.out.println("Line = "+line);  //line        
                  }
                  
               }//end of else if for grandchildren in part
               
               Role currentRole = new Role(title, rankNumber, line, true,coords );
               //System.out.println("Role made: "+currentRole.getName());               
               roles.add(currentRole);

              }//end of else if for part
               
         } //end of for childnodes
         //Initialize a card object
      SceneCard current = new SceneCard(cardName, description, roles, budget, sceneNumber, img);
      for(Role role:current.getRolesonSceneCard()){
         role.setCard(current);
      }
      //System.out.println("current Card: "+ current.getTitle());
      
      //for(Role role: current.getRolesonSceneCard()){
      //   System.out.println(role.getName());
      //}
      cardDeck.add(current); 
               
      }//for card nodes
      return cardDeck;
   }// end of readCardData class
   
   
     public static ArrayList<Room> readBoardData(Document d){
   
      //ArrayList<Room> board=new ArrayList();
      
      String boardName = null;
      ArrayList<Room> rooms = new ArrayList(); 
      
       
   
      Element root = d.getDocumentElement();
      //get name of boardGame
      boardName = root.getAttributes().getNamedItem("name").getNodeValue();
      //Groups of "types" of rooms
      NodeList sets = root.getElementsByTagName("set");
      NodeList trailers = root.getElementsByTagName("trailer");
      NodeList castingRooms = root.getElementsByTagName("office");
      
     ///////////////////////////////////////setRoom Handling 
      
      //How to handle all the sets
     
      for (int i=0; i<sets.getLength();i++){
         //variablesinSets
          ArrayList<Room> adjacentRooms = new ArrayList();
          ArrayList<Role> rolesInRoom = new ArrayList();
          ArrayList<Coordinates> takes = new ArrayList();
         int numShots = 0;
         String setName = null;

                   
                  
         //System.out.println("Next set");
         //System.out.println();
         //reads data from the set
         Node set = sets.item(i);
         //Attributes of each set
         setName = set.getAttributes().getNamedItem("name").getNodeValue();     
         //System.out.println("Set Name = "+setName); //where setRoom name is established
         //reads data from children
         NodeList setChildren = set.getChildNodes();
         
         for (int j=0; j< setChildren.getLength(); j++){
            Node sub = setChildren.item(j);            
            if ("neighbors".equals(sub.getNodeName())){
               Node neighbor = sub;
                NodeList grandChildren = neighbor.getChildNodes();
                
                for (int k=0; k < grandChildren.getLength(); k++){
                  Node sub2 = grandChildren.item(k);
                  if ("neighbor".equals(sub2.getNodeName())){
                      String neighborName = sub2.getAttributes().getNamedItem("name").getNodeValue(); 
                      Room adj = new Room(neighborName);
                      adjacentRooms.add(adj);
                      //System.out.println("Neighbor =" + neighborName); //where neighbor is established                 
                  }
                  
                }       
              }//end of neighbors
            else if ("takes".equals(sub.getNodeName())){                       
              
               NodeList grandChildren = sub.getChildNodes();             
               for (int l = 0; l < grandChildren.getLength(); l++){
                  Node sub2 = grandChildren.item(l);
                  

                  if ("take".equals(sub2.getNodeName())){
                     String takeNumberString = sub2.getAttributes().getNamedItem("number").getNodeValue();
                     int takeNumber = Integer.parseInt(takeNumberString);
                     //System.out.println("The take number is = "+takeNumber);
                     numShots++;
                     
                     NodeList grandGrandChildren = sub2.getChildNodes();
                     for (int q = 0; q <grandGrandChildren.getLength();q++){
                        Node finalSub = grandGrandChildren.item(q);
                        Coordinates takeCoords;
                        if ("area".equals(finalSub.getNodeName())){
                           String wString = finalSub.getAttributes().getNamedItem("w").getNodeValue();
                           int w = Integer.parseInt(wString);
                           //System.out.println("The w = "+w);
                           String hString = finalSub.getAttributes().getNamedItem("h").getNodeValue();
                           int h = Integer.parseInt(hString);
                           //System.out.println("The h = "+h);
                           String yString = finalSub.getAttributes().getNamedItem("y").getNodeValue();
                           int y = Integer.parseInt(yString);
                           //System.out.println("The y = "+y);
                           String xString = finalSub.getAttributes().getNamedItem("x").getNodeValue();
                           int x = Integer.parseInt(xString);
                           //System.out.println("The x = "+x);
                           takeCoords = new Coordinates(x, y, h, w); //set coordinates
                           int input = takeNumber -1;
                           takes.add(input, takeCoords);
                        } //end area
                     
                     }//end grandGrandChildren
                  }//end take
                  
            }//end for
            //System.out.println("The total number of Shots is: " + numShots);
            }//end takes
            
            
            
             else if ("parts".equals(sub.getNodeName())){
               //variables
               String line = null;
               String partName="";
               int level=0;
               NodeList grandChildrenP = sub.getChildNodes();
                  for (int w=0; w< grandChildrenP.getLength(); w++){
                     Node part = grandChildrenP.item(w);
                     if ("part".equals(part.getNodeName())){
               
                     partName = part.getAttributes().getNamedItem("name").getNodeValue();//partName
                     //System.out.println("The name of this part is:" + partName);
                     String levelString = part.getAttributes().getNamedItem("level").getNodeValue();
                     level = Integer.parseInt(levelString);//rank
                     //System.out.println("The level of this part is:" + level);
                     NodeList extraGrandChildren = part.getChildNodes();
                     for(int e= 0; e<extraGrandChildren.getLength(); e++){
                        Node sub3 = extraGrandChildren.item(e);
                        Coordinates partCoords = new Coordinates();
                        if ("area".equals(sub3.getNodeName())){
                           String wString = sub3.getAttributes().getNamedItem("w").getNodeValue();
                           int wPos = Integer.parseInt(wString);
                           //System.out.println("The w = "+z);
                           String hString = sub3.getAttributes().getNamedItem("h").getNodeValue();
                           int h = Integer.parseInt(hString);
                           //System.out.println("The h = "+h);
                           String yString = sub3.getAttributes().getNamedItem("y").getNodeValue();
                           int y = Integer.parseInt(yString);
                           //System.out.println("The y = "+y);
                           String xString = sub3.getAttributes().getNamedItem("x").getNodeValue();
                           int x = Integer.parseInt(xString);
                           //System.out.println("The x = "+x);
                           partCoords = new Coordinates(x, y, h, wPos); //set coordinates
                        }//end of if area
                        else if ("line".equals(sub3.getNodeName())){
                            line = sub3.getTextContent(); //String line
                            Role role = new Role(partName, level, line, false, partCoords);
                            //System.out.println("The name of this role is :"+role.getName());
                            rolesInRoom.add(role);
                            //System.out.println("The line for the part is:" + line);
                       
                        }//end of elseif line
                         
                        
                     }//end for loop
                 }
              } //end of forloop grandChildrenP                  
           }//end parts
                  
                  
               else if ("area".equals(sub.getNodeName())){
                           String wString = sub.getAttributes().getNamedItem("w").getNodeValue();
                           int w = Integer.parseInt(wString);
                           //System.out.println("The w = "+w);
                           String hString = sub.getAttributes().getNamedItem("h").getNodeValue();
                           int h = Integer.parseInt(hString);
                           //System.out.println("The h = "+h);
                           String yString = sub.getAttributes().getNamedItem("y").getNodeValue();
                           int y = Integer.parseInt(yString);
                           //System.out.println("The y = "+y);
                           String xString = sub.getAttributes().getNamedItem("x").getNodeValue();
                           int x = Integer.parseInt(xString);
                           //System.out.println("The x = "+x);
               }//area from setChild
           }//end forloop

           SetRoom aRoom = new SetRoom(setName, "set", numShots, rolesInRoom, adjacentRooms, takes);
           
           rooms.add(aRoom);
                 
        }//end forloop sets
        
        
        ////////////////////////////End of SetRoom Handling
        
        //////////////////////////Start of Trailers Handling
        //How to handle trailers
         for (int i = 0; i< trailers.getLength(); i++){
            String name = "Starting Room";
             ArrayList<Room> adjacentRooms = new ArrayList();
             String type = "trailer";
            
            Node trailer = trailers.item(i);
            NodeList trailerChildren = trailer.getChildNodes();
            for (int j=0; j <trailerChildren.getLength(); j++){
               Node sub = trailerChildren.item(j);
               if ("neighbors".equals(sub.getNodeName())){
                  Node neighbor = sub;
                  
                 NodeList grandChildren = neighbor.getChildNodes();
                  for (int k=0; k < grandChildren.getLength(); k++){
                     Node sub2 = grandChildren.item(k);
                     if ("neighbor".equals(sub2.getNodeName())){
                        String neighborName = sub2.getAttributes().getNamedItem("name").getNodeValue();
                        Room adj = new Room(neighborName);
                        adjacentRooms.add(adj);
                        //System.out.println("Neighbor =" + neighborName);                  
                     }
                  }       
              }//end of neighbors
              else if ("area".equals(sub.getNodeName())){
               String wString = sub.getAttributes().getNamedItem("w").getNodeValue();
               int w = Integer.parseInt(wString);
               //System.out.println("The w = "+w);
               String hString = sub.getAttributes().getNamedItem("h").getNodeValue();
               int h = Integer.parseInt(hString);
               //System.out.println("The h = "+h);
               String yString = sub.getAttributes().getNamedItem("y").getNodeValue();
               int y = Integer.parseInt(yString);
               //System.out.println("The y = "+y);
               String xString = sub.getAttributes().getNamedItem("x").getNodeValue();
               int x = Integer.parseInt(xString);
                //System.out.println("The x = "+x);
               }//area from trailers
               
         } // end trailerChildren forloop
         Room aRoom = new Room(name, type, adjacentRooms);
               rooms.add(aRoom);
        }//end trailers forloop
        
        ////////////////////////End of Trailers handling/////////////////////////
        
//////////////////HANDLING OF CASTING ROOMS/////////////////////
         for (int i = 0; i< castingRooms.getLength(); i++){
             ArrayList<Room> adjacentRooms = new ArrayList();
      
            
            Node castingRoom = castingRooms.item(i);
            NodeList castingRoomChildren = castingRoom.getChildNodes();
            for (int j=0; j <castingRoomChildren.getLength(); j++){
               Node sub = castingRoomChildren.item(j);
               if ("neighbors".equals(sub.getNodeName())){
                  Node neighbor = sub;
                  NodeList grandChildren = neighbor.getChildNodes();
                  for (int k=0; k < grandChildren.getLength(); k++){
                     Node sub2 = grandChildren.item(k);
                     if ("neighbor".equals(sub2.getNodeName())){
                        String neighborName = sub2.getAttributes().getNamedItem("name").getNodeValue();
                        Room adj = new Room(neighborName);
                        adjacentRooms.add(adj);
                        //System.out.println("Neighbor =" + neighborName);                  
                     }
                  }       
              }//end of neighbors from castingRooms
              else if ("area".equals(sub.getNodeName())){
               String wString = sub.getAttributes().getNamedItem("w").getNodeValue();
               int w = Integer.parseInt(wString);
               //System.out.println("The w = "+w);
               String hString = sub.getAttributes().getNamedItem("h").getNodeValue();
               int h = Integer.parseInt(hString);
               //System.out.println("The h = "+h);
               String yString = sub.getAttributes().getNamedItem("y").getNodeValue();
               int y = Integer.parseInt(yString);
               //System.out.println("The y = "+y);
               String xString = sub.getAttributes().getNamedItem("x").getNodeValue();
               int x = Integer.parseInt(xString);
               //System.out.println("The x = "+x);
               }//area from castingRooms
               
               else if ("upgrades".equals(sub.getNodeName())){
                  Node upgrades = sub;
                  NodeList grandChildren = upgrades.getChildNodes();
                  for ( int w =0; w<grandChildren.getLength(); w++){
                     Node sub2 = grandChildren.item(w);
                     if ("upgrade".equals(sub2.getNodeName())){
                        String levelStr = sub2.getAttributes().getNamedItem("level").getNodeValue();
                        String amtStr = sub2.getAttributes().getNamedItem("amt").getNodeValue();
                        String currency = sub2.getAttributes().getNamedItem("currency").getNodeValue();
                        int level = Integer.parseInt(levelStr);
                        int amt = Integer.parseInt(amtStr);
                        
                        //System.out.println("To upgrade to " +level+ " you need " + amt+ " " +currency);
                        NodeList grandGrandChildren = sub2.getChildNodes();
                        for ( int p =0; p<grandGrandChildren.getLength(); p++){
                           Node sub3 = grandGrandChildren.item(p);
                           if ("area".equals(sub3.getNodeName())){
                              String wString1 = sub3.getAttributes().getNamedItem("w").getNodeValue();
                              int w1 = Integer.parseInt(wString1);
                              //System.out.println("The w = "+w1);
                              String hString1 = sub3.getAttributes().getNamedItem("h").getNodeValue();
                              int h1 = Integer.parseInt(hString1);
                              //System.out.println("The h = "+h1);
                              String yString1 = sub3.getAttributes().getNamedItem("y").getNodeValue();
                              int y1 = Integer.parseInt(yString1);
                              //System.out.println("The y = "+y1);
                              String xString1 = sub3.getAttributes().getNamedItem("x").getNodeValue();
                              int x1 = Integer.parseInt(xString1);
                              //System.out.println("The x = "+x1);
                           }//area from upgrade

                     } //forloopgrandGrandChildren                  
       
                  }//end of upgrade
                  
                }//forloopgrandChildren
                
       
               }//end of upgrades
               
               
         } // end castingRoomChildren forloop
         CastingRoom aRoom = new CastingRoom("office", "office", adjacentRooms);
         rooms.add(aRoom);
         
        }//end castingRooms forloop
                
        return rooms;          
 }// end of readBoardData class

}//end class