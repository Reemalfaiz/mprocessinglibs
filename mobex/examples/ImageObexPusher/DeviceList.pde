// Screen Device List
class DeviceList
{
  // List corrdinates and size
  int x;
  int y;
  int width;
  int height;

  // Current selected item
  int selected;

  // Is visible ??
  boolean visible;

  // Set the limits of the list
  void setBounds(int x, int y, int width, int height)
  {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;    
  }

  // Draw the list
  void draw()
  {
    // If the list is not visible, do nothing
    if(!visible)
      return;

    // Restore coordinate system
    pushMatrix();
    translate(0,0);    

    // Dinamic x and y coordinates
    int tmpX = x + width/2;
    int tmpY = y + 10;

    // Draw a rect
    fill(0x000000);
    stroke(0xFFFFFF);
    rect(x,y,width,height);

    // Draw the list title
    fill(0xFFFFFF);
    textAlign(CENTER);
    tmpY += pFont.height;    
    text("Devices",tmpX,tmpY);    

    // Draw each service device name
    for(int i=0; i<mServices.length; i++)
    {
      // If selected, draw it in green
      if(i == selected)
        fill(0x00FF00);
      // Draw it in white
      else
        fill(0xFFFFFF);

      // Get the device     
      MDevice device = mServices[i].device;

      // Update current position and draw the devicename 
      tmpY += pFont.height;
      text(device.name(),tmpX,tmpY);
    }

    // Restore coordinate system
    popMatrix();
  }

  // If the user pressed some key
  void keyPressed()
  {
    // If not visible, do nothing
    if(!visible)
      return;

    switch(keyCode)    
    {
      // If down, check if more devices
    case DOWN :
      if(selected + 1 < length(mServices))
        selected++;
      break;
      // If up, check is not the first
    case UP :
      if(selected > 0)
        selected--;
      break;
      // Send the snapshot to the selected device
    case FIRE :
       sendImage();
    }
  }
}
