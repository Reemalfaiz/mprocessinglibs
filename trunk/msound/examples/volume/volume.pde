import mjs.processing.mobile.msound.*;

MSound mySound; 
int volume = 100;
 
void setup() { 
  mySound = MSoundManager.loadSound("bong.wav"); 
  mySound.loop(); 
} 
 
void draw() { 
  // Sets the volume to 0 (silent) when the 
  // mouse is on the left edge of the window 
  // and to 100 (maximum) when the mouse is on 
  // the right edge of the window. 
  // mySound.volume(float(mouseX)/width); 
}

void keyPressed()
{
  if(key == '*')
    volume += 10;
  
  if(key == '#')
    volume -= 10;
  
  mySound.volume(volume);
}
