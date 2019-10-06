package com.imfsoftware.datastructure;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for UnrolledLinkedList.
 */
public class UnrolledLinkedListTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public UnrolledLinkedListTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(UnrolledLinkedListTest.class);
	}

	/**
	 * Test isEmpty
	 */
	public void testEmptyList() {
		UnrolledLinkedList<String> testList = new UnrolledLinkedList<>(5);
		assertTrue(testList.isEmpty());
		testList.add("Item 1");
		assertFalse(testList.isEmpty());
		System.out.print(".");
	}

	/**
     * Ensure that checkpos generates appropriate errors
     */
    public void testCheckPos()
    {
        UnrolledLinkedList<String> testList = new UnrolledLinkedList<>(5);
        try {
        	String val = testList.get(0);
			assertTrue(val != null);
        } catch (IndexOutOfBoundsException e) {
        	// expect exception.
        }
        
        testList.add("Hello");
        
        try {
        	String val = testList.get(1);
			assertTrue(val != null);
        } catch (IndexOutOfBoundsException e) {
        	// expect exception.
        }
		System.out.print(".");
    }
    
    public void testSize() 
    {
        UnrolledLinkedList<String> testList = new UnrolledLinkedList<>(5);
        assert(testList.size() == 0);
        testList.add("Hello");
        assert(testList.size() == 1);
        for (int i=0; i < 10; i++) {
            testList.add("Hello"+i);
        }
        assert(testList.size() == 11);
		System.out.print(".");
    }
    
    public void testContains()
    {
        UnrolledLinkedList<String> testList = new UnrolledLinkedList<>(5);
        assertFalse(testList.contains("Foo"));
        testList.add("Foo");
        assertTrue(testList.contains("Foo"));
		System.out.print(".");
    }
    
    public void testIndexAddRemoveSet()
    {
        UnrolledLinkedList<String> testList = new UnrolledLinkedList<>(5);
        
        for (int i=1; i < 10; i++) {
            testList.add(i, "Hello"+i);
        }
        
        assertEquals("[Hello1,Hello2,Hello3,Hello4,Hello5][Hello6,Hello7,Hello8,Hello9]", testList.toString());
        
        testList.remove(3);
        
        assertEquals("[Hello1,Hello2,Hello4,Hello5][Hello6,Hello7,Hello8,Hello9]", testList.toString());
        
        testList.add(3,"Hello12");
        
        assertEquals("[Hello1,Hello2,Hello12][Hello4,Hello5][Hello6,Hello7,Hello8,Hello9]", testList.toString());
        
        testList.set(3,"Hello3");
        assertEquals("[Hello1,Hello2,Hello3][Hello4,Hello5][Hello6,Hello7,Hello8,Hello9]", testList.toString());
		System.out.print(".");
    }
}
