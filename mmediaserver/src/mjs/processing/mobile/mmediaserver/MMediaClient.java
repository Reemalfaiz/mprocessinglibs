/*

	MMediaServer - Media Server for Mobile Processing

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

package mjs.processing.mobile.mmediaserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import javax.microedition.media.Manager;
import javax.microedition.media.Player;

import mjs.processing.mobile.mbt.MBt;
import mjs.processing.mobile.mbt.MService;

import processing.core.PMIDlet;

/**
 * Audio and Video client for Mobile Processing
 */
public class MMediaClient
{
	/**
	 * Error event
	 */
	public final static int EVENT_ERROR = 1;
	
	/**
	 * Available video event 
	 */
	public final static int EVENT_VIDEO_AVAILABLE = 2;
	
	/**
	 * Discover service event  
	 */
	public final static int EVENT_DISCOVER_SERVICE = 3;	

	/**
	 * Service Discovered event   
	 */
	public final static int EVENT_SERVICE_DISCOVERED = 4;
	
	/**
	 * Multiple Service Discovered event   
	 */
	public final static int EVENT_SERVICES_DISCOVERED = 5;
	
	/**
	 * Service not found event    
	 */
	public final static int EVENT_SERVICE_NOT_FOUND = 6;
	
	/**
	 * Connection Opened     
	 */
	public final static int EVENT_CONNECTION_OPENED = 7;
	
	/**
	 * Connection Opened     
	 */
	public final static int EVENT_CHANNELS_AVAILABLE = 8;
	
	/**
	 * Connection Closed
	 */
	public final static int EVENT_CONNECTION_CLOSED = 9;
	
	/**
	 * Channels file list available      
	 */
	public final static int EVENT_FILES_AVAILABLE = 10;
	
	/**
	 * File bytes readed 
	 */
	public final static int EVENT_FILE_PROGRESS = 11;			
	
	/**
	 * Parent MIDlet 
	 */	 
	private PMIDlet pMIDlet;
	
	/**
	 * Bluetooth library
	 */
	private MBt mBt;
	
	/**
	 * MediaServer Service
	 */
	private MService service;
		
	/**
	 * Current Connetion 
	 */ 
	private StreamConnection connection;
	
	/**
	 * Input Stream 
	 */
	private DataInputStream input;

	/**
	 * Output Stream 
	 */
	private DataOutputStream output;
	
	/**
	 * Create a media client 
	 *
	 * @param pMIDlet Parent MIDlet 
	 * @param mBt Bluetooth library
	 */	
	public MMediaClient(PMIDlet pMIDlet, MBt mBt)
	{
		this.pMIDlet = pMIDlet;
		this.mBt = mBt;		
	}
	
	/**
	 * Discover server 
	 */
	public void discoverServer()
	{
		// Send event 
		pMIDlet.enqueueLibraryEvent(this,EVENT_DISCOVER_SERVICE,null);
		
		// Discover services with the UUID specified 
		mBt.discoverServices(MMediaServer.UUID_NUMBER);
	}	

	/**
	 * Open the connection with the server 
	 */
	public void open()
	{
		// If no service, send error event 
		if(service == null)
			pMIDlet.enqueueLibraryEvent(
				this,EVENT_ERROR,"Not service to connect to");
			
		// Open the service with the current one 
		open(service);
	}
	
	/**
	 * Open the connection with the specified service
	 *
	 * @param service MediaServer service  
	 */
	public void open(final MService service)
	{
		// If the connection is open, do nothing 
		if(connection != null)
			return;
					
		// Update te service 
		this.service = service;			
		
		new Thread()
		{
			public void run()
			{
				try
				{
					// Get the url of the service 	
					String url = service.url();
			
					// Create the connection with the server 	
					connection = (StreamConnection) Connector.open(url);
				
					// Open the input and outpuut with the server 
					input = connection.openDataInputStream();
					output = connection.openDataOutputStream();
					
					// Send event 
					pMIDlet.enqueueLibraryEvent(
						MMediaClient.this,EVENT_CONNECTION_OPENED,service);					
				}
				catch(Exception e)
				{
					// Send event 
					pMIDlet.enqueueLibraryEvent(
						MMediaClient.this,EVENT_ERROR,e.getMessage());			
				}
			}
		}.start();					
	}
	
	/**
	 * Close the connection 
	 */
	public void close()
	{
		// If the connection is already close, do nothing 
		if(connection == null)
			return;
		
		new Thread()
		{
			public void run()
			{
				try
				{
					// Send close request 
					output.writeInt(MMediaServer.CLOSE_CONNECTION);
					
					// Close streams 
					output.close();
					input.close();
					
					// Close connection 
					connection.close();
					
					// Reset all connection attributes 
					output = null;
					input = null;
					connection = null;
					
					// Send event 
					pMIDlet.enqueueLibraryEvent(
						MMediaClient.this,EVENT_CONNECTION_CLOSED,service);					
				}
				catch(Exception e)
				{
					// Send event 
					pMIDlet.enqueueLibraryEvent(
						MMediaClient.this,EVENT_ERROR,e.getMessage());			
				}
			}
		}.start();	
	}
	
	/**
	 * Return the list of channels
	 *
	 * @return list of available channels, null if bo service is available  
	 */
	public void channels()
	{
		// If connection is not available return null 
		if(connection == null)
		{
			pMIDlet.enqueueLibraryEvent(
				this,EVENT_ERROR,"Connection not Open");
			return;
		}
		
		new Thread()
		{
			public void run()
			{
				try
				{
					// Send the request 
					output.writeInt(MMediaServer.GET_CHANNELS);
			
					// Read the number of channels 
					int numChannels = input.readInt();
					String[] channels = new String[numChannels];
			
					// Read the names of the channels 
					for(int i=0; i<numChannels; i++)					
						channels[i] = input.readUTF();
						
					// Send event 
					pMIDlet.enqueueLibraryEvent(
						MMediaClient.this,EVENT_CHANNELS_AVAILABLE,channels);						
				}
				catch(Exception e)
				{
					// Send event 
					pMIDlet.enqueueLibraryEvent(
						MMediaClient.this,EVENT_ERROR,e.getMessage());			
				}
			}
		}.start();
	}
	
	/**
	 * Load the list of files in a channel   
	 */
	public void channelFiles(final String channelName)
	{
		// If connection is not available return null 
		if(connection == null)
		{
			pMIDlet.enqueueLibraryEvent(
				this,EVENT_ERROR,"Connection not Open");
			return;
		}
		
		new Thread()
		{
			public void run()
			{
				try
				{
					// Send the request 
					output.writeInt(MMediaServer.GET_FILES);
					
					// Write the Channel name 
					output.writeUTF(channelName);
			
					// Read the number of files 
					int numFiles = input.readInt();
					String[] fileNames = new String[numFiles];
			
					// Read the names of each file 
					for(int i=0; i<numFiles; i++)					
						fileNames[i] = input.readUTF();
						
					// Send event 
					pMIDlet.enqueueLibraryEvent(
						MMediaClient.this,EVENT_FILES_AVAILABLE,fileNames);						
				}
				catch(Exception e)
				{
					// Send event 
					pMIDlet.enqueueLibraryEvent(
						MMediaClient.this,EVENT_ERROR,e.getMessage());			
				}
			}
		}.start();
	}	 
	
	/** 
	 * Load the default media file at the server  
	 */
	public void loadDefaultFile()
	{
		// If connection is not available send error  
		if(connection == null)
		{
			pMIDlet.enqueueLibraryEvent(
				this,EVENT_ERROR,"Connection not Open");
			return;
		}
		
		new Thread()
		{
			public void run()
			{
				try
				{
					// Send the request 
					output.writeInt(MMediaServer.GET_DEFAULT_FILE);
					
					// Create the player to the file 
					createPlayer();
				}
				catch(Exception e)
				{
					// Send event 
					pMIDlet.enqueueLibraryEvent(
						MMediaClient.this,EVENT_ERROR,e.getMessage());			
				}
			}
		}.start();
	}
	
	/** 
	 * Load the media file with the name specified 
	 *
	 * @param fileName file to load   
	 */
	public void loadFile(final String fileName)
	{
		// If connection is not available send error  
		if(connection == null)
		{
			pMIDlet.enqueueLibraryEvent(
				this,EVENT_ERROR,"Connection not Open");
			return;
		}
		
		new Thread()
		{
			public void run()
			{
				try
				{
					// Send the request 
					output.writeInt(MMediaServer.GET_FILE);
					
					// Write the file name  
					output.writeUTF(fileName);
					
					// Create the player to the file 
					createPlayer();
				}
				catch(Exception e)
				{
					// Send event 
					pMIDlet.enqueueLibraryEvent(
						MMediaClient.this,EVENT_ERROR,e.getMessage());			
				}
			}
		}.start();
	}	
	
	/**
	 * Listen the library events to discover the service 
	 *
	 * @param object library 
	 * @param event event type  
	 * @param data The data send by the library
	 */
	public void libraryEvent(Object library, int event, Object data)
	{
		// If the library is bluetooth and the service discover is 
		// complete open the connection with the server 
		if(library == mBt 
			&& event == MBt.EVENT_DISCOVER_SERVICE_COMPLETED)
		{
			// Obtain the service list 
			MService[] services = (MService[]) data;
			
			// If no server found 
			if(services.length == 0)
			{
				// Send event 
				pMIDlet.enqueueLibraryEvent(
					this,EVENT_SERVICE_NOT_FOUND,null);			
			}
			// Check if only one server is available
			else if(services.length == 1)
			{
				// Get the service  
				service = services[0];
			
				// Send event 
				pMIDlet.enqueueLibraryEvent(
					this,EVENT_SERVICE_DISCOVERED,service);				
			}
			else
			{
				// Send event 
				pMIDlet.enqueueLibraryEvent(
					this,EVENT_SERVICES_DISCOVERED,services);				
			}
		}
	}
	
	/**
	 * Creates the media player with the input stream  
	 */
	private void createPlayer()
	{
		try
		{
			// Read content type 
			String contentType = input.readUTF();
			
			// Create the wrapper for the file 
			MMediaFile file = new MMediaFile(this,input);
			
			// Create the media player with the content type 
			Player player = Manager.createPlayer(file,contentType);
			
			// Create a a video with the player
			MVideoClient video = new MVideoClient(pMIDlet,player);
			
			// Send available content   
			pMIDlet.enqueueLibraryEvent(this,EVENT_VIDEO_AVAILABLE,video);			
		}
		catch(Exception e)
		{
			// Send event error 
			pMIDlet.enqueueLibraryEvent(this,EVENT_ERROR,e.getMessage());
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
}