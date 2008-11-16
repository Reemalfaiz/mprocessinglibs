/**
 * Solitary Soul
 * 
 * This solitary soul plays some tones when the user press the keypad,
 * change volumen or duration, also draws a little animation when the 
 * tone is been playing.
 *
 * Press keys : 
 * 1,2,3,4,5,6,8 to play notes C,D,E,F,G,A,B
 * 7,9 to change the duration of each note
 * #,* to change the volume
 * 0 to obtain help info
 *
 * @author Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.1 (November - 2005) Library Rename - Mobile Processing 0001 Compatible
 * 1.0 (September - 2005) Initial Release 
 */

import javax.microedition.lcdui.*;

import mjs.processing.mobile.msound.*;

// Scale, Central DO (C4)
int c4 = 60;
int d4 = c4 + 2; // add tone
int e4 = d4 + 2;
int f4 = e4 + 1; // add semi tone
int g4 = f4 + 2;
int a4 = g4 + 2;
int b4 = a4 + 2;

// Note to play
int note;
int animationNote;

// Current Values 
int volume = 50;
int duration = 500;

// Background Color
int bgColor = 200;

// Max Animation Size (Square Side Size)
int animationMaxSize;

// Is help visible
boolean helpVisible;

// Set background and draw volume and duration controls
void setup()
{
  background(bgColor);
  
  drawVolume();
  drawDuration();
  
  // Set default font
  textFont(new PFont(Font.getDefaultFont()));
  textAlign(CENTER);
  
  // Square Size is equal to min size between width and height
  animationMaxSize = (width > height ? height : width) / 2;
}

// Draw note animation
void draw()
{
  drawNotes();  
}

// Draws the volumen control
void drawVolume()
{
  if(helpVisible)
    hideHelp();

  // Hide old control  
  stroke(bgColor);
  fill(bgColor);
  rect(10,0,10,height);

  // Draw control
  stroke(0); 
  fill(bgColor + 20);
  rect(10,height/2 - volume/2,10,volume);
}

// Show Duration Control
void drawDuration()
{
  if(helpVisible)
    hideHelp();

  // Hide old control
  stroke(bgColor);  
  fill(bgColor);
  rect(width - 20,0,10,height);
  
  // Draw Control
  stroke(0);  
  fill(bgColor - 20);

  int rectHeight = duration/10;
  rect(width - 20,width/2 - rectHeight/2,10,rectHeight);
}

// Draw note animation, draw a square more little each time
void drawNotes()
{
  // If the animation is over, do nothing
  if(animationNote == 0)
    return;
  
  // Hide all square
  stroke(bgColor);
  noFill();
  rect(width/2 - animationNote/2,height/2 - animationNote/2,animationNote,animationNote);
    
  animationNote--;

  // Draw new square  
  stroke(30);
  noFill();
  rect(width/2 - animationNote/2,height/2 - animationNote/2,animationNote,animationNote);
}

// Draw the instructions to interact with the application
void drawHelp()
{
  fill(0);
  
  int lineSpace = height/7;
  int y = lineSpace;
  text("Solitary Soul Version 1.0",width/2,y);  
  y += lineSpace;
  text("Help",width/2,y);
  y += lineSpace;
  text("1,2,3,4,5,6,8 Notes",width/2,y);
  y += lineSpace;
  text("7,9 Duration",width/2,y);
  y += lineSpace;
  text("*,# Volume",width/2,y);
  y += lineSpace;
  text("http://marlonj.darkgreenmedia.com",width/2,y);  
  
  // The help is visible rigth now
  helpVisible = true;
}

// Clear the screen and draw controls
void hideHelp()
{
  helpVisible = false;
  
  background(bgColor);
  
  drawVolume();
  drawDuration();
}

// Set the note value and update animation
void setNote(int noteValue)
{
  if(helpVisible)
    hideHelp();
    
  animationNote = animationMaxSize;
  note = noteValue;
}

// Change the volume value, checks if volume are between 0 and 100
void setVolume(int volumeValue)
{
  // if the value is correct update volume control
  if(volumeValue >= 0 && volumeValue <= 100)
  {
    volume = volumeValue;
    drawVolume();
  }
}

// Change the duration value, checks if duration are mayor than 0 and less than height
void setDuration(int durationValue)
{
  // If the value is correct update duration control
  if(durationValue > 0 && durationValue/10 <= height)
  {
    duration = durationValue;
    drawDuration();
  }
}

// If the user press any key, play a tone, update controls or draw help
void keyPressed()
{
  switch(key)
  {
    // Play a different note for any key
    case '1' : setNote(c4); break;
    case '2' : setNote(d4); break;
    case '3' : setNote(e4); break;
    case '4' : setNote(f4); break;
    case '5' : setNote(g4); break;
    case '6' : setNote(a4); break;
    case '8' : setNote(b4); break;
    
    // Update Duration
    case '7' : setDuration(duration - 100); return;
    case '9' : setDuration(duration + 100); return;
    
    // Update Volume
    case '#' : setVolume(volume + 10); return;
    case '*' : setVolume(volume - 10); return;
    
    // Show Help
    case '0' : drawHelp(); return;
    
    // Other key, do nothing
    default : return;
  }
  
  // Play a tone with the current note, duration and volume
  MSoundManager.playTone(note,duration,volume);
}
