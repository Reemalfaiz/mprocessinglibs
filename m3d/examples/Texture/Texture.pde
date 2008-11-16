/**
 * Texture example
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.0 (December - 2005) Initial Release  
 */

import mjs.processing.mobile.m3d.*;

M3d m3d; // 3D System
PImage img; // Texture

void setup()
{
  // Create 3D System
  m3d = new M3d(this);
  canvas = m3d.canvas();
  
  translate(width/2,height/2);
  
  // Load texture
  img = loadImage("texture.png");  
}

void draw()
{
  // Change background and set light
  background(128);
  m3d.ambientLight(255,255,255);
  
  // Create a simple rect with a texture
  beginShape(TRIANGLE_STRIP);
  m3d.texture(img);
  m3d.vertex(-50,-50,0,0,1);
  m3d.vertex(50,-50,0,1,1);
  m3d.vertex(-50,50,0,0,0);
  m3d.vertex(50,50,0,1,0);  
  endShape(); 
  
  // Rotate the rect
  m3d.rotateY(3.1415/10);
}
