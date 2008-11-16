import mjs.processing.mobile.mmessaging.*;

MMessaging mMessaging;

byte[] imageData;
PImage pImage;

void setup()
{
  mMessaging = new MMessaging(this);
  mMessaging.receiveSMSMessages(true);
  
  imageData = loadBytes("mp.png");
  
  textFont(loadFont());
  textAlign(CENTER);
}

void draw()
{
  if(pImage != null)
    image(pImage,width/2 - pImage.width/2,height/2 - pImage.height/2);
}

void keyPressed()
{
  mMessaging.sendMessage("5550000",imageData);
}

void libraryEvent(Object library, int event, Object data)
{
  if(event == MMessaging.EVENT_SMS_MESSAGE_ARRAIVE
      || event == MMessaging.EVENT_CBS_MESSAGE_ARRAIVE)
  {
    MMessage mMessage = (MMessage) data;
    pImage = new PImage(mMessage.data);
    println(mMessage.timestamp);
  }
}


