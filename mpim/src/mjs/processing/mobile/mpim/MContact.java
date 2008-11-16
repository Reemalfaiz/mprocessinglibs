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

import javax.microedition.pim.Contact;
import javax.microedition.pim.ContactList;

import processing.core.PException;

/**
 * Represents a single Contact entry in a PIM Contact database.
 *
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */ 
public class MContact
{
	/**
	 * The native contact
	 */
	private Contact contact;
	
	/**
	 * Create a contact with the native contact 
	 *
	 * @param contact The native contact
	 */
	protected MContact(Contact contact)
	{
		this.contact = contact;
	}
	
	/**
	 * Returns the name of the contact
	 *
	 * @return Contact name
	 */
	public String name()
	{
		// Returns the field with the formatted name 
		return getString(Contact.FORMATTED_NAME,0);
	}	
	
	/**
	 * Returns the telephone number of the contact
	 *
	 * @return Contact number phone
	 */
	public String tel()
	{
		// Returns the field with the telephone number
		return getString(Contact.TEL,0);
	}
	
	/**
	 * Set the name of the contact 
	 *
	 * @param name Name of the contact
	 */
	public void name(String name)
	{
		// Set the formatted name field with the name specified
		contact.addString(Contact.FORMATTED_NAME,Contact.ATTR_NONE,name);	
	}
	
	/**
	 * Set the telephone number of the contact 
	 *
	 * @param name Telephone number of the contact
	 */
	public void tel(String tel)
	{
		// Set the tel field with the name specified	
		contact.addString(Contact.TEL,Contact.ATTR_HOME,tel);
	}
	
	/**
	 * Returns the string value of the field in the index specified. If any 
	 * exception the value returned is "Not Found" 
	 *
	 * @param field The field from which the data is retrieved.
	 * @param index an index to a particular value associated with the field.
	 *
	 * @return a String representing a value of the field.
	 */
	private String getString(int field, int index)
	{
		// Value of the field 
		String value;
		
		try
		{
			// Try to grab the value
			value = contact.getString(field,index);
		}
		catch(Exception e)
		{
			// If any problem, set like not found 
			value = "Not Found";
		}
		
		// Return the value 
		return value;
	}

	/**
	 * Save the contact in the list where was created 
	 */	
	public void save()
	{
		try
		{
			// Try to commit 
			contact.commit();
		}
		catch(Exception e)
		{
			// If aby exception throws a Processing exception
			throw new PException(e);
		}
	}
}