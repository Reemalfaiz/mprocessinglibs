/**
 * This example shows how to use the sound library events
 * STARTED and END_OF_MEDIA
 *
 * @author Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 *
 * 1.0 (December - 2005) Initial Release
 */
 
import mjs.processing.mobile.msound.*;
import javax.microedition.lcdui.*;

MSound sound; // The Sound to play
// Application status
String status = "Press Any Key to Play a Sound";

void setup()
{
  // Load the sound
  sound = new MSound("bong.wav",this);
  
  // Set text properties
  textAlign(CENTER);
  textFont(new PFont(Font.getDefaultFont()));
}

void draw()
{    
  // Draw the content
  background(0);
  translate(width/2,height/2);  
  text(status,0,0);
}

// Play the sound if any key is pressed
void keyPressed()
{
  sound.play();
}

// Change the status according with the library event
void libraryEvent(Object library, int event, Object data)
{
  if(event == MSound.EVENT_STARTED)
    status = "Sound Started";
  else if(event == MSound.EVENT_END_OF_MEDIA)
    status = "Sound Stopped";
}
