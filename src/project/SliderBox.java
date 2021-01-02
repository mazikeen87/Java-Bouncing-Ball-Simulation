/**
 * @author Christopher Boustros
 * ECSE 202 Introduction to Software Engineering
 */
package project;

import acm.gui.TableLayout;

import java.awt.Color;
import javax.swing.*; //To access the JLable, JPanel, and JSlider classes

// This class creates a JPanel object containing a single JSlider object along with its corresponding labels
public class SliderBox {
	private JPanel myPanel; // The panel that will store the slider and labels
	private JSlider mySlider; // The slider
	private JLabel nameLabel, minLabel, maxLabel, sReadout; // The labels surrounding the slider. sReadout is the label that displays the current value of the slider.
	private Integer min, initial, max; // The minimum, initial, and maximum values of the slider
	private boolean integerSlider; // True if the SliderBox object was instantiated with integer arguments and false otherwise
	
	public SliderBox(String name, Integer min, Integer initial, Integer max) { // Constructor that takes Integer arguments
		integerSlider = true; // Because the SliderBox object was instantiated with integer arguments
		this.min = min;
		this.max = max; 
		this.initial = initial;
		
		// Creates the labels
		nameLabel = new JLabel(name);
		minLabel = new JLabel(min.toString());
		sReadout = new JLabel(initial.toString());
		sReadout.setForeground(Color.blue); // Sets the sReadout label to blue
		maxLabel = new JLabel(max.toString());
		
		mySlider = new JSlider(min,max,initial); // Creates the slider
		myPanel = new JPanel(); // Creates the panel
		myPanel.setLayout(new TableLayout(1,5)); // The panel is set to have 1 row and 5 columns. So, components are added horizontally
		
		// Adds the labels and slider to the panel and specifies their widths
		myPanel.add(nameLabel,"width=100");
		myPanel.add(minLabel,"width=25");
		myPanel.add(mySlider,"width=100");
		myPanel.add(maxLabel,"width=100");
		myPanel.add(sReadout,"width=75");
		// The total width of the panel will be 400 pixels (the sum of the width of each component added to it)
	}
	
	public SliderBox(String name, Double min, Double initial, Double max) { // Constructor that takes Double arguments
		integerSlider = false; // Because the SliderBox object was not instantiated with integer arguments.
		this.min = (int)(min*10);
		this.initial = (int)(initial*10); 
		this.max = (int)(max*10);
		/*
		 * A JSlider object can only store a range of integer values.
		 * So, arguments of type double are converted to slider units by being scaled up by a factor of 10 and being truncated to integers
		 * 1 slider unit = 0.1
		 * this.min, this.initial, this.max represent the min, initial, max values in slider units
		 */
		
		// Converts this.min, this.initial, and this.max from slider units to doubles with 1 decimal place
		Double min2 = (double)this.min/10;
		Double initial2 = (double)this.initial/10;
		Double max2 = (double)this.max/10;
		
		// Creates the labels which display values as doubles (not slider units)
		nameLabel = new JLabel(name);
		minLabel = new JLabel(min2.toString());
		sReadout = new JLabel(initial2.toString());
		sReadout.setForeground(Color.blue);
		maxLabel = new JLabel(max2.toString());
		
		mySlider = new JSlider(this.min,this.max,this.initial); // Creates the slider (with integer input (slider units))
		myPanel = new JPanel(); // Creates the panel
		myPanel.setLayout(new TableLayout(1,5)); // The panel is set to have 1 row and 5 columns
		
		// Adds the labels and slider to the panel and specifies their widths
		myPanel.add(nameLabel,"width=100");
		myPanel.add(minLabel,"width=25");
		myPanel.add(mySlider,"width=100");
		myPanel.add(maxLabel,"width=100");
		myPanel.add(sReadout,"width=75");
	}
	
	// Getter method for the slider value (with Integer return type)
	public Integer getIntegerSliderValue() {
		return mySlider.getValue();
	}
	
	 // Getter method for the slider value (with Double return type)
	public Double getDoubleSliderValue() {
		return !integerSlider ? (double)mySlider.getValue()/10 : (double)mySlider.getValue();
		// If integerSlider is false, then it Converts from slider units by scaling the slider value down by a factor of 10 (1 slider unit = 0.1)
		// If integerSlider is true, then it just casts the from integer to double
	}
	
	// Setter method for the slider value (with Integer argument)
	public void setSliderValue(Integer value) {
		mySlider.setValue(value);
	}
	
	// Setter method for the slider value (with Double argument)
	public void setSliderValue(Double value) {
		if (!integerSlider) { // If integerSlider is false, then it converts the input into slider units (1 slider unit = 0.1)
			mySlider.setValue((int)(value*10));
		}
		else { // If integerSlider is true, then it simply converts from double to integer by truncating
			mySlider.setValue(value.intValue());
		}
		
	}
	
	// Returns the reference to the sReadout label so that it can be changed in the bSim class when the user moves the slider
	public JLabel getReadout() {
		return sReadout;
	}
	
	// Returns true if this SliderBox object was instantiated with integer values
	public boolean isIntegerSlider() {
		return integerSlider;
	}
	
	// Returns the reference to the panel so that it can be accessed by the init() method of the bSim class (which creates the GUI)
	public JPanel getPanel() {
		return myPanel;
	}
	
	// Returns the reference to the slider so that it can be accessed by the bSim class to add a listener to it and to catch a ChangeEvent
	public JSlider getSlider() {
		return mySlider;
	}
}
