/*

	MVideo - Video Library for Mobile Processing

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

package mjs.processing.mobile.mvideo;

import java.io.*;

import javax.microedition.media.*;
import javax.microedition.media.control.*;

import processing.core.*;

/**
 * This class allows video record.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 *
 * @since 0.3
 */

public class MVideoRecorder extends MVideo
{
	/**
	 * The output stream where the data will be recorded.
	 */ 
	 
	private MBuffer output;

	/**
	 * Location output location where the data will be recorded.
	 *
	 * @since 0.4
	 */
	private String locator;
	
	/**
	 * Record Control.
	 */

	private RecordControl recordControl;

	/**
	 * Creates a sound recorder with the parameters specified
	 *
	 * @param pMIDlet The current midlet.
	 * @param params Video capture format parameters
	 */

	public MVideoRecorder(PMIDlet pMIDlet, String parameters)
	{
		this(pMIDlet,parameters,TRANS_NONE,false);
	}
	
	/**
	 * Creates a sound recorder with the parameters specified
	 *
	 * @param pMIDlet The current midlet.
	 * @param locator Custom locator
	 * @param params Video capture format parameters
	 *
	 * @since 0.6
	 */
	public MVideoRecorder(PMIDlet pMIDlet, String locator, String parameters)
	{
		this(pMIDlet,locator,parameters,TRANS_NONE,false);
	}
		
	/**
	 * Creates a video recorder with the parameters specified
	 *
	 * @param pMIDlet The current midlet.
	 * @param params Video capture format parameters
	 * @param transform the desired transform for the video	 
	 * @param overlay Allow drawing over the video
	 *
	 * @since 0.5
	 */
	public MVideoRecorder(PMIDlet pMIDlet, String parameters, int transform, 
		boolean overlay)
	{
		this(pMIDlet,"capture://audio_video",parameters,transform,overlay);
	}
		
	/**
	 * Creates a video recorder with the parameters specified
	 *
	 * @param pMIDlet The current midlet.
	 * @param locator Custom locator
	 * @param params Video capture format parameters
	 * @param transform the desired transform for the video	 
	 * @param overlay Allow drawing over the video
	 *
	 * @since 0.5
	 */
	public MVideoRecorder(PMIDlet pMIDlet, String locator, String parameters, 
		int transform, boolean overlay)		
	{
		super(pMIDlet,locator + parameters,transform,overlay);

		// Get the RecordControl 
		recordControl = (RecordControl) player.getControl("RecordControl");

		if(recordControl == null)
			throw new RuntimeException("Video Recorder Not Supported");
	}	

	/**
	 * Creates a default video recorder.
	 *
	 * @param pMIDlet The Midlet
	 */
	 
	public MVideoRecorder(PMIDlet pMIDlet)
	{
		this(pMIDlet,"");
	}
	
	/**
	 * Start recording the capture video.
	 *
	 * If the recorder is already playing, startRecord will immediately start the recording.
	 * If the recorder is not already playing, startRecord will not record any media. 
	 * it will put the recording in a "standby" mode. As soon as the record start to playing, 
	 * the recording will start right away.
	 */

	public void startRecord()
	{
		// Create a new thread to avoid deadlocks
		
		new Thread()
		{
			public void run()
			{
				try
				{
					// If control is available create the output and waits for player start
					if(recordControl != null)
					{
						// If locator not specified, use a buffer
						if(locator == null)
						{
							// If the output is not created yet 
							if(output == null)
							{
								output = new MBuffer(pMIDlet);
								recordControl.setRecordStream(output);
							}
							else
								// Reset the current output
								output.reset();
						}
						
						recordControl.startRecord();
					}
				}
				catch(Exception e)
				{
					throw new RuntimeException(e.getMessage());
				}
			}
		}.start();
	}

	/**
	 * Stop recording the capture video.
	 * 
	 * This will not automatically stop the playing it only stops the recording.
	 */

	public void stopRecord()
	{
		try
		{
			// Simply stop the record
			recordControl.stopRecord();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Returns the video recorded like a MMovie
	 *
	 * @return MMovie with the video captured and recorded.
	 */

	public MVideo video()
	{
		// If RecordControl is available, No video available
		if(recordControl == null)
			return null;
	
		try
		{	
			// Convert input to output
			ByteArrayInputStream input = new ByteArrayInputStream(read());

			// Creates a player with the input, ant record type
			Player videoPlayer = Manager.createPlayer(input,recordControl.getContentType());

			// Return a new Sound with the player specified
			return new MMovie(pMIDlet,videoPlayer);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Returns the video recorded like a MMovie
	 *
	 * @return MMovie with the video captured and recorded.
	 */

	public MMovie movie()
	{
		return (MMovie) video();
	}

	/**
	 * Returns the video recorded like a byte array with the data.
	 *
	 * @return Recorded sound like a byte array
	 */

	public byte[] read()
	{
		// If RecordControl is not available
		if(recordControl == null)
			return null;
		
		try
		{	
			// Commit changes
			recordControl.commit();

			// Convert the output to an array
			return output.toByteArray();
		}
		catch(Exception e)
		{
			// Throws an exception if any error occur
			throw new RuntimeException(e.getMessage());
		}
	}	

	/**
	 * Set the record size limit. This limits the size of the recorded media to the number of bytes specified.
	 *
	 * @param size The record size limit in number of bytes.
	 * @return The actual size limit set, -1 if is not possible to record.
	 */

	public int setRecordSizeLimit(int size)
	{
		try
		{
			// Change the size at recordControl if available and return size
			if(recordControl != null)
				return recordControl.setRecordSizeLimit(size);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}

		// No size can be set
		return -1;
	}

	/**
	 * Return the content type of the recorded media. 
	 *
	 * @return The content type of the media.
	 */

	public String contentType()
	{
		return recordControl.getContentType();
	}

	/**
	 * Set the output location where the data will be recorded.
	 *
	 * @param locator The locator specifying where the recorded media will be
	 *			 saved. The locator must be specified as a URL.
	 *
	 * @since 0.4
	 */
	public void setLocation(String locator)
	{
		try
		{
			// Set the location
			this.locator = locator;
			
			// Change the size at recordControl if available and return size
			if(recordControl != null)
				recordControl.setRecordLocation(locator);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Save the video
	 *
	 * @since 0.4
	 */
	public void save()
	{
		try
		{	
			// Commit changes
			recordControl.commit();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}
}
