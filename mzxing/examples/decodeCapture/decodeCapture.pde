/**
 * Show how to capture image and decode.
 *
 * This example requires MVideo library
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
 * $Id: decodeCapture.pde 348 2008-01-08 06:11:31Z marlonj $
 */

import mjs.processing.mobile.mvideo.*;
import mjs.processing.mobile.mzxing.*;

// The instruction to show
String instruction = "Press Any Key to Capture"; 

PImage codeImage; // Image
String message = "Ready"; // The message to show
MZXing mZXing; // The decode engine
MCapture capture; // The Image Capture

// Video properties
int videoX, videoY, videoWidth, videoHeight;
boolean showVideo = true;

/*
 * Init aplication
 */
void setup()
{
  // Set text properties
  textFont(loadFont());
  textAlign(CENTER);
  
  // Create the decode engine
  capture = new MCapture(this);
  mZXing = new MZXing(this);  
  
  // Calculate capturte position
  videoWidth = capture.width()/2;
  videoHeight = capture.height()/2;
  videoX = (width - videoWidth) / 2;
  videoY = (height - videoHeight) / 2;
  capture.location(videoX,videoY);
  capture.size(videoWidth,videoHeight);
  
  // Begin Capture
  capture.play();
  
  // Don´t loop, avoid flashing effect 
  noLoop();
}

/*
 * Draw images and messages 
 */
void draw()
{
  // Set the background
  background(200);
  
  // Set text color
  fill(0);
  
  // If an image is available show it
  if(codeImage != null)
    image(codeImage,videoX,videoY);
    
  // Draw a message, the image and the decode message
  text(instruction,width/2,videoY - 10);
  text(message,width/2,videoY + videoHeight + 15);    
}

/*
 * Check keypad events 
 */
void keyPressed()
{
  // If the video is show, capture and decode
  if(showVideo)
  {
    showVideo = false;
    
    // Capture the image
    PImage captureImage = capture.snapshot();
    capture.hide();
    
    // Show a decoding message while the decode works
    message = "Decoding ...";
    instruction = "Wait";
    
    // Resize the image
    codeImage = new PImage(videoWidth,videoHeight);
    codeImage.copy(captureImage,0,0,captureImage.width,captureImage.height,
                   0,0,videoWidth,videoHeight); 
 
    // Decode the message
    mZXing.decode(captureImage); 
  }
  else
  {
    // Show the video and update messages
    instruction = "Press Any Key to Capture";
    message = "";
    capture.show();
    showVideo = true;
  }
  
  // After key event, always refresh
  redraw();
}

/*
 * Library events
 */
void libraryEvent(Object library, int event, Object data)
{
  // Check for events of MQRCode library
  if(library == mZXing)
  {
    switch(event)
    {
      // Show the decode message
      case MZXing.EVENT_DECODE :
        message = "Message : " + data;
        break;
      
      // Show the error message
      case MZXing.EVENT_ERROR :
        message = "Error : " + data;
        break;
    }
    
    // Draw the instructions and message 
    instruction = "Press Any Key to Show Video";
    redraw();    
  }
}
