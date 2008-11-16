/**
 * This example shows how to connect Mobile Processing 
 * with a Wiimote using a proxy 
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 0.1 (January - 2008) Initial Release  
 *
 * $Id: MWiimoteClient.pde 346 2008-01-06 06:26:04Z marlonj $
 */

import mjs.processing.mobile.mbt.*;
import mjs.processing.mobile.mwiimote.*;

// Library Objects 
MBt mBt;
MWiimote mWiimote;
MWiiControl mWiiControl;

// Message to show at screen 
String message = "Press 5 to Discover";

// Accelerometer Values 
int accelX ;
int accelY;
int accelZ;

/*
 * Init objects and text properties 
 */
void setup()
{
  // Set text and mode properties
  textFont(loadFont());
  textAlign(CENTER);
  ellipseMode(CENTER);
  
  // Create library objects 
  mBt = new MBt(this);
  
  // Create a mWiimote using a proxy 
  mWiimote = new MWiimote(this,mBt,true);
}

/*
 * Draw info into the screen
 */
void draw()
{
  // Clear the screen 
  background(0);  
  
  // Show message 
  text(message,width/2,height/2);
  
  // Draw accelerometer values like circles 
  // with different color 
  noFill();
  stroke(0xFF0000);
  ellipse(width/2,height/2,accelX,accelX);
  stroke(0x00FF00);  
  ellipse(width/2,height/2,accelY,accelY);
  stroke(0xFFFF00);
  ellipse(width/2,height/2,accelZ,accelZ);
  
  // Update accelerometer values 
  // if a control is available 
  if(mWiiControl != null)
  {
    // Convert negative g to positive 
    accelX = abs(mWiiControl.accelerometer.accelX());
    accelY = abs(mWiiControl.accelerometer.accelY());
    accelZ = abs(mWiiControl.accelerometer.accelZ());
  }
}

/*
 * Listen key events 
 */
void keyPressed()
{
  switch(key)
  {   
    // Set led lights 
    case '1' :
      mWiiControl.leds(0x1000); break;      
    case '2' :
      mWiiControl.leds(0x0100); break;
    case '3' :
      mWiiControl.leds(0x0010); break;
    case '4' :
      mWiiControl.leds(0x0001); break;            
      
    // Discover Wiimote 
    case '5' : 
      mWiimote.discover(); 
      message = "Discovering Control ...";
      break;
      
    // Active accelerometer
    case '6' : 
      mWiiControl.accelerometer(true); 
      message = "Accelerometer Active";
      break;    
  }
}

/*
 * Listen events from the libraries
 */
void libraryEvent(Object library, int event, Object data)
{
  // If bluetooth, let mwiimote handle it 
  if(library == mBt)
  {
    // Check event 
    switch(event)
    {
      // Show status message 
      case MBt.EVENT_DISCOVER_DEVICE :
        message = "Device " + data + " discovered";
        break;      
        
      // Show status message 
      case MBt.EVENT_DISCOVER_SERVICE :
        MService[] services = (MService[]) data;
        message = length(services) + " Found";
        break;              
        
      // Show status message 
      case MBt.EVENT_DISCOVER_SERVICE_COMPLETED :
        message = "Discover Service End";
        break;              
    }
   
    // Handle the event  
    mWiimote.libraryEvent(library,event,data);
  }
  else if(library == mWiimote )
  {
    // Check events 
    switch(event)
    {
      // A control was found 
      // set like proxy control
      case MWiimote.EVENT_CONTROL_FOUND : 
        mWiiControl = (MWiiControl) data;
        message = "Ready";
        break;
        
      // A control not found 
      case MWiimote.EVENT_CONTROL_NOT_FOUND : 
        message = "Control Not Found";
        break;        

      // Print error messages 
      case MWiimote.EVENT_ERROR : 
        message = "Error: " + data;
        break;
    }
  }
  // A control event 
  else if(library == mWiiControl)
  {
    // Check events 
    switch(event)
    {      
      // Print error messages 
      case MWiimote.EVENT_ERROR : 
        message = "Error: " + data;
        break;
        
      // Check buttons 
      case MWiiControl.EVENT_BUTTON_PRESSED :
        buttonPressed(((Integer) data).intValue());
        break;                    
    }
  }    
}

/*
 * Check buttons 
 */
void buttonPressed(int buttons)
{
  // Init message value 
  message = "";
  
  // Update the message with the button pressed 
  if((buttons & MWiiControl.BUTTON_LEFT) > 0)
    message = "LEFT ";
  if((buttons & MWiiControl.BUTTON_RIGHT) > 0)
    message += "RIGHT ";
  if((buttons & MWiiControl.BUTTON_UP) > 0)
    message +=  "UP ";
  if((buttons & MWiiControl.BUTTON_DOWN) > 0)
    message += "DOWN ";
  if((buttons & MWiiControl.BUTTON_PLUS) > 0)
    message += "+ ";
  if((buttons & MWiiControl.BUTTON_MINUS) > 0)
    message += "- ";
  if((buttons & MWiiControl.BUTTON_ONE) > 0)
    message += "1 ";
  if((buttons & MWiiControl.BUTTON_TWO) > 0)
    message += "2 ";
  if((buttons & MWiiControl.BUTTON_A) > 0)
    message += "A ";
  if((buttons & MWiiControl.BUTTON_B) > 0)
    message += "B ";
  if((buttons & MWiiControl.BUTTON_HOME) > 0)
    message += "HOME ";
}

/*
 * Close the application 
 */
void destroy()
{
  // If a control is open, close connections 
  if(mWiiControl != null)
    mWiiControl.close();
}
