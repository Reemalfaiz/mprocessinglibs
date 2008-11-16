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

import mjs.processing.mobile.mbt.MBt;
import mjs.processing.mobile.mbt.MDevice;
import mjs.processing.mobile.mbt.MService;

import processing.core.PMIDlet;

/**
 * Wii Remote Library 
 */
public class MWiimote
{
	/**
	 * Error Event  
	 */	 
	public static final int EVENT_ERROR = -1;

	/**
	 * Wiimote found Event 
	 */	 
	public static final int EVENT_CONTROL_FOUND = 1;
	
	/**
	 * Wiimote not found Event 
	 */	 
	public static final int EVENT_CONTROL_NOT_FOUND = 2;
	
	/**
	 * Parent MIDlet
	 */
	private PMIDlet pMIDlet;
	
	/**
	 * Bluetooth Library. Use to discover the control 
	 */
	private MBt mBt;
	
	/**
	 * Remote control found 
	 */
	private boolean controlFound;
	
	/**
	 * Use proxy client 
	 */
	private boolean useProxy;
	
	/**
	 * Show debug information 
	 */
	public static boolean debug;
	
	/**
	 * Create a Wii Remote object 
	 *
	 * @param pMIDlet Parent MIDlet 
	 * @param mBt Bluetooth library
	 */
	public MWiimote(PMIDlet pMIDlet, MBt mBt)
	{
		this(pMIDlet,mBt,false);
	}
	
	/**
	 * Create a Wii Remote object 
	 *
	 * @param pMIDlet Parent MIDlet 
	 * @param mBt Bluetooth library
	 * @param useProxy If true connects the client with a proxy 
	 */
	public MWiimote(PMIDlet pMIDlet, MBt mBt, boolean useProxy)
	{
		this.pMIDlet = pMIDlet;
		this.mBt = mBt;
		this.useProxy = useProxy;
	}	

	/**
	 * Discover the control 
	 */
	public void discover()
	{
		// Init found status 
		controlFound = false;
		
		// If proxy is use, discover proxy service 
		if(useProxy)
			mBt.discoverServices(MWiimoteProxy.UUID);
		else
			// Discover the devices
			mBt.discoverDevices();
	}
	
	/**
	 * Listen other library events 
	 *
	 * @param library Library object 
	 * @param event Event identifier 
	 * @param data Data send by the event 
	 */
	public void libraryEvent(Object library, int event, Object data)
	{
		// Check a Bluetooth discover event 
		if(library instanceof MBt)
		{
			switch(event)
			{
				// If a device is discovered, check if is a Wiimote  
				case MBt.EVENT_DISCOVER_DEVICE : 
					checkDevice((MDevice) data);
					break;					
					
				// If the discover finished, check if any control 
				// was found 
				case MBt.EVENT_DISCOVER_DEVICE_COMPLETED :
					if(!controlFound && !useProxy)
						enqueueLibraryEvent(this,
							EVENT_CONTROL_NOT_FOUND,null);
					break;
					
				// Check the proxy search  
				case MBt.EVENT_DISCOVER_SERVICE_COMPLETED :
					checkProxy((MService[]) data);
					break;					
			}	
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
		// If a midlet was registered
		// fire the event
		if(pMIDlet != null)
			pMIDlet.enqueueLibraryEvent(obj,event,data);
	}		
	
	/**
	 * Check if the device is a Wiimote 
	 */
	private void checkDevice(MDevice device)
	{
		// Get device name 
		String name = device.name();
		 
		// Check device name
		// If device name is Nintendo, store the device  
		if(name.equals("Nintendo RVL-CNT-01"))
		{
			// A control was found 
			controlFound = true;

			// Create the control
			MWiiControl control = new MWiiControl(this,device);
			
			// Fire control found event 
			enqueueLibraryEvent(this,EVENT_CONTROL_FOUND,control);
		}
	}
	
	/**
	 * Check the service proxy 
	 *
	 * @param services The services found
	 */
	private void checkProxy(MService[] services)
	{
		if(services.length != 0)
		{
			// A control was found 
			controlFound = true;

			// Create the control connected to the proxy 
			MWiiControl control = 
				new MWiiControl(this,services[0]);
			
			// Fire control found event 
			enqueueLibraryEvent(this,EVENT_CONTROL_FOUND,control);			
		}
		else
			// Not proxy found 
			enqueueLibraryEvent(this,EVENT_CONTROL_NOT_FOUND,null);					
	}
	
	/**
	 * Return a string representation of the object 
	 *
	 * @return A string representation of the object
	 */
	public String toString()
	{
		return "MWiimote"; 
	} 			
}
