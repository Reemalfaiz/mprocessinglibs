/*

	MWebServices - WebServices Library for Mobile Processing

	Copyright (c) 2005-2006 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mwebservices;

import java.util.*;

import org.ksoap2.*;
import org.ksoap2.transport.*;
import org.ksoap2.serialization.*;

/**
 * MWebService.
 * 
 * Datatype to make calls to webservices.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 */

public class MWebService
{
	/**
	 * The destination to POST SOAP data
	 */
	 
	private String endPointURL;

	/**
	 * The namespace for the soap object
	 */
	 
	private String namespace;

	/**
	 * A dynamic object that used to build soap calls
	 */
	 
	private SoapObject method;

	/**
	 * Creates a webservice consumer with the specified post site, namespace and method name.
	 *
	 * @param endPointURL The destination to POST SOAP data
	 * @param namespace The namespace for the soap object
	 * @param methodName The name for the soap object
	 */

	public MWebService(String endPointURL, String namespace, String methodName)
	{
		this.endPointURL = endPointURL;
		this.namespace = namespace;

		method = new SoapObject(namespace,methodName);
	}

	/**
	 * Adds a parameter (property) to the call.
	 *
	 * @param name the name of the parameter
	 * @param value the value of the parameter
	 */

	public void addParameter(String name, Object value)
	{
		method.addProperty(name,value);
	}

	/**
	 * Returns the result of calling the webservice.
	 *
	 * @return A object with the value returned by the webservice, null if service can be reach.
	 */

	public Object call()
	{
		try
		{
			// Create soap envelope
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

			// Assigns the object to the envelope
			envelope.setOutputSoapObject(method);
			
			// Use HTTP transport
			HttpTransport rpc = new HttpTransport(endPointURL);
			rpc.call(null,envelope);
			
			// Return the result
			return envelope.getResponse();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return null;
	}
}
