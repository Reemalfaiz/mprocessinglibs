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
 * RTSP Stream 
 */
abstract class MRTSPStream extends Thread
{
	/**
	 * Sequence Number 
	 */
	private int sequenceNumber;
	
	/**
	 * Timestamp
	 */
	private int timestamp;
	
	/**
	 * Data Buffer 
	 */
	protected byte[] buffer;
	
	/**
	 * Client session 
	 */
	private MRTSPSession session;
	
	/**
	 * Connection 
	 */
	private DatagramConnection connection;

	/**
	 * Datagram 
	 */	
	private Datagram datagram;
	
	/**
	 * Packet size 
	 */
	protected int packetSize;
	
	/**
	 * Header size 
	 */
	protected int headerSize;	
	
	/**
	 * Create a content stream 
	 *
	 * @param session current session 
	 */ 	
	public MRTSPStream(MRTSPSession session, int packetSize)
	{
		// Current session 
		this.session = session;
		this.packetSize = packetSize;
		
		// Create the data buffer 
		buffer = new byte[512];
		
		// The header is always 12 size 
		headerSize = 12;
	}

	/**
	 * Deliver the file content 
	 */	
	public void run()
	{
		try
		{
			if(connection == null)
			{
				// Create the url connection 
				StringBuffer sb = new StringBuffer();
				sb.append("datagram://:");
				sb.append(session.getServerPort());
				
				// Get url 
				String url = sb.toString();
				
				// Create connection and datagram 
				connection = (DatagramConnection) Connector.open(url);
				datagram = connection.newDatagram(buffer,buffer.length);
				
				// Set datagram client address 
				datagram.setAddress("datagram://" 
					+ session.getClientAddress() + ":" 
					+ session.getClientPort());	
				
				// Send data while is available 
				while(true)
				{
					// Update header information 
					updateHeader();
					
					// Load a data chunk 
					int numBytes = loadData();
					 
					// If no more bytes, stop sending data 
					if(numBytes == -1)
						break;
						
					// Change datagram size 
					datagram.setLength(numBytes + headerSize);						
					
					// Send datagram 
					connection.send(datagram);
				}
				
				// Close the connection 
				connection.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the packet header 
	 */
	private void updateHeader()
	{
		// Version and other things  
		buffer[0] = (byte) 128;
		
		// Payload 
		buffer[1] = (byte) 96;
		
		// Sequence package number 
		buffer[2] = (byte) (sequenceNumber >> 8);
		buffer[3] = (byte)  sequenceNumber;
		sequenceNumber++;
		
		// Timestamp 
		buffer[4] = (byte) (timestamp >> 24);
		buffer[5] = (byte) (timestamp >> 16);
		buffer[6] = (byte) (timestamp >>  8);
		buffer[7] = (byte)  timestamp;
		
		// Current time 
		timestamp += packetSize;
				
		// SSRC 
		buffer[ 8] = (byte) 0;
		buffer[ 9] = (byte) 0;
		buffer[10] = (byte) 0;
		buffer[11] = (byte) 0;		
	}
	
	/**
	 * Update the packet content 
	 */
	protected abstract int loadData() throws Exception;	
}