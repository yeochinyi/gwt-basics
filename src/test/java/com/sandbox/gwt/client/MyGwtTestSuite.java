package com.sandbox.gwt.client;

import junit.framework.Test;
import junit.framework.TestCase;

import com.google.gwt.junit.tools.GWTTestSuite;
/**
 * GWTTestCase derived tests are slow. This is because the JUnitShell has to load the module for each test (create the shell, hook into it, etc). GWTTestSuite mitigates this by grouping all the tests that are for the same module (those that return the same value for getModuleName) together and running them via the same shell instance.
 * @author Chris
 *
 */
public class MyGwtTestSuite extends TestCase /*note this is TestCase and not TestSuite */
{
    public static Test suite()
    {
        GWTTestSuite suite = new GWTTestSuite( "All Gwt Tests go in here" );
        suite.addTestSuite( MyClassTestGwt.class );
        return suite;
    }
}
