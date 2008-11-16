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

/**
 * MRest library allows Mobile Processing to connect and retrive data from rest
 * services.
 *
 * Two clases allow the access to the rest services, the MRequest encapsulate the
 * rest request, with the service url, the method and the parameters. 
 * The MResponse provide access to the server response giving access to the parts
 * of the response using a xpath like form.
 *
 * <pre>
 * // Create the request
 * MRestRequest mRequest = 
 * 	new MRestRequest("http://api.flickr.com/services/rest/");
 * mRequest.parameter("method","flickr.test.echo");
 * mRequest.parameter("value","12345");
 * mRequest.parameter("api_key","xxx");
 *
 * // Grab the response
 * MRestResponse mResponse = mRequest.waitForResponse();
 *
 * // Grab each element value
 * println(mResponse.get("/rsp/@stat"));
 * println(mResponse.get("/rsp/method/text()"));
 * </pre>
 *
 * @author Mary Jane Soft - Marlon J. Manrique
 *
 * @libname MRest
 */

package mjs.processing.mobile.mrest;
