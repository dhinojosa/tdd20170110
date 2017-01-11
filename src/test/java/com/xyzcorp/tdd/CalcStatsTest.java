package com.xyzcorp.tdd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.assertj.core.data.Offset;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CalcStatsTest {

	// 1. Know what you want to do: Determine minimum of an array
	// 2. Find the absolute simplest that you can do first

	private CalcStats calcStatWithOneElement;
	private CalcStats calcStatWithEmptyArray;
//	
//	@BeforeClass
//	public static void setUpBeforeClass() {
//	    System.out.println("Before Class");	
//	}
//	
//	@AfterClass
//	public static void setUpAfterClass() {
//	    System.out.println("After Class");	
//	}
	
	@Before
	public void setUp() {
//		System.out.println("setup up");
		calcStatWithOneElement = new CalcStats(new int[] { 1 });
		calcStatWithEmptyArray = new CalcStats(new int[] {});
	}
	
//	@After
//	public void tearDown() {
//		System.out.println("tear down");
//	}
	
	@Test
	public void testMinimumWithOneElement() {
		// 3. Go through some design possibilities
		Optional<Integer> actual = calcStatWithOneElement.getMinimum();
		assertEquals(new Integer(1), actual.get());
	}

	// What will give us a red bar
	@Test
	public void testMinimumWithTwoDifferentElements() {
		CalcStats cs = new CalcStats(new int[] { 2, 4 });
		Optional<Integer> actual = cs.getMinimum();
		assertEquals(new Integer(2), actual.get());
	}

	@Test
	public void testMinimumWithTwoDifferentElementsUnsorted() {
		CalcStats cs = new CalcStats(new int[] { 4, 2 });
		Optional<Integer> actual = cs.getMinimum();
		assertEquals(new Integer(2), actual.get());
	}
	
	@Test
	public void testMinimumWithThreeDifferentElementsUnsorted() {
		CalcStats cs = new CalcStats(new int[] { 4, 2, 1 });
		Optional<Integer> actual = cs.getMinimum();
		assertEquals(new Integer(1), actual.get());
	}
	
	//NonRedBar
	@Test
	public void testMinimumWithThreeDifferentElementsUnsortedWithANegative() {
		CalcStats cs = new CalcStats(4, -2, 1);
		Optional<Integer> actual = cs.getMinimum();
		assertEquals(new Integer(-2), actual.get());
	}
	
	@Test
	public void testMinimumWithEmptyArray() { 
		Optional<Integer> actual = calcStatWithEmptyArray.getMinimum();
		assertEquals(Optional.empty(), actual);
	}
	
	@Test
	public void testWithNullClassicExceptionHandling() { 
		try {
		   new CalcStats(null);
		   fail("This line should never run");
		} catch (IllegalArgumentException iae) {
		   assertEquals("Array is null", iae.getMessage());
		}
	}
	
	//import org.junit.rules.ExpectedException;

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testWithNullRuleExceptionHandling() { 
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Array is null");
		new CalcStats(null);
	}

	@Test
	public void testMaximumWithOneElement() {
		// 3. Go through some design possibilities
		Optional<Integer> actual = calcStatWithOneElement.getMaximum();
	    assertThat(actual).as("The actual value should be 1").contains(1);
	}
	
	@Test
	public void testMaximumWithTwoDifferentElements() {
		CalcStats cs = new CalcStats(new int[] { 2, 4 });
		assertThat(cs.getMaximum()).contains(4);
	}
	
	@Test
	public void testMaximumWithTwoDifferentElementsUnordered() {
		CalcStats cs = new CalcStats(new int[] { 4, 2 });
		assertThat(cs.getMaximum()).contains(4);
	}
	
	@Test
	public void testMaximumWithFourDifferentElementsUnsorted() {
		CalcStats cs = new CalcStats(new int[] { 4, 2, 10, 1 });
		assertThat(cs.getMaximum()).contains(10);
	}
	
	@Test
	public void testMaximumWithEmptyArray() { 
		Optional<Integer> actual = calcStatWithEmptyArray.getMaximum();
		assertEquals(Optional.empty(), actual);
	}

	@Test
	public void testSizeWithEmpty() {
		int actual = calcStatWithEmptyArray.getSize();
		assertThat(actual).isEqualTo(0);
	}

	@Test
	public void testSizeWithArrayOfOneElement() {
		int actual = calcStatWithOneElement.getSize();
		assertThat(actual).isEqualTo(1);
	}

	@Test
	public void testAverageWithAnEmptyArray() {
		Optional<Double> actual = calcStatWithEmptyArray.getAverage();
		assertThat(actual).isEmpty();
	}

	@Test
	public void testAverageWithAnArrayOfOneElement() {
		Optional<Double> actual = calcStatWithOneElement.getAverage();
		assertThat(actual).contains(1.0);
	}

	@Test
	public void testAverageWithAnArrayOfTwoElements() {
		CalcStats cs = new CalcStats(0, 100);
		Optional<Double> actual = cs.getAverage();
		assertThat(actual).contains(50.0);
	}

	@Test
	public void testAverageWithAnArrayOfThreeElements() {
		CalcStats cs = new CalcStats(25, 25, 50);
		Optional<Double> actual = cs.getAverage();
		assertThat(actual.get()).isCloseTo(33.33, Offset.offset(.01));
	}
}
