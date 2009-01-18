/*

	M3d - 3D Graphic Library for Mobile Processing

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

import java.util.*;

import javax.microedition.lcdui.*;
import javax.microedition.m3g.*;

import processing.core.*;

/**
 * 3D Drawing.
 *
 * This class override the drawing primitives of Mobile Processing,
 * is based on Mobile 3D Graphics API (M3G) for J2ME.
 * 
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */

public class M3d extends PCanvas
{
	/**
	 * Processing MIDlet. 
	 */
	 
	private PMIDlet pMIDlet;

	/**
	 * The scene graph where shapes will be added.
	 */ 
	 
	private World world;

	/**
	 * The 3D graphics context where the world will be render.
	 */
	
	private Graphics3D g3d;

	/**
	 * Current 3D objects.
	 */

	private Vector meshes;
	
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
	 * Current normal component X
	 *
	 * @since 0.2
	 */

	private int normalX;
	
	/**
	 * Current normal component Y
	 *
	 * @since 0.2
	 */

	private int normalY;

	/**
	 * Current normal component Z
	 *
	 * @since 0.2
	 */

	private int normalZ;

	/**
	 * Background color.
	 *
	 * This color is used to fill the background of the a text to draw,
	 * an optimal solution is set a transparent color to the image.
	 * 
	 * @since 0.2
	 */
	  
	private int backgroundColor;

	/**
	 * Texture to apply to vertex
	 *
	 * @since 0.2
	 */

	private Texture2D currentTexture;

	/**
	 * Active Camera 
	 *
	 * @since 0.2
	 */

	private MCamera activeCamera;

	/**
	 * PI Constant 
	 * 
	 * This constant contains the float value of PI, the M3G api use the cldc 1.1 
	 * that include float and double types
	 *
	 * @since 0.3
	 */

	public static final float PI = (float) Math.PI;

	/**
	 * Current Mesh created using beginShape
	 */

	private MMesh currentMesh;

	/**
	 * Ambient Light
	 *
	 * @since 0.4
	 */

	private MLight ambientLight;

	/**
	 * Rendering Hints
	 *
	 * @since 0.4
	 */

	private int renderingHints;	
	
	/**
	 * Create the drawing space for the 3D objects.
	 *
	 * @param pMIDlet The midlet
	 */

	public M3d(final PMIDlet pMIDlet)
	{
		super(pMIDlet);

		// Set the midlet reference
		this.pMIDlet = pMIDlet;

		// Set listeners
		pMIDlet.canvas = this;
		addCommand(pMIDlet.cmdExit);
		setCommandListener(pMIDlet);

		// Init the scene graph
		world = new World();

		// The 3D Graphics context where world will be render
		g3d = Graphics3D.getInstance();

		// The objects on scene
		meshes = new Vector();

		// Create the default camera
		activeCamera = new MCamera(new Camera());
		
		// Set the camera defaults and make perspective 
		camera();
		perspective();

		// Add the camera to the scene and make active 
		add(activeCamera);
		world.setActiveCamera((Camera) activeCamera.getObject3D());

		// Set the background color of the scene to gray
		// The PCanvas make this call but the world was no created
		background(200);

		// Set the ambientLight
		ambientLight(255,255,255);

		// Set renderinghits to max
		renderingHints = Graphics3D.ANTIALIAS | Graphics3D.TRUE_COLOR | Graphics3D.DITHER;

		// Display the m3d context
		Display.getDisplay(pMIDlet).setCurrent(this);
	}

	/**
	 * Returns the 3D canvas like PCanvas
	 * 
	 * @return The 3D Canvas
	 */

	public PCanvas canvas()
	{
		return this;
	}

	/**
	 * Draw the 3D secene.
	 * 
	 * @param g The graphics context
	 */

	public void paint(Graphics g)
	{
		// Set the target, clear the screen, 
		// render the scene graph, and release the target

		g3d.bindTarget(g,true,renderingHints);
		g3d.clear(null);
		g3d.render(world);
		g3d.releaseTarget();
	}

	/**
	 * Removes all the objects added to the scene.
	 */

	private void removeMeshes()
	{
		// Get the current 3D objects and remove it from the scene
		for(Enumeration e = meshes.elements(); e.hasMoreElements(); )
		{
			M3dObject m3dObject = (M3dObject) e.nextElement();

			// Don't remove ambient light
			if(m3dObject == ambientLight)
				continue;

			// Don't remove Active Camera
			if(m3dObject == activeCamera)
				continue;

			// Remove the object
			remove(m3dObject);
		}
	}

	/**
	 * Adds a 3d Object to the scene.
	 *
	 * @param mesh A 3D object to add to the scene
	 *
	 * @since 0.2
	 */

	public void add(M3dObject m3dObject)
	{
		// If the Object is a MMesh create the native 3D Object
		if(m3dObject instanceof MMesh)
		{
			MMesh mesh = (MMesh) m3dObject;
			mesh.getPeer();
		}

		// Add the 3d object
		add(m3dObject,true);
	}

	/**
	 * Removes the object from the scene
	 *
	 * @param m3dObject A 3D object to remove from the scene
	 *
	 * @since 0.5
	 */
	public void remove(M3dObject m3dObject)
	{
		// Remove native object from the world
		world.removeChild(m3dObject.getObject3D());

		// Remove the object from the current meshes
		meshes.removeElement(m3dObject);
	}

	/**
	 * Adds a Node to the scene.
	 *
	 * @param mesh A 3D object to add to the scene
	 * @param rotate If true rotate the node else add only
	 *
	 * @since 0.3
	 */

	private void add(M3dObject m3dObject, boolean rotate)
	{
		// If the current escene already have the object return
		if(meshes.contains(m3dObject))
			return;

		// Native 3D Object
		Node nodeObject = m3dObject.getObject3D();
	
		Transformable trans = (Transformable) nodeObject;
		
		// Translate the object to the location
		m3dObject.translate(translateX,-translateY,translateZ);

		// Rotate the object on X,Y and Z
		if(rotate)
		{
			trans.postRotate((float) Math.toDegrees(rotateX),1.0f,0.0f,0.0f);
			trans.postRotate((float) Math.toDegrees(rotateY),0.0f,1.0f,0.0f);
			trans.postRotate((float) Math.toDegrees(rotateZ),0.0f,0.0f,1.0f);
		}

		// Set the M3d Systems where object belogns
		m3dObject.setM3d(this);

		// Add to the scene	
		world.addChild(nodeObject);

		// Add to the 3D object vector
		meshes.addElement(m3dObject);
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
		// If the world is not available jet, do nothing
		if(world == null)
			return;

		// Clear the scene
		removeMeshes();

		// Create a background object and set to the world
		Background background = new Background();

		// If value is an Integer set like color else
		// the value is a image

		if(value instanceof Integer)
		{
			backgroundColor = ((Integer) value).intValue();
			background.setColor(backgroundColor);
		}
		else
			background.setImage((Image2D) value);

		// Put the background into the scene
		world.setBackground(background);
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
		// Create a special image for used like backgroun
		Image2D image2D = new Image2D(Image2D.RGBA,img.image);
		setBackground(image2D);
	}

	/**
	 * Set the translation.
	 *
	 * @param x left/right translation
	 * @param y up/down translation
	 */

	public void translate(int x, int y)
	{
		// Use 3D translation
		translate(x,y,0);
	}

	/**
	 * Set the translation.
	 *
	 * @param x left/right translation
	 * @param y up/down translation
	 * @param z forward/back translation	 
	 */

	public void translate(int x, int y, int z)
	{
		translateX += x;
		translateY += y;
		translateZ += z;		
	}

	/**
	 * Update the rotation around the X axis.
	 *
	 * @param radians angle in radians
	 */

	public void rotateX(float radians)
	{
		rotateX += radians;
	}

	/**
	 * Update the rotation around the Y axis.
	 *
	 * @param radians angle in radians
	 */

	public void rotateY(float radians)
	{
		rotateY += radians;
	}

	/**
	 * Update the rotation around the Z axis.
	 *
	 * @param radians angle in radians
	 */

	public void rotateZ(float radians)
	{
		rotateZ += radians;
	}

	/**
	 * Begins recording vertex, vertex colors for a complex form.
	 *
	 * @param mode The vertex mode.
	 */

	public void beginShape(int mode)
	{
		// Only TRIANGLE_STRIP mode is available
		
		if(mode != PMIDlet.TRIANGLE_STRIP)
			throw new IllegalArgumentException("Only TRIANGLE_STRIP mode is available");

		// Create a empty 3D object to store vertex and vertex colors	
		currentMesh = new MMesh();
	}

	/**
	 * A object with the vertex, vertex colors added is created and added to the scene.
	 */

	public void endShape()
	{
		// Set the texture
		if(currentTexture != null)
			currentMesh.texture(currentTexture);
		
		// Add to the scene
		add(currentMesh);

		// Clear the current shape and Texture
		currentMesh = null;
		currentTexture = null;
	}

	/**
	 * Add a 2D vertex to the current shape.
	 * 
	 * @param x coordinate x of the vertex
	 * @param y coordinate y of the vertex	 
	 */
	 
	public void vertex(int x, int y)
	{
		// Use 3D vertex
		vertex(x,y,0);
	}

	/**
	 * Add a 3D vertex to the current shape.
	 * 
	 * @param x coordinate x of the vertex
	 * @param y coordinate y of the vertex	 
	 * @param z coordinate z of the vertex	 	 
	 */
	
	public void vertex(int x, int y, int z)
	{
		// If a shape is being recording
		if(currentMesh != null)
		{
			// Fill the vertex with the current color and add the vertex to the 3D object
			currentMesh.fill(fillColor);
			currentMesh.normal(normalX,normalY,normalZ);
			currentMesh.vertex(x,y,z);
		}
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
		// If a shape is being recording
		if(currentMesh != null)
		{
			// Fill the vertex with the current color and add the vertex to the 3D object
			currentMesh.fill(fillColor);
			currentMesh.normal(normalX,normalY,normalZ);
			currentMesh.vertex(x,y,z,u,v);
		}
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
	 * @since 0.2
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
		}

		return new int[] { x, y, width, height };
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

		// Set the size to the half of the rectangle
		width /= 2;
		height /= 2;

		// Translate to x and y coordinate, the y coordinate is positive

		translate(x + width,y + height);

		// Create a shape with to triangles
		// Set the center of the rectangle in 0,0
		
		beginShape(PMIDlet.TRIANGLE_STRIP);

		vertex(width,height);
		vertex(-width,height);
		vertex(width,-height);
		vertex(-width,-height);

		endShape();

		// Restore translation values
		translate(-(x + width),- (y + height));
	}

	/**
	 * Creates a 3D Cube with the size specified.
	 * 
	 * Check box(width,height,depth) implementation
	 *
	 * @param size side length of the cube
	 *
	 * @return The mesh result of the box construction
	 *
	 * @see box(int width, int height, int depth)
	 */

	public MMesh box(int size)
	{
		return box(size,size,size);
	}

	/**
	 * Sets the current normal vector.
	 * @param nx normal coordinate x
	 * @param ny normal coordinate y
	 * @param nz normal coordinate z
	 *
	 * @since 0.2
	 */

	public void normal(int nx, int ny, int nz)
	{
		normalX = nx;
		normalY = ny;
		normalZ = nz;
	}

	/**
	 * Adds an ambient light. 
	 *
	 * Ambient light doesn't come from a specific direction, the rays have light have bounced around 
	 * so much that objects are evenly lit from all sides. Ambient lights are almost always used in 
	 * combination with other types of lights. Lights need to be included in the draw() to remain 
	 * persistent in a looping program. Placing them in the setup() of a looping program will cause 
	 * them to only have an effect the first time through the loop. The effect of the parameters is 
	 * determined by the current color mode.
	 * 
	 * @param v1 red or hue value
	 * @param v2 green or hue value
	 * @param v3 red or hue value
	 * @param x x-coordinate of the light
	 * @param y y-coordinate of the light
	 * @param z z-coordinate of the light
	 *
	 * @since 0.2
	 */

	public void ambientLight(int v1, int v2, int v3, int x, int y, int z)
	{
		// Create a ambient light 
		if(ambientLight == null)
			ambientLight = new MLight(MLight.AMBIENT);

		// Set the color specified, calculating the true color
		ambientLight.color(color(v1,v2,v3));

		// Set the intensity to 1
		ambientLight.intensity(1.0f);
		
		// Translate to the location specified
		ambientLight.location(x,-y,z);

		// Add the light to the scene, don't use rotation
		add(ambientLight,false);
	}

	/**
	 * Adds an ambient light. 
	 *
	 * Ambient light doesn't come from a specific direction, the rays have light have bounced around 
	 * so much that objects are evenly lit from all sides. Ambient lights are almost always used in 
	 * combination with other types of lights. Lights need to be included in the draw() to remain 
	 * persistent in a looping program. Placing them in the setup() of a looping program will cause 
	 * them to only have an effect the first time through the loop. The effect of the parameters is 
	 * determined by the current color mode.
	 * 
	 * @param v1 red or hue value
	 * @param v2 green or hue value
	 * @param v1 red or hue value
	 *
	 * @since 0.2
	 */

	public void ambientLight(int v1, int v2, int v3)
	{
		ambientLight(v1,v2,v3,0,0,0);
	}

	/**
	 * Adds a directional light. 
	 * 
	 * Directional light comes from one direction and is stronger when hitting a surface squarely 
	 * and weaker if it hits at a a gentle angle. After hitting a surface, a directional lights 
	 * scatters in all directions. Lights need to be included in the draw() to remain persistent in 
	 * a looping program. Placing them in the setup() of a looping program will cause them to only 
	 * have an effect the first time through the loop. The affect of the v1, v2, and v3 parameters 
	 * is determined by the current color mode. The nx, ny, and nz parameters specify the direction 
	 * the light is facing. For example, setting ny to -1 will cause the geometry to be lit from 
	 * below (the light is facing directly upward).
	 * 
	 * @param v1 red or hue value
	 * @param v2 green or hue value
	 * @param v3 red or hue value
	 * @param nx direction along the x axis
	 * @param ny direction along the y axis
	 * @param nz direction along the z axis
	 *
	 * @since 0.2
	 */

	public void directionalLight(int v1, int v2, int v3, int nx, int ny, int nz)
	{
		// Remove the default ambient light
		removeAmbientLight();
		
		// Create a directional light 
		Light light = new Light();
		light.setMode(Light.DIRECTIONAL);

		// Set the color specified, calculating the true color
		light.setColor(color(v1,v2,v3));

		// Set the intensity to 1
		light.setIntensity(1.0f);
		
		// Rotate the light to face the direction

		if(nx == 1)
			light.postRotate(90f,0.0f,1.0f,0.0f);
		else if(nx == -1)
			light.postRotate(270f,0.0f,1.0f,0.0f);
		else if(ny == 1)
			light.postRotate(270f,1.0f,0.0f,0.0f);
		else if(ny == -1)
			light.postRotate(90f,1.0f,0.0f,0.0f);
		else if(nz == -1)
			light.postRotate(180f,0.0f,1.0f,0.0f);

		// Add the light to the scene, don't use rotation
		add(new MLight(light),false);
	}

	/**
	 * Adds a spot light.
	 * 
	 * Lights need to be included in the draw() to remain persistent in a looping program. Placing them 
	 * in the setup() of a looping program will cause them to only have an effect the first time through
	 * the loop. The affect of the v1, v2, and v3 parameters is determined by the current color mode.
	 * The x, y, and z parameters specify the position of the light and nx, ny, nz specify the direction 
	 * or light. The angle parameter affects angle of the spotlight cone.
	 * 
	 * @param v1 red or hue value
	 * @param v2 green or hue value
	 * @param v3 red or hue value
	 * @param x x-coordinate of the light
	 * @param y y-coordinate of the light
	 * @param z z-coordinate of the light
	 * @param nx direction along the x axis
	 * @param ny direction along the y axis
	 * @param nz direction along the z axis
	 * @param radians angle of the spotlight cone
	 * @param exponent determining the center bias of the cone
	 *
	 * @since 0.2
	 */

	public void spotLight(int v1, int v2, int v3, int x, int y, int z, 
									int nx, int ny, int nz, float radians, float concentration)
	{
		// Remove the default ambient light
		removeAmbientLight();
	
		// Create a directional light 
		Light light = new Light();
		light.setMode(Light.SPOT);

		// Set the color specified, calculating the true color
		light.setColor(color(v1,v2,v3));

		// Set the intensity to 1
		light.setIntensity(1.0f);

		// Rotate the light to face the direction

		if(nx == 1)
			light.postRotate(90f,0.0f,1.0f,0.0f);
		else if(nx == -1)
			light.postRotate(270f,0.0f,1.0f,0.0f);
		else if(ny == 1)
			light.postRotate(270f,1.0f,0.0f,0.0f);
		else if(ny == -1)
			light.postRotate(90f,1.0f,0.0f,0.0f);
		else if(nz == -1)
			light.postRotate(180f,0.0f,1.0f,0.0f);

		// Translate to the location specified
		light.translate(x,-y,z);
		
		// Set spot angle
		light.setSpotAngle((float) Math.toDegrees(radians));

		// Set concetration
		light.setSpotExponent((concentration * 128)/1000.0f);

		// Add the light to the scene, don't use rotation
		add(new MLight(light),false);
	}

	/**
	 * Adds an omnidirectional light. 
	 *
	 * Note : Not available in Processing
	 * 
	 * @param v1 red or hue value
	 * @param v2 green or hue value
	 * @param v3 red or hue value
	 * @param x x-coordinate of the light
	 * @param y y-coordinate of the light
	 * @param z z-coordinate of the light
	 *
	 * @since 0.2
	 */

	public void omniLight(int v1, int v2, int v3, int x, int y, int z)
	{
		// Remove the default ambient light
		removeAmbientLight();
	
		// Create a ambient light 
		Light light = new Light();
		light.setMode(Light.OMNI);

		// Set the color specified, calculating the true color
		light.setColor(color(v1,v2,v3));

		// Set the intensity to 1
		light.setIntensity(1.0f);
		
		// Translate to the location specified
		light.translate(x,-y,z);

		// Add the light to the scene, don't use rotation
		add(new MLight(light),false);
	}

	/**
	 * Draw a image like a 3D object
	 * 
	 * @param pImage Image to draw.
	 * @param x x-coordinate of the image
	 * @param y y-coordinate of the image
	 * @param z z-coordinate of the image
	 * @param width width of the image
	 * @param height height of the image
	 *
	 * @since 0.2
	 */

	public void image(PImage pImage, int x, int y, int z, int width, int height)
	{
		// Remove the default ambient light
		removeAmbientLight();
	
		// Calculte coords and size according with the mode
		int[] coords = calculateCoords(imageMode,x,y,width,height);

		x = coords[0];
		y = coords[1];
		width = coords[2];
		height = coords[3];
		
		width /= 2;
		height /= 2;
		
		// Translate to x and y coordinate, the y coordinate is positive
		translate(x + width,y + height);
		
		// Create a simple rect with a texture
		beginShape(PMIDlet.TRIANGLE_STRIP);
		texture(pImage);
		vertex(-width,-height,0,0,1);
		vertex(width,-height,0,1,1);
		vertex(-width,height,0,0,0);
		vertex(width,height,0,1,0);  
		endShape(); 
		
		// Restore translation values
		translate(-(x + width),- (y + height));		
	}

	/**
	 * Draw a image like a 3D object
	 * 
	 * @param pImage Image to draw.
	 * @param x x-coordinate of the image
	 * @param y y-coordinate of the image
	 * @param z z-coordinate of the image	 
	 *
	 * @since 0.2
	 */

	public void image(PImage pImage, int x, int y, int z)
	{
		// Grab the image dimesions
		int width = pImage.width;
		int height = pImage.height;

		// if image mode is corners, calculate the real corner
		if(imageMode == PMIDlet.CORNERS)
		{
			width -= x;
			height -= y;
		}

		// Draw the image with this coordinates
		image(pImage,x,y,z,width,height);
	}	

	/**
	 * Draw a image like a 3D object
	 * 
	 * @param pImage Image to draw.
	 * @param x x-coordinate of the image
	 * @param y y-coordinate of the image
	 *
	 * @since 0.2
	 */

	public void image(PImage pImage, int x, int y)
	{
		image(pImage,x,y,0);
	}	

	/**
	 * Draw a image like a 3D object
	 * 
	 * @param pImage Image to draw.
	 * @param x x-coordinate of the image
	 * @param y y-coordinate of the image
	 * @param width width of the image
	 * @param height height of the image
	 *
	 * @since 0.2
	 */

	public void image(PImage pImage, int x, int y, int width, int height)
	{
		// Draw the image with this coordinates
		image(pImage,x,y,0,width,height);
	}	
	
	/**
	 * Set the current image mode.
	 *
	 * @param mode The mode to draw images
	 *
	 * @since 0.2
	 */ 
	 
	public void imageMode(int mode)
	{
		if((mode >= PMIDlet.CENTER) && (mode <= PMIDlet.CORNERS))
			imageMode = mode;
	}	

	/**
	 * Return a image with the display window.
	 *
	 * @return a image with the content of the screen.
	 *
	 * @since 0.2
	 */

	public PImage save()
	{
		// Create a image with the screen dimension
		Image screenshot = Image.createImage(width,height);

		// Get the graphics and paint the content over it 
		Graphics graphics = screenshot.getGraphics();
		paint(graphics);

		// Create a PImage with the native image
		return new PImage(screenshot);
	}

	/**
	 * Draws text to the screen.
	 *
	 * @param data the string to draw
	 * @param x x-coordinate of text
	 * @param y y-coordinate of text
	 * @param z z-coordinate of text
	 *
	 * @since 0.2
	 */

	public void text(String data, int x, int y, int z)	
	{
		// If not Font is set throws and exception
		if(textFont == null)
            throw new RuntimeException("The current font has not yet been set with textFont()");
            
		// The image containing the text
		Image textImage = null;

		// Image dimensions
		int width = 0;
		int height = 0;

		// Get the system font
		Font font = textFont.font;
		
		// If is a system font
		if(font != null)
		{
			// Get the string draw dimensions acoording with the font
			height = font.getHeight();
			width = textWidth(data);
			
			// Create a mini image for the text
			if(width == 0)
				width = 10;
			
			// Create a empty image with the dimensions calculated
			textImage = Image.createImage(width,height);

			// Draw the text inside the imane
			Graphics imageGraphics = textImage.getGraphics();

			// Fill the background with the opposite color of the text
			imageGraphics.setColor(backgroundColor);
			imageGraphics.fillRect(0,0,width,height);

			// Draw the text
			imageGraphics.setColor(fillColor);
			imageGraphics.drawString(data,0,0,Graphics.TOP | Graphics.LEFT);
		}
		else
		{
			// Each char of the text
			char c;

			// Index of the image from the font
			int index;

			// Calculate image dimensions
			height = textFont.topExtent[0] + textFont.images[0].height;
			width = textWidth(data);
			
			// Create a mini image for the text
			if(width == 0)
				width = 10;			

			// Create a empty image with the dimensions calculated
			textImage = Image.createImage(width,height);

			Graphics imageGraphics = textImage.getGraphics();			

			// Fill the background with the opposite color of the text
			imageGraphics.setColor(backgroundColor);
			imageGraphics.fillRect(0,0,width,height);

			// Set initial positions
			int textX = 0;
			int textY = height - textFont.topExtent[0];

			int length = data.length();

			// Draw each character
			for(int i=0; i<length; i++)
			{
				// Get char and index
				c = data.charAt(i);
				index = textFont.getIndex(c);

				// Draw char or make a space of the width of the character 'i'
				if(index >= 0)
					imageGraphics.drawImage(textFont.images[index].image,textX + textFont.leftExtent[index],textY - textFont.topExtent[index],Graphics.TOP | Graphics.LEFT);
				else
					index = textFont.getIndex('i');

				// Update x position
				textX += textFont.setWidth[index];
			}
		}

		// Problem : Transparent Image is not working !!!!

		// Create a transparent Image and draw the text

		// Create an image with the same size of the text
		int[] imageData = new int[width*height];

		// Get the RGB info 
		textImage.getRGB(imageData,0,width,0,0,width,height);

		// Change the color of any pixel different of fill color to transparent
		for(int i=0; i<width*height; i++)
			if(imageData[i] == backgroundColor)
				imageData[i] = 0x00000000;

		// Create the image with transparent background
		textImage = Image.createRGBImage(imageData,width,height,true);

		// Calculate the real coord for align

		if(textAlign == PMIDlet.CENTER)
		{
			x -= width/2;
			y -= height/2;
		}
		else if(textAlign == PMIDlet.RIGHT)
			x -= width;
			
		
		// Draw the image
		sprite(new PImage(textImage),x,y,z);
	}

	/**
	 * Draws text to the screen.
	 *
	 * @param data the string to draw
	 * @param x x-coordinate of text
	 * @param y y-coordinate of text
	 *
	 * @since 0.2
	 */

	public void text(String data, int x, int y)
	{
		text(data,x,y,0);
	}	

	/**
	 * Calculates and the width of a text string.
	 *
	 * @param text the text
	 * @return width of the text
	 *
	 * @since 0.2
	 */

	public int textWidth(String text)
	{
		// Neccesary until the textWidth bug is solved, return the width calculated 
		// acoording system font
		if(textFont.font != null)
			return textFont.font.stringWidth(text);

		// Return the width according the font loaded
		return super.textWidth(text);
	}

	/**
	 * Calculates the width of a character.
	 *
	 * @param char a single character
	 * @return width of the character
	 *
	 * @since 0.2	 
	 */

	public int textWidth(char c)
	{
		return textWidth("" + c);
	}

	/**
	 * Sets a texture to be applied to vertex points.	
	 *
	 * @param pImage the texture to apply
	 *
	 * @since 0.2
	 */

	public void texture(PImage pImage)
	{
		// Create a image2D and a texture2D 
		Image2D image2D = new Image2D(Image2D.RGBA,pImage.image);
		currentTexture = new Texture2D(image2D);
		currentTexture.setBlending(Texture2D.FUNC_REPLACE);
	}

	/**
	 * Sets a perspective projection applying foreshortening, making distant objects 
	 * appear smaller than closer ones.
	 *
	 * @param fov field-of-view angle (in radians) for vertical direction
	 * @param aspect ratio of width to height
	 * @param zNear z-position of nearest clipping plane
	 * @param zFar z-position of nearest farthest plane
	 *
	 * @since 0.2
	 */

	public void perspective(float fov, float aspect, float zNear, float zFar)
	{
		activeCamera.perspective(fov,aspect,zNear,zFar);
	}

	/**
	 * Sets a perspective projection applying foreshortening, making distant objects 
	 * appear smaller than closer ones.
	 *
	 * @since 0.2
	 */

	public void perspective()
	{
		// Calculate perspective values according with the size of the screen
		float cameraZ = (height/2.0f) / (float) (Math.tan(Math.PI*60.0f/360.0f));
		float fov = 60;
		float aspect = (float) width / (float) height;
		float zNear = cameraZ/10.0f;
		float zFar = cameraZ*10.0f;

		// Set the perspective
		perspective(fov,aspect,zNear,zFar);
	}

	/**
	 * Sets the position of the camera through setting the eye position,
	 * the center of the scene, and which axis is facing upward.
	 *
	 * @param eyeX x coordinate for the eye
	 * @param eyeY y coordinate for the eye
	 * @param eyeZ z coordinate for the eye
	 * @param centerX x coordinate for the center of the scene
	 * @param centerY y coordinate for the center of the scene
	 * @param centerZ z coordinate for the center of the scene
	 * @param upX usually 0.0, 1.0, or -1.0
	 * @param upY usually 0.0, 1.0, or -1.0
	 * @param upZ usually 0.0, 1.0, or -1.0
	 *
	 * @since 0.2
	 */

	// ToDo :
	//
	// This is a prelimary implementation of the camera method 
	// Check processing to see a correct implementation

	public void camera(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ,
								float upX, float upY, float upZ)
	{
		// Move the camera to the specified position
		activeCamera.translate(eyeX,eyeY,eyeZ);

		// Rotate the active camera to face the direction

		if(upX == 1.0f)
			activeCamera.orientation(90f,0.0f,1.0f,0.0f);
		else if(upX == -1.0f)
			activeCamera.orientation(270f,0.0f,1.0f,0.0f);
		else if(upY == 1.0f)
			activeCamera.orientation(270f,1.0f,0.0f,0.0f);
		else if(upY == -1.0f)
			activeCamera.orientation(90f,1.0f,0.0f,0.0f);
		else if(upZ == -1.0f)
			activeCamera.orientation(180f,0.0f,1.0f,0.0f);
		else
			activeCamera.orientation(0f,0.0f,1.0f,0.0f);
	}

	/**
	 * Sets the position of the camera through setting the eye position,
	 * the center of the scene, and which axis is facing upward.
	 *
	 * @see camera(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ)
	 *
	 * @since 0.2
	 */

	// ToDo : 
	// 
	// This method must use the following call
	// camera(width/2,height/2,cameraZ,width/2.0f,height/2.0f,0,0,1,0);
	// See camera method
	 
	public void camera()
	{
		// Calculate the z coordinate according the size of the screen
		float cameraZ = (height/2.0f) / (float) (Math.tan(Math.PI*60.0f/360.0f));

		// Set camera values
		camera(width/2,-height/2,cameraZ,0,0,0,0,0,1);
	}

	/**
	 * Loads a 3D world in M3G format from a resource
	 *
	 * @param name name of the resource to load from or a URI
	 * @throws RuntimeException if the resource is not found or the first object in the 
	 *         file is not a world
	 *
	 * @since 0.2
	 */

	public void loadWorld(String name)
	{
		// Remove all meshes
		removeMeshes();
		
		try
		{
			// If the name is not a URI load from the resources path
			// Check if the file have the root / 
			if(!name.startsWith("http://"))
				if(!name.startsWith("/"))
					name = "/" + name;
				
			// Load the world
			Object3D[] objects = Loader.load(name);

            // Find the world node, best to do it the "safe" way
            for(int i = 0; i < objects.length; i++)
                if(objects[i] instanceof World)
                {
                    world = (World) objects[i];
                    break;
                }
			
			// change the current active camara with the world
			activeCamera = new MCamera(world.getActiveCamera());

			// calculate the view port and set
			calculateViewport();
		}
		catch(Exception e)
		{
			// If the resource can't be found or the content of the file is not a world
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Calculate and set viewport according the world and the type of projection.
	 *
	 * @since 0.2
	 */

	private void calculateViewport()
	{
		// View port size
		int viewportX = 0;
		int viewportY = 0;
		int viewportWidth = width;
		int viewportHeight = height;

		// Current projection values
		float[] projection = new float[4];

		// Load current projection values and type of proyection
		int proyectionType = activeCamera.projection(projection);

		// Calculate view port if camera is different from generic
		if(proyectionType != Camera.GENERIC)
		{
			// Calculate ratio aspect
			float aspect = viewportWidth/viewportHeight;

			// If the aspect is lower we need to change height
			if(aspect<projection[1])
			{
				// Calculate y and height
				viewportHeight = (int) (viewportWidth/projection[1]);
				viewportY = (height - viewportHeight)/2;
			}
			// the aspect is greater we need to change width
			else
			{
				// Calculate x and width
				viewportWidth = (int) (viewportHeight*projection[1]);
				viewportX = (width - viewportWidth)/2;
			}
		}

		// Set the view port
		g3d.setViewport(viewportX,viewportY,viewportWidth,viewportHeight);
	}

	/**
	 * Draws a 3D Line.
	 *
	 * @param x1 x-coordinate of first point
	 * @param y1 y-coordinate of first point
	 * @param z1 z-coordinate of first point
	 * @param x2 x-coordinate of second point
	 * @param y2 y-coordinate of second point
	 * @param z2 z-coordinate of second point
	 *
	 * @since 0.?
	 */

	public void line(int x1, int y1, int z1, int x2, int y2, int z2)
	{
	}

	/**
	 * Draws a 3D Line.
	 *
	 * @param x1 x-coordinate of first point
	 * @param y1 y-coordinate of first point	 
	 * @param x2 x-coordinate of second point
	 * @param y2 y-coordinate of second point
	 *
	 * @since 0.?
	 */

	public void line(int x1, int y1, int x2, int y2)
	{
		line(x1,y1,0,x2,y2,0);
	}

	/**
	 * Creates a 3D Cube with the size specified.
	 *
	 * @param width dimension of the box in the x-dimension
	 * @param height dimension of the box in the y-dimension
	 * @param depth dimension of the box in the z-dimension
	 * 
	 * @return The mesh result of the box construction
	 *
	 * @since 0.3
	 */

	public MMesh box(int width, int height, int depth)
	{
		// Create a shape with eight vertex and the triangle strip 
		// set by the indices
		
		beginShape(PMIDlet.TRIANGLE_STRIP);

		// Get the mesh to be constructed
		MMesh mesh = currentMesh;

		// Make a cube centered in 0
		width /= 2;
		height /= 2;
		depth /= 2;

		// Add the 3D vertex for the cube
		vertex(-width,-height,depth);
		vertex(width,-height,depth);
  		vertex(-width,height,depth);
  		vertex(width,height,depth);
  		vertex(-width,-height,-depth);
  		vertex(width,-height,-depth);
  		vertex(-width,height,-depth);
  		vertex(width,height,-depth);

		// Set the strip triangle index values
		currentMesh.index(0);
		currentMesh.index(1);
		currentMesh.index(2);
		currentMesh.index(3);
		currentMesh.index(7);
		currentMesh.index(1);
		currentMesh.index(5);
		currentMesh.index(4);
		currentMesh.index(7);
		currentMesh.index(6);
		currentMesh.index(2);
		currentMesh.index(4);
		currentMesh.index(0);
		currentMesh.index(1);

		// Add the shape to the scene
		endShape();

		// Return the mesh with the box
		return mesh;
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
	 *
	 * @since 0.3
	 */

	public void ellipse(int x, int y, int width, int height)
	{
		// Center of the ellipse
		int cX = x;
		int cY = y;

		// Calculate coords acoording ellipse Mode
		switch(ellipseMode)
		{
			// The args are two points, calculate center, width and height
			case PMIDlet.CORNERS : 
							width = Math.abs(x - width);
							height = Math.abs(y - height);
							cX = x + width/2;
							cY = y + height/2;
							break;

			// The first arg are the corner, calculate real center
			case PMIDlet.CORNER : 
							cX = x + width/2;
							cY = y + height/2;
							break;

			// The width and height are the radius not the diameter
			case PMIDlet.CENTER_RADIUS : 
							width *= 2;
							height *= 2;
							break;
		}

		// the y coordinate is inverse
		cY *= -1;

		// Go to the center of the ellipse
		translate(cX,cY,0);

		// Calculate radius of the ellipse
		int a = width/2;
		int b = height/2;

		// Start at angle zero
		float tetha = 0;

		// Coordinates of one point of the ellipse
		int nX;
		int nY;

		// Number of triangles
		int numTriangles = 50;

		// Create a shape with the number of triangles specified
		beginShape(PMIDlet.TRIANGLE_STRIP);

		// Calculate all the points
		for(int i=0; i<=numTriangles; i++)
		{
			// Add a vertex at the center of the ellipse
			vertex(0,0);

			// Calculate ellipse point
			nX = (int) (a * Math.cos(tetha));
			nY = (int) (b * Math.sin(tetha));

			// Add ellipse point like vertex
			vertex(nX,nY);

			// Go to the next angle
			tetha += Math.PI*2/numTriangles;
		}

		// Add the shape to the scene
		endShape();

		// Restore translation values
		translate(-cX,-cY,0);
	}

	/**
	 * Draws a point, a coordinate in space at the dimension of one pixel. 
	 * The first parameter is the horizontal value for the point, the second value is the vertical value for 
	 * the point, and the optional third value is the depth value.
	 *  
	 * @param x x-coordinate of the point
	 * @param y y-coordinate of the point
	 * @param z z-coordinate of the point
	 *
	 * @since 0.3
	 */

	public void point(int x, int y, int z)
	{
		// Go to the point location
		translate(x,-y,z);

		// Draw the point like a colored cube
		box(2);

		// Restore location
		translate(-x,y,-z);
	}

	/**
	 * Draws a point, a coordinate in space at the dimension of one pixel. 
	 * The first parameter is the horizontal value for the point, the second value is the vertical value for 
	 * the point, and the optional third value is the depth value.
	 *  
	 * @param x x-coordinate of the point
	 * @param y y-coordinate of the point
	 *
	 * @since 0.3
	 */

	public void point(int x, int y)
	{
		point(x,y,0);
	}

	/**
	 * A quad is a quadrilateral, a four sided polygon. It is similar to a rectangle, 
	 * but the angles between its edges are not constrained to ninety degrees.
	 * 
	 * @param x1 x-coordinate of the first corner
	 * @param y1 y-coordinate of the first corner
	 * @param x2 x-coordinate of the second corner
	 * @param y2 y-coordinate of the second corner
	 * @param x3 x-coordinate of the third corner
	 * @param y3 y-coordinate of the third corner
	 * @param x4 x-coordinate of the fourth corner
	 * @param y4 y-coordinate of the fourth corner
	 *
	 * @since 0.3
	 */

	public void quad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4)
	{
		beginShape(PMIDlet.TRIANGLE_STRIP);

		vertex(x2,y2);
		vertex(x3,y3);
		vertex(x1,y1);
		vertex(x4,y4);

		endShape();
	}

	/**
	 * A triangle is a plane created by connecting three points. The first two arguments specify 
	 * the first point, the middle two arguments specify the second point, and the last two arguments 
	 * specify the third point.
	 * 
	 * @param x1 x-coordinate of the first corner
	 * @param y1 y-coordinate of the first corner
	 * @param x2 x-coordinate of the second corner
	 * @param y2 y-coordinate of the second corner
	 * @param x3 x-coordinate of the third corner
	 * @param y3 y-coordinate of the third corner
	 *
	 * @since 0.3
	 */

	public void triangle(int x1, int y1, int x2, int y2, int x3, int y3)
	{
		beginShape(PMIDlet.TRIANGLE_STRIP);

		vertex(x1,y1);
		vertex(x2,y2);
		vertex(x3,y3);

		endShape();
	}

	/**
	 * Retrieves an object that has the given user ID and is reachable from this object. 
	 * 
	 * @param userID the user ID to search for
	 * @return the first object encountered that has the given user ID, or null if no matching objects were found
	 * 
	 * @since 0.4
	 */

	public M3dObject findMesh(int userID)
	{
		M3dObject object3D = new M3dObject((Node) world.find(userID));
		meshes.addElement(object3D);
		
		return object3D;
	}

	/**
	 * Updates all animated properties in this Object3D and all Object3Ds that are reachable from this Object3D. 
	 * Objects that are not reachable are not affected.
	 * 
	 * @param time world time to update the animations to
	 * @return validity interval; the number of time units until this method needs to be called again for this or any reachable Object3D
	 *
	 * @since 0.4
	 */

	public int animate(int time)
	{
		return world.animate(time);
	}

	/**
	 * A Sphere
	 *
	 * @param radius the radius of the sphere
	 * @return The mesh result of the sphere construction
	 *
	 * @since 0.4
	 */

	public MMesh sphere(int radius)
	{
		// Begin the sphere
		beginShape(PMIDlet.TRIANGLE_STRIP);

		// Get the mesh to be constructed
		MMesh mesh = currentMesh;
  
		// Set precision. 
		// I test with some values 10,20,30,40,50,100.
		// 40 looks fine
		int n = 40;
  
		// Angle and position values
		double t1,t2,t3;
		double eX, eY, eZ;
		byte pX, pY, pZ;
  
		// Calculate vertexs
		for(int j=0; j<n/2; j++)
		{
			t1 = j * Math.PI*2 / (n/2);
			t2 = (j + 1) * Math.PI*2 / (n/2);

			for(int i=0; i<=n; i++)
			{
				t3 = i * Math.PI * 2 / n;

				eX = Math.cos(t2) * Math.cos(t3);
				eY = Math.sin(t2);
				eZ = Math.cos(t2) * Math.sin(t3);
				pX = (byte) (radius * eX);
				pY = (byte) (radius * eY);
				pZ = (byte) (radius * eZ);
				vertex(pX,pY,pZ);                  
      
				eX = Math.cos(t1) * Math.cos(t3);
				eY = Math.sin(t1);
				eZ = Math.cos(t1) * Math.sin(t3);
				pX = (byte) (radius * eX);
				pY = (byte) (radius * eY);
				pZ = (byte) (radius * eZ);
				vertex(pX,pY,pZ);                  
			}
		}  
		
		// Add the shape to the scene
		endShape();

		// Return the mesh with the sphere
		return mesh;
	}

	/**
	 * Picks the first Mesh or scaled Sprite3D in this Group that is enabled for picking, 
	 * is intercepted by the given pick ray, and is in the specified scope.
	 *
	 * @param X coordinate of the point on the viewport plane through which to cast the ray
	 * @param Y coordinate of the point on the viewport plane through which to cast the ray
	 *
	 * @return The 3D object picked or null if not mesh was picked
	 *
	 * @sinde 0.4
	 */

	public M3dObject pick(float x, float y)
	{
	
		// Set the Intersection information
		RayIntersection ray = new RayIntersection();

		// Calculate coords acoording viewport
		x /= width;
		y /= height;

		// Get the active camera
		Camera nativeCamera = (Camera) activeCamera.getObject3D();

		// Try to pick a object of all the escene
		if(world.pick(-1,x,y,nativeCamera,ray))
		{
			// Get the Node picked
			Node pickedNode = ray.getIntersected();

			// Seek into the vector the M3d Object for the real object
			M3dObject pickedObject = null;

			// Get the current 3D objects and remove it from the scene
			for(Enumeration e = meshes.elements(); e.hasMoreElements(); )
			{
				// Get the M3d Object
				M3dObject m3dObject = (M3dObject) e.nextElement();
				Node node = m3dObject.getObject3D();

				// Check if the node is the picked one
				if(node == pickedNode)
				{
					pickedObject = m3dObject;
					break;
				}
			}

			// If the object is recognized, return m3d object
			if(pickedObject != null)
				return pickedObject;
		}

		// If not object is picked or search is nor succesful return null
		return null;
	}

	/**
	 * Sets the the default ambient light, directional light, falloff, and specular values. 
	 * The defaults are are ambientLight(128, 128, 128) and directionalLight(128, 128, 128, 0, 0, -1), 
	 * falloff(1, 0, 0), and specular(0, 0, 0). Lights need to be included in the draw() to remain persistent 
	 * in a looping program. Placing them in the setup() of a looping program will cause them to only have an
	 * effect the first time through the loop.
	 *
	 * @since 0.4
	 */

	public void lights()
	{
		directionalLight(128,128,128,0,0,-1);
		lightFalloff(1,0,0);
		lightSpecular(0,0,0);
		ambientLight(128,128,128);
	}

	/**
	 * Sets the falloff rates for point lights, spot lights, and ambient lights. 
	 * The parameters are used to determine the falloff with the following equation:
	 * d = distance from light position to vertex position
	 * falloff = 1 / (CONSTANT + d * LINEAR + (d*d) * QUADRATIC)
	 *
	 * Like fill(), it affects only the elements which are created after it in the code. 
	 * The default value if LightFalloff(1.0, 0.0, 0.0). Thinking about an ambient light with 
	 * a falloff can be tricky. 
	 * It is used, for example, if you wanted a region of your scene to be lit ambiently 
	 * one color and another region to be lit ambiently by another color, you would use an 
	 * ambient light with location and falloff. You can think of it as a point light that doesn't 
	 * care which direction a surface is facing.
	 *
	 * @param constant constant value for determining falloff
	 * @param linear linear value for determining falloff
	 * @param quadratic quadratic value for determining falloff
	 *
	 * @since 0.4
	 */

	public void lightFalloff(float constant, float linear, float quadratic)
	{
	}

	/**
	 * Sets the specular color for lights. Like fill(), it affects only the elements which are created 
	 * after it in the code. Specular refers to light which bounces off a surface in a perferred 
	 * direction (rather than bouncing in all directions like a diffuse light) and is used for creating 
	 * highlights. The specular quality of a light interacts with the specular material qualities 
	 * set through the specular() and shininess() functions.
	 * 
	 * @param v1 red or hue value
	 * @param v2 red or hue value
	 * @param v1 red or hue value/
	 * 
	 * @since 0.4
	 */

	public void lightSpecular(float v1, float v2, float v3)
	{
	}

	/**
	 * Return the active camera
	 *
	 * @return The active camera
	 *
	 * @since 0.4
	 */

	public MCamera activeCamera()
	{
		return activeCamera;
	}

	/**
	 * Retrieves the translation component of this 3D System.
	 *
	 * @param xyz - a float array to fill in with (tx ty tz) 
	 *
	 * @since 0.4
	 */

	public void getTranslation(float[] xyz)
	{
		xyz[0] = translateX;
		xyz[1] = translateY;
		xyz[2] = translateZ;
	}

	/**
	 * Returns the objects found in the m3g file specified
	 *
	 * @param name name of the resource to load from or a URI
	 * @return Array with the objects found in the m3g file
	 * @throws RuntimeException if the resource is not found or the first object in the 
	 *         file is not a world
	 *
	 * @since 0.5
	 */
	public M3dObject[] loadObjects(String resourceName)
	{
		try
		{
			// If the name is not a URI load from the resources path
			if(!resourceName.startsWith("http://"))
				resourceName = "/" + resourceName;
				
			// Load the objects
			Object3D[] objects = Loader.load(resourceName);

			// Create the array of objects
			M3dObject[] m3dObjects = new M3dObject[objects.length];

            // Find the world node, best to do it the "safe" way
            for(int i = 0; i < objects.length; i++)
				m3dObjects[i] = createObject(objects[i]);

			// Return the objects
			return m3dObjects;
		}
		catch(Exception e)
		{
			// If the resource can't be found or the content of the file is not a world
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Create the M3d object with the native object 
	 *
	 * @param obj Native object to create M3d object representation
	 * @return The M3d Object
	 *
	 * @since 0.5
	 */
	protected static M3dObject createObject(Object3D obj)
	{
		// The M3d Object
	 	M3dObject m3dObject = null;
		
		// Check the object type
        if(obj instanceof Mesh)
			m3dObject = new M3dObject((Mesh) obj);
		else if(obj instanceof Light)
			m3dObject = new MLight((Light) obj);
		else if(obj instanceof Camera)
			m3dObject = new MCamera((Camera) obj);
		else if(obj instanceof Sprite3D)
			m3dObject = new MSprite3D((Sprite3D) obj);			
		else if(obj instanceof World)
			m3dObject = new MWorld((World) obj);

		// Return the object
		return m3dObject;
	}

	/**
	 * Return the current world
	 *
	 * @return The current world
	 *
	 * @since 0.5
	 */
	public MWorld world()
	{
		return new MWorld(world);
	}

	/**
	 * Creates a duplicate of this Object3D.
	 *
	 * @param m3dObject Object to duplicate
	 * @return Duplicate object
	 *
	 * @since 0.5
	 */
	public M3dObject duplicate(M3dObject m3dObject)
	{
		// Get the native object and create a duplicate
		Node nodeObject = m3dObject.getObject3D();
		Object3D obj = nodeObject.duplicate();

		// Return a new m3d object
		return createObject(obj);
	}
	
	/**
	 * Draw a image like a 3D object always facing the user
	 * 
	 * @param pImage Image to draw.
	 * @param x x-coordinate of the image
	 * @param y y-coordinate of the image
	 * @param z z-coordinate of the image
	 * @param width width of the image
	 * @param height height of the image
	 *
	 * @return the sprite object
	 *
	 * @since 0.5
	 */

	public MSprite3D sprite(PImage pImage, int x, int y, int z, int width, int height)
	{
		// Calculte coords and size according with the mode
		int[] coords = calculateCoords(imageMode,x,y,width,height);

		x = coords[0];
		y = coords[1];
		width = coords[2];
		height = coords[3];

		// Convert the native image to a M3G image
		Image2D image2D = new Image2D(Image2D.RGBA,pImage.image);

		// Create an appearance for the image
		Appearance appearance = new Appearance();

		// Don't show the transparent parts of the image
		CompositingMode compositingMode = new CompositingMode();
		compositingMode.setBlending(CompositingMode.ALPHA);
		appearance.setCompositingMode(compositingMode);

		// Create a 3D sprite
		Sprite3D sprite3D = new Sprite3D(true,image2D,appearance);

		// Scale the image to the specified size
		sprite3D.scale(width,height,1.0f);

		// Translate to the corner of the image 
		// The image is reference at center
		translate(x + (width/2),y + (height/2),z);

		// Add the image to the scene
		MSprite3D sprite = new MSprite3D(sprite3D);
		add(sprite);

		// Restore translation values
		translate(-(x + (width/2)),-(y + (height/2)),-z);		
		
		// Return the sprite object
		return sprite;
	}
	
	/**
	 * Draw a image like a 3D object always facing the user
	 * 
	 * @param pImage Image to draw.
	 * @param x x-coordinate of the image
	 * @param y y-coordinate of the image
	 * @param z z-coordinate of the image	 
	 *
	 * @return the sprite object
	 *
	 * @since 0.5
	 */

	public MSprite3D sprite(PImage pImage, int x, int y, int z)
	{
		// Grab the image dimesions
		int width = pImage.width;
		int height = pImage.height;

		// if image mode is corners, calculate the real corner
		if(imageMode == PMIDlet.CORNERS)
		{
			width -= x;
			height -= y;
		}

		// Draw the image with this coordinates
		return sprite(pImage,x,y,z,width,height);
	}	

	/**
	 * Draw a image like a 3D object always facing the user
	 * 
	 * @param pImage Image to draw.
	 * @param x x-coordinate of the image
	 * @param y y-coordinate of the image
	 *
	 * @return the sprite object
	 *
	 * @since 0.5
	 */

	public MSprite3D sprite(PImage pImage, int x, int y)
	{
		return sprite(pImage,x,y,0);
	}	

	/**
	 * Draw a image like a 3D object always facing the user
	 * 
	 * @param pImage Image to draw.
	 * @param x x-coordinate of the image
	 * @param y y-coordinate of the image
	 * @param width width of the image
	 * @param height height of the image
	 *
	 * @return the sprite object
	 *
	 * @since 0.5
	 */

	public MSprite3D sprite(PImage pImage, int x, int y, int width, int height)
	{
		// Draw the image with this coordinates
		return sprite(pImage,x,y,0,width,height);
	}			
	
	/**
	 * Returns the last mesh created, this allows get the images meshs
	 *
	 * @return Last mesh added to the scene
	 *
	 * @since 0.5
	 * @deprecated	 
	 */
	public MMesh lastMesh()
	{
		// Return the last mesh in the vector
		return (MMesh) meshes.elementAt(meshes.size() - 1);
	}
	
	/**
	 * Returns the last 3d object created
	 *
	 * @return Last mesh added to the scene
	 * c
	 * @since 0.6
	 */
	public M3dObject lastObject()
	{
		// Return the last mesh in the vector
		return (M3dObject) meshes.elementAt(meshes.size() - 1);
	}	
	
	/**
	 * Replaces the current matrix with the identity matrix.
	 *
	 * @since 0.6
	 */
	public void resetMatrix()
	{
		translateX = 0;
		translateY = 0;
		translateZ = 0;
	}
	
	/**
	 * Remove the current ambienlight 
	 *
	 * @since 0.6
	 */
	private void removeAmbientLight()
	{
		// Remove the mesh and reset value
		if(ambientLight != null)
		{
			remove(ambientLight);
			ambientLight = null;
		}
	}
}
