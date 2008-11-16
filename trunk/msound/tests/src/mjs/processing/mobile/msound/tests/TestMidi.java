package mjs.processing.mobile.msound.tests;

import mjs.processing.mobile.msound.*;

import j2meunit.framework.*;

public class TestMidi extends TestCase
{
	public TestMidi()
	{
	}

	public TestMidi(String name, TestMethod testMethod)
	{
		super(name,testMethod);
	}

	public Test suite()
	{
		TestSuite testSuite = new TestSuite();

		testSuite.addTest
		(
			new TestMidi
			(
				"testCreation", 
				new TestMethod()
				{	
					public void run(TestCase tc)
					{
						((TestMidi) tc).testCreation();
					}
				}
			)
		);

		testSuite.addTest
		(
			new TestMidi
			(
				"testNoteOn", 
				new TestMethod()
				{	
					public void run(TestCase tc)
					{
						((TestMidi) tc).testNoteOn();
					}
				}
			)
		);

		testSuite.addTest
		(
			new TestMidi
			(
				"testNoteOff", 
				new TestMethod()
				{	
					public void run(TestCase tc)
					{
						((TestMidi) tc).testNoteOff();
					}
				}
			)
		);

		testSuite.addTest
		(
			new TestMidi
			(
				"testProgramChange", 
				new TestMethod()
				{	
					public void run(TestCase tc)
					{
						((TestMidi) tc).testProgramChange();
					}
				}
			)
		);

		testSuite.addTest
		(
			new TestMidi
			(
				"testChannelVolume", 
				new TestMethod()
				{	
					public void run(TestCase tc)
					{
						((TestMidi) tc).testChannelVolume();
					}
				}
			)
		);

		return testSuite;
	}

	public void testCreation()
	{
		try
		{
			MMidi midi = new MMidi();
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	public void testNoteOn()
	{
		try
		{
			MMidi midi = new MMidi();
			midi.noteOn(1,64,100);
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	public void testNoteOff()
	{
		try
		{
			MMidi midi = new MMidi();
			midi.noteOn(1,64,100);
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	public void testProgramChange()
	{
		boolean error = false;
		
		try
		{
			MMidi midi = new MMidi();
			midi.programChange(1,9);
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	public void testChannelVolume()
	{
		try
		{
			MMidi midi = new MMidi();
			midi.channelVolume(1,90);
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}
}