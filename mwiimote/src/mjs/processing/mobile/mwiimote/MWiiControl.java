/*

	MWiimote - Wii Remote Library for Mobile Processing

	Copyright (c) 2008 Mary Jane Soft - Marlon J. Manrique
	
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

package mjs.processing.mobile.mwiimote;

import java.util.Enumeration;
import java.util.Vector;

import javax.bluetooth.L2CAPConnection;

import javax.microedition.io.Connector; 

import mjs.processing.mobile.mbt.MDevice;
import mjs.processing.mobile.mbt.MService;

import processing.core.PMIDlet;

/**
 * Wii Remote Control  
 */
public class MWiiControl
{
	/**
	 * Left button indentifier
	 * @value 1 
	 */	 
	public final static int BUTTON_LEFT = 1;

	/**
	 * Right button indentifier
	 * @value 2 
	 */	 
	public final static int BUTTON_RIGHT = 2;
	
	/**
	 * Down button indentifier
	 * @value 4 
	 */	 
	public final static int BUTTON_DOWN = 4;
	
	/**
	 * Up button indentifier
	 * @value 8 
	 */	 
	public final static int BUTTON_UP = 8;

	/**
	 * Plus button indentifier
	 * @value 16 
	 */	 	
	public final static int BUTTON_PLUS = 16;
	
	/**
	 * Two button indentifier
	 * @value 32 
	 */	 	
	public final static int BUTTON_TWO = 32;
	
	/**
	 * One button indentifier
	 * @value 64 
	 */	 	
	public final static int BUTTON_ONE = 64;
	
	/**
	 * B button indentifier
	 * @value 128 
	 */	 	
	public final static int BUTTON_B = 128;
	
	/**
	 * A button indentifier
	 * @value 256 
	 */	 	
	public final static int BUTTON_A = 256;
	
	/**
	 * Minus button indentifier
	 * @value 512 
	 */	 	
	public final static int BUTTON_MINUS = 512;
	
	/**
	 * Home button indentifier
	 * @value 1024 
	 */	 	
	public final static int BUTTON_HOME = 1024;
	
	/**
	 * Event Button pressed 
	 */	 	
	public final static int EVENT_BUTTON_PRESSED = 1;	
			 
	/**
	 * Event Data Send  
	 */	 	
	public final static int EVENT_DATA_WRITE = 2;	

	/**
	 * Data Input Packet 
	 */
	protected final static byte DATA_INPUT = (byte) 0xA1;
	
	/**
	 * Core Buttons  
	 */
	protected final static byte DATA_INPUT_CORE_BUTTONS = (byte) 0x30;
	
	/**
	 * Core Buttons and Accelerometer
	 */
	protected final static byte DATA_INPUT_CORE_BUTTONS_ACCELEROMETER 
		= (byte) 0x31;

	/**
	 * Core Buttons, Accelerometer and IR
	 */
	protected final static byte DATA_INPUT_CORE_BUTTONS_ACCELEROMETER_IR 
		= (byte) 0x33;
		
	/**
	 * Data Read  
	 */
	protected final static byte DATA_INPUT_READ = (byte) 0x21;

	/**
	 * Reading nothing
	 */
	private final static byte READ_NONE = 0;		
	
	/**
	 * Reading acceleration calibration data
	 */
	private final static byte READ_ACCELERATION_CALIBRATION_DATA = 1;		
	 
	/**
	 * Parent Library
	 */
	private MWiimote mWiimote;
	
	/**
	 * Wii Remote Device 
	 */
	private MDevice mDevice;
	
	/**
	 * Wii proxy service 
	 */
	private MService mService;
	
	/**
	 * Input stream 
	 */
	private L2CAPConnection input;

	/**
	 * Output stream 
	 */
	private L2CAPConnection output;
	
	/**
	 * Control leds 
	 */
	private boolean[] leds;
	
	/**
	 * Input buffer 
	 */
	private byte[] buffer;
	
	/**
	 * The reader is running 
	 */
	private boolean readerRunning;
	
	/**
	 * Read status
	 */
	private int readStatus;

	/**
	 * Accelerometer
	 */
	public MWiiAccelerometer accelerometer;
	
	/**
	 * Client to send the data 
	 */
	public MWiiControlClient mWiiControlClient;
	
	/**
	 * The ir is enable 
	 * @since 0.2
	 */
	private boolean irEnable;

	/**
	 * The accelerometer is enable 
	 * @since 0.2
	 */
	private boolean accelEnable;
	
	/**
	 * Invert Y coordinate values 
	 * @since 0.2
	 */
	private boolean invertY;
	
	/**
	 * Screen width 
	 * @since 0.2
	 */
	private int screenWidth;	

	/**
	 * Screen height 
	 * @since 0.2
	 */
	private int screenHeight;	
		
	/**
	 * IR data
	 * @since 0.2
	 */
	private int[] irData;
	
	/**
	 * Battery Level
	 * @since 0.2
	 */
	private int batteryLevel;
	
	/**
	 * Data listeners 
	 */
	private Vector listeners;
	
	/**
	 * Create a Wii Remote control  
	 *
	 * @param pWiimote Parent library
	 * @param mDevice Bluetooth device  
	 * @param mService MWiimote proxy service
	 * @param mWiimoteProxy Proxy to use
	 */
	protected MWiiControl(MWiimote mWiimote, MDevice mDevice, MService mService)
	{
		// Set objects 
		this.mWiimote = mWiimote;
		this.mDevice = mDevice;
		this.mService = mService;
		
		// Create an empty vector or listeners 
		listeners = new Vector();
		
		// Create the leds array 
		leds = new boolean[4];
		
		// Init ir data
		initIRData();
		
		// Init screen size 
		screenWidth = 1024;
		screenHeight = 720;
		
		// Create input buffer 
		buffer = new byte[512];
		
		// Create accelerometer 
		accelerometer = new MWiiAccelerometer();
		
		// Open the communication 
		open();
	}
	
	/** 
	 * Initilize the ir data 
	 */
	private void initIRData()
	{
		// Create array 
		irData = new int[12];
		
		// Set ir init values 
		irData[0] = 1023;
		irData[1] = 1023;
		irData[2] = 15;
		irData[3] = 1023;
		irData[4] = 1023;
		irData[5] = 15;
		irData[6] = 1023;
		irData[7] = 1023;
		irData[8] = 15;
		irData[9] = 1023;
		irData[10] = 1023;
		irData[11] = 15;	
	}
	
	/**
	 * Create a Wii Remote control  
	 *
	 * @param pWiimote Parent library
	 * @param mService MWiimote proxy service
	 * @param mWiimoteProxy Proxy to use  
	 */
	protected MWiiControl(MWiimote mWiimote, MService mService)
	{
		this(mWiimote,null,mService);
	}
	
	/**
	 * Create a Wii Remote control  
	 *
	 * @param pWiimote Parent library
	 * @param mDevice Bluetooth device  
	 * @param mWiimoteProxy Proxy to use
	 */
	protected MWiiControl(MWiimote mWiimote, MDevice mDevice)
	{
		this(mWiimote,mDevice,null);
	}		
	
	/**
	 * Open the connection with the control 
	 */
	public void open()
	{
		try
		{		
			// If a mservice is specified a proxy is use 
			if(mService != null)
			{
				// Get servie url 
				String url = mService.url();
				
				// Connect to the service, use the same connection 
				// to send and read data 
				output = (L2CAPConnection) Connector.open(url);
				
				// Set same connection to input 
				input = output;
			}
			// Connect directly to the wiimote 
			else
			{
				// Set l2cap address 
				String url = "btl2cap://" + mDevice.address;
		
				// Open input and output in different channels
				output = (L2CAPConnection) 
					Connector.open(url + ":11",Connector.WRITE); 
				input = (L2CAPConnection) 
					Connector.open(url + ":13",Connector.READ);
			
				// Configure the current input, only buttons 
				sendInputReport((byte) 0x30);
			
				// Turn on led 1 		
				led(1,true);	
			}
			
			// Start the reader thread
			startReader();
		}
		catch(Exception e)
		{
			// Fire error 
			enqueueLibraryEvent(mWiimote,MWiimote.EVENT_ERROR,e.getMessage());
		}
	}
	
	/**
	 * Close the connections with the control 
	 */
	public void close()
	{
		try
		{
			// Close connections 
			input.close();
			
			// Close the other connection 
			if(mService == null)
				output.close();			
		}
		catch(Exception e)
		{
			// Fire error 
			enqueueLibraryEvent(this,MWiimote.EVENT_ERROR,e.getMessage());
		}	
	}
	
	/**
	 * Turn on or off led
	 *
	 * @param led number 
	 * @param state true if on or false to off  
	 */
	public void led(int number, boolean state)
	{
		// Change led state 
		leds[number-1] = state;
		
		// Send leds signal		
		leds();
	}

	/**
	 * Change the state of all leds using a hexadecimal mask 0x0000.
	 * To turn on the leds use the mask and set 1 to turn on led 
	 * 0x1010 turn on the led 1 and 3 
	 *
	 * @param state State of the leds 
	 */
	public void leds(int state)
	{
		// Check for active values 
		for(int i=0; i<4; i++)
			if((state & (0x1000 >> i*4)) != 0)
				leds[i] = true;
			else
				leds[i] = false;
				
		// Send leds signal 
		leds();
	}
	
	/**
	 * Send the signal to turn leds on/off according current state.
	 * Example use leds(0x1111) to turn on all leds or led(1,false) to turn on 
	 * led one only   
	 */  	 
	private void leds()
	{
		// Led value, none active 
		byte ledValue = 0;
		
		// Set led mask for each led 
		if(leds[0])
			ledValue |= 0x10; 			
		if(leds[1])
			ledValue |= 0x20;			
		if(leds[2])
			ledValue |= 0x40;			
 		if(leds[3])
			ledValue |= 0x80; 
			 
		// Create package 
		byte[] data = { 0x52, 0x11, ledValue };
		
		// Send package 
		send(data);	
	}
	
	/**
	 * Check if any data is available from the control
	 *
	 * @return true if data available, false otherwise 
	 */
	public boolean available()
	{
		try
		{
			// Return if a packet is available to read
			if(input != null)  
				return input.ready();
		}
		catch(Exception e)
		{
			// Fire error 
			enqueueLibraryEvent(this,MWiimote.EVENT_ERROR,e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * Read the input data 
	 */
	public void read()
	{
		try
		{
			// Read the data send by the control 
			int numBytes = input.receive(buffer);

			// Show packet info if debug 
			if(MWiimote.debug)
			{			
				// Write to the console 
				for(int i=0; i<numBytes; i++)
					System.out.print(buffer[i] + " ");
					
				// Change line 
				System.out.println();
			}
			
			// If no data return  
			if(numBytes < 0)
				return;
				
			// If a proxy is waiting the data, send to it  
			if(mWiiControlClient != null)
			{
				// Copy to a new buffer 
				byte[] data = new byte[numBytes];
				System.arraycopy(buffer,0,data,0,numBytes);
				
				// Send the data to the proxy 
				mWiiControlClient.send(data);
			}
				
			// Check type of packet
			switch(buffer[0])
			{
				// If input data, read values  
				case DATA_INPUT : parseInput(buffer); break;
				
				// Input type unknow 
				default : System.out.println("Input ????");
			}
		}
		catch(Exception e)
		{
			// Fire error
			e.printStackTrace(); 
			enqueueLibraryEvent(this,MWiimote.EVENT_ERROR,e.getMessage());
		}		
	}
	
	/** 
	 * Parse the input data to retrieve values
	 *
	 * @param data data buffer   
	 */
	private void parseInput(byte[] data)
	{
		// Check type of data 
		switch(data[1])
		{
			// Check core buttons only 
			case DATA_INPUT_CORE_BUTTONS : 
				parseButtons(data);
				break;
			
			// Check core buttons and accelerometer 
			case DATA_INPUT_CORE_BUTTONS_ACCELEROMETER :
				parseButtons(data);
				parseAccelerometer(data);
				break;
				
			// Check core buttons, accelerometer and IR
			case DATA_INPUT_CORE_BUTTONS_ACCELEROMETER_IR :
				parseButtons(data);
				parseAccelerometer(data);
				parseIR(data);				
				break;

			// Read Battery Level   
			case 0x20 :
				parseBatteryLevel(data);
				break;
				
			// Read Data Response   
			case DATA_INPUT_READ :
				readDataResponse();
				break;
		}
	}
	
	/**
	 * Read some data from the control
	 */
	private void readDataResponse()
	{
		// Check status
		switch(readStatus)
		{
			// Read acceleration calibration data 
			case READ_ACCELERATION_CALIBRATION_DATA :
				readAcceleratorCalibrationData(buffer);
				break;
		}
	}
	
	/** 
	 * Check buttons input data
	 *
	 * @param data data buffer   
	 */
	private void parseButtons(byte[] data)
	{
		// Set button mask 
		int mask = 0;
		
		// Check first Byte 
		if((data[2] & (byte) 0x01) > 0)
			mask |= BUTTON_LEFT;
		if((data[2] & (byte) 0x02) > 0)
			mask |= BUTTON_RIGHT;
		if((data[2] & (byte) 0x04) > 0)
			mask |= BUTTON_DOWN;
		if((data[2] & (byte) 0x08) > 0)
			mask |= BUTTON_UP;
		if((data[2] & (byte) 0x10) > 0)
			mask |= BUTTON_PLUS;			
			
		// Check second Byte
		if((data[3] & (byte) 0x01) > 0)
			mask |= BUTTON_TWO;
		if((data[3] & (byte) 0x02) > 0)
			mask |= BUTTON_ONE;
		if((data[3] & (byte) 0x04) > 0)
			mask |= BUTTON_B;
		if((data[3] & (byte) 0x08) > 0)
			mask |= BUTTON_A;
		if((data[3] & (byte) 0x10) > 0)
			mask |= BUTTON_MINUS;			
		if((data[3] & (byte) -0x80) < 0)
			mask |= BUTTON_HOME;	
			
		// Fire button pressed event 
		enqueueLibraryEvent(this,EVENT_BUTTON_PRESSED,new Integer(mask));
	}
	
	/**
	 * Calibrate Accelerometer 
	 */
	public void calibrateAccelerometer()
	{		
		// Set accel calibration read state 
		readStatus = READ_ACCELERATION_CALIBRATION_DATA; 
		
		// Read the calibrarion data 
		readData(0x16,8);
	}
	
	/**
	 * Read the acceleration calibration data 
	 *
	 * @para buffer Data send by the control
	 */
	private void readAcceleratorCalibrationData(byte[] buffer)
	{
		// Get zero point values 
		int zeroX = buffer[7] & 0xFF;
		int zeroY = buffer[8] & 0xFF;
		int zeroZ = buffer[9] & 0xFF;
		
		// Get one point values 
		int oneX = buffer[11] & 0xFF;
		int oneY = buffer[12] & 0xFF;
		int oneZ = buffer[13] & 0xFF;
		
		// Update accelerometer calibration 
		accelerometer.setCalibration(zeroX,zeroY,zeroZ,oneX,oneY,oneZ);
		
		// Change read status
		readStatus = READ_NONE;
	}
	
	/**
	 * Read data 
	 *
	 * @param offset Memory offset 
	 * @param size Size of the data 
	 */
	private void readData(int offset, int size)
	{
		// Split offset address 
		byte offset1 = (byte) (offset >> 24 & 0xFF);
		byte offset2 = (byte) (offset >> 16 & 0xFF);
		byte offset3 = (byte) (offset >> 8  & 0xFF);
		byte offset4 = (byte) (offset & 0xFF);
		
		// Split size 
		byte size1 = (byte) (size >> 8  & 0xFF);
		byte size2 = (byte) (size & 0xFF); 
				 
		// Create packet 
		byte[] data = { 0x52, 0x17,  
			offset1, offset2, offset3, offset4,
			size1, size2 };
			
		// Send packet 
		send(data);
	}
	
	/** 
	 * Check buttons input data
	 *
	 * @param data data buffer   
	 */
	private void parseAccelerometer(byte[] data)
	{
		// Set accelerometer current values 
		accelerometer.setValues(data[4] & 0XFF,
			data[5] & 0XFF,data[6] & 0XFF);
	}
	
	/**
	 * Change the input report to the value specified 
	 *
	 * @param value Data report value 
	 */
	private void sendInputReport(byte value)
	{
		// Create a packet with the report value 
		byte[] data = { 0x52, 0x12, 0x00, value };
		
		// Send packet 
		send(data);
	}
		
	/**
	 * Enable or disable accelerometer 
	 *
	 * @param enable true to enable, false to disable 
	 */
	public void accelerometer(boolean enable)
	{
		// If the accelerometer is not calibrate 
		if(enable && !accelerometer.calibrate)
			calibrateAccelerometer();
			
		// Set buttons data report 
		byte inputReport = 0x30;
		
		// If the accelerometer is enable set like 0x31  
		if(enable)
			inputReport |= 0x01;
		
		// Send report 
		sendInputReport(inputReport);
		
		// Update accelerometer status 
		accelEnable = enable;
	}
	
	/**
	 * Send the data specified to the control 
	 * 
	 * @param data Data to send 
	 */
	protected void send(byte[] data)
	{
		try
		{
			// Write the data 
			output.send(data);
			
			// Fire send event 
			enqueueLibraryEvent(this,EVENT_DATA_WRITE,data);			
		}
		catch(Exception e)
		{
			// Fire error 
			enqueueLibraryEvent(this,MWiimote.EVENT_ERROR,e.getMessage());
		}	
	}
	
	/**
	 * Start the input reader 
	 */
	public void startReader()
	{	
		// If the reader is already running do nothing 
		if(readerRunning)
			return;
			
		// Change reader state 
		readerRunning = true;
			
		// Create a new thread to read the data send by the control 
		new Thread()
		{
			public void run()
			{
				// While the reader is active 
				// read data and fire events 
				while(readerRunning)
					read();
			}
		}.start();
	}
	
	/**
	 * Stop the reader
	 */
	public void stopReader()
	{
		// Change reader state 
		readerRunning = false;
	}
	
	/**
	 * Set remote client of the control 
	 *
	 * @param mWiiControlClient Client of the control
	 */
	protected void setClient(MWiiControlClient mWiiControlClient)
	{
		this.mWiiControlClient = mWiiControlClient;
	}
	
	/**
	 * Method used to fire the library events
	 *
	 * @param library Library object that fires the event
	 * @param event Event identifier
	 * @param data Data provide in the event
	 */	 	
	protected void enqueueLibraryEvent(Object obj, int event, Object data)
	{
		mWiimote.enqueueLibraryEvent(obj,event,data);	
	}
	
	/**
	 * Return a string representation of the object 
	 *
	 * @return A string representation of the object
	 */
	public String toString()
	{
		return "MWiiControl"; 
	}
	
	/**
	 * Write Data 
	 *
	 * @param offset Memory offset
	 * @param data Data to write
	 *
	 * @since 0.2
	 */
	private void writeData(int offset, byte[] data)
	{
		// Create a packet to write 
		byte[] packet = new byte[23];
		
		// HID command
		packet[0] = 0x52;
		
		// Output Report 
		packet[1] = 0x16;
		
		// Split offset address 
		packet[2] = (byte) (offset >> 24 & 0xFF);
		packet[3] = (byte) (offset >> 16 & 0xFF);
		packet[4] = (byte) (offset >> 8  & 0xFF);
		packet[5] = (byte) (offset & 0xFF);
		
		// Set data length 
		packet[6] = (byte) data.length;
		
		// Copy data to packet 
		System.arraycopy(data,0,packet,7,data.length);
		
		// Send packet 
		send(packet);
	}
	
	/**
	 * Enable or disable ir camera 
	 *
	 * @param enable true to enable, false to disable
	 *
	 * @since 0.2 
	 */
	public void ir(boolean enable)
	{		
		// If ir is enable do nothing 
		if(irEnable && enable)
			return;
			
		// If ir is disable do Nothing
		if(!irEnable && !enable)
			return;

		// Enable the IR
		if(enable)
			enableIR();
		else
			disableIR();			
	}
	
	/**
	 * Enable the ir 
	 */
	private void enableIR()
	{
		// Enable IR Camera (Send 0x04 to Output Report 0x13) 
		byte[] data = { 0x52, 0x13, 0x04 };
		send(data);	
		
		// Enable IR Camera 2 (Send 0x04 to Output Report 0x1a)
		data = new byte[]{ 0x52, 0x1A, 0x04 };
		send(data);	
		
		// Write 0x08 to register 0xb00030
		data = new byte[] { 0x08 };
		writeData(0x04B00030,data);
		try{ Thread.sleep(50); } catch(Exception e){}
		
		// Write Sensitivity Block 1 to registers at 0xb00000
		data = new byte[] { 
			0x02, 0x00, 0x00, 0x71, 0x01, 0x00, (byte) 0xAA, 0x00, 0x64 };
		writeData(0x04B00000,data);

		// Write Sensitivity Block 2 to registers at 0xb0001a
		data = new byte[] { 0x63, 0x03 };
		writeData(0x04B0001A,data);
		try{ Thread.sleep(50); } catch(Exception e){}
		
		// Write Mode Number to register 0xb00033
		data = new byte[] { 0x03 };
		writeData(0x04B00033,data);
				
		// Send report 
		sendInputReport((byte) 0x33);
		
		// Change ir state 
		irEnable = true;	
	}
	
	/**
	 * Disable the IR
	 */
	private void disableIR()
	{
		// Disable IR Camera (Send 0x04 to Output Report 0x13) 
		byte[] data = { 0x52, 0x13, 0x00 };
		send(data);	
		
		// Disable IR Camera 2 (Send 0x00 to Output Report 0x1a)
		data = new byte[]{ 0x52, 0x1A, 0x00 };
		send(data);
		
		// Set buttons data report 
		byte inputReport = 0x30;
		
		// If the accelerometer is enable set like 0x31  
		if(accelEnable)
			inputReport |= 0x01;
		
		// Send report 
		sendInputReport(inputReport);			
	}
	
	/** 
	 * Parse ir information 
	 *
	 * @param data data buffer   
	 */
	private void parseIR(byte[] data)
	{
		// Get first point 
		irData[0] = data[7] & 0xFF ^ (data[9] & 0x30) << 4;
		irData[1] = data[8] & 0xFF ^ (data[9] & 0xC0) << 2;
		irData[2] = data[9] & 0x0F;
		
		// Get second point 
		irData[3] = data[10] & 0xFF ^ (data[12] & 0x30) << 4;
		irData[4] = data[11] & 0xFF ^ (data[12] & 0xC0) << 2;
		irData[5] = data[12] & 0x0F;
		
		// Get third point 
		irData[6] = data[13] & 0xFF ^ (data[15] & 0x30) << 4;
		irData[7] = data[14] & 0xFF ^ (data[15] & 0xC0) << 2;
		irData[8] = data[15] & 0x0F;
		
		// Get four point 
		irData[9] = data[16] & 0xFF ^ (data[18] & 0x30) << 4;
		irData[10] = data[17] & 0xFF ^ (data[18] & 0xC0) << 2;
		irData[11] = data[18] & 0x0F;
		
		// Fire event 
		fireIRAvailable();		
	}
	
	/**
	 * Return the coordinate X of the dot specified  
	 *
	 * @param index Number of the dot 
	 * @return coordinate X of the dot 
	 */
	public int dotX(int index)
	{
		// Get X coordinate 
		int x = irData[index*3];
		
		// If coordinate is not defined send -1 
		if(x == 1023)
			return -1;
		else
		{
			// If the coordinate is different to zero
			// Normalize the coordinate to the screen 
			if(x != 0)
				x = (x*1000/1024 * screenWidth)/1000;
				
			// Return the inverse value 
			return screenWidth - x;
		}	
	}
	
	/**
	 * Return the coordinate Y of the dot specified  
	 *
	 * @param index Number of the dot 
	 * @return the coordinate Y of the dot specified  
	 */
	public int dotY(int index)
	{
		// Get Y coordinate 
		int y = irData[index*3+1];
		
		// If coordinate is not defined send -1 
		if(y == 1023)
			return -1;
		else
		{
			// If the coordinate is different to zero
			// Normalize the coordinate to the screen 			
			if(y != 0)
				y = (y*1000/720 * screenHeight)/1000;
				
			// Return the inverse value if is specified 
			return invertY ? screenHeight - y : y;
		}	
	}

	/**
	 * Return the size of the dot specified  
	 *
	 * @param index Number of the dot
	 * @return size of the dot 
	 */
	public int dotSize(int index)
	{
		// Get the size of the dot 
		int size = irData[index*3+2];
		
		// If size is 15 the dot is not defined, return -1 
		if(size == 15)
			return -1;
		else
			return size;	
	}
	
	/**
	 * Return the ir data 
	 *
	 * @return the ir data
	 */
	public int[] irData()
	{
		return irData;
	}
	
	/**
	 * Invert y coordinate 
	 *
	 * @param enable If true inverts the coordinate 
	 * @since 0.2
	 */
	public void invertY(boolean enable)
	{	
		invertY = enable;
	}
	
	/**
	 * Update screen size 
	 *
	 * @param screenWidth Screen width 
	 * @param screenHeight Screen height 
	 * @since 0.2
	 */
	public void screenSize(int screenWidth, int screenHeight)
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	/**
	 * Get batteries level
	 *
	 * @return level  
	 * @since 0.2
	 */
	public synchronized int batteryLevel()
	{
		// Create a packet with the report value 
		byte[] data = { 0x52, 0x15, 0x00 };
		
		// Send packet 
		send(data);
		
		// Wait until read the battery level 
		try{ wait(); } catch(Exception e){ e.printStackTrace(); }
		
		// Return level
		return batteryLevel; 
	}
	
	/**
	 * Parse the batteries level 
	 *
	 * @param data data buffer
	 * @since 0.2   
	 */
	private synchronized void parseBatteryLevel(byte[] data)
	{
		// Calculate the battery level 
		// Convert the byte to int 
		// Avoid the float division multiply by 10000
		// Div by 100 to percent 
		batteryLevel = ((0xFF & data[7])*10000/0xC8)/100;
		
		// Wake the thread 
		notify();
	}
	
	/**
	 * Enable or disable Rumble
	 *
	 * @param enable true to enable, false to disable
	 * @since 0.2
	 */
	public void rumble(boolean enable)
	{
		// Get rumble value 
		byte rumble = enable ? (byte) 0x01 : (byte) 0x00;
		
		// Create a packet with the report value 
		byte[] data = { 0x52, 0x14, rumble };
		
		// Send packet 
		send(data);	
	}
	
	/**
	 * Add a data listener 
	 *
	 * @param listener Data listener
	 * @since 0.2
	 */
	protected void addListener(MWiiControlListener listener)
	{
		// If no present add 
		if(!listeners.contains(listener))
			listeners.addElement(listener); 
	}
	
	/**
	 * Fire IR data available 
	 *
	 * @since 0.2
	 */
	private void fireIRAvailable()
	{
		// Fire event in each listener
		for(Enumeration e = listeners.elements(); e.hasMoreElements(); )
			((MWiiControlListener) e.nextElement()).irAvailable();
	}
}	