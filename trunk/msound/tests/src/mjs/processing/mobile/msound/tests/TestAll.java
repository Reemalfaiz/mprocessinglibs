package mjs.processing.mobile.msound.tests;

import mjs.processing.mobile.msound.*;

import j2meunit.framework.*;

public class TestAll extends TestCase
{
	public TestAll()
	{
	}

	public TestAll(String name)
	{
		super(name);
	}

	public Test suite()
	{
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new TestMidi().suite());
		testSuite.addTest(new TestRecordSound().suite());
		
		return testSuite;
	}
}