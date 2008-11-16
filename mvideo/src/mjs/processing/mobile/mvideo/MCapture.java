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

import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import javax.microedition.media.control.*;

import processing.core.*;

/**
 * MCapture.
 * 
 * The capture class allows live video output and frame capture.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 *
 * @since 0.3
 */

public class MCapture extends MVideo
{
	/**
	 * Create a video capture with the midlet specified and the size especified 
	 *
	 * @param pMIDlet The current midlet.
	 */	 
	 
	public MCapture(PMIDlet pMIDlet)
	{
		super(pMIDlet,"capture://video");
		play();
	}
	
	/**
	 * Create a video capture with the midlet specified and the size especified 
	 *
	 * @param pMIDlet The current midlet.
     * @param overlay Draw over the video	 
     *
     * @since 0.5	 
	 */	 	 
	public MCapture(PMIDlet pMIDlet, boolean overlay)
	{
		super(pMIDlet,"capture://video",TRANS_NONE,overlay);
		play();
	}	

	/**
	 * Create a video capture with the midlet specified and the size especified 
	 *
	 * @param pMIDlet The current midlet.
	 * @param width Desired width (in pixels) of the display window
     * @param height Desired height (in pixels) of the display window
	 */	 
	 
	public MCapture(PMIDlet pMIDlet, int width, int height)
	{
		this(pMIDlet);
		size(width,height);
	}
	
	/**
	 * Create a video capture with the midlet specified and the size especified 
	 *
	 * @param pMIDlet The current midlet.
	 * @param width Desired width (in pixels) of the display window
     * @param height Desired height (in pixels) of the display window
     * @param overlay Draw over the video
     *
     * @since 0.5
	 */	 
	public MCapture(PMIDlet pMIDlet, int width, int height, boolean overlay)
	{
		this(pMIDlet,overlay);
		size(width,height);
	}	 	
}
