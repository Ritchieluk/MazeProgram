import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JMenuBar;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MazeRunner extends JFrame {

    private JFrame mazeFrame;
    private MazeElementDriver driver;
    private JButton generate, solve, stop;
    private JSlider speedSlider, widthSlider, heightSlider;
    private JPanel leftSide, optionsPanel, statusPanel, generationPanel, solvePanel, sliderPanel;
    private JLabel timerLabel, statusLabel, percentLabel, speedSliderLabel, widthSliderLabel, heightSliderLabel;
    private JCheckBox showGeneration, showSolver;
    private int time, speed = 50, width = 10, height = 10;
    private String status;
    private double percent;
    private Boolean startTime = false, generateSwitch = false, solveSwitch = false, showGenerationSwitch = false, showSolveSwitch = false;

    private Timer timer;

    public MazeRunner() {
        super("Maze Generator");

        generate = new JButton("Generate");
        generate.setPreferredSize(new Dimension(75, 25));
        solve = new JButton("Solve");
        solve.setPreferredSize(new Dimension(75, 25));
        stop = new JButton("Stop");
        speedSliderLabel = new JLabel("Speed: " + speed);
        widthSliderLabel = new JLabel("Rows: " + width);
        heightSliderLabel = new JLabel("Columns: " + height);
        statusLabel = new JLabel("Press Generate to Start");
        percentLabel = new JLabel("Visited: %" + percent);
        timerLabel = new JLabel("Time: 0");
        showGeneration = new JCheckBox("Show Generation");
        showSolver = new JCheckBox("Show Solver");
        speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, speed );
        heightSlider = new JSlider(JSlider.HORIZONTAL, 10, 60, height);
        widthSlider = new JSlider(JSlider.HORIZONTAL, 10, 60, width);

        sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(6,1,0,30));
        sliderPanel.add(speedSliderLabel);
        sliderPanel.add(speedSlider);
        sliderPanel.add(widthSliderLabel);
        sliderPanel.add(widthSlider);
        sliderPanel.add(heightSliderLabel);
        sliderPanel.add(heightSlider);
        statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(2, 2, 40, 5));
        statusPanel.add(statusLabel);
        statusPanel.add(timerLabel);
        statusPanel.add(percentLabel);
        generationPanel = new JPanel();
        generationPanel.setLayout(new GridLayout(1,2,4,4));
        generationPanel.setMaximumSize(new Dimension(200, 25));
        generationPanel.add(generate);
        generationPanel.add(showGeneration);
        generationPanel.setPreferredSize(new Dimension(400,50));
        solvePanel = new JPanel();
        solvePanel.setLayout(new GridLayout(1,2,4,4));
        //solvePanel.setMaximumSize(new Dimension(200, 25));
        solvePanel.add(solve);
        solvePanel.add(showSolver);
        //solvePanel.setPreferredSize(new Dimension(400,50));

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(5,1,0,0));
        //optionsPanel.setMaximumSize(new Dimension(200, 400));
        optionsPanel.add(generationPanel);
        optionsPanel.add(solvePanel);
        optionsPanel.add(sliderPanel);
        optionsPanel.add(statusPanel);
        optionsPanel.add(stop);

        timer = new Timer(speed, e -> {
            if (startTime) {
                time++;
                timerLabel.setText("Time: " + time);
                if (driver.getFinished() == 0){
                    driver.generate();
                }
                else if (driver.getFinished() == 1) {
                    driver.solver();
                }
                else if (driver.getFinished() == 2) {
                    driver.backSolver();
                }
                else if (driver.getFinished() == 3) {
                    startTime = false;
                }
            }
        });

        timer.start();

        MazeRunnerActionListener manager = new MazeRunnerActionListener();
        solve.addActionListener(manager);
        generate.addActionListener(manager);
        stop.addActionListener(manager);
        showSolver.addActionListener(manager);
        showGeneration.addActionListener(manager);

        MazeRunnerChangeListener changer = new MazeRunnerChangeListener();
        speedSlider.addChangeListener(changer);
        heightSlider.addChangeListener(changer);
        widthSlider.addChangeListener(changer);

        driver = new MazeElementDriver(width,height);
        //driver.setPreferredSize(getPreferredSize());

        Container container = getContentPane();


        container.setLayout(new GridLayout(1,2,0,0));
        add(driver, BorderLayout.CENTER);
        container.add(optionsPanel, BorderLayout.EAST);
        pack();
        //setSize(width*20+400, height*20 + 200);
        //setMinimumSize(new Dimension(width*20 + 500, height*20 + 400));
        //setMaximumSize(new Dimension(900,900));

        setVisible(true);


    }

    private void restart() {

    }

    public static void main(String args[]) {
        MazeRunner maze = new MazeRunner();
        maze.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }
    public class MazeRunnerActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==stop){
                startTime = false;
            }
            else if(e.getSource()==solve){
                startTime = true;

            }
            else if(e.getSource()==generate){
                startTime = true;
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
            else
                System.out.println("Unknown Action Detected");

        }
    }
    public class MazeRunnerChangeListener implements ChangeListener{
        public void stateChanged(ChangeEvent e){
            if(e.getSource()==widthSlider){
                JSlider source = (JSlider) e.getSource();
                if(!source.getValueIsAdjusting()){
                    width = source.getValue();
                    driver.setWidth(width);
                    widthSliderLabel.setText("Rows: " + width);
                    driver.reset();
                }

            }
            else if(e.getSource()==heightSlider){
                JSlider source = (JSlider) e.getSource();
                if(!source.getValueIsAdjusting()){
                    height = source.getValue();
                    driver.setHeight(height);
                    heightSliderLabel.setText("Columns: "+ height);
                    driver.Reset();
                }

            }
            else if(e.getSource()==speedSlider){
                JSlider source = (JSlider) e.getSource();
                speed = source.getValue();
                speedSliderLabel.setText("Speed: "+ speed);
            }
            else
                System.out.println("Unknown Change Detected");


        }
    }
}
