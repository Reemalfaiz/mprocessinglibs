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
 * A 3D object.
 *
 * @author Mary Jane Soft - Marlon J. Manrique. 
 *
 * @since 0.4
 */

public class M3dObject
{
	/**
	 * M3d System, The M3d System where the object belogns
	 */

	private M3d m3d;
	 
	/**
	 * The native M3G object.
	 */
	 
	private Node object3d;

	/**
	 * Create a MObject3D without native 3D object.
	 */

	protected M3dObject()
	{
	}
	
	/**
	 * Create a MObject3D with the native 3D object.
	 *
	 * @param object3d Native 3D object
	 */

	protected M3dObject(Node object3d)
	{
		setObject3D(object3d);
	}

	/**
	 * Set the native 3D Object asociated 
	 *
	 * @param object3d Native 3D object
	 */

	protected void setObject3D(Node object3d)
	{
		this.object3d = object3d;
	}

	/**
	 * Return the native 3D Object asociated 
	 *
	 * @return Native 3D object
	 */

	protected Node getObject3D()
	{
		return object3d;
	}

	/**
	 * Translate the shape the amount specified for each axis.
	 * 
	 * @param x translation along the X axis
	 * @param x translation along the Y axis
	 * @param z translation along the Z axis
	 */

	public void translate(float x, float y, float z)
	{
		// Object is set
		if(object3d == null)
			return;

		// Simply use transformable translation
		object3d.translate(x,y,z);
	}

	/**
	 * Rotate the shape around X axis the amount angle specified in radians.
	 * 
	 * @param radians angle specified in radians
	 */

	public void rotateX(float radians)
	{
		// Object is set
		if(object3d == null)
			return;

		// Simply use transformable rotation	
		object3d.postRotate((float) Math.toDegrees(radians),1.0f,0.0f,0.0f);
	}

	/**
	 * Rotate the shape around Y axis the amount angle specified in radians.
	 * 
	 * @param radians angle specified in radians
	 */

	public void rotateY(float radians)
	{
		// Object is set
		if(object3d == null)
			return;
	
		// Simply use transformable rotation
		object3d.postRotate((float) Math.toDegrees(radians),0.0f,1.0f,0.0f);
	}

	/**
	 * Rotate the shape around X axis the amount angle specified in radians.
	 * 
	 * @param radians angle specified in radians
	 */

	public void rotateZ(float radians)
	{
		// Object is set
		if(object3d == null)
			return;

		// Simply use transformable rotation
		object3d.postRotate((float) Math.toDegrees(radians),0.0f,0.0f,1.0f);
	}

	/**
	 * Move the shape to the location specified.
	 * 
	 * @param x new x coordinate
	 * @param x new y coordinate
	 * @param z new z coordinate
	 */

	public void location(int x, int y, int z)
	{
		// Object is set
		if(object3d == null)
			return;
	
		// Get the current translation
		float[] currentTranslation = new float[3];
		object3d.getTranslation(currentTranslation);

		// Set to origin
		object3d.translate(-currentTranslation[0],-currentTranslation[1],-currentTranslation[2]);

		// Get the 3d system translation
		if(m3d != null)
			m3d.getTranslation(currentTranslation);
		
		// Translate to the coordinates specified
		object3d.translate(currentTranslation[0] + x,-currentTranslation[1] + y,currentTranslation[2] + z);
	}

	/**
	 * Sets the orientation component of this Transformable. The orientation is specified such that 
	 * looking along the rotation axis, the rotation is angle degrees clockwise. Note that the axis 
	 * does not have to be a unit vector.
	 *
	 * @param angle angle of rotation about the axis, in degrees
	 * @param ax X component of the rotation axis
	 * @param ay Y component of the rotation axis
	 * @param az Z component of the rotation axis
	 */

	public void orientation(float angle, float ax, float ay, float az)
	{
		// Object is set
		if(object3d == null)
			return;

		// Simply use the orientation method
		object3d.setOrientation(angle,ax,ay,az);
	}

	/**
	 * Set the M3d System where the object belogns 
	 *
	 * @param m3d M3d System where the object belogns 
	 */

	protected void setM3d(M3d m3d)
	{
		this.m3d = m3d;
	}

	/**
	 * Rotate the shape in all axis.
	 * 
	 * @param xRadians angle specified in radians to rotate in the X axis
	 * @param yRadians angle specified in radians to rotate in the Y axis
	 * @param zRadians angle specified in radians to rotate in the Z axis
	 */

	public void rotate(float xRadians, float yRadians, float zRadians)
	{
		rotateX(xRadians);
		rotateY(yRadians);
		rotateZ(zRadians);
	}
	
	/**
	 * Return the x coordinate of the object
	 *
	 * @return the x coordinate
	 *
	 * @since 0.6
	 */
	public int x()
	{
		// Get the current translation
		float[] currentTranslation = new float[3];
		object3d.getTranslation(currentTranslation);
		return (int) currentTranslation[0];
	}
	
	/**
	 * Return the y coordinate of the object
	 *
	 * @return the y coordinate
	 *
	 * @since 0.6
	 */
	public int y()
	{
		// Get the current translation
		float[] currentTranslation = new float[3];
		object3d.getTranslation(currentTranslation);
		return (int) currentTranslation[1];
	}

	/**
	 * Return the z coordinate of the object
	 *
	 * @return the z coordinate
	 *
	 * @since 0.6
	 */
	public int z()
	{
		// Get the current translation
		float[] currentTranslation = new float[3];
		object3d.getTranslation(currentTranslation);
		return (int) currentTranslation[2];
	}	
	
	/**
	 * Scale the object in eeach axis by factor specified
	 *
	 * @param sx scale along the X axis
	 * @param sy scale along the Y axis
	 * @param sz scale along the Z axis
	 *
	 * @since 0.6
	 */
	public void scale(float sx, float sy, float sz)
	{
		object3d.scale(sx,sy,sz);
	}	
}
