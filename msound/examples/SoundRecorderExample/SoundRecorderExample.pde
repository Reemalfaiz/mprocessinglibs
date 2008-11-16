/**
 * SoundRecorder Example
 *
 * This example shows how to use the SoundRecorder object to 
 * capture sound, playing, change volume, record and play 
 * the recorded sound.
 *
 * Use the keys 1 and 3 to start and stop capture sound
 * Use the keys 4 and 6 to start and stop recording
 * Use the key 8 to play recorded sound
 * 
 * @author Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 1.3 (February - 2006) Event RECORD_DATA_AVAILABLE added 
 * 1.2 (December - 2005) Replace Menu Description, Events added
 * 1.1 (November - 2005) Library Rename, Mobile Processing 0001 Compatible
 * 1.0 (October - 2005) Initial Release
 */

import javax.microedition.lcdui.*;

import mjs.processing.mobile.msound.*;

// The Sound recorder
MSoundRecorder recorder;

// The playback volume
int amp = 100;

// Status 
String playStatus = "Stop";
String recordStatus = "Stop";

// Events
String events = "";

void setup()
{
  // Set font
  textFont(new PFont(Font.getDefaultFont()));
  textAlign(CENTER);

  // Create the SoundRecorder and set volume and storage size  
  recorder = new MSoundRecorder(this);
  recorder.volume(amp);
  //recorder.setRecordSizeLimit(1024);
}

// Draw status and help
void draw()
{
  background(200);
  
  fill(0);
  text("SoundRecorder Example",width/2,10);
  text("-------------------------------",width/2,15);
  
  text("1,3 : Start - Stop Capture",width/2,40);
  text("4,6 : Start - Stop Recording",width/2,60);
  text("8 : Play Recorded Sound",width/2,80);
  text("#,* : Volume Control",width/2,100);  
  text("Play : " + playStatus,width/2,120);
  text("Record : " + recordStatus,width/2,140);
  text("Volume : " + amp,width/2,160);  
  text("Events : " + events,width/2,180);  
}

// If user press a key make the action
void keyPressed()
{
  // Volume Control
  if(key == '#')
  {
    amp += 5;
    recorder.volume(amp);
  }

  if(key == '*')
  {
    amp -= 5;
    recorder.volume(amp);
  }

  // Playback Control
  if(key == '1')
  {
    playStatus = "Playing";
    recorder.play();
  }
    
  if(key == '3')
  {
    playStatus = "Stop";
    recorder.stop();
  }

  // Record Control
  if(key == '4')
  {
    recordStatus = "Recording";  
    recorder.startRecord();
  }
    
  if(key == '6')
  {
    recordStatus = "Stop";    
    recorder.stopRecord();    
  }
    
  // Recorded Sound Control
  if(key == '8')
  {
    // Get the sound recorded and play
    MSound sound = recorder.getSound();
    sound.play();
  }
}

// Change the status of the event at the screen
public void libraryEvent(Object library, int event, Object eventData)
{
  switch(event)
  {
    case MSound.EVENT_STARTED : events = "Started"; break;
    case MSound.EVENT_END_OF_MEDIA : events = "End of Media"; break;
    case MSound.EVENT_STOPPED : events = "Stopped"; break;
    case MSound.EVENT_ERROR : events = "Error"; break;
    case MSound.EVENT_CLOSED : events = "Closed"; break;
    case MSound.EVENT_RECORD_STARTED : events = "Record Started"; break;
    case MSound.EVENT_RECORD_STOPPED : events = "Record Stopped"; break;    
    case MSound.EVENT_RECORD_ERROR : events = "Record Error"; break;
    case MSound.EVENT_RECORD_DATA_AVAILABLE : events = "Record Data"; break;    
  }  
}

