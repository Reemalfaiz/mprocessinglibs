/**
 * Create a square wave with the fundamental, the third 
 * and fifth harmonics
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
 * $Id: AddHarmonics.pde 300 2007-11-03 05:47:04Z marlonj $
 */

import mjs.processing.mobile.msound.*;
import mjs.processing.mobile.msynth.*;

void keyPressed()
{
  // Create the fundamental with frequency 100
  MSin fundamental = new MSin(100,0,1);

  // Create the thrid harmonic
  // Frequency 300, amplitude 0.333
  MSin third = new MSin(300,0,0.333);

  // Create the fifth harmonic
  // Frequency 500, amplitude 0.2
  MSin fifth = new MSin(500,0,0.2);
  
  // Add all the waves
  MAdd add = new MAdd();
  add.put(fundamental);
  add.put(third);
  add.put(fifth);
    
  // Create a wav sound generator with the result wave
  // and duration 1000 milliseconds (1 second)
  MWav wav = new MWav(add,1000);
  
  // Get the sound from the generator
  MSound sound = wav.sound();
  
  // Play the sound
  sound.play();
}
