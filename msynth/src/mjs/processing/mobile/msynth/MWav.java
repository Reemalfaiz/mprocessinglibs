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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Wav Sound Generator
 *
 * Creates a sound generator for the signals in WAV format 
 */
public class MWav extends MSoundGenerator
{
	/**
	 * Position in the file
	 */
	private long position;
	
	/**
	 * WAV file header
	 */
	private byte[] header;
	
	/**
	 * Creates a wav generator with the signal specified
	 *
	 * @param parent Signal to transform
	 */
	public MWav(MGenerator parent)
	{
		// Create a streaming sound generator 
		this(parent,-1);
	}	
	
	/**
	 * Creates a wav generator with the signal and duration specified
	 *
	 * @param parent Signal to transform
	 * @param duration Duration of sound in milliseconds
	 */
	public MWav(MGenerator parent, long duration)
	{
		// Create a sound generator with one channel and the duration 
		super(1,duration);
		
		// Set like first channel the parent signal
		channel(0,parent);
	}
	
	/**
	 * Creates a wav generator with the number of channels and duration 
	 * specified 
	 *
	 * @param numChannels Number of channels
	 * @param duration Duration of sound in milliseconds
	 */	 
	public MWav(int numChannels, long duration)
	{
		super(numChannels,duration);
	}
	
	/**
	 * Convert the int data to endian representation
	 *
	 * @param data little endian int 
	 * @return endia representation
	 */
	private int endianInt(int data)
	{
		return (data >>> 24) | (data << 24) 
			| ((data << 8) & 0x00FF0000) | ((data >> 8) & 0x0000FF00);	
	}
	
	/**
	 * Convert the short data to endian representation
	 *
	 * @param data little endian short
	 * @return endia representation
	 */
	private short endianShort(short data)
	{
		// Use the int conversion and get short part
		return (short) (endianInt(data) >> 16);
	}
	
	/**
	 * Get the header of the WAV file
	 *
	 * Based on :
	 * http://ccrma.stanford.edu/CCRMA/Courses/422/projects/WaveFormat/
	 *
	 * @return header of the wav file
	 */	
	private byte[] getHeader()
	{
		// If no header created, create one 
		if(header == null)
		{
			// Create the output streams to write into an array
			ByteArrayOutputStream array = new ByteArrayOutputStream();
			DataOutputStream output = new DataOutputStream(array);
		
			// NumSamples = DataSize / NumChannels
			int dataSize = (int) super.size();
			int numSamples = dataSize / numChannels;
			
			try
			{
				// RIFF Header
				// ChunkID = "RIFF"
				output.writeInt(0x52494646); 			
				// ChunkSize = 36 + SubChunk2Size
				// ChunkSize = 4 + (8 + SubChunk1Size) + (8 + SubChunk2Size)
				output.writeInt(36 + dataSize);
				// Format = "WAVE"	
				output.writeInt(0x57415645);
				
				// WAV Format (format|data)
				
				// Subchunk1ID = "FMT "
				output.writeInt(0x666D7420);
				// Subchunk1Size = 16 for PCM
				output.writeInt(endianInt(16));
				// AudioFormat = 1 for PCM
				output.writeShort(endianShort((short) 1));
				// NumChannels = 1 Mono, 2 Stereo ...
				output.writeShort(endianShort(numChannels));
				// SampleRate = 8000, 44100, etc.
				output.writeInt(endianInt(sampleRate));
				// ByteRate = SampleRate * NumChannels * BitsPerSample/8
				output.writeInt(endianInt(sampleRate * numChannels * bitsPerSample/8));
				// BlockAlign = NumChannels * BitsPerSample/8
				output.writeShort(endianShort((short) (numChannels * bitsPerSample/8)));
				// BitsPerSample = (8 bits = 8), (16 bits = 16), etc.
				output.writeShort(endianShort(bitsPerSample));
				
				// Subchunk2ID = "DATA"
				output.writeInt(0x64617461);
				// Subchunk2Size = NumSamples * NumChannels * BitsPerSample/8
				output.writeInt(endianInt(numSamples * numChannels * bitsPerSample/8));		
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			// Set the header
			header = array.toByteArray();
		}
		
		// Return the header for the sound file
		return header;
	}	
	
	/**
	 * Return the next sample of the wav file
	 *
	 * @return next sample of the signal
	 */	
	public double nextSample()
	{
		// The sample value
		double sample;
		
		// Calculate the header if not available yet
		getHeader();
		
		// If not header send, send a header value
		if(position < header.length)
			sample = header[(int) position];
		// Send the parent value under the byte limits
		else
			sample = (Byte.MAX_VALUE
				+ (super.nextSample() * Byte.MAX_VALUE));
		
		// Update the position 
		position++;
		
		// Send the sample
		return sample;
	}
	
	/**
	 * Return the mime type for the wav file
	 *
	 * @return mime type of the wav file
	 */
	public String type()
	{
		return "audio/x-wav";
	}	

	/**
	 * Return the size of the content 
	 *
	 * @return The data size or -1 if is streaming content
	 */	
	public long size()
	{
		// Get the data size of the sound
		long dataSize = super.size();
		
		// If the content is streaming return streaming value
		if(dataSize == -1)
			return -1;
			
		// Get the file header
		getHeader();
		
		// Return the length of the file header plus data 
		return header.length + dataSize;
	}
	
	/**
	 * Reset the sound generator
	 */	
	public void reset()
	{
		position = 0;
	}
	
	/**
	 * Returns a string representation of the generator 
	 *
	 * @param String representation of the generator
	 */
	public String toString()
	{
		return "MWav : " + super.toString();
	}		
}
