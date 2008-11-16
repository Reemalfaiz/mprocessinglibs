/*

	MPush - Asynchronous Connections Library for Mobile Processing

	Copyright (c) 2006 Mary Jane Soft - Marlon J. Manrique
	
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
	
*/

/**
 * The MPush library allows the applications receive asynchronous data, 
 * as informaction becomes available, instead of forcing the application
 * to use synchronous polling techniques that increase resource use or latency
 * 
 * The push registry enables MIDlets to set themselves up to be launched automatically,
 * without user initiation. The push registry manages network- and timer-initiated 
 * MIDlet activation; that is, it enables an inbound network connection or a timer-based 
 * alarm to wake a MIDlet up. For example, you can write a workgroup application that 
 * employs network activation to wake up and process newly received email, or new appointments 
 * that have been scheduled. Or you can use timer-based activation to schedule your MIDlet 
 * to synchronize with a server every so often then go to sleep.
 *
 * @author Mary Jane Soft - Marlon J. Manrique
 *
 * @libname MPush
 */

package mjs.processing.mobile.mpush;
