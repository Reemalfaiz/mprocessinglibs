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

import javax.microedition.media.Control;

import javax.microedition.media.protocol.ContentDescriptor;
import javax.microedition.media.protocol.DataSource;
import javax.microedition.media.protocol.SourceStream;

import mjs.processing.mobile.msound.MSound;

/**
 * MSynth implementation of the DataSource use by the MMAPI to create realtime 
 * signals 
 */
class MDataSource extends DataSource implements SourceStream
{
	/**
	 * Current position in the stream
	 */
	private long position;
	
	/**
	 * Sound generator to streaming
	 */
	private MSoundGenerator soundGenerator;
	
	/**
	 * Creates a data source with the sound generator specified
	 *
	 * @param soundGenerator sound to streaming
	 */
	public MDataSource(MSoundGenerator soundGenerator)
	{
		// Create the datasource without locator
		super(null);
		
		// Set the sound generator
		this.soundGenerator = soundGenerator;
	}
	
	/**
	 * Open a connection to the source described by the locator and initiate 
	 * communication.
	 */	
	public void connect()
	{
	}
	
	/**
	 * Close the connection to the source described by the locator and free 
	 * resources used to maintain the connection.
	 */
	public void disconnect()
	{
	}
	
	/**
	 * Get a string that describes the content-type of the media that the 
	 * source is providing.
	 *
	 * @return The sound generator media type
	 */
	public String getContentType()
	{
		// Return the sound generator media type
		return soundGenerator.type();
	}
	
	/**
	 * Get the collection of streams that this source manages. 
	 *
	 * @return The collection of streams for this source.
	 */
	public SourceStream[] getStreams()
	{
		// Create an new array with this object 
		// cause implements the source stream also
		return new SourceStream[] { this };
	}
	
	/**
	 * Initiate data-transfer. 
	 */
	public void start()
	{
	}
	
	/**
	 * Stop the data-transfer.
	 */
	public void stop()
	{
	}	
	
	/**
	 * Get the content type for this stream.
	 *
	 * @return The content descriptor object with the content type
	 */
	public ContentDescriptor getContentDescriptor()
	{
		// Create a content descriptor with the current type
		return new ContentDescriptor(getContentType());
	}
	
	/**
	 * Get the size in bytes of the content on this stream.
	 *
	 * @return The content length in bytes. -1 is returned if the length is 
	 *		not known.
	 */
	public long getContentLength()
	{
		// Length not known
		return -1;
	}
	
	/**
	 * Find out if the stream is seekable. 
	 *
	 * @return Returns an enumerated value to indicate the level of 
	 *		seekability.
	 */
	public int getSeekType()
	{
		// Not seekable
		return NOT_SEEKABLE;
	}
	
	/**
	 * Get the size of a "logical" chunk of media data from the source.
	 *
	 * @return The minimum size of the buffer needed to read a "logical" chunk
	 *			of data from the source. 
	 */
	public int getTransferSize()
	{
		// Size cannot be determined.
		return -1;
	}
	
	/**
	 * Seek to the specified point in the stream.
	 *
	 * @param where The position to seek to.
	 * @return The new stream position.
	 */
	public long seek(long where)
	{
		// Not seek implemented, return the current position
		return position;
	}
	
	/**
	 * Obtain the current position in the stream.
	 *
	 * @return The current position in the stream.
	 */
	public long tell()
	{
		// Return the position on the stream
		return position;
	}
	
	/**
	 * Obtain the object that implements the specified Control interface.
	 *
	 * @param controlType the class name of the Control.
	 */
	public Control getControl(String controlType)
	{
		// Not controls implemented
		return null;
	}
	
	/**
	 * Obtain the collection of Controls from the data source.
	 *
	 * @return the collection of Control objects.
	 */
	public Control[] getControls()
	{
		// If no Control is supported, a zero length array is returned.
		return new Control[0];
	}
	
	/**
	 * Reads up to len bytes of data from the sound generator into an array of 
	 * bytes. An attempt is made to read as many as len bytes, but a smaller 
	 * number may be read. The number of bytes actually read is returned as an
	 * integer.	
	 *
	 * @param b the buffer into which the data is read.
	 * @param off the start offset in array b  at which the data is written.
	 * @param len the maximum number of bytes to read.
	 */
	public int read(byte[] b, int off, int len)
	{	
		// Copy len number of samples to the buffer from the sound generator
		for(int i=0; i<len; i++)
			b[i] = (byte) soundGenerator.nextSample();
			
		// Update the position of the streaming
		position += len;
			
		// Returns the number of bytes readed
		return len;
	}	
	
	/**
	 * Returns a string representation of the generator 
	 *
	 * @param String representation of the generator
	 */
	public String toString()
	{
		return "MDataSource : " + soundGenerator + "," + position;
	}		
}
