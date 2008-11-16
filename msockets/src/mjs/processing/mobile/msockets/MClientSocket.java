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
import javax.microedition.io.SocketConnection;

import mjs.processing.mobile.mclientserver.MClient;

import processing.core.PException;
import processing.core.PMIDlet;

public class MClientSocket extends MClient
{
    /**
     * Parent MIDlet 
     */
    private PMIDlet pMIDlet;
    
    /**
     * Create a client to the host in the port specified 
     *
     * @param pMIDlet Parent midlet
     * @param host The host name 
     * @param port socket port
     */
	public MClientSocket(PMIDlet pMIDlet, String host, int port)
	{
		// Set parent midlet
		this.pMIDlet = pMIDlet;
		
		try
		{
			// Create connector name 
			String name = "socket://" + host + ":" + port;
			
			// Open the connection
			SocketConnection connection = 
				(SocketConnection) Connector.open(name);
				
			// Set the connection for this client
			setConnection(connection);
		}
		catch(Exception e)
		{
			throw new PException(e);
		}
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
			address = ((SocketConnection) connection).getLocalAddress();
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
			port = ((SocketConnection) connection).getLocalPort();
		}
		catch(Exception e)
		{		
		}
		
		// Return the address
		return port;
	}
	
	/**
	 * Return the remote address to which the socket is bound. The address can 
	 * be either the remote host name or the IP address(if available).
	 *
	 * @return the remote address to which the socket is bound.
	 */
	public String remoteAddress()
	{
		// The address value
		String address = null;
		
		try
		{
			// Get the address value
			address = ((SocketConnection) connection).getAddress();
		}
		catch(Exception e)
		{		
		}
		
		// Return the address
		return address;
	}

	/**
	 * Returns the remote port to which this socket is bound.
	 *
	 * @return the remote port number to which this socket is connected, -1 if 
	 * 		the connection is closed 
	 */
	public int remotePort()
	{
		// The port value
		int port = -1;
		
		try
		{
			// Get the port value
			port = ((SocketConnection) connection).getPort();
		}
		catch(Exception e)
		{		
		}
		
		// Return the address
		return port;
	}	
}