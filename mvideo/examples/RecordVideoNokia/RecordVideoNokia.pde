/**
 * Show how to record video in a Nokia Phone 
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.0 (January - 2008) Initial Release  
 *
 * $Id: RecordVideoNokia.pde 353 2008-01-11 15:47:24Z marlonj $
 */

import mjs.processing.mobile.mvideo.*;

// Video Record Object 
MVideo video;
MVideoRecorder recorder;

// Screen messages 
String statusMessage = "Ready";

/*
 * Init objects and properties 
 */
void setup()
{
  // Set font 
  textFont(loadFont());
  textAlign(CENTER);
  
  // Create a softkey 
  softkey("Record");
  
  // Avoid flashing effect 
  noLoop();
}

/*
 * Draw Info 
 */
void draw()
{
  // Clear Screen 
  background(200);
  
  // Draw messages
  fill(0x000000);
  text(statusMessage,width/2,height - 20);
}

/*
 * Listen softkeys events 
 */
void softkeyPressed(String label)
{
  // Start recording 
  if(label.equals("Record"))
  {
    // If the recorder is not createe, create one
    // using the custom locator for Nokia  
    if(recorder == null)
      recorder = new MVideoRecorder(this,"capture://video","");
  
    // Show the recorder, start capture and start recording 
    recorder.show();
    recorder.play();
    recorder.startRecord();
    
    // Change the softkey 
    softkey("Stop");
  }
  // Stop recording 
  else if(label.equals("Stop"))
  {
    // Stop recording, stop the capture and hide the video  
    recorder.stopRecord();
    recorder.stop();
    recorder.hide();
    
    // Get the video and change the softkey 
    video = recorder.video();
    softkey("Play");
  }
  // Play the recorded video 
  else if(label.equals("Play"))
  {
    // Show the video and start playing 
    video.show();
    video.play();
  }
  
  // Redraw info screen 
  redraw();
}

/*
 * Listen library events 
 */
void libraryEvent(Object library, int event, Object data)
{
  // Check record events 
  if(library == recorder)
  {
    switch(event)
    {
      // Draw status 
      case MVideo.EVENT_ERROR : 
        statusMessage = (String) data;
        break;       
      case MVideo.EVENT_RECORD_STARTED : 
        statusMessage = "Recording ...";
        break; 
      case MVideo.EVENT_RECORD_STOPPED : 
        statusMessage = "Ready";
        break;         
    }
  }
  // Check video events 
  else if(library == video)
  {
    switch(event)
    {
      // Draw status 
      case MVideo.EVENT_ERROR : 
        statusMessage = (String) data;
        break;
       
      // After playing the video, show the Record softkey again 
      case MVideo.EVENT_END_OF_MEDIA : 
        softkey("Record");
        break;         
    }
  }  
}
