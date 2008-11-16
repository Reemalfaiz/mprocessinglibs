import mjs.processing.mobile.mmessaging.*;

MMessaging mMessaging;

String message;

void setup()
{
  mMessaging = new MMessaging(this);
  mMessaging.receiveSMSMessages(true);
  
  textFont(loadFont());
  textAlign(CENTER);
  
  fill(0);
  framerate(10);
}

void draw()
{
}

void keyPressed()
{
  mMessaging.sendMessage("+5550000","Hello There " + (char) keyCode);
}

void libraryEvent(Object library, int event, Object data)
{
  if(event == MMessaging.EVENT_SMS_MESSAGE_ARRAIVE)
  {
    MMessage mMessage = (MMessage) data;
    message = mMessage.text;
    text(message,random(width),random(height));
  }
}

