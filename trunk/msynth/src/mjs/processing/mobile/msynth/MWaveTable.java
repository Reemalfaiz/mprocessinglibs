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
 * Wavetable 
 *
 * A store wave ready to use 
 */
public class MWaveTable implements MGenerator
{
	/**
	 * Samples values
	 */
	private double[] samples;
	
	/**
	 * Current position on the streaming
	 */
	private int position;
	
	/**
	 * Create a wavetable with the samples specified 
	 *
	 * @param samples Samples of the table
	 */
	public MWaveTable(double[] samples)
	{
		this.samples = samples;
	}
	
	/**
	 * Update the samples of the table
	 *
	 * @param samples The samples of the table
	 */	 
	public void samples(double[] samples)
	{
		this.samples = samples;
	}

	/**
	 * Return the samples of the table
	 *
	 * @return samples of the table
	 */	 
	public double[] samples()
	{
		return samples;
	}

	/**
	 * Return the next sample of the sound
	 *
	 * @return next sample of the signal
	 */
	public double nextSample()
	{
		// Get the sample to send 
		double sample = samples[position];
		
		// Update the position
		position++;
		
		// Reset the position to zero if end is reach 
		if(position >= samples.length)
			position = 0;
			
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
		return " Samples: " + samples.length + ", Position:" + position;
	}
}
