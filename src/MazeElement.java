import javax.swing.*;
import java.awt.*;

public class MazeElement extends JPanel {


    //These next four booleans refer to the four tiles N/E/S/W of the current tile.
    //Each will be true if there is a connection to that element
    private Boolean northElement = false;
    private Boolean eastElement = false;
    private Boolean southElement = false;
    private Boolean westElement = false;
    //the status int is what determines the elements' state. By default it will
    // initialize to 0 when the Element is constructed. 0 means it will be black
    // I have put a key below status
    private int status;
    /*
        status key:
        0 - Is in grid but has not been connected nor explored, is black
        1 - Has been connected, not explored is blue
        2 - Has been connected, and visited is green
        3 - Has been connected, visited, and backtracked through, is gray
     */

    public MazeElement() {super();}

    public MazeElement(int stat){
        super();
        status = 0;
        drawMyself();
    }
    public void drawMyself(){

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(230,80,10,10);
        g.setColor(Color.RED);
        g.fillRect(230,80,10,10);
    }
    public void setNorthElement(Boolean nElement) {
        northElement = nElement;
    }
    public void setSouthElement(Boolean sElement){
        southElement = sElement;
    }
    public void setEastElement(Boolean eElement){
        eastElement = eElement;
    }
    public void setWestElement(Boolean wElement){
        westElement = wElement;
    }
}
