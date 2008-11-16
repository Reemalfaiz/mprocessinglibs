import mjs.processing.mobile.mtinyline2d.*;

boolean smoothValue = true;

PCanvas originalCanvas;
MTinyLine2d tinyLine;

void setup()
{
  tinyLine = new MTinyLine2d(this);
  
  originalCanvas = canvas;
  canvas = tinyLine.canvas();
  
  tinyLine.smooth();
  
  background(255);
  
  strokeWeight(1);
  fill(255,0,0);
  rect(30,30,100,100);
  
  fill(255,255,0);
  rect(100,100,30,30);
  
  fill(0,255,255);
  ellipse(50,50,30,30);
}

void keyPressed()
{
  if(key == '5')
  {
      smoothValue = !smoothValue;
      
      if(smoothValue)
        tinyLine.smooth();
      else
        tinyLine.noSmooth();      
  }
  
  if(key == '1')
    if(canvas == originalCanvas)
      canvas = tinyLine.canvas();
    else
      canvas = originalCanvas;
}

