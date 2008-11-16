/**
 * DataExchange
 * 
 * This example shows how to create a simple service with bluetooth
 * library.
 *
 * Instructions :
 * Press 1 to start the bluetooth service in a device
 * Press 3 in other device to start the client
 * 
 * Type any key in any device and this will send to the server or 
 * client.
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
 * 1.0 (September - 2005) Initial Release  
 * 
 * $Id: dataExchange.pde 147 2006-07-31 02:00:44Z marlonj $
 */

import mjs.processing.mobile.mbluetooth.*;
import mjs.processing.mobile.mclientserver.*;

// Action values 
final int NONE = 0;
final int START_SERVER = 1;
final int START_CLIENT = 2;

// Action to be execute
int action;

// Service roles
final int SERVER = 1;
final int CLIENT = 2;

// Type of role Server o Client
int runningLike;

// Service UUID
int uuid = 0x12345;

// Server and Object client 
MServer server;
MClient client;

// Type of role and data to show
String role = "";
String data = "";
String help = "1 Server | 3 Client | Any SendData";

// Make device discoverable
void setup()
{
  MBluetooth.start();  
  
  textFont(loadFont());
  textAlign(CENTER);
}

// Draw information on screen and dispatch actions, used to 
// avoid thread deadlock
void draw()
{
  // Clear the display
  background(200);

  // Move to the center of the screen
  translate(width/2,height/2);
    
  // Draw the device like a circle with his role
  fill(255);
  ellipse(0,0,20,20);
  fill(0);
  text(role,0,20);
  
  // If server is active check for client requests
  if(server != null)
    checkRequests();
    
  // If client is active check for server responses
  if(client != null)
    checkResponses();
  
  // Dispatch action in this thread
  switch(action)
  {
    case START_SERVER : startServer(); break;
    case START_CLIENT : startClient(); break;   
  }

  // Draw the data capture  
  text(data,0,35);
  
  // Draw help info
  text(help,0,-40);
}

// Start the bluetooth service
void startServer()
{
  // No aditional actions (return to normal status)
  action = NONE;
  
  // Change the service role
  role = "Server";
  runningLike = SERVER;
  
  // Start a service with the uuid especified and return the server 
  // to make communication with clients
  server = MBluetooth.startService(uuid);
}

// Connect with a server for the service 
void startClient()
{
  // No aditional actions (return to normal status)
  action = NONE;
  
  // Change the service role  
  role = "Client";
  runningLike = CLIENT;
  
  // Seek a device with the service like server and connect to it
  client = MBluetooth.selectService(uuid);
  
  // If server was not found, use role to show warning
  if(client == null)
    role = "Not Master Found";
}

// Checks if any client is sending requests or data
void checkRequests()
{
  MClient remoteClient = server.available();
  
  // Get the data and show
  if(remoteClient != null)
    data = "Received :" + (char) remoteClient.read();
}

// Check if the server response or send any data to the client
void checkResponses()
{
  // If client is active and data is available
  // get the data and show
  if(client != null && client.available() > 0)
    data = "Received :" + (char) client.read();
}

// Request the action according with the key pressed
void keyPressed()
{
  // Start a server if the application don have role yet
  if(key == '1' && runningLike == NONE)
    action = START_SERVER;

  // Start a client if the application don have role yet
  if(key == '3' && runningLike == NONE)
    action = START_CLIENT;    
    
  // If client is active send the key to the server
  if(client != null)
    client.write(key);
    
  // If server is active send the key to all the clients
  if(server != null)
    server.write(key);
}



