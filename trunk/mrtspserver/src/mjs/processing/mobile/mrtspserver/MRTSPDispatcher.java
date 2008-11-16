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

import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.microedition.io.SocketConnection;

/**
 * RTSP Server 
 */
class MRTSPDispatcher extends Thread
{
	/**
	 * End Line 
	 */
	private final static String CRLF = "\r\n";
	
	/**
	 * Response Status OK
	 */
	public final static int STATUS_OK = 200;
	
	/**
	 * Current RTSP Protocol version 
	 * @value 1.0
	 */
	public final static String RTSP_VERSION = "1.0";	

	/**
	 * Parent Server 
	 */
	private MRTSPServer server;
	
	/**
	 * Current connection 
	 */
	private SocketConnection connection;
	
	/**
	 * Create the client dispatcher thread  
	 *
	 * @param connection client connection 
	 */
	public MRTSPDispatcher(MRTSPServer server, SocketConnection connection)
	{
		this.server = server;
		this.connection = connection;
	}
	
	/**
	 * Handle the client request 
	 *
	 * @param connection Current connection with the client
	 */	 
	public void run()
	{
		try
		{
			// Send client available event 
			server.enqueueLibraryEvent(
				MRTSPServer.EVENT_CLIENT_AVAILABLE,null);
	
			// Open the streams with the client 
			DataOutputStream output = connection.openDataOutputStream();
			DataInputStream input = connection.openDataInputStream();		

			// Open a permanent link with the client 
			boolean sessionOpen = true;

			// While the client is connected
			// response the client requests 
			while(sessionOpen)
			{
				// Get the client request 
				MRTSPRequest request = new MRTSPRequest(connection,input);
			
				// Send the options
				switch(request.getType())
				{
					case MRTSPRequest.OPTIONS :
						handleOptions(request,output);
						break;
					
					case MRTSPRequest.DESCRIBE :
						handleDescribe(request,output);
						break;		 
					
					case MRTSPRequest.SETUP :
						handleSetup(request,output);
						break;
				
					case MRTSPRequest.PLAY :
						handlePlay(request,output);
						break;
					
					case MRTSPRequest.TEARDOWN :
						handleTeardown(request,output);
						sessionOpen = false;
						break;
				}
			}
		
			// Close the streams 
			input.close();
			output.close();	
		
			// Close the connection 
			connection.close();
		
			// Send client available event 
			server.enqueueLibraryEvent(
				MRTSPServer.EVENT_CLIENT_DISPATCHED,null);
		}
		catch(Exception e)
		{
			// Send event error 
			server.enqueueLibraryEvent(
				MRTSPServer.EVENT_ERROR,e.getMessage());		
		}		
	}
	
	
	/**
	 * Send the options for the server 
	 *
	 * @param request The request  
	 * @param output Output stream
	 */
	private void handleOptions(MRTSPRequest request, DataOutputStream output)
		throws Exception
	{					
		// Send response status
		sendStatus(output,STATUS_OK);
		 		
		// Send sequence number
		sendSequenceNumber(output,request.getSequenceNumber());
		
		// Send Available methods 
		writeLine(output,"Public: OPTIONS, DESCRIBE, SETUP, TEARDOWN, PLAY, PAUSE");
		writeLine(output,"");
	}
	
	/**
	 * Send the describe response to the client  
	 *
	 * @param sequenceNumber Request number 
	 * @param uri Request URI 
	 * @param input Input stream  
	 * @param output Output stream
	 */
	private void handleDescribe(MRTSPRequest request, DataOutputStream output)
		throws Exception
	{					
		// Send response status
		sendStatus(output,STATUS_OK);
		 		
		// Send sequence number
		sendSequenceNumber(output,request.getSequenceNumber());
		
		// Get the content description 
		String description = getDescription(request);
			
		// Send Base URI
		writeLine(output,"Content-base: " + request.getUri() + "/");
		writeLine(output,"Content-type: " + "application/sdp");
		writeLine(output,"Content-length: " + (description.length() + 2));
		writeLine(output,"");
		writeLine(output,description);
	}

	/**
	 * Send the setup for the content 
	 *
	 * @param sequenceNumber Request number
	 * @param uri Request URI 
	 * @param input Input stream  
	 * @param output Output stream
	 */
	private void handleSetup(MRTSPRequest request, DataOutputStream output)
		throws Exception
	{
		// Create session  
		MRTSPSession session = server.createSession(request);
			
		// Send response status
		sendStatus(output,STATUS_OK);
		 		
		// Send sequence number
		sendSequenceNumber(output,request.getSequenceNumber());
		
		// Port info
		int serverPort = session.getServerPort();		
		int clientPort = request.getClientPort();
		
		// Send Session and server transport info  
		writeLine(output,"Session: " + session.getId());
		writeLine(output,"Transport: RTP/AVP;unicast;" 
			+ "clientPort=" + clientPort + "-" + (clientPort+1)
			+ ";server_port=" + serverPort + "-" + (serverPort+1));
		writeLine(output,"");
	}
	
	/**
	 * Begin Play the File   
	 *
	 * @param sequenceNumber Request number 
	 * @param uri Request URI 
	 * @param input Input stream  
	 * @param output Output stream
	 */
	private void handlePlay(MRTSPRequest request, DataOutputStream output)
		throws Exception
	{					
		// Send response status
		sendStatus(output,STATUS_OK);
		
		// Read Session Line
		MRTSPSession session = 
			server.getSession(request.getSession());
		
		// Send sequence number
		sendSequenceNumber(output,request.getSequenceNumber());
		
		// Send Base URI
		writeLine(output,"Session: " + request.getSession());
		writeLine(output,"");

		// Create streams 
		createStreams(request,session);
	}
	
	/**
	 * Stop the playing of a file    
	 *
	 * @param sequenceNumber Request number 
	 * @param uri Request URI 
	 * @param input Input stream  
	 * @param output Output stream
	 */
	private void handleTeardown(MRTSPRequest request, DataOutputStream output)
		throws Exception
	{					
		// Send response status
		sendStatus(output,STATUS_OK);
		
		// Get the session and remove it from the server 
		int session = request.getSession();
		server.removeSession(session );		
		
		// Send sequence number
		sendSequenceNumber(output,request.getSequenceNumber());
		
		// Send Base URI
		writeLine(output,"Session: " + session);
		writeLine(output,"");
	}		
	
	/**
	 * Return the status line for the request 
	 *
	 * @param status Response status 
	 */
	private String getStatus(int status)
	{
		// Status name 
		String statusText = "";
		
		// Check for status id 
		switch(status)
		{
			case STATUS_OK : statusText = "Ok"; break;
		}
		
		// Return the status line 
		return "RTSP/" + RTSP_VERSION 
			+ " " + status + " " + statusText; 
	}
	
	/**
	 * Get the request number in the request line 
	 *
	 * @param line Sequence request line 
	 * @return sequence number 
	 */
	private int getSequenceNumber(String line)		
	{
		// Return the number after "CSeq: "
		return Integer.parseInt(line.substring(6).trim());
	}
	
	/**
	 * Send the response status 
	 * 
	 * @param output Output stream
	 * @param status The response status 
	 */
	private void sendStatus(DataOutputStream output, int status) 
		throws Exception
	{
		// Get the status line and write to the client 
		writeLine(output,getStatus(STATUS_OK));	
	}
	
	/**
	 * Send the sequence number  
	 * 
	 * @param output Output stream
	 * @param status The response sequence number  
	 */
	private void sendSequenceNumber(DataOutputStream output, 
		int sequenceNumber) throws Exception
	{
		// Write a line with the sequence info 
		writeLine(output,"CSeq: " + sequenceNumber);
	}
	
	/**
	 * Line to write to the client 
	 * 
	 * @param output Output stream
	 * @param line Line to write  
	 */	
	private void writeLine(DataOutputStream output, String line)
		throws Exception
	{
		// Add the CRLF to the line and write 
		line += CRLF;
		output.write(line.getBytes());
	}
	
	/**
	 * Return the description of the URI in SDP format at the request 
	 *
	 * @param request The request 
	 * @return description of the uri 
	 */
	private String getDescription(MRTSPRequest request)
	{
		// Create a buffer 
		StringBuffer sb = new StringBuffer();
		
		// Add version 
		sb.append("v=0" + CRLF);
		
		// Add no owner - 0 0 in local address  
		sb.append("o=- 0 0 IN IP4 " + request.getServerAddress() + CRLF);
		
		// Session Name : RTPSession
		sb.append("s=RTPSession" + CRLF);

		// Time the session is active 		
		sb.append("t=0 0" + CRLF);
		
		// Describe content 
		// m=<type> port RTP/AVP <payloadType>
		sb.append("m=audio 0 RTP/AVP 96" + CRLF);
		
		// Additional media information 
		// a=rtpmap:<payload type> <encoding>
		// L8 = 8bits, 8000 frequency, 1 Channel
		sb.append("a=rtpmap:96 L8/8000/1" + CRLF);
		
		// Return the description 
		return sb.toString();	
	}
	
	/**
	 * Create the streams for the content 
	 *
	 * @param request current request 
	 * @param session current session 
	 */
	private void createStreams(MRTSPRequest request, MRTSPSession session)
	{
		// Get the file
		String uri = request.getUri();
		
		// Get resource part of the uri 
		// rtsp://server/resource 
		String fileName = uri.substring(uri.indexOf("/",7));
		
		// Send the stream 
		MRTSPStream stream = new MWavStream(session,fileName);
		stream.start();	
	} 
}