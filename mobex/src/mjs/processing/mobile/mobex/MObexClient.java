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

import javax.microedition.io.Connector;

import javax.obex.ClientSession;
import javax.obex.HeaderSet;
import javax.obex.Operation;
import javax.obex.ResponseCodes;

import mjs.processing.mobile.mbt.MService;

import processing.core.PMIDlet;

/**
 * Obex Client
 */
public class MObexClient
{
	/**
	 * The MIDlet
	 */
	private PMIDlet pMIDlet;

	/**
	 * The client session with the server
	 */
	private ClientSession clientSession;
	
	/**
	 * Create a client with the given bluetooth service discovered
	 *
	 * @param pMIDlet The midlet
	 * @param mService The bluetooth service
	 */	
	public MObexClient(PMIDlet pMIDlet, MService mService)
	{
		this(pMIDlet,mService.url());
	}
	
	/**
	 * Create a client with the given bluetooth url
	 *
	 * @param pMIDlet The midlet
	 * @param serviceURL The service url
	 */	
	public MObexClient(PMIDlet pMIDlet, String serviceURL)
	{
		// Set the midlet 
		this.pMIDlet = pMIDlet;

		try
		{
			// Try to open the connection 
			clientSession = (ClientSession) Connector.open(serviceURL);
			clientSession.connect(null);
		}
		catch(Exception e)
		{
			// If not possible send the error event and throws an exception
			reportError(e);
			throw new RuntimeException();
		}
	}
	
	/**
	 * Create the header with the object values
	 *
	 * @param name Object name
	 * @param type Object type
	 * @param length Object data length
	 */
	private HeaderSet createHeader(String name, String type, long length)
	{
		// Create the header set with the object information
		HeaderSet headerSet = clientSession.createHeaderSet();
		headerSet.setHeader(HeaderSet.NAME,name);		
		headerSet.setHeader(HeaderSet.TYPE,type);
		headerSet.setHeader(HeaderSet.LENGTH,new Long(length));	
		
		// Return the created header
		return headerSet;
	}
	
	/**
	 * Put the object
	 *
	 * @param mObexObject The Obex object
	 */
	public int put(MObexObject mObexObject)
	{
		// Server response, if exception -1
		int responseCode = -1;
		
		try
		{
			// Create the header set with the object information
			HeaderSet headerSet = clientSession.createHeaderSet();
			headerSet.setHeader(HeaderSet.NAME,mObexObject.name);
			headerSet.setHeader(HeaderSet.TYPE,mObexObject.type);
			headerSet.setHeader(HeaderSet.LENGTH,new Long(mObexObject.data.length));		
			
			// Set the PUT operation
			Operation operation = clientSession.put(headerSet);
	
			// Write data
			DataOutputStream dataOutputStream = operation.openDataOutputStream();
			dataOutputStream.write(mObexObject.data);
			dataOutputStream.close();
			
			// Get the response from the server
			responseCode = operation.getResponseCode();
			
			// Close the operation
			operation.close();
		}
		catch(Exception e)
		{
			// If any exception, report and error
			reportError(e);			
		}
		
		// Return the response code
		return responseCode;
	}
	
	/**
	 * Put a string in the server
	 *
	 * @param text The text to send 
	 */
	public int put(String text)
	{
		// Get the bytes of the text
		byte[] data = text.getBytes();		
		
		// Create an object without name, text type and bytes
		MObexObject mObexObject = 
			new MObexObject(MObex.NO_NAME,MObex.TYPE_TEXT,data);
			
		// Put the object
		return put(mObexObject);
	}
	
	/**
	 * Put binary data in the server
	 *
	 * @param data The binary data to send
	 */
	public int put(byte[] data)
	{
		// Create a object without name, binary type and bytes
		MObexObject mObexObject = 
			new MObexObject(MObex.NO_NAME,MObex.TYPE_BINARY,data);
		
		// Put the object
		return put(mObexObject);
	}	
	
	/**
	 * Get the object specified
	 */
	public void get(final String name)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// Create the header set with the object information
					HeaderSet headerSet = createHeader(name,MObex.TYPE_TEXT,0);
					
					// Set the GET operation
					Operation operation = clientSession.get(headerSet);
					
					// Read data
					DataInputStream dataInputStream = operation.openDataInputStream();
					
					int length = (int) operation.getLength();
					byte[] data = new byte[length];			
					dataInputStream.read(data);
					dataInputStream.close();
					
					// Get the response from the server
					int responseCode = operation.getResponseCode();
					
					// Close the operation
					operation.close();
					
					// Create the Obex object
					MObexObject mObexObject = new MObexObject(null,null,data);
					
					pMIDlet.enqueueLibraryEvent(MObexClient.this,
						MObex.EVENT_GET_DONE,mObexObject);
				}
				catch(Exception e)
				{
					// If any exception, report and error
					reportError(e);
				}
			}
		}.start();	
	}
	
	/**
	 * Close the client communication with the server
	 */
	public void close()
	{
		try
		{
			// If the client conexion is open disconnect first 
			// then close the connection
			if(clientSession != null)
			{
				clientSession.disconnect(null);
				clientSession.close();
				clientSession = null;
			}
		}
		catch(Exception e)
		{
			// If any exception, report and error
			reportError(e);
		}
	}	
	
	/**
	 * Report and error to the midlet
	 *
	 * @param e An exception
	 */
	private void reportError(Exception e)
	{
		pMIDlet.enqueueLibraryEvent(this,MObex.EVENT_ERROR,e.getMessage());
		e.printStackTrace();		
	}	
}
