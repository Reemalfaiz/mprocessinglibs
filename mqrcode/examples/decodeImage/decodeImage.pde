/**
 * Show how to decode from an image file
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.0 (July - 2006) Initial Release  
 *
 * $Id: decodeImage.pde 140 2006-07-23 19:24:09Z marlonj $
 */

import mjs.processing.mobile.mqrcode.*;

PImage codeImage; // Image
String message = ""; // The message to show
MQRCode qrCode; // The decode engine

void setup()
{
  // Create the decode engine
  qrCode = new MQRCode();
  
  // Load the image
  codeImage = loadImage("/v1.png");
  
  // Set text properties
  textFont(loadFont());
  textAlign(CENTER);
}

void draw()
{
  // Set the background
  background(200);
  
  // Translate to the center of the screen
  translate(width/2,height/2);
  
  // Set text color
  fill(0);
  
  // Draw a message, the image and the decode message
  text("Press Any Key to Decode",0,-codeImage.height/2 - 10);
  image(codeImage,-codeImage.width/2,-codeImage.height/2);
  text(message,0,codeImage.height/2 + 15);
}

void keyPressed()
{
  // Show a decoding message while the decode works
  message = "Decoding ...";  
  
  // Show the decode message
  message = "Message : " + qrCode.decode(codeImage);
}
