/**
 * Show the roots filesystems
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.0 (April - 2007) Initial Release  
 *
 * $Id: listRoots.pde 197 2007-04-17 19:22:58Z marlonj $
 */

import mjs.processing.mobile.mfiles.*;

// Set values
background(0);
textFont(loadFont());

// Get the roots filesystems
String[] roots = MFiles.listRoots();

// Show the roots
for(int i=0; i<roots.length; i++)
	text(roots[i],20,20 + i*10);
