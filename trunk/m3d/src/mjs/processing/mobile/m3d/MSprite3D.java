/*

	M3d - 3D Graphics Library for Mobile Processing

	Copyright (c) 2005-2007 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.m3d;

import javax.microedition.m3g.Appearance;
import javax.microedition.m3g.CompositingMode;
import javax.microedition.m3g.Image2D;
import javax.microedition.m3g.Sprite3D;

import processing.core.PImage;

/**
 * Sprite 3D.
 *
 * @since 0.6
 */
public class MSprite3D extends M3dObject
{
	/**
	 * The native sprite
	 */	 
	private Sprite3D nativeSprite;
	
	/**
	 * Create a MSprite3D with the native 3D object.
	 *
	 * @param sprite the native sprite
	 */
	protected MSprite3D(Sprite3D sprite)
	{
		super(sprite);
		nativeSprite = sprite;
	}
	
	/**
	 * Create a MSprite3D with the native 3D object.
	 *
	 * @param pImage Image to show
	 *
	 * @since 0.6
	 */
	public MSprite3D(PImage pImage)
	{
		// Convert the native image to a M3G image
		Image2D image2D = new Image2D(Image2D.RGBA,pImage.image);

		// Create an appearance for the image
		Appearance appearance = new Appearance();

		// Don't show the transparent parts of the image
		CompositingMode compositingMode = new CompositingMode();
		compositingMode.setBlending(CompositingMode.ALPHA);
		appearance.setCompositingMode(compositingMode);
		
		// Create a 3D sprite
		nativeSprite = new Sprite3D(true,image2D,appearance);
		setObject3D(nativeSprite);
		
		// Scale the image to the specified size
		nativeSprite.scale(pImage.width,pImage.height,1.0f);
	}
	
	/**
	 * Change the image to show
	 *
	 * @param pImage Image to show
	 */
	public void image(PImage pImage)
	{
		// Convert the image to a M3G image
		Image2D image2D = new Image2D(Image2D.RGBA,pImage.image);
		nativeSprite.setImage(image2D);
	}
}
