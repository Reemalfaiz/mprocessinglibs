/**
 * Show how to save a single contact
 *
 * Mary Jane Soft - Marlon J. Manrique 
 *
 * http://mjs.darkgreenmedia.com
 * http://marlonj.darkgreenmedia.com 
 * 
 * Changes :
 * 
 * 1.0 (October - 2007) Initial Release  
 *
 * $Id: saveContact.pde 296 2007-10-25 15:21:18Z marlonj $
 */

import mjs.processing.mobile.mpim.*;

// Create the PIM object 
MPIM mPIM = new MPIM();

// Open the contact list in Write mode
MContactList mContactList = mPIM.contactList(MPIM.WRITE);

// Create a new contact to add and set values
MContact mContact = mContactList.createContact();
mContact.name("Mobile Processing");
mContact.tel("1 800 12345");

// Save the new contact into the list
mContact.save();
