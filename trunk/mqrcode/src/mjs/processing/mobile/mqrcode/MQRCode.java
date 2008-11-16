/*

	MQRCode - QRCode Library for Mobile Processing

	Copyright (c) 2006-2008 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mqrcode;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;

import processing.core.PImage;
import processing.core.PMIDlet;

import com.swetake.util.Qrcode;

import jp.sourceforge.qrcode.QRCodeDecoder;

/**
 * MQRCode 
 *
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */

public class MQRCode
{
	/**
	 * Error Event.
	 *
	 * @value 1
	 */ 

	public static final int EVENT_ERROR = 1;

	/**
	 * Decode Event.
	 *
	 * @value 2
	 */ 

	public static final int EVENT_DECODE = 2;

	/**
	 * Decoder
	 */
	 
	private QRCodeDecoder decoder;

	/**
	 * Encode 
	 */

	private Qrcode encode;

	/**
	 * Processing MIDlet.
	 */

	private PMIDlet pMIDlet;
	
	/**
	 * Creates the decode for QRCodes
	 *
	 * @param pMIDlet The current midlet to notify events
	 */

	public MQRCode(PMIDlet pMIDlet)
	{
		// Set the midlet
		this.pMIDlet = pMIDlet;
		
		// Create the decoder
		decoder = new QRCodeDecoder();

		// Create the encode
		encode = new Qrcode();
		encode.setQrcodeErrorCorrect('M');
		encode.setQrcodeEncodeMode('B');
		encode.setQrcodeVersion(3);
	}

	/**
	 * Creates the decode for QRCodes without notification
	 */

	public MQRCode()
	{
		this(null);
	}

	/**
	 * Return the message encode inside the image specified.
	 *
	 * If background is true the methos returns null and the message is decode 
	 * in another thread and notified by events when the message is decode or 
	 * an error occurs
	 *
	 * @param pImage Image containing a QRCode 
	 * @param background Image containing a QRCode  
	 *
	 * @return the decode message from the image, null if any exception occurs
	 */
	
	public String decode(PImage pImage, boolean background)
	{
		if(background)
		{
			// Decode the image in background
			decodeInBackground(pImage);

			// Meanwhile return null
			return null;
		}
		// Decode the image in this thread
		else
			return decode(pImage);
	}

	/**
	 * Decode the image in background 
	 *
	 * This method decode the image in a different thread to avoid deadlocks 
	 * the result is send to the midlet associated through event EVENT_DECODE,
	 * if any error ocurrs the EVENT_ERROR with the message is send.
	 *
	 * If not midlet was specified the this methods causes a RuntimeException
	 *
	 * @param pImage Image containing a QRCode 
	 * @throws RuntimeException if the midlet was not specified
	 */

	private void decodeInBackground(final PImage pImage)
	{
		// Create a new Thread
		new Thread()
		{
			public void run()
			{
				// Check to MIDlet to notify
				if(pMIDlet == null)
					throw new RuntimeException("Not MIDlet specified to send events.");

				// Decode the image
				String result = decode(pImage);

				// Send decode event with result
				if(result != null)
					pMIDlet.enqueueLibraryEvent(
						MQRCode.this,EVENT_DECODE,result);
			}
		}.start();
	}

	/**
	 * Return the message encode inside the image specified.
	 *
	 * @param pImage Image containing a QRCode 
	 * @return the decode message from the image, null if any exception occurs
	 */
	
	public String decode(PImage pImage)
	{
		// Result is null if any problem
		String result = null;
		
		try
		{
			// Create qr image
			J2MEImage img = new J2MEImage(pImage.image);
			
			// Try to decode			
			result = new String(decoder.decode(img));
		}
		catch(Exception e)
		{
			// Check to MIDlet to notify
			if(pMIDlet != null)
			{
				// Send error event with the error message
				pMIDlet.enqueueLibraryEvent(this,EVENT_ERROR,e.getMessage());
			}

			e.printStackTrace();
		}

		// Return the result
		return result;
	}

	/**
	 * Encode the message in a image using QRCode
	 *
	 * @param message The message to encode
	 * @param moduleSize The module size in pixels
	 * @return Image containing the message encoded
	 */

	public PImage encode(String message, int moduleSize)
	{
		// Get the data encoded with qrcode
		boolean[][] data = encode.calQrcode(message.getBytes());

		// Calculate the image size, add 8 modules to margin
		int imageSize = (data.length + 8) * moduleSize;

		// Create the image
		Image image = Image.createImage(imageSize,imageSize);
		Graphics g = image.getGraphics();

		// Fill the background
		g.setColor(0xFFFFFF);
		g.fillRect(0,0,imageSize,imageSize);
		g.setColor(0);

		// Set the width for each rect to draw
		int width = moduleSize;

		// left the margin
		g.translate(4 * width,4 * width);

		// Draw a black rect for each module active
		for(int i=0; i<data.length; i++)
			for(int j=0; j<data[i].length; j++)
				if(data[i][j])
					g.fillRect(i*width,j*width,width,width);

		// Return the image
		return new PImage(image);
	}
}
