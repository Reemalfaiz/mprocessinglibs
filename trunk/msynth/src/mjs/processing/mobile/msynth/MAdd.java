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
 * Signal Addition 
 *
 * Adds two or more generators 
 */
public class MAdd extends MBinOperator
{
	/**
	 * Generators to add
	 */
	private Vector generators;
	
	/**
	 * Create an empty generator
	 */
	public MAdd()
	{
		// Create the bin operation without generators
		this(null,null);
	}

	/**
	 * Create a generator with the two generators specified
	 *
	 * @param gen1 Base generator
	 * @param gen2 Generator to subtract 
	 */
	public MAdd(MGenerator gen1, MGenerator gen2)
	{
		// Create the binary operation with the two generators
		super(gen1,gen2);
		
		// Create an empty generator list
		generators = new Vector();		
		
		// Put the generators in the vector
		if(gen1 != null)
			put(gen1);

		// Put the generators in the vector			
		if(gen2 != null)
			put(gen2);
	}
	
	/**
	 * Put the generator in the list to add
	 *
	 * @param generator Signal to add
	 */
	public void put(MGenerator generator)
	{
		// If the generator is not present yet, add it
		if(!generators.contains(generator))
			generators.addElement(generator);
	}
	
	/**
	 * Removes the generator specified
	 *
	 * @param generator Signal to remove from the addition 
	 */	
	public void remove(MGenerator generator)
	{
		generators.removeElement(generator);
	}
	 
	/**
	 * Return the next sample product of add the signals 
	 *
	 * @return next sample of the signal
	 */
	public double nextSample()
	{
		// Temporal value
		double sample = 0.0;
		
		// Add to the sample all the generators signals
		for(Enumeration e = generators.elements(); e.hasMoreElements(); )
			sample += ((MGenerator) e.nextElement()).nextSample();
			
		// Return the sample
		return sample;
	}
	
	/**
	 * Returns a string representation of the generator 
	 *
	 * @param String representation of the generator
	 */
	public String toString()
	{
		return "MAdd : " + generators;
	}	
}
