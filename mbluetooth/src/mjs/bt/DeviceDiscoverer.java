/*
 * Bluetooth Library
 *
 * http://mjs.darkgreenmedia.com
 *
 * Copyright (C) 2006 Mary Jane Soft. All rights reserved.
 * Mary Jane Soft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * $Id$
 */

package mjs.bt;

import java.util.Vector;

import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.ServiceRecord;

/**
 * This class allows the device discovery through Bluetooth
 */

public class DeviceDiscoverer implements DiscoveryListener
{
	/** 
	 * Name of the device to seek
	 */
	 
	private String seekDeviceName;
	
	/**
	 * List of the remote devices found
	 */
	
	private Vector remoteDevices;
	
	/**
	 * External listener
	 */
	 
	private DiscoveryListener discoveryListener;
	
	/**
	 * Create the device discoverer
	 */
	
	public DeviceDiscoverer()
	{
		// Create the list of the remote devices
		remoteDevices = new Vector();
	}
	
	/**
	 * This method return the list of the remote devices discovered 
	 * using bluetooth search
	 *
	 * @param seekDeviceName Name of the device to seek, null to discover 
	 *			all the devices
	 * @return List of the devices discovered
	 */
	
	public synchronized RemoteDevice[] discover(String seekDeviceName) throws Exception
	{
		// Update the device to seek
		this.seekDeviceName = seekDeviceName;
		
		// Clear the list
		remoteDevices.removeAllElements();
		
		// Get the discovery agent
		LocalDevice localDevice = LocalDevice.getLocalDevice();
		DiscoveryAgent discoveryAgent = localDevice.getDiscoveryAgent();
			
		// Begin to discover of devices
		discoveryAgent.startInquiry(DiscoveryAgent.GIAC,this);
		
		// Wait until the inquiry is completed
		wait();
		
		// Return the list of the devices, like an array
		RemoteDevice[] array = new RemoteDevice[remoteDevices.size()];
		remoteDevices.copyInto(array);
		
		return array;
	}
	
	/**
	 * Called when a device is found during an inquiry. 
	 *
	 * An inquiry searches for devices that are discoverable. The same device 
	 * may be returned multiple times.
	 *
	 * Simply add the device to the current list of the devices
	 *
	 * @param remoteDevice the device that was found during the inquiry
	 * @param deviceClass the service classes
	 */
	
	public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass)
	{
		// Call external listener
		if(discoveryListener != null)
			discoveryListener.deviceDiscovered(remoteDevice,deviceClass);
	
		try
		{
			// Add the device if not specific device name is specified
			if(seekDeviceName == null)
				remoteDevices.addElement(remoteDevice);
			else
			{
				// Retrive the name of the device 
				String remoteDeviceName = remoteDevice.getFriendlyName(true);
				
				// If the device name is equal to the device specified add 
				if(seekDeviceName.equals(remoteDeviceName))
					remoteDevices.addElement(remoteDevice);
			}
		}
		catch(Exception e)
		{
			// If any error occur simply show the stack trace
			e.printStackTrace();
    	}
	}	
	
	/**
	 * Called when an inquiry is completed.
	 *
	 * Notify the discover waiting for the device that the list is 
	 * available
	 *
	 * @param discType the type of request that was completed
	 */
	
	public synchronized void inquiryCompleted(int discType)
	{
		// Call external listener
		if(discoveryListener != null)
			discoveryListener.inquiryCompleted(discType);

		// Stop waiting
		notifyAll();			
	}	
	
	/**
	 * Called when service(s) are found during a service search.
	 *
	 * This method is not used
	 * 
	 * @param transID the transaction ID of the service search that is 
	 *			posting the result
	 * @param service a list of services found during the search request
	 */
	 
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord)
	{
	}

	/**
	 * Called when a service search is completed or was terminated because 
	 * of an error.	
	 *
	 * This method is not used
	 *
	 * @param transID the transaction ID of the service search that is 
	 *			posting the result
	 * @param respCode - the response code that indicates the status of 
	 *			the transaction
	 */
	 
	public void serviceSearchCompleted(int transID, int respCode)
	{
	}		
	
	/**
	 * Set the external discovery listener
	 *
	 * @param listener External discovery listener
	 */
	
	public void setDiscoveryListener(DiscoveryListener discoveryListener)
	{
		this.discoveryListener = discoveryListener;
	}
}
