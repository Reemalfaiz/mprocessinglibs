/**
 * A Sphere 
 *
 * @author Seltar
 * http://seltar.wliia.org/
 */

import mjs.processing.mobile.m3d.*;

M3d m3d; // 3D System Manager
MMesh geosphere; // 3D Object
int Scale = 2;
int R1 = 0, R2 = 255;
int G1 = 0, G2 = 255;
int B1 = 0, B2 = 255;

void setup()
{
  // Create the 3D graphics system
  m3d = new M3d(this);

  // Set the current graphics context
  canvas = m3d.canvas();

  // Set the origin at center of the screen
  translate(width/2,height/2);

  // Set background to black
  background(0);  

  // Create a geosphere
  makeGeosphere();

  // Set the light
  m3d.omniLight(255,255,255,255,-128,255);  

  // Add vertex, and fill the vertex with different colors

  // Add the cube to the scene
  m3d.add(geosphere);

  framerate(30);
}

void draw()
{
  m3d.box(3);
  geosphere.rotateX(3.1416f/100);    
  geosphere.rotateY(3.1416f/100);    
}

void keyPressed()
{
  if(key == '4')
    geosphere.translate(-1,0,0);

  if(key == '6')
    geosphere.rotateY(-3.1416f/10);

  if(key == '2')
    geosphere.rotateX(3.1416f/10);

  if(key == '8')
    geosphere.rotateX(-3.1416f/10);
}


void makeGeosphere()
{
  geosphere = new MMesh(); 
  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(30*Scale,0*Scale,0*Scale);
  geosphere.vertex(27*Scale,0*Scale,11*Scale);
  geosphere.vertex(27*Scale,11*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(27*Scale,0*Scale,11*Scale);
  geosphere.vertex(21*Scale,0*Scale,21*Scale);
  geosphere.vertex(24*Scale,12*Scale,12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(27*Scale,11*Scale,0*Scale);
  geosphere.vertex(27*Scale,0*Scale,11*Scale);
  geosphere.vertex(24*Scale,12*Scale,12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(27*Scale,11*Scale,0*Scale);
  geosphere.vertex(24*Scale,12*Scale,12*Scale);
  geosphere.vertex(21*Scale,21*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(21*Scale,0*Scale,21*Scale);
  geosphere.vertex(11*Scale,0*Scale,27*Scale);
  geosphere.vertex(12*Scale,12*Scale,24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(11*Scale,0*Scale,27*Scale);
  geosphere.vertex(0*Scale,0*Scale,30*Scale);
  geosphere.vertex(0*Scale,11*Scale,27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,12*Scale,24*Scale);
  geosphere.vertex(11*Scale,0*Scale,27*Scale);
  geosphere.vertex(0*Scale,11*Scale,27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,12*Scale,24*Scale);
  geosphere.vertex(0*Scale,11*Scale,27*Scale);
  geosphere.vertex(0*Scale,21*Scale,21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(21*Scale,21*Scale,0*Scale);
  geosphere.vertex(24*Scale,12*Scale,12*Scale);
  geosphere.vertex(12*Scale,24*Scale,12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(24*Scale,12*Scale,12*Scale);
  geosphere.vertex(21*Scale,0*Scale,21*Scale);
  geosphere.vertex(12*Scale,12*Scale,24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,24*Scale,12*Scale);
  geosphere.vertex(24*Scale,12*Scale,12*Scale);
  geosphere.vertex(12*Scale,12*Scale,24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,24*Scale,12*Scale);
  geosphere.vertex(12*Scale,12*Scale,24*Scale);
  geosphere.vertex(0*Scale,21*Scale,21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(21*Scale,21*Scale,0*Scale);
  geosphere.vertex(12*Scale,24*Scale,12*Scale);
  geosphere.vertex(11*Scale,27*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,24*Scale,12*Scale);
  geosphere.vertex(0*Scale,21*Scale,21*Scale);
  geosphere.vertex(0*Scale,27*Scale,11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(11*Scale,27*Scale,0*Scale);
  geosphere.vertex(12*Scale,24*Scale,12*Scale);
  geosphere.vertex(0*Scale,27*Scale,11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(11*Scale,27*Scale,0*Scale);
  geosphere.vertex(0*Scale,27*Scale,11*Scale);
  geosphere.vertex(0*Scale,30*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,30*Scale,0*Scale);
  geosphere.vertex(0*Scale,27*Scale,11*Scale);
  geosphere.vertex(-11*Scale,27*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,27*Scale,11*Scale);
  geosphere.vertex(0*Scale,21*Scale,21*Scale);
  geosphere.vertex(-12*Scale,24*Scale,12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-11*Scale,27*Scale,0*Scale);
  geosphere.vertex(0*Scale,27*Scale,11*Scale);
  geosphere.vertex(-12*Scale,24*Scale,12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-11*Scale,27*Scale,0*Scale);
  geosphere.vertex(-12*Scale,24*Scale,12*Scale);
  geosphere.vertex(-21*Scale,21*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,21*Scale,21*Scale);
  geosphere.vertex(0*Scale,11*Scale,27*Scale);
  geosphere.vertex(-12*Scale,12*Scale,24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,11*Scale,27*Scale);
  geosphere.vertex(0*Scale,0*Scale,30*Scale);
  geosphere.vertex(-11*Scale,0*Scale,27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,12*Scale,24*Scale);
  geosphere.vertex(0*Scale,11*Scale,27*Scale);
  geosphere.vertex(-11*Scale,0*Scale,27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,12*Scale,24*Scale);
  geosphere.vertex(-11*Scale,0*Scale,27*Scale);
  geosphere.vertex(-21*Scale,0*Scale,21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-21*Scale,21*Scale,0*Scale);
  geosphere.vertex(-12*Scale,24*Scale,12*Scale);
  geosphere.vertex(-24*Scale,12*Scale,12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,24*Scale,12*Scale);
  geosphere.vertex(0*Scale,21*Scale,21*Scale);
  geosphere.vertex(-12*Scale,12*Scale,24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-24*Scale,12*Scale,12*Scale);
  geosphere.vertex(-12*Scale,24*Scale,12*Scale);
  geosphere.vertex(-12*Scale,12*Scale,24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-24*Scale,12*Scale,12*Scale);
  geosphere.vertex(-12*Scale,12*Scale,24*Scale);
  geosphere.vertex(-21*Scale,0*Scale,21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-21*Scale,21*Scale,0*Scale);
  geosphere.vertex(-24*Scale,12*Scale,12*Scale);
  geosphere.vertex(-27*Scale,11*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-24*Scale,12*Scale,12*Scale);
  geosphere.vertex(-21*Scale,0*Scale,21*Scale);
  geosphere.vertex(-27*Scale,0*Scale,11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-27*Scale,11*Scale,0*Scale);
  geosphere.vertex(-24*Scale,12*Scale,12*Scale);
  geosphere.vertex(-27*Scale,0*Scale,11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-27*Scale,11*Scale,0*Scale);
  geosphere.vertex(-27*Scale,0*Scale,11*Scale);
  geosphere.vertex(-30*Scale,0*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-30*Scale,0*Scale,0*Scale);
  geosphere.vertex(-27*Scale,0*Scale,11*Scale);
  geosphere.vertex(-27*Scale,-11*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-27*Scale,0*Scale,11*Scale);
  geosphere.vertex(-21*Scale,0*Scale,21*Scale);
  geosphere.vertex(-24*Scale,-12*Scale,12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-27*Scale,-11*Scale,0*Scale);
  geosphere.vertex(-27*Scale,0*Scale,11*Scale);
  geosphere.vertex(-24*Scale,-12*Scale,12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-27*Scale,-11*Scale,0*Scale);
  geosphere.vertex(-24*Scale,-12*Scale,12*Scale);
  geosphere.vertex(-21*Scale,-21*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-21*Scale,0*Scale,21*Scale);
  geosphere.vertex(-11*Scale,0*Scale,27*Scale);
  geosphere.vertex(-12*Scale,-12*Scale,24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-11*Scale,0*Scale,27*Scale);
  geosphere.vertex(0*Scale,0*Scale,30*Scale);
  geosphere.vertex(0*Scale,-11*Scale,27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,-12*Scale,24*Scale);
  geosphere.vertex(-11*Scale,0*Scale,27*Scale);
  geosphere.vertex(0*Scale,-11*Scale,27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,-12*Scale,24*Scale);
  geosphere.vertex(0*Scale,-11*Scale,27*Scale);
  geosphere.vertex(0*Scale,-21*Scale,21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-21*Scale,-21*Scale,0*Scale);
  geosphere.vertex(-24*Scale,-12*Scale,12*Scale);
  geosphere.vertex(-12*Scale,-24*Scale,12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-24*Scale,-12*Scale,12*Scale);
  geosphere.vertex(-21*Scale,0*Scale,21*Scale);
  geosphere.vertex(-12*Scale,-12*Scale,24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,-24*Scale,12*Scale);
  geosphere.vertex(-24*Scale,-12*Scale,12*Scale);
  geosphere.vertex(-12*Scale,-12*Scale,24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,-24*Scale,12*Scale);
  geosphere.vertex(-12*Scale,-12*Scale,24*Scale);
  geosphere.vertex(0*Scale,-21*Scale,21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-21*Scale,-21*Scale,0*Scale);
  geosphere.vertex(-12*Scale,-24*Scale,12*Scale);
  geosphere.vertex(-11*Scale,-27*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,-24*Scale,12*Scale);
  geosphere.vertex(0*Scale,-21*Scale,21*Scale);
  geosphere.vertex(0*Scale,-27*Scale,11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-11*Scale,-27*Scale,0*Scale);
  geosphere.vertex(-12*Scale,-24*Scale,12*Scale);
  geosphere.vertex(0*Scale,-27*Scale,11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-11*Scale,-27*Scale,0*Scale);
  geosphere.vertex(0*Scale,-27*Scale,11*Scale);
  geosphere.vertex(0*Scale,-30*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,-30*Scale,0*Scale);
  geosphere.vertex(0*Scale,-27*Scale,11*Scale);
  geosphere.vertex(11*Scale,-27*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,-27*Scale,11*Scale);
  geosphere.vertex(0*Scale,-21*Scale,21*Scale);
  geosphere.vertex(12*Scale,-24*Scale,12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(11*Scale,-27*Scale,0*Scale);
  geosphere.vertex(0*Scale,-27*Scale,11*Scale);
  geosphere.vertex(12*Scale,-24*Scale,12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(11*Scale,-27*Scale,0*Scale);
  geosphere.vertex(12*Scale,-24*Scale,12*Scale);
  geosphere.vertex(21*Scale,-21*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,-21*Scale,21*Scale);
  geosphere.vertex(0*Scale,-11*Scale,27*Scale);
  geosphere.vertex(12*Scale,-12*Scale,24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,-11*Scale,27*Scale);
  geosphere.vertex(0*Scale,0*Scale,30*Scale);
  geosphere.vertex(11*Scale,0*Scale,27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,-12*Scale,24*Scale);
  geosphere.vertex(0*Scale,-11*Scale,27*Scale);
  geosphere.vertex(11*Scale,0*Scale,27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,-12*Scale,24*Scale);
  geosphere.vertex(11*Scale,0*Scale,27*Scale);
  geosphere.vertex(21*Scale,0*Scale,21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(21*Scale,-21*Scale,0*Scale);
  geosphere.vertex(12*Scale,-24*Scale,12*Scale);
  geosphere.vertex(24*Scale,-12*Scale,12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,-24*Scale,12*Scale);
  geosphere.vertex(0*Scale,-21*Scale,21*Scale);
  geosphere.vertex(12*Scale,-12*Scale,24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(24*Scale,-12*Scale,12*Scale);
  geosphere.vertex(12*Scale,-24*Scale,12*Scale);
  geosphere.vertex(12*Scale,-12*Scale,24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(24*Scale,-12*Scale,12*Scale);
  geosphere.vertex(12*Scale,-12*Scale,24*Scale);
  geosphere.vertex(21*Scale,0*Scale,21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(21*Scale,-21*Scale,0*Scale);
  geosphere.vertex(24*Scale,-12*Scale,12*Scale);
  geosphere.vertex(27*Scale,-11*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(24*Scale,-12*Scale,12*Scale);
  geosphere.vertex(21*Scale,0*Scale,21*Scale);
  geosphere.vertex(27*Scale,0*Scale,11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(27*Scale,-11*Scale,0*Scale);
  geosphere.vertex(24*Scale,-12*Scale,12*Scale);
  geosphere.vertex(27*Scale,0*Scale,11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(27*Scale,-11*Scale,0*Scale);
  geosphere.vertex(27*Scale,0*Scale,11*Scale);
  geosphere.vertex(30*Scale,0*Scale,0*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(30*Scale,0*Scale,0*Scale);
  geosphere.vertex(27*Scale,11*Scale,0*Scale);
  geosphere.vertex(27*Scale,0*Scale,-11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(27*Scale,11*Scale,0*Scale);
  geosphere.vertex(21*Scale,21*Scale,0*Scale);
  geosphere.vertex(24*Scale,12*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(27*Scale,0*Scale,-11*Scale);
  geosphere.vertex(27*Scale,11*Scale,0*Scale);
  geosphere.vertex(24*Scale,12*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(27*Scale,0*Scale,-11*Scale);
  geosphere.vertex(24*Scale,12*Scale,-12*Scale);
  geosphere.vertex(21*Scale,0*Scale,-21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(21*Scale,21*Scale,0*Scale);
  geosphere.vertex(11*Scale,27*Scale,0*Scale);
  geosphere.vertex(12*Scale,24*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(11*Scale,27*Scale,0*Scale);
  geosphere.vertex(0*Scale,30*Scale,0*Scale);
  geosphere.vertex(0*Scale,27*Scale,-11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,24*Scale,-12*Scale);
  geosphere.vertex(11*Scale,27*Scale,0*Scale);
  geosphere.vertex(0*Scale,27*Scale,-11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,24*Scale,-12*Scale);
  geosphere.vertex(0*Scale,27*Scale,-11*Scale);
  geosphere.vertex(0*Scale,21*Scale,-21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(21*Scale,0*Scale,-21*Scale);
  geosphere.vertex(24*Scale,12*Scale,-12*Scale);
  geosphere.vertex(12*Scale,12*Scale,-24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(24*Scale,12*Scale,-12*Scale);
  geosphere.vertex(21*Scale,21*Scale,0*Scale);
  geosphere.vertex(12*Scale,24*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,12*Scale,-24*Scale);
  geosphere.vertex(24*Scale,12*Scale,-12*Scale);
  geosphere.vertex(12*Scale,24*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,12*Scale,-24*Scale);
  geosphere.vertex(12*Scale,24*Scale,-12*Scale);
  geosphere.vertex(0*Scale,21*Scale,-21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(21*Scale,0*Scale,-21*Scale);
  geosphere.vertex(12*Scale,12*Scale,-24*Scale);
  geosphere.vertex(11*Scale,0*Scale,-27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,12*Scale,-24*Scale);
  geosphere.vertex(0*Scale,21*Scale,-21*Scale);
  geosphere.vertex(0*Scale,11*Scale,-27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(11*Scale,0*Scale,-27*Scale);
  geosphere.vertex(12*Scale,12*Scale,-24*Scale);
  geosphere.vertex(0*Scale,11*Scale,-27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(11*Scale,0*Scale,-27*Scale);
  geosphere.vertex(0*Scale,11*Scale,-27*Scale);
  geosphere.vertex(0*Scale,0*Scale,-30*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,30*Scale,0*Scale);
  geosphere.vertex(-11*Scale,27*Scale,0*Scale);
  geosphere.vertex(0*Scale,27*Scale,-11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-11*Scale,27*Scale,0*Scale);
  geosphere.vertex(-21*Scale,21*Scale,0*Scale);
  geosphere.vertex(-12*Scale,24*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,27*Scale,-11*Scale);
  geosphere.vertex(-11*Scale,27*Scale,0*Scale);
  geosphere.vertex(-12*Scale,24*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,27*Scale,-11*Scale);
  geosphere.vertex(-12*Scale,24*Scale,-12*Scale);
  geosphere.vertex(0*Scale,21*Scale,-21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-21*Scale,21*Scale,0*Scale);
  geosphere.vertex(-27*Scale,11*Scale,0*Scale);
  geosphere.vertex(-24*Scale,12*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-27*Scale,11*Scale,0*Scale);
  geosphere.vertex(-30*Scale,0*Scale,0*Scale);
  geosphere.vertex(-27*Scale,0*Scale,-11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-24*Scale,12*Scale,-12*Scale);
  geosphere.vertex(-27*Scale,11*Scale,0*Scale);
  geosphere.vertex(-27*Scale,0*Scale,-11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-24*Scale,12*Scale,-12*Scale);
  geosphere.vertex(-27*Scale,0*Scale,-11*Scale);
  geosphere.vertex(-21*Scale,0*Scale,-21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,21*Scale,-21*Scale);
  geosphere.vertex(-12*Scale,24*Scale,-12*Scale);
  geosphere.vertex(-12*Scale,12*Scale,-24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,24*Scale,-12*Scale);
  geosphere.vertex(-21*Scale,21*Scale,0*Scale);
  geosphere.vertex(-24*Scale,12*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,12*Scale,-24*Scale);
  geosphere.vertex(-12*Scale,24*Scale,-12*Scale);
  geosphere.vertex(-24*Scale,12*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,12*Scale,-24*Scale);
  geosphere.vertex(-24*Scale,12*Scale,-12*Scale);
  geosphere.vertex(-21*Scale,0*Scale,-21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,21*Scale,-21*Scale);
  geosphere.vertex(-12*Scale,12*Scale,-24*Scale);
  geosphere.vertex(0*Scale,11*Scale,-27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,12*Scale,-24*Scale);
  geosphere.vertex(-21*Scale,0*Scale,-21*Scale);
  geosphere.vertex(-11*Scale,0*Scale,-27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,11*Scale,-27*Scale);
  geosphere.vertex(-12*Scale,12*Scale,-24*Scale);
  geosphere.vertex(-11*Scale,0*Scale,-27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,11*Scale,-27*Scale);
  geosphere.vertex(-11*Scale,0*Scale,-27*Scale);
  geosphere.vertex(0*Scale,0*Scale,-30*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-30*Scale,0*Scale,0*Scale);
  geosphere.vertex(-27*Scale,-11*Scale,0*Scale);
  geosphere.vertex(-27*Scale,0*Scale,-11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-27*Scale,-11*Scale,0*Scale);
  geosphere.vertex(-21*Scale,-21*Scale,0*Scale);
  geosphere.vertex(-24*Scale,-12*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-27*Scale,0*Scale,-11*Scale);
  geosphere.vertex(-27*Scale,-11*Scale,0*Scale);
  geosphere.vertex(-24*Scale,-12*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-27*Scale,0*Scale,-11*Scale);
  geosphere.vertex(-24*Scale,-12*Scale,-12*Scale);
  geosphere.vertex(-21*Scale,0*Scale,-21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-21*Scale,-21*Scale,0*Scale);
  geosphere.vertex(-11*Scale,-27*Scale,0*Scale);
  geosphere.vertex(-12*Scale,-24*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-11*Scale,-27*Scale,0*Scale);
  geosphere.vertex(0*Scale,-30*Scale,0*Scale);
  geosphere.vertex(0*Scale,-27*Scale,-11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,-24*Scale,-12*Scale);
  geosphere.vertex(-11*Scale,-27*Scale,0*Scale);
  geosphere.vertex(0*Scale,-27*Scale,-11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,-24*Scale,-12*Scale);
  geosphere.vertex(0*Scale,-27*Scale,-11*Scale);
  geosphere.vertex(0*Scale,-21*Scale,-21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-21*Scale,0*Scale,-21*Scale);
  geosphere.vertex(-24*Scale,-12*Scale,-12*Scale);
  geosphere.vertex(-12*Scale,-12*Scale,-24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-24*Scale,-12*Scale,-12*Scale);
  geosphere.vertex(-21*Scale,-21*Scale,0*Scale);
  geosphere.vertex(-12*Scale,-24*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,-12*Scale,-24*Scale);
  geosphere.vertex(-24*Scale,-12*Scale,-12*Scale);
  geosphere.vertex(-12*Scale,-24*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,-12*Scale,-24*Scale);
  geosphere.vertex(-12*Scale,-24*Scale,-12*Scale);
  geosphere.vertex(0*Scale,-21*Scale,-21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-21*Scale,0*Scale,-21*Scale);
  geosphere.vertex(-12*Scale,-12*Scale,-24*Scale);
  geosphere.vertex(-11*Scale,0*Scale,-27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-12*Scale,-12*Scale,-24*Scale);
  geosphere.vertex(0*Scale,-21*Scale,-21*Scale);
  geosphere.vertex(0*Scale,-11*Scale,-27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-11*Scale,0*Scale,-27*Scale);
  geosphere.vertex(-12*Scale,-12*Scale,-24*Scale);
  geosphere.vertex(0*Scale,-11*Scale,-27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(-11*Scale,0*Scale,-27*Scale);
  geosphere.vertex(0*Scale,-11*Scale,-27*Scale);
  geosphere.vertex(0*Scale,0*Scale,-30*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,-30*Scale,0*Scale);
  geosphere.vertex(11*Scale,-27*Scale,0*Scale);
  geosphere.vertex(0*Scale,-27*Scale,-11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(11*Scale,-27*Scale,0*Scale);
  geosphere.vertex(21*Scale,-21*Scale,0*Scale);
  geosphere.vertex(12*Scale,-24*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,-27*Scale,-11*Scale);
  geosphere.vertex(11*Scale,-27*Scale,0*Scale);
  geosphere.vertex(12*Scale,-24*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,-27*Scale,-11*Scale);
  geosphere.vertex(12*Scale,-24*Scale,-12*Scale);
  geosphere.vertex(0*Scale,-21*Scale,-21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(21*Scale,-21*Scale,0*Scale);
  geosphere.vertex(27*Scale,-11*Scale,0*Scale);
  geosphere.vertex(24*Scale,-12*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(27*Scale,-11*Scale,0*Scale);
  geosphere.vertex(30*Scale,0*Scale,0*Scale);
  geosphere.vertex(27*Scale,0*Scale,-11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(24*Scale,-12*Scale,-12*Scale);
  geosphere.vertex(27*Scale,-11*Scale,0*Scale);
  geosphere.vertex(27*Scale,0*Scale,-11*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(24*Scale,-12*Scale,-12*Scale);
  geosphere.vertex(27*Scale,0*Scale,-11*Scale);
  geosphere.vertex(21*Scale,0*Scale,-21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,-21*Scale,-21*Scale);
  geosphere.vertex(12*Scale,-24*Scale,-12*Scale);
  geosphere.vertex(12*Scale,-12*Scale,-24*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,-24*Scale,-12*Scale);
  geosphere.vertex(21*Scale,-21*Scale,0*Scale);
  geosphere.vertex(24*Scale,-12*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,-12*Scale,-24*Scale);
  geosphere.vertex(12*Scale,-24*Scale,-12*Scale);
  geosphere.vertex(24*Scale,-12*Scale,-12*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,-12*Scale,-24*Scale);
  geosphere.vertex(24*Scale,-12*Scale,-12*Scale);
  geosphere.vertex(21*Scale,0*Scale,-21*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,-21*Scale,-21*Scale);
  geosphere.vertex(12*Scale,-12*Scale,-24*Scale);
  geosphere.vertex(0*Scale,-11*Scale,-27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(12*Scale,-12*Scale,-24*Scale);
  geosphere.vertex(21*Scale,0*Scale,-21*Scale);
  geosphere.vertex(11*Scale,0*Scale,-27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,-11*Scale,-27*Scale);
  geosphere.vertex(12*Scale,-12*Scale,-24*Scale);
  geosphere.vertex(11*Scale,0*Scale,-27*Scale);

  geosphere.fill(color((int)random(R1,R2),(int)random(G1,G2),(int)random(B1,B2)));
  geosphere.vertex(0*Scale,-11*Scale,-27*Scale);
  geosphere.vertex(11*Scale,0*Scale,-27*Scale);
  geosphere.vertex(0*Scale,0*Scale,-30*Scale);

  for(int i = 0; i < 128*3; i++)
    geosphere.index(i);
  for(int i = 0; i < 128; i++)
    geosphere.strip(3);
}
