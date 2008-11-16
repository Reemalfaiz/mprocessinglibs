/**
 * Show how to send an image to a pc with the Obex push 
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
 * $Id: ImageObexPusher.pde 360 2008-01-25 00:52:49Z marlonj $
 */
  
import mjs.processing.mobile.mbt.*;
import mjs.processing.mobile.mobex.*;
import mjs.processing.mobile.mvideo.*;

// Push Service UUID
int uuid = 0x1105;

// Bluetooth discover and Obex services
MBt mBt;
MService[] mServices;

// Obex Service Client
MObexClient mObexClient;

// Video Capture 
MCapture mCapture;
PImage pImage;
byte[] data;
int imageCounter;

// Video Properties
int videoX;
int videoY;
int videoWidth;
int videoHeight;

// Text elements
PFont pFont;
String message = "Ready";

// The Device Service List
DeviceList deviceList;

// Init the sketch
void setup()
{
  // Load Font
  pFont = loadFont();
  textFont(pFont);

  // Set initial 
  softkey("Camera");

  // Create the selection device list
  deviceList = new DeviceList();
  deviceList.setBounds(width/4,height/4,width/2,height/2);
}

// Draw the components
void draw()
{
  // Clear the screen
  background(0);

  // If the snaphost is available draw it
  // at the center of the screen
  if(pImage != null)
  {
    pushMatrix();
    translate(width/2,height/2);
    image(pImage,0-pImage.width/2,0-pImage.height/2);
    popMatrix();
  }

  // Draw the deviceList
  deviceList.draw();

  // Draw the title and status
  textAlign(CENTER);
  fill(0xFFFFFF);
  text("Image Obex Pusher",width/2,pFont.height);
  text(message,width/2,height - pFont.height);
}

// Keyboard action 
void keyPressed()
{
  // Device list actions
  deviceList.keyPressed();  
}

// User actions
void softkeyPressed(String label)
{
  // Show camera
  if(label.equals("Camera"))
    showCamera();
  // Search for push services
  else if(label.equals("Search"))
    discover();
  // Get a snapshot
  else if(label.equals("Capture"))
    capture();
}

// Dispatch library events
void libraryEvent(Object library, int event, Object data)
{
  // If bluetooth searching
  if(library == mBt)
  {
    switch(event)
    {
      // Get all the push services and show a list
    case MBt.EVENT_DISCOVER_SERVICE_COMPLETED:
      mServices = (MService[]) data;
      showServices(mServices);
      break;
    }
  }
  // If obex client
  else if(library instanceof MObexClient)
  {
    switch(event)
    {
      // Show any error 
    case MObex.EVENT_ERROR :
      message = "Error" + (String) data;
      break;
    }
  }
}

// Capture a snapshot
void capture()
{
  // Read the data from the snapshot
  data = mCapture.read();  
  PImage snapshot = loadImage(data);

  // Create an thumbnail image
  pImage = new PImage(videoWidth,videoHeight);
  pImage.copy(snapshot,0,0,snapshot.width,snapshot.height,
  0,0,videoWidth,videoHeight);

  // Hide the camera video 
  mCapture.hide();
  
  // Set next action
  softkey("Search");
  
  // Begin drawing 
  loop();
}    

// Discover all the push services
void discover()
{
  message = "searching...";
  mBt = new MBt(this);
  mBt.discoverServices(uuid);
}

// Send the image to the selected device
void sendImage()
{
  sendImage(mServices[deviceList.selected]);
}

// Draw a list of devices
void showServices(MService[] services)
{
  message = length(services) + " Services Found";      
  if(length(services) > 0)
  {
    deviceList.visible = true;
    softkey("Camera");
  }  
}

// Start showing the camera
void showCamera()
{
  // Init all the values
  pImage = null;
  deviceList.visible = false;
  message = "Ready";  

  // Don't loop avoid bad refreshing
  noLoop();  
  
  // Update the user action
  softkey("Capture");

  // Init capture 
  mCapture = new MCapture(this);

  // Calculate Video Size
  videoWidth = mCapture.width() / 2;
  videoHeight = mCapture.height() / 2;
  mCapture.size(videoWidth,videoHeight);

  // Calculate video location
  videoX = (width - videoWidth)/2;
  videoY = (height - videoHeight)/2;

  // Show the video capture at the center of the screen
  mCapture.location(videoX,videoY);
}

// Send the current snapshot to the service
void sendImage(MService service)
{
  // Get obect properties type, filename and data
  String type = mCapture.type();
  String name = "image" + imageCounter + extension(type);
  MObexObject imageObject = new MObexObject(name,type,data);

  // Create an object to send
  mObexClient = new MObexClient(this,service);
  
  // Put the object
  int responseCode = mObexClient.put(imageObject);
  
  // Grab the response code
  message = "ResponseCode " + responseCode;
  
  //Close the connection with the service
  mObexClient.close();
  
  // Update internal counter
  imageCounter++;
}

// Grab the extension of the image type
String extension(String type)
{
  // Unknown extension
  String extension = ".image";

  // Check for image type
  if(type.equals("image/jpeg") || type.equals("image/jpg"))
    extension = ".jpg";
  else if(type.equals("image/png"))
    extension = ".png";
  else if(type.equals("image/gif"))
    extension = ".gif";

  // Return the extension
  return extension;
}
