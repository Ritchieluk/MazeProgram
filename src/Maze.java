import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private JSlider speedSlider, colSlider, rowSlider;
    private JLabel timerLabel, statusLabel, percentLabel, speedSliderLabel, colSliderLabel, rowSliderLabel;
    private MazeElement elements[][];
    private MazeElement currElement;
    private int currCol = 0, currRow = 0, finished = 0, rows, cols, time = 0, tilesVisited = 0, totalTiles, speed=50;
    private boolean start = false, showGenerationSwitch, showSolveSwitch, generated = false, solved = false, running = false;
    private Stack<MazeElement> path = new Stack<>();

    public Maze(int rowNum, int colNum) {

        super("Maze");
        rows = rowNum;
        cols = colNum;
        quit = new JButton("Quit");
        stop = new JButton("Stop");
        reset = new JButton("Reset");
        generate = new JButton("Generate");
        solve = new JButton("Solve");
        showGeneration = new JCheckBox("Show Generation");
        showGeneration.setHorizontalAlignment(SwingUtilities.CENTER);
        showSolver = new JCheckBox("Show Solver");
        showSolver.setHorizontalAlignment(SwingUtilities.CENTER);
        timerLabel = new JLabel("Time: 0");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel = new JLabel("Waiting");
        statusLabel.setHorizontalAlignment(SwingUtilities.CENTER);
        percentLabel = new JLabel("Visited: 0%");
        percentLabel.setHorizontalAlignment(SwingUtilities.CENTER);
        speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, speed );
        rowSlider = new JSlider(JSlider.HORIZONTAL, 10, 60, rows);
        colSlider = new JSlider(JSlider.HORIZONTAL, 10, 60, cols);
        speedSliderLabel = new JLabel("Delay (ms): " + speed);
        speedSliderLabel.setHorizontalAlignment(SwingUtilities.RIGHT);
        colSliderLabel = new JLabel("Cols: " + cols);
        colSliderLabel.setHorizontalAlignment(SwingUtilities.RIGHT);
        rowSliderLabel = new JLabel("Rows: " + rows);
        rowSliderLabel.setHorizontalAlignment(SwingUtilities.RIGHT);
        controls = new JPanel();
        panel = new JPanel();
        panel = buildMaze(panel);


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

        MazeChangeListener changer = new MazeChangeListener();
        speedSlider.addChangeListener(changer);
        colSlider.addChangeListener(changer);
        rowSlider.addChangeListener(changer);

        controls.setLayout(new GridLayout(8,2,0,0));
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
        controls.add(speedSliderLabel);
        controls.add(speedSlider);
        controls.add(rowSliderLabel);
        controls.add(rowSlider);
        controls.add(colSliderLabel);
        controls.add(colSlider);




        add(controls, BorderLayout.EAST);
        add(panel, BorderLayout.WEST);
        pack();
        setVisible(true);

    }
    public JPanel buildMaze(JPanel inputPanel){
        totalTiles = cols*rows;
        elements = new MazeElement[rows][cols];
        panel.setLayout(new GridLayout(rows,cols,0,0));
        for (int y = 0;y<rows;y++){
            for (int x = 0; x < cols; x++){
                elements[y][x] = new MazeElement();
                elements[y][x].setXY(x, y);
                inputPanel.add(elements[y][x]);
            }
        }
        return inputPanel;
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

    public void ResetMaze(){
        panel.removeAll();
        panel = buildMaze(panel);
        pack();

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
        solved = false;
        running = false;
        time = 0;
        tilesVisited = 0;
        stop.setText("Stop");
        timerLabel.setText("Time: " + time);
        percentLabel.setText("Visited: 0%");
        try {
            if (gameTimer.isRunning()) {
                gameTimer.stop();
            }
        }
        catch(NullPointerException f){
            System.out.println("Pointer was Null");
        }
        speed = speedSlider.getValue();
    }

    public static void main(String argv[])
    {
        new Maze(22, 22);
    }
    public class MazeActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==stop){
                try {
                    if (gameTimer.isRunning()) {
                        gameTimer.stop();
                        stop.setText("Resume");
                    }
                    else if(running){
                        stop.setText("Stop");
                        gameTimer.start();
                    }
                }
                catch(NullPointerException f){
                    System.out.println("Pointer was Null");
                }

            }
            else if(e.getSource()==quit){
                dispose();
            }
            else if(e.getSource()==solve){
                if(!running) {
                    System.out.println("Solving");
                    if (!generated) {
                        while (finished == 0) {
                            finished = Generate(elements);
                        }
                        generated = true;
                    }
                    if (generated && !solved) {
                        if (showSolveSwitch) {
                            gameTimer = new Timer(speed, f -> {
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
                                    statusLabel.setText("Done");
                                    solved = true;
                                }
                            });
                            gameTimer.start();
                        } else {
                            while (finished != 3) {
                                if (finished == 1) {
                                    finished = Solver(elements);
                                    int percent = (100 * tilesVisited) / totalTiles;
                                    percentLabel.setText("Visited: " + percent + "%");
                                } else if (finished == 2)
                                    finished = backSolver(elements);
                            }
                            solved = true;
                            statusLabel.setText("Done");

                        }
                    } else if (generated && solved) {
                        Reset(elements);
                        while (finished == 0) {
                            finished = Generate(elements);
                        }
                        generated = true;
                        if (showSolveSwitch) {
                            gameTimer = new Timer( speed, f -> {
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
                                } else if (finished == 2)
                                    finished = backSolver(elements);
                            }
                            solved = true;

                        }
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
                    running = true;
                    gameTimer = new Timer(speed, f -> {
                        time++;
                        timerLabel.setText("Time: " + time);
                        if (finished == 0) {
                            statusLabel.setText("Generating");
                            finished = Generate(elements);
                        }
                        if(finished == 1){
                            generated = true;
                            running = false;
                            statusLabel.setText("Generated");
                            gameTimer.stop();
                        }
                    });
                    gameTimer.start();

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
    public class MazeChangeListener implements ChangeListener{
        public void stateChanged(ChangeEvent e){
            if(e.getSource() == colSlider){
                JSlider source = (JSlider) e.getSource();
                if(!source.getValueIsAdjusting()) {
                    cols = source.getValue();
                    colSliderLabel.setText("Columns: " + cols);
                    ResetMaze();
                }
            }
            else if(e.getSource() == rowSlider){
                JSlider source = (JSlider) e.getSource();
                if(!source.getValueIsAdjusting()) {
                    rows = source.getValue();
                    rowSliderLabel.setText("Rows: " + cols);
                    ResetMaze();
                }
            }
            else if(e.getSource() == speedSlider){
                JSlider source = (JSlider) e.getSource();
                speed = source.getValue();
                speedSliderLabel.setText("Delay (ms): "+ speed);
                gameTimer.setDelay(speed);
            }
        }
    }
}
