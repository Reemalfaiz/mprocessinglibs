/*

	MBluetooth - Bluetooth Library for Mobile Processing

	Copyright (c) 2005-2006 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mbluetooth;

import processing.core.PApplet;

import java.lang.reflect.Method;

/**
 * Bluetooth class use in Processing
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 * @since 0.4
 */

public class MBluetoothP extends MBluetooth
{
	/**
	 * The Applet
	 */
	 
	private PApplet pApplet;
	
	/**
	 * The method to fire the events
	 */
	 
	private Method eventMethod;
	
	/**
	 * Create the bluetooth service associated with the applet specified
	 *
	 * @param sketch The Applet
	 * @param uuid UUID used in the services
	 */
	public MBluetoothP(Object sketch, String uuid)
	{
		// Convert the object to the applet
		pApplet = (PApplet) sketch;

		// Set this like the bluetooth instance
		bluetoothInstance = this;

		// Init values of the bluetooth implementation		
		getInstance(sketch,uuid);
		
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
	 * Create the bluetooth service associated with the applet specified
	 *
	 * @param sketch The Applet
	 */
	public MBluetoothP(Object sketch)
	{
		this(sketch,null);
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
