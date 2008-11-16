/**
 * Shows the local device name at the center of the screen
 * 
 * $Id: pDeviceName.pde 148 2006-07-31 02:25:27Z marlonj $
 */ 

import mjs.processing.mobile.mbluetooth.*;

// The device name
String deviceName;

void setup()
{
  // Get the local device name
  deviceName = MBluetooth.deviceName(); 

  textFont(loadFont("LucidaBright-14.vlw"));
  textAlign(CENTER);
}

void draw()
{
  // Draw the name ath the center of screen
  background(200);
  fill(0);
  text(deviceName,width/2,height/2);
}


