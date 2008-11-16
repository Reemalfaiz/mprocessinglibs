/**
 * Shows the local device name at the center of the screen
 * 
 * $Id: deviceName.pde 147 2006-07-31 02:00:44Z marlonj $
 */ 

import mjs.processing.mobile.mbluetooth.*;

// The device name
String deviceName;

void setup()
{
  // Get the local device name
  deviceName = MBluetooth.deviceName(); 

  textFont(loadFont());
  textAlign(CENTER);
}

void draw()
{
  // Draw the name ath the center of screen
  fill(0);
  text(deviceName,width/2,height/2);
}


