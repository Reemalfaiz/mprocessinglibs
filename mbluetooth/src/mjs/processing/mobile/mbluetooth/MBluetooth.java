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

import javax.microedition.io.StreamConnectionNotifier;
import javax.microedition.io.Connector;
import javax.microedition.io.Connection;

import javax.bluetooth.UUID;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;

import mjs.bt.DeviceDiscoverer;
import mjs.bt.ServiceDiscoverer;

import mjs.processing.mobile.mclientserver.MClient;
import mjs.processing.mobile.mclientserver.MServer;
import mjs.processing.mobile.mclientserver.MServerListener;

/**
 * Basic class to access Bluetooth features.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 */

public class MBluetooth implements DiscoveryListener, MServerListener
{
	/**
	 * A device was discovered
	 *
	 * @since 0.4
	 */	 
	public static final int EVENT_DISCOVER_DEVICE = 1;
	
	/**
	 * The discover device process is completed
	 *
	 * @since 0.4
	 */
	 
    public static final int EVENT_DISCOVER_DEVICE_COMPLETED = 2;
    
    /**
     * A service was discovered
	 *
	 * @since 0.4
     */
    public static final int EVENT_DISCOVER_SERVICE = 3;
    
    /**
     * The discover service is completed
	 *
	 * @since 0.4
     */
    public static final int EVENT_DISCOVER_SERVICE_COMPLETED = 4;
    
    /**
     * A client is connected
	 *
	 * @since 0.4
     */
     
    public static final int EVENT_CLIENT_CONNECTED = 5;

	/**
	 * Universal Unique Identifier for Mobile Processing Service
	 */
	 
	private final static UUID MOBILE_PROCESSING_UUID = new UUID(0xCAFE2005L);

	/**
	 * Instance of the class. Based on a singleton pattern
	 */
	
	protected static MBluetooth bluetoothInstance;

	/**
	 * Universal Unique Identifier for this bluetooth service.
	 */

	private static UUID serviceUUID;

	/**
	 * Reference to the localDevice.
	 */

	private static LocalDevice localDevice;

	/**
	 * Agent to discover near devices and services.
	 */
	
	private static DiscoveryAgent discoveryAgent;
	
	/**
	 * Current Server
	 */
	 
	private MServer server;

	/**
	 * A private constructor that removes the possibility to create Bluetooth 
	 * objects.
	 * 
	 * Makes the local devices discoverable, gets the discovered agent and 
	 * creates the  array to storage near devices.
	 */

	protected MBluetooth()
	{
		try
		{
			// Get local device and make discoverable
			localDevice = LocalDevice.getLocalDevice();
			localDevice.setDiscoverable(DiscoveryAgent.GIAC);

			// Get the discovered agent
			discoveryAgent = localDevice.getDiscoveryAgent();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());		
		}
	}
	
	/**
	 * This class provides the primary interface for discovering and 
	 * establishing a Bluetooth network connection. 
	 *
	 * @param parent the sketch
	 * @param uuid a 32-character hexadecimal value representing a 128-bit 
	 * 			universally unique identifier for this service
	 */
	 
	public MBluetooth(Object parent, String uuid)
	{
		getInstance(parent,uuid);
	}

	/**
	 * This class provides the primary interface for discovering and 
	 * establishing a Bluetooth network connection. 
	 *
	 * @param parent the sketch
	 */
	 
	public MBluetooth(Object parent)
	{
		this(parent,MOBILE_PROCESSING_UUID.toString());
	}
	
	/**
	 * This methods returns the singleton instance of the Bluetooth.
	 *
	 * @return The bluetooth instance.
	 */

	public static MBluetooth getInstance()
	{
		// return the instance
		return getInstance(null,null);
	}
	
	/**
	 * This methods returns the singleton instance of the Bluetooth.
	 *
	 * @param The sketch object.
	 * @param uuid a 32-character hexadecimal value representing a 128-bit 
	 * 			universally unique identifier for this service	 
	 *
	 * @return The bluetooth instance.
	 */

	public static MBluetooth getInstance(Object sketch, String uuid)
	{
		// if bluetooth has not been created, create one		
		if(bluetoothInstance == null)
		{
			// If the device is a midp device use the mobile version
			// else use the processing version
			
			if(System.getProperty("microedition.profiles") != null)
				bluetoothInstance = new MBluetoothM(sketch);
		}
		
		// Set the service uuid
		if(uuid != null)
			bluetoothInstance.serviceUUID = new UUID(uuid,false);
		else
			bluetoothInstance.serviceUUID = MOBILE_PROCESSING_UUID;

		// return the instance
		return bluetoothInstance;
	}	

	/**
	 * Start the bluetooth library making the device discoverable
	 */
	 
	public static void start()
	{
		getInstance();
	}

	/**
	 * Returns the friendly name of the local device
	 * 
	 * @return friendly name of local device
	 */
	 
	public static String deviceName()
	{
		return getInstance().getLocalDeviceName();
	}

	/**
	 * Returns an array with all the devices near to the local device.
	 * 
	 * @return array with all the near devices
	 */
	 
	public static MDevice[] nearDevices()
	{
		return getInstance().getNearDevices();
	}

	/**
	 * Creates a client with the Mobile Processing Service if is not possible return null
	 *
	 * @return a client with the default service or null if service is not available.
	 */	

	public static MClient selectDefaultService()
	{
		return getInstance()._selectDefaultService();
	}

	/**
	 * Start the default Mobile Processing Service.
	 *
	 * @return a server ready to accept clients and send data
	 */

	public static MServer startDefaultService()
	{
		return getInstance()._startDefaultService();
	}

	/**
	 * Creates a client with the the uuid specified if is not possible return null
	 *
	 * @param uuidNumber uuid as long
	 * @return a client with the default service or null if service is not available.
	 */	

	public static MClient selectService(long uuidNumber)
	{
		return getInstance()._selectService(uuidNumber);
	}

	/**
	 * Start a service identified by the uuid specified.
	 *
	 * @param uuidNumber uuid as long	 
	 * @return a server ready to accept clients and send data
	 */

	public static MServer startService(long uuidNumber)
	{
		return getInstance()._startService(uuidNumber);
	}
	
	/**
	 * Returns the friendly name of the local device.
	 *
	 * @return friendly name of local device	 
	 */

	private String getLocalDeviceName()
	{
		return localDevice.getFriendlyName();
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
			DeviceDiscoverer deviceDiscoverer = new DeviceDiscoverer();
			deviceDiscoverer.setDiscoveryListener(this);
			RemoteDevice[] devicesDiscovered = deviceDiscoverer.discover(null);
	
			// Create the device array and return
			MDevice[] devices = new MDevice[devicesDiscovered.length];
		
			for(int i = 0; i < devices.length; i++)
				devices[i] = new MDevice(devicesDiscovered[i]);
			
			// Fire the event
			bluetoothInstance.enqueueLibraryEvent(
				this, EVENT_DISCOVER_DEVICE_COMPLETED, devices);
		
			return devices;
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Creates a client with the Mobile Processing Service if is not possible return null
	 *
	 * @return a client with the default service or null if service is not available.
	 */	

	private MClient _selectDefaultService()
	{
		return _selectService(MOBILE_PROCESSING_UUID);	
	}

	/**
	 * Start the default Mobile Processing Service.
	 *
	 * @return a server ready to accept clients and send data
	 */

	private MServer _startDefaultService()
	{
		return _startService(MOBILE_PROCESSING_UUID,null);
	}

	/**
	 * Creates a client with the the uuid specified if is not possible return null
	 *
	 * @param uuidNumber uuid as long
	 * @return a client with the default service or null if service is not available.
	 */	

	private MClient _selectService(long uuidNumber)
	{
		UUID uuid = new UUID(uuidNumber);
		return _selectService(uuid);
	}

	/**
	 * Start a service identified by the uuid specified.
	 *
	 * @param uuidNumber uuid as long	 
	 * @return a server ready to accept clients and send data
	 */

	public MServer _startService(long uuidNumber)
	{
		UUID uuid = new UUID(uuidNumber);
		return _startService(uuid,null);
	}

	/**
	 * Creates a client with the Mobile Processing Service if is not possible return null
	 *
	 * @return a client with the default service or null if service is not available.
	 */	

	private MClient _selectService(UUID uuid)
	{
		try
		{
			// Attempts to locate a service that contains uuid in the 
			// ServiceClassIDList of its service record.
			// How the service is selected if there are multiple services with 
			// uuid and which devices to search is implementation dependent.
			
			String connectionString = 
				discoveryAgent.selectService(uuid,
						ServiceRecord.NOAUTHENTICATE_NOENCRYPT,
						false);

			// if service is available 			
			if(connectionString != null)
			{
				// Open a connection and creates a client within
				Connection connection = Connector.open(connectionString);
				return new MClient(connection);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}

		// Can't found service
		return null;
	}

	/**
	 * Start the default Mobile Processing Service.
	 *
	 * @param uuid Bluetooth ID
	 * @param name Service Name
	 *
	 * @return a server ready to accept clients and send data
	 */

	private MServer _startService(UUID uuid, String name)
	{
		try
		{
			// Creates a URL to a service at btspp://localhost:uuid
			StringBuffer url = new StringBuffer();
			url.append("btspp://localhost:");
			url.append(uuid.toString());
			
			// If the service has a name
			if(name != null)
			{
				url.append(";name=");
				url.append(name);
			}

			// Open a connection notifier to accept clients
			StreamConnectionNotifier notifier = 
					(StreamConnectionNotifier) Connector.open(url.toString());
					
			// Set some attributes
			ServiceRecord record = localDevice.getRecord(notifier);
			
			// Set availability to fully available
            record.setAttributeValue(0x0008, new DataElement(DataElement.U_INT_1, 0xFF));
                
            // Set device class to telephony
            // Not implemented on Avetana Library
            // record.setDeviceServiceClasses(0x400000);

			// Start the server with the bluetooth notifier connection
			server = new MServer(notifier);
			server.setServerListener(this);
			server.start();
			
			return server;
		}
		catch(Exception e)	
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());		
		}
	}
	
	/**
	 * Called when a device is found during an inquiry.
	 * An inquiry searches for devices that are discoverable.
	 * The same device may be returned multiple times.
	 *
	 * @param remoteDevice the device that was found during the inquiry
	 * @param deviceClass the service classes, major device class, and minor 
	 *			device class of the remote device
	 */

	public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) 
	{
		// Create the device 
		MDevice mDevice = new MDevice(remoteDevice);
		
		// Fire the event
		bluetoothInstance.enqueueLibraryEvent(this, EVENT_DISCOVER_DEVICE, mDevice);
	}

	/**
	 * Called when an inquiry is completed.
	 *
	 * The discType will be INQUIRY_COMPLETED if the inquiry ended normally or
	 * INQUIRY_TERMINATED if the inquiry was canceled by a call to 
	 * DiscoveryAgent.cancelInquiry(). The discType  will be INQUIRY_ERROR if 
	 * an error occurred while processing the inquiry causing the inquiry to
	 * end abnormally.
	 *
	 * @param discType the type of request that was completed; either 
	 *			INQUIRY_COMPLETED, INQUIRY_TERMINATED, or INQUIRY_ERROR
	 */

	public void inquiryCompleted(int discType)
	{
		synchronized(this)
		{
			// Notify that near devices was discovered
			notify();
		}
	}

	/**
	 * Called when service(s) are found during a service search.
	 *
	 * @param transID the transaction ID of the service search that is posting
	 *			the result
	 * @param service a list of services found during the search request	 
	 */

	public void servicesDiscovered(int transID, ServiceRecord[] serviceRecord)
	{
		// Build service list for event callback
        MService[] services = new MService[serviceRecord.length];
        
        for(int i = 0; i < serviceRecord.length; i++)
        {
        	// Create the device
        	MDevice device = new MDevice(serviceRecord[i].getHostDevice());
			services[i] = new MService(device, serviceRecord[i]);
		}
			
		// Fire the event 
		bluetoothInstance.enqueueLibraryEvent(this,
				EVENT_DISCOVER_SERVICE,services);
	}

	/**
	 * Called when a service search is completed or was terminated because of
	 * an error. 
	 *
	 * Legal status values in the respCode argument include 
	 * SERVICE_SEARCH_COMPLETED, SERVICE_SEARCH_TERMINATED, 
	 * SERVICE_SEARCH_ERROR, SERVICE_SEARCH_NO_RECORDS and 
	 * SERVICE_SEARCH_DEVICE_NOT_REACHABLE. 
	 *
	 * @param transID the transaction ID identifying the request which 
	 * initiated the service search
	 *
	 * @param respCode the response code that indicates the status of the 
	 *			transaction
	 */

	public void serviceSearchCompleted(int transID, int respCode)
	{
	}
	
	/**
	 * Method used to fire the library events
	 *
	 * @param library Library object that fires the event
	 * @param event Event identifier
	 * @param data Data provide in the event
	 */
	 
	protected void enqueueLibraryEvent(Object library, int event, Object data)
	{
	}
	
	/**
	 * Searches for nearby Bluetooth devices sharing a serial port connection.
	 * This can include other mobile phones running a Mobile Processing sketch 
	 * or peripherals such as GPS receivers. This method returns immediately 
	 * and reports its results in the libraryEvent method.
	 * 
	 * @since 0.4
	 */
	 
	public void discover()
	{
		// Create a new thread to return immediately
		new Thread()
		{
			public void run()
			{
				getNearDevices();
			}
		}.start();
	}
	
	/**
	 * Search the services in the given device
	 *
	 * @param device remote devices to search the service
	 *
	 * @since 0.4
	 */
	 
	protected void searchServices(final MDevice device, final UUID[] uuidSet)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// Create the devices list with the device specified
					RemoteDevice[] devices = { device.getRemoteDevice() };
			
					// Create the service discoverer
					ServiceDiscoverer serviceDiscoverer = new ServiceDiscoverer();
					serviceDiscoverer.setDiscoveryListener(MBluetooth.this);
						
					// Set default service uuid to search
					UUID[] uuids = null;
										
					if(uuidSet == null)
						uuids = new UUID[] { new UUID(0x0100) };
					else
						uuids = uuidSet;
					
					// Search all services with the uuid specified and default attributes
					ServiceRecord[] serviceDiscovered = 
						serviceDiscoverer.discover(devices,uuids,null);
						
					// Build service list for event callback
        			MService[] services = new MService[serviceDiscovered.length];
        
			        for(int i = 0; i < serviceDiscovered.length; i++)
						services[i] = new MService(device, serviceDiscovered[i]);
						
					// Fire the event 
					bluetoothInstance.enqueueLibraryEvent(
						MBluetooth.this,EVENT_DISCOVER_SERVICE_COMPLETED,services);
				}
				catch(Exception e)
				{
					throw new RuntimeException(e.getMessage());
				}
			}
		}.start();
	}
	
	/**
	 * Starts sharing a serial port connection that other Bluetooth devices can
	 * discover. When another device connects, it will be reported in the 
	 * libraryEvent method.
	 *
	 * @param a name for this service
	 *
	 * @since 0.4
	 */
	 
	public void start(String name)
	{
		_startService(serviceUUID,name);
	}
	
	/**
	 * Stops sharing the Bluetooth serial port connection previously enabled 
	 * with start().
	 *
	 * @see start
	 *
	 * @since 0.4
	 */
	 
	public void stop()
	{
		// If there is a server active, close it
		if(server != null)
			server.stop();
	}
	
	/**
	 * Searches for nearby Bluetooth devices sharing a serial port connection 
	 * and advertising a service with the same uuid as this device. The uuid is
	 * set by default when the Bluetooth library is initialized or can be 
	 * custom set in the constructor of the Bluetooth library class. This 
	 * method returns immediately and reports its results in the libraryEvent 
	 * method. In the final event, EVENT_SERVICE_DISCOVER_COMPLETED, an array 
	 * of Service objects containing matching services on nearby devices is 
	 * returned.
	 *
	 * @since 0.4
	 */
	 
	public void find()
	{
		// Create a new thread to return immediately
		new Thread()
		{
			public void run()
			{
				// Get all the devices
				MDevice[] remoteDevice = getNearDevices();
				
				// Search for the common service
				for(int i = 0; i < remoteDevice.length; i++)
					searchServices(remoteDevice[i],new UUID[] { serviceUUID } );
			}
		}.start();		
	}	
	
	/**
	 * Listen the server connections
	 *
	 * @param server
	 * @param client
	 *
	 * @since 0.4
	 */
	 
	public void clientAvailable(MServer server, MClient client)
	{
		// Fire the event 
		bluetoothInstance.enqueueLibraryEvent(this,
				EVENT_CLIENT_CONNECTED,client);
	}
}
