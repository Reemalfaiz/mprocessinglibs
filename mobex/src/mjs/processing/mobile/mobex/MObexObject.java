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

import java.io.DataOutputStream;

import javax.obex.HeaderSet;

/**
 * Obex Object
 *
 * This class encapsulate a request or response object.
 */
public class MObexObject
{
	/**
	 * Name of the object
	 */
	public String name;
	
	/**
	 * Mime type
	 */
	public String type;
	
	/**
	 * Content
	 */
	public byte[] data;
	
	/**
	 * The MObexServer owner of the request
	 */
	private MObexServer mObexServer;
	
	/**
	 * Create a object with the specified values
	 *
	 * @param name Name of the object
	 * @param type mime type of the object
	 * @param data object content 
	 */
	public MObexObject(String name, String type, byte[] data)
	{
		this.name = name;
		this.type = type;
		this.data = data;
	}
	
	public MObexObject(MObexServer mObexServer, String name)
	{
		this(name,null,null);
		this.mObexServer = mObexServer;
	}
	
	public void send()
	{
		synchronized(mObexServer.requestHandler)
		{
			mObexServer.requestHandler.notifyAll();
		}
	}
}
