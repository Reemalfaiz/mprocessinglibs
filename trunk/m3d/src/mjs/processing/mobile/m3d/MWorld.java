/*

	M3d - 3D Graphics Library for Mobile Processing

	Copyright (c) 2005-2007 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.m3d;

import javax.microedition.m3g.Object3D;
import javax.microedition.m3g.World;

/**
 * A special Group node that is a top-level container for scene graphs.
 *
 * @author Mary Jane Soft - Marlon J. Manrique. 
 *
 * @since 0.5
 */
public class MWorld extends M3dObject
{
	/**
	 * The native world
	 */
	private World nativeWorld;

	/**
	 * Create a MWorld with the native World.
	 *
	 * @param world The native World
	 */
	protected MWorld(World world)
	{
		super(world);
		nativeWorld = world;
	}

    /**
	 * Retrieves an object that has the given user ID and is reachable from 
	 *	this object.
	 *
	 * @param userID - the user ID to search for
	 * @return the first object encountered that has the given user ID, or 
	 *			null if	no matching objects were found
	 *
	 * @since 0.5
	 */
	public M3dObject find(int userID)
	{
		// Try to find the object
	 	Object3D object = nativeWorld.find(userID);

		// Return the representation
		return M3d.createObject(object);
	}
}