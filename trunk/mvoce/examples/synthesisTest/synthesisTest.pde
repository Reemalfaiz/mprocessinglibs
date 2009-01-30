/**
 * Show how to synthesize voice. 
 * Run the sketch and press any key to listen a message 
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://www.maryjanesoft.com
 * http://www.marlonj.com
 * 
 * Changes :
 * 
 * 1.0 (January - 2009) Initial Release  
 *
 * $Id$
 */

import mjs.processing.mvoce.*;

// Create library object 
MVoce mVoce;

void setup() 
{
  // Create the voce engine with synthesis enable 
  // recognition unable and no grammar 
  mVoce = new MVoceP(this,true,false,"");
}

// Requiered to use events 
void draw()
{
}

// If the user press any key
// Synthesize a hello message 
void keyPressed()
{
  mVoce.synthesize("Hello There");
}

// If the sketch is close, destroy the library 
void exit()
{
  mVoce.destroy();
  super.exit();
}
