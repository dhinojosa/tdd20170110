package com.xyzcorp.tdd;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CheckoutRecordTest {

	@Test
	@Category(value = UnitTest.class)
	public void testBookWasCheckedOutSameDay() {
		CheckoutRecord checkoutRecord = new CheckoutRecord("Anu Narahari", "Charlotte's Web",
				LocalDate.of(2017, 1, 25));
		int penalty = checkoutRecord.calculatePenalty(LocalDate.of(2017, 1, 25));
		assertThat(penalty).isEqualTo(0);
	}

	@Test
	@Category(value = UnitTest.class)
	public void testBookWasCheckoutAMonthAgo() {
		LocalDate checkoutDate = LocalDate.of(2017, 1, 25);
		LocalDate today = LocalDate.of(2017, 2, 25);

		CheckoutRecord checkoutRecord = 
				new CheckoutRecord("Anu Narahari", "Charlotte's Web", checkoutDate);

		int penalty = checkoutRecord.calculatePenalty(today);
		assertThat(penalty).isEqualTo(10);
	}
	
	@Test
	@Category(value = UnitTest.class)
	public void testBookWasCheckedOutByDrEmmitBrownAndWentBackToFuture() {
		LocalDate today = LocalDate.of(2017, 1, 25);
		LocalDate checkoutDate = LocalDate.of(2017, 2, 25);
		CheckoutRecord checkoutRecord = 
				new CheckoutRecord("Anu Narahari", "Charlotte's Web", checkoutDate);
		int penalty = checkoutRecord.calculatePenalty(today);
		assertThat(penalty).isEqualTo(0);
	}

}
