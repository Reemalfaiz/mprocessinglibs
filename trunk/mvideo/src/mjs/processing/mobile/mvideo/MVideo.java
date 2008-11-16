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

import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import javax.microedition.media.control.*;

import processing.core.*;

/**
 * MVideo.
 * 
 * Datatype for storing and playing movies.
 * Movies must be located in the sketch's data directory or an accessible place on the network to load without an error.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 */

public class MVideo implements PlayerListener
{
	/**
	 * No transform is applied to the Video
	 *
	 * @value 0
	 * @since 0.5
	 */
	public static final int TRANS_NONE = 0;
	
	/**
	 * Causes the video to appear rotated clockwise by 90 degrees
	 *
	 * @value 5
	 * @since 0.5
	 */
	public static final int TRANS_ROT90 = 5;
	
	/**
	 * Causes the video to appear rotated clockwise by 180 degrees
	 *
	 * @value 3
	 * @since 0.5
	 */
	public static final int TRANS_ROT180 = 3;
	
	/**
	 * Causes the video to appear rotated clockwise by 270 degrees
	 *
	 * @value 6
	 * @since 0.5
	 */
	public static final int TRANS_ROT270 = 6;	
	
	/**
	 * Causes the video to appear reflected about its vertical center
	 *
	 * @value 2
	 * @since 0.5
	 */
	public static final int TRANS_MIRROR = 2;
	
	/**
	 * Causes the video to appear reflected about its vertical center and then 
	 * rotated clockwise by 90 degrees. 
	 *
	 * @value 7
	 * @since 0.5
	 */
	public static final int TRANS_MIRROR_ROT90 = 7;
	
	/**
	 * Causes the video to appear reflected about its vertical center and then 
	 * rotated clockwise by 180 degrees. 
	 *
	 * @value 1
	 * @since 0.5
	 */
	public static final int TRANS_MIRROR_ROT180 = 1;
	
	/**
	 * Causes the video to appear reflected about its vertical center and then 
	 * rotated clockwise by 270 degrees. 
	 *
	 * @value 4
	 * @since 0.5
	 */
	public static final int TRANS_MIRROR_ROT270 = 4;	
	
	/**
	 * Started event.
	 *
	 * @value 1
	 * @since 0.3
	 */ 

	public static final int EVENT_STARTED = 1;

	/**
	 * Stopped event.
	 *
	 * @value 2
	 * @since 0.3
	 */ 

	public static final int EVENT_STOPPED = 2;

	/**
	 * Event End of Media.
	 *
	 * @value 3
	 * @since 0.3
	 */ 

	public static final int EVENT_END_OF_MEDIA = 3;

	/**
	 * Event Closed.
	 *
	 * @value 4
	 * @since 0.3
	 */ 

	public static final int EVENT_CLOSED = 4;

	/**
	 * Event Error.
	 *
	 * @value 5
	 * @since 0.3
	 */ 

	public static final int EVENT_ERROR = 5;
	
	/**
	 * Event Record Started.
	 *
	 * @value 6
	 * @since 0.3
	 */ 

	public static final int EVENT_RECORD_STARTED = 6;

	/**
	 * Event Record Stopped.
	 *
	 * @value 7
	 * @since 0.3
	 */ 

	public static final int EVENT_RECORD_STOPPED = 7;

	/**
	 * Event Record Error.
	 *
	 * @value 8
	 * @since 0.3
	 */ 

	public static final int EVENT_RECORD_ERROR = 8;

	/**
	 * Event Record Data Available.
	 *
	 * @value 9
	 * @since 0.3
	 */ 

	public static final int EVENT_RECORD_DATA_AVAILABLE = 9;

	/**
	 * Event Volume Changed.
	 *
	 * @value 10
	 * @since 0.3
	 */ 

	public static final int EVENT_VOLUME_CHANGED = 10;	 

	/**
	 * Event Buffering Started.
	 *
	 * @value 11
	 * @since 0.3
	 */ 

	public static final int EVENT_BUFFERING_STARTED  = 11;	 

	/**
	 * Event Buffering Stopped.
	 *
	 * @value 12
	 * @since 0.3
	 */ 

	public static final int EVENT_BUFFERING_STOPPED  = 12;	 

	/**
	 * Event Device Available.
	 *
	 * @value 13
	 * @since 0.3
	 */ 

	public static final int EVENT_DEVICE_AVAILABLE  = 13;
	
	/**
	 * Event Device Unavailable.
	 *
	 * @value 14
	 * @since 0.3
	 */ 

	public static final int EVENT_DEVICE_UNAVAILABLE  = 14;

	/**
	 * Event Duration Updated.
	 *
	 * @value 15
	 * @since 0.3
	 */ 

	public static final int EVENT_DURATION_UPDATED  = 15;

	/**
	 * Sopped event.
	 *
	 * @value 16
	 * @since 0.3
	 */ 

	public static final int EVENT_STOPPED_AT_TIME = 16;

	/**
	 * Event Size Changed. Only for MVideo compatibility.
	 *
	 * @value 10
	 * @since 0.3
	 */ 

	public static final int EVENT_SIZE_CHANGED = 17;
	
	/**
	 * Video Player.
	 */

	protected Player player;

	/**
	 * Processing MIDlet. Used to get access to the canvas to draw the video.
	 */

	protected PMIDlet pMIDlet;

	/**
	 * Video Control. Used to get access the video control.
	 */

	private VideoControl videoControl;

	/**
	 * Volume Control. Used to get access the volume control.
	 */

	private VolumeControl volumeControl;

	/**
	 * Allows frame video searching.
	 */

	private FramePositioningControl framePositioningControl;
	
	/**
	 * Allow drawing over the video
	 *
	 * @since 0.5
	 */
	private boolean overlay;
	
	/**
	 * Allows rotate the video
	 *
	 * @since 0.5
	 */
	private int transform;	

	/**
	 * Creates a video with the resource or locator specified.
	 *
	 * The video can be localed stored at data directory or can be load from a server,
	 * this class permits also capture the video from the phone camera using the uri : capture://video
	 *
	 * @param pMIDlet The current midlet.
	 * @param locator A locator string in URI syntax that describes the media content or resource route.
	 */

	public MVideo(PMIDlet pMIDlet, String locator)
	{
		this(pMIDlet,locator,null);
	}
	
	/**
	 * Creates a video with the resource or locator specified.
	 *
	 * The video can be localed stored at data directory or can be load from a server,
	 * this class permits also capture the video from the phone camera using the uri : capture://video
	 *
	 * @param pMIDlet The current midlet.
	 * @param locator A locator string in URI syntax that describes the media content or resource route.
	 * @param transform the desired transform for the video
	 * @param overlay Draw over the video	 
	 *
	 * @since 0.5
	 */
	public MVideo(PMIDlet pMIDlet, String locator,int transform, 
		boolean overlay)
	{
		this(pMIDlet,locator,null,transform,overlay);
	}	
	
	/**
	 * Creates a video with the resource or locator specified.
	 *
	 * @param pMIDlet The current midlet.
	 * @param locator A locator string in URI syntax that describes the media content or resource route.
	 * @param type mime type for the content
	 */
	public MVideo(PMIDlet pMIDlet, String locator, String type)
	{
		this(pMIDlet,locator,type,TRANS_NONE,false);
	}	

	/**
	 * Creates a video with the resource or locator specified.
	 *
	 * @param pMIDlet The current midlet.
	 * @param locator A locator string in URI syntax that describes the media content or resource route.
	 * @param type mime type for the content
	 * @param transform the desired transform for the video
	 * @param overlay Draw over the video
	 */
	 
	public MVideo(PMIDlet pMIDlet, String locator, String type, int transform,
		 boolean overlay)
	{
		this.pMIDlet = pMIDlet;
		this.overlay = overlay;
		this.transform = transform;

		try
		{
			// If is a URL, load the sound from the server
			// If is a capture try to create
			// If is a file try to create
			// If is a real time url
			if(locator.startsWith("http://") || 
				locator.startsWith("capture://") || 
				locator.startsWith("file://") || 
				locator.startsWith("rtsp://") || locator.startsWith("rtp://"))
			{
				player = Manager.createPlayer(locator);
			}
			else
			{
				// If is not URL, load the sound from the resources, try to get the media type
				// and creates the player		

				if(type == null)
					type = MVideoManager.getMediaType(locator);
				
				InputStream inputStream = getClass().getResourceAsStream("/" + locator);

				// If resource is not found, throws an exception
				if(inputStream == null)
					throw new RuntimeException("File not Found");
				
				player = Manager.createPlayer(inputStream,type);
			}

			// Set the player
			setPlayer(player);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * Create the video control for this player
	 *
	 * @since 0.5
	 */
	private void createVideoControl()
	{
	}

	/**
	 * Set the player for this MVideo
	 *
	 * @param player The video player 
	 *
	 * @since 0.3
	 */

	protected void setPlayer(Player player)
	{
		try
		{
			this.player = player;

			// Load all the resources and stay ready to start
			player.prefetch();

			// Listen events	
			player.addPlayerListener(this);

			videoControl = (VideoControl) player.getControl("VideoControl");

			if(videoControl != null)
			{
				// Calculate display mode
				int displayMode = VideoControl.USE_DIRECT_VIDEO;
				
				// If overlay, add setting to the display mode
				if(overlay)
					displayMode |= 1 << 8;
					
				// Set the transformation
				displayMode |= transform << 4;
								
				// Set the display mode and show the video
				videoControl.initDisplayMode(displayMode,pMIDlet.getCanvas());
				videoControl.setVisible(true);
			}
			
			framePositioningControl = (FramePositioningControl) player.getControl("FramePositioningControl");

			// Listen events
			player.addPlayerListener(this);			
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Create a video with the player specified
	 *
	 * @param pMIDlet The current midlet.
	 * @param videoPlayer a videoPlayer.
	 *
	 * @since 0.3
	 */

	protected MVideo(PMIDlet pMIDlet, Player videoPlayer)
	{
		this.pMIDlet = pMIDlet;
		setPlayer(videoPlayer);
	}

	/**
	 * Plays the movie once.
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
	 * Plays the video continously
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
	 * Stops the video from looping.
	 * If the video is currently playing, it will finish and will not begin again.
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
	 * Pauses the video playback.
	 * If the pause is started again, it will continue to play from the position where it was paused.
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
	 * Stops the video playback.
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
	 * Returns the length of the video in seconds.
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
	 * Returns the length of the video in microseconds.
	 * 
	 * @return Length of the video in microseconds.
	 */

	public long mduration()
	{
		return player.getDuration();
	}

	/**
	 * Returns the current position of video playback in microseconds.
	 * 
	 * @return Current position of video playback in microseconds.
	 */

	public long mtime()
	{
		return player.getMediaTime();
	}	

	/**
	 * Change the location of the video inside the canvas.
	 *
	 * @param x The x coordinate (in pixels) of the video location.
	 * @param y The y coordinate (in pixels) of the video location.
	 */

	public void location(int x, int y)
	{
		// If is control available set the display location
		if(videoControl != null)
			videoControl.setDisplayLocation(x,y);
	}

	/**
	 * Change the size of the display video.
	 * 
	 * @param width Desired width (in pixels) of the display window
     * @param height Desired height (in pixels) of the display window
	 */

	public void size(int width, int height)
	{
		try
		{
			// If is control available set the display location
			if(videoControl != null)
				videoControl.setDisplaySize(width,height);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Returns the x coordinate of the video location.
	 *
	 * @return The x coordinate (in pixels) of the video location.
	 * @deprecated
	 */

	public int getX()
	{
		if(videoControl != null)
			return videoControl.getDisplayX();

		return 0;
	}

	/**
	 * Returns the x coordinate of the video location.
	 *
	 * @since 0.3
	 *
	 * @return The x coordinate (in pixels) of the video location.
	 */

	public int x()
	{
		if(videoControl != null)
			return videoControl.getDisplayX();

		return 0;
	}

	/**
	 * Returns the y coordinate of the video location.
	 *
	 * @return The y coordinate (in pixels) of the video location.
	 * @deprecated
	 */

	public int getY()
	{
		if(videoControl != null)
			return videoControl.getDisplayY();

		return 0;
	}

	/**
	 * Returns the y coordinate of the video location.
	 *
	 * @since 0.3
	 *
	 * @return The y coordinate (in pixels) of the video location.
	 */

	public int y()
	{
		if(videoControl != null)
			return videoControl.getDisplayY();

		return 0;
	}

	/**
	 * Return the actual width of the current render video.
	 *
	 * @return width of the display video
	 * @deprecated
	 */

	public int getWidth()
	{
		if(videoControl != null)
			return videoControl.getDisplayWidth();

		return 0;
	}

	/**
	 * Return the actual width of the current render video.
	 *
	 * @since 0.3
	 *
	 * @return width of the display video
	 */

	public int width()
	{
		if(videoControl != null)
			return videoControl.getDisplayWidth();

		return 0;
	}
	
	/**
	 * Return the actual height of the current render video.
	 *
	 * @return height of the display video
	 * @deprecated
	 */

	public int getHeight()
	{
		if(videoControl != null)
			return videoControl.getDisplayHeight();

		return 0;
	}

	/**
	 * Return the actual height of the current render video.
	 *
	 * @since 0.3
	 *
	 * @return height of the display video
	 */

	public int height()
	{
		if(videoControl != null)
			return videoControl.getDisplayHeight();

		return 0;
	}

	/**
	 * Return the width of the source video. The height must be a positive number.
	 *
	 * @return the width of the source video
	 */

	public int getSourceWidth()
	{
		if(videoControl != null)
			return videoControl.getSourceWidth();

		return 0;
	}

	/**
	 * Return the height of the source video. The height must be a positive number.
	 *
	 * @return the height of the source video
	 */

	public int getSourceHeight()
	{
		if(videoControl != null)
			return videoControl.getSourceHeight();

		return 0;
	}

	/**
	 * Show the video.
	 */

	public void show()
	{
		if(videoControl != null)
			videoControl.setVisible(true);
	}

	/**
	 * Show the video at the specified location with the especified size 
	 *
	 * @param x The x coordinate (in pixels) of the video location.
	 * @param y The y coordinate (in pixels) of the video location.
	 * @param width Desired width (in pixels) of the display window
     * @param height Desired height (in pixels) of the display window
	 *	 
	 * @since 0.2.1
	 */

	public void show(int x, int y, int width, int height)
	{
		location(x,y);
		size(width,height);
		show();
	}

	/**
	 * Hide the video.
	 */

	public void hide()
	{
		if(videoControl != null)
			videoControl.setVisible(false);
	}

	/**
	 * Get a snapshot of the displayed content. 
	 *
	 * @return PImage with the snapshot.
	 */

	public PImage snapshot()
	{
		try
		{
			// Read the data from the video
			byte[] imageData = read();

			// If the data is available, convert to PImage
			if(imageData != null)
			{
				Image image = Image.createImage(imageData,0,imageData.length);
				return new PImage(image);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}

		return null;
	}

	/**
	 * Seek to a given video frame. 
	 * The media time of the Player will be updated to reflect the new position set.
	 * 
	 * @param frameNumber the frame to seek to.
	 */

	public void seekFrame(int frameNumber)
	{
		if(framePositioningControl != null)
			framePositioningControl.seek(frameNumber);
	}

	/**
	 * Seek to a given microsecond time position.
	 * The media time of the Player will be updated to reflect the new position set.
	 *
	 * @param mtime Microsecond to seek.
	 */

	public void seekTime(long mtime)
	{
		if(framePositioningControl != null)
			framePositioningControl.seek(framePositioningControl.mapTimeToFrame(mtime));
	}

	/**
	 * Converts the given frame number to the corresponding media time.
	 * The method only performs the calculations. It does not position the media to the given frame.
	 *
	 * @param frameNumber - the input frame number for the conversion.
	 */

	public long frameToTime(int frameNumber)
	{
		if(framePositioningControl != null)
			return framePositioningControl.mapFrameToTime(frameNumber);

		return -1;
	}

	/**
	 * Converts the given media time to the corresponding frame number.
	 * The method only performs the calculations. It does not position the media to the given media time.
	 *
	 * @param mtime the input media time for the conversion in microseconds.
	 */

	public int timeToFrame(long mtime)
	{
		if(framePositioningControl != null)
			return framePositioningControl.mapTimeToFrame(mtime);

		return -1;
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
	 * Closes the video and releases resources.
	 *
	 * @since 0.3
	 */

	public void close()
	{
		hide();
		player.close();
	}

	/**
	 * Captures a frame from the video. 
	 * The frame is returned as an array of bytes containing the image data for the frame. 
	 * The image data is in the a file format supported by the device and can be displayed 
	 * by using loadImage() to create a PImage object.
	 *
	 * @return A byte array with the snapshot bytes.
	 */

	public byte[] read()
	{
		try
		{
			// if video control is available, get snapshot data and create a PImage
			if(videoControl != null)	
			{
				byte[] imageData = videoControl.getSnapshot(null);
				return imageData;
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}

		return null;
	}

	/**
	 * Return the list of supported content types.
	 *
	 * @return The list of supported content types
	 *
	 * @since 0.3
	 * @deprecated 
	 */

	public static String[] supportedTypes()
	{
		return MVideoManager.supportedTypes();
	}

	/**
	 * Return the list of supported capture types.
	 *
	 * @return The list of supported capture types
	 *
	 * @since 0.3
	 * @deprecated as 0.6 replaced by {@link MVideoManager#supportedCaptureTypes()}
	 */

	public static String[] supportedCaptureTypes()
	{
		return MVideoManager.supportedCaptureTypes();
	}	 

	/**
	 * This method is called to deliver an event to a registered listener when a Player event is observed.
	 *
	 * @param player The player which generated the event.
	 * @param event The event generated as defined by the enumerated types.
	 * @param eventData The associated event data.
	 * 
	 * @since 0.3
	 * @deprecated as 0.6 replaced by {@link MVideoManager#playerUpdate(Player,String,Object)}
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
		else if(event.equals(PlayerListener.SIZE_CHANGED))
			pEvent = EVENT_SIZE_CHANGED;
			
		// If event is interesting send to the midlet
		if(pEvent != 0)
			pMIDlet.enqueueLibraryEvent(this,pEvent,eventData);
		else
			pMIDlet.enqueueLibraryEvent(this,-1,event);
	}
	
	/**
	 * Returns the video content type
	 *
	 * @return The content type being played.
	 * @since 0.4
	 */
	public String type()
	{
		return player.getContentType();
	}
	
	/**
	 * Set the size of the render region for the video clip to be fullscreen.
	 *
	 * @since 0.5
	 */
	public void fullscreen()
	{
		try
		{
			// If is control available set to full screen
			if(videoControl != null)
				videoControl.setDisplayFullScreen(true);		
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * Set the size of the render region for the video clip to be fullscreen.
	 *
	 * @since 0.5
	 */
	public void noFullscreen()
	{
		try
		{
			// If is control available set to full screen
			if(videoControl != null)
				videoControl.setDisplayFullScreen(false);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}
}
