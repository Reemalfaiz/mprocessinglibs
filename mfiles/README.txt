MFiles Library for Mobile Processing
------------------------------------

File access for Mobile Processing.

This library provides implementation for basic Mobile Processing data access
using the FileConnection Optional Package 1.0 Specification included in the 
JSR 75.

The library implements the following functions :

- loadImage
- loadBytes
- loadStrings
- saveBytes
- saveStrings

Others functions are added to give file access to the sketch :

- isSupported
- listRoots

More functions will appear in future versions of the library, to support the 
Mobile Processing language and  also to allow file manipulation.

The examples directory contains examples to show library features.

Running The Examples :
----------------------

The library is development using the Sun Wireless Toolkit 2.5 but can be used 
with the version 2.2. 

To run the examples with the WTK copy the content of the example_data into :

	<WTK_HOME>/appdb/<DEVICE>/filesystem/root1

	Where :
	<WTK_HOME> is the path where the WTK is installed 
	<DEVICE> is the device used to run the MIDlets.
	
	Example :
	C:\WTK25\appdb\DefaultColorPhone\filesystem\root1

To use MPowerPlayer you can patch the release with the library available in this 
site : http://www.esnips.com/web/ivasenkosBusinessFiles. (Not Tested)

Check the JSR 75 support forum of the MPowerPlayer :
http://developer.mpowerplayer.com/index.php?topic=99.15

To use in the real phone you have to check the root list to get the real 
filesystem and copy the example data to the phone.


Enjoy!

http://mjs.darkgreenmedia.com
http://marlonj.darkgreenmedia.com

Copyright (c) 2007 Marlon J. Manrique (marlonj@darkgreenmedia.com)

$Id: README.txt 198 2007-04-17 19:57:32Z marlonj $
