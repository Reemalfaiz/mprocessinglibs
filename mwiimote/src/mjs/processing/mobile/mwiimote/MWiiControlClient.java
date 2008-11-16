/*

	MWiimote - Wii Remote Library for Mobile Processing

	Copyright (c) 2008 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mwiimote;

import javax.bluetooth.L2CAPConnection;

/**
 * Remote client for the control   
 */
class MWiiControlClient
{
	/**
	 * Proxy 
	 */	
	private MWiimoteProxy mWiimoteProxy;
	
	/**
	 * Local control 
	 */
	private MWiiControl mWiiControl;
	
	/**
	 * Current connection 
	 */
	private L2CAPConnection connection;

	/**
	 * Reader buffer  
	 */
	private byte[] buffer;	
	
	/**
	 * Client running 
	 */
	private boolean running;
	
	/**
	 * Counts how many packages send the control. Use to control the amount of 
	 * packages send to the client  
	 */
	private int packetCounter;
	
	/**
	 * Create a wiimote client for the control specified
	 *
	 * @param mWiimoteProxy Proxy of the control 
	 * @param mWiiControl Control to send data
	 * @param connection current connection with the client 
	 */
	public MWiiControlClient(MWiimoteProxy mWiimoteProxy, 
		MWiiControl mWiiControl, L2CAPConnection connection)
	{
		this.mWiimoteProxy = mWiimoteProxy;
		this.mWiiControl = mWiiControl;
		this.connection = connection;
		
		// Set client of the control
		if(mWiiControl != null) 
			mWiiControl.setClient(this);
		
		// Create buffers 
		buffer = new byte[512];
				
		// Start the connection reader 
		startReader();
	} 		
	
	/**
	 * Start the data reader 
	 */
	private void startReader()
	{
		// If already running do nothing 
		if(running)
			return;	
	
		// Change state 
		running = true;
		
		// Start input on other thread
		new Thread()
		{
			public void run()
			{			
				try
				{
					while(running)
					{		
						// Read data			
						int numBytes = connection.receive(buffer);						
												
						// Send data to the real control
						if(numBytes > 0)
						{
							// Copy the buffer content 
							byte[] data = new byte[numBytes];
							System.arraycopy(buffer,0,data,0,numBytes);
							
							// Fire data available  
							mWiimoteProxy.enqueueLibraryEvent(
								mWiimoteProxy,MWiimoteProxy.EVENT_DATA_READ,
								null);							
													
							// Send the data to the control
							if(mWiiControl != null)
								mWiiControl.send(data);
						}
					}

					// Close the connection 					
					connection.close();
				}
				catch(Exception e)
				{
					// Fire disconnected event 
					mWiimoteProxy.enqueueLibraryEvent(mWiimoteProxy,
						MWiimoteProxy.EVENT_CLIENT_DISCONNECTED,null);				
				
					// Fire error 
					mWiimoteProxy.enqueueLibraryEvent(mWiimoteProxy,
						MWiimote.EVENT_ERROR,e.getMessage());
						
					// Stop client  
					stop();
				}		
			}
		}.start();			
	}
	
	/**
	 * Send the data specified to the client 
	 *
	 * @param data Data to send 
	 */
	protected void send(byte[] data)
	{	
		try
		{
			// Check type of packet 
			if(data[1] == MWiiControl.DATA_INPUT_CORE_BUTTONS_ACCELEROMETER)
			{
				// Count packet 
				packetCounter++;
								
				// Check if the packet can be send
				// if not continue with the next one  
				if(packetCounter % mWiimoteProxy.discardPackets() != 0)
					return;
			}
		
			// Check if a connection is available and send the data 
			if(connection != null)		
				connection.send(data);
		}
		catch(Exception e)
		{
			// Fire error 
			mWiimoteProxy.enqueueLibraryEvent(mWiimoteProxy,
				MWiimote.EVENT_ERROR,e.getMessage());
		}		
	}
	
	/**
	 * Stop proxy 
	 */
	public void stop()
	{
		// If server not running, do nothing 
		if(!running)
			return;
			
		// Stop reader 
		running = false;
		
		try
		{
			// Close control connection 
			if(mWiiControl != null)
				mWiiControl.close();
		
			// Close connections 
			if(connection != null)
				connection.close();
				
			// Fire disconnected event 
			mWiimoteProxy.enqueueLibraryEvent(mWiimoteProxy,
				MWiimoteProxy.EVENT_CLIENT_DISCONNECTED,null);
		}
		catch(Exception e)
		{
			// Fire error 
			mWiimoteProxy.enqueueLibraryEvent(mWiimoteProxy,
				MWiimote.EVENT_ERROR,e.getMessage());
		}	
	}	
}
