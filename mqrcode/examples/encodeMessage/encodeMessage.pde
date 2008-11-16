/**
 * Show how to encode a message
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
 * $Id: encodeMessage.pde 140 2006-07-23 19:24:09Z marlonj $
 */

import mjs.processing.mobile.mqrcode.*;

PImage codeImage; // Image
MQRCode qrCode; // The decode engine
String instruction = "Press Any Key to enter Message"; // Instruction for the user
String message = "Message : encoded message"; // Message to encode

void setup()
{
  // Set text properties
  textFont(loadFont());
  textAlign(CENTER);
  
  // Load a example Image
  codeImage = loadImage("/v1.png");  
  
  // Create the decode engine
  qrCode = new MQRCode();      
}

void draw()
{
  // Set the background
  background(200);
  
  // Set text color
  fill(0);
  
  // Translate to the center of the screen
  translate(width/2,height/2);  
  
  // Draw a message and image
  text(instruction,0,-codeImage.height/2 - 10);
  text(message,0,codeImage.height/2 + 15);      
  image(codeImage,-codeImage.width/2,-codeImage.height/2);    
}

void keyPressed()
{
  // get the message to encode
  String decode = textInput("Enter Message","",50);
  
  // Set user messages and encode message
  message = "Encoding Message ...";
  codeImage = qrCode.encode(decode,4);
  message = "Message : " + decode;
}
