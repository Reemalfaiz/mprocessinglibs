/**
 * RecordData
 *
 * This example shows how to use access the data recorded if 
 * any is available before stopRecord is calling.
 *
 * Use the keys 4 and 6 to start and stop recording
 * Use the key 8 to play the recorded sound
 * 
 * @author Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 1.0 (February - 2006) Initial Release
 */

import mjs.processing.mobile.msound.*;

// Sound Data and Messages
byte[] soundData;
String message;
String eventMessage;

// The Sound Recorder
MSoundRecorder recorder;

// Set the initial values
void setup()
{
  // Create recorder
  recorder = new MSoundRecorder(this);
  
  // Set initial messages
  message = "Press 4 to Record";
  eventMessage = "";
  
  // Set styles
  textFont(loadFont());
  textAlign(CENTER);
  stroke(255,0,0);
}

// Draw the messages and the sound signal
void draw()
{
  // Set color and coordinate system
  background(0);
  translate(width/2,height/2);
  
  // Draw messages
  text(message,0,(-height/2)+15);
  text(eventMessage,0,(height/2)-10);
  
  // Draw sound if any available
  if(soundData != null)
    drawSound();
}

// User Options
void keyPressed()
{
  // Begin recording
  if(key == '4')
  {
    message = "Press 6 to Stop"; 
    recorder.play();
    recorder.startRecord();
  }
  
  // Stop recording
  if(key == '6')
  {
    recorder.stopRecord();
    recorder.stop();    
    message = "Press 8 to Play Recorded Sound";    
  }  
  
  // Get the sound recorded and play it
  if(key == '8')
  {
    MSound sound = recorder.getSound();
    sound.play();
  }
}

// Draw the sound data on the screen
void drawSound()
{
  int x, y, oldX = -width/2, oldY = 0;
  int step = soundData.length/width;

  // Calculate line coordinates  
  for(int i=0; i<width; i++)
  {
    x = i - width/2;
    y = (soundData[i*step] * (height/2 - 20))/128;
    line(oldX,oldY,x,y);
    oldX = x;
    oldY = y;
  }
}

// Listen events from library
void libraryEvent(Object library, int event, Object eventData)
{
  switch(event)
  {
    case MSound.EVENT_RECORD_STARTED : eventMessage = "Record Started"; break;
    case MSound.EVENT_RECORD_STOPPED : eventMessage = "Record Stopped"; break;    
    case MSound.EVENT_RECORD_ERROR : eventMessage = "Record Error"; break;
    case MSound.EVENT_RECORD_DATA_AVAILABLE : eventMessage = "Record Data"; break;    
  }
  
  // If event is data available, draw it at screen
  if(event == MSound.EVENT_RECORD_DATA_AVAILABLE)
    soundData = (byte[]) eventData;
}

