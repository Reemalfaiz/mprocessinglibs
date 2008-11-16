/*

	MVideo - Video Library for Mobile Processing

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
	
*/

package mjs.processing.mobile.mvideo;

import javax.microedition.media.*;

/**
 * Class to access video system.
 *
 * This class provides static methods to access video characteristics.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 */

public class MVideoManager
{
	/**
	 * A private constructor that removes the possibility to create VideoManager objects.
	 */

	private MVideoManager()
	{
	}
	
	/**
	 * Returns the media type according with the file extension.
	 *
	 * @param locator Video file location.
	 * @return Mime Type or null if file extension is not recognized.
	 */

	protected static String getMediaType(String locator)
	{
		locator = locator.toLowerCase();
		
		if(locator.endsWith(".mpg") || locator.endsWith(".mpeg"))
			return "video/mpeg";

		if(locator.endsWith(".3gp"))
			return "video/3gpp";

		return null;
	}
	
	/**
	 * Return the list of supported content types.
	 *
	 * @return The list of supported content types
	 *
	 * @since 0.6
	 */
	public static String[] supportedTypes()
	{
		return Manager.getSupportedContentTypes(null);
	}

	/**
	 * Return the list of supported capture types.
	 *
	 * @return The list of supported capture types
	 *
	 * @since 0.6
	 */
	public static String[] supportedCaptureTypes()
	{
		return Manager.getSupportedContentTypes("capture");
	}	 

	/**
	 * Check media type support
	 *
	 * @param mediaType The media type requiered
	 *
	 * @return true if the media type is supported, false otherwise
	 *
	 * @since 0.6
	 */
	public static boolean mediaTypeSupported(String mediaType)
	{ 
		// Get the media types
		String[] mediaTypes = supportedTypes();

		// Check if the media type is inside the array
		for(int i=0; i<mediaTypes.length; i++)
			if(mediaType.equals(mediaTypes[i]))
				return true;

	  	// The media type is not supported
		return false;
	}	
}
