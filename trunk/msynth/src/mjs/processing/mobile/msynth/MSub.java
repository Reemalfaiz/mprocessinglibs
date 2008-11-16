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
 * Signal Subtract
 *
 * Subtract two generators 
 */
public class MSub extends MBinOperator
{
	/**
	 * Create a subtract generator with the two generators specified
	 *
	 * @param gen1 Base generator
	 * @param gen2 Generator to subtract 
	 */
	public MSub(MGenerator gen1, MGenerator gen2)
	{
		super(gen1,gen2);
	}
	
	/**
	 * Return the next sample product of add the signals 
	 *
	 * @return next sample of the signal
	 */
	public double nextSample()
	{
		// Return the subtract value
		return gen1.nextSample() - gen2.nextSample();
	}
}
