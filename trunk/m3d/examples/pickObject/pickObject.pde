/**
 * Show how to pick object from the 3D scene
 * 
 * To test this example you nedd a phone with pointer support,
 * at the emulator you have to activate the touch_screen propertie
 * go to the WTK installation directory and edit the file 
 * wtklib\devices\DefaultColorPhone\DefaultColorPhone.properties
 * and set the touch_screen to true
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.0 (January - 2007) Initial Release  
 */

import mjs.processing.mobile.m3d.*;

M3d m3d; // 3D System Manager
M3dObject obj3d; // 3D Object

void setup()
{
  // Create the 3D graphics system
  m3d = new M3d(this);
  
  // Set the sccena 
  background(0);
  m3d.ambientLight(128,255,0);
  
  // Add to boxes
  fill(255,0,0);
  translate(width/2 - 40,height/2);
  fill(0,255,0);
  m3d.box(30);
  translate(70,0);  
  fill(255,0,0);    
  m3d.box(30);
}

void pointerPressed()
{
  // Get the object at the pointer position
  obj3d = m3d.pick(pointerX,pointerY);
  
  // If the object is null, not object can be pick 
  // otherwise rotate the object
  if(obj3d != null)
    obj3d.rotate(M3d.PI/10,M3d.PI/10,M3d.PI/10);
}
