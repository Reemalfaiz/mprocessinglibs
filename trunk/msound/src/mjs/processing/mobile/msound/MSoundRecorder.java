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

import javax.microedition.media.*;
import javax.microedition.media.control.*;

import processing.core.*;

/**
 * This class allows sound capture and record.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 * @since 0.2
 */

public class MSoundRecorder extends MSound
{
	/**
	 * The output stream where the data will be recorded.
	 */ 
	 
	private MBuffer output;

	/**
	 * Location output location where the data will be recorded.
	 *
	 * @since 0.6
	 */
	private String locator;

	/**
	 * Record Control.
	 */

	private RecordControl recordControl;

	/**
	 * Creates a sound recorder with the parameters specified
	 *
	 * @param params Sound capture format parameters
	 *
	 * @since 0.5
	 */

	public MSoundRecorder(String parameters)
	{
		try
		{
			// Check parameters 
			if(parameters != null && parameters.trim().length() != 0)
				parameters = "?" + parameters;
			else
				parameters = "";
				
			// Create the player to capture audio
			player = Manager.createPlayer("capture://audio" + parameters);

			// Set like current player
			setPlayer(player);

			// Get the RecordControl 
			recordControl = (RecordControl) player.getControl("RecordControl");
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Creates a default sound recorder.
	 */
	 
	public MSoundRecorder()
	{
		this("");
	}

	/**
	 * Creates a default sound recorder.
	 *
	 * @param pMIDlet The Midlet
	 */
	 
	public MSoundRecorder(PMIDlet pMIDlet)
	{
		this();
		this.pMIDlet = pMIDlet;
	}

	/**
	 * Creates a default sound recorder.
	 *
	 * @param pMIDlet The Midlet
	 * @param params Sound capture format parameters
	 *
	 * @since 0.5
	 */
	 
	public MSoundRecorder(PMIDlet pMIDlet, String parameters)
	{
		this(parameters);
		this.pMIDlet = pMIDlet;
	}

	/**
	 * Start recording the capture sound.
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
						// If locator  is not used, check output
						if(locator == null)
						{
							// If the output is not created yet
							if(output == null)
								output = new MBuffer(pMIDlet);
							else
								// Reset the current output
								output.reset();

							recordControl.setRecordStream(output);
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
	 * Stop recording the capture sound.
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
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Returns the audio recorded like a Sound
	 *
	 * @return Sound with the audio captured and recorded.
	 */

	public MSound getSound()
	{
		// If RecordControl is available, No sound available
		if(recordControl == null)
			return null;
	
		try
		{	
			// Save the sound
			save();
		
			// The native player
			Player soundPlayer;
			
			// Creates a player with the input, ant record type
			if(locator == null)
			{
				// Convert input to output
				ByteArrayInputStream input = new ByteArrayInputStream(read());
				soundPlayer = Manager.createPlayer(input,recordControl.getContentType());
			}
			else
				soundPlayer = Manager.createPlayer(locator);

			// Return a new Sound with the player specified
			return new MSound(soundPlayer);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Returns the audio recorded like a byte array with the data.
	 *
	 * @return Recorded sound like a byte array
	 * 
	 * @since 0.4
	 */

	public byte[] read()
	{
		// If RecordControl is not available
		if(recordControl == null)
			return null;

		// If the sound was 
		if(locator != null)
			throw new RuntimeException("Open the file using MFiles");
		
		try
		{	
			// Save the sound
			save();

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
	 *
	 * @since 0.5
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
	 * @since 0.6
	 */
	public void setLocation(String locator)
	{
		try
		{
			// Set the location
			this.locator = locator;
			
			// Change the location to storage the  recordControl if available and return size
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
	 * Return the location of the sound, null if no location was specified yet
	 *
	 * @return the location of the sound, null if no location was specified yet
	 *
	 * @since 0.6
	 */
	public String location()
	{
		return locator;
	}

	/**
	 * Save the sound to the storage location or buffer
	 *
	 * @since 0.6
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
