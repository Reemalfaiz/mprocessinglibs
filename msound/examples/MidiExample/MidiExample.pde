/**
 * Midi Drummer
 *
 * A ball that hits the wall produce a note
 *
 * @author Mary Jane Soft - Marlon J. Manrique
 * 
 * Changes :
 * 1.0 (December - 2005) Initial Release  
 */

import mjs.processing.mobile.msound.*;
import javax.microedition.lcdui.*;

// Midi System
MMidi midi; 

/// Ball Position and increment
int x,y,dx,dy;

void setup()
{
  // Init Midi System
  midi = new MMidi(); 
  
  // Set text attributes
  textFont(new PFont(Font.getDefaultFont())); 
  textAlign(CENTER);

  // Init ball position and increment
  x = width/2;
  y = height/2;
  dx = dy = 5;
  
  // Set animation speed
  framerate(15);
}

void draw()
{
  // Clear the scene
  background(0);
  
  // If ball is out of screen change direction
  // and play note
  
  if(x < 0 || x > width)
  {
    dx *= -1;
    playNote();
  }
    
  if(y < 0 || y > height)
  {
    dy *= -1;
    playNote();
  }
  
  // Change ball position  
  x += dx;
  y += dy;
  
  // Draw an ellipse
  ellipse(x,y,10,10);
}

// Play a random note
void playNote()
{
  midi.noteOn(0,random(127),100);
}
