/**
 * Show how to load an image from a file in the phone
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
 * $Id$
 */

import mjs.processing.mobile.mfiles.*;

// Get the image
String fileName = "file:///root1/thumb0.png";
PImage img = MFiles.loadImage(fileName);

// Show the image
image(img,10,10);
