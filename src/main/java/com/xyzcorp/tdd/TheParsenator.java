package com.xyzcorp.tdd;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TheParsenator {

	private String delimeter;

	public TheParsenator(String delimiter) {
		this.delimeter = delimiter;
	}

	public List<CheckoutRecord> getCheckoutRecords(Stream<String> stream) {
		return stream.map(this::convertStringToRecord).collect(Collectors.toList());
	}

	private CheckoutRecord convertStringToRecord(String record) {
		String[] items = record.split(delimeter);
		if (items.length != 3)
			throw new IllegalArgumentException(
					String.format("Invalid data could not split on: %s", delimeter));
		return new CheckoutRecord(items[0], items[1], LocalDate.parse(items[2]));
	}
}
