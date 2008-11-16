import mjs.processing.mobile.mbt.*;
import mjs.processing.mobile.mobex.*;

// Service UUID
int uuid = 0x1105;

MBt mBt;

MObexServer mObexServer;
MObexClient mObexClient;

String message = "Ready";

ServiceList serviceList;

void setup()
{
  textFont(loadFont());
  serviceList = new ServiceList();
  serviceList.x = 10;
  serviceList.y = 20;
}

void draw()
{
  background(0);
  translate(0,0);
  serviceList.draw();
  translate(width/2,height - 20);
  textAlign(CENTER);
  fill(0xFFFFFF);
  text(message,0,0);
}

void keyPressed()
{
  serviceList.keyPressed();

  if(key == '1')
    receive();
  else if(key == '3')
    discover(); 
}

void discover()
{
  message = "searching...";
  mBt = new MBt(this);
  mBt.discoverServices(uuid);
}

void receive()
{
  mObexServer = new MObexServer(this,uuid);
  mObexServer.start();
}

void libraryEvent(Object library, int event, Object data)
{
  println(data);
  if(library == mBt)
  {
    switch(event)
    {
    case MBt.EVENT_DISCOVER_SERVICE_COMPLETED:
      serviceList.services = (MService[]) data;
      serviceList.show();
      message = length(serviceList.services) + " Services Found";
      break;
    }
  }
  else if(library == mObexServer)
  {
    switch(event)
    {
    case MObex.EVENT_ERROR :
      message = "Error" + (String) data;
      break;
    case MObex.EVENT_ON_PUT :
      message = (String) data;
      break;
    }
  }
  else if(library instanceof MObexClient)
  {
    switch(event)
    {
    case MObex.EVENT_ERROR :
      message = "Error" + (String) data;
      break;
    }
  }
}

void sendText(MService service)
{
  mObexClient = new MObexClient(this,service);
  int responseCode = mObexClient.put("Hello World");
  message = "ResponseCode " + responseCode;
  mObexClient.close();
}

class ServiceList
{
  MService[] services;  

  int x;
  int y;
  int selected;

  PFont pFont = loadFont();

  boolean visible;

  void draw()
  {
    if(!visible)
      return;

    textAlign(LEFT);

    int tmpX = x;
    int tmpY = y;

    for(int i=0; i<services.length; i++)
    {
      if(i == selected)
        fill(0x00FF00);
      else
        fill(0xFFFFFF);

      MService service = services[i]; 
      MDevice device = service.device;

      text(device.name(),tmpX,tmpY);
      tmpY += pFont.height;
    }
  }

  void show()
  {
    visible = true;
  }

  void keyPressed()
  {
    if(!visible)
      return;

    switch(keyCode)    
    {
    case DOWN :
      if(selected + 1 < length(services))
        selected++;
      break;
    case UP :
      if(selected > 0)
        selected--;
      break;
    case FIRE:
      sendText(services[selected]);
    }
  }
}
