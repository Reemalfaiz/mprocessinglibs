/*

	MGestures - Gesture Library for Mobile Processing

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

package mjs.processing.mobile.mgestures;

import java.util.Vector;

/**
 * Gesture Recognizer 
 */
public class MGestures
{
	/** 
	 * Up Gesture 
	 * @value 2
	 */
	public final static int UP = 2;
	 
	/** 
	 * Down Gesture 
	 * @value 6
	 */
	public final static int DOWN = 6;
	
	/** 
	 * Left Gesture 
	 * @value 4
	 */
	public final static int LEFT = 4;

	/** 
	 * Right Gesture 
	 * @value 0
	 */
	public final static int RIGHT = 0;

	/** 
	 * Up Rigth Gesture 
	 * @value 1
	 */
	public final static int UP_RIGHT = 1;
	
	/** 
	 * Up Left Gesture 
	 * @value 3
	 */
	public final static int UP_LEFT = 3;
	
	/** 
	 * Down Rigth Gesture 
	 * @value 7
	 */
	public final static int DOWN_RIGHT = 7;
	
	/** 
	 * Down Left Gesture 
	 * @value 5
	 */
	public final static int DOWN_LEFT = 5;

	/**
	 * Current gesture points 
	 */
	private Vector points;
	
	/**
	 * Is capturing 
	 */
	private boolean capturing;
	
	/**
	 * Create a gesture recognizer object
	 */
	public MGestures()
	{
	 	points = new Vector();
	}
	 
	/**
	 * Add the coordinates like a new point 
	 *
	 * @param x coordinate X
	 * @param y coordinate Y
	 */
	public void addPoint(int x, int y)
	{
	 	// Add point to the gesture
	 	if(capturing)
	 		points.addElement(new MGesturePoint(x,y));
	}
	 
	/**
	 * Start capture a new gesture 
	 */
	public void start()
	{
		// Remove old points 
	 	points.removeAllElements();
	 	
	 	// Change state 
	 	capturing = true;
	}
	 
	/**
	 * Start capture a new gesture 
	 */
	public void stop()
	{
	 	// Don't capture points 
	 	capturing = false;
	}
	  
 	/**
	 * Recognize the gesture 
	 */
	public int recognize()
	{
		// Get the dir most follow 
		int dir = getMaxDir(getHistogram(getChainCode()));
		
		// Return dir 
		return dir;
	}
	 
	/**
	 * Return the chain code for the current gesture
	 *
	 * @return gesture chain code 
	 */
	private int[] getChainCode()
	{
		// Chain code elements 
		Vector chainCode = new Vector();
		
		// Check directions of points 
		for(int i=0; i<points.size()-1; i++)
		{
			// Get two consecutive points 
			MGesturePoint pointOne = (MGesturePoint) points.elementAt(i);
			MGesturePoint pointTwo = (MGesturePoint) points.elementAt(i+1);
			
			// Return the direction of the second point 
			int dir = pointOne.direction(pointTwo);
			
			// Add the dir to the chain code if available 
			if(dir != -1)
				chainCode.addElement(new Integer(dir));
		}
		
		// Copy the chain code to an array 
		int[] array = new int[chainCode.size()];
		
		// Get int values 
		for(int i=0; i<array.length; i++)
			array[i] = ((Integer) chainCode.elementAt(i)).intValue();
			
		// Return the array
		return array;
	}
	
	/**
	 * Return the histogram for the directions 
	 *
	 * @param chainCode the chain code to analize 
	 * @return direction histogram 
	 */
	private int[] getHistogram(int[] chainCode)
	{
		// Histogram 
		int[] histogram = new int[8];
		
		// Count each direction 
		for(int i=0; i<chainCode.length; i++)
			histogram[chainCode[i]]++;
			
		// Return histogram 
		return histogram;
	}
	
	/**
	 * Return the direction with the max value 
	 *
	 * @param histogram the chain code histogram
	 * @return direction with max value 
	 */
	private int getMaxDir(int[] histogram)
	{
		// The direction 
		int dir = -1;
		
		// Init max value with min 
		int maxValue = Integer.MIN_VALUE;
		
		// Check dir values 
		for(int i=0; i<histogram.length; i++)
			// If value is greater, update value and dir 
			if(maxValue < histogram[i])
			{
				maxValue = histogram[i];
				dir = i;
			}
			
		// Return the direction 
		return dir;
	}
	
	/**
	 * Return the gesture name 
	 *
	 * @param gesture Gesture id 
	 * @return Gesture name 
	 */
	public String name(int gesture)
	{
		// Gesture name 
		String name = "Unknown";
		
		// Check for names 
		switch(gesture)
		{
			case UP : name = "UP"; break;
			case DOWN : name = "DOWN"; break;
			case LEFT : name = "LEFT"; break;
			case RIGHT : name = "RIGHT"; break;
			case UP_LEFT : name = "UP_LEFT"; break;
			case UP_RIGHT : name = "UP_RIGHT"; break;
			case DOWN_LEFT : name = "DOWN LEFT"; break;
			case DOWN_RIGHT : name = "DOWN RIGHT"; break;
		}
		
		// Return the name 
		return name;
	} 
}