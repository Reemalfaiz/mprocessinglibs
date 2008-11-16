/**
 * Show how to load the bytes from a file in the phone
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.0 (April - 2007) Initial Release  
 *
 * $Id: loadBytes.pde 197 2007-04-17 19:22:58Z marlonj $
 */

import mjs.processing.mobile.mfiles.*;

// Get the image
String fileName = "file:///root1/thumb0.png";
byte[] data = MFiles.loadBytes(fileName); 
PImage img = new PImage(data);

// Show the image
image(img,10,10);

