/*

	MWiimote - Wii Remote Library for Mobile Processing

	Copyright (c) 2008 Mary Jane Soft - Marlon J. Manrique
	
	http://mjs.darkgreenmedia.com
	http://marlonj.darkgreenmedia.com

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General
	Public License along with this library; if not, write to the
	Free Software Foundation, Inc., 59 Temple Place, Suite 330,
	Boston, MA  02111-1307  USA

	$Id$
	
*/

package mjs.processing.mobile.mwiimote;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

import java.lang.reflect.Method;

import mjs.processing.mobile.mbt.MBt;
import mjs.processing.mobile.mbt.MBtP;

import processing.core.PApplet;

/**
 * Processing Whiteboard
 *  
 * @author Marlon J. Manrique
 */
public class MWhiteBoardP extends MWhiteBoard implements MWiiControlListener
{
	/**
	 * Event Calibration Done  
	 */	 
	public static final int EVENT_CALIBRATION_DONE = 1;	

	/**
	 * Pen calibration method.
	 * 	 
	 * Calibrate the whiteboard using an IR pen. 
	 */
	public final static int CALIBRATION_PEN = 1;
	
	/**
	 * Key calibration method 
	 * 	 
	 * Calibrate the whiteboard using a continous ir light 
	 * and using a keyboard event to add the point. 
	 */
	public final static int CALIBRATION_KEY = 2;
	
	/**
	 * Current calibration method 
	 */
	private int calibrationMethod;

	/**
	 * Key to add calibrate points 
	 */
	private char calibrationKey;
	
	/**
	 * Reset calibration process key  
	 */
	private char resetCalibrationKey;
	
	/**
	 * Board Width 
	 */
	private int width;
	
	/**
	 * Board Height 
	 */
	private int height;
	
	/**
	 * The Applet
	 */
	private PApplet pApplet;	
	
	/**
	 * The method to fire the events
	 */
	private Method eventMethod;
	
	/**
	 * Calibrating 
	 */
	private boolean calibrating;
	
	/**
	 * Wiimote control
	 */
	private MWiiControl mWiiControl;
	
	/**
	 * Calibration dot counter 
	 */
	private int dotCounter;
	
	/**
	 * Calibration dots 
	 */
	private int[] dots;
	
	/**
	 * Bluetooth Library 
	 */
	private MBt mBt;
	
	/**
	 * Wiimote Library 
	 */
	private MWiimote mWiimote;
	
	/**
	 * Events Robot 
	 */
	private Robot robot;
	
	/**
	 * Robot enable  
	 */
	private boolean robotEnable;
	
	/** 
	 * Creates Whiteboard
	 *
	 * @param pApplet Parent Processing Applet
	 * @param width screen width 
	 * @param height screen height 
	 */
	public MWhiteBoardP(PApplet pApplet, int width, int height)
	{
		// Set applet 
		this.pApplet = pApplet;
		
		// Set board size 
		this.width = width;
		this.height = height;
		
		// Get library event 
		getLibraryEvent();
		
		// Create dots info 
		dots = new int[8];		
		
		// Set calibration values 
		calibrationMethod = CALIBRATION_PEN;
		calibrationKey = 'c';
		resetCalibrationKey = 'r';
	}
	
	/** 
	 * Creates Whiteboard
	 *
	 * @param pApplet Parent Processing Applet
	 */
	public MWhiteBoardP(PApplet pApplet)
	{
		this(pApplet,pApplet.width,pApplet.height);
	}	

	/** 
	 * Creates Whiteboard with the bluetooth and wiimote library objects 
	 *
	 * @param pApplet Parent Processing Applet
	 * @param mBt Bluetooth library
	 * @param mWiimote Wiimote library
	 */
	public MWhiteBoardP(PApplet pApplet, MBt mBt, MWiimote mWiimote)
	{
		this(pApplet,pApplet.width,pApplet.height,mBt,mWiimote);
	}	
	
	/** 
	 * Creates Whiteboard with the bluetooth and wiimote library objects 
	 *
	 * @param pApplet Parent Processing Applet
	 * @param mBt Bluetooth library
	 * @param mWiimote Wiimote library
	 * @param width screen width 
	 * @param height screen height 
	 */
	public MWhiteBoardP(PApplet pApplet, int width, int height,
		MBt mBt, MWiimote mWiimote)
	{
		this(pApplet,width,height);
		
		// Set library objects 
		this.mBt = mBt;
		this.mWiimote = mWiimote;
	}
		
	/**
	 * Get the library event of the sketch 
	 */
	private void getLibraryEvent()
	{
		// check to see if the host applet implements
		// the event method
		try
		{
			// Check method existence
			eventMethod = pApplet.getClass().
							getMethod("libraryEvent", 
							new Class[] { Object.class, Integer.TYPE, 
											Object.class });
		}
		catch (Exception e)
		{
			// Ignore
			e.printStackTrace();
	    }	
	}
	
	/**
	 * Start calibration process
	 */
	public void startCalibration()
	{
		calibrating = true;
	}
	
	/**
	 * Draw calibration info 
	 */
	public void draw()
	{
		// If not calibratinf, do nothing 
		if(!calibrating)
			return;
			
		// Get calibrate dot location 
		int x = 0;
		int y = 0;
		
		// Set dot positions 
		switch(dotCounter)
		{
			case 0 : x = 20; y = 20; break;
			case 1 : x = width - 20; y = 20; break;
			case 2 : x = width - 20; y = height - 20; break;
			case 3 : x = 20; y = height - 20; break;
		}
		
		// Draw calibrate dot
		pApplet.stroke(0,255,0);
		pApplet.noFill();
		pApplet.rectMode(PApplet.CENTER);
		pApplet.rect(x,y,20,20);
		pApplet.line(x-15,y,x+15,y);
		pApplet.line(x,y-15,x,y+15);		
	}
	
	/**
	 * Set the wiimote to control the board 
	 *
	 * @param mWiiControl Wiimote control of the board
	 */
	public void control(MWiiControl mWiiControl)
	{
		// Get control
		this.mWiiControl = mWiiControl;
		
		// Enable ir 
		mWiiControl.ir(true);
		mWiiControl.invertY(true);
				
		// Begin listening ir data 
		mWiiControl.addListener(this);
	}
	
	/**
	 * Call when the ir data change 
	 */
	public void irAvailable()
	{
		// If no calibrating
		if(!calibrating)
		{
			// Send mouse events if robot is enable 
			if(robotEnable)
				fireMouseEvents();
			// Do nothing 
			else
				return;
		}
		else
		{
			// Add calibration dot
			if(calibrationMethod == CALIBRATION_PEN)
				addCalibrationDot();
		}
	}
	
	/**
	 * Change reset key 
	 *
	 * @param key new reset key
	 */
	public void resetKey(char key)
	{
		this.resetCalibrationKey = key;
	}
	
	/**
	 * Change calibration key 
	 *
	 * @param key new calibration key
	 */
	public void calibrationKey(char key)
	{
		this.calibrationKey = key;
	}
	
	/**
	 * Listen keyboard events s
	 */
	public void keyPressed()
	{
		// Restart calibration process 
		if(pApplet.key == resetCalibrationKey)
		{
			// Init values 
			dotCounter = 0;
			calibrating = true;
		}
		// Add calibration dot  
		else if(pApplet.key == resetCalibrationKey)
			// Add calibration dot
			addCalibrationDot();			
	}
	
	/** 
	 * Add the current point into the calibration data
	 */
	private void addCalibrationDot()
	{
		// Get first dot position 
		int x = mWiiControl.dotX(0);
		int y = mWiiControl.dotY(0);
	
		// If not valid 	
		if(x == -1 && y == -1)
			return;

		// Discard points near to before point 
		if(dotCounter > 0 &&
			Math.abs(dots[(dotCounter-1)*2]-x) < 10 &&
			Math.abs(dots[(dotCounter-1)*2+1]-y) < 10)
			return;
			
		// Set like calibration point 
		dots[dotCounter*2] = x;
		dots[dotCounter*2+1] = y;
		
			// Update dot counter 
		dotCounter++;
		
		// Check number of dots 
		if(dotCounter == 4)
		{
			// Update calibrating state 
			calibrating = false;
			
			// Set calibration data
			if(robotEnable)
			{
				// Get desktop coordinates 
				Point p = pApplet.frame.getLocationOnScreen(); 
				
				// Set desktop coordinate of reference points 
				screen(p.x+20,p.y+20,p.x+width-20,p.y+20,
					p.x+width-20,p.y+height-20,p.x+20,p.y+height-20);					
			}
			else  
				screen(20,20,width-20,20,width-20,height-20,20,height-20);
				
			// Set board dots 
			board(dots);
			
			// Compute matrix 
			computeMatrix();
			
			// Fire event 
			enqueueLibraryEvent(this,EVENT_CALIBRATION_DONE,null);
		}			
	}
	
	/**
	 * Start whiteboard 
	 */
	public void start()
	{
		// If no bluetooth specified, create one 
		if(mBt == null)
			mBt = new MBtP(pApplet);
	
		// If no wiimote specified, create one 
		if(mWiimote == null)
			mWiimote = new MWiimoteP(pApplet,mBt);
			
		// Discover wiimote control 
		mWiimote.discover();
	}	
	
	/**
	 * Listen library events 
	 *
	 * @param library Library 
	 * @param event Event 
	 * @param data Data 
	 */
	public void libraryEvent(Object library, int event, Object data)
	{
		// If any bluetooth event, let mwiimote handle it 
		if(library == mBt)
			mWiimote.libraryEvent(library,event,data);
		// Check wiimote events 
		else if(library == mWiimote)
		{
			switch(event)
			{
				// A control was found 
				// Set like current one and active accelerometer
				case MWiimote.EVENT_CONTROL_FOUND : 
					control((MWiiControl) data);
					startCalibration();
					break;
			}
		}
	}
	
	/**
	 * Stop whiteboard 
	 */
	public void stop()
	{
		// If a control is available close the communication 
		if(mWiiControl != null)
			mWiiControl.close();
	}
	
	/**
	 * Return the coordinate X of the dot specified  
	 *
	 * @param index Number of the dot 
	 * @return coordinate X of the dot 
	 */
	public int dotX(int index)
	{
		// Check if the control is available 
		if(mWiiControl == null)
			return -1;
			
		return screenPoint(mWiiControl.dotX(index),mWiiControl.dotY(index))[0];
	}
	
	/**
	 * Return the coordinate Y of the dot specified  
	 *
	 * @param index Number of the dot 
	 * @return the coordinate Y of the dot specified  
	 */
	public int dotY(int index)
	{
		// Check if the control is available 
		if(mWiiControl == null)
			return -1;
			
		return screenPoint(mWiiControl.dotX(index),mWiiControl.dotY(index))[1];
	}

	/**
	 * Return the size of the dot specified  
	 *
	 * @param index Number of the dot
	 * @return size of the dot 
	 */
	public int dotSize(int index)
	{
		// Check if the control is available 
		if(mWiiControl == null)
			return -1;
			
		return mWiiControl.dotSize(index);
	}
	
	/**
	 * Enable desktop events  
	 */
	public void desktop(boolean enable)
	{
		// Create a robot if not created 
		if(enable && robot == null)
		{
			try
			{
				robot = new Robot();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}			
		
		// Change robot status 
		robotEnable = true;
	}
	
	/**
	 * Fire mouse events when the robot is enable 
	 */
	private void fireMouseEvents()
	{
		// Get screen coordinates 
		int x = dotX(0);
		int y = dotY(0);
		
		// Check if point is valid 
		if(x > -1 && y > -1)
		{
			// Move the mouse and press button 
			robot.mouseMove(x,y);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
	}
	
	/**
	 * Method used to fire the library events
	 *
	 * @param library Library object that fires the event
	 * @param event Event identifier
	 * @param data Data provide in the event
	 */	 	
	protected void enqueueLibraryEvent(Object obj, int event, Object data)
	{
		// No event method present
		if(eventMethod == null)
			return;
			
		try
		{
			// Call the method
			eventMethod.invoke(pApplet, 
				new Object[] { obj, new Integer(event), data });
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
}