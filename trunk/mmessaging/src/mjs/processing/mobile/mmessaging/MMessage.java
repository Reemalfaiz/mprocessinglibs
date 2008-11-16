/*

	MMessaging - Wireless Messaging for Mobile Processing

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
	
*/

package mjs.processing.mobile.mmessaging;

import java.util.*;

/**
 * Message.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 * @since 0.3
 */

public class MMessage
{
	/**
	 * Constant for a message type for binary messages.
	 *
	 * @value 1
	 */

	public static final int BINARY_MESSAGE = 1;

	/**
	 * Constant for a message type for text messages.
	 *
	 * @value 2
	 */

	public static final int TEXT_MESSAGE = 2;

	/**
	 * Constant for a message type for multipart messages.
	 *
	 * @value 3
	 */

	public static final int MULTIPART_MESSAGE = 3;
	 
	/**
	 * Address associated with this message.
	 */

	public final String address;

	/**
	 * Timestamp indicating when this message has been sent.
	 */

	public final Date timestamp;

	/**
	 * The message payload data as an array of bytes.
	 */

	public final byte[] data;

	/**
	 * The message payload data as a String.
	 */

	public final String text;

	/**
	 * Message type
	 */

	public final int messageType;

	/**
	 * Creates a messages with the data specified.
	 *
	 * @param address Address associated with this message
	 * @param timestamp Timestamp indicating when this message has been sent.
	 * @param data The message payload data as an array of bytes
	 * @param text The message payload data as a String.
	 * @param messageType The message type
	 */

	public MMessage(String address, Date timestamp, byte[] data, String text, int messageType)
	{
		this.address = address;
		this.timestamp = timestamp;
		this.data = data;
		this.text = text;
		this.messageType = messageType;
	}
}
