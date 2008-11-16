/**
 * Show how to access the contact list
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
 * $Id: getContacts.pde 296 2007-10-25 15:21:18Z marlonj $
 */

import mjs.processing.mobile.mpim.*;

// Create the PIM object 
MPIM mPIM = new MPIM();

// Open the contact list in read mode and get all the contacts
MContact[] contacts = mPIM.contactList(MPIM.READ).contacts();

// For each contact show name an phone number
for(int i=0; i<length(contacts); i++)
{
  println("name : " + contacts[i].name());
  println("tel : " + contacts[i].tel());
}
