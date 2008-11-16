/**
 * Checks if the library is supported 
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
 * $Id: MSensorSupported.pde 350 2008-01-08 07:25:08Z marlonj $
 */
 
import mjs.processing.mobile.msensor.*;

// Message to show 
String message;

/*
 * Check library support 
 */
void setup()
{
  // Set font propertie s 
  textFont(loadFont());
  textAlign(CENTER);
  
  // Cjeck if the library is supported
  if(MSensorManager.supported())
    message = "MSensor Supported";
  else
    message = "MSensor Not Supported";
}

/*
 * Draw messages 
 */
void draw()
{
  // Clear the screen and write the message 
  background(0);
  text(message,width/2,height/2);
}

