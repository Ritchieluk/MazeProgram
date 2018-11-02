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
    private MazeElement elements[][];
    private MazeElement currElement;
    private int currX = 0, currY = 0, finished = 0, size = 10;
    private boolean start = false;
    private Stack<MazeElement> path = new Stack<>();

    public MazeTest () {

        super("Maze");

        elements = new MazeElement[size][size];
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
        reset.addActionListener(e -> Reset(elements));
        generate.addActionListener(e -> {
            gameTimer = new Timer(1, f -> {
                if (finished == 0){
                    finished = Generate(elements);
                }
                else if (finished == 1) {
                    finished = Solver(elements);
                }
                else if (finished == 2) {
                    finished = backSolver(elements);
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
                elements[y][x] = new MazeElement();
                elements[y][x].setXY(x, y);
                panel.add(elements[y][x]);
            }
        }


        add(controls, BorderLayout.NORTH);
        add(panel, BorderLayout.SOUTH);
        pack();
        setVisible(true);

    }

    public int Generate(MazeElement elements[][]) {
        int rnd = 0;
        if (!start){
            currElement = elements[currY][currX];
            start = true;
            currElement.incStatus();
            currElement.drawMyself();
            path.push(currElement);

        }
        int sides[] = new int[currElement.numOptionsGen(elements, currX, currY)];
        sides = currElement.getOptionsGen(sides, elements, currX, currY);

        if (sides.length != 0) {
            rnd = new Random().nextInt(currElement.numOptionsGen(elements, currX, currY));
            rnd = sides[rnd];
        }

        if (rnd == 1 && currY > 0 && elements[currY-1][currX].getStatus()==0){
            currElement.setNorth();
            currElement.drawMyself();
            currY--;
            currElement = elements[currY][currX];
            currElement.setSouth();
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (rnd == 2 && currX > 0 && elements[currY][currX-1].getStatus()==0){
            currElement.setWest();
            currElement.drawMyself();
            currX--;
            currElement = elements[currY][currX];
            currElement.setEast();
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();

        }
        if (rnd == 3 && currY < size-1 && elements[currY+1][currX].getStatus()==0){
            currElement.setSouth();
            currElement.drawMyself();
            currY++;
            currElement = elements[currY][currX];
            currElement.setNorth();
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (rnd == 4 && currX < size-1 && elements[currY][currX+1].getStatus()==0){
            currElement.setEast();
            currElement.drawMyself();
            currX++;
            currElement = elements[currY][currX];
            currElement.setWest();
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (currElement.getCurrX() == size -1 && currElement.getCurrY() == size-1){
            currElement.setBackground(Color.YELLOW);
            path.pop();
            currElement = path.peek();
            backGenerate(elements);
        }
        if (currElement.numOptionsGen(elements, currX, currY) == 0){
            backGenerate(elements);
        }
        if (path.empty()){
            start = false;
            return 1;
        }
        else{return 0;}

    }

    public void backGenerate(MazeElement[][] elements){

        if(currElement.numOptionsGen(elements, currX, currY) == 0){
            if (path.empty()){return;}
            path.pop();
            if (path.empty()){return;}
            currElement = path.peek();
            currX = currElement.getCurrX();
            currY = currElement.getCurrY();
            backGenerate(elements);
        }
        else{
            currX = currElement.getCurrX();
            currY = currElement.getCurrY();
        }

    }

    public int Solver(MazeElement[][] elements){
        int rnd = 0;
        if (!start){
            currElement = elements[currY][currX];
            start = true;
            currElement.incStatus();
            currElement.drawMyself();
            path.push(currElement);
        }
        int sides[] = new int[currElement.numOptionsSolve(elements, currX, currY)];

        sides = currElement.getOptionsSolve(sides, elements, currX, currY);

        if (sides.length != 0) {
            rnd = new Random().nextInt(currElement.numOptionsSolve(elements, currX, currY));
            rnd = sides[rnd];
        }


        if (rnd == 1 && currY > 0 && elements[currY-1][currX].getStatus()==1){
            currY--;
            currElement = elements[currY][currX];
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (rnd == 2 && currX > 0 && elements[currY][currX-1].getStatus()==1){
            currX--;
            currElement = elements[currY][currX];
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();

        }
        if (rnd == 3 && currY < size-1 && elements[currY+1][currX].getStatus()==1){
            currY++;
            currElement = elements[currY][currX];
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (rnd == 4 && currX < size-1 && elements[currY][currX+1].getStatus()==1){
            currX++;
            currElement = elements[currY][currX];
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (currElement.getCurrX() == size -1 && currElement.getCurrY() == size-1){
            currElement.setBackground(Color.YELLOW);
            return 3;
        }
        if (currElement.numOptionsSolve(elements, currX, currY) == 0){
            return 2;
        }

        return 1;

    }

    public int backSolver(MazeElement[][] elements){
        if(currElement.numOptionsSolve(elements, currX, currY) == 0){
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


    public void Reset(MazeElement[][] elements){
        for (int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
                elements[y][x].reset();
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
