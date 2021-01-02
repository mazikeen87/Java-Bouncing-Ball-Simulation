/**
 * @author Christopher Boustros
 * ECSE 202 Introduction to Software Engineering
 */
package project;

import acm.graphics.*;

import java.awt.*;
import java.awt.event.*; // To access the MouseMotionListener interface

// This class creates a GOval object and simulates a bouncing ball by updating the location of the GOval object as the ball moves.
public class gBall extends Thread implements MouseMotionListener {	
	private static final double g = 9.8; // Gravitational acceleration
	private static final double intervalTime = 0.1; // The amount of seconds between frame updates
	
	// These instance variables will be initialized in the constructor
	private double Xi; // The initial horizontal position of the center of the ball
	private double h_0; // The initial height of the center of the ball
	private double bSize; // The radius of the ball
	private double l; // The loss coefficient
	private double v_x; // The horizontal speed of the ball
	private GOval ball; // A GOval object that represents the ball
	private boolean loopActive = true; // This will be set to false once the ball stops bouncing
	
	// Constructor
	public gBall(double Xi, double h_0, double bSize, Color bColor, double l, double v_x) {
		this.Xi = Xi - bSize; // Adjust Xi so that it is the horizontal position of the center of the ball instead of the position of the leftmost point of the ball
		this.h_0 = h_0 > bSize ? h_0 - bSize : 0; // The arguments Xi, h_0 are the coordinates of the center of the ball. So, h_0 cannot be less than the radius (bSize)
												  // When the argument h_0 is less than bSize, the instance variable this.h_0 is bounded to 0 because the ball cannot start below the ground
		this.bSize = bSize;
		this.l = l;
		this.v_x = v_x;
		
		ball = new GOval(convertX(Xi),convertY(h_0+2*bSize),convertX(2*bSize),convertX(2*bSize)); // A GOval object that represents a ball is created with the parameters of the constructor
		ball.setFilled(true); // The GOval object is filled
		ball.setFillColor(bColor); // The color of the GOval object is set to color object parameter
		ball.addMouseMotionListener((MouseMotionListener) this); // Adds a mouse motion listener to ball (the GOval object)
	}
	
	// A getter method that returns the reference to the GOval object so that the bSim class can access it and add it to the Java applet
	public GOval getBall() {
		return ball;
	}
	
	// A getter method for the size of the ball to be used in the bTree class
	public double getSize() {
		return bSize;
	}
	
	// A getter method that returns false if the ball has stopped bouncing and true otherwise. This is used in the isRunning() method of bTree class.
	public boolean isActive() {
		return loopActive;
	}
	
	// Sets loopActive to false, which ends the while loop and stops the simulation
	public void stopSimulation() {
		loopActive = false;
	}
	
	// A getter method for the interval time (to be used in the bSim class)
	public double getIntervalTime() {
		return intervalTime;
	}
	
	// This method changes the location of ball (the GOval object) to (x,y) in simulation coordinates
	public void moveTo(double x, double y) {
		ball.setLocation(convertX(x), convertY(y+2*bSize)); // y+2*bSize so that the bottom of the ball is set at a height of 0 (instead of the top of the ball)
	}
	
	public double convertX(double X) { 
		return X * 10; 
	}
	
	public double convertY(double Y) {
		return (bSim.HEIGHT - bSim.OFFSET) - Y * 10;
	}
	
	// Pause for intervalTime seconds
	public void sleep() {
		try {
			Thread.sleep((long)(intervalTime*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// Overrides the run() method of the Thread class
	public void run() {
		boolean directionUp = false;
		double v_t = Math.sqrt(2*g*h_0);
		double initialUpPosition = 0;
		double h = h_0;
		double x = Xi; 
		
		while (loopActive) { // The simulation runs until loopActive is set to false once the ball stops bouncing or once stopSimulation() is called
			for (double t = intervalTime; t < 100; t += intervalTime) { 
				if (!loopActive) break; // To stop the simulation when stopSimulation() is called
				x += v_x * intervalTime;
				
				double h_ = h > 0 ? h : 0;
				ball.setLocation(convertX(x),convertY(h_+2*bSize)); 
				
				if (!directionUp) { 
					h = h_0 - (0.5)*g*Math.pow(t, 2); 
					if (h <= 0) { 
						h_0 = h;
						initialUpPosition = h;
						directionUp = true; 
						v_t *= Math.sqrt(1-l); 
						sleep(); 
						break;
					}
				}
				else { 
					h = initialUpPosition + v_t * t - (0.5)*g*Math.pow(t,2);
					if (h < 0 && h < initialUpPosition) { // If h < initialUpPosition, then the ball has changed direction and is moving downwards
						// If the ball changes direction when it is still below the ground, then this means it does not have enough energy to move up after an interval of 0.1s.
						// Therefore, the ball is assumed to have stopped bouncing.
						h = 0;
						loopActive = false; // loopActive is set to false to indicate that the ball has stopped bouncing
					}
					
					if (h > h_0) {
						h_0 = h;
					} else { 
						directionUp = false;
						sleep();
						break; 
					}
				}
				
				sleep();
			}
		}
	}
		
	// Implements the mouseDragged(MouseEvent e) method inherited from the MouseMotionListener interface
	public void mouseDragged(MouseEvent e) {
		// This method is called when the user drags the ball
		
		// Stops the simulation
		loopActive = false;
		
		// Updates ball location
		ball.setLocation(e.getX() - convertX(bSize), e.getY() - convertX(bSize));
	}
	
	// Implements the mouseMoved(MouseEvent e) method inherited from the MouseMotionListener interface
	public void mouseMoved(MouseEvent e) {
		// No implementation
	}
}