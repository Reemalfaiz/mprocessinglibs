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

import java.io.*;

import javax.microedition.media.*;
import javax.microedition.media.control.*;

import processing.core.*;

/**
 * This class allows video record.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 *
 * @since 0.3
 */

public class MMovieRecorder extends MVideoRecorder
{
	/**
	 * Creates a sound recorder with the parameters specified
	 *
	 * @param pMIDlet The current midlet.
	 * @param params Video capture format parameters
	 */

	public MMovieRecorder(PMIDlet pMIDlet, String parameters)
	{
		super(pMIDlet,parameters);
	}

	/**
	 * Creates a default video recorder.
	 *
	 * @param pMIDlet The Midlet
	 */
	 
	public MMovieRecorder(PMIDlet pMIDlet)
	{
		this(pMIDlet,"");
	}
}
