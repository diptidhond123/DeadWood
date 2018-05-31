public class Role{

   //class Role attributes
   private String name;
   private int rankLevel;
   private String phrase;
   private boolean isTaken;
   private Player actorName;
   private boolean onCard;
   protected SceneCard card;
   private Coordinates coords;

   //class Role Constructors

   public Role(){

   }
   
   public Role(String name, int rankLevel, String phrase, boolean onCard, Coordinates coords){
      this.name = name;
      this.rankLevel = rankLevel;
      this.phrase = phrase;
      this.onCard = onCard;
      this.coords = coords;

   }

   //class Role methods
   public void setCard(SceneCard card){
      this.card=card;
   }
   
   public boolean isOnCard(){
      return onCard;   
   }
   
   public boolean isTaken(){
      return isTaken;
   }
   public void removePlayer(){
      this.actorName = null;
      leaveRole();
   }

   public void addPlayer(Player player){
      actorName = player;
      takeRole();
   }

   public SceneCard getCard(){
      return card;
   }
   
   public String getPhrase(){
      return phrase;
   } 

   public String getName(){
      return name;
      }

   public int getrankLevel(){
      return rankLevel;
      }

   public void takeRole(){
      isTaken = true;
   }

   public void leaveRole(){
      isTaken = false;
   }

   public Player getPlayeronRole(){
      return actorName;
   }

   //give Bonus
   public void giveBonus(int money) {
   //System.out.println(money);
	  if(onCard &&actorName!=null) {
		  actorName.changeMoney(money); 
	  } else if(actorName!=null){
		  actorName.changeMoney(rankLevel); 
	  }
  }


}//end of Role class
