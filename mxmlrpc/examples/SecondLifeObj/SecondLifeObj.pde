/**
 * This example shows how to call a object in Second Life
 *
 * Mary Jane Soft - Marlon J. Manrique
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com
 *
 * Changes :
 *
 * 0.1 (April - 2008) Initial Release
 *
 * $Id: SecondLifeObj.pde 377 2008-04-05 04:33:37Z marlonj $
 */

import mjs.processing.mobile.mxmlrpc.*;

// Server URL 
String url = "http://xmlrpc.secondlife.com/cgi-bin/xmlrpc.cgi";

// Object Channel 
String objChannel = "da78385b-ce1a-f3fb-aaca-b160587c05d4";

// Remote Client 
MXmlRpc mXmlRpc;

/*
 * Init the current server 
 */
void setup()
{
  // Create the client with the current url
  mXmlRpc = new MXmlRpc(this,url);
 
  // Set call parameters  
  mXmlRpc.param("Channel",objChannel);
  mXmlRpc.param("IntValue",2348);
  mXmlRpc.param("StringValue","Today");
  
  // Don't loop 
  noLoop();
}

/*
 * Draw information on screen
 */
void draw()
{
}

/*
 * Listen key events 
 */
void keyPressed()
{
  // Call procedure
  mXmlRpc.execute("llRemoteData");
}

/*
 * Listen library events 
 *
 * @param library The library that fire the event 
 * @param event The event identifier 
 * @param data The data send with the event 
 */
void eventLibrary(Object library, int event, Object data)
{
  // Check if the library is the procedure call
  if(library == mXmlRpc)
  {
    // Check the type of event 
    switch(event)
    {
      // Show the error information 
      case MXmlRpc.EVENT_ERROR : 
        println("Error :" + data);
        break;
          
      // Show the result information 
      case MXmlRpc.EVENT_RESULT :
        println("Result :" + data);
    }
  }
}
