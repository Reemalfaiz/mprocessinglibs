/*

	MWiimote - Wii Remote Library for Mobile Processing

	Copyright (c) 2008 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mwiimote;

import javax.bluetooth.L2CAPConnection;

import javax.microedition.io.Connector; 

import mjs.processing.mobile.mbt.MDevice;

import processing.core.PMIDlet;

/**
 * Wii Accelerometer  
 */
public class MWiiAccelerometer
{
	/**
	 * Zero X value
	 */
	protected int zeroX;
	
	/**
	 * Zero Y value
	 */
	protected int zeroY;
	
	/**
	 * Zero Z value
	 */
	protected int zeroZ;
	
	/**
	 * One X value
	 */
	protected int oneX;
	
	/**
	 * One Y value
	 */
	protected int oneY;
	
	/**
	 * One Z value
	 */
	protected int oneZ;
	
	/**
	 * Current x value
	 */
	protected int x;
	
	/**
	 * Current y value
	 */
	protected int y;
	
	/**
	 * Current z value
	 */
	protected int z;
	
	/**
	 * The accelerometer is already calibrate 
	 */
	protected boolean calibrate;	
	
	/**
	 * Create the accelerometer
	 */
	public MWiiAccelerometer()
	{	
	}
	
	/**
	 * Set calibration data 
	 *
	 * @param zeroX Zero value of X-axis
	 * @param zeroY Zero value of Y-axis
	 * @param zeroZ Zero value of Z-axis
	 * @param oneX One value of X-axis
	 * @param oneY One value of Y-axis
	 * @param oneZ One value of Z-axis
	 */
	protected void setCalibration(int zeroX, int zeroY, int zeroZ,
		int oneX, int oneY, int oneZ)
	{
		// Set zero values 
		this.zeroX = zeroX;
		this.zeroY = zeroY;
		this.zeroZ = zeroZ;
		
		// Set one point from zero point 
		this.oneX = zeroX - oneX;
		this.oneY = zeroY - oneY;
		this.oneZ = zeroZ - oneZ;
		
		// Update calibrate status 
		calibrate = true;
	}
	
	/**
	 * Return the zero value of X-axis 
	 *
	 * @return the zero value of X-axis
	 */
	public int getZeroX()
	{
		return zeroX;
	}
	
	/**
	 * Return the zero value of Y-axis 
	 *
	 * @return the zero value of Y-axis
	 */
	public int getZeroY()
	{
		return zeroY;
	}
	
	/**
	 * Return the zero value of Z-axis 
	 *
	 * @return the zero value of Z-axis
	 */
	public int getZeroZ()
	{
		return zeroZ;
	}
	
	/**
	 * Set current values 
	 *
	 * @param x x position 
	 * @param y y position
	 * @param z z position
	 */
	protected void setValues(int x, int y, int z)
	{
		// Calculate point according zero 
		this.x = x - zeroX;
		this.y = y - zeroY;
		this.z = z - zeroZ;				
	}
	
	/**
	 * Return the g acceleration value in X 
	 *
	 * @return acceleration in X
	 */
	public int accelX()
	{
		if(oneX != 0)
			return (x*100)/oneX;
		else
			return x; 		
	} 
	
	/**
	 * Return the g acceleration value in X 
	 *
	 * @return acceleration in X
	 */
	public int accelY()
	{
		if(oneY != 0)
			return (y*100)/oneY;
		else
			return y; 		
	} 
	
	/**
	 * Return the g acceleration value in X 
	 *
	 * @return acceleration in X
	 */
	public int accelZ()
	{
		if(oneZ != 0)	
			return (z*100)/oneZ;
		else
			return z; 		
	} 
	
	/**
	 * Return a string representation of the object 
	 *
	 * @return A string representation of the object
	 */
	public String toString()
	{
		return "MWiiAccelerometer : "
			+ " Zero : (" + zeroX + "," + zeroY + "," + zeroZ + "), " 
			+ " One : (" + oneX + "," + oneY + "," + oneZ + ")," 
			+ " Current : (" + x + "," + y + "," + z + ") ]"; 
	} 
}