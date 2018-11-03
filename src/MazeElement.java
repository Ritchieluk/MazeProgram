//Class: MazeElement
//Purpose:
import javax.swing.*;
import java.awt.*;

public class MazeElement extends JPanel {


    //These next four booleans refer to the four tiles N/E/S/W of the current tile.
    //Each will be true if there is a connection to that element
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
        else if(status == 4)
            setBackground(Color.YELLOW);
        else
            setBackground(Color.GREEN);
        //repaint();
    }/*
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(this.getX(), this.getY(), 5, 5);
        g.drawRect(getX(), getY()+15, 5, 5);
        g.drawRect(getX()+15,getY(),5,5);
        g.drawRect(getX()+15, getY()+15, 5, 5);
        g.setColor(Color.BLACK);
        g.fillRect(getX(), getY(), 5, 5);
        g.fillRect(getX(), getY()+15, 5, 5);
        g.fillRect(getX()+15,getY(),5,5);
        g.fillRect(getX()+15, getY()+15, 5, 5);

    }*/

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


    public Dimension getPreferredSize(){
        return new Dimension(15, 15);
    }
    public int numOptions(MazeElement[][] cells, int currX, int currY){
        int retval = 0;
        if (northElement != 0 && currY > 0) {
            if (cells[currX][currY-1].status < status) {
                retval++;
            }
        }
        if (westElement != 0 && currX > 0) {
            if (cells[currX-1][currY].status < status) {
                retval++;
            }
        }
        if (southElement != 0 && currY < 4) {
            if (cells[currX][currY+1].status < status) {
                retval++;
            }
        }
        if (eastElement != 0 && currX < 4) {
            if (cells[currX+1][currY].status < status) {
                retval++;
            }
        }
        return retval;
    }
    public int[] getOptions(int[] sides, MazeElement[][] cells, int currX, int currY){
        int count = 0;
        if (numOptions(cells, currX, currY) !=  4) {
            if (northElement != 0 && currY > 0) {
                if (cells[currY-1][currX].status < status) {
                    sides[count] = 1;
                    count++;
                }
            }
            if (westElement != 0 && currX > 0) {
                if (cells[currY][currX-1].status < status) {
                    sides[count] = 2;
                    count++;
                }
            }
            if (southElement != 0 && currY < 4) {
                if (cells[currY+1][currX].status < status) {
                    sides[count] = 3;
                    count++;
                }
            }
            if (eastElement != 0 && currX < 4) {
                if (cells[currY][currX+1].status < status) {
                    sides[count] = 4;
                    count++;
                }
            }
        }
        return sides;
    }
    public void Reset(){
        northElement=2;
        southElement=2;
        westElement=2;
        eastElement=2;
        status=0;
        drawMyself();
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
    public void setXY(int currX, int currY){
        x = currX;
        y = currY;
    }
    public int getCurrX(){return x;}

    public int getCurrY() {return y;}

    public void incStatus() {status++;}








}