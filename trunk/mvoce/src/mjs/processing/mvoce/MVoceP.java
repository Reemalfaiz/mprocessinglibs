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

import voce.SpeechInterface;

import processing.core.PApplet;

/**
 * Allows synthesize voice or recognize speech from a grammar in Processing  
 */ 
public class MVoceP extends MVoce
{
	/**
	 * Parent Sketch 
	 */
	private PApplet parent;
	
	/**
	 * Create a text to speech or a speech recognizer or both, using a grammar 
	 * file to specified the recognizer tokens
	 *
	 * @param parent Parent Skecth
	 * @param initSynthesis true to enable synthesize, false otherwise 
	 * @param initRecognition true to enable recognition, false otherwise
	 * @param grammarName Name of the grammar file store in the data folder
	 */
	public MVoceP(PApplet parent,
		boolean initSynthesis, boolean initRecognition, 
		String grammarName)
	{
		// Set parent 
		this.parent = parent;
		
		// Get data path 
		String dataPath = parent.dataPath(".");
		
		// Init the speech interface, enable the engines 
		// and set the data folder like the voce folder and grammar folder 
		SpeechInterface.init(dataPath,
			initSynthesis,initRecognition,
			dataPath,grammarName);		
	}
	
	/**
	 * Requests that the given string be synthesized as soon as possible. 
	 * 
	 * @param message The message to sinthesize
	 */
	public void synthesize(String message)
	{
		SpeechInterface.synthesize(message);
	}
	
	/**
	 * Tells the speech synthesizer to stop synthesizing. 
	 * This cancels all pending messages.
	 */
	public void stopSynthesizing()
	{
		SpeechInterface.stopSynthesizing();
	}		
	
	/**
    * Returns the number of recognized strings currently in the recognizer's 
    * queue.
    * 
    * @return Number of tokens recognized
    */      	
	public int numTokens()
	{
		return SpeechInterface.getRecognizerQueueSize();
	}
	
	/**
	 * Returns and removes the oldest recognized string from the recognizer's 
	 * queue.
	 *
	 * @return oldest recognized string
	 */ 
	public String nextToken()
	{
		return SpeechInterface.popRecognizedString();
	}
	
	/**
	 * Enables and disables the speech recognizer. 
	 *
	 * @param e true to enable the recognizer, false other wise
	 */	
	public void recognizerEnable(boolean e)
	{
		SpeechInterface.setRecognizerEnabled(e);
	}
	
	/**
	 * Returns true if the recognizer is currently enabled. 
	 *
	 * @return true if the recognizer is currently enabled, false otherwise
	 */ 
	public boolean isRecognizerEnabled()
	{
		return SpeechInterface.isRecognizerEnabled();
	}
	
	/**
	 * Free resources 
	 */
	public void destroy()
	{
		SpeechInterface.destroy();
	}
}