/**
 * This example is a port of the example show at
 *  http://developers.sun.com/techtopics/mobility/midp/articles/game/index.html
 *
 * $Id: muTank.pde 202 2007-04-19 18:05:02Z marlonj $
 */

import mjs.processing.mobile.mgameii.*;

MGame game;
MTiledLayer tiledLayer;

TankSprite tank;

int angle;
int keyState;

void setup()
{
  game = new MGame(this);
  canvas = game.canvas();
  
  tiledLayer = new MTiledLayer(10,10,loadImage("board.png"),16,16);
  
  int[] map =
  {
       1,  1,  1,  1, 11,  0,  0,  0,  0,  0,
       0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
       0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
       0,  0,  0,  0,  9,  0,  0,  0,  0,  0,
       0,  0,  0,  0,  1,  0,  0,  0,  0,  0,
       0,  0,  0,  7,  1,  0,  0,  0,  0,  0,
       1,  1,  1,  1,  6,  0,  0,  0,  0,  0,
       0,  0,  0,  0,  0,  0,  0,  7, 11,  0,
       0,  0,  0,  0,  0,  0,  7,  6,  0,  0,
       0,  0,  0,  0,  0,  7,  6,  0,  0,  0
  };
  
  tiledLayer.map(map);
  
  tank = new TankSprite(loadImage("tank.png"),32,32);
  tank.setPosition(0,24);
  
  game.append(tank);  
  game.append(tiledLayer);
  
  framerate(30);
}

void draw()
{
  if(keyState == UP)
    tank.forward(2);
    
  if(keyState == DOWN)
    tank.forward(-2);    
    
 if(keyState == LEFT)
    tank.turn(-1);
    
 if(keyState == RIGHT)
    tank.turn(1);    
    
  if(tank.collidesWith(tiledLayer,true))
    tank.undo();
}

void keyPressed()
{
  keyState = keyCode;  
}

void keyReleased()
{
  keyState = 0;
}
