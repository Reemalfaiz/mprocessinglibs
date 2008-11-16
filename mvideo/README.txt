MVideo Library for Mobile Processing
------------------------------------

Library for Mobile Processing to play and display video in mpeg format
allows capture video from camera phones and grab snapshots of videos.

The examples directory contains examples to show library features.


Running The VideoTest MIDlet
----------------------------

If you want to see the library in action you can execute the VideoTest MIDlet.

You will need the Java Wireless Toolkit
(http://java.sun.com/products/sjwtoolkit/download-2_3.html)

- Open a System Console.

- Go to the example directory :
	example : cd C:\mobile-0001-expert\libraries\mvideo\examples\VideoTest\midlet

- Run the emulator (we need to increment the heapsize to load the video):
	c:\wtk23\bin\emulator -Xheapsize:5M -Xdescriptor:VideoTest.jad


Issues Found :
--------------

- The draw thread hangs the emulator when video is playing.
- Snapshots can be get when the video is visible (blank snapshot in other cases)
- wtk23 only shows MPEG1 files, some devices shows only MPEG4
- Some features of this realease has not been tested on real devices


Enjoy!

http://mjs.darkgreenmedia.com
http://marlonj.darkgreenmedia.com

Copyright (c) 2005 Marlon J. Manrique (marlonj@darkgreenmedia.com)

