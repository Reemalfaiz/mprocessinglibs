/*

	MSensor - Sensor Library for Mobile Processing

	Copyright (c) 2007 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.msensor;

import javax.microedition.sensor.Data;
import javax.microedition.sensor.SensorConnection;
import javax.microedition.sensor.SensorInfo;

import processing.core.PMIDlet;

/**
 * Accelerometer
 */
public class MAccelerometer extends MSensor
{
	/**
	 * Acceleration in x coordinate.
	 */
	private int x;

	/**
	 * Acceleration in y coordinate.
	 */
	private int y;
	
	/**
	 * Acceleration in z coordinate.
	 */	
	private int z;
	
	/**
	 * Create and accelerometer with the infor specified
	 *
	 *
	 * @param pMIDlet Parent MIDlet	 	 
	 * @param sensorInfo Sensor information
	 */
	protected MAccelerometer(PMIDlet pMIDlet, SensorInfo sensorInfo)
	{
		super(pMIDlet, sensorInfo);
	}
	
	/**
	 * Notification of the received sensor data.
	 * 
	 * @param sensor SensorConnection, the origin of the received data
	 * @param data the received sensor data
	 * @param true if some data has been lost between the previously delivered 
	 * 			data and the beginning of the data returned in this method call
	 */ 
	public void dataReceived(SensorConnection sensor, Data[] data, 
		boolean isDataLost)
	{
		// Search the X,Y,Z values 
		for(int i=0; i<data.length; i++)
		{
			String name = data[i].getChannelInfo().getName();
			
			if(name.equals("X"))
				x = getMax(data[i].getIntValues());
			else if(name.equals("Y"))
				y = getMax(data[i].getIntValues());
			else if(name.equals("Z"))
				z = getMax(data[i].getIntValues());
		}
		
		// Fire event		
		dataAvailable();		
	}
	
	/**
	 * Return the max value of the array
	 *
	 * @param values Integer array
	 * @return Max value of the array
	 */
	private int getMax(int[] values)
	{
		// Init the max value at min value
		int maxValue = Integer.MIN_VALUE;
		
		// Check each position 
		for(int i=0; i<values.length; i++)
			if(values[i] > maxValue)
				maxValue = values[i];
				
		// Return the max value
		return maxValue;
	}
	
	/**
	 * Return the acceleration in X
	 *
	 * @param acceleration in x
	 */
	public int accelX()
	{
		return x;
	}
	
	/**
	 * Return the acceleration in y
	 *
	 * @param acceleration in y
	 */
	public int accelY()
	{
		return y;
	} 	

	/**
	 * Return the acceleration in z
	 *
	 * @param acceleration in z
	 */
	public int accelZ()
	{
		return z;
	} 		 	
}