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
import javax.microedition.amms.control.audio3d.*;

import mjs.processing.mobile.msound.*;

/**
 * Represents the listener in the virtual acoustical space.
 * 
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */ 

public class MSpectator
{
	/**
	 * Instance of the class. Based on a singleton pattern
	 */
	
	private static MSpectator mSpectator;

	/**
	 * Represents the real listener in the virtual acoustical space.
	 */

	private Spectator spectator;

	/**
	 * Allows manipulate virtual location of a sound in the virtual acoustic space.
	 */
	 
	private LocationControl locationControl;

	/**
	 * A private constructor that removes the possibility to create MSpectator objects.
	 * 
	 * Set the current spectator and loads the different controls
	 */

	private MSpectator()
	{
		try
		{
			// Gets the spectator
			spectator = GlobalManager.getSpectator();

			// Gets the controls
			locationControl = (LocationControl) spectator.getControl("javax.microedition.amms.control.audio3d.LocationControl");
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Returns the listener in the virtual acoustical space.
	 * 
	 * @return the listener in the virtual acoustical space.
	 */

	public static MSpectator spectator()
	{
		// If the spectator is not created yet, create one
		if(mSpectator == null)
			mSpectator = new MSpectator();

		// Return the spectator
		return mSpectator;
	}

	/**
	 * Moves the object to the new location.
	 *
	 * Sets the location using cartesian right-handed coordinates that are relative to the origin.
	 *
	 * @param the x-coordinate of the new location in millimeters
	 * @param the y-coordinate of the new location in millimeters
	 * @param the z-coordinate of the new location in millimeters
	 */

	public void location(int x, int y, int z)
	{
		locationControl.setCartesian(x,y,z);
	}

	/**
	 * Moves the object to the new location.
	 *
	 * Sets the new location using spherical coordinates. The negative z-axis is the reference. 
	 * That is, a location where both azimuth and elevation are zero, is on the negative z-axis. 
	 * Radius is defined in millimeters.
	 * 
	 * @param azimuth the azimuth angle of the new location in degrees. The azimuth is measured from the negative z-axis in the direction of the x-axis.
	 * @param elevation the elevation angle of the new location in degrees. The elevation is measured from the x-z-plane in the direction of the y-axis.
	 * @param radius the radius of the new location from the origin in millimeters
	 */

	public void spherical(int azimuth, int elevation, int radius)
	{
		locationControl.setSpherical(azimuth,elevation,radius);
	}

	/**
	 * Gets the coordinates of the current location.
	 *
	 * @returns the x, y and z coordinates of the current location in millimeters
	 */

	public int[] location()
	{
		return locationControl.getCartesian();
	}
}