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

import processing.core.*;

import javax.microedition.media.*;

/**
 * MMovie.
 * 
 * Datatype for storing and playing movies.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 *
 * @since 0.3
 */

public class MMovie extends MVideo
{
	/**
	 * Create a video capture with the midlet specified and the size especified 
	 *
	 * @param pMIDlet The current midlet.
	 * @param locator A locator string in URI syntax that describes the media content or resource route.
	 * @param type mime type for the content
	 */	 

	public MMovie(PMIDlet pMIDlet, String locator, String mediaType)
	{
		super(pMIDlet,locator,mediaType);
	}

	/**
	 * Create a movie with the player specified
	 *
	 * @param pMIDlet The current midlet.
	 * @param videoPlayer a videoPlayer.
	 *
	 * @since 0.3
	 */

	protected MMovie(PMIDlet pMIDlet, Player videoPlayer)
	{
		super(pMIDlet,videoPlayer);
	}
}
