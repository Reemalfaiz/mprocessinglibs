/**
 * NearDevices
 * 
 * This example shows how discover bluetooh devices using the library.
 *
 * Instructions :
 * Press any key to perform a device discovery through the nearDevice
 * method.
 * 
 * @author Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 *
 * Changes :
 * 
 * 1.2 (July - 2006) Minor Changes
 * 1.1 (November - 2005) Library Rename, Mobile Processing 0001 
 * 1.0 (October - 2005) Initial Release  
 *
 * $Id: pNearDevices.pde 148 2006-07-31 02:25:27Z marlonj $
 */

import mjs.processing.mobile.mbluetooth.*;

// Max distance to show device 
int maxDistance;

// Current status or the result for actions
String statusString = "";

// An array with the devices discovered
MDevice[] devices;

// The number of devices discovered (Used to avoid array.length bug)
int numDevices;

// Stop the draw thread and makes devices discoverable
void setup()
{
  noLoop();
  MBluetooth.start();
  
  textFont(loadFont("LucidaBright-14.vlw"));
  textAlign(CENTER);
  
  // Calculate the distance for draw the devices
  maxDistance = (width > height ? height : width)/2 - 40;
}

// Draw the device list
void draw()
{
  background(200);

  // Draw Info   
  fill(0);
  text("Near Devices",width/2,0);
  text("Press Any Key to Search",width/2,15);
  text(statusString,width/2,height/2);  
  
  // No devices to show, return
  if(numDevices == 0)
     return;
  
  // Draw the devices in a circle 
  translate(width/2,height/2);
  
  float angle = TWO_PI / numDevices;

  for(int i=0; i<numDevices; i++)
  {
    int x = (int) (sin(angle * i) * maxDistance);
    int y = (int) (cos(angle * i) * maxDistance);

    // Show any device like a circle with his name under
    fill(255);
    ellipse(x,y,15,15);
    fill(0);
    text(devices[i].name(),x,y + 10);
  }      
}

// Discover near devices with bluetooth
void discoverDevices()
{  
  statusString = "Searching ...";
  redraw();
  
  // We need to create a different thread to avoid deadlocks
  new Thread()
  {
    public void run()
    {
      // Get near devices and show information
      devices = MBluetooth.nearDevices();
      numDevices = devices.length;    
      statusString = numDevices + " Found";    
      redraw();    
    }
  }.start();
}

// If any key is pressed begin discovery inquiry
void keyPressed()
{
  discoverDevices();
}

