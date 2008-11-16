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

import javax.bluetooth.UUID;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.ServiceRecord;

/**
 * This class allows the service discovery through Bluetooth
 */

public class ServiceDiscoverer implements DiscoveryListener
{
	/**
	 * The services
	 */
	 
	private Vector services;
	
	/**
	 * External listener
	 */
	 
	private DiscoveryListener discoveryListener;	
	
	/**
	 * Create the service discoverer
	 */
	
	public ServiceDiscoverer()
	{
		// Create the urls list
		services = new Vector();
	}
	
	/**
	 * Called when a device is found during an inquiry. 
	 *
	 * An inquiry searches for devices that are discoverable. The same device 
	 * may be returned multiple times.
	 *
	 * This method is not used
	 *
	 * @param remoteDevice the device that was found during the inquiry
	 * @param deviceClass the service classes
	 */
		
	public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass cod)
	{
	}	
	
	/**
	 * Called when an inquiry is completed.
	 *
	 * This method is not used
	 *
	 * @param discType the type of request that was completed
	 */
		
	public void inquiryCompleted(int discType)
	{
		// Call external listener
		if(discoveryListener != null)
			discoveryListener.inquiryCompleted(discType);
	}
	
	/**
	 * Called when service(s) are found during a service search.
	 *
	 * This method grab the service url and adds to the list
	 * 
	 * @param transID the transaction ID of the service search that is 
	 *			posting the result
	 * @param service a list of services found during the search request
	 */		
	
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord)
	{
		// Call external listener
		if(discoveryListener != null)
			discoveryListener.servicesDiscovered(transID, servRecord);
	
		// For each service grab the url
		for(int i=0; i<servRecord.length; i++)
		{
			// Get the url of the service
			String connectionURL = servRecord[i].getConnectionURL(1,false);
			
			// If the url is not null add to the list
			if(connectionURL != null)
				services.addElement(servRecord[i]);	
		}			
	}
	
	/**
	 * Called when a service search is completed or was terminated because 
	 * of an error.	
	 *
	 * Notify the discover waiting for the service that the list is 
	 * available
	 *
	 * @param transID the transaction ID of the service search that is 
	 *			posting the result
	 * @param respCode - the response code that indicates the status of 
	 *			the transaction
	 */
	 	
	public synchronized void serviceSearchCompleted(int transID, int respCode)
	{
		// Call external listener
		if(discoveryListener != null)
			discoveryListener.serviceSearchCompleted(transID, respCode);
	
		// Stop waiting
		notifyAll();
	}
	
	/**
	 * Returns the services urls of the services availables in the given 
	 * device list with the specified UUIDs.
	 *
	 * If not uuidSet is given (null) all the services are search 
	 *
	 * @param devices List of the devices to perform the search
	 * @param uuidSet array with all the services uuids to search if null search for all services
	 * @param attrSet array with the attributes to retrive if null retrive name, id and protocol
	 */
	
	public ServiceRecord[] discover(RemoteDevice[] devices, UUID[] uuidSet, int[] attrSet) throws Exception
	{
		// Clear the urls list
		services.removeAllElements();
		
		// If the uuids list is null, check for all the services 
		// with the 256 UUID
		if(uuidSet == null)
		{
			UUID allServices = new UUID(256L);
			uuidSet = new UUID[] { allServices };
		}
		
		// Set the attributes to retrieve	
		// ServiceName. ServiceID, ProtocolDescriptorList
		if(attrSet == null)
			attrSet = new int[] { 0x0100, 0x0003, 0x0004 };
			
		// Get the discovery agent
		LocalDevice localDevice = LocalDevice.getLocalDevice();
	    DiscoveryAgent discoveryAgent = localDevice.getDiscoveryAgent();
	    
	    // For each device perform a search
   		for(int i = 0; i < devices.length; i++)
   		{
   			// Search the services inside the the remote device
			discoveryAgent.searchServices(attrSet,uuidSet,devices[i],this);
				
			// Wait until the searc is over to perform the next
			synchronized(this)
			{
				wait();
			}
		}

		// Return the list of urls, like an array
		ServiceRecord[] array = new ServiceRecord[services.size()];
		services.copyInto(array);
		
    	return array;
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
