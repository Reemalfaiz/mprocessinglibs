/**
 * Shows how to rotate the camera 
 *
 * $Id: rotateCamera.pde 269 2007-07-21 21:40:41Z marlonj $
 */
import mjs.processing.mobile.mvideo.*;

MVideo video;

void setup()
{
  noLoop();
}

void keyPressed()
{
  int transform = MVideo.TRANS_NONE;
  
  if(keyCode == '2')
    transform = MVideo.TRANS_ROT90;
  else if(keyCode == '3')
    transform = MVideo.TRANS_ROT180;
  else if(keyCode == '4')
    transform = MVideo.TRANS_ROT270;
  else if(keyCode == '5')
    transform = MVideo.TRANS_MIRROR;    
  else if(keyCode == '6')
    transform = MVideo.TRANS_MIRROR_ROT90;    
  else if(keyCode == '7')
    transform = MVideo.TRANS_MIRROR_ROT180;
  else if(keyCode == '8')
    transform = MVideo.TRANS_MIRROR_ROT270;

  if(video != null)
    video.close();
    
  video = new MVideo(this,"capture://video",null,transform,false);
  video.play();
}
