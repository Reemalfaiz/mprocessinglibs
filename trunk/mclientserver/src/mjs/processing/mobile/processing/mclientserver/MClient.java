/*

	MClientService - Cliente Server Utility for Mobile Processing

	Copyright (c) 2005-2006 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mclientserver;

import java.io.*;
import javax.microedition.io.*;

/**
 * MClient encapsulation for Client-Server implementation.
 *
 * This class provides methods to create client objects from services based on
 * sockets, bluetooth and others.
 *
 * @author Mary Jane Soft - Marlon J. Manrique.
 */

public class MClient
{
	/**
	 * The connection with the server.
	 */
	 
	protected Connection connection;

	/**
	 * The stream used to read data from the server.
	 */

	private DataInputStream dis;

	/**
	 * The stream used to write data from the server.
	 */

	private DataOutputStream dos;

	/**
	 * Create a client with the connection specified.
	 *
	 * @param connection the connection established with the server.
	 */

	public MClient(Connection connection)
	{
		this.connection = connection;
	}
	
	/**
	 * Create an empty client
	 *
	 * @since 0.4 
	 */
	protected MClient()
	{
	}
	
	/**
	 * Update the connection 
	 *
	 * @param connection the connection established with the server.
	 *
	 * @since 0.4
	 */
	protected void setConnection(Connection connection)
	{
		this.connection = connection;
	}
	 

	/**
	 * Returns the number of bytes that can be read from this input stream without blocking.
	 * 
	 * Is used to verify if a client is sending data to the server.
	 *
	 * @return the number of bytes that can be read from the input stream without blocking.
	 */
	
	public int available()
	{
		try
		{
			// Get the input and return the available value
			return getInput().available();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		// If error nothing to read
		return 0;
	}

	/**
	 * Reads the next byte of data from this input stream.
	 * The value byte is returned as an int in the range 0 to 255.
	 * If no byte is available because the end of the stream has been reached,
	 * the value -1 is returned. 
	 * This method blocks until input data is available, the end of the stream 
	 * is detected, or an exception is thrown.
	 *
	 * @return the next byte of data, or -1 if the end of the stream is reached.
	 */

	public int read()
	{
		try
		{
			// Get the input and read
			return getInput().read();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		// If error the end of the stream is reached
		return -1;
	}
	
	/**
	 * Reads the next integer of data from this input stream.
	 *
	 * This method blocks until input data is available, the end of the stream 
	 * is detected, or an exception is thrown.
	 *
	 * @return the next integer
	 *
	 * @since 0.3
	 */

	public int readInt()
	{
		try
		{
			// Get the input and read
			return getInput().readInt();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}	 
	
	/**
	 * Reads the next boolean of data from this input stream.
	 *
	 * This method blocks until input data is available, the end of the stream 
	 * is detected, or an exception is thrown.
	 *
	 * @return the next integer
	 *
	 * @since 0.3
	 */

	public boolean readBoolean()
	{
		try
		{
			// Get the input and read
			return getInput().readBoolean();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}	 	

	/**
	 * See the general contract of the readChar method of DataInput.
	 *
	 * Bytes for this operation are read from the contained input stream. 
	 *
	 * @return the next two bytes of this input stream as a Unicode character.
	 */

	public char readChar()
	{
		try
		{
			// Get the input and read char
			return getInput().readChar();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		// If error return character '\0'
		return '\0';
	}

	/**
	 * Reads some number of bytes from the input stream and stores them into 
	 * the buffer array b.
	 * The number of bytes actually read is returned as an integer.
	 * This method blocks until input data is available, end of file is 
	 * detected, or an exception is thrown.
	 *
	 * @param b the buffer into which the data is read.
	 * @return the total number of bytes read into the buffer, or -1 if 
	 * 			there is no more data because the end of the stream has 
	 *			been reached.
	 */
	 
	public int readBytes(byte[] bytes)	
	{
		// Get the input and read bytes
		return readBytes(bytes,0,bytes.length);
	}

	/**
	 * Reads some number of bytes from the input stream and stores them into 
	 * the buffer array b.
	 * The number of bytes actually read is returned as an integer.
	 * This method blocks until input data is available, end of file is 
	 * detected, or an exception is thrown.
	 *
	 * @param bytes the buffer into which the data is read.
	 * @param offset index into buffer where to start storing data
	 * @param length maximum number of bytes to store
	 * 
	 * @return the total number of bytes read into the buffer, or -1 if 
	 * 			there is no more data because the end of the stream has 
	 *			been reached.
	 *
	 * @since 0.3	 
	 */
	 
	public int readBytes(byte[] bytes, int offset, int length)	
	{
		try
		{
			// Get the input and read bytes
			return getInput().read(bytes, offset, length);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/** 
	 * Reads in a string that has been encoded using a modified UTF-8 format.
	 * The general contract of readUTF  is that it reads a representation of a 
	 * Unicode character string encoded in Java modified UTF-8 format; this 
	 * string of characters is then returned as a String.
	 * 
	 * @param a Unicode string if available or null.
	 */

	public String readString()
	{
		return readUTF();
	}

	/** 
	 * Reads in a string that has been encoded using a modified UTF-8 format.
	 * The general contract of readUTF  is that it reads a representation of a 
	 * Unicode character string encoded in Java modified UTF-8 format; this 
	 * string of characters is then returned as a String.
	 * 
	 * @param a Unicode string if available or null.
	 *	 
	 * @since 0.3	 
	 */

	public String readUTF()
	{
		try
		{
			// Get the input and read bytes
			return getInput().readUTF();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Writes the specified byte (the low eight bits of the argument b) to the 
	 * underlying output stream. 
	 * 
	 * @param value the byte value to be written.
	 */

	public void write(int value)
	{
		try
		{
			// Get the output and write
			getOutput().write(value);
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Writes the specified boolean value
	 * 
	 * @param value the boolean value to be written.
	 *
	 * @since 0.3
	 */

	public void writeBoolean(boolean value)
	{
		try
		{
			// Get the output and write
			getOutput().writeBoolean(value);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Writes the specified string like bytes
	 * 
	 * @param s a string
	 *
	 * @since 0.3
	 */

	public void writeBytes(String s)
	{
		write(s.getBytes());
	}
	
	/**
	 * Writes an char value to the output stream. 
	 * 
	 * @param value the char value to be written.
	 *
	 * @since 0.3
	 */

	public void writeChar(int value)
	{
		try
		{
			// Get the output and write
			getOutput().writeChar(value);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}		

	/**
	 * Writes an integer value to the output stream. 
	 * 
	 * @param value the integer value to be written.
	 *
	 * @since 0.3
	 */

	public void writeInt(int value)
	{
		try
		{
			// Get the output and write
			getOutput().writeInt(value);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}		
	
	/**
	 * Writes an char value to the output stream. 
	 * 
	 * @param value the char value to be written.
	 */

	public void write(char value)
	{
		writeChar(value);
	}	

	/**
	 * Writes to the output stream all the bytes in array b. If b is null, a 
	 * NullPointerException is thrown. 
	 * If b.length is zero, then no bytes are written. Otherwise, the byte 
	 * b[0] is written first, then b[1], 
	 * and so on; the last byte written is b[b.length-1].
	 * 
	 * @param bytes the data.
	 */

	public void write(byte[] bytes)
	{
		try
		{
			getOutput().write(bytes);
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Writes two bytes of length information to the output stream, followed by
	 * the Java modified UTF representation of every character in the string s.
	 * If s is null, a NullPointerException is thrown. 
	 *
	 * Each character in the string s  is converted to a group of one, two, or 
	 * three bytes, depending on the value of the character.
	 * 
	 * @param value the string value to be written.
	 */

	public void write(String value)
	{
		writeUTF(value);
	}

	/**
	 * Writes two bytes of length information to the output stream, followed by
	 * the Java modified UTF representation of every character in the string s.
	 * If s is null, a NullPointerException is thrown. 
	 *
	 * Each character in the string s  is converted to a group of one, two, or 
	 * three bytes, depending on the value of the character.
	 * 
	 * @param value the string value to be written.
	 *
	 * @since 0.3
	 */

	public void writeUTF(String value)
	{
		try
		{
			getOutput().writeUTF(value);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * Stop the client disconnecting of the server
	 */

	public void stop()
	{
		try
		{
			// Close input and output streams if was opened
			
			if(dis != null)
				dis.close();

			if(dos != null)
				dos.close();

			// Close the connection

			if(connection != null)
				connection.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Returns the data input stream of the connection.
	 *
	 * @return de data input stream to read data.
	 */

	private DataInputStream getInput()
	{
		try
		{
			// if is not open already, get the connection
			if(dis == null)
				dis = ((InputConnection) connection).openDataInputStream();	
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		// Return the stream
		return dis;
	}

	/**
	 * Returns the data output stream of the connection.
	 *
	 * @return de data output stream to read data.
	 */

	private DataOutputStream getOutput()
	{
		try
		{
			// if is not open already, get the connection
			if(dos == null)
				dos = ((OutputConnection) connection).openDataOutputStream();	
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		// Return the stream
		return dos;
	}	
	
	/**
	 * Reads and discards bytes. 
	 *
	 * @param numBytes the number of bytes to read and discard
	 */
	 
	public int skipBytes(int numBytes)
	{
		try
		{
			// Get the input and read bytes
			return getInput().skipBytes(numBytes);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}		
	}

	/**
	 * Flushes this stream by writing any buffered output to the underlying 
	 * stream	
	 *
	 * @since 0.3
	 */
	 
	public void flush()
	{
		try
		{
			getOutput().flush();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}				
	}	
}
