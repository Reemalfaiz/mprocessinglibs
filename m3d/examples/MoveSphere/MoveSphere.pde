/**
 * Move Sphere
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

MMesh sphereMesh;

// Sphere location
int x,y,z;

// Angle
float angle = (float) Math.PI;

void setup()
{
  // Init 3D System
  M3d m3d = new M3d(this);
  canvas = m3d.canvas();
  
  // Translate to center of the screen
  translate(width/2,height/2);  
  
  // Set background, light and add a single sphere
  background(0);
  m3d.directionalLight(255,255,255,0,0,0);
  m3d.omniLight(255,255,255,0,0,0);
  
  fill(255,0,0);
  sphereMesh = m3d.sphere(50);  
}

void draw()
{
  // Move sphere in a circle
  x = (int) (Math.sin(angle) * 100);
  z = (int) (Math.cos(angle) * 100);
  
  // set location and rotate too
  sphereMesh.location(x,y,z);  
  sphereMesh.rotateY(0.05);
  
  // Change angle
  angle += 0.05;
}
