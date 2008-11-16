/**
 * Show how to set a background color
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

  // Set the origin at center of the screen
  translate(width/2,height/2);  
}

void draw()
{
  // Set color background to blue
  background(0,0,128);
  
  // Set the ambient light
  m3d.ambientLight(255,255,255);

  // Set the rotation transform
  m3d.rotateX(3.1416/100);
  m3d.rotateY(3.1416/100);
  m3d.rotateZ(3.1416/100);

  // Draw a cube with a different color
  fill(255,255,frameCount % 255);
  m3d.box(100);
}
