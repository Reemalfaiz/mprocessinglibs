MBt Library for Mobile Processing
---------------------------------

Discover service library for bluetooth, this is based on the MBluetooth library 
but separate the discoverer process of the communication for to provide a library 
that can works with RFCOMM, Obex y L2CAP.

The current version works with Processing on Linux using AvetanaBT cause is a 
GPL implementation of the JSR82 Bluetooth API. 

This version also include the Bluecove implementation, but is in early state in 
Linux but can be use in Windows and MacOSX (untested)


Installation Mobile Processing : 
--------------------------------
- Extract the library file inside libraries directory 

Installation Processing and Linux :
----------------------------------
The current Bluetooth library is AvetanaBT with a modification to support 
the Wiimote control.

- Extract the library file inside libraries directory
- Rename bluecove.jar with bluecove.jar_
- Rename bluecove-gpl.jar with bluecove-gpl.jar_

Installation Processing, MacOSX or Windows :
--------------------------------------------
- Extract the library file inside libraries directory
- Go to http://code.google.com/p/bluecove to check stack support   

Enjoy!

http://mjs.darkgreenmedia.com
http://marlonj.darkgreenmedia.com

Copyright (c) 2007-2008 Marlon J. Manrique (marlonj@darkgreenmedia.com)
$Id: README.txt 356 2008-01-23 15:44:28Z marlonj $
