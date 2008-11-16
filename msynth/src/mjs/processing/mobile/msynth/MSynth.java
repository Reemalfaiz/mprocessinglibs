/*

	MSynth - Sound Synthesis Library for Mobile Processing

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

package mjs.processing.mobile.msynth;

import javax.microedition.media.Manager;

/**
 * MSynth 
 */
public class MSynth
{
	/**
	 * Streaming sound duration
	 */
	public final static int STREAMING = -1;
	
	/**
	 * Check if the library is supported
	 *
	 * @return true if the library is supported, false otherwise
	 */
	public static boolean supported()
	{
		// Get the phone configuration
		String configuration = 
			System.getProperty("microedition.configuration");
		
		// Get the phone profile
		String profiles = 
			System.getProperty("microedition.profiles");

		// The phone that support the library are CLDC 1.1 to support float 
		// point and MIDP 2.0 to support sound media
		// Check if the configuration is different to CLDC 1.0 and profile is 
		// not MIDP 1.0		
		if(!configuration.equals("CLDC-1.0") 
			&& !profiles.equals("MIDP-1.0"))
		{
			// Check for wav support 
			String[] protocols = Manager.getSupportedProtocols("audio/x-wav");
			
			// If no empty array is returned, the library is supported
			if(protocols.length != 0)
				return true;
		}
		
		// The library is not supported
		return false;
	}
}
