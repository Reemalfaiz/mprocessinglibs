/*

	MPIM - Personal Information Management Library for Mobile Processing

	Copyright (c) 2007 Mary Jane Soft - Marlon J. Manrique
	
	http://mjs.darkgreenmedia.com
	http://marlonj.darkgreenmedia.com

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General
	Public License along with this library; if not, write to the
	Free Software Foundation, Inc., 59 Temple Place, Suite 330,
	Boston, MA  02111-1307  USA

	$Id$
	
*/

package mjs.processing.mobile.mpim;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.pim.Contact;
import javax.microedition.pim.ContactList;
import javax.microedition.pim.PIMException;

import processing.core.PException;

/**
 * Represents a Contact list containing Contact items.
 *
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */
public class MContactList
{
	/**
	 * The native contact list
	 */
	private ContactList contactList;
	
	/**
	 * Create a contact list with the native one, the only for to create this 
	 * list is use the MPIM class
	 *
	 * @param contactList the native contact list
	 */ 
	protected MContactList(ContactList contactList) 
	{
		this.contactList = contactList; 
	}
	
	/**
	 * Returns all the contacts in the list like an array of MContact 
	 *
	 * @return Array with all the contacts in the list
	 */
	public MContact[] contacts()
	{
		try
		{
			// Create a vector to storage the contacts 
			Vector vector = new Vector();	
		
			// Grab each contact and create a MContact with it
			for(Enumeration e = contactList.items(); e.hasMoreElements(); )
			{
				// Create the MContact 
				MContact mContact = 
					new MContact((Contact) e.nextElement());
				
				// Add the MContact to the vector
				vector.addElement(mContact);		
			}
			
			// Convert the vector to a fixed array
			MContact[] array = new MContact[vector.size()];
			vector.copyInto(array);
			
			// Return the array 
			return array;
		}
		catch(PIMException e)
		{			
			// If any error throw a Processing exception
			throw new PException(e);
		}
	}

	/**
	 * Create a contact for the list. 
	 * 	
	 * This method can be use to create an empty contact to set values and add 
	 * to the current list. The list must be open in WRITE or READ_WRITE mode, 
	 * also the MContact must be save using the save method
	 *
	 * @return A new empty contact not present in the list until is save
	 */	  
	public MContact createContact()
	{
		// Create the contact 
		Contact contact = contactList.createContact();
		
		// Create a MContact with the contact
		return new MContact(contact);
	}
}
