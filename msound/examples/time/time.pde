import mjs.processing.mobile.msound.*;

MSound mySound; 
 
void setup() { 
  mySound = MSoundManager.loadSound("bong.wav"); 
  mySound.play(); 
} 
 
void draw() { 
  println("" + mySound.time()); 
} 
