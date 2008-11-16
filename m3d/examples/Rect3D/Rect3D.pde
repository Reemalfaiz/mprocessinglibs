/**
 * Using the rect primitive to draw 3D rects
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.1 (December - 2005) Library redesigned
 * 1.0 (November - 2005) Initial Release  
 */

import mjs.processing.mobile.m3d.*;

M3d m3d; // 3D System Manager

void setup()
{
  // Create the 3D graphics system
  m3d = new M3d(this);

  // Set the current graphics context
  canvas = m3d.canvas();
  
  translate(20,height/2);
}

void draw()
{
  // Set the bacground to black
  background(0);  
  
  // Set ambient light
  m3d.ambientLight(255,255,255);

  // Rotate on every axis
  m3d.rotateX(3.1416/100);
  m3d.rotateY(3.1416/100);
  m3d.rotateZ(3.1416/100);

  // Draw three 3D rects with different color
  
  fill(255,0,0);
  rect(20,10,50,50);
  
  fill(0,255,0);
  rect(50,10,50,50);

  fill(0,0,255);
  rect(80,10,50,50);  
}
