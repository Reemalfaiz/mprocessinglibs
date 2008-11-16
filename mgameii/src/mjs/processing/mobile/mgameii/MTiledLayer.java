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
 * A TiledLayer can be rendered by manually calling its paint method; it can also be rendered 
 * automatically using a LayerManager object.
 *
 * @author Mary Jane Soft - Marlon J. Manrique
 */

public class MTiledLayer extends TiledLayer
{
	/**
	 * Creates a new TiledLayer.
	 *
	 * @param columns the width of the TiledLayer, expressed as a number of cells
	 * @param rows the height of the TiledLayer, expressed as a number of cells
	 * @param pImage the Image to use for creating the static tile set
	 * @param tileWidth the width in pixels of a single tile
	 * @param tileHeight the height in pixels of a single tile
	 */
	 
	public MTiledLayer(int columns, int rows, PImage pImage, int tileWidth, int tileHeight)
	{
		super(columns,rows,pImage.image,tileWidth,tileHeight);
	}

	/**
	 * Fill the cell with the specified tiles.
	 *
	 * @param map Array with the tile number for each cell
	 */

	public void map(int[] map)
	{
		// Get current number of columns and rows
		int numCols = getColumns();
		int numRows = getRows();

		// Map length (performance)		
		int mapLength = map.length; 

		// col and row number of the cell to set
		int col,row;

		// Set the tile for the col and row calculated
		for(int i=0; i<mapLength; i++)
		{
			col = i % numCols;
			row = (i - col) / 10;
			setCell(col,row,map[i]);
		}
	}

	/**
	 * Fill the cell with the specified tiles.
	 *
	 * @param map Matrix with the tile number for each cell
	 */

	public void map(int[][] map)
	{
		// Get current number of columns and rows
		int numCols = getColumns();
		int numRows = getRows();
	
		// Set the tile for the col and row
		for(int i=0; i<numRows; i++)
			for(int j=0; j<numCols; j++)
				setCell(i,j,map[i][j]);
	}	
}
