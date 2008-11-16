/**
 * Calculator Example
 *
 * This example shows how to load a simple UI and make some
 * actions.
 *
 * This example is a port of the Calculator example available at :
 * http://thinlet.sourceforge.net/calculator.html
 */
 
import mjs.processing.mobile.mthinlet.*;

MThinlet thinlet;

void setup()
{
  noLoop();
  
  textFont(loadFont());
  textAlign(CENTER);
  fill(0);
  
  thinlet = new MThinlet(this);  
  thinlet.load("calculator.xml");
}

void keyPressed()
{
  thinlet.show();
}

void draw()
{
  translate(width/2,height/2);
  text("Press Any Key to Start",0,0);
}

void libraryEvent(Object library, int event, Object data)
{
  MInvoke invoke = (MInvoke) data;
  
  Object[] args = (Object[]) invoke.args;
  
  int number1 = toInt((String) args[0]);
  int number2 = toInt((String) args[1]);
  
  thinlet.setString(args[2],"text",str(number1+number2));
}
