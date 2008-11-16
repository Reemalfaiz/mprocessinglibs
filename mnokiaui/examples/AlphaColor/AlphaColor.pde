import mjs.processing.mobile.mnokiaui.*;

// This example show how to use the MNokiaUI 
// Alpha Color Channel Support

// The NokiaUI Library support
MNokiaUI nokiaUI;

void setup()
{
  // Create the NokiaUI graphics context
  nokiaUI = new MNokiaUI(this);
}

void draw()
{
  // Set color background
  background(200);
  
  // Move to the center of the screen
  translate(width/2,height/2);
  
  // Draw Red Green Blue Ellipse with little 
  // intersection
  fill(color(255,0,0,128));
  ellipse(20,-20,70,70);
  fill(color(0,255,0,128));
  ellipse(-20,-20,70,70);
  fill(color(0,0,255,128));    
  ellipse(0,20,70,70);
}
