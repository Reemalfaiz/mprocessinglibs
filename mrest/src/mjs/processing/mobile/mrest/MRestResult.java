/*

	MRest - Rest Library for Mobile Processing

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

package mjs.processing.mobile.mrest;

import mjs.microrest.RestException;
import mjs.microrest.RestResult;

public class MRestResult
{
	/**
	 * The rest result
	 */
	private RestResult restResult;
	
	/**
	 * Create a result element with the specifie name 
	 *
	 * @param restResult Rest Result
	 */
	protected MRestResult(RestResult restResult)
	{
		this.restResult = restResult;
	}
	
	/**
	 * Return the name of the element
	 *
	 * @return Name of the element
	 */	
	public String name()
	{
		return restResult.getName();
	}
	
	/**
	 * Return the text associated with the element
	 *
	 * @return Text of the element
	 */
	public String text()
	{
		return restResult.getText();
	}
	
	/**
	 * Return the value of the attribute with the name specified, if no 
	 * attribute is found, return null
	 *
	 * @param name Attribute name
	 * @return Attribute values if the attribute is found, null otherwise
	 */
	public String attribute(String name)
	{
		return restResult.getAttributeValue(name);
	}	
	
	/**
	 * Return the text value or attribute value specified by the xpath, null if
	 * any exception
	 *
	 * @param path xpath of the element
	 */
	public String get(String path)
	{
		try
		{
			// Return the value 
			return restResult.get(path);
		}
		catch(RestException e)
		{
			e.printStackTrace();
		}
		
		return null;				
	}

	/**
	 * Return an array with the results available in the path specified, null 
	 * if any exception
	 *
	 * @param path xpath of the element
	 */
	public MRestResult[] getResults(String xpath)
	{
		try
		{
			// Get the results
			RestResult[] results = restResult.getResults(xpath);
	
			// Copy the results to a Mresult array	
			MRestResult[] mResults = new MRestResult[results.length];		
			for(int i=0; i<mResults.length; i++)
				mResults[i] = new MRestResult(results[i]);
			
			// Return the results
			return mResults;
		}
		catch(RestException e)
		{
			e.printStackTrace();
		}
		
		// If any exception return a null value
		return null;		
	}	

	/**
	 * Return a string representation of the element
	 *
	 * @return String representation of the element
	 */	
	public String toString()
	{
		return restResult.toString();
	}	
}
