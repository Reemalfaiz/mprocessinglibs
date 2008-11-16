/*

	MLocation - Location Library for Mobile Processing

	Copyright (c) 2005 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mlocation;

import javax.microedition.location.*;

/**
 * Processing library for request and get location.
 *
 * This class provides static methods to request and get location information.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 */

public class MLocation
{
	/**
	 * Instance of the class. Based on a singleton pattern
	 */
	 
	private static MLocation locationInstance;

	/**
	 * Module that provides location information.
	 */
	
	private LocationProvider locationProvider;

	/**
	 * A private constructor that removes the possibility to create Location objects.
	 * 
	 * Obtains the location module.
	 */

	private MLocation()
	{
		try
		{
			Criteria criteria = new Criteria();
			locationProvider =	LocationProvider.getInstance(criteria);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * This methods returns the singleton instance of the Location.
	 *
	 * @return Location The location instance.
	 */

	public static MLocation getLocation()
	{
		// if location has not been created, create one
		if(locationInstance == null)
			locationInstance = new MLocation();

		// return the instance
		return locationInstance;
	}

	/**
	 * This method fills the coords array with the Latitude and Longitude of the current location
	 *
	 * @param coords Array to fill with the current latitud and longitude.
	 */
	
	public void getCurrentLocation(double[] coords)
	{
		try
		{
			QualifiedCoordinates qualifiedCoordinates = locationProvider.getLocation(30).getQualifiedCoordinates();

			coords[0] = qualifiedCoordinates.getLatitude();
			coords[1] = qualifiedCoordinates.getLongitude();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Static method used from Processing for obtain the current location using the call Location.locatio(coords)
	 *
 	 * @param coords Array to fill with the current latitud and longitude.
	 */

	public static void location(double[] coords)
	{
		getLocation().getCurrentLocation(coords);
	}
}
