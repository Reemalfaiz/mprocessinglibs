/**
 * How to create a Cube
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
  m3d.ambientLight(255,255,255);

  // Create a cube 
  cube = new MMesh(); 

  // Add vertex, and fill the vertex with different colors
  cube.fill(0xFFFFFF);
  cube.vertex(-50,-50,50);
  cube.fill(0x0000FF);
  cube.vertex(50,-50,50);
  cube.fill(0x0022FF);  
  cube.vertex(-50,50,50);
  cube.fill(0x0044FF);    
  cube.vertex(50,50,50);
  cube.fill(0x0066FF);    
  cube.vertex(-50,-50,-50);
  cube.fill(0x00885FF);    
  cube.vertex(50,-50,-50);
  cube.fill(0x00AAFF);  
  cube.vertex(-50,50,-50);
  cube.fill(0x00BBFF);    
  cube.vertex(50,50,-50);

  // Set the index values
  cube.index(0);
  cube.index(1);
  cube.index(2);
  cube.index(3);
  cube.index(7);
  cube.index(1);
  cube.index(5);
  cube.index(4);
  cube.index(7);
  cube.index(6);
  cube.index(2);
  cube.index(4);
  cube.index(0);
  cube.index(1);

  // Add the cube to the scene
  m3d.add(cube);
}

// Rotate the cube if 2,8,4,6 keys are pressed
void keyPressed()
{
  if(key == '2')
    cube.rotateX(-3.1416/10);
    
  if(key == '8')
    cube.rotateX(3.1416/10);    
    
  if(key == '4')
    cube.rotateY(-3.1416/10);
    
  if(key == '6')
    cube.rotateY(3.1416/10);    
}
