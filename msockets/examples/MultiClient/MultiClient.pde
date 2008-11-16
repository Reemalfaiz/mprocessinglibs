/**
 * Show how to create a simple client/server sketch with 
 * with multiple clients data
 *
 * This sketch creates a ball for each client and shows in
 * the server all the clients location
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
 * $Id: MultiClient.pde 319 2007-11-19 18:38:12Z marlonj $
 */

import java.util.Enumeration;
import java.util.Hashtable;

import mjs.processing.mobile.mclientserver.*;
import mjs.processing.mobile.msockets.*;

// Socket port 
int port = 4444;

// Server hostname (name or ip)
String hostname = "localhost";

// Server and client objects
MServerSocket mServer;
MClientSocket mClient;

// Service Clients
Hashtable clients;

// Client Ball location and Name
int x;
int y;
String name = "name";

// Messages to show
PFont font;
String head = "MSockets MultiClient";
String message = "1 Server | 3 Client";

// Client structure to save data
class Client
{
  // Location 
  int x;
  int y;

  // Name 
  String name;
  
  // Current client 
  MClient mClient;    
}

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
  
  // Create the hashtable to store the clients names
  clients = new Hashtable();
  
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
  
  // Draw the ball if client 
  if(mClient != null)
  {
    ellipse(x,y,10,10);
    text(name,x,y+font.height);
  }    
  
  // Draw the text messages
  text(head,width/2,font.height);  
  text(message,width/2,height-font.height);
  
  // If this sketch is a server, check for data from clients
  if(mServer != null)
  {
    checkData();      
    drawClients();
  }
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

  // Only move the ball if client
  if(mClient != null)
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
 * Listen library events 
 *
 * @param library Library 
 * @param event Event send by the library
 * @param data Data send
 */
void libraryEvent(Object library, int event, Object data)
{
  switch(event)
  {
    case MServerSocket.EVENT_CLIENT_CONNECTED : 
      addClient((MClient) data);
  }
}

/*
 * Start the server 
 */
void startServer()
{
  // Create a server in the port specified and change message
  mServer = new MServerSocket(this,port);  
  message = "Server" + mServer.address() + ":" + mServer.port();
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
  message = "Client" + mClient.address() + ":" + mClient.port();  
  
  // Ask the client name
  name = textInput("Name",name,100);
  
  // Send client data to the server
  mClient.writeUTF(name);
  mClient.write(x);
  mClient.write(y);
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

  // If the client is started, send the location
  if(mClient != null)
  {
    mClient.write(x);
    mClient.write(y);
  }    
}

/*
 * Check for data of the server
 */
void checkData()
{
  // Check if a client send data
  MClient client = mServer.available();
  
  // If data is available
  if(client != null)
  {
    // Update the client location
    Client c = (Client) clients.get(client);
    c.x = client.read();
    c.y = client.read();    
  }
}

/*
 * Add the client to the clients list
 *
 * @param client Client to add
 */
void addClient(MClient client)
{
  // Read client properties 
  String name = client.readUTF();
  int x = client.read();
  int y = client.read();
  
  // Create a new client to save the state 
  Client c = new Client();
  c.x = x;
  c.y = y;
  c.name = name;
  
  // Put the client connection and the current representation
  // on the list
  clients.put(client,c);
}

/*
 * Draw the client information
 */
void drawClients()
{
  // For each client, draw a ellipse and his name
  for(Enumeration e = clients.elements(); e.hasMoreElements(); )
  {
    // Get the client 
    Client client = (Client) e.nextElement();

    // Draw client information    
    ellipse(client.x,client.y,10,10);
    text(client.name,client.x,client.y+font.height);
  }
}
