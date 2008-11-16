/*

	MVideo - Video Library for Mobile Processing

	Copyright (c) 2005-2006 Mary Jane Soft - Marlon J. Manrique
	
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
	
*/

package mjs.processing.mobile.mvideo;

import java.io.*;

import processing.core.*;

/**
 * This class store the audio buffer while the library is recording.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 *
 * @since 0.3
 */

class MBuffer extends OutputStream
{
	/**
	 * The data recordered
	 */
	 
	private byte[] bufferData;

	/**
	 * The number of valid bytes in the buffer
	 */

	private int count;

	/**
	 * Processing MIDlet.
	 */

	private PMIDlet pMIDlet;

	/**
	 * Creates a empty MBuffer 
	 */

	public MBuffer(PMIDlet pMIDlet)
	{
		this(128,pMIDlet);
	}

	/**
	 * Creates a new MBuffer with a buffer capacity of the specified size, in bytes.
	 *
	 * @param size the initial size
	 */

	public MBuffer(int size, PMIDlet pMIDlet)
	{
		bufferData = new byte[size];
		this.pMIDlet = pMIDlet;
	}
	 
	/**
	 * Writes the specified byte to this output stream. 
	 * The general contract for write is that one byte is written to the output stream. 
	 * The byte to be written is the eight low-order bits of the argument b. 
	 * The 24 high-order bits of b are ignored.
	 * 
	 * @param b the byte.
	 * @exception IOException if an I/O error occurs. In particular, an IOException may be 
	 * 						thrown if the output stream has been closed.
	 */

	public synchronized void write(int b) throws IOException
	{
		if(count >= bufferData.length)
		{
			byte[] newBufferData = new byte[bufferData.length << 1];
			System.arraycopy(bufferData,0,newBufferData,0,count);
			bufferData = newBufferData;
		}

		bufferData[count] = (byte) b;
		count++;

		// Send event to MIDlet
		if(pMIDlet != null)
		{
			byte[] bufferEvent = new byte[]{ (byte) b };
			pMIDlet.enqueueLibraryEvent(this,MVideo.EVENT_RECORD_DATA_AVAILABLE,bufferEvent);
		}
	}

	/**
	 * Writes len bytes from the specified byte array starting at offset off to this 
	 * byte array output stream.
	 *
     * @param   b     the data.
	 * @param   off   the start offset in the data.
	 * @param   len   the number of bytes to write.
	 */

	public synchronized void write(byte b[], int off, int len) throws IOException
	{
		int newCount = count + len;

		if(newCount >= bufferData.length)
		{
			byte[] newBufferData = new byte[Math.max(bufferData.length << 1,newCount)];
			System.arraycopy(bufferData,0,newBufferData,0,count);
			bufferData = newBufferData;
		}

		System.arraycopy(b,off,bufferData,count,len);
		count = newCount;

		// Send event to MIDlet
		if(pMIDlet != null)
		{
			byte[] bufferEvent = new byte[len];
			System.arraycopy(b,off,bufferEvent,0,len);
			pMIDlet.enqueueLibraryEvent(this,MVideo.EVENT_RECORD_DATA_AVAILABLE,bufferEvent);
		}
	}

	/**
	 * Return a array with all the data readed in this buffer.
	 * 
	 * @return the current contents as a byte array.
	 */

	public synchronized byte[] toByteArray()
	{
		byte[] byteArray = new byte[count];
		System.arraycopy(bufferData,0,byteArray,0,count);

		return byteArray;
	}

	/**
	 * Resets the count field of this byte array output stream to zero, 
	 * so that all currently accumulated output in the output stream is discarded. 
	 * The output stream can be used again, reusing the already allocated buffer space.
	 */

	public void reset()
	{
		count = 0;
	}
}
