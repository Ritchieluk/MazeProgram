import javax.swing.*;
import java.awt.*;

public class MazeElement extends JPanel {


    //These next four booleans refer to the four tiles N/E/S/W of the current tile.
    //Each will be true if there is a connection to that element
    private int northElement = 5;
    private int eastElement = 5;
    private int southElement = 5;
    private int westElement = 5;
    //the status int is what determines the elements' state. By default it will
    // initialize to 0 when the Element is constructed. 0 means it will be black
    // I have put a key below status
    private int status = 0;
    /*
        status key:
        0 - Is in grid but has not been connected nor explored, is black
        1 - Has been connected, not explored is blue
        2 - Has been connected, and visited is green
        3 - Has been connected, visited, and backtracked through, is gray
     */


    public MazeElement(){
        super();
        status = 0;
        drawMyself();
    }

    public void drawMyself(){
        setBorder(BorderFactory.createMatteBorder(northElement, westElement, southElement, eastElement, Color.BLACK));
        if(status==0){
            setBackground(Color.BLACK);
        }
        else if(status == 1)
            setBackground(Color.BLUE);
        else if(status == 2)
            setBackground(Color.GREEN);
        else if(status == 3)
            setBackground(Color.GRAY);
        else
            setBackground(Color.YELLOW);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(getX(), getY(), 5, 5);
        g.drawRect(getX(), getY()+45, 5, 5);
        g.drawRect(getX()+45,getY(),5,5);
        g.drawRect(getX()+45, getY()+45, 5, 5);
        g.setColor(Color.BLACK);
        g.fillRect(getX(), getY(), 5, 5);
        g.fillRect(getX(), getY()+45, 5, 5);
        g.fillRect(getX()+45,getY(),5,5);
        g.fillRect(getX()+45, getY()+45, 5, 5);

    }
    public Dimension getPreferredSize(){
        return new Dimension(50,50);
    }
    public void setNorth() {
        northElement = 0;
    }
    public void setSouth(){
        southElement = 0;
    }
    public void setEast(){
        eastElement = 0;
    }
    public void setWest(){
        westElement = 0;
    }
    public int getStatus(){
        return status;
    }
    public void setStatus(int newStat){
        status = newStat;
    }






}