//Class: MazeElement
//Purpose: The model component of the Maze program
import javax.swing.*;
import java.awt.*;

// the MazeElement is a JPanel, a basic swing component
public class MazeElement extends JPanel {


    //These ints will represent if a wall exists or not
    // Because of the BorderFactory.createMatteBorder function,
    // when inputting these ints, a number will create that wall's thickness, but
    // a 0 will create a blank, making it seem connected to nearby neighbors with adjacent
    // blank walls. We use this to create connections in the maze
    private int northElement = 2;
    private int eastElement = 2;
    private int southElement = 2;
    private int westElement = 2;
    //x and y keep the coordinates of the element relative to the matrix of all the elements
    private int x, y;
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

    // when initialized, it sets its status to 0 and then draws itself
    public MazeElement(){
        super();
        status = 0;
        drawMyself();
    }
    // drawMyself is used in place of any repaint function
    // depending upon the current status, it paints the background
    // and builds the walls of the current cell.
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
        else if(status == 4)
            setBackground(Color.YELLOW);
        else
            setBackground(Color.GREEN);
    }
    // getOptionsGen will look populate a given array with numbers representing the
    // available neighbors for a given tile in a given board.
    // Ideally this function would be in a view class, but like I said I had issues and
    // the time constraits forced me to abandon that framework
    public int[] getOptionsGen(int[] sides, MazeElement[][] elements, int currRow, int currCol, int r, int c){
        int count = 0;
        if (numOptionsGen(elements, currRow, currCol, r, c) !=  4) {
            if (northElement != 0 && currRow > 0) {
                if (elements[currRow-1][currCol].getStatus() < status) {
                    sides[count] = 1;
                    count++;
                }
            }
            if (westElement != 0 && currCol > 0) {
                if (elements[currRow][currCol-1].getStatus() < status) {
                    sides[count] = 2;
                    count++;
                }
            }
            if (southElement != 0 && currRow < r -1) {
                if (elements[currRow+1][currCol].getStatus() < status) {
                    sides[count] = 3;
                    count++;
                }
            }
            if (eastElement != 0 && currCol < c-1) {
                if (elements[currRow][currCol+1].getStatus() < status) {
                    sides[count] = 4;
                }
            }
        }
        return sides;
    }
    // getOptionsSolve does a very similar thing to getOptionsGen just with different
    // safe choice requirements
    public int[] getOptionsSolve(int[] sides, MazeElement[][] elements, int currRow, int currCol, int r, int c){
        int count = 0;
        if (numOptionsSolve(elements, currRow, currCol, r, c) !=  4) {
            if (northElement == 0 && currRow > 0) {
                if (elements[currRow-1][currCol].getStatus() < status) {
                    sides[count] = 1;
                    count++;
                }
            }
            if (westElement == 0 && currCol > 0) {
                if (elements[currRow][currCol-1].getStatus() < status) {
                    sides[count] = 2;
                    count++;
                }
            }
            if (southElement == 0 && currRow < r -1) {
                if (elements[currRow+1][currCol].getStatus() < status) {
                    sides[count] = 3;
                    count++;
                }
            }
            if (eastElement == 0 && currCol < c-1) {
                if (elements[currRow][currCol+1].getStatus() < status) {
                    sides[count] = 4;
                    count++;
                }
            }
        }
        return sides;
    }
    // numOptionsGen returns an int equal to the number of available moves from a given tile
    public int numOptionsGen(MazeElement[][] elements, int currRow, int currCol, int r, int c){
        int retval = 0;
        if (northElement != 0 && currRow > 0) {
            if (elements[currRow-1][currCol].getStatus() < status) {
                retval++;
            }
        }
        if (westElement != 0 && currCol > 0) {
            if (elements[currRow][currCol-1].getStatus() < status) {
                retval++;
            }
        }
        if (southElement != 0 && currRow < r-1) {
            if (elements[currRow+1][currCol].getStatus() < status) {
                retval++;
            }
        }
        if (eastElement != 0 && currCol < c-1) {
            if (elements[currRow][currCol+1].getStatus() < status) {
                retval++;
            }
        }
        return retval;
    }
    // numOptionsSolve is similar to numOptionsGen except with a safe move comparison
    // that fits the Solve functionality
    public int numOptionsSolve(MazeElement[][] elements, int currRow, int currCol, int r, int c){
        int retval = 0;
        if (northElement == 0 && currRow > 0) {
            if (elements[currRow-1][currCol].getStatus() < status) {
                retval++;
            }
        }
        if (westElement == 0 && currCol > 0) {
            if (elements[currRow][currCol-1].getStatus() < status) {
                retval++;
            }
        }
        if (southElement == 0 && currRow < r -1) {
            if (elements[currRow+1][currCol].getStatus() < status) {
                retval++;
            }
        }
        if (eastElement == 0 && currCol < c-1) {
            if (elements[currRow][currCol+1].getStatus() < status) {
                retval++;
            }
        }
        return retval;
    }

    // we set the preferred size of each panel so that when we pack the Maze it isn't too large
    public Dimension getPreferredSize(){
        return new Dimension(15, 15);
    }
    // Reset returns a tile to its initial values and then redraws it
    public void Reset(){
        northElement=2;
        southElement=2;
        westElement=2;
        eastElement=2;
        status=0;
        drawMyself();
    }
    // the setDirection functions will remove that wall. Since there is no need to ever add a wall back
    // these functions just set them to 0.
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
    public void setXY(int currX, int currY){
        x = currX;
        y = currY;
    }
    public int getCurrX(){return x;}

    public int getCurrY() {return y;}
    // incStatus ups the status to the next level.
    // this is the best function to use rather than setting the status each time you need to change it.
    public void incStatus() {status++;}
}