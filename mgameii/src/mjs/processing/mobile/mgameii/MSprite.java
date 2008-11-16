/*

	MGame - Game Library for Mobile Processing

	Copyright (c) 2006-2007 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mgameii;

import javax.microedition.lcdui.game.*;

import processing.core.*;

/**
 * A Sprite is a basic visual element that can be rendered with one of several 
 * frames stored in an Image
 *
 * @author Mary Jane Soft - Marlon J. Manrique
 */

public class MSprite extends Sprite
{
	/**
	 * Creates a new non-animated Sprite using the provided PImage.
	 * This constructor is functionally equivalent to calling new 
	 * Sprite(image, image.getWidth(), image.getHeight())
	 *
	 * By default, the Sprite is visible and its upper-left corner is positioned at (0,0) 
	 * in the painter's coordinate system. 
	 *
	 * @param pImage the Image to use as the single frame for the Sprite
	 */
	 
	public MSprite(PImage pImage)
	{
		super(pImage.image);
	}

	/**
	 * Creates a new animated Sprite using frames contained in the provided Image. 
	 * The frames must be equally sized, with the dimensions specified by frameWidth and frameHeight.
	 * They may be laid out in the image horizontally, vertically, or as a grid. The width of the source 
	 * image must be an integer multiple of the frame width, and the height of the source image must be an 
	 * integer multiple of the frame height. The values returned by Layer.getWidth() and Layer.getHeight() 
	 * will reflect the frame width and frame height subject to the Sprite's current transform.
	 * 
	 * Sprites have a default frame sequence corresponding to the raw frame numbers, starting with frame 0.
	 * The frame sequence may be modified with setFrameSequence(int[]).
	 *
	 * By default, the Sprite is visible and its upper-left corner is positioned at (0,0) in the painter's 
	 * coordinate system. 
	 * 
	 * @param pImage the Image to use for Sprite
	 * @param frameWidth the width, in pixels, of the individual raw frames
	 * @param frameHeight the height, in pixels, of the individual raw frames
	 */

	public MSprite(PImage pImage, int frameWidth, int frameHeight)
	{
		super(pImage.image,frameWidth,frameHeight);
	}

	/**
	 * Creates a new Sprite from another Sprite.
	 *
	 * All instance attributes (raw frames, position, frame sequence, current frame, reference point, 
	 * collision rectangle, transform, and visibility) of the source Sprite are duplicated in the new Sprite. 
	 *
	 * @param mSprite the Sprite to create a copy of
	 */

	public MSprite(MSprite mSprite)
	{
		super(mSprite);
	}

	/**
	 * Gets the horizontal position of this Sprite's upper-left corner in the painter's coordinate system.
	 * 
	 * @return the Sprite's horizontal position.
	 */

	public int x()
	{
		return getX();
	}

	/**
	 * Gets the vertical position of this Sprite's upper-left corner in the painter's coordinate system.
	 *
	 * @param the Layer's vertical position.
	 */

	public int y()
	{
		return getY();
	}
}
