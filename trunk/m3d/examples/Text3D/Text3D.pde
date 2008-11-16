/**
 * Text primitives
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.1 (May - 2007) Load Text Font 
 * 1.0 (December - 2005) Initial Release  
 */

import mjs.processing.mobile.m3d.*;

M3d m3d; // 3D System

void setup()
{
  // create the 3d systems, set graphics context
  m3d = new M3d(this);
  canvas = m3d.canvas();
  
  // Set the text font
  textFont(loadFont());
  
  // Move to the center of the screen
  translate(width/2,height/2);
  textAlign(CENTER);   
}

void draw()
{
  background(200);
  m3d.ambientLight(255,255,255);
  
  // Draw some text
  text("Hello World",-50,-50);  
  m3d.text("Hello World",0,0,100);      
  m3d.text("Hello World",50,-50,-100);    
}

// If any key is pressed load a user font
void keyPressed()
{
  textFont(loadFont("ArialMT-12.mvlw"));
}
