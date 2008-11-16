import mjs.processing.mobile.mvideo.*;

PImage pImage;
MCapture capture;

void setup()
{
  capture = new MCapture(this);
  capture.show(5,5,50,50);
  capture.play();  
}

void draw()
{
  if(pImage != null)
    image(pImage,5,5);
}

void keyPressed()
{
  if(key == '5')
    pImage = capture.snapshot();
}

