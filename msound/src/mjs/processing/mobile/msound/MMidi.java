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

import javax.microedition.media.*;
import javax.microedition.media.control.*;

import processing.core.*;

/**
 * MMidi
 * 
 * Provides access to MIDI rendering and transmitting devices.
 *
 * @author Mary Jane Soft - Marlon J. Manrique
 *
 * @since 0.4
 */

public class MMidi extends MSound
{
	/**
	 * MMAPI Midi Control
	 */
	
	private MIDIControl midiControl;

	/**
	 * Creates a Midi access.
	 */

	public MMidi()
	{
		this(null);
	}

	/**
	 * Creates a Midi access.
	 * 
	 * @param pMIDlet The parent MIDlet
	 */

	public MMidi(PMIDlet pMIDlet)
	{
		try
		{
			// Create a sound player with the MIDI device locator
			player = Manager.createPlayer(Manager.MIDI_DEVICE_LOCATOR);

			// Set the current player
			setPlayer(player);

			// Prefetch the player
			player.prefetch();

			// Set the current MIDlet
			this.pMIDlet = pMIDlet;

			// Get the midi control
			midiControl = (MIDIControl) player.getControl("MIDIControl");

			// If no midi control is pressent
			if(midiControl == null)
				throw new RuntimeException("MIDI don't supported by this device");
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Play the note specified in the channel specified. 
	 *
	 * @param channel midi channel
	 * @param pitch pitch of a note
	 * @param velocity velocity of a note
	 */
	 
	public void noteOn(int channel, int pitch, int velocity)
	{
		midiControl.shortMidiEvent(MIDIControl.NOTE_ON | channel,pitch,velocity);
	}

	/**
	 * Stop playing the note specified in the channel specified
	 *
	 * @param channel midi channel
	 * @param pitch pitch of a note
	 * @param velocity velocity of a note
	 */
	 
	public void noteOff(int channel, int pitch)
	{
		midiControl.shortMidiEvent(MIDIControl.NOTE_ON | channel,pitch,0);
	}

	/**
	 * Change the program of a channel.
	 * 
	 * @param channel midi channel
	 * @param program the midi program
	 */

	public void programChange(int channel, int program)
	{
		midiControl.setProgram(channel,-1,program);
	}

	/**
	 * Change the volume for the given channel, To mute, set to 0. 
	 *
	 * @param channel midi channel
	 * @param volumen volumen
	 */

	public void channelVolume(int channel, int volumen)
	{
		midiControl.setChannelVolume(channel,volumen);
	}
}
