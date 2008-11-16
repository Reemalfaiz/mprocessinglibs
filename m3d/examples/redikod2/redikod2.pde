import mjs.processing.mobile.m3d.*;

M3d m3d;
M3dObject cube;

void setup()
{
  m3d = new M3d(this);
  canvas = m3d.canvas();
  
  m3d.loadWorld("cube.m3g");
  cube = m3d.findMesh(13);
  
  framerate(10);
}

void draw()
{
  if(keyCode == LEFT)
    cube.rotateY(0.2);
    
  if(keyCode == RIGHT)
    cube.rotateY(-0.2);
    
  if(keyCode == UP)
    cube.rotateX(0.2);
    
  if(keyCode == DOWN)
    cube.rotateX(-0.2);   
}

void keyReleased()
{
  keyCode = 0;
}
