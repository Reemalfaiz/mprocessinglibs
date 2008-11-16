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

import javax.microedition.sensor.SensorInfo;
import javax.microedition.sensor.SensorListener;
import javax.microedition.sensor.SensorManager;

import processing.core.PMIDlet;

class MSensorListener implements SensorListener
{
	/**
	 * Current Sensor Manager
	 */
	private MSensorManager manager;
	
	/**
	 * Create a sensor listener
	 *
	 * @param manager Current sensor manager
	 */
	public MSensorListener(MSensorManager manager)
	{
		this.manager = manager;
	}	
	
	/**
	 * The notification called when the sensor becomes available.
	 *
	 * @param info the SensorInfo object indicating which sensor is now 
	 *		available
	 */
	public void sensorAvailable(SensorInfo info)
	{
		// If a sketch is listening the events 
		if(manager.pMIDlet != null)
		{
			// Get the sensor  
			MSensor mSensor = manager.sensor(info);
				
			// Fire the event
			manager.pMIDlet.enqueueLibraryEvent(manager,
				MSensor.EVENT_AVAILABLE,mSensor);
		}
	}
	
	/**
	 * The notification called when the sensor becomes unavailable.
	 *
	 * @param info the SensorInfo object indicating which sensor is now 
	 *		unavailable
	 */  
	public void sensorUnavailable(SensorInfo info)
	{
		// If a sketch is listening the events 
		if(manager.pMIDlet != null)
		{
			// Get the sensor  
			MSensor mSensor = manager.sensor(info);
				
			// Fire the event
			manager.pMIDlet.enqueueLibraryEvent(manager,
				MSensor.EVENT_UNAVAILABLE,mSensor);
		}
	}

	/** 
	 * Listen availability events of the sensor 
	 *
	 * @param quantity a quantity in which the application is interested
	 */ 
	public void listen(String quantity)
	{
		// Register singleton to listen  
		SensorManager.addSensorListener(this,quantity);
	}
	
	/** 
	 * Listen availability events of the sensor 
	 *
	 * @param sensor SensorInfo
	 */ 
	public void listen(MSensor mSensor)
	{
		// Register singleton to listen  
		SensorManager.addSensorListener(this,mSensor.info);
	}
}