/**
 * A box primitive like Processing
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
  // Clear the screen and set black as background
  background(0);

  // Set the light
  m3d.ambientLight(255,255,255);

  // Fill a cube with gray color  
  fill(255 - (frameCount%255));
  m3d.box(width/3);  
  
  // Rotate the cube around x,y,z axis
  m3d.rotateX(3.1416/100);
  m3d.rotateY(3.1416/100);
  m3d.rotateZ(3.1416/100);
}
