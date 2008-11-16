package mjs.processing.mobile.mthinlet;

import processing.core.*;

import thinlet.midp.*;

import javax.microedition.lcdui.*;

public class MThinlet extends Thinlet
{
	public final static int ACTION_EVENT = 1;
	
	PMIDlet pMIDlet;

	MThinletCanvas mThinletCanvas;

	/**
	 * Command used to exit from the MIDlet. Same as PMIDlet cmdExit
	 */

	private Command cmdExit;

	public MThinlet(final PMIDlet pMIDlet)
	{
		super(pMIDlet);
		
		this.pMIDlet = pMIDlet;
		mThinletCanvas = new MThinletCanvas(pMIDlet);

		// Create the exit command, 
		// cmdExit on PMIDlet have protected privilege
		
		cmdExit = new Command("Exit",Command.EXIT,1);
		addCommand(cmdExit);
		
		// Listen the exit command and also the softkey
		// We can't use pMIDlet because cmdExit is different
		
		setCommandListener
		(
			new CommandListener()
			{
				public void commandAction(Command c, Displayable d)
				{
					// Exit from the PMIDlet
					if(c == cmdExit)
						pMIDlet.exit();
					else 
					{
						// Send sofkey pressed event
						pMIDlet.enqueueEvent(PMIDlet.EVENT_SOFTKEY_PRESSED,0,c.getLabel());

						// Send Command to Thinlet Canvas
						MThinlet.this.commandAction(c,d);
					}
				}
			}
		);		
	}

	public void show()
	{
		pMIDlet.display.setCurrent(this);
		pMIDlet.canvas = mThinletCanvas;
	}

	public void load(String filename)
	{
		add(parse(filename));
	}

	public Object parse(String filename)
	{
		try
		{
			if(!filename.startsWith("/"))
				filename = "/" + filename;
			
			return parse(getClass().getResourceAsStream(filename));
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	public boolean handle(Object source, Object part, String action, Object[] args)
	{
		MInvoke invoke = new MInvoke(source,part,action,args);
		pMIDlet.enqueueLibraryEvent(this,ACTION_EVENT,invoke);
		
		return true;
	}

	public class MThinletCanvas extends PCanvas
	{
		MThinletCanvas(PMIDlet pMIDlet)
		{
			super(pMIDlet);
		}
	}
}