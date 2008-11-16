import mjs.processing.mobile.msound.*;

MSound mySound; 
 
void setup() { 
  mySound = MSoundManager.loadSound("bong.wav"); 
  mySound.play(); 
} 
 
//void draw() 
//{ 
  // The blank draw() is not necessary to let the sound play 
//} 
