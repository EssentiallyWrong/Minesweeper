
package minesweeper;


import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author tonynan
 */
public class SweepButton extends JButton{
    private boolean won= false ;

    private String what = "nothing";
    private boolean exposed ;
    private int[] location ;
    private int value;
    private Boolean flagged = false;// flagged yes or no
    
    public void flag(boolean f){
        flagged = f;
    }
     public boolean won(){
         return won;
     }
     public void wonTrue(){
        won= true;
     }
    public boolean isFlagged(){
        return flagged;
    }
    public void reset(){   
        exposed = false;
        value = 0;
        flagged = false;       
        won = false;
    }
    
    public void setLoc(int row,int col){
        location= new int[row+1];
        
        location[row] = col;
    }   
    public int[] getLoc(){
        return location;
    }
    
    public int getValue(){
        
        return value;    
    }
    public void setValue(int num){
        value = num;
        
        
    }       
   
    public boolean isExposed(){
        
        return exposed;
    }
    
     public void expose(){// exposes a mine if not exposed of flagged
        
        
        if((exposed == false && flagged == false) && value!= -1){// exposes

            this.setIcon(null);
            exposed = true;
            this.setText(Integer.toString(value));
            if (value == 0) {
                this.setText(null);
                
            }
            MineSweepStage1.numExposed ++;
        }
     }
    
    public SweepButton(){//regular buttons
        
        
        
        exposed = false;
       
        this.setPreferredSize(new Dimension(40, 40));
        
    }
    public SweepButton(int number){// the reset and difficulty buttons
        value = number;
        flagged = true;
        this.setPreferredSize(new Dimension(40, 40));
    }
}
