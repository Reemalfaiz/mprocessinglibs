/**
 * Show how to recognize some words. 
 * The words are configure in the grammar file in the data folder.
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
  // Create the voce engine with synthesis unable
  // recognition enable and recognice the words 
  // acoording the grammar file digits inside the data folder
  mVoce = new MVoceP(this,false,true,"digits");
}

// Use the draw loop to check for digits 
void draw()
{
  if(mVoce.numTokens() > 0)
    println(mVoce.nextToken());
}

// If the sketch is close, destroy the library 
void exit()
{
  mVoce.destroy();
  super.exit();
}
