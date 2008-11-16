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

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import processing.core.PException;

/**
 * Wavetable base on wav data file
 */
public class MWavTable extends MWaveTable
{
	/**
	 * Create an empty generator
	 *
	 * @param data Wav file content
	 */
	public MWavTable(byte[] data)
	{
		super(null);
		
		// Create the samples with the data
		createSamples(data);
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
	 * Create the samples from the wav file
	 *
	 * @param data Wav file content
	 */
	private void createSamples(byte[] data)
	{
		try
		{
			// Create the streams to read the data 
			ByteArrayInputStream array = new ByteArrayInputStream(data);	
			DataInputStream input = new DataInputStream(array);
			
			// RIFF Header
			// ChunkID = "RIFF"
			int chunkID = input.readInt();
			// ChunkSize = 36 + SubChunk2Size
			// ChunkSize = 4 + (8 + SubChunk1Size) + (8 + SubChunk2Size)
			int chunkSize = input.readInt();
			// Format = "WAVE"	
			int format = input.readInt();
			
			// WAV Format (format|data)
			
			// Subchunk1ID = "FMT "
			int subchunk1ID = input.readInt();
			// Subchunk1Size = 16 for PCM
			int subchunk1Size = endianInt(input.readInt());
			// AudioFormat = 1 for PCM
			short audioFormat = endianShort(input.readShort());
			// NumChannels = 1 Mono, 2 Stereo ...
			short numChannels = endianShort(input.readShort());
			// SampleRate = 8000, 44100, etc.
			int sampleRate = endianInt(input.readInt());
			// ByteRate = SampleRate * NumChannels * BitsPerSample/8
			int byteRate = endianInt(input.readInt());
			// BlockAlign = NumChannels * BitsPerSample/8
			short blockAlign = endianShort(input.readShort());
			// BitsPerSample = (8 bits = 8), (16 bits = 16), etc.
			short bitsPerSample = endianShort(input.readShort());
			
			// Subchunk2ID = "DATA"
			int subchunk2ID = input.readInt();
			// Subchunk2Size = NumSamples * NumChannels * BitsPerSample/8
			int subchunk2Size = endianInt(input.readInt());
			
			// Create the sample array with the size of the data
			double[] samples = new double[subchunk2Size];
			
			// Maximun value in double format
			double maxValue = Byte.MAX_VALUE;
			
			// Load the data from the wav to the samples array
			for(int i=0; i<subchunk2Size; i++)
			{
				// Convert byte to normalized
				samples[i] = (input.readByte() - Byte.MAX_VALUE)/maxValue;
				
				// If sample is minor than -1 is the up wave form 
				// add 2 to normalize
				if(samples[i] < -1.0)	
					samples[i] += 2.0;
			}
				
			// Update the sample information of the table
			samples(samples);
		}
		catch(IOException e)
		{
			throw new PException(e);
		}
	}
	
	/**
	 * Returns a string representation of the generator 
	 *
	 * @param String representation of the generator
	 */
	public String toString()
	{
		return "MWavTable : " + super.toString();
	}		
}
