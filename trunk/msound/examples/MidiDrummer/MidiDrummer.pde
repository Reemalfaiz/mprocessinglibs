/**
 * Midi Drummer
 *
 * This Sketch converts the phone into a Drumer, press 
 * any key from the key pad to produce a drum sound.
 * 
 * This example is based on the MMAPIDrummer example included 
 * into the Sun Java Wirless Toolkit 
 *
 * @author Mary Jane Soft - Marlon J. Manrique
 * 
 * Changes :
 * 1.0 (December - 2005) Initial Release  
 */

import mjs.processing.mobile.msound.*;

import javax.microedition.lcdui.*;

// Midi Numbers for the Drums
int closeHitHat = 0x2A;
int openHitHat = 0x2E;
int tambourine = 0x36;
int hiTom = 0x32;
int midTom = 0x2F;
int lowTom = 0x2B;
int rideCymbal = 0x33;
int cowBell = 0x38;
int crashCymbal = 0x31;
int bassDrum = 0x24;
int handClap = 0x27;
int snareDrum = 0x28;

// Midi System
MMidi midi; 

// The name of the sound;
String sound = "";

void setup()
{
  midi = new MMidi(); // Init Midi System
  
  // Set text attributes
  textFont(new PFont(Font.getDefaultFont())); 
  textAlign(CENTER);
  fill(255);
}

void draw()
{
  // Draw the sound information and help
  background(0);
  translate(width/2,height/2);
  text(sound,0,0);
  text("Use the numeric keys to play drums",0,height-30);
}

void keyPressed()
{
  // Play a drum sound according with the key pressed
  switch(key)
  {
    case '1' : playDrum(closeHitHat); sound = "Close HitHat"; break;
    case '2' : playDrum(openHitHat); sound = "Open HitHat"; break;
    case '3' : playDrum(tambourine); sound = "Tambourine"; break;
    case '4' : playDrum(hiTom); sound = "Hi Tom"; break;
    case '5' : playDrum(midTom); sound = "Mid Tom"; break;
    case '6' : playDrum(lowTom); sound = "Low Tom"; break;
    case '7' : playDrum(rideCymbal); sound = "Ride Cymbal"; break;
    case '8' : playDrum(cowBell); sound = "Cow Bell"; break;
    case '9' : playDrum(crashCymbal); sound = "Crash Cymbal"; break;
    case '*' : playDrum(bassDrum); sound = "Bass Drum"; break;
    case '0' : playDrum(handClap); sound = "Hand Clap"; break;
    case '#' : playDrum(snareDrum); sound = "Snare Drum"; break;
  }
}

// Play the note in channel 9
void playDrum(int value)
{
  midi.noteOn(9,value,120);
  midi.noteOff(9,value);
}

