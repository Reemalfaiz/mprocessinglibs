/**
 * Calculator Example
 *
 * This example shows how to load a simple UI and make some
 * actions.
 *
 * This example is a port of the Calculator example available at :
 * http://javaboutique.internet.com/tutorials/thinletone
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
}

void keyPressed()
{
  thinlet.load("calculator.xml");
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
  
  if(invoke.action.equals("display")) 
    display(invoke.args);
  else if(invoke.action.equals("clear"))
    clear(invoke.args);
}

void display(Object[] args)
{
  String s = thinlet.getString(args[1],"text");
  String t = thinlet.getString(args[0],"name");
  
  thinlet.setString(args[1],"text",s+t);
}

void clear(Object[] args)
{
  thinlet.setString(args[1],"text","");
}
