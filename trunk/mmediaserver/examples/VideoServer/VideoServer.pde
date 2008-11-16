/**
 * Blueotooh Video Server 
 *
 * This server allows the transmission of video over bluetooth,
 * the videos are store into the data directory and two files 
 * allow the configuration of channels of the server and create 
 * a lis of files for each client 
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 0.1 (December - 2007) Initial Release  
 *
 * $Id: VideoServer.pde 337 2007-12-27 07:32:18Z marlonj $
 */

// This sketch needs the bluetooth library to create a service
// and the media server library to response the requests 
import mjs.processing.mobile.mbt.*;
import mjs.processing.mobile.mmediaserver.*;

// Libraru objects 
MBt mBt;
MMediaServer mMediaServer;

// Server messages
String message;

/*
 * Create the sketch, init font settings and objects 
 */
void setup()
{
  // Set font settings 
  textFont(loadFont());
  textAlign(CENTER);
  
  // Create library objects 
  mBt = new MBt(this);
  mMediaServer = new MMediaServer(this,mBt);
  
  // Set initial message 
  message = "Press Any Key to Start Server";
}

/*
 * Draw the status of the server 
 */
void draw()
{
  // Clear the screen 
  background(0);
  
  // Draw the message if any 
  if(message != null)
    text(message,width/2,height/2);
}

/*
 * If a key is pressed, start the server 
 */
void keyPressed()
{
  mMediaServer.start();
}

/*
 * Check for library events 
 *
 * @param library Library that fires the event 
 * @param event event identifier 
 * @param data Data fire with the event 
 */
void libraryEvent(Object library, int event, Object data)
{
  // Check for mediaserver events 
  if(library == mMediaServer)
  {
    switch(event)
    {
      // If any error, show at the screen 
      case MMediaServer.EVENT_ERROR : 
        message = (String) data;
        break;

      // The server is running 
      case MMediaServer.EVENT_SERVER_STARTED : 
        message = "Server Started";
        break;        

      // The server is not running 
      case MMediaServer.EVENT_SERVER_STOPPED : 
        message = "Server Stopped";
        break;        
        
      // A client is connected 
      case MMediaServer.EVENT_CLIENT_AVAILABLE : 
        message = "Client Connected";
        break;        

      // A client disconnected 
      case MMediaServer.EVENT_CLIENT_DISPATCHED : 
        message = "Client Dispatched";
        break;        
    }
  }
}

/*
 * Destry the application 
 */
void destroy()
{
  // Stop the server 
  mMediaServer.stop();
}
