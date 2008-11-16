/*

	MWiimote - Wii Remote Library for Mobile Processing

	Copyright (c) 2008 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mwiimote;

/**
 * Whiteboard
 *
 * Creates a virtual board product of the projection points of the computer 
 * screen read from a wiimote.
 *
 * This class use a Projector-Camera Homography to calibrate the projection 
 * according wiimote data. 
 *
 * More info :
 * Smarter Presentations: Exploiting Homography in Camera-Projector Systems
 * Rahul Sukthankar, Robert G. Stockton, Matthew D. Mullin
 * http://www.cs.cmu.edu/~rahuls/pub/iccv2001-rahuls.pdf
 *  
 * @author Marlon J. Manrique
 */
public class MWhiteBoard
{
	/** 
	 * Board Coordinates 
	 */
	private int[] board;
	
	/** 
	 * Screen Coordinates 
	 */
	private int[] screen;
	
	/** 
	 * Transform matrix 
	 */
	public float[][] matrix;
	
	/** 
	 * Creates Whiteboard
	 */
	public MWhiteBoard()
	{
		// Create coordinates 
		board = new int[8];
		screen = new int[8];
		
		// Create matrix
		matrix = new float[8][8];
	}
	
	/**
	 * Update board coordinates 
	 */
	public void board(int[] board)
	{
		// Copy board data to internal array 
		System.arraycopy(board,0,this.board,0,this.board.length);
	}
	
	/**
	 * Update board coordinates
	 *
	 * @param x1 coordinate x of point one 
	 * @param y1 coordinate y of point one 
	 * @param x2 coordinate x of point two  
	 * @param y2 coordinate y of point two
	 * @param x3 coordinate x of point three 
	 * @param y3 coordinate y of point three 
	 * @param x4 coordinate x of point four
	 * @param y4 coordinate y of point four	  
	 */
	public void board(int x1, int y1, int x2, int y2, 
		int x3, int y3, int x4, int y4 )
	{
		// Set board values
		board[0] = x1;
		board[1] = y1;
		board[2] = x2;
		board[3] = y2;
		board[4] = x3;
		board[5] = y3;
		board[6] = x4;
		board[7] = y4;
	}	
	
	/**
	 * Update screen coordinates 
	 */
	public void screen(int[] screen)
	{
		// Copy board data to internal array 
		System.arraycopy(screen,0,this.screen,0,this.screen.length);
	}

	/**
	 * Update screen coordinates
	 *
	 * @param x1 coordinate x of point one 
	 * @param y1 coordinate y of point one 
	 * @param x2 coordinate x of point two  
	 * @param y2 coordinate y of point two
	 * @param x3 coordinate x of point three 
	 * @param y3 coordinate y of point three 
	 * @param x4 coordinate x of point four
	 * @param y4 coordinate y of point four	  
	 */
	public void screen(int x1, int y1, int x2, int y2, 
		int x3, int y3, int x4, int y4 )
	{
		// Set board values
		screen[0] = x1;
		screen[1] = y1;
		screen[2] = x2;
		screen[3] = y2;
		screen[4] = x3;
		screen[5] = y3;
		screen[6] = x4;
		screen[7] = y4;
	}
	
	/** 
	 * Compute Transformation matrix
	 */
	public void computeMatrix()
	{
		// Matrix One 
		float[][] m = new float[8][8];
		float[][] n = new float[8][1];
		
		// Fill the matrix with the screen data
		for(int i=0; i<4; i++)
		{
			// Copy the screen point
			n[i*2][0] = screen[i*2];
			n[i*2+1][0] = screen[i*2+1];
		}
					
		// Fill the matrix with the transformation values
		for(int i=0; i<4; i++)
		{
			// Row values 
			int row = i*2;
			int nextRow = row + 1;
			
			// Copy the board point 
			m[0][row] = board[row];
			m[1][row] = board[nextRow];
			
			// Fill other values 
			m[2][row] = 1;
			m[3][row] = 0;
			m[4][row] = 0;
			m[5][row] = 0;
			
			// Set point  
			m[6][row] = - screen[row] * board[row];
			m[7][row] = - screen[row] * board[nextRow];
			
			// Fill other values  
			m[0][nextRow] = 0;
			m[1][nextRow] = 0;
			m[2][nextRow] = 0;
			
			// Copy the board points  
			m[3][nextRow] = board[row];
			m[4][nextRow] = board[nextRow];
			
			// Fill other values
			m[5][nextRow] = 1;			

			// Set point  
			m[6][nextRow] = - screen[nextRow] * board[row];
			m[7][nextRow] = - screen[nextRow] * board[nextRow];
		}
		
		// Get matrix values  
		matrix = multiply(inverse(m),n);
	}

	/**
	 * Calculate the matrix inverse.
	 * 
	 * @param m The matrix
	 * @reult inverse matrix
	 */
	private float[][] inverse(float[][] m)
	{
		// Get determinant, cofactor 
		float determinant = determinant(m);
		float[][] cofactor = cofactor(m);
					
		//  Create result matrix
		float[][] result = new float[m.length][m.length];
		
		// Transponse 
		float[][] transponse = transpose(cofactor);
		
		// Calculate each value
		for(int i=0; i<m.length; i++)
			for(int j=0; j<m.length; j++)
				result[i][j] = transponse[i][j]/determinant;
				
		// Return the result 
		return result;
	}
	
	/**
	 * Matrix Determinant 
	 *
	 * @param m The matrix
	 * @return determinant 
	 */
	private float determinant(float[][] m)
	{
		// Init determinant value
		float determinant = 0;
		
		// Check min matrix 
		if(m.length == 2)
			return (m[0][0]*m[1][1]) - (m[0][1]*m[1][0]);

		// Factor 		
		float p;
		
		// Calculate value 
		for(int i=0; i<m.length; i++)
		{
			// Calculate cof
			float[][] cofactor = cofactor1(m,i,0);
			
			// Set factor 
			if(i%2 == 0)
				p = 1;
			else
				p = -1;
				
			// Calculate determinant
			determinant += m[i][0] * p * determinant(cofactor);
		}
		
		// Return result 
		return determinant;
	}  

	/**
	 * Matrix Cofactor  
	 *
	 * @param m The matrix
	 * @param i row 
	 * @param j column
	 *
	 * @return cofactor 
	 */
	private float[][] cofactor1(float[][] m, int i, int j)
	{
		// Create result matrix 
		float[][] result = new float[m.length-1][m.length-1];
		
		// Accumulators 
		int row = 0;
		int column = 0;
		
		// Calculate each value 
		for(int k=0; k<m.length; k++)
		{
			// If row is the same 
			if(k == i)
				continue;
				
			// Init row accumulator
			column = 0;
			
			// Copy values 
			for(int l=0; l<m.length; l++)
			{
				// If column is the same
				if(l == j)
					continue;
					
				// Copy matrix values 
				result[row][column] = m[k][l];
				column++;
			}
			
			// Update row 
			row++;
		}
		
		// Return result
		return result;	
	}

	/**
	 * Matrix Cofactor  
	 *
	 * @param m The matrix
	 * @return cofactor 
	 */
	private float[][] cofactor(float[][] m)
	{
		// Result matrix 
		float[][] result = new float[m.length][m.length];
		float p;
		
		// Calculate values 
		for(int i=0; i<result.length; i++)
			for(int j=0; j<result.length; j++)
			{
				// Calculate factor
				if((i+j) % 2 == 0)
					p = 1;
				else
					p = -1; 
					
				// Get cofactor 
				result[i][j] = p * determinant(cofactor1(m,i,j));
			}
			
		// Return the result 
		return result;
	}
	
	/**
	 * Matrix transpose  
	 *
	 * @param m The matrix
	 * @return transponse matrix
	 */
	private float[][] transpose(float[][] m)
	{
		// Create result 
		float[][] result = new float[m.length][m.length];
		
		// Set transponse values
		for(int i=0; i<m.length; i++)
			for(int j=0; j<m.length; j++)
				result[i][j] = m[i][j];

		// Return result 
		return result;
	}
	
	/**
	 * Multiply the two matrix
	 *
	 * @param m first matrix 
	 * @param n second matrix
	 *  
	 * @return result matrix
	 */
	private float[][] multiply(float[][] m, float[][] n)
	{
		// Create result matrix 
		float[][] result = new float[m.length][n[0].length];
		
		// Calculate values 
		for(int i=0; i<result.length; i++)
			for(int j=0; j<result[0].length; j++)
				for(int k = 0; k<n.length; k++)
					result[i][j] += m[i][k]*n[k][j];

		// Return result
		return result; 
	}
		
	/** 
	 * Return the screen point given the board point 
	 *
	 * @param boardX Board point coordinate X
	 * @param boardY Board point coordinate Y
	 *
	 * @return array with x,y values of screen point 
	 */
	public int[] screenPoint(int boardX, int boardY)
	{
		// Create result 
		int[] result = new int[2];
		
		// The denominator 
		float denominator = (matrix[6][0]*boardX + matrix[7][0]*boardY + 1);
		
		// Calculate X
		result[0] = (int) 
			((matrix[0][0]*boardX + matrix[1][0]*boardY + matrix[2][0]) 
			/ denominator);
			
		// Calculate Y
		result[1] = 
			(int)
			((matrix[3][0]*boardX + matrix[4][0]*boardY + matrix[5][0]) 
			/ denominator);
		
		// Return the result 
		return result;
	}
}