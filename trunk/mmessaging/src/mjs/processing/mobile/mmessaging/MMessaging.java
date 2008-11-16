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

import javax.microedition.io.*;
import javax.wireless.messaging.*;

import mjs.processing.mobile.mpush.*;

import processing.core.*;

/**
 * Processing library for send and receive messages.
 *
 * This class provides static methods send and receive messages.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 */

public class MMessaging
{
	/**
	 * Event sms message arraive.
	 *
	 * @value 1
	 * @since 0.3
	 */ 

	public static final int EVENT_SMS_MESSAGE_ARRAIVE = 1;

	/**
	 * Event cbs message arraive.
	 *
	 * @value 2
	 * @since 0.3
	 */ 

	public static final int EVENT_CBS_MESSAGE_ARRAIVE = 2;
	
	/**
	 * Connection listening message arrive.
	 */

	private MessageConnection smsConnection;

	/**
	 * Connection listening cbs message arrive.
	 *
	 * @since 0.3
	 */

	private MessageConnection cbsConnection;

	/**
	 * Processing MIDlet. 
	 *
	 * @since 0.3
	 */
	 
	private PMIDlet pMIDlet;

	/**
	 * Port for receive or send message
	 *
	 * @since 0.3
	 */

	private int smsPort;

	/**
	 * Port for receive cbs message
	 *
	 * @since 0.3
	 */

	private int cbsPort;

	/**
	 * Default port used to receive or send sms messages in Mobile Processing.
	 *
	 * @since 0.3
	 */

	public static final int DEFAULT_SMS_PORT = 2005;

	/**
	 * Default cbs port used to receive cbs messages in Mobile Processing.
	 *
	 * @since 0.3
	 */

	public static final int DEFAULT_CBS_PORT = 2005;

	/**
	 * Message Listener is listening.
	 *
	 * @since 0.3
	 */

	private boolean smsListenerRunning;

	/**
	 * CBS Message Listener is listening.
	 *
	 * @since 0.3
	 */

	private boolean cbsListenerRunning;

	/**
	 * Create a messaging service for Mobile Processing.
	 *
	 * @param pMIDlet The midlet
	 *
	 * @since 0.3
	 */

	public MMessaging(PMIDlet pMIDlet)
	{
		smsPort = DEFAULT_SMS_PORT;
		cbsPort = DEFAULT_CBS_PORT;
		
		this.pMIDlet = pMIDlet;
	}
	
	/**
	 * Sends a text message to the specified phone
	 * 
	 * @param phoneNumber Number of the phone to send message
	 * @param text Text message to send
	 */
	 
	public void sendMessage(String phoneNumber, String text)
	{
		sendMessage(phoneNumber,text,DEFAULT_SMS_PORT);
	}

	/**
	 * Sends a binary message to the specified phone
	 * 
	 * @param phoneNumber Number of the phone to send message
	 * @param data Data message to send
	 */
	 
	public void sendMessage(String phoneNumber, byte[] data)
	{
		sendMessage(phoneNumber,data,DEFAULT_SMS_PORT);
	}

	/**
	 * Sends the text message to the specified phone number.
	 *
	 * @param phoneNumber Phone number to send the message
	 * @param text The conten of the message.
	 */

	public void sendMessage(final String phoneNumber, final Object content, final int port)
	{
		// Start a new thread to open the connection to the phone and send the message
		
		new Thread()
		{
			public void run()
			{
				try
				{
					// Open the connection with the phone
					// Create the text message
					// Send the message
					// Close the connection
					
					String url = "sms://" + phoneNumber + ":" + port;
			
					MessageConnection smsConnection = (MessageConnection) Connector.open(url);

					Message message;

					if(content instanceof String)
					{
						TextMessage textMessage = (TextMessage) smsConnection.newMessage(MessageConnection.TEXT_MESSAGE);
						textMessage.setPayloadText((String) content);
						message = textMessage;
					}
					else
					{
						BinaryMessage binaryMessage = (BinaryMessage) smsConnection.newMessage(MessageConnection.BINARY_MESSAGE);
						binaryMessage.setPayloadData((byte[]) content);
						message = binaryMessage;
					}
					
					smsConnection.send(message);
					smsConnection.close();
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * Start to listening messages.
	 *
	 * Creates a thread if any connection has been created, reads the messages and updates the current content
	 * and begins to wait for the next message.
	 */

	private void startListeningSMSMessages()
	{
		// If is not any connection started, create the thread to get messages
		
		if(smsConnection == null)
		{
			smsListenerRunning = true;
			
			new Thread()
			{
				public void run()
				{
					try
					{
						Object messageContent;
						
						// Create the connection
						String url = "sms://:" + smsPort;
						smsConnection = (MessageConnection) Connector.open(url);

						// Get the next message binary or text
						while(smsListenerRunning)
						{
							Message message = smsConnection.receive();
							messageArraive(MMessaging.EVENT_SMS_MESSAGE_ARRAIVE,message);
						}

						smsConnection.close();
						smsConnection = null;
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	/**
	 * Start listening messages in the default port.
	 *
	 * @since 0.3
	 */

	public void receiveSMSMessages(boolean receive)
	{
		receiveSMSMessages(receive,DEFAULT_SMS_PORT);
	}

	/**
	 * Start listening messages in the port specified.
	 *
	 * @param smsPort Port to listen messages
	 *
	 * @since 0.3
	 */

	public void receiveSMSMessages(boolean receive, int smsPort)
	{
		if(receive == true)
		{
			this.smsPort = smsPort;
			startListeningSMSMessages();
		}
		else
			smsListenerRunning = false;
	}

	/**
	 * Start to listening cbs messages.
	 *
	 * Creates a thread if any connection has been created, reads the messages and updates the current content
	 * and begins to wait for the next message.
	 */

	private void startListeningCBSMessages()
	{
		// If is not any connection started, create the thread to get messages
		
		if(cbsConnection == null)
		{
			cbsListenerRunning = true;
			
			new Thread()
			{
				public void run()
				{
					try
					{
						// Create the connection
						String url = "cbs://:" + cbsPort;
						cbsConnection = (MessageConnection) Connector.open(url);

						// Get the next message binary or text
						while(cbsListenerRunning)
						{
							Message message = cbsConnection.receive();
							messageArraive(MMessaging.EVENT_CBS_MESSAGE_ARRAIVE,message);
						}

						cbsConnection.close();
						cbsConnection = null;
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
				}
			}.start();
		}
	}	

	/**
	 * Receive Message
	 *
	 * @since 0.3
	 */

	private void messageArraive(int messageSource, Message message)
	{
		MMessage mMessage;

		int messageType;
		
		byte[] data = null;
		String text = null;
		
		// The message is text
		if(message instanceof TextMessage)
		{
			TextMessage textMessage = (TextMessage) message;
			text = textMessage.getPayloadText();
			messageType = MMessage.TEXT_MESSAGE;
		}
		// The message is data
		else
		{
			BinaryMessage binaryMessage = (BinaryMessage) message;
			data = binaryMessage.getPayloadData();
			messageType = MMessage.BINARY_MESSAGE;
		}

		mMessage = new MMessage(message.getAddress(),message.getTimestamp(),data,text,messageType);
	
		pMIDlet.enqueueLibraryEvent(this,messageSource,mMessage);
	}

	/**
	 * Start listening messages in the default port.
	 *
	 * @since 0.3
	 */

	public void receiveCBSMessages(boolean receive)
	{
		receiveCBSMessages(receive,DEFAULT_CBS_PORT);
	}

	/**
	 * Start listening messages in the port specified.
	 *
	 * @param port Port to listen messages
	 *
	 * @since 0.3
	 */

	public void receiveCBSMessages(boolean receive, int cbsPort)
	{
		if(receive == true)
		{
			this.cbsPort = cbsPort;
			startListeningCBSMessages();
		}
		else
			cbsListenerRunning = false;
	}

	/**
	 * Register the sms and cbs connections to the register if this are running
	 * 
	 * @param mpush The push register
	 *
	 * @since 0.3.1
	 */

	public void register(MPush mpush)
	{
		if(smsListenerRunning)
			mpush.registerConnection("sms://:" + smsPort,pMIDlet.getClass().getName(),"*");

		if(cbsListenerRunning)
			mpush.registerConnection("cbs://:" + cbsPort,pMIDlet.getClass().getName(),"*");		
	}

	/**
	 * Unregister the sms and cbs connections to the register
	 *
	 * @param mpush The push register
	 *
	 * @since 0.3.1
	 */

	public void unregister(MPush mpush)
	{
		mpush.unregisterConnection("sms://:" + smsPort);
		mpush.unregisterConnection("cbs://:" + cbsPort);
	}
}
