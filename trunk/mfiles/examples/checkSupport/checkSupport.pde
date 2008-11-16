/**
 * Show how to check if files are supported by the phone
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
 * $Id: checkSupport.pde 197 2007-04-17 19:22:58Z marlonj $
 */

import mjs.processing.mobile.mfiles.*;

// Set display values
background(0);
textFont(loadFont());

// Get the support
boolean hasFiles = MFiles.isSupported();

// Display a message
if(hasFiles)
	text("Files are Supported",20,20);
else
	text("Files are NOT Supported",20,20);
