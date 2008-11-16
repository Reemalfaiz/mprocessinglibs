/*

	MPush - Asynchronous Connections Library for Mobile Processing

	Copyright (c) 2006 Mary Jane Soft - Marlon J. Manrique
	
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
	
*/

package mjs.processing.mobile.mpush;

import java.util.*;
import javax.microedition.io.*;

import processing.core.*;

/**
 * MPush 
 *
 * This class allows register alarms and inbounds asynchronous connections
 * each library has to register the connections used to activate the MIDlet
 * 
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */

public class MPush
{
	/**
	 * Current MIDlet
	 */

	PMIDlet pMIDlet;
	
	/**
	 * Create register associated with the specified midlet
	 *
	 * @param pMIDlet The midlet
	 */

	public MPush(PMIDlet pMIDlet)
	{
		this.pMIDlet = pMIDlet;
	}

	/**
	 * Register a time to launch the midlet associated with the push.	
	 *
	 * @param time time at which the MIDlet is to be executed 	 
	 */

	void registerAlarm(long time)
	{
		// Extract the midlet name
		String midlet = pMIDlet.getClass().getName();
		
		registerAlarm(midlet,time);	
	}

	/**
	 * Register a time to launch the specified application.
	 * MPush supports one outstanding wake up time per MIDlet in the current suite. 
	 * An application is expected to use a TimerTask for notification of time based 
	 * events while the application is running.
	 *
	 * If a wakeup time is already registered, the previous value will be returned, 
	 * otherwise a zero is returned the first time the alarm is registered.
	 *
	 * @param midlet class name of the MIDlet
	 * @param time time at which the MIDlet is to be executed 
	 * @return the time at which the most recent execution of this MIDlet was scheduled to occur
	 */

	public void registerAlarm(final String midlet, final long time)
	{
		// Create a new thread to avoid dead locks
		new Thread()
		{
			public void run()
			{
				try
				{
					// Register the alarm into the PushRegistry
					PushRegistry.registerAlarm(midlet,time);
				}
				catch(Exception e)
				{
					throw new RuntimeException(e.getMessage());
				}
			}
		}.start();
	}

	/**
	 * Register the midlet associated to the push to execute at given time.
	 *
	 * @param midlet class name of the MIDlet
	 * @param year year value
	 * @param month month value
	 * @param day day value
	 * @param hour hour value
	 * @param minute minute value
	 * @param second second value
	 */

	public void executeAt(int year, int month, int day, int hour, int minute, int second)
	{
		// Extract the midlet name
		String midlet = pMIDlet.getClass().getName();
		executeAt(midlet,year,month,day,hour,minute,second);		
	}

	/**
	 * Register the midlet to execute at given time.
	 *
	 * @param midlet class name of the MIDlet
	 * @param year year value
	 * @param month month value
	 * @param day day value
	 * @param hour hour value
	 * @param minute minute value
	 * @param second second value
	 */

	public void executeAt(String midlet, int year, int month, int day, int hour, int minute, int second)
	{
		// Create a calendar and set the given time
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,year);
		calendar.set(Calendar.MONTH,month);
		calendar.set(Calendar.DAY_OF_MONTH,day);
		calendar.set(Calendar.HOUR_OF_DAY,hour);
		calendar.set(Calendar.MINUTE,minute);
		calendar.set(Calendar.SECOND,second);

		System.out.println(calendar);

		// Get the give time like a date
		Date date = calendar.getTime();

		System.out.println(date);

		// Register an alarm with the given time
		registerAlarm(midlet,date.getTime());
	}

	/**
	 * Register the midlet to execute in the next time miliseconds.
	 *
	 * @param time delta time
	 */

	public void executeIn(long time)
	{
		// Extract the midlet name
		String midlet = pMIDlet.getClass().getName();
		executeIn(midlet,time);
	}

	/**
	 * Register the midlet to execute in the next time miliseconds.
	 *
	 * @param midlet class name of the MIDlet
	 * @param time delta time
	 */

	public void executeIn(String midlet, long time)
	{
		// Add to the alarm time the current time
		registerAlarm(midlet,new Date().getTime() + time);
	}

	/**
	 * Register a dynamic connection with the application management software. 
	 * Once registered, the dynamic connection acts just like a connection preallocated 
	 * from the descriptor file.
	 *
	 * This method is used by libraries to register their connections.
	 *
	 * @param connection generic connection protocol, host  and port number  
	 * @param midlet class name of the MIDlet to be launched, when new external data is available. 
	 * @param filter a connection URL string indicating which senders are allowed to cause the MIDlet to be launched
	 */

	public void registerConnection(String connection, String midlet, String filter)
	{
		try
		{
			// Register the connection with the PushRegistry
			PushRegistry.registerConnection(connection,midlet,filter);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());			
		}
	}

	/**
	 * Remove a dynamic connection registration.
	 *
	 * @param connection generic connection protocol, host and port number 
	 */

	public void unregisterConnection(String connection)
	{
		try
		{
			// Unregister the connection 
			PushRegistry.unregisterConnection(connection);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());			
		}
	}

	/**
	 * Return a list of all registered connections for the current MIDlet suite.
	 *
	 * @return array of registered connection strings, where each connection 
	 * 			is represented by the generic connection protocol, host and 
	 *			port number identification
	 */

	public String[] connections()
	{
		return PushRegistry.listConnections(false);
	}

	/**
	 * Return a list of all the connections with data available.
	 *
	 * @return array of registered connection strings, where each connection 
	 * 			is represented by the generic connection protocol, host and 
	 *			port number identification
	 */

	public String[] activeConnections()
	{
		return PushRegistry.listConnections(true);	
	}

	/**
	 * Check if the connection is already registered by some MIDlet
	 *
	 * @return true if the connection is already registered, false if not
	 */

	public boolean isRegistered(String connection)
	{
		// Get all registered connections 
		String[] connections = connections();

		// Check if some one is the connection specified
		for(int i=0; i<connections.length; i++)
			if(connection.equals(connections[i]))
				return true;

		// The connection is not registered
		return false;
	}
}
