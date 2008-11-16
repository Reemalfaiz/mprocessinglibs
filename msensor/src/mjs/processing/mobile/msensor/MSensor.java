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

import javax.microedition.io.Connector;

import javax.microedition.sensor.Data;
import javax.microedition.sensor.DataListener;
import javax.microedition.sensor.SensorConnection;
import javax.microedition.sensor.SensorInfo;

import processing.core.PException;
import processing.core.PMIDlet;

/**
 * Sensor
 */
public class MSensor implements DataListener
{
	/**
	 * Available Event
	 *
	 * @value 1
	 */
	public final static int EVENT_AVAILABLE = 1;	

	/**
	 * Unavailable Event
	 *
	 * @value 10
	 */
	public final static int EVENT_UNAVAILABLE = 10;	

	/**
	 * Data Available Event
	 *
	 * @value 30
	 */
	public final static int EVENT_DATA_AVAILABLE = 30;	

	/**
	 * Environmental sensors.
	 *
	 * @value 1
	 */
	public final static int AMBIENT = 1;
	
	/**
	 * Vehicle sensors.
	 *
	 * @value 2
	 */
	public final static int VEHICLE = 2;

	/**
	 * Device sensors.
	 *
	 * @value 3
	 */
	public final static int DEVICE = 3;
	
	/**
	 * User sensors.
	 *
	 * @value 4
	 */
	public final static int USER = 4;
	
	/**
	 * Search criteria.
	 *
	 * @value -1
	 */
	public final static int ALL = -1;
	
	/**
	 * Current MIDlet
	 */
	private PMIDlet pMIDlet;
	
	/**
	 * Info of the sensor
	 */
	protected SensorInfo info;
	
	/**
	 * Connection with the sensor
	 */
	private SensorConnection connection;
	
	/**
	 * Create a sensor with the info specified
	 *
	 * @param pMIDlet Parent MIDlet	 
	 * @param sensorInfo Sensor info
	 */
	protected MSensor(PMIDlet pMIDlet, SensorInfo info)
	{
		this.pMIDlet = pMIDlet;
		this.info = info;		
	}
	
	/**
	 * Open the sensor connection 
	 */
	public void open()
	{
		try
		{
			// If the connection is already open, do nothing  
			if(connection != null)
				return;
			
			// Obtain the connection url and create one 
			String url = info.getUrl();
			connection = (SensorConnection) Connector.open(url);
			
			// Listen data from the sensor 
			// create a buffer of 10 
			connection.setDataListener(this,10);
		}
		catch(Exception e)
		{
			throw new PException(e);
		}		
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
	}
	
	/**
	 * Fire the avialable data event
	 */
	public void dataAvailable()
	{
		if(pMIDlet != null)
			pMIDlet.enqueueLibraryEvent(
				this,MSensor.EVENT_DATA_AVAILABLE,null);
	}
	
	/**
	 * Close the connection with the sensor
	 */
	public void close()
	{
		try
		{
			// If the connection is already open, do nothing  
			if(connection == null)
				return;
			
			// Close the connection and set like null
			connection.close();
			connection = null;
		}
		catch(Exception e)
		{
			throw new PException(e);
		}			
	}
}
