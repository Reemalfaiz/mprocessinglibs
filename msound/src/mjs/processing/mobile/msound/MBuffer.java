/*

	MSound - Sound Library for Mobile Processing

	Copyright (c) 2005-2007 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.msound;

import java.io.*;

import processing.core.*;

/**
 * This class store the audio buffer while the library is recording.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 * @since 0.5
 */

class MBuffer extends ByteArrayOutputStream
{
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
		super(size);
		
		this.pMIDlet = pMIDlet;
	}
	 
	/**
	 * Writes the specified byte to this output stream. 
	 * The general contract for write is that one byte is written to the output stream. 
	 * The byte to be written is the eight low-order bits of the argument b. 
	 * The 24 high-order bits of b are ignored.
	 * 
	 * @param b the byte.
	 */

	public void write(int b)
	{
		super.write(b);
		
		// Send event to MIDlet
		if(pMIDlet != null)
		{
			byte[] bufferEvent = new byte[]{ (byte) b };
			pMIDlet.enqueueLibraryEvent(this,MSound.EVENT_RECORD_DATA_AVAILABLE,bufferEvent);
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

	public void write(byte b[], int off, int len)
	{
		super.write(b,off,len);
		
		// Send event to MIDlet
		if(pMIDlet != null)
		{
			byte[] bufferEvent = new byte[len];
			System.arraycopy(b,off,bufferEvent,0,len);
			pMIDlet.enqueueLibraryEvent(this,MSound.EVENT_RECORD_DATA_AVAILABLE,bufferEvent);
		}
	}
}
