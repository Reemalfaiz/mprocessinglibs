/**
 * Show how to generate cosine wave
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
 * $Id: CosineWave.pde 304 2007-11-04 06:48:50Z marlonj $
 */

import mjs.processing.mobile.msound.*;
import mjs.processing.mobile.msynth.*;

void keyPressed()
{
  // Create a cosine wave with a frequency of 440 Hz
  // without phase and amplitude 1
  MCos gen = new MCos(440,0,1);
  
  // Create a wav sound generator with the sine wav
  // and duration 1000 milliseconds (1 second)
  MWav wav = new MWav(gen,1000);
  
  // Get the sound from the generator
  MSound sound = wav.sound();
  
  // Play the sound
  sound.play();
}
