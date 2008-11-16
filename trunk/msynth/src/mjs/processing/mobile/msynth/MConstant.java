/*

	MSynth - Sound Synthesis Library for Mobile Processing

	Copyright (c) 2007 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.msynth;

/**
 * A constant signal
 */
public class MConstant implements MGenerator
{
	/**
	 * Signal value
	 */
	private double value;
	
	/**
	 * Create a constant signal with the value specified
	 *
	 * @param value Value of the signal
	 */
	public MConstant(double value)
	{
		this.value = value;
	}
	
	/**
	 * Updates the current value
	 *
	 * @param value New value
	 */	
	public void value(double value)
	{
		this.value = value;
	}
	
	/**
	 * Return the current value
	 *
	 * @param current value
	 */	
	public double value()
	{
		return value;
	}
	
	/**
	 * Return the value of the constant
	 *
	 * @return next sample of the signal
	 */	
	public double nextSample()
	{
		return value;
	}
	
	/**
	 * Returns a string representation of the generator 
	 *
	 * @param String representation of the generator
	 */
	public String toString()
	{
		return "MConstant : " + value;
	}
}
