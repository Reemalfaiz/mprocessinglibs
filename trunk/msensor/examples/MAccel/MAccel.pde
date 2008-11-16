/**
 * Draw acceleration information at screen
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.0 (January - 2008) Initial Release  
 *
 * $Id: MAccel.pde 350 2008-01-08 07:25:08Z marlonj $
 */
import mjs.processing.mobile.msensor.*;

// Message to show 
String message = "Press Any Key To Start";

/*
 * Init the application, and check library support 
 */
void setup()
{
  // Set text properties
  textFont(loadFont());
  textAlign(CENTER);   
  
  // Check if the library is supported 
  if(!MSensorManager.supported())
    message = "MSensor Not Supported";  
}

/*
 * Draw the message and accelerometer information 
 */
void draw()
{
  // Clear the screen and update information
  background(0);
  text(message,width/2,height/2);
}

/*
 * Listen keyboard events 
 */
void keyPressed()
{
  // Create sensor manager 
  MSensorManager manager = new MSensorManager(this);
  
  // Get all accelerometer sensor,
  // almost one must exists 
  MSensor[] sensors = manager.findSensors("acceleration");
  
  // Check if the sensor available
  if(length(sensors) == 0)
    message = "NO Sensor Available";
  else  
  {
    // Get sensor and open connection 
    MSensor sensor = (MAccelerometer) sensors[0];  
    sensor.open();  
  }
}

/*
 * Check library events 
 */
void libraryEvent(Object library, int event, Object data)
{
  // Check data form sensors 
  if(library instanceof MSensor)
  {
    // Only one sensor is open and is accelerometer
    MAccelerometer sensor = (MAccelerometer) library;
    
    // Draw accelerometer values 
    message = "X: " + sensor.accelX() 
      + "Y: " + sensor.accelY() 
      + "Z: " + sensor.accelZ();
  }
}
