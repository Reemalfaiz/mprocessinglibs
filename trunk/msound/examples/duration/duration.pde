import mjs.processing.mobile.msound.*;

MSound mySound; 
 
void setup() { 
  mySound = MSoundManager.loadSound("bong.wav"); 
  mySound.play(); 
  // Displays the length of the sound 
  // in the text area of the Processing 
  // environment 
  println("" + mySound.duration()); 
} 
 
//void draw() 
//{ 
  // The blank draw() is not necessary to let the sound play 
//} 
