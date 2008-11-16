/*

	MFiles - File Access Library for Mobile Processing

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

package mjs.processing.mobile.mfiles;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Image;

import javax.microedition.io.Connector;

import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemListener;
import javax.microedition.io.file.FileSystemRegistry;

import processing.core.PMIDlet;
import processing.core.PImage;

/**
 * MFiles.
 *
 * File access class for Mobile Processing.
 * 
 * @author Mary Jane Soft - Marlon J. Manrique. 
 */
public class MFiles //implements FileSystemListener
{
	/**
	 * Root added event.
	 *
	 * @value 1
	 */ 
	public static final int EVENT_ROOT_ADDED = 1;

	/**
	 * Root removed event.
	 *
	 * @value 2
	 */ 
	public static final int EVENT_ROOT_REMOVED = 1;
	
	/**
	 * Processing MIDlet.
	 * Used to get access to the canvas to draw the video.
	 */
	private PMIDlet pMIDlet;

	/**
	 * Creates a file access object to the midlet specified.
	 *
	 * @param pMIDlet The current midlet.
	 */
	public MFiles(PMIDlet pMIDlet)
	{
		// Set the midlet
		this.pMIDlet = pMIDlet;
		
		// Register the object to listen add or removing roots
		// FileSystemRegistry.addFileSystemListener(this);
	}

	/**
	 * Return true if the MFile library is supported
	 *
	 * @return true if the MFile library is supported
	 */
	public static boolean isSupported()
	{
		try
		{
			// Try to load a class from the file package
			Class.forName("javax.microedition.io.file.FileSystemRegistry");
			return true;
		}
		catch(ClassNotFoundException e)
		{
		}

		// If class can be load, the library is not supported
		return false;
	}


	/**
	 * This method is invoked when a root on the device has changed state.
	 *
	 * @param state representing the state change that has happened to the root
	 * @param rootName the String name of the root, following the root naming 
	 * 			conventions detailed in FileConnection.
	 *
	 */
	public void rootChanged(int state, String rootName)
	{
		// If not MIDlet was specified do nothing
		if(pMIDlet == null)
			return;

		// Not event
		int pEvent = 0;

		// Convert state into a processing event
		switch(state)
		{
			case FileSystemListener.ROOT_ADDED : 
				pEvent = EVENT_ROOT_ADDED; break;
				
			case FileSystemListener.ROOT_REMOVED : 
				pEvent = EVENT_ROOT_REMOVED; break;
		}

		// If event is interesting send to the midlet
		if(pEvent != 0)
			pMIDlet.enqueueLibraryEvent(this,pEvent,rootName);
		else
			pMIDlet.enqueueLibraryEvent(this,-1,rootName);
	}

	/**
	 * Returns the currently mounted root file systems on a device as String 
	 * array. If there are no roots available on the device, a zero length 
	 * array is returned. If file systems are not supported on a device, a zero
	 * length Enumeration is also returned
	 *
	 * @return an array of mounted file systems as String objects.
	 */
	public static String[] listRoots()
	{
		// Get the roots
		Enumeration roots = FileSystemRegistry.listRoots();

		// Create a vector with the enumration
		Vector rootVector = new Vector();

		while(roots.hasMoreElements())
			rootVector.addElement(roots.nextElement());

		// Copy the vetor to an array
		String[] theArray = new String[rootVector.size()];
		rootVector.copyInto(theArray);

		// Return the array
		return theArray;
	}

	/**
	 * Loads an image into a variable of type PImage. Only .png images may be 
	 * loaded.
	 *
	 * @param filename name of file to load, must be .png
	 * @return the PImage 
	 */
	public static PImage loadImage(String filename)
	{
		try
		{
			// Open the file connection
			FileConnection fileConn = (FileConnection) Connector.open(filename);

			// Open the inputstream, create a midp image and encapsulate if a 
			// mobile processing file
			InputStream inputStream = fileConn.openInputStream();
			Image image = Image.createImage(inputStream);
			PImage pImage = new PImage(image);

			// Close the file
			fileConn.close();

			// Return the mobile image
			return pImage;
		}
		catch(IOException e)
		{
			// If any problem creating the connection or the input stream
			// throws the exception
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Reads the contents of a file and places it in a byte array.
	 *
	 * @param filename name of file to load
	 * @return the file bytes
	 */
	public static byte[] loadBytes(String filename)
	{
		try
		{
			// Open the file connection
			FileConnection fileConn = (FileConnection) Connector.open(filename);

			// Get the file size and create an array with the length
			int fileSize = (int) fileConn.fileSize();
			byte[] bytes = new byte[fileSize];

			// Open the inputstream and try to load all content
			DataInputStream dataInputStream = fileConn.openDataInputStream();
			dataInputStream.readFully(bytes);

			// Close the file
			fileConn.close();

			// Return the bytes
			return bytes;
		}
		catch(IOException e)
		{
			// If any problem creating the connection or the input stream
			// throws the exception
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Reads the contents of a file and creates a String array of its individual
	 * lines. 
	 *
	 * @param filename name of file to load
	 * @return the file lines
	 */
	public static String[] loadStrings(String filename)
	{
		try
		{
			// Open the file connection
			FileConnection fileConn = (FileConnection) Connector.open(filename);

			// Open the inputstream, create a midp image and encapsulate if a 
			// mobile processing file
			InputStream inputStream = fileConn.openInputStream();

			// To read all the characters inside a line
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			// Character to read
			int c;

			// Create a empty list of images
			Vector lines = new Vector();

			while(true)
			{
				// Read the next char
				c = inputStream.read();

				// If the character ends a line
				if(c == '\n' || c == '\r' || c == -1)
				{
					// Remove spaces and add the line to the vector
					String line = out.toString().trim();

					// Check if the names have characters
					if(line.length() > 0)
						lines.addElement(line);

					// Break the loop 
					if(c == -1)
						break;
					// Clear the line content
					out.reset();
				}
				else
					// Write the character inside the current line
					out.write(c);
			}

			// Copy the vector to the array names
			String[] linesValues = new String[lines.size()];
			lines.copyInto(linesValues);

			// Return the lines
			return linesValues;			
		}
		catch(IOException e)
		{
			// If any problem creating the connection or the input stream
			// throws the exception
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Opposite of loadBytes(), will write an entire array of bytes to a file.
	 *
	 * @param filename name of file to save
	 * @param bytes array of bytes to be written
	 */
	public static void saveBytes(String filename, byte[] bytes)
	{
		try
		{
			// Open the file connection
			FileConnection fileConn = (FileConnection) Connector.open(filename);

			// Check if file exists, if not created
			if(!fileConn.exists())
				fileConn.create();

			// Open the oututstream and write the bytes
			OutputStream outputStream = fileConn.openOutputStream();
			outputStream.write(bytes);
			
			// Close the file
			fileConn.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			
			// If any problem creating the connection or the input stream
			// throws the exception
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Writes an array of strings to a file, one line per string. 
	 *
	 * @param filename name of file to save
	 * @param strings string array to be written
	 */
	public static void saveStrings(String filename, String[] strings)
	{
		try
		{
			// Open the file connection
			FileConnection fileConn = (FileConnection) Connector.open(filename);

			// Check if file exists, if not created
			if(!fileConn.exists())
				fileConn.create();

			// Open an print stream 
			OutputStream outputStream = fileConn.openOutputStream();
			PrintStream printStream = new PrintStream(outputStream);

			// Print each string into the file
			for(int i=0; i<strings.length; i++)
				printStream.println(strings[i]);
			
			// Close the file
			fileConn.close();
		}
		catch(IOException e)
		{
			// If any problem creating the connection or the input stream
			// throws the exception
			throw new RuntimeException(e.getMessage());
		}
	}
}
