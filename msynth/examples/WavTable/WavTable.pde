/**
 * Show how to use the wav table
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
 * $Id: WavTable.pde 308 2007-11-04 19:43:56Z marlonj $
 */

import mjs.processing.mobile.msound.*;
import mjs.processing.mobile.msynth.*;

// Create the components to create, show and play a wave
MGenerator gen;
MWaveImage waveImage;
MSound sound;

void setup()
{
  // Load the data from a wav file 
  byte[] data = loadBytes("wave.wav");
  
  // Create a sine wave with a frequency of 440 Hz
  // without phase and amplitude 1  
  gen = new MWavTable(data);
  
  // Create the wave viewer with the generator
  waveImage = new MWaveImage(gen,width-20,height-20);
  
  // Update the frequency of the wave
  waveImage.frequency(1);  
}

void draw()
{
  // Clear the screen and draw the wave 
  background(0);
  image(waveImage.image(),10,10);
}

void keyPressed()
{  
  // If the sound is not calculate yet 
  // create one with the generator
  if(sound == null)
  {
    // Create a wav sound generator with the sine wav
    // and duration 1000 milliseconds (1 second)
    MWav wav = new MWav(waveImage,1000);
  
    // Get the sound from the generator
    sound = wav.sound();
  }
  
  // Play the sound
  sound.play();
}
