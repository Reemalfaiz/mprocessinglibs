/*

	MSynth - Sound Synthesis Library for Mobile Processing

	Copyright (c) 2007 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.msynth;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import java.util.Enumeration;
import java.util.Vector;

import processing.core.PImage;
import processing.core.PMIDlet;

/**
 * Creates a wave image
 */
public class MWaveImage implements MGenerator
{
	/**
	 * Width of the image
	 */
	private int width;
	
	/**
	 * Height of the image
	 */
	private int height;

	/**
	 * Background color of the viewer
	 */
	public int bgColor = 0x000000;
	
	/**
	 * Color of the wave
	 */	
	public int signalColor = 0xFFFFFF;
	
	/**
	 * Current position under the stream
	 */
	private double position;
	
	/**
	 * Native image to draw the wave
	 */
	private Image image;
	
	/**
	 * Graphics context use to draw inside the wave image
	 */	 
	private Graphics graphics;
	
	/**
	 * Mobile Processing image
	 */
	private PImage pImage;		
	
	/**
	 * Previous x dot value to draw a line. 
	 * Is init in the max value to recognize when is set the first one
	 */
	private int previousX = Integer.MAX_VALUE;
	
	/**
	 * Previous y dot value to draw a line. 
	 */
	private int previousY;
	
	/**
	 * Frequency of the signal to show 
	 */
	private double frequency;
	
	/**
	 * Number of sample to show in the viewer
	 */
	private int numSamplers;
	
	/**
	 * Generator to show
	 */
	private MGenerator generator;

	/**
	 * Create a wave image with the generator specified
	 *
	 * @param generator Generator to show
	 */
	public MWaveImage(MGenerator generator, int width, int height)
	{
		this.generator = generator;
		this.width = width;
		this.height = height;

		// Init the frequency at 1
		frequency(1.0);		
	}
	
	/**
	 * Set the signal generator to show 
	 *
	 * @param generator Generator to show	 
	 */
	public void generator(MGenerator generator)
	{
		this.generator = generator;
	}
	
	/**
	 * Return the generator current show
	 *
	 * @return generator current show
	 */
	public MGenerator generator()
	{
		return generator;
	}
	
	/**
	 * Update the frequency use by the viewer
	 *
	 * @param frequency new frequency of the viewer
	 */
	public void frequency(double frequency)
	{
		// Set the frequency 
		this.frequency = frequency;
		
		// Calculate the new number of samples to show 
		numSamplers = (int) (8000/frequency);
	}
	
	/**
	 * Create the image to draw the wave
	 */
	private void createImage()
	{
		// Create the native image with the current width and height
		image = Image.createImage(width,height);
		
		// Get the graphics context
		graphics = image.getGraphics();
		
		// Draw the background with the color specified
		graphics.setColor(bgColor);
		graphics.fillRect(0,0,width,height);
		
		// Create the mobile processing image 
		pImage = new PImage(image);
	}
	
	/**
	 * Return the next sample product of add the signals 
	 *
	 * @return next sample of the signal
	 */
	public double nextSample()
	{
		// If not image created yet, create one 
		if(pImage == null)
			createImage();
	
		// Get the signal value
		double sample = generator.nextSample();

		// If wave samples are not draw yet 
		if(position <= numSamplers)
		{
			// Calculate the x,y coordinate of the wav sample 
			int x = (int) (position/numSamplers * width);
			int y = (int) (height - (sample + 1.0) * (height-2)/2);
		
			// If is not the first sample, draw a line between 
			// previous and next sample 
			if(previousX != Integer.MAX_VALUE)
			{
				graphics.setColor(signalColor);
				graphics.drawLine(previousX,previousY,x,y);
			}

			// Update the previous sample position
			previousX = x;
			previousY = y;
		}
		
		// Update the position in the stream		
		position++;		
		
		// Return the sample value
		return sample;
	}
	
	/**
	 * Return the image of the wave
	 *
	 * @return The image of the wave
	 */
	public PImage image()
	{
		// If not image created yet, create one 
		if(pImage == null)
			createImage();
			
		return pImage;
	}
	
	/**
	 * Returns a string representation of the generator 
	 *
	 * @param String representation of the generator
	 */
	public String toString()
	{
		return "MWaveImage : " + generator;
	}		
}
