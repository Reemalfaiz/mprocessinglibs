/*

	MVoce - Voice Library for Processing

	Copyright (c) 2009 Mary Jane Soft - Marlon J. Manrique
	
	http://www.maryjanesoft.com
	http://www.marlonj.com

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

package mjs.processing.mvoce;

/**
 * Allows synthesize voice or recognize speech from a grammar 
 */ 
public abstract class MVoce
{
	/**
	 * Requests that the given string be synthesized as soon as possible. 
	 * 
	 * @param message The message to sinthesize
	 */
	public abstract void synthesize(String message);
	
	/**
	 * Tells the speech synthesizer to stop synthesizing. 
	 * This cancels all pending messages.
	 */
	public abstract void stopSynthesizing();
	
	/**
    * Returns the number of recognized strings currently in the recognizer's 
    * queue.
    * 
    * @return Number of tokens recognized
    */      	
	public abstract int numTokens();
	
	/**
	 * Returns and removes the oldest recognized string from the recognizer's 
	 * queue.
	 *
	 * @return oldest recognized string
	 */ 
	public abstract String nextToken();

	/**
	 * Enables and disables the speech recognizer. 
	 *
	 * @param e true to enable the recognizer, false other wise
	 */	
	public abstract void recognizerEnable(boolean e);
	
	/**
	 * Returns true if the recognizer is currently enabled. 
	 *
	 * @return true if the recognizer is currently enabled, false otherwise
	 */ 
	public abstract boolean isRecognizerEnabled();
	
	/**
	 * Free resources 
	 */
	public abstract void destroy();
}
