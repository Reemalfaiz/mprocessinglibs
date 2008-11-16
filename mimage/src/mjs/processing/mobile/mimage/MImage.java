/*

	MImage - Image Library for Image Manipulation on Mobile Processing

	Copyright (c) 2006 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mimage;

import javax.microedition.lcdui.Image;

import processing.core.PImage;

/**
 * An image that allows pixel manipulation
 */

public class MImage extends PImage
{
	/**
	 * Array containing the values for all the pixels in the image. 
	 * These values are of the color datatype. This array is the size of the image, 
	 * meaning if the image is 100x100 pixels, there will be 10000 values and if the window 
	 * is 200x300 pixels, there will be 60000 values. The index value defines the position of 
	 * a value within the array. For example, the statement color b = img.pixels[230] will set 
	 * the variable b equal to the value at that location in the array. Before accessing this array, 
	 * the data must loaded with the loadPixels() functions. After the array data has been modified, 
	 * the updatePixels() function must be loaded to update the changes.
	 */
	 
	public int pixels[];

	/**
	 * Create a image with a preload PImage
	 *
	 * @param pImage preload mobile processing image
	 */
	
	public MImage(PImage pImage)
	{
		// Create the Image
		super(pImage.image);

		// Create the array to store the pixels
		pixels = new int[width*height];

		// Load the pixels
		loadPixels();
	}

	/**
	 * Loads the pixel data for the display window into the pixels[] array. 
	 * This function must always be called before reading or writing to pixels[].
	 */

	public void loadPixels()
	{
		// Grab the pixels into the array
		image.getRGB(pixels,0,width,0,0,width,height);
	}

	/**
   	 * Mark all pixels as needing update.
   	 */
	 
	public void updatePixels()
	{	
		// Replace the image with one with the current pixels
		image = Image.createRGBImage(pixels,width,height,false);
	}
}