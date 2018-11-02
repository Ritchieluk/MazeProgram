import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MazeTest extends JFrame{

    public JPanel panel, controls;
    public JButton quit, generate, reset;
    public Cell cells[][];
    public Cell currCell;
    public int currX = 0, currY = 0, finished = 0;
    public boolean start = false;
    private Stack<Cell> path = new Stack<>();

    public MazeTest () {

        super("Maze");

        Cell cells[][] = new Cell[5][5];
        quit = new JButton("Quit");
        reset = new JButton("Reset");
        generate = new JButton("Generate");
        controls = new JPanel();
        panel = new JPanel();
        panel.setLayout(new GridLayout(5,5,0,0));
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });


        quit.addActionListener(e -> System.exit(0));
        reset.addActionListener(e -> Reset(cells));
        generate.addActionListener(e -> {
            if (finished == 0){
                finished = Generate(cells);
            }
            else if (finished == 1) {
                finished = Solver(cells);
            }
            else if (finished == 2) {
                finished = backSolver(cells);
            }
        });
        controls.add(generate);
        controls.add(reset);
        controls.add(quit);
        for (int y = 0;y<5;y++){
            for (int x = 0; x < 5; x++){
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
            currCell.status++;
            currCell.change();
            path.push(currCell);

        }
        int sides[] = new int[currCell.numOptions(cells, currX, currY)];
        sides = currCell.getOptions(sides, cells, currX, currY);

        if (sides.length != 0) {
            rnd = new Random().nextInt(currCell.numOptions(cells, currX, currY));
            rnd = sides[rnd];
        }

        if (rnd == 1 && currY > 0 && cells[currY-1][currX].status==0){
            currCell.North = 0;
            currCell.change();
            currY--;
            currCell = cells[currY][currX];
            currCell.South = 0;
            currCell.status++;
            path.push(currCell);
            currCell.change();
        }
        if (rnd == 2 && currX > 0 && cells[currY][currX-1].status==0){
            currCell.West = 0;
            currCell.change();
            currX--;
            currCell = cells[currY][currX];
            currCell.East = 0;
            currCell.status++;
            path.push(currCell);
            currCell.change();

        }
        if (rnd == 3 && currY < 4 && cells[currY+1][currX].status==0){
            currCell.South = 0;
            currCell.change();
            currY++;
            currCell = cells[currY][currX];
            currCell.North = 0;
            currCell.status++;
            path.push(currCell);
            currCell.change();
        }
        if (rnd == 4 && currX < 4 && cells[currY][currX+1].status==0){
            currCell.East = 0;
            currCell.change();
            currX++;
            currCell = cells[currY][currX];
            currCell.West = 0;
            currCell.status++;
            path.push(currCell);
            currCell.change();
        }
        if (currCell.numOptions(cells, currX, currY) == 0){
            backGenerate(cells);
        }
        if (currCell.getCurrX() == 4 && currCell.getCurrY() == 4){
            currCell.setBackground(Color.GREEN);
            path.pop();
            currCell = path.peek();
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

        return 1;
    }

    public int backSolver(Cell[][] cells){

        return 2;
    }


    public void Reset(Cell[][] cells){
        for (int x = 0; x < 5; x++){
            for (int y = 0; y < 5; y++){
                cells[y][x].reset();
            }
        }
        currX = 0;
        currY = 0;
        start = false;

    }

    public static void main(String argv[])
    {
        new MazeTest();
    }

}
