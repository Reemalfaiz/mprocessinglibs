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

/**
 * MObex 
 */
public class MObex
{
 	/**
	 * Event fired when an error is produced.
	 * The data object will be a string with the message.
	 */
	public final static int EVENT_ERROR = 1;
		
 	/**
	 * Event fired when a put operation is performed.
	 * The data object will be an string, byte[] or MObexObject.
	 */
	public final static int EVENT_ON_PUT = 2;
	
 	/**
	 * Event fired when the authetication fails.
	 * The data object will be a byte[] with the username.
	 */
	public final static int EVENT_AUTHENTICATION_FAILURE = 3;
	
 	/**
	 * Event fired when a client connects to the server.
	 * The data object will be null.
	 */
	public final static int EVENT_ON_CONNECT = 4;
	
 	/**
	 * Event fired when a delete operation is performed.
	 * The data object will be null.
	 */
	public final static int EVENT_ON_DELETE = 5;
	
 	/**
	 * Event fired when a client disconnects from the server.
	 * The data object will be null.
	 */
	public final static int EVENT_ON_DISCONNECT = 6;
	
 	/**
	 * Event fired when a get operation is performed.
	 * The data object will be null.
	 */
	public final static int EVENT_ON_GET = 7;
		
 	/**
	 * Event fired when a seth path operation is performed.
	 * The data object will be null.
	 */
	public final static int EVENT_ON_SET_PATH = 8;
	
 	/**
	 * Event fired when the get operation is finnished
	 * The data object will the MObexObject.
	 */
	public final static int EVENT_GET_DONE = 9;	
	
	/**
	 * Identifies a request without name. Used to send strings and byte arrays
	 */
	public final static String NO_NAME = "NO_NAME";
	
	/**
	 * Mime type for the byte array
	 */
	public final static String TYPE_BINARY = "application/octet-stream";

	/**
	 * 	Mime Type for the strings
	 */
	public final static String TYPE_TEXT = "text/plain";
}
