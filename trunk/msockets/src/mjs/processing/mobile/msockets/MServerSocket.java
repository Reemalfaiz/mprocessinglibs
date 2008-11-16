/*

	MSockets - Sockets Library for Mobile Processing

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

package mjs.processing.mobile.msockets;

import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.StreamConnectionNotifier;

import mjs.processing.mobile.mclientserver.MClient;
import mjs.processing.mobile.mclientserver.MServer;
import mjs.processing.mobile.mclientserver.MServerListener;

import processing.core.PException;
import processing.core.PMIDlet;

public class MServerSocket extends MServer implements MServerListener
{
    /**
     * A client is connected event
     *
	 * @value 5
     */     
    public static final int EVENT_CLIENT_CONNECTED = 5;
    
    /**
     * Parent MIDlet 
     */
    private PMIDlet pMIDlet;
    
    /**
     * Create a socket server in the specified port 
     *
     * @param pMIDlet Parent midlet
     * @param port socket port
     */
	public MServerSocket(PMIDlet pMIDlet, int port)
	{
		// Set parent midlet
		this.pMIDlet = pMIDlet;
		
		try
		{
			// Create connector name 
			String name = "socket://:" + port;
			
			// Open the notifier
			StreamConnectionNotifier notifier = 
				(StreamConnectionNotifier) Connector.open(name);
				
			// Update the notifier for the server
			setNotifier(notifier);
			setServerListener(this);
			start();							
		}
		catch(Exception e)
		{
			throw new PException(e);
		}
	}
	
	/**
	 * Listen the server connections
	 *
	 * @param server
	 * @param client
	 */	 
	public void clientAvailable(MServer server, MClient client)
	{	
		// Fire the event if the midlet was specified
		if(pMIDlet != null)
			pMIDlet.enqueueLibraryEvent(this,EVENT_CLIENT_CONNECTED,client);
	}
	
	/**
	 * Return the local address to which the socket is bound.
	 *
	 * The host address(IP number) that can be used to connect to this end of 
	 * the socket connection from an external system. Since IP addresses may be
	 * dynamically assigned, a remote application will need to be robust in the
	 * face of IP number reasssignment.
	 *
	 * @return the local address to which the socket is bound, null if the 
	 *    connection is closed
	 */
	public String address()
	{
		// The address value
		String address = null;
		
		try
		{
			// Get the address value
			address = ((ServerSocketConnection) notifier).getLocalAddress();
		}
		catch(Exception e)
		{		
		}
		
		// Return the address
		return address;
	}

	/**
	 * Returns the local port to which this socket is bound.
	 *
	 * @return the local port number to which this socket is connected, -1 if 
	 * 		the connection is closed 
	 */
	public int port()
	{
		// The port value
		int port = -1;
		
		try
		{
			// Get the port value
			port = ((ServerSocketConnection) notifier).getLocalPort();
		}
		catch(Exception e)
		{		
		}
		
		// Return the address
		return port;
	}	
}