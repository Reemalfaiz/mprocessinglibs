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

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

import processing.core.*;

/**
 * MGame enable the development of rich gaming content for wireless devices.
 *
 * @author Mary Jane Soft - Marlon J. Manrique
 */

public class MGame extends PCanvas
{
	/**
	 * Processing MIDlet. 
	 */
	 
	private PMIDlet pMIDlet;

	/**
	 * The Layer Manager.
	 */
	
	LayerManager layerManager;

	/**
	 * Create the drawing space for a Game.
	 *
	 * @param pMIDlet The midlet
	 */
	 	
	public MGame(final PMIDlet pMIDlet)
	{
		super(pMIDlet);

		// Create the layer manages
		layerManager = new LayerManager();

		// Set the midlet reference
		this.pMIDlet = pMIDlet;

		// Set listeners
		pMIDlet.canvas = this;
		addCommand(pMIDlet.cmdExit);
		setCommandListener(pMIDlet);

 		// Display the MGame
  		Display.getDisplay(pMIDlet).setCurrent(this);		
	}

	/**
	 * Draw the game scene.
	 *
	 * @param g The graphics context
	 */

	public void paint(Graphics g)
	{
		super.paint(g);
		
		layerManager.paint(g,0,0);
	}

	/**
	 * Returns the game canvas like PCanvas
	 * 
	 * @return The Game Canvas
	 */

	public PCanvas canvas()
	{
		return this;
	}

	/**
	 * Returns the number of Layers.
	 * 
	 * @returns the number of Layers
	 */
	
	public int numLayers()
	{
		return layerManager.getSize();
	}

	/**
	 * Sets the view window on the LayerManager.
	 * 
	 * The view window specifies the region that the LayerManager draws when its 
	 * paint(javax.microedition.lcdui.Graphics, int, int) method is called. 
	 * It allows the developer to control the size of the visible region, as well as the location 
	 * of the view window relative to the LayerManager's coordinate system.
	 *
	 * The view window stays in effect until it is modified by another call to this method. 
	 * By default, the view window is located at (0,0) in the LayerManager's coordinate system and its width 
	 * and height are both set to Integer.MAX_VALUE. 
	 * 
	 * @param x - the horizontal location of the view window relative to the LayerManager's origin
	 * @param y - the vertical location of the view window relative to the LayerManager's origin
	 * @param width - the width of the view window
	 * @param height - the height of the view window
	 */

	public void viewWindow(int x, int y, int width, int height)
	{
		layerManager.setViewWindow(x,y,width,height);
	}

	/**
	 * Appends a Layer to this game. 
	 * 
	 * The layer is appended to the list of existing Layers such that it has the highest index 
	 * (i.e. it is furthest away from the user). The Layer is first removed from this LayerManager 
	 * if it has already been added.
	 * 
	 * @param layer the Layer to be added
	 */

	public void append(Layer layer)
	{
		layerManager.append(layer);
	}

	/**
	 * Inserts a new Layer in this LayerManager at the specified index. 
	 * The Layer is first removed from this LayerManager if it has already been added.
	 * 
	 * @param layer - the Layer to be inserted
	 * @param index - the index at which the new Layer is to be inserted
	 */

	public void insert(Layer layer, int index)
	{
		layerManager.insert(layer,index);
	}

	/**
	 * Removes the specified Layer from this LayerManager. 
	 * This method does nothing if the specified Layer is not added to the this LayerManager.
	 * 
	 * @param layer - the Layer to be removed
	 */	 

	public void remove(Layer layer)
	{
		layerManager.remove(layer);
	}
}
