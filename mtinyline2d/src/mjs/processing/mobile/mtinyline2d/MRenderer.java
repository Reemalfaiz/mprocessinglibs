/*

	MTinyLine2d - 2D Library based on TinyLine2D for Mobile Processing

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

package mjs.processing.mobile.mtinyline2d;

import com.tinyline.tiny2d.*;

/**
 *
 * 
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */

public class MRenderer extends GTRenderer
{
	private MTinyLine2d mTinyLine2d;

	public MRenderer(MTinyLine2d mTinyLine2d)
	{
		this.mTinyLine2d = mTinyLine2d;
	}
	
	/**
	 * Determine if there is a consumer that is currently interested in data 
	 * from this GTRenderer.
	 * 
	 * @return true it has a consumer, false otherwise
	 */
	 
	public boolean hasConsumer()
	{
		return true;
	}

	/**
	 * The imageComplete method is called when the GTRenderer is finished 
	 * delivering all of the pixels to a consumer.
	 */

	public void imageComplete()
	{
	}

	/**
	 * Send a rectangular region of the buffer of pixels to any consumer that is interested
	 * in the data from this GTRenderer.
	 */
	 
	public void sendPixels()
	{
		mTinyLine2d.newPixels(clipRect.xmin, clipRect.ymin, clipRect.xmax - clipRect.xmin, clipRect.ymax - clipRect.ymin);
	}
}
