import mjs.processing.mobile.mrest.*;

/**
 * Flickr Echo example
 *
 * $Id: FlickrExample.pde 234 2007-06-05 23:15:27Z marlonj $
 */

// Request and Response of the service
MRestRequest mRequest;
MRestResponse mResponse;

// Flickr url and api key
String serverURL = "http://api.flickr.com/services/rest/";
String apiKey;

// Message to show
String message = "Press Any Key to Send the Request";

void setup()
{
  // get the api key
  String[] strings = loadStrings("flickr.txt");

  // If not file or key
  if(strings.length == 0)
    message = "No API Key found";
  else
    apiKey = strings[0];

  textAlign(CENTER);
  textFont(loadFont());    
}

void draw()
{
  background(0);
  translate(width/2,height/2);
  text(message,0,0);
}

void keyPressed()
{
  StringBuffer sb = new StringBuffer();

  // Create the request
  mRequest = new MRestRequest(this,serverURL);
  mRequest.parameter("method","flickr.test.echo");
  mRequest.parameter("value","12345");
  mRequest.parameter("api_key",apiKey);
  
  // Send the request and wait for response
  mResponse = mRequest.waitForResponse();

  // Add result to a string
  sb.append(mResponse.get("/rsp/@stat") + "\n");
  sb.append(mResponse.get("/rsp/method/text()") + "\n");

  // Create another request
  mRequest = new MRestRequest(serverURL);
  mRequest.parameter("method","flickr.people.findByUsername");
  mRequest.parameter("api_key",apiKey);
  mRequest.parameter("username","marlonj");  
  mResponse = mRequest.waitForResponse();

  sb.append(mResponse + "\n");
  sb.append(mResponse.get("/rsp/user/@nsid") + "\n");
  sb.append(mResponse.get("/rsp/user/username/text()") + "\n");

  // Show the response
  textInput("Response",sb.toString(),500);
}
