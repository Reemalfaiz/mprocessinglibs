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

import processing.core.*;

import mjs.processing.mobile.msound.*;

/**
 * Represents a sound source in a virtual acoustical space.
 * 
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */ 

public class MSoundSource3D extends MSound
{
	/**
	 * Represents the sound source in a virtual acoustical space.
	 */
	 
	private SoundSource3D soundSource3D;

	/**
	 * Allows manipulate virtual location of a sound in the virtual acoustic space.
	 */
	 
	private LocationControl locationControl;

	/**
	 * Allows manipulate directivity pattern of a sound source.
	 */

	private DirectivityControl directivityControl;

	/**
	 * Controls how the sound from a sound source is attenuated with its distance from the Spectator.
	 */

	private DistanceAttenuationControl distanceAttenuationControl;

	/**
	 * Creates a virtual sound source in a virtual 3D space.
	 *
	 * @param locator A locator string in URI syntax that describes the media content or resource route.
	 */

	public MSoundSource3D(String locator)
	{
		super(locator);
		init();
	}

	/**
	 * Creates a virtual sound source with the sound player specified.
	 *
	 * @param player Sound Player
	 */

	protected MSoundSource3D(Player player)
	{
		super(player);
		init();
	}

	/**
	 * Creates a virtual sound source in a virtual 3D space.
	 *
	 * @param locator A locator string in URI syntax that describes the media content or resource route.
	 * @param pMIDlet The current midlet.
	 */

	public MSoundSource3D(String locator, PMIDlet pMIDlet)
	{
		super(locator,pMIDlet);
		init();
	}

	/**
	 * Creates a virtual sound source with the sound player specified.
	 *
	 * @param player Sound Player
	 * @param pMIDlet The current midlet.	 
	 */

	protected MSoundSource3D(Player player, PMIDlet pMIDlet)
	{
		super(player,pMIDlet);
		init();
	}

	/**
	 * Create the 3D sound source and gets the different controls.
	 */
	
	private void init()
	{
		try
		{
			// Create the sound source with the player of the sound
			soundSource3D = GlobalManager.createSoundSource3D();
			soundSource3D.addPlayer(getPlayer());
			
			// Get the controls
			locationControl = (LocationControl) soundSource3D.getControl("javax.microedition.amms.control.audio3d.LocationControl");
			directivityControl = (DirectivityControl) soundSource3D.getControl("javax.microedition.amms.control.audio3d.DirectivityControl");
			distanceAttenuationControl = (DistanceAttenuationControl) soundSource3D.getControl("javax.microedition.amms.control.audio3d.DistanceAttenuationControl");
		}
		catch(Exception e)
		{
			// If something is wrong throws and exception
			throw new RuntimeException(e.getMessage());
		}
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

	/**
	 * Sets all the directivity parameters simultaneously.
	 *
	 * @param minAngle the total angle in degrees (a value from 0 to 360 inclusive), 
	 *			subtended by the inner cone at its vertex
	 * @param maxAngle the total angle in degrees (a value from 0 to 360 inclusive, where minAngle <= maxAngle), 
	 * 			subtended by the outer cone at its vertex
	 * @param rearLevel the level in millibels (mB, 1 mB = 1/100 dB), at which the sound from a source will 
	 * 			be heard at maxAngle and wider angles; must be a non-positive value. Setting the rearLevel 
	 *			to 0 makes the source omnidirectional.
	 */

	public void directivity(int minAngle, int maxAngle, int rearLevel)
	{
		directivityControl.setParameters(minAngle,maxAngle,rearLevel);
	}

	/**
	 * Gets the directivity pattern of a sound source.
	 *
	 * @returns an array of type int[3] containing the minAngle, maxAngle and rearLevel  parameters, in that order
	 */

	public int[] directivity()
	{
		return directivityControl.getParameters();
	}

	/**
	 * Sets all the 3D audio distance attenuation parameters simultaneously.
	 * 
	 * Distances are specified in millimeters.
	 *
	 * @param minDistance the minimum distance, below which the distance gain is clipped to its maximum value of 1.0
	 * @param maxDistance the maximum distance, beyond which the distance gain does not decrease any more. 
	 *			The exact behaviour of the gain at distances beyond the maximum distance depends on the value 
	 *			of the muteAfterMax.
	 * @param muteAfterMax a boolean determining how the distance gain behaves at distances greater than maxDistance: 
	 * 			true if beyond the maximum distance the source is silent; false if beyond the maximum distance 
	 *			the source's gain is held constant at the level at the maximum distance.
	 */

	public void distanceAttenuation(int minDistance, int maxDistance, boolean muteAfterMax, int rolloffFactor)
	{
		distanceAttenuationControl.setParameters(minDistance,maxDistance,muteAfterMax,rolloffFactor);
	}
}