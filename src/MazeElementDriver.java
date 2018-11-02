import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MazeElementDriver extends JPanel {
    private int width;
    private int height;
    private MazeElement currCell;
    private int currX = 0, currY = 0;
    private MazeElement tiles[][];

    public MazeElementDriver(int w, int h){
        super();
        width = w;
        height = h;
        setupTiles();



    }
    public void setupTiles(){
        tiles = new MazeElement[width][height];
        setLayout(new GridLayout(width,height,0,0));

        for(int i = 0; i<width; i++) {
            for (int j = 0; j < height; j++){
                tiles[i][j] = new MazeElement();
                tiles[i][j].setPreferredSize(tiles[i][j].getPreferredSize());
                add(tiles[i][j]);
            }
        }
    }
    public void generate(){
        int rnd = 0;
        currCell = tiles[currY][currX];
        currCell.setStatus(currCell.getStatus()+1);
        currCell.drawMyself();


        int sides[] = new int[currCell.numOptions(tiles, currX, currY)];
        sides = currCell.getOptions(sides, tiles, currX, currY);
        for (int i = 0; i < currCell.numOptions(tiles, currX, currY); i++){
            //System.out.println(sides[i]);
        }

        if (sides.length != 0) {
            System.out.println(currCell.numOptions(tiles, currX, currY));
            rnd = new Random().nextInt(currCell.numOptions(tiles, currX, currY));
            rnd = sides[rnd];
        }

        //System.out.println(rnd);
        if (rnd == 1 && currY > 0 && tiles[currY-1][currX].getStatus()==0){
            currCell.setNorth();
            currCell.drawMyself();
            currY--;
            currCell = tiles[currY][currX];
            currCell.setSouth();
            currCell.setStatus(currCell.getStatus()+1);

            currCell.drawMyself();
        }
        if (rnd == 2 && currX > 0 && tiles[currY][currX-1].getStatus()==0){
            currCell.setWest();
            currCell.drawMyself();
            currX--;
            currCell = tiles[currY][currX];
            currCell.setEast();
            currCell.setStatus(currCell.getStatus()+1);
            currCell.drawMyself();

        }
        if (rnd == 3 && currY < 4 && tiles[currY+1][currX].getStatus()==0){
            currCell.setSouth();
            currCell.drawMyself();
            currY++;
            currCell = tiles[currY][currX];
            currCell.setNorth();
            currCell.setStatus(currCell.getStatus()+1);
            currCell.drawMyself();
        }
        if (rnd == 4 && currX < 4 && tiles[currY][currX+1].getStatus()==0){
            currCell.setEast();
            currCell.drawMyself();
            currX++;
            currCell = tiles[currY][currX];
            currCell.setWest();
            currCell.setStatus(currCell.getStatus()+1);
            currCell.drawMyself();
        }
    }

    public void reset(){
        setupTiles();
        currX = 0;
        currY = 0;
    }

}
