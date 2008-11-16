/*

	MRTSPServer - RTSP Server for Mobile Processing

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

package mjs.processing.mobile.mrtspserver;

import java.io.InputStream;

import java.util.Vector;
import java.util.Hashtable;

import javax.microedition.io.SocketConnection;

/**
 * RTSP Request 
 */
class MRTSPRequest 
{
	/**
	 * Options Request 
	 */
	public final static int OPTIONS = 1;
	 
	/**
	 * Describe Request 
	 */
	public final static int DESCRIBE = 2;
	
	/**
	 * Setup Request 
	 */
	public final static int SETUP = 3;	

	/**
	 * Play Request 
	 */
	public final static int PLAY = 4;
	
	/**
	 * Teardown Request 
	 */
	public final static int TEARDOWN = 5;
	
	/**
	 * The type of the request 
	 */
	private int type;
	
	/**
	 * URI 
	 */
	private String uri;
	
	/**
	 * Version 
	 */
	private String version;
	
	/**
	 * Headers 
	 */
	private Hashtable headers;
	
	/**
	 * Client Address 
	 */
	private String clientAddress;
	
	/**
	 * Server Address 
	 */
	private String serverAddress; 	 			
	
	/**
	 * Create a request from the input specified 
	 *
	 * @param connection Current connection 
	 */ 
	public MRTSPRequest(SocketConnection connection, InputStream input)
		throws Exception
	{		
		// Get socket addresses  
		clientAddress = connection.getAddress();
		serverAddress = connection.getLocalAddress();
		
		// Create an empty header request
		headers = new Hashtable();
		
		// Parse the request 		
		parseRequest(input);
	}
	
	private void parseRequest(InputStream input) throws Exception
	{
		// Request line 
		String line = null;
		
		// Find the first not empty line 
		while((line = readLine(input)).length() == 0);
		
		// Split request 
		String[] tokens = split(line," ");
		
		// Extract the type of request 
		if(tokens[0].equals("OPTIONS"))
			type = OPTIONS;
		else if(tokens[0].equals("DESCRIBE"))
			type = DESCRIBE;
		else if(tokens[0].equals("SETUP"))
			type = SETUP;
		else if(tokens[0].equals("PLAY"))
			type = PLAY;
		else if(tokens[0].equals("TEARDOWN"))
			type = TEARDOWN;
		
		// Read other request info 
		uri = tokens[1];
		version = tokens[2];
		
		// Read other headers until an empty line  
		while((line = readLine(input)).length() != 0)
		{
			// Split the line to get header identifier 
			tokens = split(line,":");
			
			// Add to the header the header and values 
			headers.put(tokens[0],parseHeader(tokens[1].trim()));
		}
	}
	
	/**
	 * Read a single line from the input 
	 *
	 * @param input Input 
	 */
	private String readLine(InputStream input) throws Exception
	{
		// Current line  
		StringBuffer buffer = new StringBuffer();
		
		// Read until a empty line is read  			
		while(true)
		{
			// Read one character 
			int ch = input.read();
			
			// If no more data available or character is line change 
			// or carriage return, return the current line  
			if(ch == -1 || ch == '\n')
				break;				
				
			// Add the character to the line 
			buffer.append((char) ch);
		}
		
		// Remove empty spaces
		String line = buffer.toString().trim();		
		//System.out.println(line + ":" + line.length());		
		
		// Return the read line 
		return line.toString();
	}
	
	/**
	 * Split the line  
	 *
	 * @param line complete line
	 * @param sparator token separator 
	 * @return The tokens on the line 
	 */ 
	private String[] split(String line, String separator)
	{
		// The tokens on the line 
		Vector tokens = new Vector();
		
		// Index of the separator 
		int index;
		int indexOld = 0;
		
		// Searhc for the separator 
		while((index = line.indexOf(separator,indexOld)) != -1)
		{
			// Add the token and update his location 
			tokens.addElement(line.substring(indexOld,index));
			indexOld = index + 1;
		}

		// Add last token
		tokens.addElement(line.substring(indexOld));
		
		// Copy the tokens into an array 
		String[] array = new String[tokens.size()];
		tokens.copyInto(array);
		
		// Return the array 
		return array;
	}		
	
	/**
	 * Parse the header line specified 
	 *
	 * @param line Header line 
	 */
	private Hashtable parseHeader(String line)
	{
		// Create an empty list of parameters 
		Hashtable parameters = new Hashtable();
		
		// Get the parameters 
		String[] tokens = split(line,";");
		
		if(tokens.length == 1)
			parameters.put("Value",tokens[0]);
		else		
			// Get the value for each parameter 
			for(int i=0; i<tokens.length; i++)
			{
				// Check if the parameter has a value 
				String[] values = split(tokens[i],"=");
			
				// Put the param and value into the table  
				if(values.length == 2)
					parameters.put(values[0],values[1]);
				// Put the parameter 
				else
					parameters.put(values[0],values[0]);
			}
		
		// Return the hashtable
		return parameters; 
	}

	/**
	 * Return the sequence number of the request 
	 *
	 * @return the sequence number 
	 */	
	public int getSequenceNumber()
	{
		// Get the header sequence header  
		Hashtable header = (Hashtable) headers.get("CSeq");
		
		// Get the number value 
		String value = (String) header.get("Value");
		
		// Return it 
		return Integer.parseInt(value);
	}	

	/**
	 * Return the sequence number of the request 
	 *
	 * @return the sequence number 
	 */	
	public int getSession()
	{
		// Get the header sequence header  
		Hashtable header = (Hashtable) headers.get("Session");
		
		// Get the number value 
		String value = (String) header.get("Value");
		
		// Return it 
		return Integer.parseInt(value);
	}	
	
	/**
	 * Return the client port for the transport header in the Setup request 
	 *
	 * @return the first client port
	 */
	public int getClientPort()
	{
		// Get the header sequence header  
		Hashtable header = (Hashtable) headers.get("Transport");
		
		// Get the number value 
		String value = (String) header.get("client_port");
		String[] tokens = split(value,"-");
		
		// Return it 
		return Integer.parseInt(tokens[0]);	
	}
	
	/**
	 * Return the type of the request 
	 *
	 * @return request type
	 */
	public int getType()
	{
		return type;
	}
	
	/**
	 * Return the request uri 
	 *
	 * @return request uri
	 */
	public String getUri()
	{
		return uri;
	}
	
	/**
	 * Return the client address 
	 *
	 * @return client address
	 */
	public String getClientAddress()
	{
		return clientAddress;
	}
	
	/**
	 * Return the server Address  
	 *
	 * @return server address
	 */
	public String getServerAddress()
	{
		return serverAddress;
	}		 	 
}