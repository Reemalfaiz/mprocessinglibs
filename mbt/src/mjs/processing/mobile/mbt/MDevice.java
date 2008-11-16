/*

	MBt - Bluetooth Library for Mobile Processing

	Copyright (c) 2005 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mbt;

import javax.bluetooth.RemoteDevice;

/**
 * Represents a Bluetooth device.
 */
public class MDevice
{
	/**
	 * The real remote device.
	 */	 
	private RemoteDevice remoteDevice;
	
	/**
	 * The device name
	 */	 
	public String name;

	/**
	 * The device address
	 */	 
	public String address;
	
	/**
	 * Creates a device with the real remote device.
	 *
	 * @param remoteDevice device discovered via bluetooth 
	 */
	protected MDevice(RemoteDevice remoteDevice)
	{
		this.remoteDevice = remoteDevice;
		
		// Get the address and name
		address = remoteDevice.getBluetoothAddress();
		name();
	}

	/**
	 * Returns the friendly name of the device.
	 *
	 * @return friendly name.
	 */
	public String name()
	{
		try
		{
			// Return the name of the real device without asking always		
			if(name == null)
				name = remoteDevice.getFriendlyName(false);
		}
		catch(Exception e)
		{
			// The name is not available
			name = "Unknown";
			e.printStackTrace();
		}

		return name;
	}

	/**
	 * Convert this object into a string representation.
	 *
	 * @return the name of the device.
	 */
	public String toString()
	{
		return name();
	}

	/**
	 * Reurns the device address.
	 *
	 * @return the address of the device.
	 */
	public String address()
	{
		return remoteDevice.getBluetoothAddress();
	}

	/**
	 * Returns the real device.
	 *
	 * @return the remote device.
	 */
	protected RemoteDevice getRemoteDevice()
	{
		return remoteDevice;
	}
}
