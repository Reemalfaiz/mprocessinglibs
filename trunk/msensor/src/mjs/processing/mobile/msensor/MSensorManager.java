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

import java.util.Hashtable;

import javax.microedition.sensor.SensorInfo;
import javax.microedition.sensor.SensorManager;

import processing.core.PMIDlet;

public class MSensorManager
{
	/**
	 * Current MIDlet
	 */
	protected PMIDlet pMIDlet;
	
	/**
	 * Sensor listener
	 */
	private MSensorListener listener;
	
	/**
	 * Current Sensors
	 *
	 * Store the sensors created in the library to return the same objects 
	 * when the user try to find again the sensor
	 */
	private Hashtable sensorsCache;
	
	/**
	 * Create a Sensor Manager
	 */
	public MSensorManager(PMIDlet pMIDlet)
	{
		// Set midlet
		this.pMIDlet = pMIDlet;
		
		// Create the sensor listener		
		listener = new MSensorListener(this);
		
		// Create an empty list of sensors
		sensorsCache = new Hashtable();
	}
	
	/**
	 * Returns the version of the API
	 *
	 * @return version of the API
	 */
	public static String version()
	{
		return System.getProperty("microedition.sensor.version");
	}
	
	/**
	 * Return all the sensors availables
	 *
	 * @return All the sensors availables
	 */
	public MSensor[] findSensors()
	{
		return findSensors(null,-1);
	}
	
	/**
	 * Return all the sensors availables with the quantity specified
	 *
	 * @param quantity the quantity defining the desired sensor
	 * @return All the sensors availables
	 */
	public MSensor[] findSensors(String quantity)
	{
		return findSensors(quantity,-1);
	}	
	
	/**
	 * Returns an array of SensorInfo objects of sensors that match the given 
	 * quantity and context type.
	 *
	 * @param quantity the quantity defining the desired sensor
	 * @param contextType the context type qualifying the desired sensor
	 */
	public MSensor[] findSensors(String quantity, int contextType)
	{
		// Get the JSR context 
		String sType = contextType(contextType);
		
		// Search for sensors
		SensorInfo[] infos = SensorManager.findSensors(quantity,sType);
		
		// Create a MSensor array with the sensors info
		MSensor[] sensors = new MSensor[infos.length];
				
		for(int i=0; i<infos.length; i++)
		{
			// Check if the sensor is in the cache
			MSensor sensor = sensor(infos[i]);
			
			// If the sensor is not present, create one 
			// and save in the cache
			if(sensor == null)		
			{	
				if(quantity.equals("acceleration"))
					sensor = new MAccelerometer(pMIDlet,infos[i]);
				else 
					sensor = new MSensor(pMIDlet,infos[i]);
				
				// Put the new sensor in the cache 
				sensorsCache.put(infos[i],sensor);
			}
			
			// Put the sensor in the array
			sensors[i] = sensor;
		}
		
		// Return the sensors
		return sensors;
	}
	
	/**
	 * Return the JSR sensor context according to the MSensor context
	 *
	 * @param MSesor context
	 * @return JSR context
	 */
	private String contextType(int contextType)
	{
		// Init the sensor type to null
		String sType = null;
		
		switch(contextType)
		{
			case MSensor.AMBIENT : 
				sType = SensorInfo.CONTEXT_TYPE_AMBIENT;
				break;
	
			case MSensor.DEVICE : 
				sType = SensorInfo.CONTEXT_TYPE_DEVICE;
				break;  			

		/*
			No available on Nokia RI
			case MSensor.VEHICLE : 
				sType = SensorInfo.CONTEXT_TYPE_VEHICLE;
				break;
		*/  			

			case MSensor.USER : 
				sType = SensorInfo.CONTEXT_TYPE_USER;
				break; 
		} 

		// Return the sesor context		
		return sType;
	}
	
	/** 
	 * Listen availability events of the sensor 
	 *
	 * @param quantity a quantity in which the application is interested
	 */ 
	public void listen(String quantity)
	{
		listener.listen(quantity);
	}	
	
	/** 
	 * Listen availability events of the sensor 
	 *
	 * @param sensor SensorInfo
	 */ 
	public void listen(MSensor mSensor)
	{
		listener.listen(mSensor);	
	}
	
	/**
	 * Checks if the library is supported
	 *
	 * @return true if the library is supported, false otherwise
	 */
	public static boolean supported()
	{
		try
		{
			// Try to load a class from the Sensor API 
			Class.forName("javax.microedition.sensor.SensorInfo");
		}
		catch(ClassNotFoundException e)
		{
			// If the class can't be load, the library is not supported 
			return false;
		}
		
		// All ok
		return true;
	}
	
	/**
	 * Return the sensor if exists from the cache
	 *
	 * @param info The sensor info
	 * @return null if no available, a sensor otherwise
	 */
	protected MSensor sensor(SensorInfo info)
	{
		return (MSensor) sensorsCache.get(info);
	} 	
}