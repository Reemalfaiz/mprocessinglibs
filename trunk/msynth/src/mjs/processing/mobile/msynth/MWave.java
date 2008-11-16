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
 * Wave generator
 */
public abstract class MWave implements MGenerator
{
	/**
	 * Period of the wave 
	 */
	public final static double PI2 = 2 * Math.PI;

	/**
	 * Current time
	 */
	protected double t;
	
	/**
	 * Delta time to slide
	 */
	protected double dt;
		
	/**
	 * Frequency generator
	 */
	protected MGenerator frequency;
	
	/**
	 * Phase generator
	 */
	protected MGenerator phase;
	
	/**
	 * Amplitude generator
	 */	
	protected MGenerator amplitude;
	
	/**
	 * Create a wave signal with the constant parameters specified
	 *
	 * @param frequency Frenquency of the signal 
	 * @param phase Phase of the signal 
	 * @param amplitude Amplitude of the signal 
	 */
	protected MWave(double frequency, double phase, double amplitude)
	{
		// Create a constant generator for each parameter
		this(new MConstant(frequency),
			 new MConstant(phase),
			 new MConstant(amplitude));
	}
	
	/**
	 * Create a wave signal with the generator parameters 
	 *
	 * @param frequency Frenquency of the signal 
	 * @param phase Phase of the signal 
	 * @param amplitude Amplitude of the signal 
	 */	
	protected MWave(MGenerator frequency, MGenerator phase, MGenerator amplitude)
	{
		// Set paremeters
		this.frequency = frequency;
		this.phase = phase;
		this.amplitude = amplitude;

		// Update the delta time to frequency in the sample rate value
		dt(PI2/8000);
	}	
	
	/**
	 * Update the delta time 
	 *
	 * @param dt new delta time
	 */	
	public synchronized void dt(double dt)
	{
		this.dt = dt;
	}
	
	/**
	 * Returns a string representation of the generator 
	 *
	 * @param String representation of the generator
	 */
	public String toString()
	{
		return frequency + "," + phase + "," + amplitude;
	}	
}
