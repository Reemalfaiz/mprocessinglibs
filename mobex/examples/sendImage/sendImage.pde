/**
 * Show how to send an from one phone to other using obex service 
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
 * $Id: sendImage.pde 360 2008-01-25 00:52:49Z marlonj $
 */
 
import mjs.processing.mobile.mbt.*;
import mjs.processing.mobile.mobex.*;
import mjs.processing.mobile.mvideo.*;

// Service UUID
int uuid = 0x1105;

// Bluetooth service
MBt mBt;

// Obex objects 
MObexServer mObexServer;
MObexClient mObexClient;

// Capture image data 
MCapture mCapture;
PImage pImage;
byte[] data;

// Message to show 
String message = "Press 1 (Server) - 3 (Client)";

// List of services 
ServiceList serviceList;

/*
 * Init the application 
 */
void setup()
{
  // Setn font 
  textFont(loadFont());
      
  // Create the service list 
  serviceList = new ServiceList();
  serviceList.x = 10;
  serviceList.y = 20;
}

/*
 * Draw status and images
 */
void draw()
{
  // Clear the screen 
  background(0);
    
  // Show an image if available 
  if(pImage != null)
    image(pImage,0,0);
    
  // show service list 
  translate(0,0);
  serviceList.draw();    
  
  // show status 
  translate(width/2,height - 20);
  textAlign(CENTER);
  fill(0xFFFFFF);
  text(message,0,0);
}

/*
 * Check user event 
 */
void softkeyPressed(String label)
{
  // Capture the image 
  if(label.equals("Capture"))
  {
    // Get the data and show the image 
    data = mCapture.read();
    pImage = loadImage(data);
    mCapture.hide();
    softkey("Search");
  }
  // Discover other devices 
  else if(label.equals("Search"))
  {
     discover();
  }
  // Send the image to the selected device 
  else if(label.equals("Send"))
  {
     sendImage();
  }
}

/*
 * Check key events 
 */
void keyPressed()
{
  // Check list navigation 
  serviceList.keyPressed();
  
  // Create server or client 
  switch(keyCode)
  {
    case '1' : server(); break;
    case '3' : client(); break;
  }
}

/*
 * Discover devices with service active 
 */
void discover()
{
  message = "searching...";
  mBt = new MBt(this);
  mBt.discoverServices(uuid);
}

/*
 * Create a Obex Server 
 */
void server()
{
  mObexServer = new MObexServer(this,uuid);
  mObexServer.start();
}

/*
 * Send the image to the device selected 
 */
void sendImage()
{
  sendImage(serviceList.services[serviceList.selected]);  
}

/*
 * Create the client objects 
 */
void client()
{
  // Change the softkey 
  softkey("Capture");
    
  // Init capture objects 
  mCapture = new MCapture(this);
  
  int videoWidth = mCapture.width();
  int videoHeight = mCapture.height();  
  mCapture.location((width - videoWidth)/2,(height - videoHeight)/2);
  
  mCapture.show();    
}

/* 
 * Check library events 
 */
void libraryEvent(Object library, int event, Object data)
{
  if(library == mBt)
  {
    switch(event)
    {
    // Create a list with all the devices discovered 
    case MBt.EVENT_DISCOVER_SERVICE_COMPLETED:
      serviceList.services = (MService[]) data;
      serviceList.show();
      message = length(serviceList.services) + " Services Found";
      if(length(serviceList.services) > 0)   
        softkey("Send");
      break;
    }
  }
  else if(library == mObexServer)
  {
    switch(event)
    {
    // Show an error message  
    case MObex.EVENT_ERROR :
      message = "Error" + (String) data;
      break;
    // Get the image send by the client 
    case MObex.EVENT_ON_PUT :
      this.data = (byte[]) data;
      pImage = loadImage(this.data);
      break; 
    }
  }
  else if(library instanceof MObexClient)
  {
    switch(event)
    {
    // Show an error message 
    case MObex.EVENT_ERROR :
      message = "Error" + (String) data;
      break;
    }
  }
}

void sendImage(MService service)
{
  mObexClient = new MObexClient(this,service);
  int responseCode = mObexClient.put(data);
  message = "ResponseCode " + responseCode;
  mObexClient.close();
}
