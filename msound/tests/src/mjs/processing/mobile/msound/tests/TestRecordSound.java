package mjs.processing.mobile.msound.tests;

import mjs.processing.mobile.msound.*;

import j2meunit.framework.*;

public class TestRecordSound extends TestCase
{
	public TestRecordSound()
	{
	}

	public TestRecordSound(String name, TestMethod testMethod)
	{
		super(name,testMethod);
	}

	public Test suite()
	{
		TestSuite testSuite = new TestSuite();

		testSuite.addTest
		(
			new TestRecordSound
			(
				"testCreation", 
				new TestMethod()
				{	
					public void run(TestCase tc)
					{
						((TestRecordSound) tc).testCreation();
					}
				}
			)
		);

		testSuite.addTest
		(
			new TestRecordSound
			(
				"testRecord", 
				new TestMethod()
				{	
					public void run(TestCase tc)
					{
						((TestRecordSound) tc).testRecord();
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
			MSoundRecorder soundRecorder = MSoundManager.createRecorder();
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	public void testRecord()
	{
		try
		{
			MSoundRecorder soundRecorder = MSoundManager.createRecorder();

			soundRecorder.play();
			soundRecorder.startRecord();

			Thread.sleep(10000);

			soundRecorder.stopRecord();

			byte[] data = soundRecorder.read();

			assertTrue(data.length != 0);
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}
	
}