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

import javax.microedition.lcdui.*;

import com.tinyline.tiny2d.*;

import processing.core.*;

/**
 * MTinyLine2d Graphics System.
 *
 * This class override the drawing primitives of Mobile Processing,
 * and draw the content using TinyLine2D Graphics.
 * 
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */

// Problem:
//
// This class extends PCanvas and uses a lot of features like fillColor,
// translateX, translateY, width, height, rectMode, ..., because all this
// attributes has private access can't be access from this class.
// 
// A local attribute has been created to solve the problem

public class MTinyLine2d extends PCanvas
{
	/**
	 * Processing MIDlet. 
	 */
	 
	private PMIDlet pMIDlet;

	/**
	 * Command used to exit from the MIDlet. Same as PMIDlet cmdExit
	 */

	private Command cmdExit;

	/**
	 * The fill color.
	 */

	private TinyColor fillColor;

	/**
	 * The fill color.
	 */

	private TinyColor strokeColor;

	/**
	 * Traslation X coordinate.
	 */

	private int translateX;
	
	/**
	 * Traslation Y coordinate.
	 */

	private int translateY;

	/**
	 * Traslation Z coordinate.
	 */

	private int translateZ;

	/**
	 * Rotate angle in X.
	 */

	private float rotateX;

	/**
	 * Rotate angle in Y.
	 */

	private float rotateY;

	/**
	 * Rotate angle in Z.
	 */

	private float rotateZ;	

	/**
	 * Current width of the screen
	 */

	private int width;

	/**
	 * Current height of the screen
	 */

	private int height;

	/**
	 * Current rectangle mode.
	 */

	private int rectMode;

	/**
	 * Image mode
	 *
	 */

	private int imageMode;

	/**
	 * Alignment for drawing text
	 *
	 */

	private int textAlign;

	/**
	 * Current Font
	 *
	 */

	private PFont textFont;

	/**
	 * Background color.
	 *
	 * This color is used to fill the background of the a text to draw,
	 * an optimal solution is set a transparent color to the image.
	 * 
	 */
	  
	private TinyColor backgroundColor;

	/**
	 * The weight (in pixels) of the stroke
	 *
	 */

	private int strokeWeight;

	/**
	 * Current ellipse mode.
	 */

	private int ellipseMode;

	/** 
	 * The Graphics Tree 
	 */
	 
    private GTRootNode rootNode;

	/**
	 * The Graphics Renderer.
	 */

	private GTRenderer renderer;

	/**
	 * Create the drawing space for the 2D objects.
	 *
	 * @param pMIDlet The midlet
	 */

	public MTinyLine2d(final PMIDlet pMIDlet)
	{
		super(pMIDlet);

		// Set the midlet reference
		this.pMIDlet = pMIDlet;

		// set the canvas like current. 
		// pMIDlet.canvas = this;
		// Error, Canvas has protected privilege		

		// Problem:
		//
		// The canvas can't be assigned directly from this class to the PMIDlet
		// The exit command has to be created again and listener also

		// Create the exit command, 
		// cmdExit on PMIDlet have protected privilege
		
		cmdExit = new Command("Exit",Command.EXIT,1);
		addCommand(cmdExit);
		
		// Listen the exit command and also the softkey
		// We can't use pMIDlet because cmdExit is different
		
		setCommandListener
		(
			new CommandListener()
			{
				public void commandAction(Command c, Displayable d)
				{
					// Exit from the PMIDlet
					if(c == cmdExit)
						pMIDlet.exit();
					else 
						// Send sofkey pressed event
						pMIDlet.enqueueEvent(PMIDlet.EVENT_SOFTKEY_PRESSED,0,c.getLabel());
				}
			}
		);
		

		// Initialize attributes width, heigth, fillColor, rectMode
		// imageMode same as PCanvas

		width = getWidth();
		height = getHeight();

		fill(255);
		rectMode(PMIDlet.CORNER);
		imageMode(PMIDlet.CORNER);
		textAlign(PMIDlet.LEFT);
		strokeWeight(1);
		stroke(0);
		ellipseMode(PMIDlet.CENTER);

		// Set the default font like systems default font
		textFont = new PFont(Font.getDefaultFont());

		// Create the Graphics Renderer
		renderer = new MRenderer(this);
		
		TinyPixbuf	buffer = new TinyPixbuf(width, height);
        renderer.setPixelBuffer(buffer);
		renderer.clearRect(new TinyRect(0,0,width,height));

		// Set the background color of the scene to gray
		// The PCanvas make this call but the world was no created
		background(200);
	}

	/**
	 * Returns the TinyLine2D canvas like PCanvas
	 * 
	 * @return The TinyLine2D Canvas
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
		g.drawRGB(renderer.getPixels32(),0,renderer.width,0,0,renderer.width,renderer.height,false);
	}

	/**
	 * Sets the current alignment for drawing text.
	 *
	 * The parameters LEFT, CENTER, and RIGHT set the display characteristics of the 
	 * letters in relation to the values for the x and y parameters of the text() 
	 * function.
	 * 
	 * @param mode Either LEFT, CENTER, or RIGHT
	 */

	public void textAlign(int mode)
	{
		if((mode != PMIDlet.LEFT) && (mode != PMIDlet.CENTER) && (mode != PMIDlet.RIGHT))
			throw new IllegalArgumentException("Invalid textAlign MODE value");
			
		textAlign = mode;
	}

	/**
	 * The origin of the ellipse is modified by the ellipseMode() function.
	 * 
	 * The default configuration is ellipseMode(CENTER), which specifies the location of the ellipse as 
	 * the center of the shape. The CENTER_RADIUS mode is the same, but the width and height parameters 
	 * to ellipse() specify the radius of the ellipse, rather than the diameter. 
	 * The CORNER mode draws the shape from the upper-left corner of its bounding box.
	 * The CORNERS mode uses the four parameters to ellipse() to set two opposing corners of the ellipse's 
	 * bounding box. The parameter must be written in "ALL CAPS" because Processing is a case sensitive language.
	 *
	 * @param mode The mode to draw ellipses either CENTER, CENTER_RADIUS, CORNER, CORNERS
	 *
	 * @since 0.3
	 */ 
	 
	public void ellipseMode(int mode)
	{
		if((mode >= PMIDlet.CENTER) && (mode <= PMIDlet.CORNERS))
			ellipseMode = mode;
	}

	/**
	 * Sets the width of the stroke used for lines, points, and the border around shapes.
	 * All widths are set in units of pixels. 
	 *
	 * @param width the weight (in pixels) of the stroke
	 */

	public void strokeWeight(int width)
	{
		strokeWeight = width + 1;
	}
	

	public void newPixels(int x, int y, int w, int h)
	{
		repaint(x,y,w,h);
		serviceRepaints();
	}	

	/**
	 * Set the fill color.
	 *
	 * @param gray the color to use to fill.
	 */

	public void fill(int gray)
	{
		// Is a component color or a true color
		if(((gray&0xff000000) == 0) && (gray<=255))
			fill(gray,gray,gray);
		else
			fillColor = new TinyColor(gray);
	}

	/**
	 * Set the fill color.
	 * 
	 * @param value1 red value.
	 * @param value2 green value.
	 * @param value3 blue value.
	 */
	 
	public void fill(int value1, int value2, int value3)
	{
		// Calculate the color using the PCanvas method
		fillColor = new TinyColor(color(value1,value2,value3));
	}

	/**
	 * Set the stroke color.
	 *
	 * @param gray the color to use to stroke.
	 */

	public void stroke(int gray)
	{
		// Is a component color or a true color
		if(((gray&0xff000000) == 0) && (gray<=255))
			stroke(gray,gray,gray);
		else
			strokeColor = new TinyColor(gray);
	}

	/**
	 * Sets the color used to draw lines and borders around shapes. This color is either specified 
	 * in terms of the RGB or HSB color depending on the current colorMode() (the default color space 
	 * is RGB, with each value in the range from 0 to 255). Note: the value for the parameter "gray" 
	 * must be less than or equal to the current maximum value as specified by colorMode(). 
	 * The default maximum value is 255.
	 * 
	 * @param value1 red value.
	 * @param value2 green value.
	 * @param value3 blue value.
	 */
	 
	public void stroke(int value1, int value2, int value3)
	{
		// Calculate the color using the PCanvas method
		strokeColor = new TinyColor(color(value1,value2,value3));
	}
	

	/**
	 * Set the current rect mode.
	 *
	 * @param mode The mode to draw rects
	 */ 
	 
	public void rectMode(int mode)
	{
		if((mode >= PMIDlet.CENTER) && (mode <= PMIDlet.CORNERS))
			rectMode = mode;
	}

	/**
	 * Set the current image mode.
	 *
	 * @param mode The mode to draw images
	 */ 
	 
	public void imageMode(int mode)
	{
		if((mode >= PMIDlet.CENTER) && (mode <= PMIDlet.CORNERS))
			imageMode = mode;
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

	private GTShapeNode createShape(TinyPath tinyPath)
	{
		GTShapeNode shape = new GTShapeNode();
		shape.setPath(tinyPath);
		shape.setBounds(shape.getPath().getBBox());
		shape.setFillColor(fillColor);
		shape.setStrokeColor(strokeColor);
		shape.setLineThickness(strokeWeight<<TinyUtil.FIX_BITS);

		rootNode.add(shape,-1);

		//renderer.setDevDirtyRgn(shape.getBounds());
		renderer.invalidate();
        renderer.repaint();

		return shape;
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

		createShape(TinyPath.roundRectToPath(x<<TinyUtil.FIX_BITS,y<<TinyUtil.FIX_BITS,width<<TinyUtil.FIX_BITS,height<<TinyUtil.FIX_BITS,0,0));
	}

	public void smooth()
	{
		renderer.setAntialiased(true);
		renderer.invalidate();
        renderer.repaint();
	}

	public void noSmooth()
	{
		renderer.setAntialiased(false);
		renderer.invalidate();
        renderer.repaint();
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
		int[] coords = calculateCoords(rectMode,x,y,width,height);
		
		x = coords[0];
		y = coords[1];
		width = coords[2];
		height = coords[3];

		createShape(TinyPath.ovalToPath(x<<TinyUtil.FIX_BITS,y<<TinyUtil.FIX_BITS,width<<TinyUtil.FIX_BITS,height<<TinyUtil.FIX_BITS));
	}

	/**
	 * Set the background of the scene, if also used to remove all objects from scene.
	 * 
	 * @param gray the color to use like background.
	 */

	public void background(int gray)
	{
		// Is a component color or a true color
 		if(((gray&0xff000000)==0) && (gray<=255))
			background(gray, gray, gray);
		else 
			setBackground(new Integer(gray));
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
		// Calculate the color using the PCanvas method
		int color = color(value1,value2,value3);
		setBackground(new Integer(color));
	}

	/**
	 * Set the background of the scene with a image, if also used to remove all objects from scene.
	 * 
	 * @param img The PImage to use like background.
	 */

	public void background(PImage img)
	{
		// ToDo : Background is an image
	}

	/**
	 * Set the background of the scene.
	 * 
	 * This method is used by background methods to avoid different implementation 
	 * when the background is an image or integer.
	 * 
	 * The method receive a parameter that could be an object instance of Integer or 
	 * Image2D.
	 *
	 * @param obj a integer color value or a Image2D to use like background
	 */

	private void setBackground(Object value)
	{
		// No renderer available yet
		if(renderer == null) 
			return;
			
		// Create a new rootNode
		rootNode = new GTRootNode();
		renderer.setTree(rootNode);

		// If value is an Integer set like color else
		// the value is a image

		if(value instanceof Integer)
		{
			TinyColor currentFillColor = fillColor;
			int currentStrokeWeight = strokeWeight;
			
			fill(((Integer) value).intValue());
			strokeWeight = 0;
			rect(0,0,width,height);

			fillColor = currentFillColor;
			strokeWeight = currentStrokeWeight;
		}
		else
		{
			// ToDo : Background is image
		}
	}
}
