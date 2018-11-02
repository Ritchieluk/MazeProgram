import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MazeTest extends JFrame{

    private Timer gameTimer;
    private JPanel panel, controls;
    private JButton quit, generate, reset, stop;
    private Cell cells[][];
    private Cell currCell;
    private int currX = 0, currY = 0, finished = 0, size = 10;
    private boolean start = false;
    private Stack<Cell> path = new Stack<>();

    public MazeTest () {

        super("Maze");

        cells = new Cell[size][size];
        quit = new JButton("Quit");
        stop = new JButton("Stop");
        reset = new JButton("Reset");
        generate = new JButton("Generate");
        controls = new JPanel();
        panel = new JPanel();
        panel.setLayout(new GridLayout(size,size,0,0));
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        stop.addActionListener(e-> gameTimer.stop());


        quit.addActionListener(e -> System.exit(0));
        reset.addActionListener(e -> Reset(cells));
        generate.addActionListener(e -> {
            gameTimer = new Timer(1, f -> {
                if (finished == 0){
                    finished = Generate(cells);
                }
                else if (finished == 1) {
                    finished = Solver(cells);
                }
                else if (finished == 2) {
                    finished = backSolver(cells);
                }
                else if (finished == 3) {
                    gameTimer.stop();
                }
            });
            gameTimer.start();
        });
        controls.add(generate);
        controls.add(stop);
        controls.add(reset);
        controls.add(quit);
        for (int y = 0;y<size;y++){
            for (int x = 0; x < size; x++){
                cells[y][x] = new Cell();
                cells[y][x].setXY(x, y);
                panel.add(cells[y][x]);
            }
        }


        add(controls, BorderLayout.NORTH);
        add(panel, BorderLayout.SOUTH);
        pack();
        setVisible(true);

    }

    public int Generate(Cell cells[][]) {
        int rnd = 0;
        if (!start){
            currCell = cells[currY][currX];
            start = true;
            currCell.incStatus();
            currCell.change();
            path.push(currCell);

        }
        int sides[] = new int[currCell.numOptions(cells, currX, currY)];
        sides = currCell.getOptions(sides, cells, currX, currY);

        if (sides.length != 0) {
            rnd = new Random().nextInt(currCell.numOptions(cells, currX, currY));
            rnd = sides[rnd];
        }

        if (rnd == 1 && currY > 0 && cells[currY-1][currX].getStatus()==0){
            currCell.setNorth();
            currCell.change();
            currY--;
            currCell = cells[currY][currX];
            currCell.setSouth();
            currCell.incStatus();
            path.push(currCell);
            currCell.change();
        }
        if (rnd == 2 && currX > 0 && cells[currY][currX-1].getStatus()==0){
            currCell.setWest();
            currCell.change();
            currX--;
            currCell = cells[currY][currX];
            currCell.setEast();
            currCell.incStatus();
            path.push(currCell);
            currCell.change();

        }
        if (rnd == 3 && currY < size-1 && cells[currY+1][currX].getStatus()==0){
            currCell.setSouth();
            currCell.change();
            currY++;
            currCell = cells[currY][currX];
            currCell.setNorth();
            currCell.incStatus();
            path.push(currCell);
            currCell.change();
        }
        if (rnd == 4 && currX < size-1 && cells[currY][currX+1].getStatus()==0){
            currCell.setEast();
            currCell.change();
            currX++;
            currCell = cells[currY][currX];
            currCell.setWest();
            currCell.incStatus();
            path.push(currCell);
            currCell.change();
        }
        if (currCell.getCurrX() == size -1 && currCell.getCurrY() == size-1){
            currCell.setBackground(Color.YELLOW);
            path.pop();
            currCell = path.peek();
            backGenerate(cells);
        }
        if (currCell.numOptions(cells, currX, currY) == 0){
            backGenerate(cells);
        }
        if (path.empty()){
            start = false;
            return 1;
        }
        else{return 0;}

    }

    public void backGenerate(Cell[][] cells){

        if(currCell.numOptions(cells, currX, currY) == 0){
            if (path.empty()){return;}
            path.pop();
            if (path.empty()){return;}
            currCell = path.peek();
            currX = currCell.getCurrX();
            currY = currCell.getCurrY();
            backGenerate(cells);
        }
        else{
            currX = currCell.getCurrX();
            currY = currCell.getCurrY();
        }

    }

    public int Solver(Cell[][] cells){
        int rnd = 0;
        if (!start){
            currCell = cells[currY][currX];
            start = true;
            currCell.incStatus();
            currCell.change();
            path.push(currCell);
        }
        int sides[] = new int[currCell.numOptions2(cells, currX, currY)];

        sides = currCell.getOptions2(sides, cells, currX, currY);

        if (sides.length != 0) {
            rnd = new Random().nextInt(currCell.numOptions2(cells, currX, currY));
            rnd = sides[rnd];
        }


        if (rnd == 1 && currY > 0 && cells[currY-1][currX].getStatus()==1){
            currY--;
            currCell = cells[currY][currX];
            currCell.incStatus();
            path.push(currCell);
            currCell.change();
        }
        if (rnd == 2 && currX > 0 && cells[currY][currX-1].getStatus()==1){
            currX--;
            currCell = cells[currY][currX];
            currCell.incStatus();
            path.push(currCell);
            currCell.change();

        }
        if (rnd == 3 && currY < size-1 && cells[currY+1][currX].getStatus()==1){
            currY++;
            currCell = cells[currY][currX];
            currCell.incStatus();
            path.push(currCell);
            currCell.change();
        }
        if (rnd == 4 && currX < size-1 && cells[currY][currX+1].getStatus()==1){
            currX++;
            currCell = cells[currY][currX];
            currCell.incStatus();
            path.push(currCell);
            currCell.change();
        }
        if (currCell.getCurrX() == size -1 && currCell.getCurrY() == size-1){
            currCell.setBackground(Color.YELLOW);
            return 3;
        }
        if (currCell.numOptions2(cells, currX, currY) == 0){
            return 2;
        }

        return 1;

    }

    public int backSolver(Cell[][] cells){
        if(currCell.numOptions2(cells, currX, currY) == 0){
            currCell.incStatus();
            currCell.change();
            path.pop();
            currCell = path.peek();
            currX = currCell.getCurrX();
            currY = currCell.getCurrY();
        }
        else{
            currX = currCell.getCurrX();
            currY = currCell.getCurrY();
            return 1;
        }
        return 2;
    }


    public void Reset(Cell[][] cells){
        for (int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
                cells[y][x].reset();
            }
        }
        currX = 0;
        currY = 0;
        finished = 0;
        start = false;
        gameTimer.stop();

    }

    public static void main(String argv[])
    {
        new MazeTest();
    }

}
