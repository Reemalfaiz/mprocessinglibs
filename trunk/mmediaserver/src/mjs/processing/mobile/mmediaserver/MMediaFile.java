/*

	MMediaServer - Media Server for Mobile Processing

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

package mjs.processing.mobile.mmediaserver;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * File Stream  
 */
class MMediaFile extends InputStream
{
	/**
	 * Current client of the file 
	 */
	private MMediaClient client;
	
	/**
	 * Connection inputstream
	 */
	private DataInputStream input;
	
	/**
	 * One byte buffer use in sinlge byte read
	 */
	private byte[] miniBuffer;
	
	/**
	 * Current Buffer
	 */
	private byte[] currentBuffer;
	
	/**
	 * Data offset
	 */
	private int currentOffset;
	
	/**
	 * Number of bytes availables
	 */
	private int currentBytes;
	
	/**
	 * Total number of bytes readed
	 */
	private int bytesReaded;  
	
	/** 
	 * Create the media file with the size especified and the input stream to 
	 * read from   
	 *   
	 * @param client Client of the file 
	 * @param input the input stream of the file  
	 */ 
	public MMediaFile(MMediaClient client, DataInputStream input)
	{
		this.client = client;
		this.input = input;		
		
		// Current Buffers
		currentBuffer = new byte[512];
		miniBuffer = new byte[0];
	}
	
	/**
	 * Read a single byte of the stream 
	 *
	 * @return single byte of the stream
	 */
	public int read() throws IOException
	{
		// Read a buffer of one single byte 
		if(read(miniBuffer,0,1) == -1)
			return -1;
		else
			return miniBuffer[0];
	}
	
	/**
	 * Read a buffer 
	 *
	 * @param buffer The buffer to read in
	 * @param offset The first index 
	 * @param length Number of data to read 
	 */
	public int read(byte[] buffer, int offset, int length) throws IOException
	{
		// If not bytes availables, return -1 
		if(currentBytes == -1)
			return -1;
		// If the bytes are already read, try to read more 
		else if(currentBytes == 0)
		{
			// Read the chunk size, init offset 
			currentBytes = input.readInt();
			currentOffset = 0;

			// If is the end of the stream, return -1
			if(currentBytes == -1)
				return -1;
				
			// Read all the buffer with the available bytes  
			input.readFully(currentBuffer,0,currentBytes);
		}

		// Calculate the max length of byte to read 		
		int maxLength = currentBytes < length ? currentBytes : length;
						
		// Copy the bytes from the current buffer into the buffer passed 
		System.arraycopy(currentBuffer,currentOffset,buffer,offset,maxLength);
		
		// Update the current state of the buffer 
		currentOffset += maxLength;
		currentBytes -= currentOffset;
		
		// Update the total of bytes readed 
		bytesReaded += maxLength;
		
		// Fire the data available event 
		client.enqueueLibraryEvent(
			client,MMediaClient.EVENT_FILE_PROGRESS,new Integer(bytesReaded));

		// Return the num of bytes readed 		
		return maxLength;
	}
}