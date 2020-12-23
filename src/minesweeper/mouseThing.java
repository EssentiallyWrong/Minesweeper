/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author tonynan
 */
public class mouseThing implements MouseListener{

    int highscore = 14;
    @Override
    public void mouseClicked(MouseEvent e) {
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        
        
        
        SweepButton buttonThing = (SweepButton)e.getSource();
        
        if(buttonThing.getValue()<8){// starts  timer if a grid button is pressed
            MineSweepStage1.startTimer();
        }
        
        if(buttonThing.getValue() == 10){//reset button
            
            MineSweepStage1.Reset();  
        }
        
        if(buttonThing.getValue() == 11){//beginner 
            MineSweepStage1.selectMode(11);
        }
        
        if(buttonThing.getValue() == 12){//mild
            MineSweepStage1.selectMode(12);
        }
                
        if(buttonThing.getValue() == 13) {//expert
            MineSweepStage1.selectMode(13);
        }      
        if(buttonThing.getValue()==highscore){
            MineSweepStage1.highScore();
            
        }
        
        if(SwingUtilities.isLeftMouseButton(e) && SwingUtilities.isRightMouseButton(e)){// if both mouse buttons are clicked call the bothClick method
            if(buttonThing.getValue()> 0){
                MineSweepStage1.bothClick(buttonThing.getLoc().length-1,buttonThing.getLoc()[buttonThing.getLoc().length-1]);
                
            }
        }
        
        if(e.isMetaDown()){// checks if something has been right clicked to flag and unflag
            if(buttonThing.getValue()<8){
                if(!MineSweepStage1.mineHasBeenHit){
                    if((!buttonThing.isExposed())&& (!buttonThing.isFlagged())){// makes sure it has not been exposed or already flagged
                        buttonThing.setIcon(MineSweepStage1.FLAG);
                        buttonThing.flag(true);
                        MineSweepStage1.numOfFlags--;//increments number of tiles been flagged
                    
                        MineSweepStage1.flagUpdate();
                    
                        return;
                    }
                    if(buttonThing.isFlagged()==true ){// checks if flagged
                        buttonThing.setIcon(MineSweepStage1.TILE);
                        buttonThing.flag(false);
                        MineSweepStage1.numOfFlags++; //increments number of tiles been flagged
                        MineSweepStage1.flagUpdate();
                        return;
                        }
                    }
                }
            }   
        if(buttonThing.isFlagged()==false){// exposes button if has not been flagged
        buttonThing.expose();
        }
       
        
        if (buttonThing.getValue() ==-1 && !buttonThing.isFlagged()){//checks if the button clicked is a mine and has been flagged
            MineSweepStage1.mineHit();// calls the losing method
        }
        else if (buttonThing.getValue() == 0){// checks if the button hit is a zero then sends the location to the method
            MineSweepStage1.hitZero(buttonThing.getLoc().length-1,buttonThing.getLoc()[buttonThing.getLoc().length-1]);
            
        }
        else if((MineSweepStage1.numExposed == (MineSweepStage1.row*MineSweepStage1.col-MineSweepStage1.Num_Mines))&& !buttonThing.won()){// calls the win method if you expose all non mines
            MineSweepStage1.win();
            buttonThing.wonTrue();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
}
