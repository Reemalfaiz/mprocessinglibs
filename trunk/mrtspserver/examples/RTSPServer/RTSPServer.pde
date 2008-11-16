import mjs.processing.mobile.mrtspserver.*;

String message;
MRTSPServer mRTSPServer;

void setup()
{
  textFont(loadFont());
  textAlign(CENTER);
  
  mRTSPServer = 
    new MRTSPServer(this,MRTSPServer.ALTERNATE_PORT);
    
  message = "Press Any Key to Start Server";
}

void draw()
{
  background(0);
  
  if(message != null)
    text(message,width/2,height/2);
}

void keyPressed()
{
  mRTSPServer.start();
}

void libraryEvent(Object library, int event, Object data)
{
  if(library == mRTSPServer)
  {
    switch(event)
    {
      case MRTSPServer.EVENT_ERROR : 
        message = "Error : " + (String) data;
        break;

      case MRTSPServer.EVENT_SERVER_STARTED : 
        message = "Server Started";
        break;        

      case MRTSPServer.EVENT_SERVER_STOPPED : 
        message = "Server Stopped";
        break;        
        
      case MRTSPServer.EVENT_CLIENT_AVAILABLE : 
        message = "Client Connected";
        break;        

      case MRTSPServer.EVENT_CLIENT_DISPATCHED : 
        message = "Client Dispatched";
        break;        
    }
  }
}

void destroy()
{
  mRTSPServer.stop();
}
