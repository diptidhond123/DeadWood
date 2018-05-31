/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.lang.*;
import java.util.Arrays;
import java.lang.Object.*;
//import org.apache.commons.lang3.ArrayUtils;


public class BoardView extends JFrame {

  // JLabels
  JLabel boardlabel;
  JLabel cardlabel;
  JLabel playerlabel;
  JLabel mLabel;
  JLabel nLabel;
  JLabel pNumLabel;
  JLabel nameLabel;
  JLabel moneyLabel;
  JLabel fameLabel;
  JLabel rankLabel;
  JLabel rehearsalLabel;
  
  JLabel money4;
  JLabel money10;
  JLabel money18;
  JLabel money28;
  JLabel money40;
  
  JLabel fame5;
  JLabel fame10;
  JLabel fame15;
  JLabel fame20;
  JLabel fame25;
  
  //JButtons
  JButton bAct;
  JButton bRehearse;
  JButton bMove;
  
  // JLayered Pane
  JLayeredPane bPane;
  
  Dimension boardSize = new Dimension(1200, 900);
  
  
  public  JPanel configureStats(JLabel mLabel, JLayeredPane bPane){
                  mLabel.setBounds(boardSize.width+20,20,100,20);
       bPane.add(mLabel,new Integer(2));
       JPanel stats = new JPanel();   

   	    stats.setLayout(new GridLayout(6, 1));
	      stats.setPreferredSize(new Dimension (1200, 68));
	      stats.setBounds(boardSize.width+20,40, boardSize.height, 100);
      
         pNumLabel = new JLabel("Player:");
         //namrankLabel.setBounds(icon.getIconWidth()+20, 320, 100, 50);
         stats.add(pNumLabel);
       
         nameLabel = new JLabel("Name:");
         //namrankLabel.setBounds(icon.getIconWidth()+20, 320, 100, 50);
         stats.add(nameLabel);
       
         moneyLabel = new JLabel("Money:");
         //moneyLabel.setBounds(icon.getIconWidth()+20, 335, 100, 50);
         stats.add(moneyLabel);
       
         fameLabel = new JLabel("Fame:");
         //fameLabel.setBounds(icon.getIconWidth()+20, 350, 100, 50);
         stats.add(fameLabel);
       
         rankLabel = new JLabel("Rank:");
         //rankLabel.setBounds(icon.getIconWidth()+20, 365, 100, 50);
         stats.add(rankLabel);
       
         rehearsalLabel = new JLabel("Rehearsal Credits:");
         //rehearsalLabel.setBounds(icon.getIconWidth()+20, 380, 200, 50);
         stats.add(rehearsalLabel);

   return stats;
  
 
  }
  
  public JPanel configureControls(JPanel controls){
   	   controls.setLayout(new GridLayout(1, 4));
	   controls.setPreferredSize(new Dimension (1200, 68));
	   controls.setBounds(0,boardSize.height, 1200, 68);
      
	   JButton act = new JButton("Act");
      act.setBackground(Color.white);

	   JButton move = new JButton("Rehearse");
      move.setBackground(Color.white);

	   JButton takeRole = new JButton("Take Role");
      takeRole.setBackground(Color.white);

      JButton endTurn = new JButton("End Turn");
      endTurn.setBackground(Color.white);

	   controls.add(act);
	   controls.add(move);
	   controls.add(takeRole);
      controls.add(endTurn);

  
  
  
  return controls;
  
  
  
  }
  
  public JLayeredPane addbPaneAdditionals(JLayeredPane bPane){
       money4 = new JLabel();
       money4.setBounds(90,540,37,23);
       money4.addMouseListener(new boardMouseListener());
       bPane.add(money4, new Integer(2));
       
		 money10 = new JLabel();
       money10.setBounds(90,561,37,23);
       money10.addMouseListener(new boardMouseListener());
       bPane.add(money10, new Integer(2));
       
		 money18 = new JLabel();
       money18.setBounds(90,584,37,23);
       money18.addMouseListener(new boardMouseListener());
       bPane.add(money18, new Integer(2));
       
		 money28 = new JLabel();
       money28.setBounds(90,606,37,23);
       money28.addMouseListener(new boardMouseListener());
       bPane.add(money28, new Integer(2));
       
		 money40 = new JLabel();
		 money40.setBounds(90,629,37,23);
       money40.addMouseListener(new boardMouseListener());
       bPane.add(money40, new Integer(2));
		
		 money4.setVisible(true);
		 money10.setVisible(true);
		 money18.setVisible(true);
		 money28.setVisible(true);
		 money40.setVisible(true);
		
		 money4.setName("money4");
		 money10.setName("money10");
		 money18.setName("money18");
		 money28.setName("money28");
		 money40.setName("money40");
       
       fame5 = new JLabel();
       fame5.setBounds(139,540,37,23);
       fame5.addMouseListener(new boardMouseListener());
       bPane.add(fame5, new Integer(2));
       
		 fame10 = new JLabel();
       fame10.setBounds(139,561,37,23);
       fame10.addMouseListener(new boardMouseListener());
       bPane.add(fame10, new Integer(2));
       
		 fame15 = new JLabel();
       fame15.setBounds(139,584,37,23);
       fame15.addMouseListener(new boardMouseListener());
       bPane.add(fame15, new Integer(2));
       
		 fame20 = new JLabel();
       fame20.setBounds(139,606,37,23);
       fame20.addMouseListener(new boardMouseListener());
       bPane.add(fame20, new Integer(2));
       
		 fame25 = new JLabel();
		 fame25.setBounds(139,629,37,23);
       fame25.addMouseListener(new boardMouseListener());
       bPane.add(fame25, new Integer(2));
		
		 fame5.setVisible(true);
		 fame10.setVisible(true);
		 fame15.setVisible(true);
		 fame20.setVisible(true);
		 fame25.setVisible(true);
		
		 fame5.setName("fame5");
		 fame10.setName("fame10");
       fame15.setName("fame15");
		 fame20.setName("fame20");
		 fame25.setName("fame25");
       
    return bPane;
  }
  
  
  public JLabel createDeadWoodBoard(JLabel boardlabel){
       ImageIcon icon =  new ImageIcon("board.jpg");
       boardlabel.setIcon(icon); 
       boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
  }
  
  public JLabel getSceneCard(JLabel cardlabel){
         ImageIcon cIcon =  new ImageIcon("01.png");
       cardlabel.setIcon(cIcon); 
       cardlabel.setBounds(20,65,cIcon.getIconWidth()+2,cIcon.getIconHeight());
       cardlabel.setOpaque(true);
return cardlabel;

  }
  
  
  public JLabel setPlayerIcon(JLabel playerlabel){
         ImageIcon pIcon = new ImageIcon("r2.png");
       playerlabel.setIcon(pIcon);
       //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());  
       playerlabel.setBounds(116,227,46,46);
       
       
       return playerlabel;
  }
  
 
  // Constructor
  public BoardView() {
      
       // Set the title of the JFrame
       super("Deadwood");
       // Set the exit option for the JFrame
       setDefaultCloseOperation(EXIT_ON_CLOSE);
      
       // Create the JLayeredPane to hold the display, cards, dice and buttons
       bPane = getLayeredPane();
    
       // Create the deadwood board
       boardlabel = new JLabel();
       boardlabel = createDeadWoodBoard(boardlabel);
             
       // Add the board to the lowest layer
       bPane.add(boardlabel, new Integer(0));
      
       // Set the size of the GUI
       setSize(icon.getIconWidth()+200,icon.getIconHeight()+100);
       
       // Add a scene card to this room
       cardlabel = new JLabel();
       cardlabel = getSceneCard(cardlabel);
       
       // Add the card to the lower layer
       bPane.add(cardlabel, new Integer(1));

       // Add a dice to represent a player. 
       // Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
       //This needs to be done for each player AFTER the numPlayers is set. So this needs to be out of the contructor, after the pop ups are displayed!
       playerlabel = new JLabel();
       playerlabel = setPlayerIcon(playerlabel);
       bPane.add(playerlabel,new Integer(3));
      
       //display player stats
       mLabel = new JLabel("STATS:"); 
       bPane.add(configureStats(mLabel, bPane), JLayeredPane.DEFAULT_LAYER);
        
        //add money, fame
       bPane = addbPaneAdditionals(bPane);
       
       //add buttons       
      JPanel controls = new JPanel();
	   bPane.add(configureControls(controls), JLayeredPane.DEFAULT_LAYER);
  }//end of BoardView constructor
  
  

  
  // This class implements Mouse Events
  
  class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         
         if (e.getSource()== bAct){
            System.out.println("Acting is Selected\n");
         }
         else if (e.getSource()== bRehearse){
            System.out.println("Rehearse is Selected\n");
         }
         else if (e.getSource()== bMove){
            System.out.println("Move is Selected\n");
         }
         else if (e.getSource()== money4){
            System.out.println("c4 is Selected\n");
         }
         else if (e.getSource()== money10){
            System.out.println("c10 is Selected\n");
         }
         else if (e.getSource()== money18){
            System.out.println("c18 is Selected\n");
         }
         else if (e.getSource()== money28){
            System.out.println("c28 is Selected\n");
         }
         else if (e.getSource()== money40){
            System.out.println("c40 is Selected\n");
         }
         else if (e.getSource()== fame5){
            System.out.println("f5 is Selected\n");
         }
         else if (e.getSource()== fame10){
            System.out.println("f10 is Selected\n");
         }
         else if (e.getSource()== fame15){
            System.out.println("f15 is Selected\n");
         }
         else if (e.getSource()== fame20){
            System.out.println("f20 is Selected\n");
         }
         else if (e.getSource()== fame25){
            System.out.println("f25 is Selected\n");
         }        
      }
      public void mousePressed(MouseEvent e) {
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
   }
   public static int findNumPlayers(){   
   	Object[] possibilities = {"2", "3", "4", "5", "6", "7", "8"};
   	String picked = (String)JOptionPane.showInputDialog(
   							    null,                                  //JFrame
   							    "Choose your number of players.\n",    //Text in box
   							    "Pick Number of Players",              //Text labeling box
   							    JOptionPane.DEFAULT_OPTION,            //automatic buttons
   							    null,                                  //icon
   							    possibilities,                         //elements in dropdown menu
   							    "0"                                    //Default option
   							    );
   	Integer numPlayers = Integer.parseInt(picked);
   	return numPlayers;
    }
    
    
    
     public static Object[] removeElementFromArray(Object[] colorsOfDice, String color){
 
      Object[] colorsOfDice2 = new Object[colorsOfDice.length -1];
      int i = 0;
      int j = 0;
      while( i < colorsOfDice.length){
            if (!(colorsOfDice[i].equals(color))){
               colorsOfDice2[j] = (colorsOfDice[i]);
               i++;
               j++;
            }
            
            else i++;
         
         }
         return colorsOfDice2;

      
      }
      
      
    public static int findIndexOfPicked(Object[] colorsOfDice, String color){
      int i = 0;
      while( i < colorsOfDice.length){
            if (colorsOfDice[i] == color){
               return i;
            }
            
            else i++;
         
         }
      return 0;
    }
    
    
    
public static ImageIcon getPNG(String colorPicked, int rank){
    ImageIcon pIcon;
     
        switch(colorPicked){
         case "Blue": pIcon= new ImageIcon("b" + rank + ".png");
                    return pIcon;
                    
          case "Teal": pIcon = new ImageIcon("c" + rank + ".png");
                     return pIcon;
                     
                     
          case "Light Green": pIcon = new ImageIcon("g" + rank + ".png");
                     return pIcon;
                      
                     
          case "Orange": pIcon = new ImageIcon("o" + rank + ".png");
                     return pIcon;
                     
                     
                     
          case "Pink":  pIcon = new ImageIcon("p" + rank + ".png");
                     return pIcon;
                     
                     
          case "Red":  pIcon = new ImageIcon("r" + rank + ".png");
                     return pIcon; 
                       
          case "Violet": pIcon = new ImageIcon("v" + rank + ".png");
                     return pIcon;
                      
         case "Yellow": pIcon = new ImageIcon("y" + rank + ".png");
                     return pIcon;
                      
        }
           return null;
  }
    
    //using Drag and Drop- Opens up a new pop up that has player
    public static void playersChooseDie(int numPlayers){
      int playerRank = 1;
      int playerNums = 1; //count of players that have inputted info
      int numPlayersPlusOne = numPlayers + 1; //players + 1 
      Object[] colorsOfDice = {"Blue", "Teal", "Light Green", "Orange", "Pink", "Red", "Violet", "Yellow"};
      Object[] playerDiceChoices = new Object[numPlayers]; //Player num and what comes out is Dice Choice
      int k = 0;
    while(playerNums!= numPlayersPlusOne){
      String playerName;
      playerName = JOptionPane.showInputDialog("Please enter player " + playerNums + "'s name");
      String textInDiceInput = "Please choose die color of Player " + playerNums; 
   	String picked = (String)JOptionPane.showInputDialog(
                            null,                                  //JFrame
   							    textInDiceInput,    //Text in box
   							    "Enter player names and choose player dice",              //Text labeling box
   							    JOptionPane.DEFAULT_OPTION,            //automatic buttons
   							    null,                                  //icon
   							    colorsOfDice,                         //elements in dropdown menu
   							    "0"                                    //Default option
         	                 );
         playerDiceChoices[k] = picked;
         k++;
         Icon jpegForPlayer = getPNG(picked, playerRank);
         colorsOfDice = removeElementFromArray(colorsOfDice, picked);      
         playerNums++;
   	
      }    
    }
    
       
    
   

  public static void main(String[] args) {
    BoardView board = new BoardView();
    board.setVisible(true);
    playersChooseDie(findNumPlayers());
  }
}