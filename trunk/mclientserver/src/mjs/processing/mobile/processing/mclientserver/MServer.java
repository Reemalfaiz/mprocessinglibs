/*

	MClientService - Cliente Server Utility for Mobile Processing

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

package mjs.processing.mobile.mclientserver;

import java.util.*;
import javax.microedition.io.*;

/**
 * MServer encapsulation for Client-Server implementation.
 *
 * This class provides methods to create server objects from services based on
 * sockets, bluetooth and others.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 */

public class MServer implements Runnable
{
	/**
	 * The current clients 
	 */
	 
	private Vector clients;

	/**
	 * The connection used to accept clients.
	 */
	
	protected StreamConnectionNotifier notifier;

	/**
	 * The thread to accept clients.
	 */

	private Thread thread;

	/**
	 * Status of the server.
	 */

	private boolean running;
	
	/**
	 * Server Listener
	 *
	 * @since 0.3
	 */
	private MServerListener	listener;

	/**
	 * Creates a Server with the notifier specified. The notifier can be a serversocket, 
	 * bluetooth or other.
	 *
	 * @param notifier The connection notifier.
	 */
	
	public MServer(StreamConnectionNotifier notifier)
	{
		this();
		
		this.notifier = notifier;
	}
	
	/**
	 * Create an empty server
	 *
	 * @since 0.4
	 */
	protected MServer()
	{
		clients = new Vector();
	}
	
	/**
	 * Set the notifier for the server
	 *
	 * @param notifier Notifier server
	 *
	 * @since 0.4
	 */
	protected void setNotifier(StreamConnectionNotifier notifier)
	{
		this.notifier = notifier;
	}

	/**
	 * Close the actual connection with a client.
	 *
	 * @param client client to disconnect.
	 */

	public void disconnect(MClient client)
	{
		// Remove from the current clients and stop the client access
		clients.removeElement(client);
		client.stop();
	}

	/**
	 * Returns the first client with available data to read, null if no client is sending data
	 *
	 * @param client sending data or null if nobody is sending data.
	 */

	public MClient available()
	{
		// get the first client via available data to read
		for(Enumeration e = clients.elements(); e.hasMoreElements(); )
		{	
			MClient client = (MClient) e.nextElement();

			if(client.available() > 0)
				return client;
		}

		// No clients
		return null;
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
				MClient client = (MClient) e.nextElement();
				client.stop();
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Writes the specified byte (the low eight bits of the argument b) to all clients. 
	 * 
	 * @param value the byte value to be written.
	 */
	 
	public void write(int value)
	{
		// Get all clients and send data 
		for(Enumeration e = clients.elements(); e.hasMoreElements(); )
		{	
			MClient client = (MClient) e.nextElement();
			client.write(value);
		}
	}

	/**
	 * Writes an char value to all clients. 
	 * 
	 * @param value the char value to be written.
	 */

	public void write(char value)
	{
		// Get all clients and send data 
		for(Enumeration e = clients.elements(); e.hasMoreElements(); )
		{	
			MClient client = (MClient) e.nextElement();
			client.write(value);
		}
	}		

	/**
	 * Writes to all clientes the bytes in array bytes. 
	 *
	 * @param bytes the data.
	 */

	public void write(byte[] bytes)
	{
		for(Enumeration e = clients.elements(); e.hasMoreElements(); )
		{	
			MClient client = (MClient) e.nextElement();
			client.write(bytes);
		}
	}

	/**
	 * Writes the String especified to all clients
	 * 
	 * @param value the String value to be written.
	 */	

	public void write(String value)
	{
		for(Enumeration e = clients.elements(); e.hasMoreElements(); )
		{	
			MClient client = (MClient) e.nextElement();
			client.write(value);
		}
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
				StreamConnection streamConnection = (StreamConnection) notifier.acceptAndOpen();

				// Create client and adds to the current clients vector
				MClient client = new MClient(streamConnection);
				clients.addElement(client);
				
				// A new client is available
				if(listener != null)
					listener.clientAvailable(this,client);
			}
		}
		catch(Exception e)	
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Set the listener
	 *
	 * @param listener The listener
	 *
	 * @since 0.3
	 */
	 
	public void setServerListener(MServerListener listener)
	{
		this.listener = listener;
	}
}
