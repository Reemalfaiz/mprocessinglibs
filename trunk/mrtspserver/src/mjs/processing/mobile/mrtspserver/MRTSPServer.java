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

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;
import javax.microedition.io.StreamConnectionNotifier;

import processing.core.PMIDlet;

/**
 * RTSP Server 
 */
public class MRTSPServer implements Runnable
{	
	/**
	 * Error event
	 */
	public final static int EVENT_ERROR = 1;
	
	/**
	 * Client Available Event 
	 */
	public final static int EVENT_CLIENT_AVAILABLE = 2; 

	/**
	 * Server started Event 
	 */
	public final static int EVENT_SERVER_STARTED = 4;
	
	/**
	 * Server stopped Event 
	 */
	public final static int EVENT_SERVER_STOPPED = 5;
	
 	/**
	 * Client Dispatched event  
	 */
	public final static int EVENT_CLIENT_DISPATCHED = 3; 

	/**
	 * Alternate RTPS port
	 * @value 8554
	 */
	public final static int ALTERNATE_PORT = 8554;
	
	/**
	 * Default RTPS port
	 * @value 554
	 */
	public final static int DEFAULT_PORT = 554;
	
	/**
	 * Server thread to listen clients
	 */
	private Thread thread;
	
	/**
	 * Connection Notifier 
	 */
	private StreamConnectionNotifier notifier;
	
	/**
	 * The server is running ?
	 */
	private boolean running;	
	
	/**
	 * Current port 
	 */
	private int port;
	
	/**
	 * Session Counter 
	 */
	private int sessionCounter;
	
	/**
	 * Sessions table 
	 */
	private Hashtable sessions;
	
	/**
	 * Parent MIDlet 
	 */
	private PMIDlet pMIDlet;
	
	/**
	 * Create a RTSP Server
	 *
	 * @param pMIDlet Parent MIDlet
	 */
	public MRTSPServer(PMIDlet pMIDlet)
	{
		// Set the default port 
		this(pMIDlet,DEFAULT_PORT);		
	}
	
	/**
	 * Create a RTSP Server
	 *
	 * @param pMIDlet Parent MIDlet	 
	 * @param port Port to listen clients 
	 */
	public MRTSPServer(PMIDlet pMIDlet, int port)
	{
		// Init attributes 		
		this.pMIDlet = pMIDlet;				
		this.port = port;
		
		// Init the sessions 
		sessions = new Hashtable();		
	}	
	
	/**
	 * Start the server 
	 */
	public void start()
	{
		// If the server is running return 
		if(thread != null)
			return;

		// Set running like true 
		running = true;		
			
		// Create a new thread and start listen clients 
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Create the connection and listen clients 
	 */
	public void run()
	{
		try
		{
			// Create the url of the server            		
			StringBuffer url = new StringBuffer();
			url.append("socket://:");
			url.append(port);
			
			// Open the connection
			notifier = (StreamConnectionNotifier) 
				Connector.open(url.toString());
				
			// Send event 
			enqueueLibraryEvent(EVENT_SERVER_STARTED,null);				

			// Wait and dispatch client while the server id running
			while(running)
			{				
				// Wait for a client and open a connection 
				SocketConnection connection = 
					(SocketConnection) notifier.acceptAndOpen();					
				
				// Handle the client in other thread
				MRTSPDispatcher dispatcher = 
					new MRTSPDispatcher(this,connection);

				// Start the thread 					
				dispatcher.start();
			}
			
			// Stop listening and close the server
			notifier.close();
		}
		catch(Exception e)
		{
			// Send event error 
			enqueueLibraryEvent(EVENT_ERROR,e.getMessage());
		}
	}

	/**
	 * Stop the server 
	 */	
	public void stop()
	{
		try
		{
			// Stop listening and close the server
			if(notifier != null)
				notifier.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
		// Stop the execution 
		running = false;
		
		// Send event 
		enqueueLibraryEvent(EVENT_SERVER_STOPPED,null);		
	}
	
	/**
	 * Method used to fire the library events
	 *
	 * @param library Library object that fires the event
	 * @param event Event identifier
	 * @param data Data provide in the event
	 */	 	
	protected void enqueueLibraryEvent(int event, Object data)
	{
		// If a midlet was registered
		// fire the event
		if(pMIDlet != null)
			pMIDlet.enqueueLibraryEvent(this,event,data);
	}
	
	/**
	 * Create a session for a client 
	 *
	 * @param request Current request
	 * @return the new session  
	 */
	protected MRTSPSession createSession(MRTSPRequest request)
	{
		// Update counter 
		sessionCounter++;

		// Create session values 
		int sessionId = sessionCounter;
		int serverPort = 5000 + sessionCounter*2;
		
		// Create session and return it 
		MRTSPSession session = new MRTSPSession(sessionId,serverPort,
			request.getClientAddress(),request.getClientPort()); 
			
		// Store the session 
		sessions.put(Integer.toString(sessionId),session);
		
		// Return the current session 
		return session;
	}
	
	/**
	 * Return the session with the given id 
	 *
	 * @param id Session identifier 
	 * @return The new session 
	 */
	protected MRTSPSession getSession(int id)
	{
		return (MRTSPSession) sessions.get(Integer.toString(id));
	}
	
	/**
	 * Remove the session with the id specified 
	 *
	 * @param id Session identifier
	 */
	protected void removeSession(int id)
	{
		sessions.remove(Integer.toString(id));
	}
}