/*

	MXmlRpc - XmlRpc Library for Mobile Processing

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

package mjs.processing.mobile.mxmlrpc;

import java.util.Hashtable;
import java.util.Vector;

import org.kxmlrpc.XmlRpcClient;

import processing.core.PMIDlet;

/**
 * XmlRpc Client for Mobile Processing 
 */ 
public class MXmlRpc
{
	/**
	 * Event fired when an error is produced.
    * The data object will be a string with the message.
    */
   public final static int EVENT_ERROR = 0;
   
	/**
	 * Event fired when the call is satisfactory.
    * The data object will be the result object
    */
   public final static int EVENT_RESULT = 1;

   /**
    * Mobile Processing MIDlet
    */
   private PMIDlet pMIDlet;
   
	/**
	 * XmlRpc client 
	 */
	private XmlRpcClient client;
	
	/**
	 * Parameters 
	 */
	private Hashtable params;
	
	/**
	 * Creates a client with the url specified 
	 *
	 * @param pMIDlet parent midlet 
	 * @param url The full URL for the XML-RPC server
	 */
	private MXmlRpc(PMIDlet pMIDlet)
	{
		// Set midlet location 
		this.pMIDlet = pMIDlet;
		
		// Create empty list of parameters 
		params = new Hashtable();
	}	

	/**
	 * Creates a client with the url specified 
	 *
	 * @param pMIDlet parent midlet 
	 * @param url The full URL for the XML-RPC server
	 */
	public MXmlRpc(PMIDlet pMIDlet, String url)
	{
		// Call constructor 
		this(pMIDlet);		
		
		// Create client 
		client = new XmlRpcClient(url);
	}	

	/**
	 * Creates a client with the url specified 
	 *
	 * @param pMIDlet parent midlet 
	 * @param url The full URL for the XML-RPC server
	 * @param port The server's port number
	 */
	public MXmlRpc(PMIDlet pMIDlet, String url, int port)
	{
		// Call constructor 
		this(pMIDlet);		

		// Create client 
		client = new XmlRpcClient(url,port);
	}
	
	/**
	 * Excute the method 
	 *
	 * @param method The method name 
	 * @param params The parameters 
	 */
	public void execute(final String method, final Vector params)
	{
		// Create another thread to call the procedure 
		new Thread()
		{
			public void run()
			{
				try
				{
					// Try to call the procedure 
					Object result = client.execute(method,params);
					
					// Fire result 
					enqueueLibraryEvent(MXmlRpc.this,EVENT_RESULT,result);
				}
				catch(Exception e)
				{
					// Fire error event 
					enqueueLibraryEvent(MXmlRpc.this,EVENT_ERROR,e.getMessage());
				}
			}
		}.start();
	}
	
	/**
	 * Excute the method 
	 *
	 * @param method The method name 
	 */
	public void execute(String method)
	{
		// Create a struct with the parameters 
		Vector vector = new Vector();
		vector.addElement(params);	

		// Call the method 
		execute(method,vector);
	}
		
	/**
	 * Set a parameter value
	 *
	 * @param name Name of the parameter 
	 * @param value Value of the parameter 	 
	 */
	public void param(String name, String value)
	{
		params.put(name,value);
	}

	/**
	 * Set a parameter value
	 *
	 * @param name Name of the parameter 
	 * @param value Value of the parameter 	 
	 */
	public void param(String name, int value)
	{
		params.put(name,new Integer(value));
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
