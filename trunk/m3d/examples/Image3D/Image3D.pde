/**
 * Loading and drawing images
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
PImage img; // The image

void setup()
{
  // Create the 3D graphics system
  m3d = new M3d(this);

  // Set the current graphics context
  canvas = m3d.canvas();

  // Load the image from resources  
  img = loadImage("m3d.png");

  // Change background and light
  background(0);
  m3d.ambientLight(128,128,128);  
  
  // Draw the image into the screen
  imageMode(CORNERS);
  m3d.image(img,10,10);
  m3d.image(img,10,10,60,60);
}
