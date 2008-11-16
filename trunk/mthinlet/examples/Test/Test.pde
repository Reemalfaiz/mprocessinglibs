import mjs.processing.mobile.mthinlet.*;

MThinlet thinlet;

void setup()
{
  thinlet = new MThinlet(this);  
  thinlet.load("test01.xml");
  thinlet.show();  
}

void keyPressed()
{
  display.setCurrent(thinlet);
}
