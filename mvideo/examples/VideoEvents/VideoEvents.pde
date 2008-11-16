import mjs.processing.mobile.mvideo.*;

String events;
MCapture capture;

void setup()
{
  textFont(loadFont());
  textAlign(CENTER);
  
  capture = new MCapture(this);
  
  int videoWidth = capture.width();
  int videoHeight = capture.height();
  
  capture.location((width - videoWidth)/2,(height - videoHeight)/2);
}

void draw()
{
  background(0);
  text(events,width/2,height-20);
}

void keyPressed()
{
  if(key == '1')
    capture.hide();
  else if(key == '2')
    capture.show();
  else if(key == '3')
    capture.play();    
  else if(key == '4')
    capture.stop();    
  else if(key == '5')
    capture.close();    
}

// Change the status of the event at the screen
public void libraryEvent(Object library, int event, Object eventData)
{
  switch(event)
  {
    case MVideo.EVENT_STARTED : events = "Started"; break;
    case MVideo.EVENT_END_OF_MEDIA : events = "End of Media"; break;
    case MVideo.EVENT_STOPPED : events = "Stopped"; break;
    case MVideo.EVENT_ERROR : events = "Error"; break;
    case MVideo.EVENT_CLOSED : events = "Closed"; break;
  }  
}
