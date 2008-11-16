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

import java.io.InputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.util.Vector;

import javax.bluetooth.UUID;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import mjs.processing.mobile.mbt.MBt;

import processing.core.PMIDlet;

/**
 * Audio and Video server for Mobile Processing
 */
public class MMediaServer implements Runnable
{
	/**
	 * Get channels request 
	 */
	public final static int GET_CHANNELS = 1;

	/**
	 * Get file request 
	 */
	public final static int GET_FILE = 2;
	
	/**
	 * Get default file request 
	 */
	public final static int GET_DEFAULT_FILE = 3;
	
	/**
	 * Close the connection  
	 */
	public final static int CLOSE_CONNECTION = 4;	

	/**
	 * Get files of channel request 
	 */
	public final static int GET_FILES = 5;
		
	/**
	 * Error event
	 */
	public final static int EVENT_ERROR = 1;
	
	/**
	 * Client Available Event 
	 */
	public final static int EVENT_CLIENT_AVAILABLE = 2; 

	/**
	 * Server started Event 
	 */
	public final static int EVENT_SERVER_STARTED = 4;
	
	/**
	 * Server stopped Event 
	 */
	public final static int EVENT_SERVER_STOPPED = 5;
	
 	/**
	 * Client Dispatched event  
	 */
	public final static int EVENT_CLIENT_DISPATCHED = 3; 
	
	/**
	 * Bluetooth MMediaServer UUID number 
	 */
	public final static long UUID_NUMBER = 0x1789L;
	
	/**
	 * Parent MIDlet 
	 */
	private PMIDlet pMIDlet;
	
	/**
	 * Bluetooth library
	 */
	private MBt mBt;	
	
	/**
	 * Server thread to listen clients
	 */
	private Thread thread;
	
	/**
	 * The server is running ?
	 */
	private boolean running;
	
	/**
	 * Channels names 
	 */
	protected String[] channels;
	
	/**
	 * File list 
	 */
	protected String[] files;
	
	/**
	 * Connection notifier for the server 
	 */
	private StreamConnectionNotifier notifier;
	
	/**
	 * Create a media server 
	 *
	 * @param pMIDlet Parent MIDlet
  	 * @param mBt Bluetooth library
	 */	
	public MMediaServer(PMIDlet pMIDlet, MBt mBt)
	{
		this.pMIDlet = pMIDlet;
		this.mBt = mBt;		
	}

	/**
	 * Start the server 
	 */	
	public void start()
	{
		// Load the channels info 
		loadChannelsInfo();
	
		// If the server is running return 
		if(thread != null)
			return;

		// Set running like true 
		running = true;		
			
		// Create a new thread and start listen clients 
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Stop the server 
	 */	
	public void stop()
	{
		try
		{
			// Stop listening and close the server
			if(notifier != null)
				notifier.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
		// Stop the execution 
		running = false;
		
		// Send event 
		enqueueLibraryEvent(this,EVENT_SERVER_STOPPED,null);		
	}
	
	/**
	 * Create the connection and listen clients 
	 */
	public void run()
	{
		try
		{
			// Create the uuid of the service               
            UUID uuid = new UUID(UUID_NUMBER);

			// Create the url of the server            		
			StringBuffer url = new StringBuffer();
			url.append("btspp://localhost:");
			url.append(uuid.toString());
			url.append(";name=MMediaServer");
			
			// Open the connection
			notifier = (StreamConnectionNotifier) 
				Connector.open(url.toString());
				
			// Send event 
			enqueueLibraryEvent(this,EVENT_SERVER_STARTED,null);				

			// Wait and dispatch client while the server id running
			while(running)
			{				
				// Wait for a client and open a connection 
				StreamConnection connection = 
					(StreamConnection) notifier.acceptAndOpen();					
				
				// Handle the client in other thread
				dispatchClient(connection);
			}
			
			// Stop listening and close the server
			notifier.close();
		}
		catch(Exception e)
		{
			// Send event error 
			enqueueLibraryEvent(this,EVENT_ERROR,e.getMessage());
		}
	}
	
	/**
	 * Dispatch the client request  
	 *
	 * @param connection Current connection with the client
	 */
	private void dispatchClient(final StreamConnection connection)
	{
		// Create a thread for each client 
		new Thread()
		{
			public void run()
			{
				try
				{
					// Send the response to the client
					handleRequest(connection);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					
					// Send event error 
					enqueueLibraryEvent(
						MMediaServer.this,EVENT_ERROR,e.getMessage());
				}
			}
		}.start();
	}

	/**
	 * Handle the client request 
	 *
	 * @param connection Current connection with the client
	 */	 
	private void handleRequest(StreamConnection connection) throws Exception
	{
		// Send client available event 
		enqueueLibraryEvent(this,EVENT_CLIENT_AVAILABLE,null);
	
		// Open the streams with the client 
		DataInputStream input = connection.openDataInputStream();
		DataOutputStream output = connection.openDataOutputStream();

		// Open a permanent link with the client 
		boolean open = true;		
		
		// While the client is connected
		// response the client requests 
		while(open)
		{
			// Read the request 
			int request = input.readInt();

			// Dispatch the request 
			switch(request)
			{
				case GET_CHANNELS : sendChannels(output); break;
				case GET_DEFAULT_FILE : sendDefaultFile(output); break;
				case GET_FILE : sendFile(input,output); break;
				case GET_FILES : sendChannelFiles(input,output); break;
				case CLOSE_CONNECTION : open = false; break;
			}
		}
		
		// Close the streams 
		input.close();
		output.close();	
		
		// Close the connection 
		connection.close();
		
		// Send client available event 
		enqueueLibraryEvent(this,EVENT_CLIENT_DISPATCHED,null);		
	}

	/**
	 * Dispatch the channels request 
	 *
	 * @param output Strem to send the response
	 */		
	private void sendChannels(DataOutputStream output) throws Exception
	{
		// Write the number of channels 
		output.writeInt(channels.length);
		
		// Write each channel name 
		for(int i=0; i<channels.length; i++)
			output.writeUTF(channels[i]);
	}
	
	/**
	 * Return the files of a channel 
	 *
	 * @param channelName Name of channel
	 * @return array with file in the channel 
	 */
	private String[] getChannelsFiles(String channelName)
	{
		// Create an empty array of file names 
		Vector fileNames = new Vector();
		
		// Check if a file name of the channel is on the 
		// file name : /channelName/fileName
		for(int i=0; i<files.length; i++)
			if(files[i].indexOf(channelName) == 1)
				fileNames.addElement(files[i]);
				
		// Copy the list on the array 
		String[] array = new String[fileNames.size()];
		fileNames.copyInto(array);
		
		// Return the array 
		return array;
	}	
	
	/**
	 * Dispatch the channels files request 
	 *
	 * @param input Strem to read the filename
	 * @param output Strem to send the response
	 */		
	private void sendChannelFiles(DataInputStream input, 
		DataOutputStream output) throws Exception
	{
		// Read the channel name 
		String channelName = input.readUTF();
		
		// Get the files of a channel 
		String[] fileNames = getChannelsFiles(channelName);
		
		// Write the number of files 
		output.writeInt(fileNames.length);
		
		// Write each file name 
		for(int i=0; i<fileNames.length; i++)
			output.writeUTF(fileNames[i]);
	}	
	
	/**
	 * Dispatch the default file request 
	 *
	 * @param output Strem to send the response
	 */		
	private void sendDefaultFile(DataOutputStream output) throws Exception
	{
		// Send the first file in the list 
		sendFile(output,files[0]);
	}
	
	/**
	 * Dispatch the file request 
	 *
	 * @param input Strem to read the filename 
	 * @param output Strem to send the response
	 */		
	private void sendFile(DataInputStream input,
		DataOutputStream output) throws Exception
	{
		// Read the name of the file 		
		String fileName = input.readUTF();
		
		// Send the file with the read name 
		sendFile(output,fileName);
	}

	/**
	 * Dispatch the file request with the specified name 
	 *
	 * @param output Strem to send the response
	 * @param fileName Name of the file to send 
	 */		
	private void sendFile(DataOutputStream output, String fileName)
		throws Exception
	{
		// Remove the channel info 
		int index = fileName.lastIndexOf('/');
		fileName = fileName.substring(index);
		
		// Write the content type 
		String contentType = getContentType(fileName);
		output.writeUTF(contentType);
		
		// Open the file to send 						
		InputStream file = openFile(fileName);
		
		// Create the buffer to read the file 
		int numBytes;
		byte[] buffer = new byte[512];
		
		// Read the file by chunks and send it to the client 
		while((numBytes = file.read(buffer)) != -1)
		{
			// Write the chunk size and the chunk
			output.writeInt(numBytes);
			output.write(buffer,0,numBytes);
		}
		
		// No more bytes availables
		output.writeInt(-1);
			
		// Flush content 
		output.flush();
			
		// Close the file 
		file.close();
	}
	
	/**
	 * Check if the server is running 
	 *
	 * @return true if the server is running, false otherwise
	 */
	public boolean running()
	{
		return running;
	}
	
	/**
	 * Return the media channels
	 *
	 * @return channels names
	 */
	public String[] channels()
	{
		return channels;
	}
	
	/**
	 * Return the list of videos on a channel 
	 *
	 * @param channelName name of the channel 
	 * @return list of videos 
	 */
	public String[] files(String channelName)
	{
		// Create a vector to store the file names 
		Vector channelFiles = new Vector();
		
		// Check if the file begins with the channel name 
		// and add to the vector 
		for(int i=0; i<files.length; i++)
			if(files[i].indexOf(channelName) == 1)
				channelFiles.addElement(files[i]);
				
		// Copy the vector into an array 
		String[] array = new String[channelFiles.size()];
		channelFiles.copyInto(array);
				
		// Return the array with the name of the files 
		return array;
	}
	
	/**
	 * Load the channel info 
	 */
	public void loadChannelsInfo()
	{
		// Load the channels configuration file 
		channels = loadStrings("channels.txt");
		
		// Check if the info is available
		if(channels.length == 0)
			throw new RuntimeException("Unable to load channels info");
		
		// Load the video information 
		files = loadStrings("files.txt");
		
		// Check if the info is available
		if(files.length == 0)
			throw new RuntimeException("Unable to load channels files");		
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
	 * Load strings according platform 
	 *
	 * @param fileName File name 
	 */
	protected String[] loadStrings(String fileName)
	{
		return pMIDlet.loadStrings(fileName);
	}
	
	/**
	 * Open the file to send 
	 *
	 * @param fileName name of the file to send
	 * @return stream to read the file 
	 */
	protected InputStream openFile(String fileName)
	{
		// Open the file like a resource 
		return this.getClass().getResourceAsStream(fileName);
	}
	
	/**
	 * Return the content type of the file 
	 *
	 * @param fileName Name of the file 
	 * @return Content type 
	 */
	public String getContentType(String fileName)
	{
		// lowercase to compare 
		fileName = fileName.toLowerCase();
		
		// Check extensions 
		if(fileName.endsWith(".mpg") || fileName.endsWith(".mpeg"))
			return "video/mpeg";

		if(fileName.endsWith(".3gp"))
			return "video/3gpp";
			
		// No content type recognize 
		return null;	
	} 
}