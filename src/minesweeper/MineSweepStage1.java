
package minesweeper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;



public class MineSweepStage1 {
    
    static int time=0; 
    static JLabel timerLabel;
    static Timer timer;
    
    static JFrame highScore;
    
    
    static JLabel flags;
    static int numOfFlags;
    
    static int numExposed= 0;
    
    static boolean mineHasBeenHit;
    
    
   
    static JFrame frame;
    static ImageIcon TILE = new ImageIcon("pictures/minetile.png");
    static ImageIcon FLAG = new ImageIcon("pictures/flag.png");
    static ImageIcon MINE = new ImageIcon("pictures/mines.jpg");
    static ImageIcon FACE = new ImageIcon("pictures/face.png");
    
    static ArrayList<Integer> mineNums;
    static int Num_Mines = 10;
    static int row = 8;
    static int col = 8;
    static Random rand = new Random();
    
    
    static int[][] grid;
    static SweepButton [][] buttons;
    private static JPanel panel;
    static boolean[][] beenCalled;
    
    SweepButton beginner = new SweepButton(11);
    SweepButton mild = new SweepButton(12);
    SweepButton expert = new SweepButton(13);
    static SweepButton highScoreButt = new SweepButton(14);
    
    
    
    
    SweepButton reset = new SweepButton(10);
    
    static JPanel difPanel;
    static JPanel resetPanel;
    static JPanel finPanel;
  
    public static void main(String[] args){
       MineSweepStage1 sweep = new MineSweepStage1();
       System.out.println();
       
       
    }
    
    
    public MineSweepStage1(){
        
        timerLabel = new JLabel();  //creates a jlabel for the timeer 
        timer = new Timer(1000, new ActionListener() {  // new timer 
            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                timerLabel.setText(Integer.toString(time));// updates time value
                
            }
            
        } );
               
        
        numOfFlags = Num_Mines;

        
        flags = new JLabel();
        flags.setText(Integer.toString(numOfFlags));
        
        finPanel = new JPanel();
        
        grid= new int[row][col]; //sets size of grid 
        buttons = new SweepButton[row][col];
        beenCalled = new boolean[row][col];
        
       
        finPanel.setLayout(new BoxLayout(finPanel, BoxLayout.PAGE_AXIS));
        resetPanel = new JPanel();
        difPanel = new JPanel();
        
        difPanel.setLayout(new GridLayout(1,3));
        
        beginner.setPreferredSize(new Dimension(80, 40));
        mild.setPreferredSize(new Dimension(80, 40));
        expert.setPreferredSize(new Dimension(80, 40));
        
        
        reset.addMouseListener(new mouseThing());
        beginner.addMouseListener(new mouseThing());
        mild.addMouseListener(new mouseThing());
        expert.addMouseListener(new mouseThing());
        highScoreButt.addMouseListener(new mouseThing());
        
        
        beginner.setText("Beginner");
        mild.setText("Mild");
        expert.setText("Expert");
        highScoreButt.setText("High Score");
       
                
        difPanel.add(beginner);
        difPanel.add(mild);
        difPanel.add(expert);
        
        
        resetPanel.setLayout(new GridLayout(1,3));
        resetPanel.add(flags);
        resetPanel.add(reset);
        resetPanel.add(timerLabel);

        
          
        panel= new JPanel();
        panel.setLayout(new GridLayout(row,col));
        
        FACE = scaleImage(FACE);
        reset.setIcon(FACE);
        
        
        TILE = scaleImage(TILE);
        MINE = scaleImage(MINE);
        FLAG = scaleImage(FLAG);
       
        
        frame = new JFrame("MineSweeper");
        
        frame.setSize(500,500);
        
        
       
        frame.addWindowListener(new WindowAdapter() { //closes program on exit
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
        });    
        
                
        fill();//
      
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                System.out.print("["+ grid[i][j]+ "]");
        
            }
            System.out.println();
        }
        
        finPanel.add(highScoreButt);
        finPanel.add(difPanel);
        finPanel.add(resetPanel);
        finPanel.add(panel);
        
        
        frame.add(finPanel);
        frame.pack();
        frame.setVisible(true);
        
        
    }
    public static void startTimer(){//starts the timer
        timer.start();
    }
    public static void flagUpdate(){// updates the number of flags displayed when called
        flags.setText(Integer.toString(numOfFlags));
    }
    public static void fill(){// fills the the grid with values and adds buttons to the panel
        
        panel= new JPanel();
        panel.setLayout(new GridLayout(row,col));
        
        
        mineNums = new ArrayList<>();//initialize the ArrayList
        while (mineNums.size()< Num_Mines) {// makes sure the ArrayList is filled to the number of desired mines
            int loc = rand.nextInt(row*col-1);//random integer from 0 to row*col-1
            if(!mineNums.contains(loc)){//checks if the number is already in the arraylist
                mineNums.add(loc);//adds random int
            }
          
        }
        for(int i=0;i < mineNums.size();i++){ //sets the location of the mine in the 2d array
            grid[mineNums.get(i)/col][mineNums.get(i)%col] = -1;
        }
        
        for(int i=0;i < mineNums.size();i++){ //adds 1 to the numbers around the mine location 
            for(int a = mineNums.get(i)/col-1;a<mineNums.get(i)/col+2;a++){
                for(int b = mineNums.get(i)%col-1;b<mineNums.get(i)%col+2;b++){
               
                    if(((a>-1 && a<row)&&(b>-1 && b <col))&&(grid[a][b]!=-1)){ //checks if the location is in bounds and is not -1
                                
                        grid[a][b]=grid[a][b]+1; // increments the value of the location checked;
                    }
                }
            }    
        }
        for(int i=0;i<row;i++){ 
            for(int j=0;j<col;j++){
            SweepButton button = new SweepButton(); // creates new button for each location in the grid
            button.setIcon(TILE); //sets the icon to the tile icon
            button.setValue(grid[i][j]); // sets value fo the button
            button.setLoc(i, j); // sends location to the button class
            
            button.addMouseListener(new mouseThing());// adds a mouse listener
            buttons[i][j]= button;// adds the button to the array of buttons 
            panel.add(button);// adds the button to the panel
            }
        } 
      
        
    }
    public static void mineHit(){ //cycles through the grid and expose non mines and set mines to the mine icon
        
    mineHasBeenHit = true;
    for(int i=0;i<row;i++){
        for(int j=0; j<col;j++){
         
            buttons[i][j].expose(); 
            
             if(buttons[i][j].getValue()== -1){

                buttons[i][j].setIcon(MINE);//sets mines to a different icon;
                timer.stop();// stops the timer
             }
            }
        }
    }
    
    public static void hitZero(int rows , int cols){
// check if the value = 0 and if the ones areound it is 0 and calls itself if it is
      int a,b;
      beenCalled[rows][cols]=true;
        for (a = rows - 1; a <=rows+1 ;a++){
            for(b = cols -1; b<=cols+1 ;b++){
                
                if(((a > -1 && a <row)&&(b>-1 && b <col))){ // checks if it is inbounds 
                    
                    buttons[a][b].expose();
                    
                    if(buttons[a][b].getValue()==0 && !beenCalled[a][b]){// checks if the grid value is 0 and has never been 
                                                                         //called by the method to prevent stackoverflow
                                    
                        
                        System.out.println(a+" "+b);
                        hitZero(a,b); // calls itself 
                  
                            
                            
                        
                    }
                }
            }  
        }
    }
    public ImageIcon scaleImage(ImageIcon initialImage){//scales the images
     
       
       Image newImage = initialImage.getImage().getScaledInstance(40, -1, Image.SCALE_DEFAULT);
       
       ImageIcon scaledImage = new ImageIcon(newImage);
       
       return  scaledImage; 
    } 
    
    
    
    public static void  win(){// cycles through the array if the number of spaces that have been exposed = the total number - number of mines
        
        if((numExposed == (row*col-Num_Mines)) && !mineHasBeenHit){
            for(int i=0;i < mineNums.size();i++){ 
                buttons[mineNums.get(i)/col][mineNums.get(i)%col].setIcon(FLAG);
                timer.stop();// stops timer
                
            }
        }
        changeScore(time);
    }
    public static void bothClick(int locRow,int locCol){// reveals the tiles around a location
        int a,b;
        
        for (a = locRow - 1; a <=locRow+1 ;a++){
            for(b = locCol -1; b<=locCol+1 ;b++){
                
                if(((a > -1 && a <row)&&(b>-1 && b <col))){// checks if inbounds
                    if(buttons[a][b].getValue()>-1){
                        buttons[a][b].expose();
                    }
                    if(buttons[a][b].getValue()==-1&& !buttons[a][b].isFlagged()){// if it is a mine and it has not been flagged calls the minehit method
                        mineHit();
                    }
                        
                }
            }
        }
    }
    public static void Reset(){// resets the game
        
        
        //sets everything to their initial values
        numOfFlags = Num_Mines;
        flagUpdate();
        
        time = 0;
        timer.stop();

        mineHasBeenHit = false;
        numExposed = 0;
        mineNums = null;
        grid = null;
        grid = new int[row][col];
        
        beenCalled = null;
        beenCalled = new boolean[row][col];
        for (int i =0;i<row;i++){ 
            for (int j =0;j<col;j++){
                buttons[i][j].reset();
                buttons[i][j].setText(null);
                buttons[i][j].setIcon(TILE);
            }
        }
        
        
        
        mineNums = new ArrayList<>();
            while (mineNums.size()< Num_Mines) {// makes sure the ArrayList is filled to the number of desired mines
                int loc = rand.nextInt(row*col-1);//random integer from 0 to row*col-1
                if(!mineNums.contains(loc)){//checks if the number is already in the arraylist
                mineNums.add(loc);//adds random int
                }
          
            }
            for(int i=0;i < mineNums.size();i++){ //sets the location of the mine in the 2d array
                grid[mineNums.get(i)/col][mineNums.get(i)%col] = -1;
            }
        
            for(int i=0;i < mineNums.size();i++){ //adds 1 to the numbers around the mine location 
                for(int a = mineNums.get(i)/col-1;a<mineNums.get(i)/col+2;a++){
                    for(int b = mineNums.get(i)%col-1;b<mineNums.get(i)%col+2;b++){
               
                        if(((a>-1 && a<row)&&(b>-1 && b <col))&&(grid[a][b]!=-1)){
                                
                            grid[a][b]=grid[a][b]+1;
                        }
                    }
                }    
            }
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
            
            buttons[i][j].setIcon(TILE);
            buttons[i][j].setValue(grid[i][j]);
            }
        }
    }
    public static void selectMode(int a){//difficulty selector
        
        //checks what kind of level is desire
      
        if(a==11){//beginner
            row = 8;
            col = 8;
            Num_Mines = 10;
        }
        if(a==12){//mild
            row = 16;
            col = 16;
            Num_Mines = 40;
        }
        
        if(a==13){//expert
            row = 16;
            col = 30;
            Num_Mines = 99;
        }
        
        //resets to initial values
        numExposed = 0;
        numOfFlags = Num_Mines;
        flagUpdate();
        
        finPanel = null;
        finPanel = new JPanel();
        finPanel.setLayout(new BoxLayout(finPanel, BoxLayout.PAGE_AXIS));
       
        
        mineHasBeenHit = false;
        
        
        mineNums = null;
        grid = null;
        grid = new int[row][col];
        
        beenCalled = null;
        beenCalled = new boolean[row][col];
        
        buttons = null;
        buttons = new SweepButton[row][col];
        
        panel= null;    
        panel= new JPanel();
        panel.setLayout(new GridLayout(row,col));
        // calls the fill to create and but with the updated values
        fill();
                  
        
        //resets the frames 
        frame.getContentPane().removeAll();
        
        
        
        finPanel.add(highScoreButt);
        finPanel.add(difPanel);
        finPanel.add(resetPanel);
        finPanel.add(panel);
        
        frame.add(finPanel);
        frame.pack();
        frame.setVisible(true);
    
    }
    static void highScore(){
        
        FileReader oName = null;
        BufferedReader bReadName;
        String theName = null;
        
        
        JPanel finScorePanel = new JPanel();
        finScorePanel.setLayout(new BoxLayout(finScorePanel, BoxLayout.PAGE_AXIS));
        
        JPanel highScorePanel = new JPanel();
        highScorePanel.setLayout(new GridLayout(1,7));
        
        JPanel difHighScorePanel = new JPanel();
        difHighScorePanel.setLayout(new GridLayout(1,4));
       
        highScore = new JFrame();
        
        JLabel space = new JLabel();
        difHighScorePanel.add(space);
        
        JLabel beginner = new JLabel();
        beginner.setText("basic");
        difHighScorePanel.add(beginner);
        
        JLabel mild = new JLabel();
        mild.setText("mild");
        difHighScorePanel.add(mild);
        
        JLabel expert = new JLabel();
        expert.setText("expert");
        difHighScorePanel.add(expert);
        
        File test = null;
        File name = null;
        
        for(int i = 0;i <4;i++ ){
           if(i==0){
           test = new File("score/place.txt");
           }
           if(i==1){
           test = new File("score/bHS.txt");
           name = new File("score/bNameHS.txt");
           }
           if(i==2){
           test = new File("score/mHS.txt");
           name = new File("score/mNameHS.txt");
           }
           if(i==3){
           test = new File("score/eHS.txt");
           name = new File("score/eNameHS.txt");
           } 
            
                try
            {
                JPanel scorePanel = new JPanel();
                JPanel namePanel = new JPanel();
                scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.PAGE_AXIS));
                namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.PAGE_AXIS));
        
                FileReader fr = new FileReader(test);
                BufferedReader bRead = new BufferedReader(fr);
                
                
                if(name != null){
                oName = new FileReader(name);
                bReadName = new BufferedReader(oName);
                theName = bReadName.readLine();
                }
             



                String data = bRead.readLine();
                
           
                while(data != null)
                {
                    if(name!= null){
                    JLabel scoreName = new JLabel();
                    scoreName.setText(theName);
                    namePanel.add(scoreName);
                    }
                    
                    JLabel label = new JLabel();
                    label.setText(data);
                
                    scorePanel.add(label);
                
                    
                
                    data = bRead.readLine();
                    
                
                }
                highScorePanel.add(namePanel);
                highScorePanel.add(scorePanel);
                fr.close();//close the file
                if(name != null){
                    
                
                oName.close();
                }
            }
        
            catch(IOException e)
            {
                System.out.println(e);
            }
                
            finScorePanel.add(difHighScorePanel);
            finScorePanel.add(highScorePanel);
            
            highScore.add(finScorePanel);
            highScore.pack();
            highScore.setVisible(true);
        }
    }
    public static void changeScore(int newScore){
        
        String newName = null;
        
        int count = 0;
        System.out.println(newScore);
                
        boolean scoreBeenCalled = false;
        File test = null;
        File name = null;
        if(Num_Mines == 10){
            test = new File("bHS.txt");
            name = new File("bNameHS.txt");
            newName = JOptionPane.showInputDialog("name");
        }
        if(Num_Mines == 40){
            test = new File("mHS.txt");
            name = new File("bNameHS.txt");
            newName = JOptionPane.showInputDialog("name");
        }
        if(Num_Mines == 99){
            test = new File("eHS.txt");
            name = new File("bNameHS.txt");
            newName = JOptionPane.showInputDialog("name");
        }
        
        try
        {
            FileReader old = new FileReader(test);
            BufferedReader bRead = new BufferedReader(old);
            
            FileReader oldName = new FileReader(name);
            BufferedReader bReadName = new BufferedReader(oldName);

            String[] oldData = new String[10];
            String[] oldNameAr = new String[10];
            for(int i = 0;i<10;i++){
                oldNameAr[i] = bReadName.readLine();
                oldData[i] = bRead.readLine();
            }
            old.close();
            oldName.close();
            
            FileWriter fw = new FileWriter(test);
            FileWriter fwName = new FileWriter(name);
            
            
            
            while(count<10){
                if((Integer.parseInt(oldData[count]) > newScore) && !scoreBeenCalled){
                    fw.write(Integer.toString(newScore));
                    fwName.write(newName);
                    scoreBeenCalled = true;
                    fw.write("\r\n");
                }
                else{
                    
                    fw.write(oldData[count]);
                    fw.write(oldNameAr[count]);
                    fw.write("\r\n");
                    count++;
                }
                
            }
            
            fw.close();
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

}