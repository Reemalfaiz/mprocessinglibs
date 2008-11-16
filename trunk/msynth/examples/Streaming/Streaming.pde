/**
 * Show how to create a continuous sound and change
 * the frequency in real time
 *
 * Be careful with this example, not all phones support 
 * audio/X-wav content streaming
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
 * $Id: Streaming.pde 313 2007-11-07 05:23:13Z marlonj $
 */

import mjs.processing.mobile.msound.*;
import mjs.processing.mobile.msynth.*;

// Create the components to create, show and play a wave
MConstant freq;
MGenerator gen;
MWaveImage waveImage;
MSound sound;

// Note number, 0 is A, add 1 to half 
int note;
String freqMessage = "up,down change Frequency";

void setup()
{
  // Frequency of the signal 
  freq = new MConstant(440);

  // Create a sine wave with a frequency of 440 Hz
  // without phase and amplitude 1  
  gen = new MSin(freq,
  new MConstant(0),
  new MConstant(1));

  // Create the wave viewer with the generator
  waveImage = new MWaveImage(gen,width-20,height-20);

  // Update the frequency of the wave
  waveImage.frequency(440);  
  
  // Set text attributes
  textFont(loadFont());
  textAlign(CENTER);
}

void draw()
{
  // Clear the screen and draw the wave 
  background(0);
  image(waveImage.image(),10,10);  
  text(freqMessage,width/2,height/2);
}

void keyPressed()
{  
  // If the sound is not calculate yet 
  // create one with the generator
  if(sound == null)
  {
    // Create a wav sound generator with the sine wav
    // in continuous production
    MWav wav = new MWav(waveImage,MSynth.STREAMING);

    // Get the sound from the generator
    sound = wav.sound();
  }

  // Play the sound
  sound.play();

  // Modify the frequency according the keys
  switch(keyCode)
  {
  case UP   : 
    note++; 
    break;
  case DOWN : 
    note--; 
    break;
  }

  // Calculate the new frequency
  // f = 2^(n/12) * 440 Hz
  freq.value(Float11.pow(2.0,note/12.0) * 440);
  freqMessage = "" + freq.value();    
}
