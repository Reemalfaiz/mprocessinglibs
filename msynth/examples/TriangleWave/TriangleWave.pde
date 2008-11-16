/**
 * Create a triangle wave
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
 * $Id: TriangleWave.pde 303 2007-11-04 06:09:59Z marlonj $
 */

import mjs.processing.mobile.msound.*;
import mjs.processing.mobile.msynth.*;

void keyPressed()
{
  // Create a triangle wave with a frequency of 440 Hz
  // without phase and amplitude 1
  MGenerator gen = new MTriangle(440,0,1);
  
  // Create a wav sound generator with the sine wav
  // and duration 1000 milliseconds (1 second)
  MWav wav = new MWav(gen,1000);
  
  // Get the sound from the generator
  MSound sound = wav.sound();
  
  // Play the sound
  sound.play();  
}
