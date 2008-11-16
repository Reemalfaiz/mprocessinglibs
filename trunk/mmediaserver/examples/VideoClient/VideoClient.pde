/**
 * Video Client for the MMediaServer
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
 * $Id: VideoClient.pde 339 2007-12-27 15:27:48Z marlonj $
 */
 
// This sketch needs the bluetooth library to search devices 
// the media server library to connect like client and the 
// video library to show videos loaded from the server 
import mjs.processing.mobile.mbt.*;
import mjs.processing.mobile.mmediaserver.*;
import mjs.processing.mobile.mvideo.*;

// Menu identifiers 
final static int MENU_MAIN = 0;
final static int MENU_SERVERS = 1;
final static int MENU_SERVER = 2;
final static int MENU_CHANNELS = 3;
final static int MENU_FILES = 4;
final static int MENU_VIDEO = 5;

// Identifier of the current menu
int currentMenu;

// Libraries to use 
MBt mBt;
MMediaClient mMediaClient;
MVideo mVideo;

// MediaServer services discover through bluetooth
MService[] servers;
MService currentService;

// Current channels and files on a channel 
String[] channels;
String[] files;

// Header and status messages 
String header = "VideoClient";
String message = "Ready"; 

// Mini menu with a name, a selected item and options  
String listName;
int selectedIndex;
String[] elements;

// Current font use in the application 
PFont pFont;

/*
 * Create the sketch, init font settings
 * initial messages, objects and display main menu 
 */
void setup()
{
  // Load default font 
  pFont = loadFont();
  
  // Set font settings 
  textFont(pFont);
  textAlign(CENTER);
  
  // Create library objects 
  mBt = new MBt(this);
  mMediaClient = new MMediaClient(this,mBt);
 
  // Show main menu 
  showMainMenu();
  
  // Don't loop to avoid flashing efect while the 
  // video is playing 
  noLoop();
}

/*
 * Draw the menu and info messages 
 */
void draw()
{
  // Clear the screen 
  background(0);
  
  // Init the application messages under top
  int y = 10 + pFont.height;
  
  // Draw header 
  fill(0xFFFFFF);
  text(header,width/2,y);
  
  // Let some space 
  y += 2*pFont.height;

  // Draw menu name     
  fill(0xFFFFFF);
  text(listName,width/2,y);

  // Let some space     
  y += pFont.height;

  // Draw menu options 
  // show current one with different color 
  for(int i=0; i<length(elements); i++)
  {
    if(i == selectedIndex)
      fill(0x00FF00);
    else
      fill(0xFFFFFF);
      
    text(elements[i],width/2,y + pFont.height*i);
  }

  // Draw status message 
  fill(0xFFFFFF);  
  text(message,width/2,height-20);
}

/*
 * Listen user key events 
 */
void keyPressed()
{
  // Use UP and Down to move over the menu 
  switch(keyCode)
  {
    // Check if not first option 
    case UP : 
      if(selectedIndex > 0)
        selectedIndex--;
      break;

    // Check if not last option 
    case DOWN : 
      if(selectedIndex+1 < length(elements))
        selectedIndex++;
      break;
    
    // Check when the user select and option 
    case FIRE :
      dispatchMenu();
  }
  
  // Redraw the menu 
  redraw();
}

/*
 * Check the softkeys 
 *
 * @param label Label of the soft key 
 */
void softkeyPressed(String label)
{
  // If back show other menu
  if(label.equals("Back"))
    gotoBeforeMenu();
    
  // Redraw the menu  
  redraw();
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
  // Let the library attend their events 
  mMediaClient.libraryEvent(library,event,data);
  
  // Check media client events 
  if(library == mMediaClient)
    dispatchMediaEvent(event,data);
    
  // Check video events 
  if(library == mVideo)
    dispatchVideoEvent(event,data);
    
  // Redraw status messages 
  redraw();
}

/*
 * Dispatch the events generate by the client 
 *
 * @param event event identifier 
 * @param data data fire with the event 
 */
public void dispatchMediaEvent(int event, Object data)
{
  // Check the event 
  switch(event)
  {
    // If error, show it at screen 
    case MMediaClient.EVENT_ERROR :
      message = (String) data;
      break;

    // The application is discovering server 
    case MMediaClient.EVENT_DISCOVER_SERVICE :
      message = "Discovering Server ...";
      break;

    // Not server found
    case MMediaClient.EVENT_SERVICE_NOT_FOUND :
      message = "Server Not Found";
      break;

    // A single server discovered
    // Open a connection with it 
    case MMediaClient.EVENT_SERVICE_DISCOVERED :
      message = "Server Discovered";
      mMediaClient.open();
      break;        

    // Lot of servers found 
    // Show a menu to choose the server 
    case MMediaClient.EVENT_SERVICES_DISCOVERED :
      servers = (MService[]) data;
      showServersMenu();
      break;        
        
    // When the connection is establish 
    // Show the actions available with the server 
    case MMediaClient.EVENT_CONNECTION_OPENED :
      message = "Connection Opened";
      currentService = (MService) data;
      showServerMenu();      
      break;
      
    // The channels request is ready 
    // show the channels in a menu to allow the user choose 
    case MMediaClient.EVENT_CHANNELS_AVAILABLE :      
      channels = (String[]) data;
      showChannelsMenu();
      break;

    // The files request is ready 
    // show the files in a menu to allow the user choose
    case MMediaClient.EVENT_FILES_AVAILABLE :      
      files = (String[]) data;
      showFilesMenu();
      break;      
        
    // A video is available 
    // Play it 
    case MMediaClient.EVENT_VIDEO_AVAILABLE :
      mVideo = (MVideo) data;
      showVideoMenu();
      break;
      
    // The connection with a server is closes 
    // Show main menu again 
    case MMediaClient.EVENT_CONNECTION_CLOSED :
      message = "Connection Closed";
      currentService = null;
      showMainMenu();
      break;      
      
    // The connection with a server is closes 
    // Show main menu again 
    case MMediaClient.EVENT_FILE_PROGRESS :
      message = "Bytes : " + data;
      break;            
  }
}

/*
 * Dispatch the events generate by the video 
 *
 * @param event event identifier 
 * @param data data fire with the event 
 */
public void dispatchVideoEvent(int event, Object data)
{println(event);
  // Check the event 
  switch(event)
  {
    // The video is playing
    case MVideo.EVENT_STARTED :
      message = "Playing Video ...";
      break;
    
    // The video is loading 
    case MVideo.EVENT_BUFFERING_STARTED :
      message = "Buffering ...";
      break;
      
    // The video is end, close it and release resources 
    // goto the channels menu 
    case MVideo.EVENT_END_OF_MEDIA :
      message = "Video End";      
      mVideo.close();
      mVideo = null;
      gotoBeforeMenu();
      break;      

    // Some error when the video is played 
    case MVideo.EVENT_ERROR :
      message = (String) data;
      break;      
  }
}

/*
 * Destroy the application 
 */
void destroy()
{
  // Close the connection with the server 
  // if it is open 
  mMediaClient.close();
}

/*
 * Update the current menu 
 *
 * @param name Name of the menu
 * @param array Array of options to show 
 */ 
void updateElements(String name, String[] array)
{
  // Update attributes 
  listName = name;
  elements = array;
  
  // Reset selected index 
  selectedIndex = 0;
}

/*
 * Show the main menu 
 */
void showMainMenu()
{
  // Show the main menu and remove any softkey 
  String[] menu = { "Search Server" }; 
  updateElements("Menu",menu);
  currentMenu = MENU_MAIN;
  softkey(null);
}

/*
 * Show a menu with the servers discovered 
 */
void showServersMenu()
{
  // Create an array with the names of the servers 
  String[] names = new String[length(servers)];
  
  // Copy each device name into the array 
  for(int i=0; i<length(servers); i++)
    names[i] = servers[i].device.name();

  // Update menu 
  updateElements("Servers:",names);
  currentMenu = MENU_SERVERS;
  
  // Show status message 
  message = "Multiple Servers Discovered";  
  
  // Add back option 
  softkey("Back");
}

/*
 * Shows the server actions availables 
*/ 
void showServerMenu()
{
  // Show the menu and remove any softkey 
  String[] menu = { "Channels", "Close" }; 
  updateElements(currentService.device.name(),menu);
  currentMenu = MENU_SERVER;
  softkey(null);  
}

/*
 * Show action with each channel 
 */ 
void showChannelsMenu()
{
  // Show the main menu and back option 
  updateElements("Channels:",channels);
  message = "Channels Loaded";
  currentMenu = MENU_CHANNELS;
  softkey("Back");  
}

/*
 * Show files availables into the current channel 
 */
void showFilesMenu()
{
  // Show the menu and add back option 
  updateElements("Videos:",files);
  message = "File list Loaded";
  currentMenu = MENU_FILES;
  softkey("Back");
}

/*
 * Show the video
 */
void showVideoMenu()
{
  // Update message 
  message = "Video Loaded";
  currentMenu = MENU_VIDEO;
  softkey("Back");
  
  // Center the video at screen 
  mVideo.location((width - mVideo.width())/2,
    (height - mVideo.height())/2);
    
  // Begin to play the video 
  mVideo.play();
}

/* 
 * Show the menu below the current one 
 */
void gotoBeforeMenu()
{ 
  // If the menu is server, go to main 
  // don't show again the list of servers 
  if(currentMenu == MENU_SERVER)
    currentMenu--;
  // If the menu is video, stop playing video if available
  // and release resources 
  else if(currentMenu == MENU_VIDEO && mVideo != null)
     mVideo.close();

  // Go to the before menu 
  currentMenu--;
  showMenu();
}

/*
 * Show the menu according number 
 */
void showMenu()
{
  switch(currentMenu)
  {
    case MENU_MAIN : showMainMenu(); break;
    case MENU_SERVERS : showServersMenu(); break;
    case MENU_SERVER : showServerMenu(); break;
    case MENU_CHANNELS : showChannelsMenu(); break; 
    case MENU_FILES : showFilesMenu(); break;
  }
}

/*
 * Dispatch select option in each menu according current menu
 */
void dispatchMenu()
{
  switch(currentMenu)
  {
    // Discover servers 
    // Close any previous connection 
    case MENU_MAIN : 
      mMediaClient.close();
      mMediaClient.discoverServer();    
      break;

    // Open the connection with the server selected 
    case MENU_SERVERS : 
      mMediaClient.open(servers[selectedIndex]);
      break;
      
    // Dispatch the server options 
    case MENU_SERVER : 
      // Load the channels from the server 
      if(elements[selectedIndex].equals("Channels"))
      {
        mMediaClient.channels();
        message = "Loading Channels ...";
      }
      // Close the connection with the server 
      else if(elements[selectedIndex].equals("Close"))
        mMediaClient.close();
      break;
      
    // Load the channels files for the channel selected 
    case MENU_CHANNELS : 
      mMediaClient.channelFiles(elements[selectedIndex]);
      message = "Loading Files ...";      
      break;
      
    // Load the video selected in the files menu 
    case MENU_FILES : 
      mMediaClient.loadFile(elements[selectedIndex]);
      message = "Loading Video ...";
      break;
  }
  
  // Redraw menu and messages 
  redraw();
}
