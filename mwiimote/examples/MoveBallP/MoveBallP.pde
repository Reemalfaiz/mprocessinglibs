/**
 * This example shows how to control the movement of a 
 * shape at the screen
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
 * $Id: MoveBallP.pde 345 2008-01-05 04:57:09Z marlonj $
 */
 
import mjs.processing.mobile.mbt.*;
import mjs.processing.mobile.mwiimote.*;

// Library Objects 
MBt mBt; // Bluetooth library use to discover control 
MWiimote mWiimote; // The Wiimote library 
MWiiControl mWiiControl; // The Wiimote control

// Ball location 
int x;
int y;

/*
 * Create library object and init ball location 
 */
void setup()
{
  // Create libraries 
  mBt = new MBtP(this);
  mWiimote = new MWiimoteP(this,mBt);
  
  // Set ball location 
  x = width/2;
  y = height/2;
  
  // Discover wiimote control 
  mWiimote.discover();
}

/*
 * Draw ball 
 */
void draw()
{
  // Draw the background 
  background(0);
  
  // Draw the ball 
  ellipse(x,y,10,10);
  
  // Check accelerometer data 
  if(mWiiControl != null)
  {
    // Get current acceleration 
    int accelX = mWiiControl.accelerometer.accelX();
    int accelY = mWiiControl.accelerometer.accelY();
    
    // Update ball position according acceleration 
    if(accelX < -10)
      x += 1;
    else if(accelX > 10)
      x -= 1;
    if(accelY < -10)
      y -= 1;
    else if(accelY > 10)
      y += 1;      
      
    // Check limits of the screen
    x = constrain(x,0,width);
    y = constrain(y,0,height);
  }
}

/*
 * Listen library events 
 */
public void libraryEvent(Object library, int event, Object data)
{
  // If any bluetooth event, let mwiimote handle it 
  if(library == mBt)
    mWiimote.libraryEvent(library,event,data);
  // Check wiimote events 
  else if(library == mWiimote)
  {
    switch(event)
    {
      // A control was found 
      // Set like current one and active accelerometer
      case MWiimote.EVENT_CONTROL_FOUND : 
        mWiiControl = (MWiiControl) data;
        mWiiControl.accelerometer(true);
        break;
    }
  }
}

/*
 * Close the sketch 
 */
void exit()
{
  // If a control is active close the communication
  if(mWiiControl != null)
    mWiiControl.close();
    
  // Call sketch exit 
  super.exit();
}
