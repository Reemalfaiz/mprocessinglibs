/**
 * Load the string lines of a file
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
 * $Id: loadStrings.pde 197 2007-04-17 19:22:58Z marlonj $
 */

import mjs.processing.mobile.mfiles.*;

// Set values
background(0);
textFont(loadFont());

// Load the lines
String fileName = "file:///root1/list1.txt";
String[] lines = MFiles.loadStrings(fileName);

// Show the lines
for(int i=0; i<lines.length; i++)
	text(lines[i],20,20 + i*10);
