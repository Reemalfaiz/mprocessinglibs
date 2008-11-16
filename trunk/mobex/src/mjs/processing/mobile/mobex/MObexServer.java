/*

	MObex - Obex Library for Mobile Processing

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

package mjs.processing.mobile.mobex;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.bluetooth.LocalDevice;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.UUID;

import javax.microedition.io.Connector;

import javax.obex.HeaderSet;
import javax.obex.Operation;
import javax.obex.ResponseCodes;
import javax.obex.ServerRequestHandler;
import javax.obex.SessionNotifier;

import processing.core.PMIDlet;

/**
 * Obex Server
 */
public class MObexServer
{
	/**
	 * The MIDlet
	 */
	private PMIDlet pMIDlet;
	
	/**
	 * The server UUID
	 */
	private long uuidNumber;
	
	/**
	 * The request handler
	 */
	protected RequestHandler requestHandler;
	
	/**
	 * Server is running ?
	 */
	private boolean running;
	
	/**
	 * Create a MObexServer with the parent midlet and uuid specified
	 *
	 * @param pMIDlet The parent midlet
	 * @param uuidNumer The uuid 
	 */
	public MObexServer(PMIDlet pMIDlet, long uuidNumber)
	{
		this.pMIDlet = pMIDlet;
		this.uuidNumber = uuidNumber;
		
		// Create the request handler
		requestHandler = new RequestHandler();
	}
	
	/**
	 * Start the server
	 */
	public void start()
	{
		// If the server is already running do nothing
		if(running)
			return;
			
		// Create a new thread to accept clients
		new Thread()
		{
			public void run()
			{
				try
				{
					// Change the status of the server
					running = true;
					
					// Set the localdevice like discoverable
					LocalDevice localDevice = LocalDevice.getLocalDevice();
					localDevice.setDiscoverable(DiscoveryAgent.GIAC);
				
					// Get the UUUID
					UUID uuid = new UUID(uuidNumber);
					
					// Create the obex url, no authentication, no encryp, no master
					String url = "btgoep://localhost:" + uuid 
						+ ";authenticate=false;master=false;encrypt=false";
				
					// Open the server					
					SessionNotifier sessionNotifier = 
						(SessionNotifier) Connector.open(url);
						
					// Accept and open the client connection 
					// while the server is running
					while(running)
						sessionNotifier.acceptAndOpen(MObexServer.this.requestHandler);
					
					// Close the server
					sessionNotifier.close();
				}
				catch(Exception e)
				{
					// If any exception, report an error
					reportError(e);
				}				
			}
		}.start();
	}
	
	/**
	 * Stop the execution of the server
	 */
	public void stop()
	{
		running = false;
	}
	
	class RequestHandler extends ServerRequestHandler
	{
		public void onAuthenticationFailure(byte[] userName)
		{
			// Send the event to the midlet	
			enqueueLibraryEvent(MObexServer.this,MObex.EVENT_ON_PUT,userName);
		}
		
		public int onConnect(HeaderSet request, HeaderSet reply)
		{
			// Send the event to the midlet	
			enqueueLibraryEvent(MObexServer.this,MObex.EVENT_ON_CONNECT,null);
			
			// Return an ok response
			return ResponseCodes.OBEX_HTTP_OK;
		}
		
		public int onDelete(HeaderSet request, HeaderSet reply)
		{
			// Send the event to the midlet
			enqueueLibraryEvent(MObexServer.this,MObex.EVENT_ON_DELETE,null);
			
			// Return an ok response
			return ResponseCodes.OBEX_HTTP_OK;	
		}
		
		public void onDisconnect(HeaderSet request, HeaderSet reply)
		{
			// Send the event to the midlet
			enqueueLibraryEvent(MObexServer.this,MObex.EVENT_ON_DISCONNECT,null);	
		}
		
		public int onGet(Operation op)
		{
			try
			{
				// Get the received headers
				HeaderSet headerSet = op.getReceivedHeaders();
				String name = (String) headerSet.getHeader(HeaderSet.NAME);
							
				// Create Object ant set output
				MObexObject mObexObject 
					= new MObexObject(MObexServer.this,name);
				
				// Send the event to the midlet
				enqueueLibraryEvent(MObexServer.this,
					MObex.EVENT_ON_GET,mObexObject);
								
				synchronized(this)
				{
					wait();
				}
				
				// Reponse Headers
				headerSet = createHeaderSet();				
				headerSet.setHeader(HeaderSet.LENGTH,new Long(mObexObject.data.length));
				op.sendHeaders(headerSet);
				
				// Open the outputstream
				DataOutputStream dataOutputStream = op.openDataOutputStream();
				dataOutputStream.write(mObexObject.data);
				dataOutputStream.close();
				
				// Close the operation
				op.close();
			}
			catch(Exception e)		
			{
				// If any exception report an error
				reportError(e);
				e.printStackTrace();
			}
						
			// Return an ok response
			return ResponseCodes.OBEX_HTTP_OK;	
		}
		
		public int onPut(Operation op)
		{
			try
			{
				// Get the header set
				HeaderSet headerSet = op.getReceivedHeaders();
				String name = (String) headerSet.getHeader(HeaderSet.NAME);
				String type = (String) headerSet.getHeader(HeaderSet.TYPE);
				Long length = (Long) headerSet.getHeader(HeaderSet.LENGTH);
				
				// Create the data buffer
				byte[] data = new byte[(int) length.longValue()];
				
				// Open the inputstream and read all the data
				DataInputStream dataInputStream = op.openDataInputStream();			
				dataInputStream.read(data);
				dataInputStream.close();
				
				// Create a response
				Object response;
				
				// If the request don't have name is a string or an byte array
				if(name.equals(MObex.NO_NAME))
				{
					// If type is binary the response is the data
					if(type.equals(MObex.TYPE_BINARY))
						response = data;
					// The type is a string
					else
						response = new String(data);
				}
				// The reponse is a request
				else		
					response = new MObexObject(name,type,data);
					
				// Close the operation
				op.close();
				
				// Send the event to the midlet
				enqueueLibraryEvent(MObexServer.this,MObex.EVENT_ON_PUT,response);
			}
			catch(Exception e)		
			{
				// If any exception report an error
				reportError(e);
			}
			
			// Return an ok response			
			return ResponseCodes.OBEX_HTTP_OK;	
		}
		
		public int onSetPath(HeaderSet request, HeaderSet reply, boolean backup,
			 boolean create)
		{
			// Send the event to the midlet
			enqueueLibraryEvent(MObexServer.this,MObex.EVENT_ON_SET_PATH,null);
			
			// Return an ok response
			return ResponseCodes.OBEX_HTTP_OK;	
		}		
	}
	
	private void reportError(Exception e)
	{
		// Send the error event
		enqueueLibraryEvent(this,MObex.EVENT_ERROR,e.getMessage());
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
