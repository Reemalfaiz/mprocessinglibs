/*

	MRTSPServer - RTSP Server for Mobile Processing

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

package mjs.processing.mobile.mrtspserver;

import java.io.InputStream;

/**
 * WAV RTSP Stream 
 */
class MWavStream extends MRTSPStream
{
	/**
	 * Wav File Stream 
	 */
	private InputStream inputStream; 
	
	/**
	 * Name of the file to send  
	 */
	private String fileName;
	
	/**
	 * Create a Wav stream 
	 *
	 * @param session current session 
	 */ 	
	public MWavStream(MRTSPSession session, String fileName)
	{
		// Create a stream with 320 of packet size 
		super(session,320);
		
		// Set file attributes 
		this.fileName = fileName;
	}

	/**
	 * Update the packet content 
	 */
	protected int loadData() throws Exception 
	{
		// If the stream is not yet open 
		if(inputStream == null)
			openFile();
			
		// Read a piece of the file 
		int numBytes = inputStream.read(buffer,headerSize,packetSize);
		
		// If no more data close the stream
		if(numBytes == -1)
			inputStream.close();
		
		// Return the size of the read 			
		return numBytes;
	}
	
	/**
	 * Opem the file to read 
	 */
	private void openFile() throws Exception 
	{		
		// Open the stream 
		inputStream = getClass().getResourceAsStream(fileName);
	}	 
}