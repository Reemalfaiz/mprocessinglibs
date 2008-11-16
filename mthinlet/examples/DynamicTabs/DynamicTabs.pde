/**
 * DynamicTabs Example
 *
 * This example shows how to load a tab when a menu option
 * is pressed.
 *
 * This example is a port of the DynamicTabs example available at :
 * http://thinlet.blog-city.com/tutorial_2_dynamic_tabs.htm
 *
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
  thinlet.load("tabExample.xml");
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
  
  if(invoke.action.equals("menuOpen"))
    addTextTab();
}

public void addTextTab()
{
  Object newTab = thinlet.parse("texttab.xml");
  Object tabParent = thinlet.find("tabparent");
  
  if(tabParent != null)
  {
    thinlet.setString(newTab,"text","Text File");
    thinlet.add(tabParent,newTab);
  }
}
