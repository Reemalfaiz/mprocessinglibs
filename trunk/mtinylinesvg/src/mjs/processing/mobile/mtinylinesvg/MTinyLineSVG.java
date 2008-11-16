/*

	MTinyLineSVG - SVG Library based on TinyLineSVG for Mobile Processing

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

package mjs.processing.mobile.mtinylinesvg;

import java.io.*;

import javax.microedition.lcdui.*;
import javax.microedition.io.*;

import com.tinyline.svg.*;
import com.tinyline.tiny2d.*;
import com.tinyline.util.*;

import processing.core.*;

/**
 * MTinyLineSVG Graphics System.
 *
 * This class override the drawing primitives of Mobile Processing,
 * and draw the content using TinyLineSVG Graphics.
 * 
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */

public class MTinyLineSVG extends PCanvas
{
	/**
	 * Current Text Size
	 */

	private int textSize;
	
	/**
	 * The SVG Document use to render the scene
	 */

	private SVGDocument svgDocument;
	 
	/**
	 * The SVGRaster
	 */
	 
	private SVGRaster raster;

	/**
	 * Inits the graphics system with support for alpha channel color
	 *
	 * @param pMIDlet The midlet associated with the application
	 */
	
	public MTinyLineSVG(PMIDlet pMIDlet)
	{
		// PCanvas Initilization
		super(pMIDlet);

		// Add the exit command to the canvas
		addCommand(pMIDlet.cmdExit);

		// If the custom command is set add to the canvas
		if(pMIDlet.cmdCustom != null)
			addCommand(pMIDlet.cmdCustom);

		// Set the listener command like the MIDlet
		setCommandListener(pMIDlet);

		// Creates the SVG raster
		TinyPixbuf buffer = new TinyPixbuf(width, height);
		raster = new SVGRaster(buffer);

		// Set background
		background(200);

		// Init Default Values
		fill(0xFFFFFFFF);
		textSize = 14;
	}

	/**
	 * Returns this object like the PCanvas.
	 *
	 * This is used by convention in the MLibraries 
	 *
	 * @return The Canvas
	 */

	public PCanvas canvas()
	{
		return this;
	}

	/**
	 * Draw the Graphics.
	 * 
	 * @param g The graphics context
	 */

	public void paint(Graphics g)
	{
		// Get the pixels and draw it
		TinyPixbuf pixbuf = raster.getPixelBuffer();
		g.drawRGB(pixbuf.pixels32,0,pixbuf.width,0,0,pixbuf.width,pixbuf.height,false);
	}	

	/**
	 * Calculate the x,y position and width, height of the specified data
	 * acoording with the mode
	 *
	 * @param mode draw mode
	 * @param x coordinate x
	 * @param y coordinate y
	 * @param width width or coordinate x of the other corner
	 * @param height height or coordinate y of the other corner
	 *
	 */

	private int[] calculateCoords(int mode, int x, int y, int width, int height)
	{
		// Calculate real x,y, width and height coordinates 
		// according with the mode
		
		switch(mode)
		{
			case PMIDlet.CORNERS :

						// Calculate a rect from point (x,y) to (width,height)

						int temp;

						// Calculate minor x coordinate and width
						if(x > width)
						{
							temp = x;
							x = width;
							width = temp - width;
						}
						else
							width -= x;

						// Calculate minor y coordinate and height
						if(y > height)
						{
							temp = y;
							y = height;
							height = temp - height;
						}
						else
							height -= y;
						
						break;
						
			case PMIDlet.CENTER :

						// Calculate a rect with center on (x,y) and dimesions (width,height)
						
						x -= width/2;
						y -= height/2;
						break;

			
			case PMIDlet.CENTER_RADIUS : 

						// The width and height are the radius not the diameter
						width *= 2;
						height *= 2;
						break;
						
		}

		return new int[] { x, y, width, height };
	}

	/**
	 * Adds a new graphic element to the escene
	 *
	 * @param node Graphic Element
	 */

	private void addNode(SVGNode node)
	{
		// Get the current root from the document
		SVGSVGElem root = (SVGSVGElem) svgDocument.root;
		
		// Add the element like child of the root, -1 set like last element
		root.addChild(node,-1);

		// Set fill color, if no fill don't use color
		if(fill)
			node.fill = new TinyColor(fillColor);
		else
			node.fill = TinyColor.NONE;

		// Set stroke color and Width
		node.stroke = new TinyColor(strokeColor);
		node.strokeWidth = strokeWidth << TinyUtil.FIX_BITS;

		// Init the element
		node.createOutline();

		// Update raster and send pixels to draw
		raster.setDevClip(node.getDevBounds(raster));
		raster.update();
		raster.sendPixels();
	}

	/**
	 * Draw 2D rect on screen.
	 *
	 * A 3Dshape with the position and size is created.
	 *
	 * @param x coordinate x
	 * @param y coordinate y
	 * @param width width or coordinate x of the other corner
	 * @param height height or coordinate y of the other corner
	 */

	public void rect(int x, int y, int width, int height)
	{
		// Calculte coords and size according with the mode
		int[] coords = calculateCoords(rectMode,x,y,width,height);
		
		x = coords[0];
		y = coords[1];
		width = coords[2];
		height = coords[3];		

		// Create the rect element			
		SVGRectElem rect = (SVGRectElem) svgDocument.createElement(SVG.ELEM_RECT);

		// Set Rect properties
		rect.x = x << TinyUtil.FIX_BITS;
		rect.y = y << TinyUtil.FIX_BITS;
		rect.width = width << TinyUtil.FIX_BITS;
		rect.height = height << TinyUtil.FIX_BITS;

		// Add element to the current document
		addNode(rect);
	}

	/**
	 * Draws an ellipse (oval) in the display window. 
	 * 
	 * An ellipse with an equal width and height is a circle. 
	 * The first two parameters set the location, the third sets the width, and the fourth sets the height.
	 * The origin may be changed with the ellipseMode() function.
	 *
	 * @param x x-coordinate of the ellipse
	 * @param y y-coordinate of the ellipse
	 * @param width width of the ellipse
	 * @param height height of the ellipse
	 *
	 * @see ellipseMode(int mode)
	 */

	public void ellipse(int x, int y, int width, int height)
	{
		// Calculte coords and size according with the mode
		int[] coords = calculateCoords(ellipseMode,x,y,width,height);

		x = coords[0];
		y = coords[1];
		width = coords[2];
		height = coords[3];		
	
		// Create the ellipse element
		SVGEllipseElem ellipse = (SVGEllipseElem) svgDocument.createElement(SVG.ELEM_ELLIPSE);

		// Set ellipse properties
		ellipse.cx = (x + width/2)<< TinyUtil.FIX_BITS;
		ellipse.cy = (y + height/2) << TinyUtil.FIX_BITS;
		ellipse.rx = (width/2) << TinyUtil.FIX_BITS;
		ellipse.ry = (height/2) << TinyUtil.FIX_BITS;

		// Add element to the current document
		addNode(ellipse);
	}	

	/**
	 * Set the background of the scene, if also used to remove all objects from scene.
	 * 
	 * @param value1 red value.
	 * @param value2 green value.
	 * @param value3 blue value.
	 */

	public void background(int value1, int value2, int value3)
	{
		// If the raster is not available yet, do nothing
		// This happens when PCanvas constructor tries to set background
		if(raster == null)
			return;

		// Create a empty document and set like current
		svgDocument = raster.createSVGDocument();
		raster.setSVGDocument(svgDocument);
		
		// Calculate the color using the PCanvas method
		int color = color(value1,value2,value3);

		// Set the color
		raster.setBackground(color);

		// Init the raster and send pixels
		raster.setCamera();
		raster.invalidate();
		raster.update();
		raster.sendPixels();			
	}	

	/**
	 * Creates a SVGTextElem with the Current Information
	 *
	 * @param data the string to draw
	 * @param x x-coordinate of text
	 * @param y y-coordinate of text
	 * @param z z-coordinate of text
	 *
	 */

	public SVGTextElem createTextElem(String data, int x, int y)
	{
		// Create the Text element
		SVGTextElem text = (SVGTextElem) svgDocument.createElement(SVG.ELEM_TEXT);

		// Set Text properties
		text.str = new TinyString(data.toCharArray());
		text.x = x << TinyUtil.FIX_BITS;
		text.y = y << TinyUtil.FIX_BITS;
		text.fontSize = textSize << TinyUtil.FIX_BITS;

		// Set the textAnchor
		switch(textAlign)
		{
			case PMIDlet.LEFT : text.textAnchor = SVG.VAL_START; break;
			case PMIDlet.CENTER : text.textAnchor = SVG.VAL_MIDDLE; break;
			case PMIDlet.RIGHT : text.textAnchor = SVG.VAL_END; break;
		}

		// Returns the text element
		return text;
	}	

	/**
	 * Draws text to the screen.
	 *
	 * @param data the string to draw
	 * @param x x-coordinate of text
	 * @param y y-coordinate of text
	 * @param z z-coordinate of text
	 *
	 */

	public void text(String data, int x, int y)
	{
		// Creates and add the text element to the current document
		addNode(createTextElem(data,x,y));
	}	

	private SVGDocument loadSVGDocument(String locator)
	{
		// Create a empty document
		SVGDocument svgDoc = raster.createSVGDocument();

		try
		{
			// The stream to read the data
			InputStream inputStream = null;

			// If the locator is an url open a connection
			if(locator.startsWith("http://"))
			{
				ContentConnection connection = (ContentConnection) Connector.open(locator);
				inputStream = connection.openInputStream();
			}
			else
				// If is not a url open like resource
				inputStream = getClass().getResourceAsStream("/" + locator);

			// If the content is compressed 
			if(locator.endsWith("svgz"))
				inputStream = new GZIPInputStream(inputStream);
				
			// Create a new buffer
			TinyPixbuf pixbuf = raster.getPixelBuffer();
			SVGAttr attrParser = new SVGAttr(pixbuf.width, pixbuf.height);

			// Parse the document
			SVGParser parser = new SVGParser(attrParser);
			parser.load(svgDoc,inputStream);
		}
		catch(Exception e)
		{
			// If any error throws an exception
			throw new RuntimeException(e.getMessage());
		}

		return svgDoc;
	}

	/**
	 * Loads a SVG Document and shows into the canvas, this will erase all previous elements
	 *
	 * @param locator A locator string in URI syntax that describes the svg document location or resource route.
	 */

	public void load(String locator)
	{
		// load the svg document
		svgDocument = loadSVGDocument(locator);

		// Set the document and sends pixels
		raster.setSVGDocument(svgDocument);
		raster.setCamera();
		raster.update();
		raster.sendPixels();			
	}

	/**
	 * Draws all geometry with smooth (anti-aliased) edges. 
	 * This will slow down the frame rate of the application, 
	 * but will enhance the visual refinement.
	 */

	public void smooth()
	{
		raster.setAntialiased(true);
		raster.update();
	}

	/**
	 * Draws all geometry with jagged (aliased) edges.
	 */

	public void noSmooth()
	{
		raster.setAntialiased(false);
		raster.update();
	}

	/**
	 * Load a font from a SVG document
	 *
	 * @return The font presented into the SVG document
	 */

	public SVGFontElem loadSVGFont(String locator)
	{
		// Load the SVG Document
		SVGDocument svgDoc = loadSVGDocument(locator);

		// Get the default font
		SVGFontElem font = SVGDocument.getFont(svgDoc,SVG.VAL_DEFAULT_FONTFAMILY);

		// Return the font
		return font;
	}

	/**
	 * Set the current SVG Font for the document
	 *
	 * @param font SVG font loaded from some document and used to draw text
	 */

	public void textFont(SVGFontElem font)
	{
		// Set the font like the default font for all documents
    	SVGDocument.defaultFont = font;	
	}


	/**
	 * Sets the current font size. This size will be used in all subsequent calls 
	 * to the text() function. Font size is measured in units of pixels.
	 *
	 * @param size the size of the letters in units of pixels
	 */

	public void textSize(int size)
	{
		textSize = size;
	}	

	/**
	 * Calculates and the width of a text string.
	 *
	 * @param text the text
	 * @return width of the text
	 *
	 */

	public int textWidth(String text)
	{
		// Create the SVGTextElem
		SVGTextElem textElem = createTextElem(text,0,0);

		// Init the element
		textElem.createOutline();

		// Get the bounds of the element
		TinyRect bounds = textElem.getBounds();
		int width = bounds.xmax >> TinyUtil.FIX_BITS - bounds.xmin >> TinyUtil.FIX_BITS;

		// Return the width
		return width;
	}
}
