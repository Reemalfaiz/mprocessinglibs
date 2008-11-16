/**
 * Save a wave in a wav file in the phone
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
 * $Id$
 */

import mjs.processing.mobile.mfiles.*;
import mjs.processing.mobile.msound.*;
import mjs.processing.mobile.msynth.*;

void keyPressed()
{
  // Create the wave
  MGenerator gen = new MSin(440,0,1);
  
  // Create a wav sound generator with the wave
  MWav wav = new MWav(gen,1000);

  // Get the data and save to a file
  byte[] data = wav.data();
  
  // Save the byte to a file,
  // also you can use only saveBytes to save in the RMS
  MFiles.saveBytes("file:///root1/wave.wav",data);  
}
