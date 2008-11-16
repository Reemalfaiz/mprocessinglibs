/**
 * Simple Sphere
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 1.0 (February - 2006) Initial Release  
 */
 
import mjs.processing.mobile.m3d.*;

void setup()
{
  // Init 3D System
  M3d m3d = new M3d(this);
  canvas = m3d.canvas();
  
  // Translate to center of the screen
  translate(width/2,height/2);
  
  // Set background, light and add a single sphere
  background(0);
  m3d.directionalLight(255,255,255,100,100,100);
  m3d.sphere(50);
}
