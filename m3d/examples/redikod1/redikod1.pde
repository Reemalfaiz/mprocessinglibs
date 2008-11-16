import mjs.processing.mobile.m3d.*;

// 3D System
M3d m3d;
float camRot = 0;

void setup()
{
  // Init 3D System
  m3d = new M3d(this);
  canvas = m3d.canvas();

  // Load the World
  m3d.loadWorld("map.m3g");

  // Add an ambient light
  // I can't use ambientLight because we need a light with intensity 3
  MLight light = new MLight(MLight.AMBIENT);
  light.intensity(3.0f);
  m3d.add(light);
}

void draw()
{
  moveCamera();
}

void moveCamera()
{
  // Check controls
  if(keyCode == LEFT)
    camRot += 5.0f;
  else if(keyCode == RIGHT)
    camRot -= 5.0f;    

  MCamera cam = m3d.activeCamera();
  cam.orientation(camRot,0,1,0);

  // Calculate trigonometry for camera movement
  double rads = Math.toRadians(camRot);
  float camSine = (float) Math.sin(rads);
  float camCosine = (float) Math.cos(rads);
  
  if(keyCode == UP)
    cam.translate(-2*camSine,0,-2*camCosine);
  else if(keyCode == DOWN)
    cam.translate(2*camSine,0,2*camCosine);  
}

void keyReleased()
{
  keyCode = 0;
}
