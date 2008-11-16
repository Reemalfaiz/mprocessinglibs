import mjs.processing.mobile.mpush.*;

void setup()
{  
  // Execute this application in 10 minutes from now
  MPush mPush = new MPush(this);
  mPush.executeIn(1000 * 60 *10);
  
  // Set font
  textFont(loadFont());
  textAlign(CENTER); 
}

void draw()
{
  background(0);  
  translate(width/2,height/2);  
  text("Thanks for Execute Me !!!",0,0);
  text("If the application is closed",0,15);
  text("will be execute in 10 minutes",0,30);
}
