package com.xyzcorp.tdd;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class TheParsenatorTest {

	@Test
	@Category(value = UnitTest.class)
	public void testEmptyStream() {
		Stream<String> stream = Stream.empty();
		TheParsenator theParsenator = new TheParsenator("~");
		List<CheckoutRecord> pojos = theParsenator.getCheckoutRecords(stream);
		assertThat(pojos).isEmpty();
	}

	@Test
	@Category(value = UnitTest.class)
	public void testSingleItemList() {
		Stream<String> stream = Stream.of("Chris Reinke~Sojourn~1849-10-12");
		TheParsenator theParsenator = new TheParsenator("~");
		List<CheckoutRecord> pojos = theParsenator.getCheckoutRecords(stream);
		assertThat(pojos).hasSize(1);
		assertThat(pojos.get(0).getName()).isEqualTo("Chris Reinke");
		assertThat(pojos.get(0).getTitle()).isEqualTo("Sojourn");
		assertThat(pojos.get(0).getDate()).isEqualTo(LocalDate.of(1849, 10, 12));
	}

	@Test
	@Category(value = UnitTest.class)
	public void testTwoItemList() {
		Stream<String> stream = Stream.of("Chris Reinke~Sojourn~1849-10-12",
				"Phi Tran~Harry Potter and the Sorcerer's Stone~2015-03-04");
		TheParsenator theParsenator = new TheParsenator("~");
		List<CheckoutRecord> pojos = theParsenator.getCheckoutRecords(stream);
		assertThat(pojos).hasSize(2);
		assertThat(pojos.get(1).getName()).isEqualTo("Phi Tran");
		assertThat(pojos.get(1).getTitle()).isEqualTo("Harry Potter and the Sorcerer's Stone");
		assertThat(pojos.get(1).getDate()).isEqualTo(LocalDate.of(2015, 3, 4));
	}

	// NoRedBar
	@Test
	@Category(value = UnitTest.class)
	public void testCommasDelimitedTwoItems() {
		Stream<String> stream = Stream.of("Chris Reinke,Sojourn,1849-10-12",
				"Phi Tran,Harry Potter and the Sorcerer's Stone,2015-03-04");
		TheParsenator theParsenator = new TheParsenator(",");
		List<CheckoutRecord> pojos = theParsenator.getCheckoutRecords(stream);
		assertThat(pojos).hasSize(2);
		assertThat(pojos.get(1).getName()).isEqualTo("Phi Tran");
		assertThat(pojos.get(1).getTitle()).isEqualTo("Harry Potter and the Sorcerer's Stone");
		assertThat(pojos.get(1).getDate()).isEqualTo(LocalDate.of(2015, 3, 4));
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	@Category(value = UnitTest.class)
	public void testCommasDelimitedExtraComma() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid data could not split on: ,");
		Stream<String> stream = Stream.of("Chris Reinke,Sojourn,1849-10-12",
				"Phi Tran,Harry Potter, and the Sorcerer's Stone,2015-03-04");
		TheParsenator theParsenator = new TheParsenator(",");
		theParsenator.getCheckoutRecords(stream);
	}
	
	@Test
	@Category(value = UnitTest.class)
	public void testCommasDelimitedAnApostrophe() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid data could not split on: \'");
		Stream<String> stream = Stream.of("Chris Reinke\'Sojourn\'1849-10-12",
				"Phi Tran\'Harry Potter and the Sorcerer's Stone\'2015-03-04");
		TheParsenator theParsenator = new TheParsenator("'");
		theParsenator.getCheckoutRecords(stream);
	}
	
	@Test
	@Category(value = UnitTest.class)
	public void testCommasDelimitedAnH() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid data could not split on: H");
		Stream<String> stream = Stream.of("Chris ReinkeHSojournH1849-10-12",
				"Phi TranHHarry Potter and the Sorcerer's StoneH2015-03-04");
		TheParsenator theParsenator = new TheParsenator("H");
		theParsenator.getCheckoutRecords(stream);
	}
	
	@Test
	@Category(value = IntegrationTest.class) 
	public void testWithRealFile() {
		InputStream is = getClass().getResourceAsStream("/library.txt");
		InputStreamReader reader = new InputStreamReader(is);
		BufferedReader buff = new BufferedReader(reader);
		TheParsenator theParsenator = new TheParsenator("~");
		List<CheckoutRecord> records = theParsenator.getCheckoutRecords(buff.lines());
		assertThat(records).hasSize(16);
	}
	
	@Test
	@Category(value = IntegrationTest.class) 
	public void testWithRealWebService() throws IOException {
		URL url = new URL("https://raw.githubusercontent.com/dhinojosa/tdd20170110/master/src/main/resources/library.txt");
		InputStream is = url.openStream();
		InputStreamReader reader = new InputStreamReader(is);
		BufferedReader buff = new BufferedReader(reader);
		TheParsenator theParsenator = new TheParsenator("~");
		List<CheckoutRecord> records = theParsenator.getCheckoutRecords(buff.lines());
		assertThat(records).hasSize(16);
	}
	
	@Test
	@Category(value = UnitTest.class)
	public void testCommasDelimitedTwoItemsInvalidDateOnOne() {
		thrown.expect(DateTimeParseException.class);
		Stream<String> stream = Stream.of("Chris Reinke,Sojourn,1849-10-12",
				"Phi Tran,Harry Potter and the Sorcerer's Stone,2015403-04");
		TheParsenator theParsenator = new TheParsenator(",");
		theParsenator.getCheckoutRecords(stream);
	}

}
