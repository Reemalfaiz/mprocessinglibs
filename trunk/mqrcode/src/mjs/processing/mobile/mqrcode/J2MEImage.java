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

import jp.sourceforge.qrcode.data.QRCodeImage;

/**
 * QRCodeImage wrapper for J2ME Image
 *
 * @since 0.2
 */
 
class J2MEImage implements QRCodeImage
{
	/**
	 * J2ME Native Image
	 */
	private Image image;
	
	/**
	 * Image data
	 */
	private int[] imageData;
	
	/**
	 * Create and J2ME QRCode image with the J2ME image specified
	 *
	 * @param image J2ME image to process like qrcode
	 */
	public J2MEImage(Image image)
	{
		// Set the image
		this.image = image;

		// Create the data of the image and grab the pixels 		
		imageData = new int[image.getWidth() * image.getHeight()];
		image.getRGB(imageData,
			0,image.getWidth(),0,0,image.getWidth(),image.getHeight());
	}
	
	/**
	 * Return the height of the image 
	 *
	 * @return height of the image
	 */
	public int getHeight()
	{
		return image.getHeight();
	}
	
	/**
	 * Return the width of the image 
	 *
	 * @return width of the image
	 */
	public int getWidth()
	{
		return image.getWidth();
	}

	/**
	 * Return the pixel at the gicen position 
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 */	 	
	public int getPixel(int x, int y)
	{
		return imageData[x + y*image.getWidth()];
	}
}