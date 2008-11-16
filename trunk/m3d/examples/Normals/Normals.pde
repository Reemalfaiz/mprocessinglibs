/**
 * Using normals
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.1 (December - 2005) Library redesigned
 * 1.0 (November - 2005) Initial Release  
 */

import mjs.processing.mobile.m3d.*;

M3d m3d; // 3D System Manager
MMesh cube; // 3D Object

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
  
  // Create a cube 
  cube = new MMesh(); 
  
  // Set the light
  m3d.omniLight(255,255,255,250,-250,250);  

  // Add vertex, and fill the vertex with different colors
  cube.fill(0xFFFFFF);
  cube.normal(0,0,127);
  
  cube.vertex(-50,-50,50);
  cube.vertex(50,-50,50);
  cube.vertex(-50,50,50);
  cube.vertex(50,50,50);
  
  cube.fill(0xFF0000);    
  cube.normal(0,0,-128);  
  
  cube.vertex(50,-50,-50);
  cube.vertex(-50,-50,-50);
  cube.vertex(50,50,-50);
  cube.vertex(-50,50,-50);
  
  cube.fill(0x00FF00);  
  cube.normal(127,0,0);
  
  cube.vertex(50,-50,50);
  cube.vertex(50,-50,-50);
  cube.vertex(50,50,50);
  cube.vertex(50,50,-50);
  
  cube.fill(0x0000FF);  
  cube.normal(-128,0,0);    
  
  cube.vertex(-50,-50,-50);
  cube.vertex(-50,-50,50);
  cube.vertex(-50,50,-50);
  cube.vertex(-50,50,50);
  
  cube.fill(0xFFFF00);
  cube.normal(0,127,0);
  
  cube.vertex(-50,50,50);
  cube.vertex(50,50,50);
  cube.vertex(-50,50,-50);
  cube.vertex(50,50,-50);
  
  cube.fill(0x00FFFF);
  cube.normal(0,-128,0);
  
  cube.vertex(-50,-50,-50);
  cube.vertex(50,-50,-50);
  cube.vertex(-50,-50,50);
  cube.vertex(50,-50,50);

  // Set the index values
  cube.index(0);
  cube.index(1);
  cube.index(2);
  cube.index(3);
  cube.index(4);
  cube.index(5);
  cube.index(6);
  cube.index(7);
  cube.index(8);
  cube.index(9);
  cube.index(10);
  cube.index(11);
  cube.index(12);
  cube.index(13);
  cube.index(14);
  cube.index(15);
  cube.index(16);
  cube.index(17);
  cube.index(18);
  cube.index(19);
  cube.index(20);
  cube.index(21);
  cube.index(22);
  cube.index(23);
  
  cube.strip(4);
  cube.strip(4);
  cube.strip(4);
  cube.strip(4);
  cube.strip(4);
  cube.strip(4);
  
  // Add the cube to the scene
  m3d.add(cube);
  
  framerate(10);
}

void draw()
{
  cube.rotateX(3.1416/100);    
  cube.rotateY(3.1416/100);    
}

void keyPressed()
{
  if(key == '4')
    cube.rotateY(31416/10);
    
  if(key == '6')
    cube.rotateY(-31416/10);
    
  if(key == '2')
    cube.rotateX(31416/10);
    
  if(key == '8')
    cube.rotateX(-31416/10);
}
