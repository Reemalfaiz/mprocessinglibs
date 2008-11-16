/**
 * Show how to load worlds.
 *
 * The worlds used in this examples was download from 
 * http://www.m3gexporter.com
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.1 (December - 2005) Initial Release
 */

import mjs.processing.mobile.m3d.*;

M3d m3d; // 3D System Manager

void setup()
{
  // Create the 3D graphics system and set the graphics context
  m3d = new M3d(this);
  canvas = m3d.canvas();
}

// If a key is pressed load the world with the number 
// specified, if world is not found a exception is throw
void keyPressed()
{
  m3d.loadWorld("world0" + key + ".m3g");
}
