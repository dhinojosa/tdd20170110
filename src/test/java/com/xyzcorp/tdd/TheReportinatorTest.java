package com.xyzcorp.tdd;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TheReportinatorTest {

	@Test
	public void testEmptyList() {
	    	TheReportinator repo = new TheReportinator(Arrays.asList(), null);
	    	List<CheckoutRecord> checkoutRecords = repo.reportViolators(5);
		assertThat(checkoutRecords).isEmpty();
	}
	
	@Test
	public void testOneItemList() {
		Supplier<LocalDate> supplier = () -> LocalDate.of(2017, 2, 22);
		List<CheckoutRecord> init = Arrays.asList(new CheckoutRecord("Dan", "The Beirnstain", LocalDate.of(2000, 2, 22)));
	    	TheReportinator repo = new TheReportinator(init, supplier);
	    	List<CheckoutRecord> checkoutRecords = repo.reportViolators(5);
		assertThat(checkoutRecords).hasSize(1);
	}
	
	@Test
	public void testTwoItemList() {
		Supplier<LocalDate> supplier = () -> LocalDate.of(2017, 2, 22);
		List<CheckoutRecord> init = Arrays.asList(
				new CheckoutRecord("Ann", "Eating Bugs", LocalDate.of(2017, 1, 25)),
				new CheckoutRecord("Dan", "The Beirnstein", LocalDate.of(2000, 2, 22)));
	    	TheReportinator repo = new TheReportinator(init, supplier);
	    	List<CheckoutRecord> checkoutRecords = repo.reportViolators(5);
		assertThat(checkoutRecords).hasSize(1);
	}
	
	@Test
	public void testFourItemListWithTopThree() {
		Supplier<LocalDate> supplier = () -> LocalDate.of(2017, 2, 22);
		List<CheckoutRecord> init = Arrays.asList(
				new CheckoutRecord("Ann", "Eating Bugs", LocalDate.of(2016, 7, 9)),
				new CheckoutRecord("Dan", "The Beirnstein", LocalDate.of(2015, 2, 22)),
				new CheckoutRecord("Taylor", "To Kill A Mockingbird", LocalDate.of(2016, 1, 25)),
				new CheckoutRecord("Norman", "Les Miserable", LocalDate.of(2016, 3, 1)));
	    	TheReportinator repo = new TheReportinator(init, supplier);
	    	List<CheckoutRecord> checkoutRecords = repo.reportViolators(3);
		assertThat(checkoutRecords).hasSize(3);
	}
	
	
	@Test
	public void testEveryoneIsGreat() {
		Supplier<LocalDate> supplier = () -> LocalDate.of(2017, 2, 22);
		List<CheckoutRecord> init = Arrays.asList(
				new CheckoutRecord("Ann", "Eating Bugs", LocalDate.of(2017, 2, 9)),
				new CheckoutRecord("Dan", "The Beirnstein", LocalDate.of(2017, 1, 23)),
				new CheckoutRecord("Taylor", "To Kill A Mockingbird", LocalDate.of(2017, 1, 30)),
				new CheckoutRecord("Norman", "Les Miserable", LocalDate.of(2017, 2, 1)));
	    	TheReportinator repo = new TheReportinator(init, supplier);
	    	List<CheckoutRecord> checkoutRecords = repo.reportViolators(10);
		assertThat(checkoutRecords).hasSize(0);
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testLimitIsNegative() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Limit cannot be negative");
		Supplier<LocalDate> supplier = () -> LocalDate.of(2017, 2, 22);
		List<CheckoutRecord> init = Arrays.asList(
				new CheckoutRecord("Ann", "Eating Bugs", LocalDate.of(2017, 2, 9)),
				new CheckoutRecord("Dan", "The Beirnstein", LocalDate.of(2017, 1, 23)),
				new CheckoutRecord("Taylor", "To Kill A Mockingbird", LocalDate.of(2017, 1, 30)),
				new CheckoutRecord("Norman", "Les Miserable", LocalDate.of(2017, 2, 1)));
	    	TheReportinator repo = new TheReportinator(init, supplier);
	    	List<CheckoutRecord> checkoutRecords = repo.reportViolators(-10);
	}
	
	@Test
	public void testFourItemListWithTopThreeAsStringReport() {
		Supplier<LocalDate> supplier = () -> LocalDate.of(2017, 2, 22);
		List<CheckoutRecord> init = Arrays.asList(
				new CheckoutRecord("Ann", "Eating Bugs", LocalDate.of(2016, 7, 9)),
				new CheckoutRecord("Dan", "The Beirnstein", LocalDate.of(2016, 2, 22)),
				new CheckoutRecord("Taylor", "To Kill A Mockingbird", LocalDate.of(2016, 1, 25)),
				new CheckoutRecord("Norman", "Les Miserable", LocalDate.of(2016, 3, 1)));
	    	TheReportinator repo = new TheReportinator(init, supplier);
	    	List<String> strings = repo.reportViolatorsAsString(3);
		assertThat(strings).hasSize(3);
		assertThat(strings).contains("Dan : 120");
	}
	
}
