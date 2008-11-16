package mjs.processing.mobile.msound.tests;

import j2meunit.midletui.TestRunner;

public class MSoundTestMIDlet extends TestRunner
{
	public MSoundTestMIDlet()
	{
	}

	public void startApp()
	{
		start(new String[] { "mjs.processing.mobile.msound.tests.TestAll" } );
	}
}