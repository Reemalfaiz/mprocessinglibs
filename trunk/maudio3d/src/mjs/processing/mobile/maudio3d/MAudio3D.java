/*

	MAudio3D - 3D Audio Library for Mobile Processing

	Copyright (c) 2006 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.maudio3d;

import javax.microedition.media.*;
import javax.microedition.amms.*;

import processing.core.*;

import mjs.processing.mobile.msound.*;

/**
 * Class to access the 3D audio engine.
 *
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */

public class MAudio3D
{
	/**
	 * Creates a new audio 3d system
	 */
	 
	public MAudio3D()
	{
	}

	/**
	 * Returns the spectator which represents the listener in the virtual acoustical space.
	 *
	 * @returns the MSpectator, which represents the listener in the virtual acoustical space
	 */

	public MSpectator spectator()
	{
		return MSpectator.spectator();
	}

	/**
	 * Creates a new 3D sound source with the sound specified.
	 *
	 * @param mSound The sound preloaded
	 */

	public MSoundSource3D createSource(MSound mSound)
	{
		return new MSoundSource3D(mSound.getPlayer());
	}

	/**
	 * Creates a new 3D sound source with the sound specified in the locator.
	 *
	 * @param locator A locator string in URI syntax that describes the media content or resource route.
	 */

	public MSoundSource3D createSource(String locator)
	{
		return new MSoundSource3D(locator);
	}	

	/**
	 * Creates a new 3D sound source with the sound specified.
	 *
	 * @param mSound The sound preloaded
	 * @param pMIDlet The current midlet.	 
	 */
	 
	public MSoundSource3D createSource(MSound mSound, PMIDlet pMIDlet)
	{
		return new MSoundSource3D(mSound.getPlayer(),pMIDlet);
	}

	/**
	 * Creates a new 3D sound source with the sound specified in the locator.
	 *
	 * @param locator A locator string in URI syntax that describes the media content or resource route.
	 * @param pMIDlet The current midlet.	 
	 */

	public MSoundSource3D createSource(String locator, PMIDlet pMIDlet)
	{
		return new MSoundSource3D(locator,pMIDlet);
	}	
}