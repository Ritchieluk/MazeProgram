import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Maze extends JFrame{

    private Timer gameTimer;
    private JPanel panel, controls;
    private JButton quit, generate, reset, stop, solve;
    private JCheckBox showGeneration, showSolver;
    private JSlider speedSlider, widthSlider, heightSlider;
    private JLabel timerLabel, statusLabel, percentLabel;
    private MazeElement elements[][];
    private MazeElement currElement;
    private int currCol = 0, currRow = 0, finished = 0, rows, cols, time = 0, tilesVisited = 0, totalTiles;
    private boolean start = false, showGenerationSwitch, showSolveSwitch, generated = false, solved = false;
    private Stack<MazeElement> path = new Stack<>();

    public Maze(int rowNum, int colNum) {

        super("Maze");
        rows = rowNum;
        cols = colNum;
        totalTiles = cols*rows;
        elements = new MazeElement[rows][cols];
        quit = new JButton("Quit");
        stop = new JButton("Stop");
        reset = new JButton("Reset");
        generate = new JButton("Generate");
        solve = new JButton("Solve");
        showGeneration = new JCheckBox("Show Generation");
        showSolver = new JCheckBox("Show Solver");
        timerLabel = new JLabel("Time: 0");
        statusLabel = new JLabel("Waiting");
        percentLabel = new JLabel("Visited: 0%");
        controls = new JPanel();
        panel = new JPanel();
        panel.setLayout(new GridLayout(rows,cols,0,0));
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        MazeActionListener runner = new MazeActionListener();

        stop.addActionListener(runner);
        quit.addActionListener(runner);
        reset.addActionListener(runner);
        generate.addActionListener(runner);
        solve.addActionListener(runner);
        showGeneration.addActionListener(runner);
        showSolver.addActionListener(runner);

        controls.setLayout(new GridLayout(5,2,0,0));
        controls.add(generate);
        controls.add(showGeneration);
        controls.add(solve);
        controls.add(showSolver);
        controls.add(stop);
        controls.add(statusLabel);
        controls.add(reset);
        controls.add(timerLabel);
        controls.add(quit);
        controls.add(percentLabel);
        for (int y = 0;y<rows;y++){
            for (int x = 0; x < cols; x++){
                elements[y][x] = new MazeElement();
                elements[y][x].setXY(x, y);
                panel.add(elements[y][x]);
            }
        }

        add(controls, BorderLayout.EAST);
        add(panel, BorderLayout.WEST);
        pack();
        setVisible(true);

    }

    public int Generate(MazeElement elements[][]) {
        int rnd = 0;
        if (!start){
            currElement = elements[currRow][currCol];
            start = true;
            currElement.incStatus();
            currElement.drawMyself();
            path.push(currElement);

        }
        int sides[] = new int[currElement.numOptionsGen(elements, currRow, currCol, rows, cols)];
        sides = currElement.getOptionsGen(sides, elements, currRow, currCol, rows, cols);

        if (sides.length != 0) {
            rnd = new Random().nextInt(currElement.numOptionsGen(elements, currRow, currCol, rows, cols));
            rnd = sides[rnd];
        }

        if (rnd == 1 && currRow > 0 && elements[currRow -1][currCol].getStatus()==0){
            currElement.setNorth();
            currElement.drawMyself();
            currRow--;
            currElement = elements[currRow][currCol];
            currElement.setSouth();
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (rnd == 2 && currCol > 0 && elements[currRow][currCol -1].getStatus()==0){
            currElement.setWest();
            currElement.drawMyself();
            currCol--;
            currElement = elements[currRow][currCol];
            currElement.setEast();
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();

        }
        if (rnd == 3 && currRow < rows-1 && elements[currRow +1][currCol].getStatus()==0){
            currElement.setSouth();
            currElement.drawMyself();
            currRow++;
            currElement = elements[currRow][currCol];
            currElement.setNorth();
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (rnd == 4 && currCol < cols-1 && elements[currRow][currCol +1].getStatus()==0){
            currElement.setEast();
            currElement.drawMyself();
            currCol++;
            currElement = elements[currRow][currCol];
            currElement.setWest();
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (currElement.getCurrX() == cols-1 && currElement.getCurrY() == rows-1){
            currElement.setBackground(Color.YELLOW);
            path.pop();
            currElement = path.peek();
            backGenerate(elements);
        }
        if (currElement.numOptionsGen(elements, currRow, currCol, rows, cols) == 0){
            backGenerate(elements);
        }
        if (path.empty()){
            start = false;
            return 1;
        }
        else{return 0;}

    }

    public void backGenerate(MazeElement[][] elements){

        if(currElement.numOptionsGen(elements, currRow, currCol, rows, cols) == 0){
            if (path.empty()){return;}
            path.pop();
            if (path.empty()){return;}
            currElement = path.peek();
            currCol = currElement.getCurrX();
            currRow = currElement.getCurrY();
            backGenerate(elements);
        }
        else{
            currCol = currElement.getCurrX();
            currRow = currElement.getCurrY();
        }

    }

    public int Solver(MazeElement[][] elements){

        tilesVisited++;
        System.out.println(tilesVisited);
        int rnd = 0;
        if (!start){
            currElement = elements[currRow][currCol];
            start = true;
            currElement.incStatus();
            currElement.drawMyself();
            path.push(currElement);
        }
        int sides[] = new int[currElement.numOptionsSolve(elements, currRow, currCol, rows, cols)];

        sides = currElement.getOptionsSolve(sides, elements, currRow, currCol, rows, cols);

        if (sides.length != 0) {
            rnd = new Random().nextInt(currElement.numOptionsSolve(elements, currRow, currCol, rows, cols));
            rnd = sides[rnd];
        }


        if (rnd == 1 && currRow > 0 && elements[currRow -1][currCol].getStatus()==1){
            currRow--;
            currElement = elements[currRow][currCol];
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (rnd == 2 && currCol > 0 && elements[currRow][currCol -1].getStatus()==1){
            currCol--;
            currElement = elements[currRow][currCol];
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();

        }
        if (rnd == 3 && currRow < rows-1 && elements[currRow +1][currCol].getStatus()==1){
            currRow++;
            currElement = elements[currRow][currCol];
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (rnd == 4 && currCol < cols-1 && elements[currRow][currCol +1].getStatus()==1){
            currCol++;
            currElement = elements[currRow][currCol];
            currElement.incStatus();
            path.push(currElement);
            currElement.drawMyself();
        }
        if (currElement.getCurrX() == cols -1 && currElement.getCurrY() == rows-1){
            currElement.setBackground(Color.GREEN);
            return 3;
        }
        if (currElement.numOptionsSolve(elements, currRow, currCol, rows, cols) == 0){
            return 2;
        }

        return 1;

    }

    public int backSolver(MazeElement[][] elements){
        if(currElement.numOptionsSolve(elements, currRow, currCol, rows, cols) == 0){
            currElement.incStatus();
            currElement.drawMyself();
            path.pop();
            currElement = path.peek();
            currCol = currElement.getCurrX();
            currRow = currElement.getCurrY();
        }
        else{
            currCol = currElement.getCurrX();
            currRow = currElement.getCurrY();
            return 1;
        }
        return 2;
    }


    public void Reset(MazeElement[][] elements){
        for (int x = 0; x < cols; x++){
            for (int y = 0; y < rows; y++){
                elements[y][x].Reset();
            }
        }
        currCol = 0;
        currRow = 0;
        finished = 0;
        start = false;
        generated = false;
        time = 0;
        tilesVisited = 0;
        percentLabel.setText("Visited: 0%");
    }

    public static void main(String argv[])
    {
        new Maze(22, 22);
    }
    public class MazeActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==stop){
                gameTimer.stop();
            }
            else if(e.getSource()==quit){
                dispose();
            }
            else if(e.getSource()==solve){
                if(!generated) {
                    while (finished == 0) {
                        finished = Generate(elements);
                    }
                    generated = true;
                }
                if(generated && !solved) {
                    if (showSolveSwitch) {
                        gameTimer = new Timer(10, f -> {
                            if (finished == 1) {
                                time++;
                                statusLabel.setText("Solving");
                                timerLabel.setText("Time: " + time);
                                finished = Solver(elements);
                                int percent = (100 * tilesVisited) / totalTiles;
                                System.out.println(percent);
                                percentLabel.setText("Visited: " + percent + "%");
                            } else if (finished == 2) {
                                time++;
                                timerLabel.setText("Time: " + time);
                                finished = backSolver(elements);
                            } else if (finished == 3) {
                                gameTimer.stop();
                            }
                        });
                        gameTimer.start();
                        statusLabel.setText("Done");
                        solved = true;

                    } else {
                        while (finished != 3) {
                            if (finished == 1) {
                                finished = Solver(elements);
                                int percent = (100 * tilesVisited) / totalTiles;
                                percentLabel.setText("Visited: " + percent + "%");
                            }
                            else if (finished == 2)
                                finished = backSolver(elements);
                        }
                        solved = true;

                    }
                }
                else if(generated && solved){
                    Reset(elements);
                    while (finished == 0) {
                        finished = Generate(elements);
                    }
                    generated = true;
                    if (showSolveSwitch) {
                        gameTimer = new Timer(10, f -> {
                            if (finished == 1) {
                                time++;
                                statusLabel.setText("Solving");
                                timerLabel.setText("Time: " + time);
                                finished = Solver(elements);
                                int percent = (100 * tilesVisited) / totalTiles;
                                percentLabel.setText("Visited: " + percent + "%");
                            } else if (finished == 2) {
                                time++;
                                timerLabel.setText("Time: " + time);
                                finished = backSolver(elements);
                            } else if (finished == 3) {
                                gameTimer.stop();
                            }
                        });
                        gameTimer.start();
                        statusLabel.setText("Done");
                        solved = true;

                    } else {
                        while (finished != 3) {
                            if (finished == 1) {
                                int percent = (100 * tilesVisited) / totalTiles;
                                percentLabel.setText("Visited: " + percent + "%");
                                finished = Solver(elements);
                            }
                            else if (finished == 2)
                                finished = backSolver(elements);
                        }
                        solved = true;

                    }
                }

            }
            else if(e.getSource()==reset){
                Reset(elements);
            }
            else if(e.getSource()==generate){
                finished = 0;
                Reset(elements);
                if(showGenerationSwitch) {
                    gameTimer = new Timer(10, f -> {
                        time++;
                        timerLabel.setText("Time: " + time);
                        if (finished == 0) {
                            statusLabel.setText("Generating");
                            finished = Generate(elements);
                        }
                        if(finished == 1){
                            statusLabel.setText("Generated");
                            gameTimer.stop();
                        }
                    });
                    gameTimer.start();
                    generated = true;
                }
                else{
                    while(finished == 0){
                        finished = Generate(elements);
                    }
                    generated = true;
                    statusLabel.setText("Generated");
                }
            }
            else if(e.getSource()==showGeneration){
                if(showGeneration.isSelected())
                    showGenerationSwitch = true;
                else
                    showGenerationSwitch = false;
            }
            else if(e.getSource()==showSolver){
                if(showSolver.isSelected())
                    showSolveSwitch = true;
                else
                    showSolveSwitch = false;

            }
        }
    }
}
