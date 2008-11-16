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
 * Sine signal generator
 */
public class MSin extends MWave
{
	/**
	 * Create a sine signal with the constant parameters specified
	 *
	 * @param frequency Frenquency of the signal 
	 * @param phase Phase of the signal 
	 * @param amplitude Amplitude of the signal 
	 */
	public MSin(double frequency, double phase, double amplitude)
	{
		super(frequency,phase,amplitude);
	}
	
	/**
	 * Create a sine signal with the generator parameters 
	 *
	 * @param frequency Frenquency of the signal 
	 * @param phase Phase of the signal 
	 * @param amplitude Amplitude of the signal 
	 */	
	public MSin(MGenerator frequency, MGenerator phase, MGenerator amplitude)
	{
		super(frequency,phase,amplitude);
	}	
	
	/**
	 * Return the next sample of the sine signal
	 *
	 * @return next sample of the signal
	 */
	public double nextSample()
	{
		// Calculate sample value
		double sample = Math.sin(phase.nextSample() + t * frequency.nextSample()) 
			* amplitude.nextSample();
			
		// Update the time 
		t += dt;
		
		// Return value
		return sample;
	}
	
	/**
	 * Returns a string representation of the generator 
	 *
	 * @param String representation of the generator
	 */
	public String toString()
	{
		return "MSin : " + super.toString();
	}		
}
