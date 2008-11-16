import mjs.processing.mobile.mbt.*;
import mjs.processing.mobile.mmediaserver.*;

MBt mBt;
MMediaServer mMediaServer;

String message;

void setup()
{
  textFont(createFont("monospaced",12));
  textAlign(CENTER);
  
  mBt = new MBtP(this);
  mMediaServer = new MMediaServerP(this,mBt);
  
  message = "Press Mouse to Start";
}

void draw()
{
  background(0);
  
  if(message != null)
    text(message,width/2,height/2);
}

void mousePressed()
{
  mMediaServer.start();
}

void libraryEvent(Object library, int event, Object data)
{
  if(library == mMediaServer)
  {
    switch(event)
    {
      case MMediaServer.EVENT_ERROR : 
      println(data);
        message = (String) data;
        break;

      case MMediaServer.EVENT_SERVER_STARTED : 
        message = "Server Started";
        break;        

      case MMediaServer.EVENT_SERVER_STOPPED : 
        message = "Server Stopped";
        break;        
        
      case MMediaServer.EVENT_CLIENT_AVAILABLE : 
        message = "Client Connected";
        break;        

      case MMediaServer.EVENT_CLIENT_DISPATCHED : 
        message = "Client Dispatched";
        break;        
    }
  }
}

void exit()
{
  mMediaServer.stop();
  super.exit();
}
