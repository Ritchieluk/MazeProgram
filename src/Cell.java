import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    private int North = 5;
    private int South = 5;
    private int East = 5;
    private int West = 5;
    private int status = 0;
    private int x, y, size = 10;

    public Cell(){

        setPreferredSize(new Dimension(50,50));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createMatteBorder(North, West, South, East, Color.BLACK));

    }
    public void change(){
        if (status == 0) { setBackground(Color.BLACK);}
        if (status == 1) { setBackground(Color.BLUE);}
        if (status == 2) { setBackground(Color.GREEN);}
        if (status == 3) { setBackground(Color.GRAY);}
        setBorder(BorderFactory.createMatteBorder(North, West, South, East, Color.BLACK));
    }

    public int[] getOptionsGen(int[] sides, Cell[][] cells, int currX, int currY){
        int count = 0;
        if (numOptionsGen(cells, currX, currY) !=  4) {
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
            if (South != 0 && currY < size -1) {
                if (cells[currY+1][currX].status < status) {
                    sides[count] = 3;
                    count++;
                }
            }
            if (East != 0 && currX < size-1) {
                if (cells[currY][currX+1].status < status) {
                    sides[count] = 4;
                    count++;
                }
            }
        }
        return sides;
    }
    public int[] getOptionsSolve(int[] sides, Cell[][] cells, int currX, int currY){
        int count = 0;
        if (numOptionsSolve(cells, currX, currY) !=  4) {
            if (North == 0 && currY > 0) {
                if (cells[currY-1][currX].status < status) {
                    sides[count] = 1;
                    count++;
                }
            }
            if (West == 0 && currX > 0) {
                if (cells[currY][currX-1].status < status) {
                    sides[count] = 2;
                    count++;
                }
            }
            if (South == 0 && currY < size -1) {
                if (cells[currY+1][currX].status < status) {
                    sides[count] = 3;
                    count++;
                }
            }
            if (East == 0 && currX < size-1) {
                if (cells[currY][currX+1].status < status) {
                    sides[count] = 4;
                    count++;
                }
            }
        }
        return sides;
    }

    public int numOptionsGen(Cell[][] cells, int currX, int currY){
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
        if (South != 0 && currY < size -1) {
            if (cells[currY+1][currX].status < status) {
                retval++;
            }
        }
        if (East != 0 && currX < size-1) {
            if (cells[currY][currX+1].status < status) {
                retval++;
            }
        }
        return retval;
    }
    public int numOptionsSolve(Cell[][] cells, int currX, int currY){
        int retval = 0;
        if (North == 0 && currY > 0) {
            if (cells[currY-1][currX].status < status) {
                retval++;
            }
        }
        if (West == 0 && currX > 0) {
            if (cells[currY][currX-1].status < status) {
                retval++;
            }
        }
        if (South == 0 && currY < size -1) {
            if (cells[currY+1][currX].status < status) {
                retval++;
            }
        }
        if (East == 0 && currX < size-1) {
            if (cells[currY][currX+1].status < status) {
                retval++;
            }
        }
        return retval;
    }


    public void setXY(int currX, int currY){
        x = currX;
        y = currY;
    }

    public int getCurrX(){return x;}

    public int getCurrY() {return y;}

    public int getNorth() {return North;}

    public void setNorth() {North = 0;}

    public int getSouth() {return South;}

    public void setSouth() {South = 0;}

    public int getEast() {return East;}

    public void setEast() {East = 0;}

    public int getWest() {return West;}

    public void setWest() {West = 0;}

    public int getStatus() {return status;}

    public void incStatus() {status++;}



    public void reset(){
        North = 5;
        West = 5;
        South = 5;
        East = 5;
        status = 0;
        change();
    }


}
