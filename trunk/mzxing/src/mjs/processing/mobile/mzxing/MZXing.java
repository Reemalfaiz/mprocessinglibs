/*

	MZXing - ZXing qrcode decode Library for Mobile Processing

	Copyright (c) 2008 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mzxing;

import com.google.zxing.MonochromeBitmapSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;

import com.google.zxing.client.j2me.LCDUIImageMonochromeBitmapSource;

import processing.core.PMIDlet;
import processing.core.PImage;

/**
 * MZXing 
 *
 * Decode QRCodes
 *
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */ 
public class MZXing
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
	 * Parent MIDlet.
	 */
	private PMIDlet pMIDlet;
	
	/**
	 * Creates the decode for QRCodes
	 *
	 * @param pMIDlet The current midlet to notify events
	 */
	public MZXing(PMIDlet pMIDlet)
	{
		// Set the midlet
		this.pMIDlet = pMIDlet;		
	}	

	/**
	 * Decode the message encode in the image 
	 *
	 * @param pImage Image containing a QRCode 
	 */
	public void decode(final PImage pImage)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// Generate a zxing image 
					MonochromeBitmapSource source 
						= new LCDUIImageMonochromeBitmapSource(pImage.image);

					// Create a multiformat reader to decode the image 			
					Reader reader = new MultiFormatReader();		

					// get the result fro the codification  		
					Result result = reader.decode(source);
		
					// Get the result text 
					String text = result.getText();
					
					// Send the decode event 
					pMIDlet.enqueueLibraryEvent(
						MZXing.this,EVENT_DECODE,text);					
				}
				catch(Exception e)
				{
					// Send error event with the error message
					pMIDlet.enqueueLibraryEvent(
						MZXing.this,EVENT_ERROR,e.getMessage());
				}
			}
		}.start();
	}	
}