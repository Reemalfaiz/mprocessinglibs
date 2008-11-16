/**
 * Check if the phone supports the MSynth Library
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.0 (November - 2007) Initial Release  
 *
 * $Id: MSynthSupported.pde 312 2007-11-07 04:55:54Z marlonj $
 */

import mjs.processing.mobile.msynth.*;

// Message to show 
String message;

void setup()
{
  // Check if the library is supported and set a message
  if(MSynth.supported())
    message = "MSynth Supported";
  else
    message = "MSynth Not Supported";
    
  // Set the font attributes
  textFont(loadFont());
  textAlign(CENTER);
}

void draw()
{
  // Clear the screen and draw the message
  background(0);
  text(message,width/2,height/2);
}
