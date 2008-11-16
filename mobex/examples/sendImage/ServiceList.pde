// UI to show list of devices 
class ServiceList
{
  // Current services 
  MService[] services;  

  // Position 
  int x;
  int y;
  
  // Current selected item 
  int selected;

  // Current font 
  PFont pFont = loadFont();

  // true if the list is visible 
  boolean visible;

  // Draw the list 
  void draw()
  {
    // if not visible, do nothing 
    if(!visible)
      return;

    // Align the text to left 
    textAlign(LEFT);

    // Temporal positions 
    int tmpX = x;
    int tmpY = y;

    // Show each element of the list 
    for(int i=0; i<services.length; i++)
    {
      // If the current is selected show in green 
      if(i == selected)
        fill(0x00FF00);
      else
        fill(0xFFFFFF);

		// Get device       
      MService service = services[i]; 
      MDevice device = service.device;

      // Print device name 
      text(device.name(),tmpX,tmpY);
      tmpY += pFont.height;
    }
  }

  /*
   * Show the list
   */
  void show()
  {
    visible = true;
  }

  /* 
   * Check key events 
   */
  void keyPressed()
  {
    // if not visible, do nothing 
    if(!visible)
      return;

    // Move the selected item 
    switch(keyCode)    
    {
    case DOWN :
      if(selected + 1 < length(services))
        selected++;
      break;
    case UP :
      if(selected > 0)
        selected--;
      break;
    }
  }
}
