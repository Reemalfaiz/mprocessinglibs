import mjs.processing.mobile.mrest.*;

// Request and Response
MRestRequest mRequest;
MRestResponse mResponse;

// URL of the service 
String serverURL = "http://127.0.0.1";

void setup()
{
  // Create the request for the twitter service
  // Use the POST method and the user agent 
  mRequest = new MRestRequest(this,serverURL,MRestRequest.POST);
  mRequest.property("User-Agent"," Mozilla/4.0");
}

// If the user press any key send the message
void keyPressed()
{
  // Set message 
  String message = "Using custom User-Agent";
  
  //  Set parameters and send 
  mRequest.parameter("message",message);
  mRequest.send();
}

void libraryEvent(Object library, int event, Object data)
{
  // Check for errors 
  if(library == mRequest)
    if(event == MRestRequest.EVENT_ERROR)
      println((String) data);
}
