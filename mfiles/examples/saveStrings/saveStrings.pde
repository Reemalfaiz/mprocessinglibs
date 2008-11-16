/**
 * Load the lines from a file
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

// Save the lines
String[] lines = { "Line 1" , "Line 2" }; 
String fileName = "file:///root1/list2.txt";
MFiles.saveStrings(fileName,lines);

// Display a little message
background(0);
textFont(loadFont());
text("Ok",10,10);
