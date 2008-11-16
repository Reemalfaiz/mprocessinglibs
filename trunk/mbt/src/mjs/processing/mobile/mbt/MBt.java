/*

	MBt - Bluetooth Library for Mobile Processing

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

package mjs.processing.mobile.mbt;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

import mjs.microburst.DeviceDiscoverer;
import mjs.microburst.ServiceDiscoverer;

import processing.core.PMIDlet;

/**
 * This class provides the primary interface for discovering and establishing
 * a Bluetooth network connection. 
 */
public class MBt implements DiscoveryListener
{
	/**
	 * A device was discovered
	 */	 
	public static final int EVENT_DISCOVER_DEVICE = 1;
	
	/**
	 * The discover device process is completed
	 */	 
    public static final int EVENT_DISCOVER_DEVICE_COMPLETED = 2;
    
    /**
     * A service was discovered
     */
    public static final int EVENT_DISCOVER_SERVICE = 3;
    
    /**
     * The discover service is completed
     */
    public static final int EVENT_DISCOVER_SERVICE_COMPLETED = 4;
    
 	/**
	 * Event fired when an error is produced.
	 * The data object will be a string with the message.
	 */
	public final static int EVENT_ERROR = 5;

	/**
	 * The Mobile Processing MIDlet
	 */
	private PMIDlet pMIDlet;

	/**
	 * Device discoverer
	 */	
	private DeviceDiscoverer deviceDiscoverer;
	
	/**
	 * Service discoverer
	 */
	private ServiceDiscoverer serviceDiscoverer;
	
	/**
	 * The Local device
	 */
	private LocalDevice localDevice;
		
	/**
	 * This class provides the primary interface for discovering and 
	 * establishing a Bluetooth network connection. 
	 *
	 * @param pMIDlet Parent MIDlet
	 */
	public MBt(PMIDlet pMIDlet)
	{
		this.pMIDlet = pMIDlet;
		
		try
		{
			// Get local device and make discoverable
			localDevice = LocalDevice.getLocalDevice();
			localDevice.setDiscoverable(DiscoveryAgent.GIAC);		
		}
		catch(BluetoothStateException e)
		{
			reportError(e);
		}
	}
	
	/**
	 * Return the device discoverer
	 *
	 * @return The device discoverer
	 */
	private DeviceDiscoverer getDeviceDiscovever()
	{
		// If the device discoverer is not created yet, create one 
		if(deviceDiscoverer == null)
		{
			deviceDiscoverer = new DeviceDiscoverer();
			deviceDiscoverer.setDiscoveryListener(this);
		}
		
		// Return the device discoverer
		return deviceDiscoverer;
	}
	
	/**
	 * Return the service discoverer
	 *
	 * @return the service discoverer
	 */
	private ServiceDiscoverer getServiceDiscoverer()
	{
		// If the device discoverer is not created yet, create one 
		if(serviceDiscoverer == null)
		{
			serviceDiscoverer = new ServiceDiscoverer();
			serviceDiscoverer.setDiscoveryListener(this);
		}
		
		// Return the device discoverer
		return serviceDiscoverer;
	}
	
	/**
	 * Discover all the near bluetooth devices
	 */	
	public void discoverDevices()
	{
		new Thread()
		{
			public void run()
			{
				// Get the near devices in a new thread
				getNearDevices();
			}
		}.start();
	}
	
	/**
	 * Discover the services with the uuid number specified
	 *
	 * @param uuidNumber The uuid number
	 */
	public void discoverServices(long uuidNumber)
	{	
		discoverServices(new UUID(uuidNumber));
	}
	
	/**
	 * Discover the services with the uuid specified
	 *
	 * @param uuidString The uuid string
	 */
	public void discoverServices(String uuidString)
	{
		discoverServices(new UUID(uuidString,false));
	}
	
	/**
	 * Discover the services with the uuid specified
	 *
	 * @param uuid The uuid
	 */
	private void discoverServices(UUID uuid)
	{
		UUID[] uuidSet = { uuid };
		discoverServices(uuidSet);		
	}
	
	/**
	 * Discover the services with the set of uuids specified
	 *
	 * @param uuidSet Array with the service uuids to search
	 */	
	private void discoverServices(final UUID[] uuidSet)
	{
		new Thread()
		{
			public void run()
			{
				// Get the near devices
				MDevice[] devices = getNearDevices();		
				
				// Discover the services in the devices 
				discoverServices(devices,uuidSet);
			}
		}.start();		
	}
	
	/**
	 * Discover the services in the devices specified with the uuuids 
	 * specified
	 *
	 * @param devices List of devices 
	 * @param uuidSet List with the service uuids to search
	 */	
	private void discoverServices(final MDevice[] devices, 
		final UUID[] uuidSet)
	{
		new Thread()
		{
			public void run()
			{
				// Get the services from the devices with the uuids
				getServices(devices,uuidSet);
			}
		}.start();		
	}
	
	/**
	 * Return the services in the devices specified with the uuuids 
	 * specified
	 *
	 * @param devices List of devices 
	 * @param uuidSet List with the service uuids to search
	 * @return The services discovered
	 */	
	private MService[] getServices(MDevice[] mDevices, UUID[] uuidSet)
	{
		try
		{
			// Create the devices list with the device specified
			RemoteDevice[] devices = new RemoteDevice[mDevices.length];
			
			// Get native devices
			for(int i=0; i<devices.length; i++)
				devices[i] = mDevices[i].getRemoteDevice();
		
			// Search all services with the uuid specified and default attributes
			ServiceDiscoverer serviceDiscoverer = getServiceDiscoverer();
			ServiceRecord[] services = 
				serviceDiscoverer.discover(devices,uuidSet,null);
						
			// Build service list for event callback
   			MService[] mServices = new MService[services.length];
        
        	// Create a MService for each service discovered
	        for(int i = 0; i < services.length; i++)
	        {
				MDevice mDevice = new MDevice(services[i].getHostDevice());
				mServices[i] = new MService(mDevice,services[i]);
			}
						
			// Fire the event 
			enqueueLibraryEvent(this,
				EVENT_DISCOVER_SERVICE_COMPLETED,mServices);
				
			// Return the services
			return mServices;
		}
		catch(Exception e)
		{
			reportError(e);
		}		
		
		// Return null like signal of error
		return null;
	}
	
	/**
	 * Returns an array with all the devices near to the local device.
	 * 
	 * @return array with all the near devices
	 */
	private MDevice[] getNearDevices()
	{	
		try
		{
			// Get device discoverer and remote devices
			DeviceDiscoverer deviceDiscoverer = getDeviceDiscovever();			
			RemoteDevice[] devicesDiscovered = deviceDiscoverer.discover(null);
	
			// Create the device array and return
			MDevice[] devices = new MDevice[devicesDiscovered.length];		
			for(int i = 0; i < devices.length; i++)
				devices[i] = new MDevice(devicesDiscovered[i]);
			
			// Fire the event
			enqueueLibraryEvent(this,EVENT_DISCOVER_DEVICE_COMPLETED,devices);
		
			return devices;
		}
		catch(Exception e)
		{
			reportError(e);
		}
		
		// Return null like signal of error
		return null;
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
	
	public void deviceDiscovered(RemoteDevice remoteDevice, 
		DeviceClass deviceClass)
	{
		// Create the device 
		MDevice mDevice = new MDevice(remoteDevice);
		
		// Fire the event
		enqueueLibraryEvent(this,EVENT_DISCOVER_DEVICE,mDevice);
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
		// Build service list for event callback
		MService[] mServices = new MService[servRecord.length];
		        
        for(int i = 0; i < servRecord.length; i++)
        {
	        MDevice mDevice = new MDevice(servRecord[i].getHostDevice());
			mServices[i] = new MService(mDevice,servRecord[i]);
		}
	
		// Fire the event 
		enqueueLibraryEvent(this,EVENT_DISCOVER_SERVICE,mServices);
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
	 * Report and error to the midlet
	 *
	 * @param e An exception
	 */
	private void reportError(Exception e)
	{
		enqueueLibraryEvent(this,MBt.EVENT_ERROR,e.getMessage());
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
}
