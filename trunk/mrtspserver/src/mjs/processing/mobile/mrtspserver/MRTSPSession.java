/*

	MRTSPServer - RTSP Server for Mobile Processing

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

package mjs.processing.mobile.mrtspserver;

import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;

import processing.core.PMIDlet;

/**
 * RTSP Server 
 */
class MRTSPSession
{
	/**
	 * Session ID
	 */
	private int id;	
	
	/**
	 * The server port 
	 */
	private int serverPort;
	
	/**
	 * The client address 
	 */
	private String clientAddress;
	
	/**
	 * The client port  
	 */
	private int clientPort;
	
	/**
	 * Create a RTSP session 
	 *
	 * @param id the session identifier
	 * @param serverPort the sever Port
	 * @param clientAddres Client address 
	 * @param clientPort the client Port
	 */
	public MRTSPSession(int id, int serverPort, 
		String clientAddress, int clientPort)
	{
		this.id = id;
		this.clientAddress = clientAddress;
		this.clientPort = clientPort;
		this.serverPort = serverPort;
	}
	
	/**
	 * Return the session identifier
	 *
	 * @return session id
	 */
	public int getId()
	{
		return id;
	} 

	/**
	 * Return the server port 
	 *
	 * @return server port 
	 */
	public int getServerPort()
	{
		return serverPort;
	}
	
	/**
	 * Return the client port 
	 *
	 * @return client port 
	 */
	public int getClientPort()
	{
		return clientPort;
	}
	
	/**
	 * Return the client address
	 *
	 * @return the client address
	 */
	public String getClientAddress()
	{
		return clientAddress;
	} 

}