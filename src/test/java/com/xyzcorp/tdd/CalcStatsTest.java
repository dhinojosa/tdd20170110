package com.xyzcorp.tdd;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalcStatsTest {

	// 1. Know what you want to do: Determine minimum of an array
	// 2. Find the absolute simplest that you can do first

	@Test
	public void testMinimumWithOneElement() {
		// 3. Go through some design possibilities
		CalcStats cs = new CalcStats(new int[] { 1 });
		int actual = cs.getMinimum();
		assertEquals(1, actual);
	}

	// What will give us a red bar
	@Test
	public void testMinimumWithTwoDifferentElements() {
		CalcStats cs = new CalcStats(new int[] { 2, 4 });
		int actual = cs.getMinimum();
		assertEquals(2, actual);
	}

	@Test
	public void testMinimumWithTwoDifferentElementsUnsorted() {
		CalcStats cs = new CalcStats(new int[] { 4, 2 });
		int actual = cs.getMinimum();
		assertEquals(2, actual);
	}
	
	@Test
	public void testMinimumWithThreeDifferentElementsUnsorted() {
		CalcStats cs = new CalcStats(new int[] { 4, 2, 1 });
		int actual = cs.getMinimum();
		assertEquals(1, actual);
	}
	
	//NonRedBar
	@Test
	public void testMinimumWithThreeDifferentElementsUnsortedWithANegative() {
		CalcStats cs = new CalcStats(new int[] { 4, -2, 1 });
		int actual = cs.getMinimum();
		assertEquals(-2, actual);
	}
}
