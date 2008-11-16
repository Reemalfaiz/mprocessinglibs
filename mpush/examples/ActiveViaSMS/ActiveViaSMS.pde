// This example show how register a midlet to be
// activate via sms
//
// To run this example you need the mmessaging library
// at least the 0.3.1 release and mpush library

import mjs.processing.mobile.mmessaging.*;
import mjs.processing.mobile.mpush.*;

String message = "";
MPush push;
MMessaging messaging;

void setup()
{  
  // Create the push and messaging services
  push = new MPush(this);
  messaging = new MMessaging(this);
  
  // Begin listening sms messages
  messaging.receiveSMSMessages(true);
  
  // Register the messages activation into the push
  messaging.register(push);
    
  // Set font
  textFont(loadFont());
  textAlign(CENTER); 
}

// Draw the message
void draw()
{
  background(0);  
  translate(width/2,height/2);  
  text("Registered and Waiting for Messages :",0,-10);  
  text("Message : " + message,0,10);  
}

// A sms message arraive
void libraryEvent(Object library, int event, Object data)
{
  if(event == MMessaging.EVENT_SMS_MESSAGE_ARRAIVE || 
      event == MMessaging.EVENT_CBS_MESSAGE_ARRAIVE)
  {
    MMessage mMessage = (MMessage) data;
    message = mMessage.text;
  }
}
