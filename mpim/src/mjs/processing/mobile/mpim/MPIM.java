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

import javax.microedition.pim.ContactList;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;

import processing.core.PException;

/**
 * MPIM
 *
 * Personal Information Management Library for Mobile Processing
 *
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */
public class MPIM
{
	/**
	 * Constant Representing opening a list in read only mode
	 * 
	 * @value 1 
	 */
	public final static int READ = 1;
	
	/**
	 * Constant Representing opening a list in write only mode
	 * 
	 * @value 2 
	 */
	public final static int WRITE = 2;
	
	/**
	 * Constant Representing opening a list in read/write mode
	 * 
	 * @value 3 
	 */
	public final static int READ_WRITE = 3;
	
	/**
	 * Native object for PIM access
	 */
	private PIM pim;
	
	/**
	 * Create a new instance of the library
	 */
	public MPIM()
	{
		// Obtain the instance of PIM
		pim = PIM.getInstance();
	}
	
	/**
	 * Check if the library is supported by the phone
	 *
	 * @return true if the library is supported by the phone, false otherwise
	 */
	public static boolean isSupported()
	{
		// Get the version of the api 
		String version = System.getProperty("microedition.pim.version");
		
		// If the version is not present the library is not supported
		if(version == null)
			return false;
			
		// The library is supported
		return true;	
	}

	/**
	 * Opens the contact list in the mode specified
	 *
	 * @param mode in wich mode the list is opened, either READ, WRITE 
	 *		or READ_WRITE
	 */
	public MContactList contactList(int mode)
	{
		try
		{
			// Open the Contact List in the PIM mode especified
			ContactList contactList = 
				(ContactList) pim.openPIMList(PIM.CONTACT_LIST,getMode(mode));
		
			// Returns a new Contact List create with the native one
			return new MContactList(contactList);
		}
		catch(PIMException e)
		{
			// Throws a Processing exception if any error
			throw new PException(e);
		}
	}

	/**
	 * Converts the MPIM mode to the PIM mode
	 *
	 * @return the PIM mode acoording the MPIM mode
	 */
	private int getMode(int mode)
	{
		// Convert the mode 	
		switch(mode)
		{
			// Check for PIM modes
			case MPIM.READ: return PIM.READ_ONLY;
			case MPIM.WRITE : return PIM.WRITE_ONLY;
			case MPIM.READ_WRITE : return PIM.READ_WRITE;
			
			// If the mode is not recognized return the same input mode
			default : return mode;
		}						
	}
}
