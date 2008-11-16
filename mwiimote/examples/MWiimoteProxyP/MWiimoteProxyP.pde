/**
 * This example shows how to create a WiimoteProxy use to 
 * connect the Mobile Processing version 
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
 * $Id: MWiimoteProxyP.pde 347 2008-01-06 07:15:51Z marlonj $
 */

import mjs.processing.mobile.mbt.*;
import mjs.processing.mobile.mwiimote.*;

// Library objects 
MBt mBt;
MWiimote mWiimote;
MWiiControl mWiiControl;
MWiimoteProxy mWiimoteProxy;

// Current Message 
String message = "Press 5 to Discover Control";
String errorMessage = "";

// Client connected 
boolean clientConnected;

/*
 * Create library objects 
 */
void setup()
{
  // Don't loop 
  noLoop();
  
  // Init font properties
  textFont(createFont("monospaced",12));
  textAlign(CENTER);
  
  // Set application size 
  size(450,300);
  
  // Set align 
  rectMode(CENTER);
  
  // Create library objects 
  mBt = new MBtP(this);
  mWiimote = new MWiimoteP(this,mBt);    
  mWiimoteProxy = new MWiimoteProxyP(this);  
  mWiimoteProxy.start();
}

/*
 * Draw information 
 */
void draw()
{
  // Clear the screen
  background(0);
  
  // Draw text info 
  fill(0xFFFFFFFF);
  text(message,width/2,height/2);
  
  // Draw Error Message 
  fill(0xFFFF0000);
  text(errorMessage,width/2,height-20);  
 
  // Move to a corner to draw client info 
  translate(width-40,height-40);
 
  // Draw Proxy Client 
  noFill();
  stroke(0xFFFFFFFF);
  rect(0,0,30,30);
  fill(0xFFFFFFFF);  
  text("Client",0,30);
  
  // Fill the ellipse
  if(clientConnected)
  {
    fill(0xFF00FF00);
    rect(0,0,28,28);
  }    
}

/*
 * Listen keyboard events 
 */
void keyPressed()
{
  switch(key)
  {
    // Discover wiimote 
    case '5' :       
      mWiimote.discover();  
      message = "Discovering Control, Press 1 and 2 at same time";
      redraw();
      break;
  }
}

/*
 * Listen library events 
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
    }

    // Handle the event       
    mWiimote.libraryEvent(library,event,data);    
  }
  // A wiimote event 
  else if(library == mWiimote)
  {
    // Check events 
    switch(event)
    {
      // A control was found 
      // set like proxy control
      case MWiimote.EVENT_CONTROL_FOUND : 
        message = "Control Found";
        mWiiControl = (MWiiControl) data;
        mWiimoteProxy.control(mWiiControl);
        break;

      // No control found 
      case MWiimote.EVENT_CONTROL_NOT_FOUND : 
        message = "Control Not Found, Press 5 to discover again";
        break;

      // Print error messages 
      case MWiimote.EVENT_ERROR : 
        errorMessage = "Error: " + data;
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
        errorMessage = "Error: " + data;
        break;
        
      // Check buttons 
      case MWiiControl.EVENT_BUTTON_PRESSED :
        buttonPressed(((Integer) data).intValue());
        break;                                    
    }
  }  
  // A Proxy event 
  else if(library == mWiimoteProxy)
  {
    // Check events 
    switch(event)
    {
      // Print error messages 
      case MWiimote.EVENT_ERROR : 
        errorMessage = "Error: " + data;
        break;
        
      // Client connected
      case MWiimoteProxy.EVENT_CLIENT_CONNECTED :
        clientConnected = true;
        break;                            
        
      // Client connected
      case MWiimoteProxy.EVENT_CLIENT_DISCONNECTED :
        message = "Press 5 to Discover Control";
        clientConnected = false;
        break;                                    
    }
  }    
  
  // Draw info at screen
  redraw();  
}

/*
 * Check buttons 
 */
void buttonPressed(int buttons)
{
  // Init message value 
  message = "Events : ";
  
  // Update the message with the button pressed 
  if((buttons & MWiiControl.BUTTON_LEFT) > 0)
    message += "LEFT ";
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
 * Close sketch
 */
void exit()
{
  // Stop proxy
  mWiimoteProxy.stop();
  
  // Call exit 
  super.exit();
}

/*
 * Start the application 
 */
public static void main(String args[])
{ 
  // Set parameters and window name 
  String[] options = { "MWiimoteProxy" };
  PApplet.main(options); 
}
