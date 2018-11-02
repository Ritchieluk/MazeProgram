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
