/*

	MBluetooth - Bluetooth Library for Mobile Processing

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

	$Id$
	
*/

package mjs.processing.mobile.mbluetooth;

import processing.core.PMIDlet;

import java.util.*;
import javax.microedition.io.*;
import javax.bluetooth.*;

import mjs.processing.mobile.mclientserver.*;

/**
 * Bluetooth class use in Processing
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 * @since 0.4
 */

public class MBluetoothM extends MBluetooth
{
	/**
	 * The midlet 
	 */
	 
	protected PMIDlet midlet;
	
	/**
	 * Create the bluetooth service associated with the midlet specified
	 *
	 * @param sketch The MIDlet
	 */
	
	public MBluetoothM(Object sketch)
	{
		midlet = (PMIDlet) sketch;
	}
	
	/**
	 * Method used to fire the library events
	 *
	 * @param library Library object that fires the event
	 * @param event Event identifier
	 * @param data Data provide in the event
	 */
	 
	protected void enqueueLibraryEvent(Object obj, int event, Object data)
	{
		midlet.enqueueLibraryEvent(obj,event,data);
	}
}
