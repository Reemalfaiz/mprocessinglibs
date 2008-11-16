import mjs.processing.mobile.mvideo.*;

MVideoRecorder recorder;

void setup()
{
  recorder = new MVideoRecorder(this);
}

void keyPressed()
{
  if(key == '1')
  {
    recorder.show();  
    recorder.play();
  }

  if(key == '2')
  {
    recorder.stop();  
    recorder.hide();
  }

  if(key == '7')
    recorder.startRecord();
    
  if(key == '9')
    recorder.stopRecord();
    
  if(key == '5')
  {
    MVideo mVideo = recorder.video();
    mVideo.show();
    mVideo.play();
  }
}
