/**
 * Create a pure square wave
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

import mjs.processing.mobile.msound.*;
import mjs.processing.mobile.msynth.*;

void keyPressed()
{
  // Create a square wave with a frequency of 440 Hz
  // without phase and amplitude 1
  MSquare gen = new MSquare(440,0,0.5);
  
  // Create a wav sound generator with the sine wav
  // and duration 1000 milliseconds (1 second)
  MWav wav = new MWav(gen,1000);
  
  // Get the sound from the generator
  MSound sound = wav.sound();
  
  // Play the sound
  sound.play();  
}
