/*

	MSound - Sound Library for Mobile Processing

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

package mjs.processing.mobile.msound;

import javax.microedition.media.*;

import processing.core.*;

/**
 * Class to access sound system.
 *
 * This class provides static methods to access sound caracteristics.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 */

public class MSoundManager
{
	/**
	 * A private constructor that removes the possibility to create SoundManager objects.
	 */

	private MSoundManager()
	{
	}
	
	/**
	 * Return the sound from the resources or server.
	 *
	 * @param locator resource name or URL
	 * @return the sound from the resources or server.
	 */

	public static MSound loadSound(String locator)
	{
		return new MSound(locator);
	}

	/**
	 * Return the sound from the resources or server and send events to the Processing MIDlet.
	 *
	 * @param locator resource name or URL
	 * @param pMIDlet Processing MIDlet
	 *
	 * @return the sound from the resources or server.
	 * 
	 * @since 0.4
	 */

	public static MSound loadSound(String locator, PMIDlet pMIDlet)
	{
		return new MSound(locator,pMIDlet);
	}

	/**
	 * Play back a tone as specified by a note and its duration. 
	 *
     * @param note Defines the tone of the note as specified by the above formula.
     * @param duration The duration of the tone in milli-seconds. Duration must be positive.
     * @param volume Audio volume range from 0 to 100. 100 represents the maximum volume at the 
	 *  current hardware level. Setting the volume to a value less than 0 will set the volume to 0.
	 *  Setting the volume to greater than 100 will set the volume to 100.	 
	 *
	 * @see javax.microedition.media.Manager.playTone
	 */

	public static void playTone(int note, int duration, int volume)
	{
		try
		{
			Manager.playTone(note,duration,volume);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Creates a MSoundRecorder ready to capture sound from the device.
	 *
	 * @return the sound capture recorder.
	 * @since 0.2
	 */

	public static MSoundRecorder createRecorder()
	{
		return new MSoundRecorder();
	}	

	/**
	 * Returns the media type according with the file extension.
	 *
	 * @param locator Sound file location.
	 * @return Mime Type or null if file extension is not recognized.
	 */

	public static String getMediaType(String locator)
	{
		locator = locator.toLowerCase();
		
		if(locator.endsWith(".wav"))
			return "audio/x-wav";

		if(locator.endsWith(".mid"))
			return "audio/midi";
			
		if(locator.endsWith(".mp3"))
			return "audio/mpeg";

		if(locator.endsWith(".au"))
			return "audio/basic";
		
		return null;
	}

	/**
	 * Return the list of supported content types.
	 *
	 * @return The list of supported content types
	 *
	 * @since 0.4
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
	 * @since 0.5
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
