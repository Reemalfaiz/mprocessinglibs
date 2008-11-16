/**
 * Show how to load the contact list and save a new contact
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
 * $Id: MContactsViewer.pde 296 2007-10-25 15:21:18Z marlonj $
 */
import mjs.processing.mobile.mpim.*;

// The Personal Information Management Object
MPIM mPIM;

// The font to grab the height
PFont pFont;

// The contact information to show in the screen
// and the messages 
String contactName = "";
String contactTel = "";
String counterMessage = "";
String message = "";

// The contact list and the number of the current show
int contactIndex;
MContact[] contacts;

// Contact List
MContactList mContactList;

/**
 * Init the sketch loading fonts and adding the softkey,
 * also create the MPIM object
 */
void setup()
{
  // Load fonts and set init values
  pFont = loadFont();
  textAlign(CENTER);
  textFont(pFont);  
  softkey("New Contact");
  
  // Check if the library is supported
  // if not, show a message 
  if(!mPIM.isSupported())
    message = "MPIM Not Supported";
  // If the library is supported 
  // create the object and show message 
  else
  {
  	 mPIM = new MPIM();
    message = "Press 5 to Load Contacts";
  }   
}

/**
 * Draw the information to the user
 */
void draw()
{
  background(0);
  text("Contacts",width/2,pFont.height);
  text(contactName,width/2,pFont.height*2);
  text(contactTel,width/2,pFont.height*3);
  text(counterMessage,width/2,pFont.height*4);
  text(message,width/2,pFont.height*5);
}

/**
 * Check the key events to load and browse the contact list
 */
void keyPressed()
{
  if(key == '5')
    loadContacts();
  else if(key == '6')
    nextContact();
  else if(key == '4')
    previousContact();
}

/**
 * Check the softkeys
 *
 * @param label Label of the softkey
 */
void softkeyPressed(String label)
{
  // Get the name and phonenumber of the contact 
  String name = textInput("Contact Name","",120);
  String tel = textInput("Contact Tel","",30);
  
  // Save the contact and reload the contact list
  saveContact(name,tel);
  loadContacts();
}

/**
 * Load the contacts from the default list 
 */
void loadContacts()
{
  // Init the current contact index 
  contactIndex = 0;
  
  // Create the contact list if not created yet  
  if(mContactList == null)
    mContactList = mPIM.contactList(MPIM.READ_WRITE);
    
  // Load the contacts 
  contacts = mContactList.contacts();
  
  // If contacts are available, show the first one 
  if(length(contacts) > 0)
  {
    showContact(0);
    message = "Press 4 or 6 to Browse";
  }
  else
    message = "No Contacts Found";  
}

/**
 * Show the next contact 
 */
void nextContact()
{
  // Check if the index is valid and show 
  if(contacts != null && contactIndex+1 < length(contacts))
    showContact(++contactIndex);
}

/**
 * Show the previous contact
 */
void previousContact()
{
  // Check if the index is valid and show
  if(contacts != null && contactIndex > 0)
    showContact(--contactIndex);
}

/**
 * Show the information of the contact
 *
 * @param contactIndex index of the contact to show 
 */
void showContact(int contactIndex)
{
  contactName = contacts[contactIndex].name();
  contactTel = contacts[contactIndex].tel();
  counterMessage = contactIndex+1 + " of " + length(contacts);
}

/**
 * Save a new contact with the data specified
 *
 * @param name Name of the contact
 * @param tel Phone number of the contact 
 */ 
void saveContact(String name, String tel)
{
  // Create the contact and set the values 
  MContact mContact = mContactList.createContact();
  mContact.name(name);
  mContact.tel(tel);
  
  // Save the contact to the list
  mContact.save();
}
