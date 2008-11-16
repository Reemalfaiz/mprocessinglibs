/*

	MM3G - 3D Graphics Library for Mobile Processing

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

import javax.microedition.m3g.*;

/**
 * A Camera.
 *
 * @author Mary Jane Soft - Marlon J. Manrique. 
 *
 * @since 0.4
 */

public class MCamera extends M3dObject
{
	/**
	 * The native camera
	 */
	 
	private Camera nativeCamera;
	
	/**
	 * Create a MObject3D without native 3D object.
	 *
	 * @param the native camera
	 */

	protected MCamera(Camera camera)
	{
		super(camera);
		nativeCamera = camera;
	}

	/**
	 * Constructs a perspective projection matrix and sets that as the current projection matrix. 
	 * Note that the near and far clipping planes may be in arbitrary order, although usually near < far. 
	 * If near and far are equal, nothing is rendered.
	 * 
	 * @param fovy field of view in the vertical direction, in degrees
	 * @param aspectRatio aspect ratio of the viewport, that is, width divided by height
	 * @param near distance to the front clipping plane
	 * @param far distance to the back clipping plane
	 */

	public void perspective(float fovy, float aspectRatio, float near, float far)
	{
		nativeCamera.setPerspective(fovy,aspectRatio,near,far);
	}

	/**
	 * Gets the current projection parameters and type. 
	 * The given float array is populated with the projection parameters in the same order as 
	 * they are supplied to the respective set methods, setPerspective and setParallel. If the 
	 * projection type is GENERIC, the float array is left untouched. This is the case even if the 
	 * generic projection matrix actually is a perspective or parallel projection.
	 *
	 * @param params float array to fill in with the four projection parameters, 
	 *               or null to only return the type of projection
	 *
	 * @return the type of projection: GENERIC, PERSPECTIVE, or PARALLEL
	 */

	public int projection(float[] params)
	{
		return nativeCamera.getProjection(params);
	}
}