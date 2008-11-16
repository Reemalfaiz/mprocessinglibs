/**
 * Show how to create a simple client/server sketch
 *
 * This sketch creates a ball that is contolled by the 
 * server and is show in all the clients
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.0 (November - 2007) Initial Release  
 *
 * $Id: MasterControl.pde 319 2007-11-19 18:38:12Z marlonj $
 */

import mjs.processing.mobile.mclientserver.*;
import mjs.processing.mobile.msockets.*;

// Socket port 
int port = 4444;

// Server hostname (name or ip)
String hostname = "localhost";

// Server and client objects
MServerSocket mServer;
MClientSocket mClient;

// Ball location
int x;
int y;

// Messages to show
PFont font;
String head = "MSockets MasterControl";
String message = "1 Server | 3 Client";

/*
 * Init the ball location and set text properties
 */
void setup()
{
  // Put the ball at the center of the screen
  x = width/2;
  y = height/2;
  
  // Set text properties
  font = loadFont();
  textFont(font);
  textAlign(CENTER);
  
  // Check library support 
  if(!MSockets.supported())
    message = "MSockets Not Supported";  
}

/*
 * Draw the ball on the screen and messages
 * also check for data send by the server
 */
void draw()
{
  // Clear the screen
  background(0);
  
  // Draw the ball 
  ellipse(x,y,10,10);
  
  // Draw the text messages
  text(head,width/2,font.height);  
  text(message,width/2,height-font.height);
  
  // If this sketch is a client, check data from server
  if(mClient != null)
    checkData();      
}

/*
 * Start the server or client 
 * also move the ball around the screen
 */
void keyPressed()
{
  // Check for client server option
  switch(key)
  {
    case '1' : startServer(); break;
    case '3' : startClient(); break;
  }

  // Only move the ball if the server is created 
  if(mServer != null)
  {
    switch(keyCode)
    {
      case UP : moveBall(UP); break;
      case DOWN : moveBall(DOWN); break;
      case LEFT : moveBall(LEFT); break;
      case RIGHT : moveBall(RIGHT); break;
    }    
  }
}

/*
 * Start the server 
 */
void startServer()
{
  // Create a server in the port specified and change message
  mServer = new MServerSocket(this,port);  
  message = "Server: " + mServer.address() + ":" + mServer.port();
}

/*
 * Create a client
 */
void startClient()
{
  // Ask for the hostname 
  String input = textInput("Hostname",hostname,100);
  
  // If option canceled by the user, do nothing
  if(input == null)
    return;
  
  // Update hostname 
  hostname = input;
  
  // Create a client with the specified host and port
  // also change message
  mClient = new MClientSocket(this,hostname,port);
  message = "Client:" + mClient.address() + ":" + mClient.port();  
}

/*
 * Move the ball around the screen acoording the dir
 *
 * @param dir Direction to move the ball
 */
void moveBall(int dir)
{
  // Check direction
  switch(dir)
  {
    case UP : y -= 5; break;
    case DOWN : y += 5; break;
    case LEFT : x -= 5; break;
    case RIGHT : x += 5; break;
  }

  // If the server is started, send the location
  if(mServer != null)
  {
    mServer.write(x);
    mServer.write(y);
  }    
}

/*
 * Check for data of the server
 */
void checkData()
{
  // If data is available
  if(mClient.available() > 0)
  {
    // Read the location of the ball and update values
    x = mClient.read();
    y = mClient.read();
  }
}
