import mjs.processing.mobile.mimage.*;

MImage mImage;

void setup()
{
  mImage = new MImage(loadImage("thumb0.png"));
}

void draw()
{
  translate(width/2,height/2);
  image(mImage,-mImage.width/2,-mImage.height/2);
  
  updateImage();
}

void updateImage()
{
  int numPixels = mImage.pixels.length;
  
  for(int i=0; i<numPixels; i++)
    mImage.pixels[i] = ~mImage.pixels[i];
    
  mImage.updatePixels();
}
