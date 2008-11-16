/*

	MM3G - 3D Graphics Library for Mobile Processing

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

import javax.microedition.m3g.*;

import processing.core.*;

/**
 * A 3D object described by his vertex, vertex colors, normals and 
 * triangle strip sequence and lengths.
 *
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */

public class MMesh extends M3dObject
{
	/**
	 * The num of components of all vertex for this shape.
	 */
	 
	private int vertexValuesIndex;

	/**
	 * The vertex components values.
	 */
	 	
	private byte[] vertexValues;

	/**
	 * The num of strip triangle index.
	 */

	private int indicesIndex;

	/**
	 * The indices for the triangle strip.
	 */
	 
	private int[] indicesValues;

	/**
	 * The current vertex color.
	 */

	private int vertexColor;

	/**
	 * The num of color components of all vertex for this shape.
	 */

	private int vertexColorsIndex;

	/**
	 * The vertex color values.
	 */

	private byte[] vertexColors;

	/**
	 * Current normal component X
	 *
	 * @since 0.2
	 */

	private byte normalX;

	/**
	 * Current normal component Y
	 *
	 * @since 0.2
	 */

	private byte normalY;

	/**
	 * Current normal component Z
	 *
	 * @since 0.2
	 */

	private byte normalZ;

	/**
	 * The num of textures components.
	 *
	 * @since 0.2
	 */

	private int texturesIndex;

	/**
	 * The texture values.
	 *
	 * @since 0.2
	 */

	private byte[] textureValues;

	/**
	 * The num of normals components.
	 *
	 * @since 0.2
	 */

	private int normalsIndex;

	/**
	 * The normals values.
	 *
	 * @since 0.2
	 */

	private byte[] normalValues;

	/**
	 * Number of strips
	 *
	 * @since 0.2
	 */

	private int numStrips;

	/**
	 * Strip triangle lengths
	 *
	 * @since 0.2
	 */

	private int[] stripLengths;

	/**
	 * The native M3G object to create this shape.
	 */
	 
	private Mesh peer;

	/**
	 * Creates and empty 3D shape.
	 */
	 
	public MMesh()
	{
		// Init all arrays for store a tipical 3D shape 
		// this arrays will grow when no space is available
		
		vertexValues = new byte[15];
		vertexColors = new byte[15];
		
		normalValues = new byte[15];
		textureValues = new byte[10];

		stripLengths = new int[2];
		indicesValues = new int[15];

		// Set color white like default vertex color
		vertexColor = 0xFFFFFF;

		// Set the default normal
		normalX = 0;
		normalY = 0;
		normalZ = 0;
	}

	/**
	 * Add a 3D vertex to the current shape with the current vertex color.
	 * 
	 * @param x coordinate x of the vertex
	 * @param y coordinate y of the vertex	 
	 * @param z coordinate z of the vertex	 	 
	 */
	
	public void vertex(int x, int y, int z)
	{
		vertex((byte) x, (byte) y, (byte) z);
	}

	/**
	 * Add a 3D vertex to the current shape with the current vertex color.
	 * 
	 * @param x coordinate x of the vertex
	 * @param y coordinate y of the vertex	 
	 * @param z coordinate z of the vertex	 	 
	 * @param u horizontal coordinate for the texture mapping
	 * @param v vertical coordinate for the texture mapping
	 *
	 * @since 0.2
	 */
	
	public void vertex(int x, int y, int z, int u, int v)
	{
		// Add the vertex
		vertex((byte) x, (byte) y, (byte) z);

		// Set the texture
		addTexture((byte) u, (byte) v);
	}

	/**
	 * Add a 3D vertex to the current shape with the current vertex color.
	 * 
	 * @param x coordinate x of the vertex
	 * @param y coordinate y of the vertex	 
	 * @param z coordinate z of the vertex	 	 
	 */
	
	public void vertex(byte x, byte y, byte z)
	{
		// Add the vertex to the array
		vertexValues[vertexValuesIndex++] = x;
		vertexValues[vertexValuesIndex++] = y;
		vertexValues[vertexValuesIndex++] = z;

		// Get the current length
		int length = vertexValues.length;

		// If array is full create a new one with 
		// double length and copy values
		if(vertexValuesIndex == length)
		{
			byte[] old = vertexValues;
			vertexValues = new byte[length * 2];
			System.arraycopy(old,0,vertexValues,0,length);
		}

		// Set the color to the vertex
		setVertexColor(vertexColor);

		// Set normal
		addNormal(normalX,normalY,normalZ);
	}

	/**
	 * Set current normal.
	 * 
	 * @param nx normal coordinate x
	 * @param ny normal coordinate y
	 * @param nz normal coordinate z
	 *
	 * @since 0.2
	 */
	
	public void normal(int nx, int ny, int nz)
	{
		normalX = (byte) nx;
		normalY = (byte) ny;
		normalZ = (byte) nz;
	}

	/**
	 * Add normal for vertex.
	 * 
	 * @param nx normal coordinate x
	 * @param ny normal coordinate y
	 * @param nz normal coordinate z
	 *
	 * @since 0.2
	 */
	
	private void addNormal(byte nx, byte ny, byte nz)
	{
		// Add the normal to the array
		normalValues[normalsIndex++] = nx;
		normalValues[normalsIndex++] = ny;
		normalValues[normalsIndex++] = nz;

		// Get the current length
		int length = normalValues.length;

		// If array is full create a new one with 
		// double length and copy values
		if(normalsIndex == length)
		{
			byte[] old = normalValues;
			normalValues = new byte[length * 2];
			System.arraycopy(old,0,normalValues,0,length);
		}
	}

	/**
	 * Add a strip triangle length to the shape.
	 *
	 * @param stripLength Length of the strip
	 *
	 * @since 0.2
	 */

	public void strip(int stripLength)
	{
		// Add the strip length to the array
		stripLengths[numStrips++] = stripLength;

		// Get the current length
		int length = stripLengths.length;

		// If array is full create a new one with 
		// double length and copy values
		if(numStrips == length)
		{
			int[] old = stripLengths;
			stripLengths = new int[length * 2];
			System.arraycopy(old,0,stripLengths,0,length);
		}
	}

	/**
	 * Set the color components to the vertex.
	 *
	 * @param color color asigned to last vertex added.
	 */

	private void setVertexColor(int color)
	{
		// Get R,G,B components 
		vertexColors[vertexColorsIndex++] = (byte) ((color & 0xFF0000) >> 16);
		vertexColors[vertexColorsIndex++] = (byte) ((color & 0x00FF00) >> 8);
		vertexColors[vertexColorsIndex++] = (byte) (color & 0x0000FF);

		// Get the current length
		int length = vertexColors.length;

		// If array is full create a new one with 
		// double length and copy values
		if(vertexColorsIndex == length)
		{
			byte[] old = vertexColors;
			vertexColors = new byte[length * 2];
			System.arraycopy(old,0,vertexColors,0,length);
		}
	}

	/**
	 * Add a index strip triangle if is requiered
	 *
	 * @param indexVertex triangle vertex used to create the strip.
	 */

	public void index(int indexVertex)
	{
		// Add the index of the vertex
		indicesValues[indicesIndex++] = indexVertex;

		// Get the current length
		int length = indicesValues.length;

		// If array is full create a new one with 
		// double length and copy values
		if(indicesIndex == length)
		{
			int[] old = indicesValues;
			indicesValues = new int[length * 2];
			System.arraycopy(old,0,indicesValues,0,length);
		}
	}

	/**
	 * Set the color to assign to the next vertex.
	 *
	 * @param color color to assign to the next vertex.
	 */

	public void fill(int color)
	{
		this.vertexColor = color;
	}

	/**
	 * Add a texture for the vertex.
	 * 
	 * @param u horizontal texture
	 * @param v vertical texture
	 *
	 * @since 0.2
	 */
	
	private void addTexture(byte u, byte v)
	{
		// Add the texture to the array
		textureValues[texturesIndex++] = u;
		textureValues[texturesIndex++] = v;

		// Get the current length
		int length = textureValues.length;

		// If array is full create a new one with 
		// double length and copy values
		if(texturesIndex == length)
		{
			byte[] old = textureValues;
			textureValues = new byte[length * 2];
			System.arraycopy(old,0,textureValues,0,length);
		}
	}

	/**
	 * Returns the native representation for the shape.
	 *
	 * @return Native representation for the shape
	 */

	protected Mesh getPeer()
	{
		// if peer has been created, return that
		if(peer != null)
			return peer;

		// Create the buffer to store shape information
		VertexBuffer vb = new VertexBuffer();
		
		setPositions(vb);
		setNormals(vb);
		setColors(vb);

		// Set the textures
		if(texturesIndex > 0)
			setTextures(vb);

		// Set the triangle strips for this shape
		TriangleStripArray tsa = getTriangleStrips();

		Appearance appearance = getAppearance();

		// Create the native peer
		peer = new Mesh(vb,tsa,appearance);

		// Free the vertex, vertex color, indices spaces
		vertexValues = null;
		vertexColors = null;
		indicesValues = null;
		normalValues = null;
		stripLengths = null;
		textureValues = null;

		// Set the 3D Object
		setObject3D(peer);

		// Return the native peer
		return peer;
	}

	/**
	 * Set the appearance for this shape.
	 *
	 * This method is use by getPeer to set appearance to the shape
	 *
	 * @param vertexBuffer Shape information
	 * @return appearance of the shape
	 *
	 * @since 0.2
	 */

	private Appearance getAppearance()
	{
		// Create PolygonMode
		PolygonMode polygonMode = new PolygonMode();
		polygonMode.setCulling(PolygonMode.CULL_NONE);

		// Create Material
		Material material = new Material();
		material.setVertexColorTrackingEnable(true);

		// Set Appearance
		Appearance appearance = new Appearance(); 
		appearance.setPolygonMode(polygonMode);
		appearance.setMaterial(material);

		// Set Alpha Color 
		CompositingMode compositingMode = new CompositingMode();
		compositingMode.setBlending(CompositingMode.ALPHA);
		appearance.setCompositingMode(compositingMode);

		return appearance;
	}

	/**
	 * Add the positions array to the vertex buffer specified.
	 *
	 * This method is use by getPeer to set positions to the shape
	 *
	 * @param vertexBuffer Shape information
	 *
	 * @since 0.2
	 */

	private void setPositions(VertexBuffer vertexBuffer)
	{
		// Create the vertex position information using current vertexValues
		VertexArray vp = new VertexArray(vertexValuesIndex/3,3,1);
		vp.set(0,vertexValuesIndex/3,vertexValues);

		vertexBuffer.setPositions(vp,1.0f,null);
	}

	/**
	 * Add the normals array to the vertex buffer specified.
	 *
	 * This method is use by getPeer to set normals to the shape
	 *
	 * @param vertexBuffer Shape information
	 *
	 * @since 0.2
	 */

	private void setNormals(VertexBuffer vertexBuffer)
	{
		// Create the normals information using current normals
		VertexArray vn = new VertexArray(normalsIndex/3,3,1);
		vn.set(0,normalsIndex/3,normalValues);

		vertexBuffer.setNormals(vn);
	}

	/**
	 * Add the colors array to the vertex buffer specified.
	 *
	 * This method is use by getPeer to set textures to the shape
	 *
	 * @param vertexBuffer Shape information
	 *
	 * @since 0.2
	 */

	private void setColors(VertexBuffer vertexBuffer)
	{
		// Create the vertex color information using current vertexValues
		VertexArray vc = new VertexArray(vertexColorsIndex/3,3,1);
		vc.set(0,vertexColorsIndex/3,vertexColors);

		vertexBuffer.setColors(vc);
	}

	/**
	 * Calculate the triangle strips for this shape.
	 *
	 * This method is use by getPeer to set textures to the shape
	 *
	 * @param vertexBuffer Shape information
	 * @return the triangle strips info
	 *
	 * @since 0.2
	 */

	private TriangleStripArray getTriangleStrips()
	{
		// Create the triangle strip information 
		TriangleStripArray tsa;

		// If any index was specified used that array
		if(indicesIndex != 0)
		{
			// If no strips lenghts was specified, make a strip with
			// all vertex
			
			if(numStrips == 0)
				strip(indicesIndex);

			// Copy the real stripLengths 
			int[] lengths = new int[numStrips];
			System.arraycopy(stripLengths,0,lengths,0,numStrips);

			// Create the current strip array
			tsa = new TriangleStripArray(indicesValues,lengths);
		}
		else
			// Create the default triangle strip with only one strip with all the points
			tsa = new TriangleStripArray(0,new int[] { vertexValuesIndex/3 });

		return tsa;
	}

	/**
	 * Add the texture array to the vertex buffer specified.
	 *
	 * This method is use by getPeer to set textures to the shape
	 *
	 * @param vertexBuffer Shape information
	 *
	 * @since 0.2
	 */

	private void setTextures(VertexBuffer vertexBuffer)
	{
		// Create the vertex color information using current vertexValues
		VertexArray vt = new VertexArray(texturesIndex/2,2,1);
		vt.set(0,texturesIndex/2,textureValues);

		vertexBuffer.setTexCoords(0,vt,1.0f,null);
	}

	/**
	 * Set the texture for this shape.
	 *
	 * @param texture2D the texture to apply.
	 *
	 * @since 0.2
	 */

	public void texture(Texture2D texture2D)
	{
		if(peer == null)
			getPeer();

		Appearance appearance = peer.getAppearance(0);
		appearance.setTexture(0,texture2D);
	}

	/**
	 * Translate the shape the amount specified for each axis.
	 * 
	 * @param x translation along the X axis
	 * @param x translation along the Y axis
	 * @param z translation along the Z axis
	 */

	// This method is include for compatility with previous releases

	public void translate(int x, int y, int z)
	{
		// if peer is not created
		if(peer == null)
			getPeer();

		super.translate(x,y,z);
	}
}
