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
import javax.bluetooth.L2CAPConnectionNotifier;
import javax.bluetooth.UUID;

import javax.microedition.io.Connector;

import processing.core.PMIDlet;

/**
 * Proxy for MWiimote 
 */
public class MWiimoteProxy
{
	/**
	 * Server UUID 
	 */
	public final static int UUID = 0xBEE1;
	
	/**
	 * Event Client Connected 
	 */	 
	public static final int EVENT_CLIENT_CONNECTED = 2;	

	/**
	 * Event Data Read
	 */	 
	public static final int EVENT_DATA_READ = 3;	

	/**
	 * Event Client Connected 
	 */	 
	public static final int EVENT_CLIENT_DISCONNECTED = 4;	
	
	/**
	 * Parent MIDlet
	 */
	private PMIDlet pMIDlet;	
	
	/**
	 * Local control 
	 */
	private MWiiControl mWiiControl;
	
	/**
	 * Connection Notifier 
	 */
	private L2CAPConnectionNotifier notifier;

	/**
	 * Proxy running 
	 */
	private boolean running;
	
	/**
	 * Number of packages to  
	 */
	private int packetsAmount;	
	
	/**
	 * Create a wiimote proxy
	 *
	 * @param pMIDlet Parent midlet
	 */
	public MWiimoteProxy(PMIDlet pMIDlet)
	{
		this.pMIDlet = pMIDlet;
		
		// Set discard packet amount to 10 
		packetsAmount = 5;		
	}
	
	/**
	 * Set the wiimote control to set like proxy 
	 *
	 * @param control The control to proxy 
	 */
	public void control(MWiiControl mWiiControl)
	{			
		this.mWiiControl = mWiiControl;
	}
	
	/**
	 * Change the discard package amount of the proxy. Change this value to 
	 * discard the amount of packets specified to allow the client process the
	 * information qickly     
	 *
	 * @paran packetsAmount Amount of packets to discard 
	 */
	public void discardPackets(int packetsAmount)
	{
		this.packetsAmount = packetsAmount;
	}
	
	/**
	 * Retunr the amount of packets to discard 
	 *
	 * @return  amount of packets to discard
	 */
	public int discardPackets()
	{
		return packetsAmount;
	}
	
	/**
	 * Start the proxy 
	 */
	public void start()
	{
		// If the proxy is already running do nothing
		if(running)
			return;
			
		// Start running 
		running = true;
			
		// Start server on another thread
		new Thread()
		{
			public void run()
			{
				try
				{
					// Create uuid 		
					UUID uuid = new UUID(UUID);
		
					// Create service url  		
					String url =  "btl2cap://localhost:" + uuid.toString();
				
					// Open input
					notifier = (L2CAPConnectionNotifier) Connector.open(url);
										
					// Dispatch Clients	
					while(running)
					{
						// Accept the client
						L2CAPConnection connection = (L2CAPConnection) 
							notifier.acceptAndOpen();						
						
						// A client connected to the proxy 
						enqueueLibraryEvent(MWiimoteProxy.this,
							EVENT_CLIENT_CONNECTED,null);						

						// Create a new client 
						MWiiControlClient client = new MWiiControlClient(
							MWiimoteProxy.this,mWiiControl,connection);
					}					
				}
				catch(Exception e)
				{
					// Fire error 
					enqueueLibraryEvent(MWiimoteProxy.this,
						MWiimote.EVENT_ERROR,e.getMessage());
				}
			}
		}.start();		
	}

	/**
	 * Stop proxy 
	 */
	public void stop()
	{
		// Stop listen 
		running = false;

		try
		{	
			// Close service 			
			notifier.close();
		}
		catch(Exception e)
		{
			// Fire error 
			enqueueLibraryEvent(this,MWiimote.EVENT_ERROR,e.getMessage());
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
