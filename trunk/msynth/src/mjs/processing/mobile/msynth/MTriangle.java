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
 * Triangle Wave
 *
 * The triangle wave
 */
public class MTriangle extends MWave
{
	/**
	 * Create a triangle signal with the constant parameters specified
	 *
	 * @param frequency Frenquency of the signal 
	 * @param phase Phase of the signal 
	 * @param amplitude Amplitude of the signal 
	 */
	public MTriangle(double frequency, double phase, double amplitude)
	{
		super(frequency,phase,amplitude);
	}
	
	/**
	 * Create a triangle signal with the generator parameters 
	 *
	 * @param frequency Frenquency of the signal 
	 * @param phase Phase of the signal 
	 * @param amplitude Amplitude of the signal 
	 */	
	public MTriangle(MGenerator frequency, MGenerator phase, MGenerator amplitude)
	{
		super(frequency,phase,amplitude);
	}	
	
	/**
	 * Return the next sample of the signal
	 *
	 * @return next sample of the signal
	 */
	public double nextSample()
	{
		// The sample value
		double sample;
		
		// Calculate values
		double amp = amplitude.nextSample();
		double angle = (phase.nextSample() + t * frequency.nextSample()) % PI2;
		
		sample = -amp + (2 * amp)/Math.PI * angle;

		// Calculate sample 
		if(angle > Math.PI)
			sample = 2 * amp - sample;

		// Update the time 
		t += dt;
								
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
		return "MTriangle : " + super.toString();
	}		
}
