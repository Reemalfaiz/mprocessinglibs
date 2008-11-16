/*

	MBt - Bluetooth Library for Mobile Processing

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

package mjs.processing.mobile.mbt;

import javax.bluetooth.ServiceRecord;
import javax.bluetooth.DataElement;

import javax.microedition.io.Connector;
import javax.microedition.io.Connection;

/**
 * Represents a Bluetooth service.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 */ 
public class MService
{
	/**
	 * The default name of the service
	 */
	public static final String UNKNOWN = "(Unknown)";

	/**
	 * Service name attribute
	 */	
    public static final int ATTR_SERVICENAME = 0x0100;
    
	/**
	 * Service description attribute
	 */	
    public static final int ATTR_SERVICEDESC = 0x0101;
    
	/**
	 * Service provider name attribute
	 */	    
    public static final int ATTR_PROVIDERNAME = 0x0102;
	
	/**
	 * The device owner of the service
	 */
	 
    public MDevice device;
    
    /**
     * The service record.
     */
    private ServiceRecord record;
    
    /**
     * The service name
     */
    public String name;
    
    /**
     * The service description
     */
    public String description;
    
    /**
     * The service provider
     */
    public String provider;    
    
    /**
     * Create a service in the device and service record specified.
     *
     * @param device The host device
     * @param record The service record
     */
    public MService(MDevice device, ServiceRecord record)
    {
    	this.device = device;
    	this.record = record;
    	
    	// Get the service attributes
		DataElement element;
		
		// Get the name
        element = record.getAttributeValue(ATTR_SERVICENAME);
        if(element != null)
            name = (String) element.getValue();
        else
            name = UNKNOWN;
            
		// Get the description
        element = record.getAttributeValue(ATTR_SERVICEDESC);
        if(element != null)
            description = (String) element.getValue();
        else
            description = UNKNOWN;
            
		// Get the provider name
        element = record.getAttributeValue(ATTR_PROVIDERNAME);
        if(element != null)
            provider = (String) element.getValue();
        else
            provider = UNKNOWN;
    }
 
 	/**
 	 * Return the url connection to the service
 	 *
 	 * @return the url address
 	 */   
    public String url()
    {
    	// Get the URL
    	return record.getConnectionURL(
    		ServiceRecord.NOAUTHENTICATE_NOENCRYPT,false);
    }
}
