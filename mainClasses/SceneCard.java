import java.util.ArrayList;
public class SceneCard{

   //class sceneCard attributes
   private String title;
   private String description;
   private boolean cardAvailable;
   private ArrayList<Role> roles;
   private int budget;
   private String img;
   SetRoom room;
   private int sceneNumber;
   


   //class sceneCard Constructors

   public SceneCard(){
   }

   public SceneCard(String title, String description, ArrayList<Role> roles, int budget, int sceneNumber, String img){

      this.title = title;
      this.description = description;
      this.budget = budget;
      cardAvailable = true;
      this.sceneNumber = sceneNumber;
      this.img = img;
      this.roles=roles;
      }

   //class SceneCard methods
   public boolean isARoleTaken(){
      boolean roleTaken=false;
      for(int i=0;i<roles.size();i++){
         roleTaken=roleTaken|roles.get(i).isTaken();
      }
      return roleTaken;
   }
   public boolean isAvailable(){
      return cardAvailable;
   }
   public ArrayList<Role> getRolesonSceneCard(){
      return roles;
   }

   public String getDescription(){
      return description;
   }

   public String getTitle(){
      return title;
   }

   public int getBudget(){
      return budget; 
   }

   public void changeAvailability(){
      cardAvailable = false;
   }

} //end of SceneCard class
