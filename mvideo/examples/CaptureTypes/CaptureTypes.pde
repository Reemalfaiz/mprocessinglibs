import mjs.processing.mobile.mvideo.*;

String[] types = new String[0];

void setup()
{
  background(0);
  textFont(loadFont());
  textAlign(CENTER);
   
  types = MVideoManager.supportedCaptureTypes();
}

void draw()
{
  translate(width/2,height/2);
  int y = 30;
  
  text("Supported Capture Types :",0,0);
  
  for(int i=0; i<types.length; i++)
  {
    text(types[i],0,y);
    y += 15;
  }
}

