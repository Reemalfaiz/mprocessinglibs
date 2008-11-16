/**
 * Show how create a 3D Sound Source
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.0 (July - 2006) Initial Release   
 *
 * $Id: Sound3D.pde 133 2006-07-14 22:59:24Z marlonj $
 */

// You have to import the MSound library
import mjs.processing.mobile.msound.*;
import mjs.processing.mobile.maudio3d.*;

int x,y; // Dog Screen Position
MSoundSource3D dogSound; // Dog 3D Sound Source
// Images
PImage dogImage, spectatorImage, soundImage, noSoundImage; 
boolean soundLoop; // Sound Loop
String instructions;

// Dog positions
final int L = 3, R = 4, C = 5, N = 1, F = 2;

void setup()
{
  // Init the Audio3D engine
  MAudio3D audio3d = new MAudio3D();
  
  // Load the sound
  dogSound = audio3d.createSource("largedog16k.wav",this);
  
  // Load the image
  dogImage = loadImage("dog.png");  
  spectatorImage = loadImage("spectator.png");  
  soundImage = loadImage("sound.png");  
  noSoundImage = loadImage("nosound.png");  
  
  // Set instructions
  instructions = "Sound3D\nPress 4,6,8,2\nto change Dog Position\nPress 0 to\nPlay or Stop Sound";
  
  textAlign(CENTER);
  textFont(loadFont());
}

void draw()
{
  // White Screen
  background(0xFFFFFF);
  
  // Draw Instructions
  fill(0x000000);
  text(instructions,0,0,width,height);  
  
  // Set the center of the screen
  translate(width/2,height/2);  
  
  // Draw the dog image
  image(dogImage,x-dogImage.width/2,y-dogImage.height/2);
  
  // Draw The Spectator
  image(spectatorImage,0,height/2 - spectatorImage.height - 2);  
  
  // Draw sound icon
  if(soundLoop)
    image(soundImage,width/2-soundImage.width,height/2 - spectatorImage.height);
  else
    image(noSoundImage,width/2-soundImage.width,height/2 - spectatorImage.height);      
}

// Move the dog 
void moveDog(int position)
{
  // 3D Coordinates
  int coorX = 0, coorY = 0, coorZ = 0;
  
  // Image Position
  x = width/2 - dogImage.width;
  y = height/2 - dogImage.height;  
  
  // Calculate 3D coords and image position
  switch(position)
  {
    case L : coorX = 1000; coorY = 0; coorZ = 0; 
                x *= -1; y = 0;
      break;
      
    case R : coorX = -1000; coorY = 0; coorZ = 0; 
               y = 0;
      break;
    
    case C : coorX = 0; coorY = 0; coorZ = 0; 
               x = 0; y = 0;
      break;
      
    case N : coorX = 0; coorY = 0; coorZ = 1000; 
                y *= -1; x = 0;
      break;
      
    case F : coorX = 0; coorY = 0; coorZ = -1000; 
                x = 0;
      break;
  }
  
  // Set 3d sound dog location
  dogSound.location(coorX,coorY,coorZ);
}

void keyPressed()
{
  // Play the sound or stop 
  if(key == '0')
  {
    if(soundLoop)
      dogSound.stop();
    else
      dogSound.loop();
      
    soundLoop = !soundLoop;
  }
  
  // Move the dog
  if(key == '5')
    moveDog(C);    
    
  if(key == '2')
    moveDog(N);
    
  if(key == '8')
    moveDog(F);
    
  if(key == '4')
    moveDog(L);
    
  if(key == '6')
    moveDog(R);
}
