/**
 * Show how to modulate the amplitude
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
 * $Id: AmplitudeModulation.pde 310 2007-11-04 20:09:49Z marlonj $
 */

import mjs.processing.mobile.msound.*;
import mjs.processing.mobile.msynth.*;

// Create the components to create, show and play a wave
MGenerator gen;
MWaveImage waveImage;
MSound sound;

void setup()
{
  // Modulate the amplitude using a saw waves
  MGenerator amp = new MSaw(4,0,1.0);
  MGenerator freq = new MConstant(440);
  MGenerator phase = new MConstant(0); 
  
  // Create a sine wave with the parameters
  gen = new MSin(freq,phase,amp);
  
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
