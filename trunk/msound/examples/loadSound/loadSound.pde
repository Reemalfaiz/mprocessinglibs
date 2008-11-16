import mjs.processing.mobile.msound.*;

MSound soundA; 
 
void setup() { 
  soundA = MSoundManager.loadSound("bong.wav"); 
  soundA.loop(); 
} 
 
//void draw() { 
  // The blank draw() is not necessary to let the sound play 
//} 
