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

import java.util.Enumeration;
import java.util.Vector;

/**
 * Binary operation between two generators
 */
public abstract class MBinOperator implements MGenerator
{
	/**
	 * First generator
	 */
	protected MGenerator gen1;

	/**
	 * Second generator
	 */
	protected MGenerator gen2;
	
	/**
	 * Create a subtract generator with the two generators specified
	 *
	 * @param gen1 Base generator
	 * @param gen2 Generator to subtract 
	 */
	protected MBinOperator(MGenerator gen1, MGenerator gen2)
	{
		this.gen1 = gen1;
		this.gen2 = gen2;
	}	
	
	/**
	 * Returns a string representation of the generator 
	 *
	 * @param String representation of the generator
	 */
	public String toString()
	{
		return gen1 + "," + gen2;
	}		
}
