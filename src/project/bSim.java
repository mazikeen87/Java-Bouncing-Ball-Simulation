/**
 * @author Christopher Boustros
 * ECSE 202 Introduction to Software Engineering
 */
package project;

import assets.bTree;
import assets.bNode;

import acm.graphics.*; // To access the GLabel, GObject, GOval, and GRect class
import acm.program.GraphicsProgram;
import acm.gui.TableLayout;

import java.awt.*; // To access the Color class
import java.awt.event.*; // To access ItemListener, ItemEvent, ActionListener, ActionEvent, and MouseMotionListener
import acm.util.RandomGenerator; // To access the RandomGenerator class
import javax.swing.*; // To access the JComboBox, JLabel, JPanel, and JSlider class
import javax.swing.event.*; // To access ChangeListener and ChangeEvent

// This class runs the simulation. It contains the main method run().
// It creates the gBall objects with random parameters, displays the GOval object that was created in each gBall object, and calls the start() method of each gBall object to start the simulations.
@SuppressWarnings("serial")
public class bSim extends GraphicsProgram implements ChangeListener, ItemListener, ActionListener, MouseListener {
	// This class is from Assignment 3 with modifications indicated by comments
	
	public static final int WIDTH = 1200; 
	public static final int HEIGHT = 800;
	public static final int OFFSET = 200;
	
	private static final int NUM_BALLS_MIN = 1; // The lower bound of the "NUM BALLS" slider
	private static final int NUM_BALLS_MAX = 30; // The upper bound of the "NUM BALLS" slider
	private static final int INITIAL_NUM_BALLS = 15; // The initial value of the "NUM BALLS" slider
	
	private static final double MIN_SIZE_MIN = 1.0; // The lower bound of the "MIN SIZE" slider
	private static final double MIN_SIZE_MAX = 25.0; // The upper bound of the "MIN SIZE" slider
	private static final double MAX_SIZE_MIN = 1.0; // The lower bound of the "MAX SIZE" slider
	private static final double MAX_SIZE_MAX = 25.0; // The upper bound of the "MAX SIZE" slider
	private static final double X_MIN_MIN = 1.0; // The lower bound of the "X MIN" slider
	private static final double X_MIN_MAX = 200.0; // The upper bound of the "X MIN" slider
	private static final double X_MAX_MIN = 1.0; // The lower bound of the "X MAX" slider
	private static final double X_MAX_MAX = 200.0; // The upper bound of the "X MAX" slide
	private static final double Y_MIN_MIN = 1.0; // The lower bound of the "Y MIN" slider
	private static final double Y_MIN_MAX = 100.0; // The upper bound of the "Y MIN" slider
	private static final double Y_MAX_MIN = 1.0; // The lower bound of the "Y MAX" slider
	private static final double Y_MAX_MAX = 100.0; // The upper bound of the "Y MAX" slider
	private static final double E_MIN_MIN = 0.0; // The lower bound of the "LOSS MIN" slider
	private static final double E_MIN_MAX = 1.0; // The upper bound of the "LOSS MIN" slider
	private static final double E_MAX_MIN = 0.0; // The lower bound of the "LOSS MAX" slider
	private static final double E_MAX_MAX = 1.0; // The upper bound of the "LOSS MAX" slider
	private static final double V_MIN_MIN = 0.0; // The lower bound of the "X VEL MIN" slider
	private static final double V_MIN_MAX = 10.0; // The upper bound of the "X VEL MIN" slider
	private static final double V_MAX_MIN = 0.0; // The lower bound of the "X VEL MAX" slider
	private static final double V_MAX_MAX = 10.0; // The upper bound of the "X VEL MAX" slider
	
	// An array containing all of the above double parameters
	private static final double[] PARAMETERS = new double[] {MIN_SIZE_MIN,MIN_SIZE_MAX,MAX_SIZE_MIN,MAX_SIZE_MAX,X_MIN_MIN,X_MIN_MAX,X_MAX_MIN,X_MAX_MAX,Y_MIN_MIN,Y_MIN_MAX,Y_MAX_MIN,Y_MAX_MAX,E_MIN_MIN,E_MIN_MAX,E_MAX_MIN,E_MAX_MAX,V_MIN_MIN,V_MIN_MAX,V_MAX_MIN,V_MAX_MAX};
	
	// An array containing the names of each slider after the "NUM BALLS" slider in the General Simulation Parameters section
	private static final String[] SLIDER_NAMES = new String[] {"MIN SIZE:","MAX SIZE:","X MIN:","X MAX:","Y MIN:", "Y MAX:","LOSS MIN:","LOSS MAX:","X VEL MIN:","X VEL MAX:"};
	
	// An array containing the initial slider values for each slider in the same order as the names in the SLIDER_NAMES array
	private static final double[] INITIAL_SLIDER_VALUES = new double[] {1.0,8.0,10.0,50.0,50.0,100.0,0.4,1.0,1.0,5.0};
	
	// An array of colors
	private static final Color[] COLORS = new Color[] {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN,Color.BLUE,Color.MAGENTA, Color.WHITE, Color.BLACK, Color.GRAY, Color.PINK}; //Array of colors
	
	// An array of color names (in the same order as the array of colors)
	private static final String[] COLOR_NAMES = new String[] {"RED","ORANGE", "YELLOW", "GREEN", "CYAN", "BLUE", "MAGENTA", "WHITE", "BLACK", "GRAY", "PINK"};
	
	SliderBox[] sliderBoxes; // An array that will contain each SliderBox after the "NUM BALLS" SliderBox in the General Simulation Parameters section
	SliderBox numBallsSliderBox; // The SliderBox object for the "NUM BALLS" slider
	SliderBox singleBallColor, singleBallSize, singleBallLoss, singleBallVel; // Single ball sliders
	JComboBox<String> comboBox; // The JComboBox object at the top of the window
	JButton button; //The JButton object at the top of the window that sorts the balls
	GLabel label = new GLabel("", (WIDTH-OFFSET)/3, HEIGHT/3); // The label that will be shown once the simulation stops (to prompt the user to click the sort button)
	
	private bTree myTree = new bTree(); // The bTree object that will contain each gBall object in a simulation (now declared outside of the run() method)
	
	private boolean simulationBeginning = false; // Will be used in the run() method to determine whether to call runSimulation()
	private boolean programRunning = true; // Will be used in the run() method to determine whether to continue looping (gets set to false when the user selects "Quit" from the combo box)
	
	public void init() { // Creates the graphical user interface
		// Creates the ground
		this.resize(WIDTH,HEIGHT);
		GRect ground = new GRect(0,HEIGHT-OFFSET,WIDTH,10);
		ground.setFilled(true);
		add(ground);
		
		// Creates the East panel that contains all of the slider
		JPanel panel = new JPanel();
		panel.setLayout(new TableLayout(50,1)); // The panel consists of many rows and 1 column
		add(panel, EAST); // Adds the panel to the East

		panel.add(new JLabel("General Simulation Parameters")); // Adds the title
		panel.add(new JLabel(" ")); // Adds a blank space below the title
		
		// Creates the "NUM BALLS" SliderBox object and adds its panel to the east panel
		numBallsSliderBox = new SliderBox("NUM BALLS:", NUM_BALLS_MIN, INITIAL_NUM_BALLS, NUM_BALLS_MAX);
		panel.add(numBallsSliderBox.getPanel());
		numBallsSliderBox.getSlider().addChangeListener((ChangeListener)this); // Adds a change listener for the slider named NUM BALLS
		
		sliderBoxes = new SliderBox[10]; // Instantiates the sliderBoxes array
		int j = 0;
		for(int i = 0; i < 10; i++) { // Uses the SLIDER_NAMES, PARAMETERS, and INITIAL_SLIDER_VALUES arrays to create and add the 10 sliders
			panel.add(new JLabel(" ")); // Blank space
			String name = SLIDER_NAMES[i];
			double min = PARAMETERS[j];
			double initial = INITIAL_SLIDER_VALUES[i];
			double max = PARAMETERS[j+1];
			sliderBoxes[i] = new SliderBox(name,min, initial, max);
			panel.add(sliderBoxes[i].getPanel());
			sliderBoxes[i].getSlider().addChangeListener((ChangeListener)this); // Adds a change listener for the slider of sliderBoxes[i]
			// Each change listener continually calls the stateChanged(ChangeEvent e) method to catch when the user moves its slider
			j+=2;
		}
		
		// Creates the JComboBox object and adds it North
		comboBox = new JComboBox<String>();
		comboBox.addItem(" ");
		comboBox.addItem("Run");
		comboBox.addItem("Stop");
		comboBox.addItem("Clear");
		comboBox.addItem("Quit");
		add(comboBox, NORTH);
		comboBox.addItemListener((ItemListener)this); // Adds an item listener for comboBox
		// This item listener continually calls the itemStateChanged(ItemEvent e) method to catch when the user selects a new option in comboBox
		
		// Creates the JButton object (to sort the balls)
		button = new JButton("Sort");
		add(button, NORTH);
		button.addActionListener((ActionListener)this); //Adds an action listener which continually calls the actionPerformed(ActionEvent e) method to catch when the user click the button
		
		//Single ball sliders
		panel.add(new JLabel(" "));
		panel.add(new JLabel("Single Ball Instance Parameters"));
		panel.add(new JLabel(" "));
		
		singleBallColor = new SliderBox("Color:", 0, 0, COLORS.length - 1); // The value of this slider is the index of the COLORS array that will be used
		singleBallColor.getReadout().setText(COLOR_NAMES[0]);
		singleBallColor.getReadout().setForeground(COLORS[0]);
		singleBallColor.getSlider().addChangeListener((ChangeListener)this);
		panel.add(singleBallColor.getPanel());
		panel.add(new JLabel(" "));
		
		singleBallSize = new SliderBox("Ball Size:", 1.0, 4.0, 8.0);
		singleBallSize.getSlider().addChangeListener((ChangeListener)this);
		panel.add(singleBallSize.getPanel());
		panel.add(new JLabel(" "));
		
		singleBallLoss = new SliderBox("E Loss:", 0.2, 0.4, 1.0);
		singleBallLoss.getSlider().addChangeListener((ChangeListener)this);
		panel.add(singleBallLoss.getPanel());
		panel.add(new JLabel(" "));
		
		singleBallVel = new SliderBox("X Vel:", 1.0, 1.0, 5.0);
		singleBallVel.getSlider().addChangeListener((ChangeListener)this);
		panel.add(singleBallVel.getPanel());
		
		
	}
	
	public void runSimulation() { // Simulation code moved from run() method to a separate runSimulation() method so that it can be called each time the user selects "Run" from the combo box
		RandomGenerator rgen = RandomGenerator.getInstance();
		gBall[] balls = new gBall[numBallsSliderBox.getIntegerSliderValue()];
		for (int i = 0; i < numBallsSliderBox.getIntegerSliderValue(); i++) { // Uses the slider values to generate random values
			Color color = rgen.nextColor();
			double size = rgen.nextDouble(sliderBoxes[0].getDoubleSliderValue(),sliderBoxes[1].getDoubleSliderValue());
			double x = rgen.nextDouble(sliderBoxes[2].getDoubleSliderValue(),sliderBoxes[3].getDoubleSliderValue());
			double y = rgen.nextDouble(sliderBoxes[4].getDoubleSliderValue(),sliderBoxes[5].getDoubleSliderValue());
			double l = rgen.nextDouble(sliderBoxes[6].getDoubleSliderValue(),sliderBoxes[7].getDoubleSliderValue());
			double v = rgen.nextDouble(sliderBoxes[8].getDoubleSliderValue(),sliderBoxes[9].getDoubleSliderValue());
			balls[i] = new gBall(x,y,size/2.0,color,l,v);
			add(balls[i].getBall());
			myTree.addNode(balls[i]);
			balls[i].getBall().addMouseListener((MouseListener) this); // Adds a mouse listener to the GOval object of each gBall added to the bTree
		}
		
		for (int i = 0; i < numBallsSliderBox.getIntegerSliderValue(); i++) {
			balls[i].start();
		}
		
		// The loop checks if the simulation is still running every interval amount of time
		while (!myTree.isEmpty() && myTree.isRunning()) {
			pause(1000*balls[0].getIntervalTime());
		}
		
		if (!myTree.isEmpty()) {
			label.setLabel("Click the sort button to sort"); // No longer using the waitForClick() method. So, it prompts the user to click the sort button.
			label.setColor(Color.RED);
			add(label);
		}
		
		/*
		 * If myTree is empty, then the user either selected "Run" or "Clear" from the combo box
		 * So, the user will not be prompted to click the sort button
		 */
	}
	
	public void stopSimulation() { // Stops the simulation
		if (!myTree.isEmpty()) { // Cannot run stopSimulation() from an empty bTree
			myTree.stopSimulation(); 
		}
	}
	
	public void clear() { //Stops the simulation and removes all GOval objects
		stopSimulation();
		if (!myTree.isEmpty()) myTree.clear(); // Cannot call clear() from an empty bTree
		this.removeAll();
		// The ground is created again after all GObjects are removed
		GRect ground = new GRect(0,HEIGHT-OFFSET,WIDTH,10);
		ground.setFilled(true);
		add(ground);
	}
	
	
	public void run() { // This is the main method
		while (programRunning) { // While loop iterates until the user quits the program
			pause(200); // Every 200 milliseconds, the program checks if simulationBeginning is true (when the user selects "Run" from the combo box) and runs the simulation if it is true
			if (simulationBeginning) {
				simulationBeginning = false; // So that the simulation doesn't run again until the user selects "Run" from the combo box
				runSimulation();
			}
		}
		
	}
	
	public void actionPerformed(ActionEvent e) { 
		// This method catches and handles an action event (which occurs when the user clicks the "Sort" button) (The JButton object has an action listener)
		JButton source = (JButton)e.getSource(); // Gets the source of the event and stores the reference to it
		if (!myTree.isEmpty()) { // To sort the balls, myTree must not be empty and myTree must not be running
			if (source == button) { // The source will always be button because it is the only JButton object
				if (myTree.isRunning()) {
					System.out.println("Wait for the simulation to stop or select stop from the combo box before sorting.");
				}
				else {
					myTree.moveSort(); // Sorts the balls
					label.setLabel("All sorted!");
				}
			}
		}
		else {
			System.out.println("There are no balls to sort.");
		}
		
	}
	
	public void itemStateChanged(ItemEvent e) { // Implementation of the abstract method inherited from the ItemListener interface
		// This method catches and handles an ItemEvent (which occurs when the user selects a different item from the combo box) (the ComboBox object has an item listener)
		@SuppressWarnings("rawtypes")
		JComboBox source = (JComboBox)e.getSource();
		
		if (source == comboBox) { // The source will always be comboBox because it is the only JComboBox object
			switch (comboBox.getSelectedIndex()) {
				case 1: // If "Run" is selected
					System.out.println("Starting Simulation"); // This will be printed twice because when an option is selected, a duplicate event occurs
					clear();
					simulationBeginning = true;
					break;
				case 2: // If "Stop" is selected
					stopSimulation();
					break;
				case 3: // If "Clear" is selected
					clear();
					break;
				case 4: // If "Quit" is selected
					programRunning = false; // Stops the while loop inside the run() method
					System.exit(0);
			}
		}
	}
	
	public void stateChanged(ChangeEvent e) { // Implementation of the abstract method inherited from the ChangeLister interface
		// This method catches and handles a ChangeEvent (which occurs when the user moves a slider) (each JSlider object has a change listener)
		JSlider source = (JSlider)e.getSource();
		
		if (source == numBallsSliderBox.getSlider()) {
			int value = numBallsSliderBox.getIntegerSliderValue();
			numBallsSliderBox.getReadout().setText(Integer.toString(value)); // Changes the "sReadout" label in the SliderBox object to display the current value of the slider
		}
		
		for (int i = 0; i < 10; i++) { // Checks each SliderBox object in the sliderBoxes array
			if (source == sliderBoxes[i].getSlider()) {
				double value = sliderBoxes[i].getDoubleSliderValue();
				sliderBoxes[i].getReadout().setText(Double.toString(value));
			}
		}
		
		if (source == singleBallColor.getSlider()) {
			int index = singleBallColor.getIntegerSliderValue();
			singleBallColor.getReadout().setText(COLOR_NAMES[index]);
			singleBallColor.getReadout().setForeground(COLORS[index]);
		}
		if (source == singleBallSize.getSlider()) {
			double value = singleBallSize.getDoubleSliderValue();
			singleBallSize.getReadout().setText(Double.toString(value));
		}
		if (source == singleBallLoss.getSlider()) {
			double value = singleBallLoss.getDoubleSliderValue();
			singleBallLoss.getReadout().setText(Double.toString(value));
		}
		if (source == singleBallVel.getSlider()) {
			double value = singleBallVel.getDoubleSliderValue();
			singleBallVel.getReadout().setText(Double.toString(value));
		}
	}
	
	// This method is called when the user releases the mouse from a GOval object (each GOval object has a mouse listener)
	public void mouseReleased(MouseEvent e) {
		GOval source = (GOval) e.getSource(); // Gets and stores the reference to the source (the GOval object that was released)
		remove(source); // Removes the source from the canvas
		
		bNode node = myTree.findNode(source); // Finds and stores the reference to the node corresponding to the source
		
		// Gets and stores the x and y position of the mouse when it was released (in simulation coordinates)
		double xPos = e.getX() / 10;
		double yPos = ((HEIGHT-OFFSET) - e.getY()) / 10;
		
		// Gets values from the slider
		double size = singleBallSize.getDoubleSliderValue(); 
		Color color = COLORS[singleBallColor.getIntegerSliderValue()];
		double loss = singleBallLoss.getDoubleSliderValue();
		double vel = singleBallVel.getDoubleSliderValue();
		
		// Uses the values to create a new gBall object and change the reference stored in the node to reference that new gBall object
		node.ball = new gBall(xPos, yPos, size, color, loss, vel);
		node.ball.getBall().addMouseListener((MouseListener) this); // Adds a mouse listener to the GOval object of the new gBall object
		
		add(node.ball.getBall()); // Adds to the canvas the GOval object of the gBall that was created
		
		myTree.reSort(); // Resorts the bTree
		
		node.ball.start(); // Starts the simulation for the new gBall object
		
		label.setLabel("Click the sort button once the ball has stopped bouncing"); // Changes the label
	}
}