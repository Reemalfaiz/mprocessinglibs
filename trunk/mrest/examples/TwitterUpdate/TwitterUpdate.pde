/**
 * TwitterUpdate 
 *
 * This example show how to update the status of a user in twitter 
 * $Id: TwitterUpdate.pde 382 2008-05-15 05:49:42Z marlonj $
 */

import mjs.processing.mobile.mrest.*;

// Request and Response of the twitter service 
MRestRequest mRequest;
MRestResponse mResponse;

// User authorization informacion 
String username = "username";
String password = "password";

// URL of the twitter service 
String serverURL = "http://twitter.com/statuses/update.xml";

void setup()
{
  // Create the request for the twitter service
  // Use the POST method and set authorization information
  mRequest = new MRestRequest(this,serverURL,MRestRequest.POST);
  mRequest.setAuthorization(username,password);  
}

// If the user press any key send a new status message to twitter 
void keyPressed()
{
  // Set message 
  String message = "Testing Twitter and Mobile Processing";
  
  //  Set parameters and send 
  mRequest.parameter("status",message);
  mRequest.send();
}

void libraryEvent(Object library, int event, Object data)
{
  // Check for errors 
  if(library == mRequest)
    if(event == MRestRequest.EVENT_ERROR)
      println((String) data);
}
