/**
 * Show how to generate a stereo sound
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
 * $Id: Stereo.pde 311 2007-11-04 21:32:35Z marlonj $
 */

import mjs.processing.mobile.msound.*;
import mjs.processing.mobile.msynth.*;

void keyPressed()
{
  // Create two generators, one for each channel
  // create a saw sine wave envelope in a saw 
  // alse create a second wave with the phase move 
  // half period, to create a stereo effect 
  MGenerator gen1 = new MSin(
    new MConstant(440),
    new MConstant(0),
    new MSaw(0.5,0.0,1.0));
  MGenerator gen2 = new MSin(
    new MConstant(440),
    new MConstant(0),
    new MSaw(0.5,Math.PI,1.0));
  
  // Create a wav sound generator with two channels
  // and duration 1000 milliseconds (1 second)
  MWav wav = new MWav(2,1000);
  wav.channel(0,gen1);
  wav.channel(1,gen2);  
  
  // Get the sound from the generator
  MSound sound = wav.sound();
  
  // Play the sound
  sound.play();
}
