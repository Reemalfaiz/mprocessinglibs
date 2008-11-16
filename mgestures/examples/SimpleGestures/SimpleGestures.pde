/**
 * This example shows how to recognize simple 
 * pointer gestures  
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 0.1 (January - 2008) Initial Release  
 *
 * $Id: SimpleGestures.pde 368 2008-01-26 19:46:42Z marlonj $
 */
 
import mjs.processing.mobile.mgestures.*;

// Gesture library
MGestures mGestures;

// Gesture recognize 
int gesture;

// Message to show 
String message = "Just Draw";

/*
 * Init the sketch 
 */
void setup()
{
  // Set font properties 
  textFont(loadFont());
  textAlign(CENTER);
  
  // Set colors 
  background(0);
  fill(0x00FF00);
  
  // Create gesture library 
  mGestures = new MGestures();
}

/* 
 * Draw message 
 */
void draw()
{
  text(message,width/2,height/2);
}

/* 
 * If pointer is pressed start capturing gesture 
 */
void pointerPressed()
{
  mGestures.start();
}
 
/*
 * If pointer dragged add the point to the gesture
 * and draw a green point 
 */
void pointerDragged()
{
  mGestures.addPoint(pointerX,pointerY);
  ellipse(pointerX,pointerY,5,5);  
}

/*
 * If pointer is release try to recognize the gesture 
 */
void pointerReleased()
{
  // Try to recognize the gesture 
  gesture = mGestures.recognize();
  
  // Get the gesture name 
  message = mGestures.name(gesture);
  
  // Clear the screen
  background(0);  
}
