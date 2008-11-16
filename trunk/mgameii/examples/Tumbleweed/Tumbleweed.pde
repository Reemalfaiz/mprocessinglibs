/**
 * Mini Game Example
 *
 * $Id: Tumbleweed.pde 202 2007-04-19 18:05:02Z marlonj $
 */

import mjs.processing.mobile.mgameii.*;

MGame mGame;
MSprite cowboy;
MTiledLayer tiledLayer;
MSprite[] tumbleweeds;

int[] jumpSequence = { 0 };   
int[] cowboySequence = { 3, 2, 1, 2 };
int[] tileSequence = { 2, 3, 2, 4 };

int tileSequenceIndex;
int animatedTileIndex;

int x;
int dx = 1;
int dy = -4;
int numTumbleweeds = 4;
boolean left;
boolean jumping;

int score;

void setup()
{
   mGame = new MGame(this);
   canvas = mGame.canvas();

   createCowboy();   
   createTumbleweeds();   
   createTile();
   
   textFont(loadFont());
   
   framerate(30);
}

void draw()
{
  background(0);
  
  x += dx;
  
  relocateSprites();
  
  mGame.viewWindow(x,0,width,height);
  
  if(left)
    cowboy.move(-1,0);
  else
    cowboy.move(1,0);
  
  if(frameCount % 3 == 0)
  {
    cowboy.nextFrame();
        
    tiledLayer.setAnimatedTile(animatedTileIndex, tileSequence[tileSequenceIndex]);
    tileSequenceIndex++;
    tileSequenceIndex %= 4;
  }
  
  moveTumbleweeds();      
    
  if(jumping)
  {
    if(cowboy.getY() < height - 100)
      dy *= -1;
      
    if(cowboy.getY() > height - 50)
    {
      cowboy.setFrameSequence(cowboySequence);      
      jumping = false;
      dy *= -1;
    }
      
    cowboy.move(0,dy);
  }  
  
  text("" + score,width/2,20);
}

void keyPressed()
{
  if(keyCode == LEFT)
  {
    cowboy.setTransform(MSprite.TRANS_MIRROR);
    left = true;
    dx = -1;
  }

  if(keyCode == RIGHT)
  {
    cowboy.setTransform(MSprite.TRANS_NONE);
    left = false;
    dx = 1;    
  }
  
  if(keyCode == UP)
  {
   cowboy.setFrameSequence(jumpSequence);
   cowboy.setFrame(0);
   
   jumping = true;
  }
}

void createCowboy()
{
   cowboy = new MSprite(loadImage("cowboy.png"),32,48);
   cowboy.defineReferencePixel(32/2,0);
   cowboy.setFrameSequence(cowboySequence);
   cowboy.setPosition(width/2,height-50);
   
   mGame.append(cowboy);   
}

void createTile()
{
   int columns = (width/20 + 1)*3;
   
   tiledLayer = new MTiledLayer(columns,1,loadImage("grass.png"),20,20);
   tiledLayer.setPosition(0,height-20);
   
   animatedTileIndex = tiledLayer.createAnimatedTile(2);
   
   for(int i=0; i<columns; i++)
     if(i % 5 == 0 || i % 5 == 2)
       tiledLayer.setCell(i,0,animatedTileIndex);
     else
       tiledLayer.setCell(i,0,1);
       
   mGame.append(tiledLayer);       
}

void createTumbleweeds()
{
   MSprite tumbleweed = new MSprite(loadImage("tumbleweed.png"),16,16);
   tumbleweed.setPosition(width/2,height-50);
   
   tumbleweeds = new MSprite[numTumbleweeds];
   
   for(int i=0; i<numTumbleweeds; i++)
   {
     tumbleweeds[i] = new MSprite(tumbleweed);
     
     if(i%2 == 0)
       tumbleweeds[i].setPosition(-random(width),height-15);
     else
       tumbleweeds[i].setPosition(2*width + random(width),height-15);
       
     mGame.append(tumbleweeds[i]);
   } 
}

void moveTumbleweeds()
{
   int dx;
   
   for(int i=0; i<numTumbleweeds; i++)
   {
     if(i%2 == 0)
       dx = -2;
     else
       dx = 2;
       
     tumbleweeds[i].move(dx,0);
     tumbleweeds[i].nextFrame();
     
     int x = tumbleweeds[i].getX();
     
     if(x < -width)
       tumbleweeds[i].setPosition(2*width + random(width),height-15);
     else if(x > 3*width)
       tumbleweeds[i].setPosition(-width + random(width),height-15);
       
     if(cowboy.collidesWith(tumbleweeds[i],true))
     {
       score -= 1;
       tumbleweeds[i].setPosition((i%2)*3*width,0);
     }
   }
}

void relocateSprites()
{
  int dx = 0;
  
  if(x == width)
  {
    dx = -width;
    x = 0;
  }
  else if(x == 0)
  {
    dx = width;
    x = width;
  }
  
  cowboy.move(dx,0);

  for(int i=0; i<numTumbleweeds; i++)
    tumbleweeds[i].move(dx,0);
}
