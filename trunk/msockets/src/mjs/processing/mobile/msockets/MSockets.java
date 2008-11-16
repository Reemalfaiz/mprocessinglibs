/*

	MSockets - Sockets Library for Mobile Processing

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

package mjs.processing.mobile.msockets;

public class MSockets
{
	/**
	 * Check if the library is supported
	 *
	 * @return true if the library is supported, false otherwise
	 */ 
	public static boolean supported()
	{
		try
		{
			// Try to load the SocketConnection class 
			// it fails to load, the library is not supported			
			Class.forName("javax.microedition.io.SocketConnection");
			return true;
		}
		catch(ClassNotFoundException e)
		{
		}
		
		// The library is not supported
		return false;
	}
	
	/**
	 * Return the hostname 
	 * 
	 * @return The hostname, null if no hostname available
	 */
	public static String hostname()
	{
		return System.getProperty("microedition.hostname");
	}
}