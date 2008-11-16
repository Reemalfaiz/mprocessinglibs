/**
 * VideoTest
 * 
 * Sketch to test video features. Allows video playing from resource and video capture,
 * also makes and show a maximum of ten snaphots from the video.
 *
 * Press keys : 
 * 1 : Hide Video
 * 2 : Play Video
 * 3 : Show Video
 * 4 : Video Loop
 * 5 : Stop Video
 * 6 : Video No Loop
 * 7 : Previous Snapshot
 * 8 : Make Snapshot
 * 9 : Next Snapshot
 * 0 : Change Source (videoFile,Capture)
 *
 * You will need the Java Wireless Toolkit
 * (http://java.sun.com/products/sjwtoolkit/download-2_3.html)
 *
 * - Open a System Console. 
 * - Go to the example directory : 
 *   example : cd C:\mobile-0001-expert\libraries\mvideo\examples\VideoTest\midlet
 * - Run the emulator (we need to increment the heapsize to load the video): 
 *   c:\wtk23\bin\emulator -Xheapsize:5M -Xdescriptor:VideoTest.jad *
 * 
 * @author Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 1.1 (November - 2005) Library Rename, Mobile Processing 0001 Compatible
 * 1.0 (September - 2005) Initial Release 
 */

import javax.microedition.lcdui.*;

import mjs.processing.mobile.mvideo.*;

// Video Objects 
MVideo video; // This is the current source video
MVideo videoFile; 
MVideo videoCapture;

// Size and index of current snapshot
int snapshotsSize = 0;
int snapshotIndex = 0;

// Array of snapshots
PImage[] snapshots = new PImage[10];

// Video position
int videoX;
int videoY;

// Prepare video sources, set video at center of screen and show help
void setup()
{
  // Loop hangs the emulator
  noLoop();
  
  // Set default font
  textFont(new PFont(Font.getDefaultFont()));
  textAlign(CENTER);  
  
  videoFile = new MVideo(this,"video.mpg");
  videoCapture = new MVideo(this,"capture://video");
  
  // Current video form file
  video = videoFile;
  
  // Center the video on the screen
  videoX = (width - video.getWidth())/2;
  videoY = (height - video.getHeight())/2;
  
  // Set location for both videos
  videoFile.location(videoX,videoY);
  videoCapture.location(videoX,videoY);
    
  // Show Help
  drawHelp();
}

// Shows video source name
void drawVideoInfo()
{
  background(200);
  
  String title = "Video File";
  
  if(video == videoCapture)
    title = "Capture";
    
  text(title,width/2,videoY - 20);
}

// Grab a snaphost if is available space on array
void grabSnapshot()
{
  // No space for more snapshot, return 
  if(snapshotsSize > snapshots.length)
    return;
  
  // We need to create a different thread to avoid deadlocks, 
  // any other way ?
  new Thread()
  {
    public void run()
    {
      // Get the snaphost
      PImage snapshot = video.snapshot();
      snapshots[snapshotsSize++] = snapshot;
    }
  }.start();
}

// Draws the current snapshot
void drawSnapshot()
{
  // Stop video and hide it
  video.stop();
  video.hide();

  // Draw current snapshot
  background(200);
  text("Snaphot " + (snapshotIndex+1) + " of " + snapshotsSize,width/2,videoY - 20);
  image(snapshots[snapshotIndex],videoX,videoY);
}

// Display snapshot and move index right
void nextSnapshot()
{
  if(snapshotIndex < snapshotsSize)
  {
    drawSnapshot();
    snapshotIndex++;    
  }
    
  // if is last change next to first
  if(snapshotIndex == snapshotsSize)
    snapshotIndex = 0;
}

// Display snapshot and move index left
void previousSnapshot()
{
  if(snapshotIndex >= 0)
  {
    drawSnapshot();
    snapshotIndex--;    
  }
    
  // if is first change next to last 
  if(snapshotIndex == -1)
    snapshotIndex = snapshotsSize - 1;
}

// Show video info and play video
void playVideo()
{
  video.play();
  drawVideoInfo();
}

// Hide video info and hide video
void hideVideo()
{
  video.hide();
  drawVideoInfo();
}

// Show video info and show video
void showVideo()
{
  drawVideoInfo();
  video.show();
}

// Change the source setting the video to the next source
void changeSource()
{
  // if is file change to capture
  if(video == videoFile)
  {
    videoFile.hide();
    video = videoCapture;
    showVideo();
  }
  else
  // if is capture change to file
  {
    videoCapture.hide();
    video = videoFile;
    showVideo();
  }
}

// Draw help info
void drawHelp()
{
  background(200);
  fill(0);

  int lineSpace = height/14;
  int y = lineSpace;
  text("Video Test Version 1.0",width/2,y);  
  y += lineSpace;
  text("Help",width/2,y);
  y += lineSpace;
  text("1 Hide Video",width/2,y);
  y += lineSpace;
  text("2 Play Video",width/2,y);
  y += lineSpace;
  text("3 Show Video",width/2,y);
  y += lineSpace;
  text("4 Video Loop",width/2,y);
  y += lineSpace;
  text("5 Stop Video",width/2,y);
  y += lineSpace;
  text("6 Video No Loop",width/2,y);
  y += lineSpace;
  text("7 Previous Snapshot",width/2,y);
  y += lineSpace;
  text("8 Make Snapshot",width/2,y);
  y += lineSpace;  
  text("9 Next Snapshot",width/2,y);
  y += lineSpace;  
  text("0 Change Source (VideoFile,Capture)",width/2,y);
  y += lineSpace;  
  text("http://marlonj.darkgreenmedia.com",width/2,y);    
}

// Allows interaction with user through keypad
void keyPressed()
{
  switch(key)
  {
    case '1' : hideVideo(); break;
    case '2' : playVideo(); break;
    case '3' : showVideo(); break;  
    case '4' : video.loop(); break;  
    case '5' : video.stop(); break;
    case '6' : video.noLoop(); break;  
    case '7' : previousSnapshot(); break;
    case '8' : grabSnapshot(); break;
    case '9' : nextSnapshot(); break;
    case '0' : changeSource(); break;
  }
}
