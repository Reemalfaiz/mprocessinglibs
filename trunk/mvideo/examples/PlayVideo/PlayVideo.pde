import mjs.processing.mobile.mvideo.*;

MVideo mVideo;

void setup()
{
  mVideo = new MVideo(this,"video.3gp");
}

void keyPressed()
{
  if(key == '1')
  {
    mVideo.show();
    mVideo.play();
  }

  if(key == '3')
  {
    mVideo.stop();
    mVideo.hide();
  }  
}
