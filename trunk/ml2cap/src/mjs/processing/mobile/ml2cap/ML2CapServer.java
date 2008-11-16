/*

	ML2Cap - L2CAP Library for Mobile Processing

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

package mjs.processing.mobile.ml2cap;

import java.util.Enumeration;
import java.util.Vector;

import javax.bluetooth.UUID;
import javax.bluetooth.L2CAPConnection;
import javax.bluetooth.L2CAPConnectionNotifier;

import javax.microedition.io.Connector;

import processing.core.PException;
import processing.core.PMIDlet;

/**
 * L2CAP Server 
 */
public class ML2CapServer implements Runnable 
{
    /**
     * A client is connected event
     *
	 * @value 5
     */     
    public static final int EVENT_CLIENT_CONNECTED = 5;
    
	/**
	 * The current clients 
	 */
	private Vector clients;    

	/**
	 * The thread to accept clients.
	 */
	private Thread thread;
	
	/**
	 * Status of the server.
	 */
	private boolean running;	

	/**
	 * Connection Notifier 
	 */
	private L2CAPConnectionNotifier notifier;
	
    /**
     * Parent MIDlet 
     */
    private PMIDlet pMIDlet;
    
	/**
	 * Create a L2CAP server
	 * 
	 * @param pMIDlet parent midlet 
	 * @param uuid uuid of the service 
	 * @param name Name of the service 
	 * @param receiveMTU the maximum number of bytes that can be read 
	 * @param transmitMTU the maximum number of bytes that can be sent
	 */ 
	public ML2CapServer(PMIDlet pMIDlet, UUID uuid, String name, 
		int receiveMTU, int transmitMTU)
	{
		// Set parent midlet
		this.pMIDlet = pMIDlet;
		
		// Create empty vector of clients 
		clients = new Vector();
		
		try
		{
			// Creates a URL to a service at btl2cap://localhost:uuid
			StringBuffer url = new StringBuffer();
			url.append("btl2cap://localhost:");
			url.append(uuid.toString());
			
			// If the service has a name
			if(name != null)
			{
				url.append(";name=");
				url.append(name);
			}
			
			// If receive MTU is specified 
			if(receiveMTU != -1)
			{
				url.append(";ReceiveMTU=");
				url.append(Integer.toString(receiveMTU));
			}			

			// If transmit MTU is specified 
			if(transmitMTU != -1)
			{
				url.append(";TransmitMTU=");
				url.append(Integer.toString(transmitMTU));
			}

			// Open a connection notifier to accept clients
			notifier = 
				(L2CAPConnectionNotifier) Connector.open(url.toString());

			// Start the service 					
			start();
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
		}
	}

	/**
	 * Create a L2CAP server
	 * 
	 * @param pMIDlet parent midlet 
	 * @param uuid uuid of the service 
	 * @param name Name of the service 
	 */ 
	public ML2CapServer(PMIDlet pMIDlet, UUID uuid, String name)
	{
		this(pMIDlet,uuid,name,-1,-1);
	}
	
    /**
	 * Create a L2CAP server
	 * 
	 * @param pMIDlet parent midlet 
	 * @param uuidNumber uuid number of the service 
	 * @param name Name of the service
  	 * @param receiveMTU the maximum number of bytes that can be read 
	 * @param transmitMTU the maximum number of bytes that can be sent
     */
	public ML2CapServer(PMIDlet pMIDlet, long uuidNumber, String name,
		int receiveMTU, int transmitMTU)	
	{
		this(pMIDlet,new UUID(uuidNumber),name,receiveMTU,transmitMTU);
	}
	
    /**
	 * Create a L2CAP server
	 * 
	 * @param pMIDlet parent midlet 
	 * @param uuidNumber uuid number of the service 
	 * @param name Name of the service 
     */
	public ML2CapServer(PMIDlet pMIDlet, long uuidNumber, String name)
	{
		this(pMIDlet,new UUID(uuidNumber),name,-1,-1);
	}	
	
	/**
	 * Start running the server if is not running.
	 */
	public void start()
	{
		// The server is running
		running = true;

		// If the thread is over, create a new thread
		if(thread == null)
			thread = new Thread(this);

		// Start the thread
		thread.start();
	}

	/**
	 * Accept clients while is running.
	 */
	public void run()
	{
		try
		{
			// While is running
			while(running)
			{
				// Accept the client
				L2CAPConnection connection = 
					(L2CAPConnection) notifier.acceptAndOpen();

				// Create client and fire event 
				ML2CapClient client = new ML2CapClient(pMIDlet,connection);
				
				// Add the client to the list 
				clients.addElement(client);
				
				// Fire event 				
				enqueueLibraryEvent(this,EVENT_CLIENT_CONNECTED,client);
			}
		}
		catch(Exception e)	
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
		}
	}
	
	/**
	 * Stop the server.
	 *
	 * Stop all the clients connected to the server.
	 */
	public void stop()
	{
		try
		{
			// Stop thread and close connection
			running = false;
			notifier.close();
			
			// Close all the clients connected
			for(Enumeration e = clients.elements(); e.hasMoreElements(); )
			{	
				ML2CapClient client = (ML2CapClient) e.nextElement();
				client.stop();
			}			
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
		}
	}
	
	/**
	 * Returns the first client with available data to read, 
	 * null if no client is sending data
	 *
	 * @return client sending data or null if nobody is sending data.
	 */
	public ML2CapClient available()
	{
		// get the first client via available data to read
		for(Enumeration e = clients.elements(); e.hasMoreElements(); )
		{	
			// Get client 
			ML2CapClient client = (ML2CapClient) e.nextElement();

			// Check if the client is ready 
			if(client.ready())
				return client;
		}

		// No clients
		return null;
	}
	
	/**
	 * Writes the specified byte (the low eight bits of the argument b) to the
	 * packet 
	 * 
	 * @param value the byte value to be written.
	 */
	public void write(int value)
	{
		// Get all clients and send data 
		for(Enumeration e = clients.elements(); e.hasMoreElements(); )
		{	
			ML2CapClient client = (ML2CapClient) e.nextElement();
			client.write(value);
		}
	}

	/**
	 * Writes the specified boolean value
	 * 
	 * @param value the boolean value to be written.
	 */
	public void writeBoolean(boolean value)
	{
		// Get all clients and send data 
		for(Enumeration e = clients.elements(); e.hasMoreElements(); )
		{	
			ML2CapClient client = (ML2CapClient) e.nextElement();
			client.writeBoolean(value);
		}
	}

	/**
	 * Writes the specified string like bytes
	 * 
	 * @param s a string
	 */
	public void writeBytes(String s)
	{
		write(s.getBytes());		
	}
	
	/**
	 * Writes an char value to the output stream. 
	 * 
	 * @param value the char value to be written.
	 */
	public void writeChar(int value)
	{
		// Get all clients and send data 
		for(Enumeration e = clients.elements(); e.hasMoreElements(); )
		{	
			ML2CapClient client = (ML2CapClient) e.nextElement();
			client.writeChar(value);
		}
	}		

	/**
	 * Writes an integer value to the output stream. 
	 * 
	 * @param value the integer value to be written.
	 */
	public void writeInt(int value)
	{
		// Get all clients and send data 
		for(Enumeration e = clients.elements(); e.hasMoreElements(); )
		{	
			ML2CapClient client = (ML2CapClient) e.nextElement();
			client.writeInt(value);
		}
	}		
	
	/**
	 * Writes an char value to the output stream. 
	 * 
	 * @param value the char value to be written.
	 */
	public void write(char value)
	{
		writeChar(value);
	}	

	/**
	 * Writes to the output stream all the bytes in array b. If b is null, a 
	 * NullPointerException is thrown. 
	 * If b.length is zero, then no bytes are written. Otherwise, the byte 
	 * b[0] is written first, then b[1], and so on; the last byte written is 
	 * b[b.length-1].
	 * 
	 * @param bytes the data.
	 */
	public void write(byte[] bytes)
	{
		// Get all clients and send data 
		for(Enumeration e = clients.elements(); e.hasMoreElements(); )
		{	
			ML2CapClient client = (ML2CapClient) e.nextElement();
			client.write(bytes);
		}	
	}

	/**
	 * Writes two bytes of length information to the output stream, followed by
	 * the Java modified UTF representation of every character in the string s.
	 * If s is null, a NullPointerException is thrown. 
	 *
	 * Each character in the string s  is converted to a group of one, two, or 
	 * three bytes, depending on the value of the character.
	 * 
	 * @param value the string value to be written.
	 */
	public void write(String value)
	{
		writeUTF(value);
	}

	/**
	 * Writes two bytes of length information to the output stream, followed by
	 * the Java modified UTF representation of every character in the string s.
	 * If s is null, a NullPointerException is thrown. 
	 *
	 * Each character in the string s  is converted to a group of one, two, or 
	 * three bytes, depending on the value of the character.
	 * 
	 * @param value the string value to be written.
	 */
	public void writeUTF(String value)
	{
		// Get all clients and send data 
		for(Enumeration e = clients.elements(); e.hasMoreElements(); )
		{	
			ML2CapClient client = (ML2CapClient) e.nextElement();
			client.writeUTF(value);
		}	
	}	
	
	/**
	 * Close the actual connection with a client.
	 *
	 * @param client client to disconnect.
	 */
	public void disconnect(ML2CapClient client)
	{
		// Remove from the current clients and stop the client access
		clients.removeElement(client);
		client.stop();
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
		if(pMIDlet != null)
			pMIDlet.enqueueLibraryEvent(obj,event,data);
	}	
}
