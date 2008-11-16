/**
 * Show how to generate a note A above middle C each time the
 * user press a key
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.0 (October - 2007) Initial Release  
 *
 * $Id: A440.pde 300 2007-11-03 05:47:04Z marlonj $
 */

import mjs.processing.mobile.msound.*;
import mjs.processing.mobile.msynth.*;

void keyPressed()
{
  // Create a sine wave with a frequency of 440 Hz
  // without phase and amplitude 1
  MSin gen = new MSin(440,0,1);
  
  // Create a wav sound generator with the sine wav
  // and duration 1000 milliseconds (1 second)
  MWav wav = new MWav(gen,1000);
  
  // Get the sound from the generator
  MSound sound = wav.sound();
  
  // Play the sound
  sound.play();
}
