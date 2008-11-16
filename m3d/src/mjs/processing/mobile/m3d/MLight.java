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

/**
 * A light.
 *
 * @author Mary Jane Soft - Marlon J. Manrique. 
 *
 * @since 0.4
 */

public class MLight extends M3dObject
{
	/**
	 * An ambient light source.
	 */

	public final static int AMBIENT = Light.AMBIENT;

	/**
	 * A directional light source.
	 */

	public final static int DIRECTIONAL = Light.DIRECTIONAL;

	/**
	 * A omni light source.
	 */

	public final static int OMNI = Light.OMNI;

	/**
	 * A spot light source.
	 */

	public final static int SPOT = Light.SPOT;
	 
	/**
	 * The native light
	 */
	 
	private Light nativeLight;
	
	/**
	 * Create a MObject3D without native 3D object.
	 */

	protected MLight(Light light)
	{
		super(light);
		nativeLight = light;
	}

	/**
	 * Create a MObject3D without native 3D object.
	 */

	public MLight(int lightMode)
	{
		nativeLight = new Light();
		setObject3D(nativeLight);

		mode(lightMode);
	}

	/**
	 * Sets the type of this Light. See the class description for more information.
	 *
	 * @param mode the mode to set AMBIENT, DIRECTIONAL, OMNI, SPOT
	 */

	public void mode(int mode)
	{
		nativeLight.setMode(mode);
	}	

	/**
	 * Sets the color of this Light. 
	 * 
	 * Depending on the type of light, this represents either the ambient color or both 
	 * the diffuse and specular colors. See the class description for more information. 
	 * The high order byte of the color value (that is, the alpha component) is ignored.
	 *
	 * @param RGB the color to set for this Light in 0x00RRGGBB format
	 */

	public void color(int RGB)
	{
		nativeLight.setColor(RGB);
	}

	/**
	 * Sets the intensity of this Light. The RGB color of this Light is multiplied 
	 * component-wise with the intensity before computing the lighting equation. 
	 * 
	 * @param intensity the intensity to set; may be negative or zero
	 */

	public void intensity(float intensity)
	{
		nativeLight.setIntensity(intensity);
	}
}