import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Stack;

public class MazeElementDriver extends JPanel {
    private int width;
    private int height;
    private MazeElement currElement;
    private int currX = 0, currY = 0, size = 10;
    private MazeElement tiles[][];
    private Stack<MazeElement> path = new Stack<>();


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
        setPreferredSize(getPreferredSize());
    }
    public int generate() {
        int rnd = 0;
        currElement = tiles[currY][currX];
        currElement.incStatus();
        currElement.drawMyself();
        path.push(currElement);

        int sides[] = new int[currElement.numOptionsGen(tiles, currX, currY)];
        sides = currElement.getOptionsGen(sides, tiles, currX, currY);

        if (sides.length != 0) {
            rnd = new Random().nextInt(currElement.numOptionsGen(tiles, currX, currY));
            rnd = sides[rnd];
        }

        if (rnd == 1 && currY > 0 && tiles[currY-1][currX].getStatus()==0){
            currElement.setNorth();
            currElement.drawMyself();
            currY--;
            currElement = tiles[currY][currX];
            currElement.setSouth();
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (rnd == 2 && currX > 0 && tiles[currY][currX-1].getStatus()==0){
            currElement.setWest();
            currElement.drawMyself();
            currX--;
            currElement = tiles[currY][currX];
            currElement.setEast();
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();

        }
        if (rnd == 3 && currY < size-1 && tiles[currY+1][currX].getStatus()==0){
            currElement.setSouth();
            currElement.drawMyself();
            currY++;
            currElement = tiles[currY][currX];
            currElement.setNorth();
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (rnd == 4 && currX < size-1 && tiles[currY][currX+1].getStatus()==0){
            currElement.setEast();
            currElement.drawMyself();
            currX++;
            currElement = tiles[currY][currX];
            currElement.setWest();
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (currElement.getCurrX() == size -1 && currElement.getCurrY() == size-1){
            currElement.setBackground(Color.YELLOW);
            path.pop();
            currElement = path.peek();
            backGenerate();
        }
        if (currElement.numOptionsGen(tiles, currX, currY) == 0){
            backGenerate();
        }
        if (path.empty()){
            return 1;
        }
        else{return 0;}

    }

    public void backGenerate(){

        if(currElement.numOptionsGen(tiles, currX, currY) == 0){
            if (path.empty()){return;}
            path.pop();
            if (path.empty()){return;}
            currElement = path.peek();
            currX = currElement.getCurrX();
            currY = currElement.getCurrY();
            backGenerate();
        }
        else{
            currX = currElement.getCurrX();
            currY = currElement.getCurrY();
        }

    }

    public int solver(){
        int rnd = 0;
        currElement = tiles[currY][currX];
        currElement.incStatus();
        currElement.drawMyself();
        path.push(currElement);
        int sides[] = new int[currElement.numOptionsSolve(tiles, currX, currY)];

        sides = currElement.getOptionsSolve(sides, tiles, currX, currY);

        if (sides.length != 0) {
            rnd = new Random().nextInt(currElement.numOptionsSolve(tiles, currX, currY));
            rnd = sides[rnd];
        }


        if (rnd == 1 && currY > 0 && tiles[currY-1][currX].getStatus()==1){
            currY--;
            currElement = tiles[currY][currX];
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (rnd == 2 && currX > 0 && tiles[currY][currX-1].getStatus()==1){
            currX--;
            currElement = tiles[currY][currX];
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();

        }
        if (rnd == 3 && currY < size-1 && tiles[currY+1][currX].getStatus()==1){
            currY++;
            currElement = tiles[currY][currX];
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (rnd == 4 && currX < size-1 && tiles[currY][currX+1].getStatus()==1){
            currX++;
            currElement = tiles[currY][currX];
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (currElement.getCurrX() == size -1 && currElement.getCurrY() == size-1){
            currElement.setBackground(Color.YELLOW);
            return 3;
        }
        if (currElement.numOptionsSolve(tiles, currX, currY) == 0){
            return 2;
        }

        return 1;

    }

    public int backSolver(){
        if(currElement.numOptionsSolve(tiles, currX, currY) == 0){
            currElement.incStatus();
            currElement.drawMyself();
            path.pop();
            currElement = path.peek();
            currX = currElement.getCurrX();
            currY = currElement.getCurrY();
        }
        else{
            currX = currElement.getCurrX();
            currY = currElement.getCurrY();
            return 1;
        }
        return 2;
    }
    public Dimension getPreferredSize(){
        return new Dimension(width*20, height*20);
    }
    public void reset(){
        setupTiles();
        currX = 0;
        currY = 0;
    }

}

