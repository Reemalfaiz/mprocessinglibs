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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import javax.bluetooth.L2CAPConnection;

import javax.microedition.io.Connector;

import processing.core.PMIDlet;

/**
 * L2CAP Client 
 */
public class ML2CapClient
{
	/**
	 * Current connection 
	 */
	private L2CAPConnection connection;

	/**
	 * Array stream 
	 */
	private ByteArrayOutputStream arrayStream;
	
	/**
	 * Data to send 
	 */
	private DataOutputStream output;
	
	/**
	 * Autoflush 
	 */
	private boolean autoflush;
	
    /**
     * Parent MIDlet 
     */
    private PMIDlet pMIDlet;	
	
	/**
	 * Create a client with the connection specified 
	 *
	 * @param pMIDlet parent midlet 	 
	 * @param connection Current connection 
	 */
	public ML2CapClient(PMIDlet pMIDlet, L2CAPConnection connection)
	{
		this.pMIDlet = pMIDlet;
		this. connection = connection;
		
		// Create the output to send the data
		arrayStream = new ByteArrayOutputStream();
		output = new DataOutputStream(arrayStream);
	}
	
	/**
	 * Return autoflush configuration 
	 *
	 * @return true if each data is send, false if waits a flush call 
	 */
	public boolean autoflush()
	{
		return autoflush;
	}
	
	/**
	 * Updates the autoflush configuration 
	 *
	 * @param value true if each data is send, 
	 *		false if waits a flush call 
	 */
	public void autoflush(boolean value)
	{
		autoflush = value;
	}	
	
	/** 
	 * Creates a client with the url specified 
	 * 
	 * @param pMIDlet parent midlet	 
	 * @param url Service url
	 */
	public ML2CapClient(PMIDlet pMIDlet, String url)
	{
		this(pMIDlet,(L2CAPConnection) null);
		
		try
		{
			// Try to open the connection 
			connection = (L2CAPConnection) Connector.open(url);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * Requests that data be sent to the remote device.
	 *
	 * @param data data to be sent
	 */
	public void send(byte[] data)
	{
		try
		{
			// Send the data 
			connection.send(data);
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
		}
	}
	
	/**
	 * Reads a packet of data.
	 *
	 * @param inBuf byte array to store the received data
	 * @return the actual number of bytes read, -1 if error 
	 */
	public int receive(byte[] inBuf)
	{
		try
		{
			// Receive Data  
			return connection.receive(inBuf);
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
		}
		
		// Error 
		return -1;
	}
	
	/**
	 * Determines if there is a packet that can be read via a call to 
	 * receive(). If true, a call to receive() will not block the application.
	 *
	 * @return true if there is data to read; false if there is no data to read
	 */
	public boolean ready()
	{
		try
		{
			// Check if a packet is available   
			return connection.ready();
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
		}
		
		// Error 
		return false;	
	}
	
	/**
	 * Writes the specified byte (the low eight bits of the argument b) to the
	 * packet 
	 * 
	 * @param value the byte value to be written.
	 */
	public void write(int value)
	{
		try
		{
			output.write(value);
			
			// Check autoflush 
			if(autoflush)
				flush();
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
		}
	}

	/**
	 * Writes the specified boolean value
	 * 
	 * @param value the boolean value to be written.
	 */
	public void writeBoolean(boolean value)
	{
		try
		{
			output.writeBoolean(value);

			// Check autoflush 
			if(autoflush)
				flush();
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
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
		try
		{
			output.writeChar(value);

			// Check autoflush 
			if(autoflush)
				flush();
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
		}		
	}		

	/**
	 * Writes an integer value to the output stream. 
	 * 
	 * @param value the integer value to be written.
	 */
	public void writeInt(int value)
	{
		try
		{
			output.writeInt(value);

			// Check autoflush 
			if(autoflush)
				flush();
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
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
		try
		{
			output.write(bytes);

			// Check autoflush 
			if(autoflush)
				flush();
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
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
		try
		{
			output.writeUTF(value);		

			// Check autoflush 
			if(autoflush)
				flush();
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
		}	
	}
	
	/**
	 * Flushes this stream by writing any buffered output to the underlying 
	 * stream	
	 */
	public void flush()
	{
		try
		{
			// Check if the data size is not greater than MTU 
			if(arrayStream.size() < connection.getTransmitMTU())
			{
				// Get the data to send 
				byte[] data = arrayStream.toByteArray();
			
				// Reset current packet 
				arrayStream.reset();
			
				// Send the data 
				send(data);
			}
			// Fire and error  
			else		
				enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,
					"Data greater than MTU");
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
		}
	}
	
	/**
	 * Return the transmit MTU 
	 *
	 * @return max number of bytes to transmit 
	 */
	public int transmitMTU()
	{
		try
		{
			return connection.getTransmitMTU();
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
		}
		
		// Error 
		return -1;		
	}
	
	/**
	 * Return the receive MTU 
	 *
	 * @return max number of bytes to receive 
	 */
	public int receiveMTU()
	{
		try
		{
			return connection.getReceiveMTU();
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
		}
		
		// Error 
		return -1;		
	}			

	/**
	 * Stop the client disconnecting of the server
	 */
	public void stop()
	{
		try
		{
			// Close the connection
			if(connection != null)
				connection.close();
		}
		catch(Exception e)
		{
			// Fire event 
			enqueueLibraryEvent(this,ML2Cap.EVENT_ERROR,e.getMessage());
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
		if(pMIDlet != null)
			pMIDlet.enqueueLibraryEvent(obj,event,data);
	} 
}
