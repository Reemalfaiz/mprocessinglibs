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
import javax.microedition.media.protocol.DataSource;

import processing.core.*;

/**
 * MSound.
 * 
 * Class for storing and playing sounds
 *
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */

public class MSound implements PlayerListener
{
	/**
	 * Started event.
	 *
	 * @value 1
	 * @since 0.4
	 */ 

	public static final int EVENT_STARTED = 1;

	/**
	 * Stopped event.
	 *
	 * @value 2
	 * @since 0.4
	 */ 

	public static final int EVENT_STOPPED = 2;

	/**
	 * Event End of Media.
	 *
	 * @value 3
	 * @since 0.4
	 */ 

	public static final int EVENT_END_OF_MEDIA = 3;

	/**
	 * Event Closed.
	 *
	 * @value 4
	 * @since 0.4
	 */ 

	public static final int EVENT_CLOSED = 4;

	/**
	 * Event Error.
	 *
	 * @value 5
	 * @since 0.4
	 */ 

	public static final int EVENT_ERROR = 5;
	
	/**
	 * Event Record Started.
	 *
	 * @value 6
	 * @since 0.4
	 */ 

	public static final int EVENT_RECORD_STARTED = 6;

	/**
	 * Event Record Stopped.
	 *
	 * @value 7
	 * @since 0.4
	 */ 

	public static final int EVENT_RECORD_STOPPED = 7;

	/**
	 * Event Record Error.
	 *
	 * @value 8
	 * @since 0.4
	 */ 

	public static final int EVENT_RECORD_ERROR = 8;

	/**
	 * Event Record Data Available.
	 *
	 * @value 9
	 * @since 0.5
	 */ 

	public static final int EVENT_RECORD_DATA_AVAILABLE = 9;

	/**
	 * Event Volume Changed.
	 *
	 * @value 10
	 * @since 0.5
	 */ 

	public static final int EVENT_VOLUME_CHANGED = 10;	 

	/**
	 * Event Buffering Started.
	 *
	 * @value 11
	 * @since 0.5
	 */ 

	public static final int EVENT_BUFFERING_STARTED  = 11;	 

	/**
	 * Event Buffering Stopped.
	 *
	 * @value 12
	 * @since 0.5
	 */ 

	public static final int EVENT_BUFFERING_STOPPED  = 12;	 

	/**
	 * Event Device Available.
	 *
	 * @value 13
	 * @since 0.5
	 */ 

	public static final int EVENT_DEVICE_AVAILABLE  = 13;
	
	/**
	 * Event Device Unavailable.
	 *
	 * @value 14
	 * @since 0.5
	 */ 

	public static final int EVENT_DEVICE_UNAVAILABLE  = 14;

	/**
	 * Event Duration Updated.
	 *
	 * @value 15
	 * @since 0.5
	 */ 

	public static final int EVENT_DURATION_UPDATED  = 15;

	/**
	 * Sopped event.
	 *
	 * @value 16
	 * @since 0.5
	 */ 

	public static final int EVENT_STOPPED_AT_TIME = 16;

	/**
	 * Event Size Changed. Only for MVideo compatibility.
	 *
	 * @value 10
	 * @since 0.5
	 */ 

	public static final int EVENT_SIZE_CHANGED = 17;	 

	/**
	 * Processing MIDlet.
	 */

	protected PMIDlet pMIDlet;
	
	/**
	 * Sound Player.
	 */

	protected Player player;

	/**
	 * Volume Control.
	 */

	protected VolumeControl volumeControl;

	/**
	 * Creates a sound with the resource or locator specified.
	 *
	 * @param locator A locator string in URI syntax that describes the media content or resource route.
	 */

	public MSound(String locator)
	{
		try
		{
			// If is a URL, load the sound from the server
			// or is a File, load from the filesystem
			if(locator.startsWith("http://") || locator.startsWith("file://"))
			{
				player = Manager.createPlayer(locator);
			}
			else
			{
				// If is not URL, load the sound from the resources, try to get the media type
				// and creates the player			
				
				InputStream inputStream = getClass().getResourceAsStream("/" + locator);

				// If resource is not found, throws an exception
				if(inputStream == null)
					throw new RuntimeException("File not Found");

				player = Manager.createPlayer(inputStream,MSoundManager.getMediaType(locator));
			}

			setPlayer(player);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Creates a sound with the resource or locator specified.
	 *
	 * @param locator A locator string in URI syntax that describes the media content or resource route.
	 * @param pMIDlet The current midlet.
	 * 
	 * @since 0.4
	 */

	public MSound(String locator, PMIDlet pMIDlet)
	{
		this(locator);
		
		this.pMIDlet = pMIDlet;
	}	

	/**
	 * Creates a player with the data and mime type specified
	 *
	 * @param data The sound data 
	 * @param type mime type of the data
	 *
	 * @since 0.7
	 */
	public MSound(byte[] data, String type)
	{
		try
		{
			// Create a inputstream with the data specified 
			InputStream input = new ByteArrayInputStream(data);
			
			// Create a player for the type
			Player player = Manager.createPlayer(input,type);
			
			// Set the player
			setPlayer(player);
		}
		catch(Exception e)
		{
			// If any error throw a Processing exception
			throw new PException(e);
		}
	}
	
	/**
	 * Creates a player with the datasource specified
	 *
	 * @param dataSource The DataSource that provides the media content.
	 *
	 * @since 0.7
	 */
	public MSound(DataSource dataSource)
	{
		try
		{
			// Create a player for the datasource
			Player player = Manager.createPlayer(dataSource);
			
			// Set the player
			setPlayer(player);
		}
		catch(Exception e)
		{
			// If any error throw a Processing exception
			throw new PException(e);
		}		
	}

	/**
	 * Creates a sound represented by the player specified.
	 *
	 * @param player Sound Player
	 *
	 * @since 0.2
	 */

	protected MSound(Player player)
	{
		setPlayer(player);
	}

	/**
	 * Creates a sound represented by the player specified.
	 *
	 * @param player Sound Player
	 * @param pMIDlet The current midlet.
	 *
	 * @since 0.4
	 */

	protected MSound(Player player, PMIDlet pMIDlet)
	{
		this(player);
		this.pMIDlet = pMIDlet;
	}

	/**
	 * Creates a empty sound without a player.
	 *
	 * @param player Sound Player
	 *
	 * @since 0.4
	 */

	protected MSound()
	{
	}
	
	/**
	 * Creates a empty sound without a player.
	 *
	 * @param player Sound Player
	 * @param pMIDlet The current midlet.	 
	 *
	 * @since 0.4
	 */

	protected MSound(PMIDlet pMIDlet)
	{
		this.pMIDlet = pMIDlet;
	}

	/**
	 * Set the current player
	 *
	 * @param player Sound Player
	 *
	 * @since 0.4
	 */

	protected void setPlayer(Player player)
	{
		// Set the player
		this.player = player;
		
		// Remove volumeControl
		volumeControl = null;

		try
		{
			// Load all the resources and stay ready to start
			player.realize();			

			// Listen events
			player.addPlayerListener(this);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Return the current player
	 *
	 * @return Sound Player
	 *
	 * @since 0.4
	 */

	public Player getPlayer()
	{
		return player;
	}

	/**
	 * Plays the sound once.
	 */

	public void play()
	{
		try
		{
			// Start playing
			player.start();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Plays the sound continously
	 */

	public void loop()
	{
		try
		{
			// Set infinite Loop and play
			player.setLoopCount(-1);
			play();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Stops the sound from looping.
	 * If the sound is currently playing, it will finish and will not begin again.
	 */
	 
	public void noLoop()
	{
		try
		{
			// Simply stop the player
			player.stop();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Pauses the sound playback.
	 * If the sound is started again, it will continue to play from the position where it was paused.
	 */

	public void pause()
	{
		try
		{
			// Simply stop, the next play will start from the stop point
			player.stop();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Stops the sound playback.
	 */

	public void stop()
	{
		try
		{
			// Simply stop
			player.stop();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Sets the volume of the sound playback where setting the parameter to 100 is the sound's actual loudness 
	 * with descending numbers decreasing the volume until the parameter reaches 0,
	 * which is the smallest valid value.
	 *
	 * @param amp The new volume specified in the level scale.
	 */

	public void volume(int amp)
	{
		// To change the volume we need the Volume Control

		// If is not control yet, get the control could return null if the control 
		// is not available
		if(volumeControl == null)
			volumeControl = (VolumeControl) player.getControl("VolumeControl");

		// If is control available (phones don't have all the controls)
		// change the volume
		if(volumeControl != null)	
			volumeControl.setLevel(amp);
	}

	/**
	 * Returns the length of the sound in seconds.
	 * 
	 * @return Length of the sound in seconds.
	 */

	public int duration()
	{
		// The duration is returned in microseconds convert to seconds
		
		return (int) (player.getDuration() / 1000000);
	}

	/**
	 * Returns the current position of sound playback in seconds.
	 * 
	 * @return Current position of sound playback in seconds.
	 */

	public int time()
	{
		// The time is returned in microseconds to seconds
		
		return (int) (player.getMediaTime() / 1000000);
	}	

	/**
	 * This method is called to deliver an event to a registered listener when a Player event is observed.
	 *
	 * @param player The player which generated the event.
	 * @param event The event generated as defined by the enumerated types.
	 * @param eventData The associated event data.
	 * 
	 * @since 0.4
	 */

	public void playerUpdate(Player player, String event, Object eventData)
	{
		// If not MIDlet was specified do nothing
		if(pMIDlet == null)
			return;
			
		// Not event
		int pEvent = 0;

		// Convert player event into processing event
		if(event.equals(PlayerListener.STARTED))
			pEvent = EVENT_STARTED;
		if(event.equals(PlayerListener.STOPPED))
			pEvent = EVENT_STOPPED;			
		else if(event.equals(PlayerListener.END_OF_MEDIA))
			pEvent = EVENT_END_OF_MEDIA;
		else if(event.equals(PlayerListener.CLOSED))
			pEvent = EVENT_CLOSED;			
		else if(event.equals(PlayerListener.ERROR))
			pEvent = EVENT_ERROR;
		else if(event.equals(PlayerListener.RECORD_STARTED))
			pEvent = EVENT_RECORD_STARTED;			
		else if(event.equals(PlayerListener.RECORD_STOPPED))
			pEvent = EVENT_RECORD_STOPPED;
		else if(event.equals(PlayerListener.RECORD_ERROR))
			pEvent = EVENT_RECORD_ERROR;
		else if(event.equals(PlayerListener.VOLUME_CHANGED))
			pEvent = EVENT_VOLUME_CHANGED;
		else if(event.equals(PlayerListener.BUFFERING_STARTED))
			pEvent = EVENT_BUFFERING_STARTED;
		else if(event.equals(PlayerListener.BUFFERING_STOPPED))
			pEvent = EVENT_BUFFERING_STOPPED;
		else if(event.equals(PlayerListener.DEVICE_AVAILABLE))
			pEvent = EVENT_DEVICE_AVAILABLE;
		else if(event.equals(PlayerListener.DEVICE_UNAVAILABLE))
			pEvent = EVENT_DEVICE_UNAVAILABLE;
		else if(event.equals(PlayerListener.DURATION_UPDATED))
			pEvent = EVENT_DURATION_UPDATED;
		else if(event.equals(PlayerListener.STOPPED_AT_TIME))
			pEvent = EVENT_STOPPED_AT_TIME;
			
		// If event is interesting send to the midlet
		if(pEvent != 0)
			pMIDlet.enqueueLibraryEvent(this,pEvent,eventData);
		else
			pMIDlet.enqueueLibraryEvent(this,-1,event);
	}
}
