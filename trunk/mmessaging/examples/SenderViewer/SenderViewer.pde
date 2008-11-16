/**
 * Message Sender and Viewer
 *
 * Type the phone number, push # key to send message, push * to clear phone number
 *
 * @author Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 *
 * Changes :
 * 
 * 2.0 (February - 2006) Library Redesigned
 * 1.1 (November - 2005) Library Rename, Mobile Processing 0001 
 * 1.0 (September - 2005) Initial Release  
 */

import mjs.processing.mobile.mmessaging.*;

// The Message
MMessaging mMessaging;

// Creates the message to send, the phone number to send the message and 
// the message receive
String message = "Hello From Mobile Processing";
String phoneNumber = "";
String receiveMessage = "";

// Was the message send ?
boolean messageSend = false;

void setup()
{
  // Init messaging system
  mMessaging = new MMessaging(this);

  // Receive SMS and CBS Messages
  mMessaging.receiveSMSMessages(true);
  mMessaging.receiveCBSMessages(true);  
  
  textFont(loadFont());
  textAlign(CENTER);
}

// Draws the phone numer and receive message
void draw()
{
  background(255);

  // Show the telephone number at the center of the screen
  fill(0);
  text("phone number",width/2,height/2 - 10);
  text("message",width/2,height/2 + 10);
  text(phoneNumber,width/2,height/2);
  text(receiveMessage,width/2,height/2 + 20);
}

// If the user press some keys, capture the phone number and send the message
void keyPressed()
{
  // If the user press # send the message 
  if(keyCode == '#')
  {
    // Adds the time to the message
    String msg = message + " " + hour() + ":" + minute() + ":" + second();
    mMessaging.sendMessage(phoneNumber,msg);
    messageSend = true;
    return;
  }
  
  // If the user press some key after the message was send, capture phone number again
  if(messageSend)
  {
    messageSend = false;
    phoneNumber = "";
  }

  // If user press * reset phone number
  if(key == '*')  
    phoneNumber = "";
    
  // Append the number to the phone number
  if(key >= '0' && key <= '9')
    phoneNumber += "" + key;
}

void libraryEvent(Object library, int event, Object data)
{
  if(event == MMessaging.EVENT_SMS_MESSAGE_ARRAIVE
   || event == MMessaging.EVENT_CBS_MESSAGE_ARRAIVE)
  {
    MMessage mMessage = (MMessage) data;
    receiveMessage = (String) mMessage.text;
  }
}


