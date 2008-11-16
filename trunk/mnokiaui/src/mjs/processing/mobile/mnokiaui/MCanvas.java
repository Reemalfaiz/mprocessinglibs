/*

	MNokiaUI - NokiaUI Support Library for Mobile Processing

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

package mjs.processing.mobile.mnokiaui;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;

import processing.core.PCanvas;
import processing.core.PMIDlet;

/**
 * Implementation of PCanvas to support alpha color
 *
 * This class implements the methods that use setColor buffer method to allow 
 * subclasses overwritte it
 *
 * @since 0.2
 */
public class MCanvas extends PCanvas
{
	public MCanvas(PMIDlet midlet)
	{
		super(midlet);
	}

	public void point(int x1, int y1) {
        if (stroke) {
            setColor(strokeColor);
            bufferg.drawLine(x1, y1, x1, y1);
        }
    }
    
    public void line(int x1, int y1, int x2, int y2) {
        if (stroke) {
            setColor(strokeColor);
            bufferg.drawLine(x1, y1, x2, y2);
            if (strokeWidth > 1) {
                boolean steep = Math.abs(y2 - y1) > Math.abs(x2 - x1);
                if (steep) {
                    int swap = x1;
                    x1 = y1;
                    y1 = swap;
                    swap = x2;
                    x2 = y2;
                    y2 = swap;
                }
                if (x1 > x2) {
                    int swap = x1;
                    x1 = x2;
                    x2 = swap;
                    swap = y1;
                    y1 = y2;
                    y2 = swap;
                }
                int dx = x2 - x1;
                int dy = (y2 > y1) ? y2 - y1 : y1 - y2;
                int error = 0;
                int halfWidth = strokeWidth >> 1;
                int y = y1 - halfWidth;
                int ystep;
                if (y1 < y2) {
                    ystep = 1;
                } else {
                    ystep = -1;
                }
                for (int x = x1 - halfWidth, endx = x2 - halfWidth; x <= endx; x++) {
                    if (steep) {
                        bufferg.fillArc(y, x, strokeWidth, strokeWidth, 0, 360);
                    } else {
                        bufferg.fillArc(x, y, strokeWidth, strokeWidth, 0, 360);
                    }
                    error += dy;
                    if ((2 * error) >= dx) {
                        y += ystep;
                        error -= dx;
                    }
                }
            }
        }
    }
    
    public void rect(int x, int y, int width, int height) {
        int temp;
        switch (rectMode) {
            case PMIDlet.CORNERS:
                temp = x;
                x = Math.min(x, width);
                width = Math.abs(x - temp);
                temp = y;
                y = Math.min(y, height);
                height = Math.abs(y - temp);
                break;
            case PMIDlet.CENTER:
                x -= width / 2;
                y -= height / 2;
                break;
        }
        if (fill) {
            setColor(fillColor);
            bufferg.fillRect(x, y, width, height);
        }
        if (stroke) {
            setColor(strokeColor);
            bufferg.drawRect(x, y, width, height);
        }
    }
    
    public void ellipse(int x, int y, int width, int height) {
        int temp;
        switch (ellipseMode) {
            case PMIDlet.CORNERS:
                temp = x;
                x = Math.min(x, width);
                width = Math.abs(x - temp);
                temp = y;
                y = Math.min(y, height);
                height = Math.abs(y - temp);
                break;
            case PMIDlet.CENTER:
                x -= width / 2;
                y -= height / 2;
                break;
            case PMIDlet.CENTER_RADIUS:
                x -= width;
                y -= height;
                width *= 2;
                height *= 2;
                break;
        }
        if (fill) {
            setColor(fillColor);
            bufferg.fillArc(x, y, width, height, 0, 360);
        }
        if (stroke) {
            setColor(strokeColor);
            bufferg.drawArc(x, y, width, height, 0, 360);
        }
    }
    
    protected void polygon(int startIndex, int endIndex) {
        //// make sure at least 2 vertices
        if (endIndex >= (startIndex + 2)) {
            //// make sure at least 3 vertices for fill
            if (endIndex >= (startIndex + 4)) {
                if (fill) {
                    setColor(fillColor);
                    
                    //// insertion sort of edges from top-left to bottom right
                    Vector edges = new Vector();
                    int edgeCount = 0;
                    int[] e1, e2;
                    int i, j;
                    int yMin = Integer.MAX_VALUE;
                    int yMax = Integer.MIN_VALUE;
                    for (i = startIndex; i <= endIndex; i += 2) {
                        e1 = new int[EDGE_ARRAY_SIZE];
                        if (i == startIndex) {
                            //// handle connecting line between start and endpoints
                            if (vertex[startIndex + 1] < vertex[endIndex + 1]) {
                                e1[EDGE_X1] = vertex[startIndex];
                                e1[EDGE_Y1] = vertex[startIndex + 1];
                                e1[EDGE_X2] = vertex[endIndex];
                                e1[EDGE_Y2] = vertex[endIndex + 1];
                            } else {
                                e1[EDGE_X1] = vertex[endIndex];
                                e1[EDGE_Y1] = vertex[endIndex + 1];
                                e1[EDGE_X2] = vertex[startIndex];
                                e1[EDGE_Y2] = vertex[startIndex + 1];
                            }                            
                        } else if (vertex[i - 1] < vertex[i + 1]) {
                            e1[EDGE_X1] = vertex[i - 2];
                            e1[EDGE_Y1] = vertex[i - 1];
                            e1[EDGE_X2] = vertex[i];
                            e1[EDGE_Y2] = vertex[i + 1];
                        } else {
                            e1[EDGE_X1] = vertex[i];
                            e1[EDGE_Y1] = vertex[i + 1];
                            e1[EDGE_X2] = vertex[i - 2];
                            e1[EDGE_Y2] = vertex[i - 1];
                        }                            
                        e1[EDGE_X] = e1[EDGE_X1];
                        e1[EDGE_DX] = e1[EDGE_X2] - e1[EDGE_X1];
                        e1[EDGE_DY] = e1[EDGE_Y2] - e1[EDGE_Y1];
                        
                        yMin = Math.min(e1[EDGE_Y1], yMin);
                        yMax = Math.max(e1[EDGE_Y2], yMax);
                        
                        for (j = 0; j < edgeCount; j++) {
                            e2 = (int[]) edges.elementAt(j);
                            if (e1[EDGE_Y1] < e2[EDGE_Y1]) {
                                edges.insertElementAt(e1, j);
                                e1 = null;
                                break;
                            } else if (e1[EDGE_Y1] == e2[EDGE_Y1]) {
                                if (e1[EDGE_X1] < e2[EDGE_X1]) {
                                    edges.insertElementAt(e1, j);
                                    e1 = null;
                                    break;
                                }
                            }
                        }
                        if (e1 != null) {
                            edges.addElement(e1);
                        }
                        edgeCount += 1;
                    }
                    
                    //// draw scanlines
                    Vector active = new Vector();
                    int activeCount = 0;
                    for (int y = yMin; y <= yMax; y++) {
                        //// update currently active edges
                        for (i = activeCount - 1; i >= 0; i--) {
                            e1 = (int[]) active.elementAt(i);
                            if (e1[EDGE_Y2] <= y) {
                                //// remove edges not intersecting current scan line
                                active.removeElementAt(i);
                                activeCount--;
                            } else {
                                //// update x coordinate
                                e1[EDGE_X] = (y - e1[EDGE_Y1]) * e1[EDGE_DX] / e1[EDGE_DY] + e1[EDGE_X1];
                            }
                        }
                        
                        //// re-sort active edge list
                        Vector newActive = new Vector();
                        for (i = 0; i < activeCount; i++) {
                            e1 = (int[]) active.elementAt(i);
                            
                            for (j = 0; j < i; j++) {
                                e2 = (int[]) newActive.elementAt(j);
                                if (e1[EDGE_X] < e2[EDGE_X]) {
                                    newActive.insertElementAt(e1, j);
                                    e1 = null;
                                    break;
                                }
                            }
                            if (e1 != null) {
                                newActive.addElement(e1);
                            }
                        }
                        active = newActive;
                        
                        //// insertion sort any new intersecting edges into active list
                        for (i = 0; i < edgeCount; i++) {
                            e1 = (int[]) edges.elementAt(i);
                            if (e1[EDGE_Y1] == y) {
                                for (j = 0; j < activeCount; j++) {
                                    e2 = (int[]) active.elementAt(j);
                                    if (e1[EDGE_X] < e2[EDGE_X]) {
                                        active.insertElementAt(e1, j);
                                        e1 = null;
                                        break;
                                    }
                                }
                                if (e1 != null) {
                                    active.addElement(e1);
                                }
                                activeCount++;
                                
                                //// remove from edges list
                                edges.removeElementAt(i);
                                edgeCount--;
                                //// indices are shifted down one
                                i--;
                            } else {
                                break;
                            }
                        }
                        //// draw line segments between pairs of edges
                        for (i = 1; i < activeCount; i += 2) {
                            e1 = (int[]) active.elementAt(i - 1);
                            e2 = (int[]) active.elementAt(i);
                            
                            bufferg.drawLine(e1[EDGE_X], y, e2[EDGE_X], y);
                        }
                    }
                }
            }
            if (stroke) {
                setColor(strokeColor);
                for (int i = startIndex + 2; i <= endIndex; i += 2) {
                    line(vertex[i - 2], vertex[i - 1], vertex[i], vertex[i + 1]);
                }
                line(vertex[endIndex], vertex[endIndex + 1], vertex[startIndex], vertex[startIndex + 1]);
            }
        }
    }

    public void background(int gray) {
        if (((gray & 0xff000000) == 0) && (gray <= colorMaxX)) {
            setColor(color(gray, colorMaxA));
        } else {
            setColor(gray);
        }    
        bufferg.fillRect(0, 0, width, height);
    }
    
    public void background(int value1, int value2, int value3) {
        setColor(color(value1, value2, value3));
        bufferg.fillRect(0, 0, width, height);
    }
    
    public void text(String data, int x, int y) {
        if (textFont == null) {
            throw new RuntimeException("The current font has not yet been set with textFont()");
        }
        //// for system fonts, set fillcolor
        setColor(fillColor);
        //// check for embedded new-line characters
        if (data.indexOf('\n') >= 0) {
            text(data, x, y - textFont.baseline, Integer.MAX_VALUE, Integer.MAX_VALUE);
        } else {
            textFont.draw(bufferg, data, x, y, textAlign);
        }
    }
    
    public void text(String[] data, int x, int y, int width, int height) {
        if (textFont == null) {
            throw new RuntimeException("The current font has not yet been set with textFont()");
        }
        //// for system fonts, set fillcolor
        setColor(fillColor);
        //// save current clip and apply clip to bounding area
        pushMatrix();
        clip(x, y, width, height);
        //// adjust starting baseline so that text is _contained_ within the bounds
        int textX = x;
        y += textFont.baseline;
        String line;
        for (int i = 0, length = data.length; i < length; i++) {
            line = data[i];
            //// calculate alignment within bounds
            switch (textAlign) {
                case PMIDlet.CENTER:
                    textX = x + ((width - textWidth(line)) >> 1);
                    break;
                case PMIDlet.RIGHT:
                    textX = x + width - textWidth(line);
                    break;
            }
            textFont.draw(bufferg, line, textX, y, PMIDlet.LEFT);
            y += textLeading;
        }
        //// restore clip
        popMatrix();
    }
    
	/**
	 * This method set the current color
	 * 
	 * @param color The color to set
	 */
	protected void setColor(int color)
	{
		bufferg.setColor(color);
	}
}
