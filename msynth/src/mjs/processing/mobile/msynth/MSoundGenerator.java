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

import mjs.processing.mobile.msound.MSound;

/**
 * Sound Generator.
 *
 * This class allows the creation of a sound product of a signal
 */
public abstract class MSoundGenerator implements MGenerator
{
	/**
	 * Channels signals
	 */
	protected MGenerator[] channels;
	
	/**
	 * Duration of the sound
	 */
	protected long duration;
		
	/**
	 * Number of samples by unit of time
	 */
	protected int sampleRate;
	
	/**
	 * Number of channels 
	 */
	protected short numChannels;
	
	/**
	 * Number of bits per each sample
	 */
	protected short bitsPerSample;		
	
	/**
	 * Current position of the stream 
	 */
	private long position;
	
	/**
	 * Creates a sound generator with the number of channels and duration 
	 * specified 
	 *
	 * @param numChannels Number of channels 
	 * @param duration Duration of the sound, if -1 use a streaming sound
	 */
	protected MSoundGenerator(int numChannels, long duration)
	{
		// Set parameters
		sampleRate = 8000;
		bitsPerSample = 8;
	
		// Set the duration of the spund
		this.duration = duration;
	
		// Update the number of channels 
		channels(numChannels);				
	}
	
	/**
	 * Return the number of channels 
	 *
	 * @return number of channels
	 */
	public short channels()
	{
		return numChannels;
	}
	
	/**
	 * Update the number of channels 
	 *
	 * @param numChannels New number of channels
	 */
	public void channels(int numChannels)
	{
		// If the number of channels changed, 
		// create a new array with the old channels 
		if(this.numChannels != numChannels)
		{
			// Create an array with the new number of channels 
			MGenerator[] generators = new MGenerator[numChannels];
			
			// Copy the old channels 
			for(int i=0; i<this.numChannels; i++)
				generators[i] = channels[i];

			// Update the channels array
			this.channels = generators;				
		}
				
		// Update the number of channels 
		// The channels is a short value but to simplify the call 
		// the parameter is receive like int
		this.numChannels = (short) numChannels;
	}
	
	/**
	 * Set the generator for the channel
	 *
	 * @param number Channel number staring at 0
	 * @param generator Generator asigned to the channel
	 */
	public void channel(int number, MGenerator generator)
	{
		// Put the generator in the current array in the position specified
		channels[number] = generator;
	}
	
	/**
	 * Returns the sound data like an array of bytes
	 *
	 * @return sound data bytes
	 */
	public byte[] data()
	{
		// Reset the streaming 
		reset();
		
		// Get the data size of the sound 
		int size = (int) size();
		
		// Create an array of the size of the sound 
		byte data[] = new byte[size];
		
		// Grab the values for each sample
		for(int i=0; i<size; i++)
			data[i] = (byte) nextSample();
				
		// Return the data
		return data;
	}
	
	/**
	 * Return the next value of the sound signal 
	 *
	 * @return  next sample of the signal
	 */
	public double nextSample()
	{
		// Calculate the index of the channel into the array
		// according the position in the stream 
		int index = (int) position % numChannels;	
		
		// Get the sample for the channel
		double sample = channels[index].nextSample();
		
		// Update the position of the streaming 
		position++;
		
		// return the sample value 
		return sample;		
	}
	
	/**
	 * Return the mime type of the sound generate
	 *
	 * @return mime type of the content generate
	 */
	public String type()
	{
		// Return a binary mime type
		return "application/octet-stream";
	}

	/**
	 * Return a platform specific representation sound for the generator
	 *
	 * @param platform specific representation sound for the generator
	 */
	public MSound sound()
	{
		// If not duration specified, use streaming 
		if(duration == -1)
			return new MSound(new MDataSource(this));
		// Grab a sound with the data generate
		else
			return new MSound(data(),type());
	}

	/**
	 * Return the size of the data 
	 *
	 * @return The data size or -1 if is streaming content
	 */	
	public long size()
	{
		// If streaming return -1
		if(duration == -1)
			return -1;
			
		// Return the duration in seconds multiply by the samples per unit
		return (duration/1000) * sampleRate * numChannels;
	}

	/**
	 * Reset the sound generator
	 */	
	public void reset()
	{
		// Reset the values
		position = 0;
	}
	
	/**
	 * Returns a string representation of the generator 
	 *
	 * @param String representation of the generator
	 */
	public String toString()
	{
		// Create a string buffer to create the string representation
		StringBuffer sb = new StringBuffer();
				
		// Add each attribute
		sb.append(" Channels: ");
		sb.append(numChannels);
		
		sb.append("[");
		for(int i=0; i<channels.length; i++)
			sb.append(channels[i] + " ");
		sb.append("]");
				
		sb.append(" SampleRate: ");
		sb.append(sampleRate);
		
		sb.append(" BitsPerSample: ");
		sb.append(bitsPerSample);
		
		sb.append(" Duration: ");
		sb.append(duration);
		
		// Return the string 
		return sb.toString();
	}		
}
