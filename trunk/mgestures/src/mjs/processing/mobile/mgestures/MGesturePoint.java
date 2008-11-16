/*

	MGestures - Gesture Library for Mobile Processing

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

package mjs.processing.mobile.mgestures;

import java.util.Vector;

/**
 * Gesture Point 
 */
public class MGesturePoint
{
	/**
	 * Coordinate X
	 */
	private int x;

	/**
	 * Coordinate X
	 */
	private int y;
	
	/**
	 * Create a gesture point 
	 *
	 * @param x coordinate X
	 * @param y coordinate Y
	 */
	public MGesturePoint(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the X coordinate 
	 *
	 * @return x coordinate 
	 */
	public int x()
	{
		return x;
	}
	
	/**
	 * Returns the Y coordinate 
	 *
	 * @return y coordinate 
	 */
	public int y()
	{
		return y;
	}
	
	/**
	 * Return the chain code direction of the current point to the 
	 * specified point 
	 *
	 * @param point next point
	 * @return direction  
	 */
	protected int direction(MGesturePoint point)
	{
		// Distances
		int tmpX = point.x() - x;
		int tmpY = point.y() - y;
		 
		// Check Up/Down
		if(tmpX == 0)
			if(tmpY == 0)
				return -1;
			else if(tmpY > 0)
				return 6;
			else 
				return 2;

		// Check Left/Right
		if(tmpY == 0)
			if(tmpX > 0)
				return 0;
			else 
				return 4;
				
		// Check diagonal right				
		if(tmpX > 0)
			if(tmpY < 0)
				return 1;
			else
				return 7;

		// Check diagonal left
		if(tmpX < 0)
			if(tmpY < 0)
				return 3;
			else
				return 5;
				
		// Not direction found 
		return -1;
	}
}
