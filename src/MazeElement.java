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
import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    public int North = 10;
    public int South = 10;
    public int East = 10;
    public int West = 10;
    public int status = 0;

    public Cell(){

        setPreferredSize(new Dimension(50,50));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createMatteBorder(North, West, South, East, Color.BLACK));

    }
    public void change(){
        if (status == 0) { setBackground(Color.BLACK);}
        if (status == 1) { setBackground(Color.BLUE);}
        setBorder(BorderFactory.createMatteBorder(North, West, South, East, Color.BLACK));
    }

    public int[] getOptions(int[] sides, Cell[][] cells, int currX, int currY){
        int count = 0;
        if (numOptions(cells, currX, currY) !=  4) {
            if (North != 0 && currY > 0) {
                if (cells[currY-1][currX].status < status) {
                    sides[count] = 1;
                    count++;
                }
            }
            if (West != 0 && currX > 0) {
                if (cells[currY][currX-1].status < status) {
                    sides[count] = 2;
                    count++;
                }
            }
            if (South != 0 && currY < 4) {
                if (cells[currY+1][currX].status < status) {
                    sides[count] = 3;
                    count++;
                }
            }
            if (East != 0 && currX < 4) {
                if (cells[currY][currX+1].status < status) {
                    sides[count] = 4;
                    count++;
                }
            }
        }
        return sides;
    }

    public int numOptions(Cell[][] cells, int currX, int currY){
        int retval = 0;
        if (North != 0 && currY > 0) {
            if (cells[currY-1][currX].status < status) {
                retval++;
            }
        }
        if (West != 0 && currX > 0) {
            if (cells[currY][currX-1].status < status) {
                retval++;
            }
        }
        if (South != 0 && currY < 4) {
            if (cells[currY+1][currX].status < status) {
                retval++;
            }
        }
        if (East != 0 && currX < 4) {
            if (cells[currY][currX+1].status < status) {
                retval++;
            }
        }
        return retval;
    }

    public void reset(){
        North = 10;
        West = 10;
        South = 10;
        East = 10;
        status = 0;
        change();
    }
}
