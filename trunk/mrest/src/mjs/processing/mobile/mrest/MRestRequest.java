/*

	MRest - Rest Library for Mobile Processing

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

package mjs.processing.mobile.mrest;

import mjs.microrest.RestException;
import mjs.microrest.RestRequest;
import mjs.microrest.RestResponse;
import mjs.microrest.RestResult;
import mjs.microrest.RestResultListener;

import processing.core.PMIDlet;

/**
 * REST Request
 *
 * Contains all the elements requiered to perform a rest request, the service url, 
 * and parameters, it returns a MRestResponse
 */
public class MRestRequest extends RestRequest implements RestResultListener
{
	/**
	 * Event fired when the entire response has been read and is available.
	 * The data object will be a MResponse.
	 */
	public final static int EVENT_DONE = 1;
	
	/**
	 * Event fired when an error is produced in the response.
	 * The data object will be a String with the message error.
	 */
	public final static int EVENT_ERROR = 2;
	
	/**
	 * The MIDlet
	 */
	private PMIDlet pMIDlet;
	
	/**
	 * Create a request to the given service
	 *
	 * @param pMIDlet The midlet
	 * @param serviceURL The url of the service
	 */	
	public MRestRequest(PMIDlet pMIDlet, String serviceURL)
	{
		super(serviceURL,GET);
		
		// Set the parent midlet
		this.pMIDlet = pMIDlet;
		
		// Register this like a result listener
		setRestResultListener(this);
	}

	/**
	 * Create a request to the given service
	 *
	 * @param pMIDlet The midlet
	 * @param serviceURL The url of the service
	 * @param method HTTP method to use 
	 */	
	public MRestRequest(PMIDlet pMIDlet, String serviceURL, int method)
	{
		super(serviceURL,method);
		
		// Set the parent midlet
		this.pMIDlet = pMIDlet;
		
		// Register this like a result listener
		setRestResultListener(this);
	}
	
	/**
	 * Create a request to the given service
	 *
	 * @param serviceURL The url of the service
	 */	
	public MRestRequest(String serviceURL)	
	{
		this(null,serviceURL);
	}
	
	/**
	 * Set the parameter of the request
	 *
	 * @param name Parameter name
	 * @param value Parameter value
	 */
	public void parameter(String name, String value)
	{
		setParameter(name,value);
	}	
	
	/**
	 * Send the request, dont wait. The result are send in a library event
	 */
	public void send()
	{
		try
		{
			// Send the request
			super.send();
		}
		catch(RestException e)
		{
			reportError(this,e.getMessage());
		}
	}
	
	/**
	 * Send the request and wait for the response, null if any exception 
	 *
	 * @return The response
	 */
	public MRestResponse waitForResponse()
	{
		try
		{
			// Send the request in the same thread
			RestResponse restResponse = super.sendAndWait();
			
			// Encapsulate the response in a MResponse
			MRestResponse mResponse = new MRestResponse(restResponse);
			return mResponse;
		}
		catch(RestException e)
		{
			reportError(this,e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Called when a result is available.
	 *
	 * @param restRequest The request object
	 * @param restResponse The response object
	 * @param restResult The result
	 */
	public void resultAvailable(RestRequest restRequest, 
		RestResponse restResponse, RestResult restResult)
	{
		// Encapsulate the response in a MResponse
		MRestResponse mResponse = new MRestResponse(restResponse);
		
		// Send event to the midlet			
		if(pMIDlet != null)
			pMIDlet.enqueueLibraryEvent(this,EVENT_DONE,mResponse);
	}

	/**
	 * Report an error in the request
	 *
	 * @param restRequest The request object
	 * @param message The message to report
	 */	
	public void reportError(RestRequest restRequest, String message)
	{
		// Send event to the midlet			
		if(pMIDlet != null)
			pMIDlet.enqueueLibraryEvent(this,EVENT_ERROR,message);
	}

	/**
	 * Sets the general request property. If a property with the key 
	 * already exists, overwrite its value with the new value.
	 *
	 * @param key the keyword by which the request is known (e.g., "accept")
	 * @param value - the value associated with it. 
	 *
	 * @since 0.3
	 */
	public void property(String key, String value)
	{
		setProperty(key,value);
	}	
}
