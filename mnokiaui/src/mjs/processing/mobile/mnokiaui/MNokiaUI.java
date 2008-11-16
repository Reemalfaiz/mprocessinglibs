/*

	MNokiaUI - NokiaUI Support Library for Mobile Processing

	Copyright (c) 2006-2007 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mnokiaui;

import processing.core.*;

import com.nokia.mid.ui.*;

import javax.microedition.lcdui.Display;

/**
 * MNokiaUI
 *
 * This class provides implementation of Mobile Processing primitives to 
 * support NokiaUI API.
 *
 * The current version add alpha channel color support
 *
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */

public class MNokiaUI extends MCanvas
{
	/**
	 * Nokia Direct Graphics Manipulator.
	 */
	 
	private DirectGraphics directGraphics;

	/**
	 * Inits the graphics system with support for alpha channel color
	 *
	 * @param pMIDlet The midlet associated with the application
	 */
	
	public MNokiaUI(PMIDlet pMIDlet)
	{
		// PCanvas Initilization
		super(pMIDlet);

		// Check if the current system provides NokiaUI API support
		// and create the direct graphics object with the current 
		// graphics of the buffer created by PCanvas
		
		if(compatible())
			directGraphics = DirectUtils.getDirectGraphics(bufferg);

		// Add the exit command to the canvas
		addCommand(pMIDlet.cmdExit);

		// If the custom command is set add to the canvas
		if(pMIDlet.cmdCustom != null)
			addCommand(pMIDlet.cmdCustom);

		// Set the listener command like the MIDlet
		setCommandListener(pMIDlet);
		
		// Display the context
		pMIDlet.canvas = this;
		Display.getDisplay(pMIDlet).setCurrent(this);		
	}

	/**
	 * Returns this object like the PCanvas.
	 *
	 * This is used by convention in the MLibraries 
	 *
	 * @return The Canvas
	 */

	public PCanvas canvas()
	{
		return this;
	}

	/**
	 * Sets the current color (and alpha) to the specified ARGB value 
	 * (0xAARRGGBB). All subsequent rendering operations will use this 
	 * color (and alpha) in associated Graphics.
	 *
	 * This method overwrites the MCanvas setColor method to allow color 
	 * costumization.
	 *
	 * The setColor method was added to a modified version of PCanvas to
	 * allow the reutilization of the PCanvas methods and easy color manipulation
	 *
	 * @param argbColor the color being set
	 */

	protected void setColor(int argbColor)
	{
		// If the directGraphics is not available use current implementation
		if(directGraphics == null)
			bufferg.setColor(argbColor);
		else
			// Set the color with alpha values 
			directGraphics.setARGBColor(argbColor);
	}

	/** 
	 * Checks if the system supports NokiaUI API
	 *
	 * @return true if the system supports NokiaUI API, false otherwise
	 */

	public static boolean compatible()
	{
		try
		{
			// Try to load the DirectUtils class from the Nokia API
			Class.forName("com.nokia.mid.ui.DirectUtils");
		}
		catch(ClassNotFoundException e)
		{
			// If the class is not present, returns false
			return false;
		}

		// If everything is ok, return true
		return true;
	}
}
