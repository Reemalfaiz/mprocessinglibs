/**
 * Show how to get the hostname
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.0 (November - 2007) Initial Release  
 *
 * $Id: HostName.pde 319 2007-11-19 18:38:12Z marlonj $
 */

import mjs.processing.mobile.msockets.*;

// Server hostname (name or ip)
String hostname = "localhost";

/*
 * Int properties
 */
void setup()
{
  // Set text properties
  textFont(loadFont());
  textAlign(CENTER);
  
  // Check library support 
  if(!MSockets.supported())
    hostname = "MSockets Not Supported";  
  else
    hostname = "Hostname: " + MSockets.hostname();
}

/*
 * Draw the hostname value
 */
void draw()
{
  // Clear the screen
  background(0);
  
  // Draw the text messages
  text(hostname,width/2,height/2);    
}
